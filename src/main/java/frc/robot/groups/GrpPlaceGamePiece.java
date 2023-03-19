package frc.robot.groups;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.CmdArmSetPosition;
import frc.robot.commands.CmdManipulatorPlaceGamePiece;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Arm.HandMode;
import frc.robot.subsystems.drive.Vector;

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
                    new CmdArmSetPosition(new Vector(0, -6), 3.0, 0.0, Constants.Arm.PLACE_MOTION_RATE, false),

                    // Retract the arm, but also run the intake in reverse as long as we're retracting
                    new ParallelDeadlineGroup
                    (
                        // Retract the arm and wait for it to be retracted
                        new CmdArmSetPosition(new Vector(-10, 0), 0.0, 0.0, Constants.Arm.PLACE_MOTION_RATE, false),

                        // Start the intake when retraction starts, disable the intake when retraction ends
                        Commands.startEnd(Manipulator.getInstance()::setIntakeToConeEjectSpeed, Manipulator.getInstance()::disableIntake)
                    )
                ),

                // The supplied hand mode determines which sequence we use
                () -> handModeSupplier.get() == HandMode.Cube || Arm.getInstance().getTargetArmPreset() == ArmPosition.Low
            ),
            new GrpSetArmPosition(ArmPosition.Stow)
        );
    }
}
