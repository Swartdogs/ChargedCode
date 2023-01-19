package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class CmdArmSetShoulderAngle extends InstantCommand
{
    private final Arm            _armSubsystem;
    private final DoubleSupplier _shoulderAngle;

    public CmdArmSetShoulderAngle(Arm armSubsystem, DoubleSupplier shoulderAngle) 
    {
        _armSubsystem  = Arm.getInstance();
        _shoulderAngle = shoulderAngle;
    }

    @Override
    public void execute() 
    {
        _armSubsystem.setShoulderAngle(_shoulderAngle.getAsDouble());
    }

    @Override
    public boolean isFinished() 
    {
        return true;
    }
}
