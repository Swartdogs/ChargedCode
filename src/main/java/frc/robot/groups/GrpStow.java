package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Arm.ArmPosition;

public class GrpStow extends SequentialCommandGroup 
{
    public GrpStow() 
    {
        addCommands
        (
            new GrpSetArmPosition(ArmPosition.Stow),
            new InstantCommand(() -> Manipulator.getInstance().disableIntake())
        );
    }
}
