package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.RobotLog;

public class CmdManipulatorSetTwistAngle extends CommandBase 
{
    private final Arm _armSubsystem;
    private final double      _twistAngle;

    public CmdManipulatorSetTwistAngle(double twistAngle) 
    {
        _armSubsystem = Arm.getInstance();
        _twistAngle   = twistAngle;
    }

    @Override
    public void initialize() 
    {
        RobotLog.getInstance().log(String.format("Setting twist angle to %6.2f", _twistAngle));
        _armSubsystem.setTwistAngle(_twistAngle);
    }
    
    @Override
    public boolean isFinished()
    {
        return _armSubsystem.twistAtAngle();
    }
}
