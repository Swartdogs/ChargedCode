package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Manipulator;
import frc.robot.commands.CmdManipulatorIntakeGamePiece;
import frc.robot.subsystems.Arm.ArmPosition;

public class GrpIntakeGamePiece extends ConditionalCommand 
{
    public GrpIntakeGamePiece(ArmPosition position) 
    {
        super
        (
            Commands.none(),
            new SequentialCommandGroup
            (
                new GrpSetArmPosition(position),
                new CmdManipulatorIntakeGamePiece(),
                new GrpSetArmPosition(ArmPosition.Stow)
            ),
            Manipulator.getInstance()::hasGamePiece
        );
    }
}
