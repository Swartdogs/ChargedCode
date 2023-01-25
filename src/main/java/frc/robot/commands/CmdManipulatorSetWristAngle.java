package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Manipulator;

public class CmdManipulatorSetWristAngle extends InstantCommand 
{
    private final Manipulator _manipulatorSubsystem;
    private final double      _wristAngle;

    public CmdManipulatorSetWristAngle(double wristAngle) 
    {
        _manipulatorSubsystem = Manipulator.getInstance();
        _wristAngle           = wristAngle;
    }

    @Override
    public void initialize() 
    {
        _manipulatorSubsystem.setWristAngle(_wristAngle);
    }
}
