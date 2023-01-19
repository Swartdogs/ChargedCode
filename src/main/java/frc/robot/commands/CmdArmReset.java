package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class CmdArmReset extends InstantCommand 
{
    private final Arm _armSubsystem;

    public CmdArmReset(Arm armSubystem) 
    {
        _armSubsystem = Arm.getInstance();
    }

    @Override
    public void execute() 
    {
        _armSubsystem.resetExtension();
    }

    @Override
    public boolean isFinished() 
    {
        return true;
    }
}
