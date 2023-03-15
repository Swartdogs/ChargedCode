package frc.robot.commands;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.Drive;

public abstract class DriveCommand extends CommandBase 
{
    protected Drive _drive = Drive.getInstance();

    protected PIDControl _xPID;
    protected PIDControl _yPID;
    protected PIDControl _rotatePID;

    protected PIDControl _xVelocityPID;
    protected PIDControl _yVelocityPID;
    protected PIDControl _rotateVelocityPID;

    public DriveCommand() 
    {
        _xPID = new PIDControl();
        _yPID = new PIDControl();
        _rotatePID = new PIDControl();

        for (PIDControl pid : new PIDControl[] { _xPID, _yPID })
        {
            pid.setCoefficient(Coefficient.P, 0.0, 0.012, 0.0); // based on previous code
            pid.setCoefficient(Coefficient.I, 5.0, 0.0, 0.05);
            pid.setCoefficient(Coefficient.D, 0.0, 0.00016, 0.0);
            pid.setInputRange(-720.0, 720.0); // assumed unit of inches
            pid.setOutputRange(-1.0, 1.0);
            pid.setOutputRamp(0.1, 0.05);
            pid.setSetpointDeadband(2.0);
        }

        _rotatePID.setCoefficient(Coefficient.P, 0.0, 0.008, 0.0);
        _rotatePID.setCoefficient(Coefficient.I, 20.0, 0.0, 0.0135);
        _rotatePID.setCoefficient(Coefficient.D, 0.0, 0.00002, 0.0);
        _rotatePID.setInputRange(-180.0, 180.0);
        _rotatePID.setContinuous(true);
        _rotatePID.setOutputRange(-1.0, 1.0);
        _rotatePID.setOutputRamp(0.1, 0.05);
        _rotatePID.setSetpointDeadband(2.0);

        // velocity control
        _xVelocityPID = new PIDControl();
        _yVelocityPID = new PIDControl();
        _rotateVelocityPID = new PIDControl();

        for (PIDControl pid : new PIDControl[] { _xVelocityPID, _yVelocityPID })
        {
            pid.setFeedForward((setpoint) -> setpoint / Constants.Drive.MAX_DRIVE_SPEED);
            pid.setCoefficient(Coefficient.P, 1 / Constants.Drive.MAX_DRIVE_SPEED); // FIXME: needs tuning
            pid.setCoefficient(Coefficient.I, 0.0, 0.00, 0.0);
            pid.setCoefficient(Coefficient.D, 0.0, 0.00, 0.0);
            pid.setInputRange(-Constants.Drive.MAX_DRIVE_SPEED, Constants.Drive.MAX_DRIVE_SPEED);
            pid.setOutputRange(-1.0, 1.0);
            pid.setOutputRamp(0.1, 0.05);
            pid.setSetpointDeadband(2.0); // keep within 2 in/s
        }

        _rotateVelocityPID.setFeedForward((setpoint) -> setpoint / Constants.Drive.MAX_ROTATE_SPEED);
        _rotateVelocityPID.setCoefficient(Coefficient.P, 1 / Constants.Drive.MAX_ROTATE_SPEED); // FIXME: needs tuning
        _rotateVelocityPID.setCoefficient(Coefficient.I, 0.0, 0.0, 0.0);
        _rotateVelocityPID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);
        _rotateVelocityPID.setInputRange(-Constants.Drive.MAX_ROTATE_SPEED, Constants.Drive.MAX_ROTATE_SPEED);
        _rotateVelocityPID.setOutputRange(-1.0, 1.0);
        _rotateVelocityPID.setOutputRamp(0.1, 0.05);
        _rotateVelocityPID.setSetpointDeadband(2.0); // keep within 2 deg/s

        addRequirements(_drive);
    }

    @Override
    public void end(boolean interrupted)
    {
        _drive.chassisDrive(0, 0, 0);
    }
}
