package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;

public class CmdManipulatorIntakeGamePiece extends CommandBase 
{
    private final Manipulator _manipulatorSubsystem;
    private int _timer;
    private boolean _timerActive;

    public CmdManipulatorIntakeGamePiece() 
    {
        _manipulatorSubsystem = Manipulator.getInstance();
        _timer = (int)(_manipulatorSubsystem.getIntakeStopDelay() * Constants.LOOPS_PER_SECOND);
        _timerActive = false;
    }

    @Override
    public void initialize() 
    {
        RobotLog.getInstance().log("Intaking Game Piece");
        _manipulatorSubsystem.enableIntake();
        _timer = (int)(_manipulatorSubsystem.getIntakeStopDelay() * Constants.LOOPS_PER_SECOND);
        _timerActive = false;
    }

    @Override
    public void execute()
    {
        if (_manipulatorSubsystem.hasGamePiece())
        {
            _timerActive = true;
        }

        if (_timerActive)
        {
            _timer--;
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotLog.getInstance().log("Intook Game Piece");
        _manipulatorSubsystem.disableIntake();
    }

    @Override
    public boolean isFinished() 
    {
        return _timer <= 0 && _timerActive;
    }
}
