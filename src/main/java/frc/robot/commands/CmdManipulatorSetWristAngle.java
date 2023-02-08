package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;

public class CmdManipulatorSetWristAngle extends CommandBase 
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
        RobotLog.getInstance().log(String.format("Setting wrist angle to %6.2f", _wristAngle));
        _manipulatorSubsystem.setWristAngle(_wristAngle);
    }

    @Override
    public boolean isFinished()
    {
        return _manipulatorSubsystem.wristAtAngle();
    }
}
