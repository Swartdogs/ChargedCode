package frc.robot.groups;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ArmData;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Manipulator;
import frc.robot.commands.CmdArmSetPosition;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;

public class GrpSetArmPosition extends SequentialCommandGroup 
{
    private ArmData _armData = Constants.Lookups.STOW_FRONT_CONE;

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
                Arm.getInstance().setTargetArmPreset(position);
            }),

            new ProxyCommand(()-> new CmdArmSetPosition(Constants.Lookups.STOW_FRONT_CUBE, Constants.Arm.PRESET_MOTION_RATE, true))
                // If the signs of where we are and where we need to go are different, we need to stow.
                // If the product of the signs is -1, we're crossing sides
                // If the product of the signs is  0, we're either stowing or are already stowed
                // If the product of the signs is  1, the arm is NOT changing which side it's on  
            .unless(() -> Math.signum(_armData.getCoordinate().getX()) * Math.signum(Arm.getInstance().getCoordinate().getX()) >= 0),

            new ProxyCommand(()-> new CmdArmSetPosition(_armData, Constants.Arm.PRESET_MOTION_RATE, true))
        );

        addRequirements(Arm.getInstance(), Manipulator.getInstance());
    }
}
