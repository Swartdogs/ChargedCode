package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ArmData;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.CmdArmSetExtensionPosition;
import frc.robot.commands.CmdArmSetShoulderAngle;
import frc.robot.commands.CmdManipulatorSetTwistAngle;
import frc.robot.commands.CmdManipulatorSetWristAngle;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmPosition;

public class GrpSetArmPosition extends SequentialCommandGroup 
{
    private ArmData _armData = new ArmData(0, 0, 0, 0);

    public GrpSetArmPosition(ArmPosition position) 
    {
        addCommands
        (
            new InstantCommand(()-> _armData = Constants.Lookups.lookUpArmData(position, RobotContainer.getInstance().getArmSide(), RobotContainer.getInstance().getHandMode())), 
            new InstantCommand(()->Arm.getInstance().setArmPosition(position)), 
            new CmdArmSetExtensionPosition(0), 
            new ParallelCommandGroup
            (
                new SequentialCommandGroup
                (
                    new ProxyCommand(()-> new CmdArmSetShoulderAngle(_armData.getArmAngle())), 
                    new ProxyCommand(()-> new CmdArmSetExtensionPosition(_armData.getArmExtension()))
                ),
                new SequentialCommandGroup
                (
                    new ProxyCommand(()-> new CmdManipulatorSetTwistAngle(_armData.getTwistAngle())),
                    new ProxyCommand(()-> new CmdManipulatorSetWristAngle(_armData.getWristAngle()))
                )
            )
        );
    }
}
