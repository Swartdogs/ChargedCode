package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.RobotLog;

public class CmdArmReset extends CommandBase 
{
    private final Arm _armSubsystem;

    public CmdArmReset() 
    {
        _armSubsystem = Arm.getInstance();
    }

    @Override
    public void initialize()
    {
        RobotLog.getInstance().log("Initializing Arm Extension Reset");
    }

    @Override
    public void execute() 
    {
        _armSubsystem.setExtensionMotorSpeed(-0.5);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotLog.getInstance().log("Arm Extension Reset");
        _armSubsystem.setExtensionMotorSpeed(0);
        _armSubsystem.setExtensionEncoderPosition(0);
    }

    @Override
    public boolean isFinished() 
    {
        return _armSubsystem.isLimitSwitchPressed();
    }
}
