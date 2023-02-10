package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CmdManipulatorIntakeGamePiece;
import frc.robot.subsystems.Arm.ArmPosition;

public class GrpIntakeGamePiece extends SequentialCommandGroup 
{
    public GrpIntakeGamePiece(ArmPosition position) 
    {
        addCommands
        (
            new GrpSetArmPosition(position),
            new CmdManipulatorIntakeGamePiece()
        );
    }
}
