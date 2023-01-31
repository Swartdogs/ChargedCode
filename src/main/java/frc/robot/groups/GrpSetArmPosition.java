package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CmdArmSetExtensionPosition;
import frc.robot.commands.CmdArmSetShoulderAngle;
import frc.robot.subsystems.drive.Vector;

public class GrpSetArmPosition extends SequentialCommandGroup 
{

    public GrpSetArmPosition(Vector position) 
    {
        super
        (
            new CmdArmSetExtensionPosition(0), 
            new CmdArmSetShoulderAngle(position.getHeading()), 
            new CmdArmSetExtensionPosition(position.getMagnitude())
        );
    }
}
