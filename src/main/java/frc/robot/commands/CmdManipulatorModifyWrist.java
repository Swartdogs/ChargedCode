
package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Manipulator;

public class CmdManipulatorModifyWrist extends CommandBase 
{
    private DoubleSupplier _modification;
    private boolean        _instant;
    private boolean        _overridden;

    public CmdManipulatorModifyWrist(double modification) 
    {
        _modification = () -> modification;
        _instant = true;
        _overridden = false;
    }

    public CmdManipulatorModifyWrist() 
    {
        _modification = () -> -RobotContainer.getInstance().getOperatorY() * Constants.Manipulator.WRIST_JOYSTICK_RATE * Math.signum(Arm.getInstance().getShoulderAngleSetpoint());
        _instant = false;
        _overridden = false;
    }

    @Override
    public void execute() 
    {
        if (Manipulator.getInstance().isWristOverridden())
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

            Manipulator.getInstance().setWristSpeed(_modification.getAsDouble());
        }
        else
        {
            Manipulator.getInstance().modifyWristAngle(_modification.getAsDouble());
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        if (Manipulator.getInstance().isWristOverridden())
        {
            Manipulator.getInstance().setWristSpeed(0);
        }
    }

    @Override
    public boolean isFinished()
    {
        return _instant;
    }
}
