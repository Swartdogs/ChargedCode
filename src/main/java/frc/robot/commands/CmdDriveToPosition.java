package frc.robot.commands;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class CmdDriveToPosition extends CommandBase 
{
    private PIDControl _xPID;
    private PIDControl _yPID;
    private PIDControl _rotatePID;

    private Vector _targetPosition;
    private double _targetHeading;

    public CmdDriveToPosition(Vector targetPosition, double targetHeading) 
    {
        _xPID.setCoefficient(Coefficient.P, 0.0, 0.015, 0.0);// based on previous code
        _xPID.setCoefficient(Coefficient.I, 5.0, 0.0, 0.001);
        _xPID.setCoefficient(Coefficient.D, 0.0, 0, 0.0);
        _xPID.setInputRange(-720.0, 720.0);// assumed unit of inches
        _xPID.setOutputRange(-1.0, 1.0);
        _xPID.setOutputRamp(0.1, 0.05);
        _xPID.setSetpointDeadband(2.0);

        _yPID.setCoefficient(Coefficient.P, 0.0, 0.015, 0.0);// these should all be the same as xPID
        _yPID.setCoefficient(Coefficient.I, 5.0, 0.0, 0.001);
        _yPID.setCoefficient(Coefficient.D, 0.0, 0, 0.0);
        _yPID.setInputRange(-720.0, 720.0);// assumed unit of inches
        _yPID.setOutputRange(-1.0, 1.0);
        _yPID.setOutputRamp(0.1, 0.05);
        _yPID.setSetpointDeadband(2.0);

        _rotatePID.setCoefficient(Coefficient.P, 0.005, 0.0, 0.0);
        _rotatePID.setCoefficient(Coefficient.I, 20.0, 0.0, 0.00027);
        _rotatePID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);
        _rotatePID.setInputRange(-180.0, 180.0);
        _rotatePID.setContinuous(true);
        _rotatePID.setOutputRamp(0.1, 0.05);
        _rotatePID.setSetpointDeadband(2.0);
    }

    @Override
    public void initialize() 
    {        
        _xPID.setSetpoint(_targetPosition.getX(), Drive.getInstance().getFieldPosition().getX(), true);
        _yPID.setSetpoint(_targetPosition.getY(), Drive.getInstance().getFieldPosition().getY(), true);
        _rotatePID.setSetpoint(_targetHeading, Drive.getInstance().getHeading(), true);
    }

    @Override
    public void execute() 
    {
        Vector fieldPosition = Drive.getInstance().getFieldPosition();
        double outputX = _xPID.calculate(fieldPosition.getX());
        double outputY = _yPID.calculate(fieldPosition.getY());
        double outputR = _rotatePID.calculate(Drive.getInstance().getHeading());

        Drive.getInstance().fieldDrive(outputX, outputY, outputR);

    }

    @Override
    public void end(boolean interrupted) 
    {
        Drive.getInstance().chassisDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished()
    {
        return _xPID.atSetpoint() && _yPID.atSetpoint() && _rotatePID.atSetpoint();
    }
}