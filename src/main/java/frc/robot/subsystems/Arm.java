package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase 
{
    public static Arm _instance;

    public static Arm getInstance()
    {
        if(_instance == null) 
        {
            _instance = new Arm();
        }

        return _instance;
    }

    private DigitalInput     _limitSwitch;
    private DutyCycleEncoder _pitchEncoder;
    private CANSparkMax      _linearMotor;
    private CANSparkMax      _pitchMotor;
    private CANSparkMax      _followerPitchMotor;
    private PIDControl       _shoulderPid;
    private boolean          _extensionPidActive;
    private PIDControl       _extensionPid;
    private RelativeEncoder  _extensionEncoder;

    public Arm() 
    {
        _limitSwitch        = new DigitalInput(0);
        _pitchEncoder       = new DutyCycleEncoder(1);
        _linearMotor        = new CANSparkMax(0, MotorType.kBrushless);
        _pitchMotor         = new CANSparkMax(1, MotorType.kBrushless);
        _followerPitchMotor = new CANSparkMax(2, MotorType.kBrushless);
        _extensionPidActive = false; 
        _extensionPid       = new PIDControl();
        _shoulderPid        = new PIDControl();
        _extensionEncoder   = _linearMotor.getEncoder();

        _shoulderPid.setCoefficient(Coefficient.P, 0, 0, 0);
        _shoulderPid.setCoefficient(Coefficient.I, 0, 0, 0);
        _shoulderPid.setCoefficient(Coefficient.D, 0, 0, 0);
        _shoulderPid.setInputRange(0, 0);
        _shoulderPid.setOutputRange(-1, 1);
        _shoulderPid.setSetpointDeadband(1);

        _extensionPid.setCoefficient(Coefficient.P, 0, 0, 0);
        _extensionPid.setCoefficient(Coefficient.I, 0, 0, 0);
        _extensionPid.setCoefficient(Coefficient.D, 0, 0, 0);
        _extensionPid.setInputRange(0, 0);
        _extensionPid.setOutputRange(-1, 1);
        _extensionPid.setSetpointDeadband(1);

        _extensionEncoder.setPositionConversionFactor(0);

        _followerPitchMotor.follow(_pitchMotor, true);
    }

    public boolean isLimitSwitchPressed()
    {
        return _limitSwitch.get();
    }

    public double getExtensionPosition()
    {
        return _extensionEncoder.getPosition();
    }

    public double getShoulderAngle()
    {
        return _pitchEncoder.get() - 45;
    }

    public void setExtensionMotorSpeed(double speed)
    {
        _linearMotor.set(speed);
        _extensionPidActive = false;
    }

    public void setExtensionMotorPosition(double position)
    {
       _extensionPidActive = true;
       _extensionPid.setSetpoint(position, getExtensionPosition());
    }

    public void resetExtension()
    {
        _extensionPidActive = false;
        _linearMotor.set(-0.5);

        if(_limitSwitch.get())
        {
            _linearMotor.set(0);
        }
    }

    public void setShoulderAngle(double angle)
    {
        _shoulderPid.setSetpoint(angle, getShoulderAngle());
    }

    @Override 
    public void periodic()
    {
        _pitchMotor.set(_shoulderPid.calculate(getShoulderAngle()));

        if (_extensionPidActive) 
        {
            _linearMotor.set(_extensionPid.calculate(getExtensionPosition()));
        }
    }

}
