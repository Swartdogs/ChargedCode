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
        double angle = _twistAngle;
        if(_armSubsystem.isFlipped())
        {
            angle *= -1;
        }
        RobotLog.getInstance().log(String.format("Setting twist angle to %6.2f", angle));
        _armSubsystem.setTwistAngle(angle);
    }
    
    @Override
    public boolean isFinished()
    {
        return _armSubsystem.twistAtAngle();
    }
}
