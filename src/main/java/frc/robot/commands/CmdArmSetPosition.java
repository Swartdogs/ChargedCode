package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Vector;

public class CmdArmSetPosition extends CommandBase 
{
    private Vector _targetCoordinate;
    private double _targetHandAngle;
    private double _translateSpeed;
    private double _rotateSpeed;
    private Vector _startCoordinate;
    private double _startHandAngle;

    public CmdArmSetPosition(Vector coordinate, double handAngle, double translateSpeed, double rotateSpeed) 
    {
        _targetCoordinate = coordinate;
        _targetHandAngle  = handAngle;
        _translateSpeed   = translateSpeed;
        _rotateSpeed      = rotateSpeed;
    }

    @Override
    public void initialize() 
    {
        
    }

    @Override
    public void execute() 
    {

    }

    @Override
    public void end(boolean interrupted) 
    {

    }

    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
