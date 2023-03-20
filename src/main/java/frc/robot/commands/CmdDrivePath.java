package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.autonomous.Trajectory;
import frc.robot.autonomous.TrajectoryFrame;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class CmdDrivePath extends DriveCommand
{
    private Timer _timer;

    private Trajectory _trajectory;

    private boolean _resetPosition;

    public CmdDrivePath(Trajectory path, boolean resetPosition)
    {
        _trajectory = path;
        _resetPosition = resetPosition;
        _timer = new Timer();
        
        addRequirements(Drive.getInstance());
    }

    public CmdDrivePath(Trajectory path)
    {
        this(path, false);
    }

    @Override
    public void initialize()
    {
        _timer.reset();
        _timer.start();

        if (_resetPosition)
        {
            _drive.setPosition(_trajectory.getFrame(0).getPosition());
            _drive.setGyro(_trajectory.getFrame(0).getHeading());
        }
        
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
