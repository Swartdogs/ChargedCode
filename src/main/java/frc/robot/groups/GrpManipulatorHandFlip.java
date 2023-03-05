package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CmdManipulatorSetTwistAngle;
import frc.robot.subsystems.Manipulator;

public class GrpManipulatorHandFlip extends SequentialCommandGroup
{
    public GrpManipulatorHandFlip() 
    {
        super
        (
            new InstantCommand(()-> Manipulator.getInstance().setIsFlipped(!Manipulator.getInstance().isFlipped())),
            new ProxyCommand(()-> new CmdManipulatorSetTwistAngle(Manipulator.getInstance().getTwistTargetAngle()))
        );
    }
}
