package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CmdArmSetExtensionPosition;
import frc.robot.commands.CmdArmSetShoulderAngle;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmPosition;

public class GrpSetArmPosition extends SequentialCommandGroup 
{

    public GrpSetArmPosition(ArmPosition position) 
    {
        super
        (
            new CmdArmSetExtensionPosition(0), 
            new CmdArmSetShoulderAngle(position.getAngle()), 
            new CmdArmSetExtensionPosition(position.getDistance()),
            new InstantCommand(()->Arm.getInstance().setArmPosition(position)) 
        );
    }
}
