package frc.robot.groups;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.CmdArmModifyExtensionPosition;
import frc.robot.commands.CmdArmModifyShoulderAngle;
import frc.robot.commands.CmdManipulatorPlaceGamePiece;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Manipulator.HandMode;

public class GrpPlaceGamePiece extends SequentialCommandGroup
{
    public GrpPlaceGamePiece()
    {
        this(() -> RobotContainer.getInstance().getHandMode());
    }

    public GrpPlaceGamePiece(Supplier<HandMode> handModeSupplier)
    {
        addCommands
        (
            // The sequence is different for cones and cubes
            Commands.either
            (
                // For cubes, run the intake in reverse and be done
                new CmdManipulatorPlaceGamePiece(),

                // For cones, run the following sequence
                new SequentialCommandGroup
                (
                    // Modify the shoulder downward and wait for it to be in position
                    new CmdArmModifyShoulderAngle(5),
                    Commands.waitSeconds(1),

                    // Retract the arm, but also run the intake in reverse as long as we're retracting
                    new ParallelDeadlineGroup
                    (
                        // Retract the arm and wait for it to be retracted
                        new SequentialCommandGroup
                        (
                            new CmdArmModifyExtensionPosition(-6),
                            Commands.waitSeconds(1)
                        ),

                        // Start the intake when retraction starts, disable the intake when retraction ends
                        Commands.startEnd(Manipulator.getInstance()::setIntakeToConeEjectSpeed, Manipulator.getInstance()::disableIntake)
                    )
                ),

                // The supplied hand mode determines which sequence we use
                () -> handModeSupplier.get() == HandMode.Cube
            ),
            new GrpSetArmPosition(ArmPosition.Stow)
        );
    }
}
