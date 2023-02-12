package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ArmData;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmPosition;

public class GrpSetArmPosition extends SequentialCommandGroup 
{
    private ArmData _armData = new ArmData(0, 0, 0, 0);

    public GrpSetArmPosition(ArmPosition position) 
    {
        addCommands
        (
            Commands.run(()-> 
            {
                _armData = Constants.Lookups.lookUpArmData(position, RobotContainer.getInstance().getArmSide(), RobotContainer.getInstance().getHandMode());
                Arm.getInstance().setArmPosition(position);
                if(position == ArmPosition.Stow)
                {
                    Manipulator.getInstance().setIsFlipped(false);
                }
            }),  
            new ParallelCommandGroup
            (
                new ProxyCommand(()-> Arm.getInstance().setShoulderAngleCommand(_armData.getArmAngle())), 
                new ProxyCommand(()-> Arm.getInstance().setExtensionPositionCommand(_armData.getArmExtension())),
                new ProxyCommand(()-> Manipulator.getInstance().setTwistAngleCommand(_armData.getTwistAngle())),
                new ProxyCommand(()-> Manipulator.getInstance().setWristAngleCommand(_armData.getWristAngle())) 
            )
        );

        addRequirements(Arm.getInstance(), Manipulator.getInstance());
    }
}
