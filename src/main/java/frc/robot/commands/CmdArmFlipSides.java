package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ProxyCommand;
import frc.robot.groups.GrpSetArmPosition;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Manipulator.HandMode;

public class CmdArmFlipSides extends ProxyCommand 
{
    public CmdArmFlipSides(Supplier<ArmSide> sideFunc, Supplier<HandMode> handFunc) 
    {
        super(()-> new GrpSetArmPosition(Arm.getInstance().getArmPosition(), sideFunc, handFunc));
    }
}
