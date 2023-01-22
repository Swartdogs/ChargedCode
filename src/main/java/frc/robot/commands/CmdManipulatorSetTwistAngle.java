package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;

public class CmdManipulatorSetTwistAngle extends InstantCommand 
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
        RobotLog.getInstance().log("Setting twist angle to " + _twistAngle);
        _manipulatorSubsystem.setTwistAngle(_twistAngle);
    }
}
