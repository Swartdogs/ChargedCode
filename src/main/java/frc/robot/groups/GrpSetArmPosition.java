package frc.robot.groups;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ArmData;
import frc.robot.Constants;
import frc.robot.commands.CmdArmSetExtensionPosition;
import frc.robot.commands.CmdArmSetShoulderAngle;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Manipulator.HandMode;

public class GrpSetArmPosition extends SequentialCommandGroup 
{
    private ArmData _armData = new ArmData(0, 0, 0, 0);

    public GrpSetArmPosition(ArmPosition position, Supplier<ArmSide> sideFunc, Supplier<HandMode> handFunc) 
    {
        addCommands
        (
            new InstantCommand(()-> _armData = Constants.Lookups.lookUpArmData(position, sideFunc.get(), handFunc.get())), 
            new InstantCommand(()->Arm.getInstance().setArmPosition(position)), 
            new CmdArmSetExtensionPosition(0), 
            new ProxyCommand(()-> new CmdArmSetShoulderAngle(_armData.getArmAngle())), 
            new ProxyCommand(()-> new CmdArmSetExtensionPosition(_armData.getArmExtension()))
        );
    }
}
