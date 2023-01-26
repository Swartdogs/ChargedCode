package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;

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
        RobotLog.getInstance().log(String.format("Setting grasp to %6.2f", _graspPosition));
        _manipulatorSubsystem.setGraspPosition(_graspPosition);
    }
}
