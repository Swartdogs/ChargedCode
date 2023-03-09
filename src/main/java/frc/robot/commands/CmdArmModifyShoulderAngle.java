
package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class CmdArmModifyShoulderAngle extends CommandBase 
{
    private DoubleSupplier _modification;
    private boolean        _instant;

    public CmdArmModifyShoulderAngle(double modification) 
    {
        _modification = () -> modification;
        _instant = true;
    }

    public CmdArmModifyShoulderAngle() 
    {
        _modification = () -> RobotContainer.getInstance().getOperatorY() * Constants.Arm.SHOULDER_JOYSTICK_RATE;
        _instant = false;
    }

    @Override
    public void execute() 
    {
        Arm.getInstance().modifyShoulderAngle(_modification.getAsDouble() * Math.signum(Arm.getInstance().getShoulderTargetAngle()));
    }

    @Override
    public boolean isFinished()
    {
        return _instant;
    }
}
