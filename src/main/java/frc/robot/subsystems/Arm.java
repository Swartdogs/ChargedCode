package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase 
{
    private DigitalInput _limitSwitch;
    private DutyCycleEncoder _pitchEncoder;
    private CANSparkMax _linearMotor;
    private CANSparkMax _pitchMotor;

    public Arm() 
    {

    }

}
