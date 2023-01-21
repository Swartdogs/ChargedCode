package frc.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.robot.Constants;

public class SwerveModule extends Vector
{
    //private CANSparkMax          _driveMotor;
    private TalonFX                _driveMotor;
    private CANSparkMax          _rotateMotor;
    private AnalogPotentiometer  _rotateSensor;
    private PIDControl           _rotatePID;
    
    private double            _rotateSetpoint;
    private double            _driveSpeedSetpoint;

    private double            _rotationZero;
    private double            _resetOffset;

    private double            _drivePosition;

    public SwerveModule(double x,// (positive right) relative to the center of the robot 
                        double y,// (positive forward) relative to the center of the robot
                        double defaultRelativeZero,// default zeroing position
                        double resetOffset,// for physically rotated modules, reset the offsets at a different angle
                        int driveMotorCanId,
                        int rotateMotorCanId,
                        int rotateSensorPort)
    {
        super(x, y);

        _rotateSetpoint       = 0;
        _driveSpeedSetpoint   = 0;

        _rotationZero         = defaultRelativeZero;
        _resetOffset          = resetOffset;

        _drivePosition        = 0;

        _driveMotor           = new TalonFX(driveMotorCanId);
        _rotateMotor          = new CANSparkMax(rotateMotorCanId, MotorType.kBrushless);
        _rotateSensor         = new AnalogPotentiometer(rotateSensorPort, 360 / 0.92, (360 - 360 / 0.92) / 2.0); // copied from 2021

        // the sensor is attached by a gear, so the drive motor must be inverted to counteract that
        // rotating the module more right should result in a more positive sensor reading (heading)
        
        // SET INVERTED DOES NOTHING;;;; DONT
        //_rotateMotor.setInverted(true);

        _rotatePID = new PIDControl();

        _rotatePID.setCoefficient(Coefficient.P, 0.0, 0.01, 0.0);// 1% power for every degree of error
        _rotatePID.setCoefficient(Coefficient.I, 0.0, 0.0, 0.0);
        _rotatePID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);

        // rotate continuously to the nearest 180 degrees
        _rotatePID.setInputRange(-90, 90);
        _rotatePID.setContinuous(true);

        _rotatePID.setOutputRange(-1, 1);

        _rotatePID.setSetpointDeadband(2.5);// deadband recommended online by other teams
    }

    /**
     * Drive the module (percent output)
     * @param moduleCommand a Vector where heading represents the target rotation of the module, while the magnitude represents the percent output of the drive moter
     * @return nothing lol
     */
    public void drive(Vector moduleCommand)
    {
        _driveSpeedSetpoint  = MathUtil.clamp(moduleCommand.getMagnitude(), -1, 1);
        _rotateSetpoint = moduleCommand.getHeading();

        double currentHeading = getHeading();

        _rotatePID.setSetpoint(_rotateSetpoint, currentHeading);

        double rotateSpeed = _rotatePID.calculate(currentHeading);// calling setSetpoint() then calculate() causes previous error to always be the same as the current error, meaning the Derivative coefficient will do nothing

        double driveSpeed = _driveSpeedSetpoint * Math.cos(Math.toRadians(currentHeading - _rotateSetpoint));

        if (_rotatePID.atSetpoint())
        {
            _rotateMotor.setVoltage(0);
        }
        else
        {
            //_rotateMotor.setVoltage(0.2 * 12);
            _rotateMotor.setVoltage(-rotateSpeed * 12);// setVoltage(speed * 12) is similar to set(speed); counteracts battery drain
        }

        _driveMotor.set(TalonFXControlMode.PercentOutput, driveSpeed);
        //System.out.println(String.format("rotate Setpoint: %6.2f, current heading: %6.2f", _rotateSetpoint, currentHeading));
        //System.out.println(String.format("Heading: %6.2f, PID Setpoint: %6.2f, PID Error: %6.2f, PID Output: %6.2f", getHeading(), _rotateSetpoint, _rotatePID.getError(), rotateSpeed));
    }

    public double getHeading()
    {
        return Math.IEEEremainder(-_rotateSensor.get() - _rotationZero, 360);
    }

    /**
     * set the zero point so that the module is now facing the "reset offset" direction
     */
    public void zeroEncoder()
    {
        _rotationZero = Math.IEEEremainder(-_rotateSensor.get() - _resetOffset, 360);
        System.out.println(_rotationZero);
    }

    /**
     * Get the rotation encoder's zero offset 
     * @return the offset as previously set in degrees
     */
    public double getRelativeZero()
    {
        return _rotationZero;
    }

    /**
     * Set the zero point to align the module to offset encoder inputs
     * @param zero new zero position; scaled to the range of 0 to 360 degrees
     */
    public void setRotationZero(double zero)
    {
        _rotationZero = Math.IEEEremainder(zero, 360);
    }

    /**
     * get the current position of the drive motor's encoder
     * @return the position (distance), scaled to a usable unit
     */
    public double getDrivePosition()
    {
        //return _driveMotor.getEncoder().getPosition();
        return _driveMotor.getSelectedSensorPosition() * Constants.Drive.DRIVE_ENCODER_TO_DISTANCE;
    }

    /**
     * Get the change of position of the module 
     * @return Vector representing the distance and direction of the module's motion
     */
    public Vector updateOdometry()
    {
        
        Vector change = new Vector();

        double newDrivePosition = getDrivePosition();

        change.setMagnitude((newDrivePosition - _drivePosition));
        change.setHeading(getHeading());

        _drivePosition = newDrivePosition;

        return change;
    }
}
