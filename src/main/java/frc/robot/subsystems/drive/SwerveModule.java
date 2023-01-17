package frc.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.robot.Constants;

public class SwerveModule extends Vector
{
    //private CANSparkMax          _driveMotor;
    private TalonFX                _driveMotor;
    private CANSparkMax          _rotateMotor;
    private AnalogPotentiometer  _rotateSensor;
    private PIDControl           _rotatePID;

    private PIDControl           _driveVelocityPID;
    
    private double            _rotateSetpoint;
    private double            _velocitySetpoint;

    private double            _relativeZero;

    private double            _oldDrivePosition;

    private double _driveVelocity;

    public SwerveModule(double x, 
                        double y,
                        int driveMotorCanId,
                        int rotateMotorCanId,
                        int rotateSensorPort)
    {
        super(x, y);

        _rotateSetpoint       = 0;
        _velocitySetpoint        = 0;

        _relativeZero         = 0.0;

        _driveVelocity = 0;

        _oldDrivePosition     = 0;

        _driveMotor = new TalonFX(driveMotorCanId);
        _rotateMotor = new CANSparkMax(rotateMotorCanId, MotorType.kBrushless);
        _rotateSensor = new AnalogPotentiometer(rotateSensorPort, 360 / 0.92, (360 - 360 / 0.92) / 2.0); // this number will need to change

        _rotatePID = new PIDControl();

        _rotatePID.setCoefficient(Coefficient.P, 0.0, 0.01, 0.0);
        _rotatePID.setCoefficient(Coefficient.I, 0.0, 0.0, 0.0);
        _rotatePID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);

        _rotatePID.setInputRange(-90, 90);
        _rotatePID.setContinuous(true);
        _rotatePID.setOutputRange(-1, 1);
        _rotatePID.setSetpointDeadband(1);

        _driveVelocityPID = new PIDControl();

        _driveVelocityPID.setCoefficient(Coefficient.P, 0.0, 0.05, 0.0);// FIXME: play with these PID coefficients
        _driveVelocityPID.setCoefficient(Coefficient.I, 0.0, 0.0, 0.0);
        _driveVelocityPID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);

        _driveVelocityPID.setInputRange(-Constants.Drive.MAX_DRIVE_SPEED, Constants.Drive.MAX_DRIVE_SPEED);// can't go faster than max
        _driveVelocityPID.setOutputRange(-1, 1);
        _driveVelocityPID.setFeedForward((setpoint)->setpoint * 0.015 + Math.signum(setpoint) * 0.01);// proportional, friction minimum
    }

    public void drive(Vector moduleCommand)
    {
        _velocitySetpoint  = moduleCommand.getMagnitude();
        _rotateSetpoint = moduleCommand.getHeading();

        double currentHeading = getHeading();

        _driveVelocityPID.setSetpoint(_velocitySetpoint, _velocitySetpoint * Math.cos(Math.toRadians(currentHeading)));

        double driveSpeed = _driveVelocityPID.calculate(_velocitySetpoint * Math.cos(Math.toRadians(currentHeading)));

        _rotatePID.setSetpoint(_rotateSetpoint, currentHeading);

        double rotateSpeed = _rotatePID.calculate(currentHeading);
        if (_rotatePID.atSetpoint())
        {
            _rotateMotor.setVoltage(0);
        }
        else
        {
            _rotateMotor.setVoltage(rotateSpeed * 12);// setVoltage is similar to set * 12; counteracts battery drain
        }

        _driveMotor.set(TalonFXControlMode.PercentOutput, driveSpeed);
    }

    public double getHeading()
    {
        return Math.IEEEremainder(_rotateSensor.get() - _relativeZero, 360);
    }

    public void zeroEncoder()
    {
        _relativeZero = _rotateSensor.get();
    }

    public double getRelativeZero()
    {
        return _relativeZero;
    }

    public void setRelativeZero(double zero)
    {
        _relativeZero = zero;
    }

    public double getDrivePosition()
    {
        //return _driveMotor.getEncoder().getPosition();
        return _driveMotor.getSensorCollection().getIntegratedSensorPosition();
    }

    public void resetDrivePosition()
    {
        _oldDrivePosition = getDrivePosition();
    }

    public Vector getOffset() 
    {
        Vector offset = new Vector();

        double drivePosition = getDrivePosition(); 

        _driveVelocity = (drivePosition - _oldDrivePosition) * Constants.LOOPS_PER_SECOND;
        
        offset.setMagnitude((drivePosition - _oldDrivePosition) * Constants.Drive.DRIVE_ENCODER_TO_DISTANCE);
        _oldDrivePosition = drivePosition;

        offset.setHeading(getHeading());

        return offset;
    }

    public double getVelocity()
    {
        return _driveVelocity;
    }
}
