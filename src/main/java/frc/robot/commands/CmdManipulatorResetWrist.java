package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;

public class CmdManipulatorResetWrist extends CommandBase {
    
    private final Manipulator _manipulatorSubsystem;

    public CmdManipulatorResetWrist() 
    {
        _manipulatorSubsystem = Manipulator.getInstance();
    }

    @Override
    public void initialize() 
    {
        RobotLog.getInstance().log("Initializing Manipulator Wrist Reset");
    }

    @Override
    public void execute() 
    {
        _manipulatorSubsystem.setWristSpeed(0.25);
    }

    @Override
    public void end(boolean interrupted) 
    {
        RobotLog.getInstance().log("Manipulator Wrist Reset");
        _manipulatorSubsystem.setWristSpeed(0);
        _manipulatorSubsystem.setWristResetAngle(Constants.Manipulator.WRIST_MAX_ANGLE);
    }

    @Override
    public boolean isFinished() 
    {
        return _manipulatorSubsystem.isUpperWristSwitchActive();
    }
}
