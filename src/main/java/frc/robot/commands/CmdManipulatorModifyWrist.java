
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

    public CmdManipulatorModifyWrist(double modification) 
    {
        _modification = () -> modification;
        _instant = true;
    }

    public CmdManipulatorModifyWrist() 
    {
        _modification = () -> RobotContainer.getInstance().getOperatorY() * Constants.Manipulator.WRIST_JOYSTICK_RATE;
        _instant = false;
    }

    @Override
    public void execute() 
    {
        Manipulator.getInstance().modifyWristAngle(_modification.getAsDouble() * Math.signum(Arm.getInstance().getShoulderAngleSetpoint()));
    }

    @Override
    public boolean isFinished()
    {
        return _instant;
    }

}
