package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Manipulator;

public class CmdManipulatorPlaceGamePiece extends CommandBase 
{
    private final Manipulator _manipulatorSubsystem;
    private double            _ejectTimer;
    
    public CmdManipulatorPlaceGamePiece() 
    {
        _manipulatorSubsystem = Manipulator.getInstance();
    }

    @Override
    public void initialize() 
    {
        _manipulatorSubsystem.setIntakeSpeed(-Constants.Manipulator.INTAKE_SPEED);
        _ejectTimer = (int)(Constants.Manipulator.EJECT_TIME * Constants.LOOPS_PER_SECOND);
    }

    @Override
    public void execute() 
    {
        _ejectTimer--;
    }

    @Override
    public void end(boolean interrupted) 
    {
        _manipulatorSubsystem.setIntakeSpeed(0);
    }

    @Override
    public boolean isFinished() 
    {
        return _ejectTimer <= 0;
    }
}
