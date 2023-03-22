package frc.robot.groups;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Manipulator;
import frc.robot.commands.CmdManipulatorIntakeGamePiece;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;
import frc.robot.RobotContainer;

public class GrpIntakeGamePiece extends ConditionalCommand 
{
    public GrpIntakeGamePiece(ArmPosition position)
    {
        this(position, ()-> RobotContainer.getInstance().getArmSide(), ()-> RobotContainer.getInstance().getHandMode());
    }

    public GrpIntakeGamePiece(ArmPosition position, Supplier<ArmSide> armSideSupplier, Supplier<HandMode> handModeSupplier) 
    {
        super
        (
            Commands.none(),
            new SequentialCommandGroup
            (
                new GrpSetArmPosition(position, armSideSupplier, handModeSupplier),
                new CmdManipulatorIntakeGamePiece(),
                Commands.waitSeconds(Manipulator.getInstance().getIntakeStowDelay()),
                new GrpSetArmPosition(ArmPosition.Stow)
            ),
            Manipulator.getInstance()::hasGamePiece
        );
    }
}
