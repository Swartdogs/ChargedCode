package frc.robot.commands;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RobotLog;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class CmdDriveToPosition extends CommandBase 
{
    private Drive      _drive = Drive.getInstance();

    private PIDControl _xPID;
    private PIDControl _yPID;
    private PIDControl _rotatePID;

    private Vector _targetPosition;
    private double _targetHeading;

    public CmdDriveToPosition(Vector targetPosition, double targetHeading) 
    {
        _targetPosition = targetPosition;
        _targetHeading = targetHeading;

        _xPID = new PIDControl();
        _yPID = new PIDControl();
        _rotatePID = new PIDControl();

        for (PIDControl pid : new PIDControl[] { _xPID, _yPID })
        {
            pid.setCoefficient(Coefficient.P, 0.0, 0.012, 0.0);// based on previous code
            pid.setCoefficient(Coefficient.I, 5.0, 0.0, 0.001);
            pid.setCoefficient(Coefficient.D, 0.0, 0.008, 0.0);
            pid.setInputRange(-720.0, 720.0);// assumed unit of inches
            pid.setOutputRange(-1.0, 1.0);
            pid.setOutputRamp(0.1, 0.05);
            pid.setSetpointDeadband(2.0);
        }

        _rotatePID.setCoefficient(Coefficient.P, 0.0, 0.008, 0.0);
        _rotatePID.setCoefficient(Coefficient.I, 20.0, 0.0, 0.00027);
        _rotatePID.setCoefficient(Coefficient.D, 0.0, 0.001, 0.0);
        _rotatePID.setInputRange(-180.0, 180.0);
        _rotatePID.setContinuous(true);
        _rotatePID.setOutputRange(-1.0, 1.0);
        _rotatePID.setOutputRamp(0.1, 0.05);
        _rotatePID.setSetpointDeadband(2.0);

        addRequirements(_drive);
    }

    @Override
    public void initialize() 
    {
        Vector fieldPosition = _drive.getFieldPosition();

        _xPID.setSetpoint(_targetPosition.getX(), fieldPosition.getX(), true);
        _yPID.setSetpoint(_targetPosition.getY(), fieldPosition.getY(), true);
        _rotatePID.setSetpoint(   _targetHeading,  _drive.getHeading(), true);

        RobotLog.getInstance().log("Driving to " + _targetPosition + " with heading " + _targetHeading);
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

        RobotLog.getInstance().log("Drove to " + _targetPosition + " with heading " + _targetHeading);
    }

    @Override
    public boolean isFinished()
    {
        return _xPID.atSetpoint() && _yPID.atSetpoint() && _rotatePID.atSetpoint();
    }
}
