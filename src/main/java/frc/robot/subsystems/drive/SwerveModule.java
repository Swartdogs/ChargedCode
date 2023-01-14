package frc.robot.subsystems.drive;

import com.revrobotics.CANSparkMax;

import PIDControl.PIDControl;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public abstract class SwerveModule extends Vector
{
    protected CANSparkMax          _driveMotor;
    protected CANSparkMax          _rotateMotor;
    protected AnalogPotentiometer  _positionSensor;
    protected PIDControl           _rotatePID;
    
    private double            _rotateSetpoint;
    private double            _driveSetpoint;

    private double            _relativeZero;

    private double            _oldDrivePosition;
    private double            _distanceScaler;

    public SwerveModule(double x, 
                        double y,
                        double relativeZero,
                        double distanceScaler)
    {
        super(x, y);

        _rotateSetpoint       = 0;
        _driveSetpoint        = 0;

        _relativeZero         = relativeZero;

        _oldDrivePosition     = 0;
        _distanceScaler       = distanceScaler;
    }

    public void drive(Vector moduleCommand)
    {
        _driveSetpoint  = moduleCommand.getR();
        _rotateSetpoint = moduleCommand.getTheta();
        
        drive();
    }

    public void drive()
    {
        double PIDPosition = Math.toRadians(_rotateSetpoint - getPosition());
        double driveSpeed = _driveSetpoint * Math.cos(PIDPosition);

        // https://www.desmos.com/calculator/sehmrxy3lt
        PIDPosition = Math.sin(PIDPosition) * (Math.cos(PIDPosition) / -Math.abs(Math.cos(PIDPosition)));

        _rotatePID.setSetpoint(0, PIDPosition);

        double rotateSpeed = _rotatePID.calculate(PIDPosition);
        _rotateMotor.set(rotateSpeed);

        _driveMotor.set(driveSpeed);
    }

    public double getPosition()
    {
        return _positionSensor.get() - _relativeZero;
    }

    public void setPosition(double newPosition)
    {
        return;
    }

    public double getRotateSetpoint()
    {
        return _rotateSetpoint;
    }

    public double getDriveSetpoint()
    {
        return _driveSetpoint;
    }

    public CANSparkMax getDriveMotor()
    {
        return _driveMotor;
    }

    public CANSparkMax getRotateMotor()
    {
        return _rotateMotor;
    }

    public PIDControl getRotatePID()
    {
        return _rotatePID;
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
        return _positionSensor.get();
    }

    public void resetDrivePosition()
    {
        _oldDrivePosition = getDrivePosition();
    }

    public Vector getOffset() 
    {
        Vector offset = new Vector();
        
        offset.setR((getDrivePosition() - _oldDrivePosition) * _distanceScaler);
        resetDrivePosition();

        offset.setTheta(getPosition());

        return offset;
    }
}
