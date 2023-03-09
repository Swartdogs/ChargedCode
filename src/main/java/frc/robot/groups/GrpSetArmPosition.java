package frc.robot.groups;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ArmData;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Manipulator;
import frc.robot.commands.CmdArmSetExtensionPosition;
import frc.robot.commands.CmdArmSetShoulderAngle;
import frc.robot.commands.CmdManipulatorSetTwistAngle;
import frc.robot.commands.CmdManipulatorSetWristAngle;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;

public class GrpSetArmPosition extends SequentialCommandGroup 
{
    private ArmData _armData = new ArmData(0, 0, 0, 0);

    public GrpSetArmPosition(ArmPosition position)
    {
        // Pure lambda notation must be used (() -> ...) instead of method reference (object::methodName)
        // This class is usually constructed during code startup when RobotContainer is being built. Method reference
        // notation will cause a stack overflow exception
        this(position, () -> RobotContainer.getInstance().getArmSide(), () -> RobotContainer.getInstance().getHandMode());
    }

    public GrpSetArmPosition(ArmPosition position, Supplier<ArmSide> armSideSupplier, Supplier<HandMode> handModeSupplier) 
    {
        addCommands
        (
            new InstantCommand(()-> 
            {
                _armData = Constants.Lookups.lookUpArmData(position, armSideSupplier.get(), handModeSupplier.get());
                Arm.getInstance().setArmPosition(position);
                if(position == ArmPosition.Stow)
                {
                    Arm.getInstance().setIsFlipped(false);
                }
            }),

            new ParallelCommandGroup
            (
                new ProxyCommand(()-> new CmdArmSetShoulderAngle(Constants.Lookups.STOW_FRONT_CUBE.getArmAngle())), 
                new ProxyCommand(()-> new CmdArmSetExtensionPosition(Constants.Lookups.STOW_FRONT_CUBE.getArmExtension())),
                new ProxyCommand(()-> new CmdManipulatorSetTwistAngle(Constants.Lookups.STOW_FRONT_CUBE.getTwistAngle())),
                new ProxyCommand(()-> new CmdManipulatorSetWristAngle(Constants.Lookups.STOW_FRONT_CUBE.getWristAngle()))

                // If the signs of where we are and where we need to go are different, we need to stow.
                // If the product of the signs is -1, we're crossing sides
                // If the product of the signs is  0, we're either stowing or are already stowed
                // If the product of the signs is  1, the arm is NOT changing which side it's on  
            ).unless(() -> Math.signum(_armData.getArmAngle()) * Math.signum(Arm.getInstance().getShoulderTargetAngle()) >= 0),

            new ParallelCommandGroup
            (
                new ProxyCommand(()-> new CmdArmSetShoulderAngle(_armData.getArmAngle())), 
                new ProxyCommand(()-> new CmdArmSetExtensionPosition(_armData.getArmExtension())),
                new ProxyCommand(()-> new CmdManipulatorSetTwistAngle(_armData.getTwistAngle())),
                new ProxyCommand(()-> new CmdManipulatorSetWristAngle(_armData.getWristAngle())) 
            )
        );

        addRequirements(Arm.getInstance(), Manipulator.getInstance());
    }
}
