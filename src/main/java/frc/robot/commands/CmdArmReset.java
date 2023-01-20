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
        if (_armSubsystem.isLimitSwitchPressed())
        {
            _armSubsystem.setExtensionMotorSpeed(0);
            return true;
        }
        else
        {
            return false;
        }
    }
}
