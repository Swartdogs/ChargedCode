package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Manipulator;

public class CmdManipulatorSetGraspPosition extends InstantCommand 
{
    private final Manipulator _manipulatorSubsystem;
    private final double      _graspPosition;

    public CmdManipulatorSetGraspPosition(double graspPosition) 
    {
        _manipulatorSubsystem = Manipulator.getInstance();
        _graspPosition        = graspPosition;
    }

    @Override
    public void initialize() 
    {
        _manipulatorSubsystem.setGraspPosition(_graspPosition);
    }
}
