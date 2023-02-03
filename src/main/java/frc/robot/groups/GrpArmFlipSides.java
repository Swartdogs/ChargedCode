// Copyright (c) FIRST and other WPILib contributors.
package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;

public class GrpArmFlipSides extends SequentialCommandGroup 
{

    private GrpSetArmPosition _highCommand = new GrpSetArmPosition(ArmPosition.High);
    private GrpSetArmPosition _middleCommand = new GrpSetArmPosition(ArmPosition.Middle);
    private GrpSetArmPosition _lowCommand = new GrpSetArmPosition(ArmPosition.Low);

    public GrpArmFlipSides() 
    {

        super
        (
            new ConditionalCommand(null, null, null)
            
        );
    }
}
