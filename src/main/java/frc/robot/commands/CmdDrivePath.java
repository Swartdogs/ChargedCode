package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.paths.Trajectory;
import frc.robot.paths.TrajectoryFrame;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class CmdDrivePath extends DriveCommand
{
    Timer _timer;

    Trajectory _trajectory;

    public CmdDrivePath(Trajectory path)
    {
        _trajectory = path;

        _timer = new Timer();
        
        addRequirements(Drive.getInstance());
    }

    @Override
    public void initialize()
    {
        _timer.reset();
        _timer.start();
    }

    @Override
    public void execute()
    {
        // update the time 
        double time = _timer.get();
        
        // get the current target speed and position
        TrajectoryFrame frame = _trajectory.getFrame(time);

        _xPID.setSetpoint(frame.getPosition().getX());
        _yPID.setSetpoint(frame.getPosition().getY());
        _rotatePID.setSetpoint(frame.getHeading());

        _xVelocityPID.setSetpoint(frame.getVelocity().getX());
        _yVelocityPID.setSetpoint(frame.getVelocity().getY());
        _rotateVelocityPID.setSetpoint(frame.getAngularVelocity());

        Vector odometer = Drive.getInstance().getFieldPosition();
        Vector driveVelocity = Drive.getInstance().getFieldVelocity();

        double x = _xPID.calculate(odometer.getX()) + _xVelocityPID.calculate(driveVelocity.getX());
        double y = _yPID.calculate(odometer.getY()) + _yVelocityPID.calculate(driveVelocity.getY());
        double rotate = _rotatePID.calculate(Drive.getInstance().getHeading()) + _rotateVelocityPID.calculate(Drive.getInstance().getHeadingVelocity());

        Drive.getInstance().fieldDrive(x, y, rotate);
    }

    @Override
    public void end(boolean interrupted)
    {
        Drive.getInstance().chassisDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished()
    {
        return _trajectory.isFinished(_timer.get());
    }
}
