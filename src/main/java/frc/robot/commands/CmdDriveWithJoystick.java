package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveWithJoystick extends CommandBase 
{
    DoubleSupplier _xInput;// joystick axis for x/strafe
    DoubleSupplier _yInput;// joystick axis for y/drive
    DoubleSupplier _rInput;// joystick axis for rotation
    BooleanSupplier _robotCentricInput;// button for field/robot centered driving

    public CmdDriveWithJoystick(DoubleSupplier xInput, DoubleSupplier yInput, DoubleSupplier rInput, BooleanSupplier robotCentricInput)
    {
        _xInput            = xInput;
        _yInput            = yInput;
        _rInput            = rInput;
        _robotCentricInput = robotCentricInput;

        addRequirements(Drive.getInstance());
    }

    @Override
    public void execute()
    {
        // get our inputs from the joystick, and convert them to velocities
        double x = _xInput.getAsDouble() * Constants.Drive.MAX_DRIVE_SPEED;
        double y = _yInput.getAsDouble() * Constants.Drive.MAX_DRIVE_SPEED;
        double r = _rInput.getAsDouble() * Constants.Drive.MAX_ROTATE_SPEED;

        boolean robotCentric = _robotCentricInput.getAsBoolean();

        // actually drive the robot
        Drive drive = Drive.getInstance();

        if (robotCentric)
        {
            drive.chassisDrive(y, x, r);
        }
        else
        {
            drive.fieldDrive(x, y, r);
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        Drive.getInstance().chassisDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
