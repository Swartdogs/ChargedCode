package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;

public class CmdManipulatorIntakeGamePiece extends CommandBase 
{
    private final Manipulator _manipulatorSubsystem;

    public CmdManipulatorIntakeGamePiece() 
    {
        _manipulatorSubsystem = Manipulator.getInstance();
    }

    @Override
    public void initialize() 
    {
        RobotLog.getInstance().log("Intaking Game Piece");
        _manipulatorSubsystem.setIntakeSpeed(Constants.Manipulator.INTAKE_SPEED);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotLog.getInstance().log("Intook Game Piece");
        _manipulatorSubsystem.setIntakeSpeed(0);
    }

    @Override
    public boolean isFinished() 
    {
        return _manipulatorSubsystem.isIntakeSensorActive();
    }
}
