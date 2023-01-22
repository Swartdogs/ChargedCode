package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.RobotLog;

public class CmdArmSetShoulderAngle extends InstantCommand
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
}
