package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CmdManipulatorPlaceGamePiece;
import frc.robot.subsystems.Arm.ArmPosition;

public class GrpPlaceGamePiece extends SequentialCommandGroup
{

    public GrpPlaceGamePiece()
    {
        addCommands
        (
            new CmdManipulatorPlaceGamePiece(),
            new GrpSetArmPosition(ArmPosition.Stow)
        );
    }
}
