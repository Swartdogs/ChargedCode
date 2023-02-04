package frc.robot.commands;

import frc.robot.subsystems.drive.Vector;

public class CmdDriveToPosition extends DriveCommand 
{
    private Vector _targetPosition;
    private double _targetHeading;

    public CmdDriveToPosition(Vector targetPosition, double targetHeading) 
    {
        _targetPosition = targetPosition;
        _targetHeading = targetHeading;

        addRequirements(_drive);
    }

    @Override
    public void initialize() 
    {
        Vector fieldPosition = _drive.getFieldPosition();

        _xPID.setSetpoint(_targetPosition.getX(), fieldPosition.getX(), true);
        _yPID.setSetpoint(_targetPosition.getY(), fieldPosition.getY(), true);
        _rotatePID.setSetpoint(   _targetHeading,  _drive.getHeading(), true);
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
    }

    @Override
    public boolean isFinished()
    {
        return _xPID.atSetpoint() && _yPID.atSetpoint() && _rotatePID.atSetpoint();
    }
}
