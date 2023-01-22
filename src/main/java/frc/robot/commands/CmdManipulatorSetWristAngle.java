package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;

public class CmdManipulatorSetWristAngle extends InstantCommand 
{
    private final Manipulator _manipulatorSubsystem;
    private final double      _wristAngle;

    public CmdManipulatorSetWristAngle(double wristAngle) 
    {
        _manipulatorSubsystem = Manipulator.getInstance();
        _wristAngle           = wristAngle;
    }

    @Override
    public void initialize() 
    {
        RobotLog.getInstance().log("Setting wrist angle to " + _wristAngle);
        _manipulatorSubsystem.setWristAngle(_wristAngle);
    }
}
