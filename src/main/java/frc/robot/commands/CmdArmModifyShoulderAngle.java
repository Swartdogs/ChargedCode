
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class CmdArmModifyShoulderAngle extends InstantCommand 
{
    private double _modification;

    public CmdArmModifyShoulderAngle(double modification) 
    {
        _modification = modification;
    }

    @Override
    public void initialize() 
    {
        Arm.getInstance().modifyShoulderAngle(_modification);
    }
}
