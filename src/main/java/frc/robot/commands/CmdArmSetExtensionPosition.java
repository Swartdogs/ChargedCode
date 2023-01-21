package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

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
        _armSubsystem.setExtensionMotorPosition(_extensionPosition);
    }
}