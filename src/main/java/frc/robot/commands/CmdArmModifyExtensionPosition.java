
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class CmdArmModifyExtensionPosition extends InstantCommand 
{
    private double _modification;
    
    public CmdArmModifyExtensionPosition(double modification)
    {
        _modification = modification;
    }

    @Override
    public void initialize() 
    {
        Arm.getInstance().modifyExtensionMotorPosition(_modification);
    }
}
