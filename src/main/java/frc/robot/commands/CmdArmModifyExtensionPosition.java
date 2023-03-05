
package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class CmdArmModifyExtensionPosition extends CommandBase 
{
    private DoubleSupplier _modification;
    private boolean        _instant;
    
    public CmdArmModifyExtensionPosition(double modification)
    {
        _modification = () -> modification;
        _instant = true;
    }

    public CmdArmModifyExtensionPosition()
    {
        _modification = () -> RobotContainer.getInstance().getOperatorY() * Constants.Arm.EXTENSION_JOYSTICK_RATE;
        _instant = false;
    }

    @Override
    public void execute() 
    {
        Arm.getInstance().modifyExtensionMotorPosition(_modification.getAsDouble());
    }

    @Override
    public boolean isFinished()
    {
        return _instant;
    }
}
