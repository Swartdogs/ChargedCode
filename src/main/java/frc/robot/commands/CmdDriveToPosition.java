package frc.robot.commands;

import frc.robot.subsystems.RobotLog;
import frc.robot.subsystems.drive.Vector;

public class CmdDriveToPosition extends DriveCommand 
{
    private Vector _targetPosition;
    private double _targetHeading;
    private double _maxSpeed;

    public CmdDriveToPosition(Vector targetPosition, double targetHeading, double maxSpeed) 
    {
        _targetPosition = targetPosition;
        _targetHeading = targetHeading;
        _maxSpeed = maxSpeed;

        addRequirements(_drive);
    }

    @Override
    public void initialize() 
    {
        Vector fieldPosition = _drive.getFieldPosition();

        _xPID.setSetpoint(_targetPosition.getX(), fieldPosition.getX(), true);
        _yPID.setSetpoint(_targetPosition.getY(), fieldPosition.getY(), true);
        _rotatePID.setSetpoint(   _targetHeading,  _drive.getHeading(), true);

        _xPID.setOutputRange(-_maxSpeed, _maxSpeed);
        _yPID.setOutputRange(-_maxSpeed, _maxSpeed);

        RobotLog.getInstance().log(String.format("Drive to position: Current position and heading: %s, %6.2f ; Target position and heading: %s, %6.2f", _drive.getFieldPosition().toString(), _drive.getHeading(), _targetPosition.toString(), _targetHeading));
    }

    @Override
    public void execute() 
    {
        Vector fieldPosition = _drive.getFieldPosition();

        double outputX = _xPID.calculate(fieldPosition.getX());
        double outputY = _yPID.calculate(fieldPosition.getY());
        double outputR = _rotatePID.calculate(_drive.getHeading());
        
        _drive.fieldDrive(outputX, outputY, outputR);
    }

    @Override
    public void end(boolean interrupted) 
    {
        _drive.chassisDrive(0, 0, 0);

        RobotLog.getInstance().log(String.format("Drive to position: Current position and heading: %s, %6.2f ; Target position and heading: %s, %6.2f", _drive.getFieldPosition().toString(), _drive.getHeading(), _targetPosition.toString(), _targetHeading));
    }

    @Override
    public boolean isFinished()
    {
        return _xPID.atSetpoint() && _yPID.atSetpoint() && _rotatePID.atSetpoint();
    }
}
