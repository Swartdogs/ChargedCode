
package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class CmdArmModifyWrist extends CommandBase 
{
    private DoubleSupplier _modification;
    private boolean        _instant;
    private boolean        _overridden;

    public CmdArmModifyWrist(double modification) 
    {
        _modification = () -> modification;
        _instant = true;
        _overridden = false;
    }

    public CmdArmModifyWrist() 
    {
        _modification = () -> -RobotContainer.getInstance().getOperatorY() * Constants.Arm.WRIST_JOYSTICK_RATE * Math.signum(Arm.getInstance().getShoulderAngleSetpoint());
        _instant = false;
        _overridden = false;
    }

    @Override
    public void execute() 
    {
        if (Arm.getInstance().isWristOverridden())
        {
            if (!_instant && !_overridden)
            {
                _modification = () -> 
                {
                    var sign = Math.signum(Arm.getInstance().getShoulderAngleSetpoint());

                    if (sign == 0)
                    {
                        sign = 1;
                    }

                    return -RobotContainer.getInstance().getOperatorY() * sign;
                };

                _overridden = true;
            }

            Arm.getInstance().setWristSpeed(_modification.getAsDouble());
        }
        else
        {
            Arm.getInstance().modifyWristAngle(_modification.getAsDouble());
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        if (Arm.getInstance().isWristOverridden())
        {
            Arm.getInstance().setWristSpeed(0);
        }
    }

    @Override
    public boolean isFinished()
    {
        return _instant;
    }
}
