package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;

public class CmdManipulatorHandFlip extends CommandBase 
{
    @Override
    public void initialize()
    {
        Manipulator.getInstance().flipHand();
    }

    @Override
    public boolean isFinished() 
    {
        return Manipulator.getInstance().twistAtAngle();
    }
}
