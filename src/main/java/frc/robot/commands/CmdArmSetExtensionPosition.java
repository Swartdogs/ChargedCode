package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class CmdArmSetExtensionPosition extends InstantCommand 
{
    private final Arm            _armSubsystem;
    private final DoubleSupplier _extensionPosition;

    public CmdArmSetExtensionPosition(Arm armSubsystem, DoubleSupplier extensionPosition) 
    {
        _armSubsystem      = Arm.getInstance();
        _extensionPosition = extensionPosition;
    }   

    @Override
    public void execute() 
    {
        _armSubsystem.setExtensionMotorPosition(_extensionPosition.getAsDouble());
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
