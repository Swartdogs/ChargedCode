package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.RobotLog;

public class CmdManipulatorSetWristAngle extends CommandBase 
{
    private final Arm _armSubsystem;
    private final double      _wristAngle;

    public CmdManipulatorSetWristAngle(double wristAngle) 
    {
        _armSubsystem = Arm.getInstance();
        _wristAngle           = wristAngle;
    }

    @Override
    public void initialize() 
    {
        RobotLog.getInstance().log(String.format("Setting wrist angle to %6.2f", _wristAngle));
        _armSubsystem.setWristAngle(_wristAngle);
    }

    @Override
    public boolean isFinished()
    {
        return _armSubsystem.wristAtAngle() || _armSubsystem.isWristOverridden();
    }
}
