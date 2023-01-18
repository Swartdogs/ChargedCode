package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;

import PIDControl.PIDControl;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase 
{
    private DigitalInput _limitSwitch;
    private DutyCycleEncoder _pitchEncoder;
    private CANSparkMax _linearMotor;
    private CANSparkMax _pitchMotor;
    private PIDControl _shoulderPid;
    private boolean _extensionPidActive;
    private PIDControl _extensionPid;
    

    public Arm() 
    {

    }

    public boolean isLimitSwitchPressed()
    {
        return _limitSwitch.get();
    }

    public double getExtensionPosition()
    {
        return 0;
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
