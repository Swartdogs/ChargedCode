package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.RobotLog;

public class CmdArmSetShoulderAngle extends CommandBase
{
    private final Arm     _armSubsystem;
    private final double  _shoulderAngle;

    public CmdArmSetShoulderAngle(double shoulderAngle) 
    {
        _armSubsystem  = Arm.getInstance();
        _shoulderAngle = shoulderAngle;
    }

    @Override
    public void initialize() 
    {
        RobotLog.getInstance().log("Setting arm shoulder angle to " + _shoulderAngle);
        _armSubsystem.setShoulderAngle(_shoulderAngle);
    }

    @Override
    public boolean isFinished()
    {
        return _armSubsystem.shoulderAtAngle();
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotLog.getInstance().log("Arm Shoulder at " + _shoulderAngle);
    }
}
