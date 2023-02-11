package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;

public class CmdManipulatorSetTwistAngle extends CommandBase 
{
    private final Manipulator _manipulatorSubsystem;
    private final double      _twistAngle;

    public CmdManipulatorSetTwistAngle(double twistAngle) 
    {
        _manipulatorSubsystem = Manipulator.getInstance();
        _twistAngle           = twistAngle;
    }

    @Override
    public void initialize() 
    {
        double angle = _twistAngle;
        if(_manipulatorSubsystem.isFlipped())
        {
            angle = 180 - angle;
        }
        RobotLog.getInstance().log(String.format("Setting twist angle to %6.2f", angle));
        _manipulatorSubsystem.setTwistAngle(angle);
    }
    
    @Override
    public boolean isFinished()
    {
        return _manipulatorSubsystem.twistAtAngle();
    }
}
