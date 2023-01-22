package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.RobotLog;

public class CmdArmSetExtensionPosition extends InstantCommand 
{
    private final Arm    _armSubsystem;
    private final double _extensionPosition;

    public CmdArmSetExtensionPosition(double extensionPosition) 
    {
        _armSubsystem      = Arm.getInstance();
        _extensionPosition = extensionPosition;
    }   

    @Override
    public void initialize() 
    {
        RobotLog.getInstance().log("Setting arm extension position to " + _extensionPosition);
        _armSubsystem.setExtensionMotorPosition(_extensionPosition);
    }
}
