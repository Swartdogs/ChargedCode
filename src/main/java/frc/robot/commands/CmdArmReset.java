package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class CmdArmReset extends CommandBase 
{
    private final Arm _armSubsystem;

    public CmdArmReset() 
    {
        _armSubsystem = Arm.getInstance();
    }

    @Override
    public void execute() 
    {
        _armSubsystem.setExtensionMotorSpeed(-0.5);
    }

    @Override
    public boolean isFinished() 
    {
        return _armSubsystem.isLimitSwitchPressed();
    }

    public void end(boolean interrupted)
    {
        _armSubsystem.setExtensionMotorSpeed(0);
        _armSubsystem.setExtensionEncoderPosition(0);
    }
}
