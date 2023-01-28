package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveWithJoystick extends CommandBase
{
    private Drive _drive = Drive.getInstance(); 

    private DoubleSupplier  _xInput;           // joystick axis for x/strafe
    private DoubleSupplier  _yInput;           // joystick axis for y/drive
    private DoubleSupplier  _rInput;           // joystick axis for rotation
    private BooleanSupplier _robotCentricInput;// button for field/robot centered driving

    public CmdDriveWithJoystick(DoubleSupplier xInput, DoubleSupplier yInput, DoubleSupplier rInput, BooleanSupplier robotCentricInput)
    {
        _xInput            = xInput;
        _yInput            = yInput;
        _rInput            = rInput;
        _robotCentricInput = robotCentricInput;

        addRequirements(_drive);
    }

    @Override
    public void execute()
    {
        // get our inputs from the joystick
        double x = _xInput.getAsDouble();
        double y = _yInput.getAsDouble();
        double r = _rInput.getAsDouble();

        boolean robotCentric = _robotCentricInput.getAsBoolean();
        
        // apply deadbands
        r = r * Math.abs(r);// square rotation input

        x = applyDeadband(x, 0.05);
        y = applyDeadband(y, 0.05);
        r = applyDeadband(r, 0.10);

        // actually drive the robot
        if (robotCentric)
        {
            _drive.chassisDrive(y, x, r);
        }
        else
        {
            _drive.fieldDrive(x, y, r);
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        _drive.chassisDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() 
    {
        return false;
    }

    private double applyDeadband(double input, double deadband)
    {
        double output = 0;

        if (Math.abs(input) > deadband)
        {
            output = (input - (deadband * Math.signum(input))) / (1.0 - deadband);
        }

        return output;
    }
}
