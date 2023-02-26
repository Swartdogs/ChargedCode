package frc.robot.subsystems.drive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.robot.Constants;

public class SwerveModule extends Vector
{
    private CANSparkMax          _driveMotor;
    private CANSparkMax          _rotateMotor;
    private AnalogPotentiometer  _rotateSensor;
    private PIDControl           _rotatePID;
    
    private double            _rotateSetpoint;
    private double            _driveSpeedSetpoint;

    private double            _rotationZero;
    private double            _resetOffset;

    private double            _drivePosition;

    public SwerveModule(double x,                   // (positive right) relative to the center of the robot 
                        double y,                   // (positive forward) relative to the center of the robot
                        double defaultRelativeZero, // default zeroing position
                        double resetOffset,         // for physically rotated modules, reset the offsets at a different angle
                        int driveMotorCanId,
                        int rotateMotorCanId,
                        int rotateSensorPort)
    {
        super(x, y);

        _rotateSetpoint      = 0;
        _driveSpeedSetpoint  = 0;

        _rotationZero        = defaultRelativeZero;
        _resetOffset         = resetOffset;

        _drivePosition       = 0;

        _driveMotor          = new CANSparkMax(driveMotorCanId,  MotorType.kBrushless);
        _rotateMotor         = new CANSparkMax(rotateMotorCanId, MotorType.kBrushless);
        _rotateSensor        = new AnalogPotentiometer(rotateSensorPort, 360 / 0.92, (360 - 360 / 0.92) / 2.0); // copied from 2021

        _driveMotor.restoreFactoryDefaults();
        _rotateMotor.restoreFactoryDefaults(); // doesn't matter how the sparkmax has been previously set up

        _driveMotor.setIdleMode(IdleMode.kCoast);
        _rotateMotor.setIdleMode(IdleMode.kCoast);

        _driveMotor.getEncoder().setPositionConversionFactor(Constants.Drive.DRIVE_ENCODER_TO_DISTANCE);

        _rotatePID = new PIDControl();

        _rotatePID.setCoefficient(Coefficient.P, 0.0, 0.01, 0.0);// 1% power for every degree of error
        _rotatePID.setCoefficient(Coefficient.I, 0.0, 0.0, 0.0);
        _rotatePID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);

        // rotate continuously to the nearest 180 degrees
        _rotatePID.setInputRange(-90, 90);
        _rotatePID.setContinuous(true);

        _rotatePID.setOutputRange(-1, 1);

        _rotatePID.setSetpointDeadband(5);// 5 degree deadband recommended online by other teams
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

        // calling setSetpoint() then calculate() causes previous error to always be the same as the current error, meaning the Derivative coefficient will do nothing
        double rotateSpeed = _rotatePID.calculate(currentHeading);

        // invert if facing 180 off setpoint (continuous rotation), also scale if it's perpendicular
        double driveSpeed = _driveSpeedSetpoint * Math.cos(Math.toRadians(currentHeading - _rotateSetpoint));

        if (_rotatePID.atSetpoint())
        {
            _rotateMotor.stopMotor();
        }
        else
        {
            // setVoltage(speed * 12) is similar to set(speed); counteracts battery drain
            _rotateMotor.setVoltage(rotateSpeed * Constants.MOTOR_VOLTAGE);
        }

        _driveMotor.setVoltage(driveSpeed * Constants.MOTOR_VOLTAGE);
    }

    public double getHeading()
    {
        return Math.IEEEremainder(-_rotateSensor.get() - _rotationZero, 360);
    }

    /**
     * set the zero point so that the module is now facing the "reset offset" direction
     */
    public void zeroRotation()
    {
        _rotationZero = Math.IEEEremainder(-_rotateSensor.get() - _resetOffset, Constants.DEGREES_PER_REVOLUTION);
        System.out.println(String.format("%6.2f", _rotationZero));
    }

    /**
     * Get the rotation encoder's zero offset 
     * @return the offset as previously set in degrees
     */
    public double getRotationZero()
    {
        return _rotationZero;
    }

    /**
     * Set the zero point to align the module to offset encoder inputs
     * @param zero new zero position; scaled to the range of 0 to 360 degrees
     */
    public void setRotationZero(double zero)
    {
        _rotationZero = Math.IEEEremainder(zero, Constants.DEGREES_PER_REVOLUTION);
    }

    /**
     * get the current position of the drive motor's encoder
     * @return the position (distance), scaled to a usable unit
     */
    public double getDrivePosition()
    {
        return _driveMotor.getEncoder().getPosition();
    }

    /**
     * Get the change of position of the module 
     * @return Vector representing the distance and direction of the module's motion
     */
    public Vector updateOdometry()
    {
        
        Vector change = new Vector();

        double newDrivePosition = getDrivePosition();

        change.setPolarPosition((newDrivePosition - _drivePosition), getHeading());

        _drivePosition = newDrivePosition;

        return change;
    }
}
