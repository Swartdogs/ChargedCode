package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;

public class CmdManipulatorSetIntakeSpeed extends CommandBase 
{
    private final Manipulator _manipulatorSubsystem;
    private final double      _intakeSpeed;

    public CmdManipulatorSetIntakeSpeed(double intakeSpeed) 
    {
        _manipulatorSubsystem = Manipulator.getInstance();
        _intakeSpeed          = intakeSpeed;
    }

    @Override
    public void initialize() 
    {
        _manipulatorSubsystem.setIntakeSpeed(_intakeSpeed);
    }

    @Override
    public void end(boolean interrupted) 
    {
        if(_manipulatorSubsystem.isIntakeSensorActive())
        {
            _manipulatorSubsystem.setIntakeSpeed(0);
        }
    }
}
