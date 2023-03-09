package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ArmData;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.drive.Vector;

public class CmdArmSetPosition extends CommandBase 
{
    private Vector _targetCoordinate;
    private Vector _diff;
    private double _targetHandAngle;
    private double _initalHandAngle;
    private double _targetTwistAngle;
    private double _initalTwistAngle;
    private double _speed;
    private int    _step;
    private int    _steps;

    public CmdArmSetPosition(Vector coordinate, double handAngle, double twistAngle, double speed) 
    {
        _targetCoordinate = coordinate;
        _targetHandAngle  = handAngle;
        _targetTwistAngle = twistAngle;

        _speed   = speed;
    }

    public CmdArmSetPosition(ArmData data, double speed)
    {
        this(data.getCoordinate(), data.getHandAngle(), data.getTwistAngle(), speed);
    }

    @Override
    public void initialize() 
    {
        _diff = Arm.getInstance().getCoordinate().subtract(_targetCoordinate);
        _initalHandAngle = Arm.getInstance().getHandAngle();
        _initalTwistAngle = Arm.getInstance().getTwistTargetAngle();
        _steps = Math.max((int) (Constants.LOOPS_PER_SECOND * _diff.getMagnitude() / _speed), 1);
        _step = 0;

    }

    @Override
    public void execute() 
    {
        if (_step < _steps)
        {
            _step += 1;
        }

        double ratio = (double)_step / _steps;

        Vector armCoord = _targetCoordinate.add(_diff.multiply(1 - ratio));
        double handAngle = _initalHandAngle * (1 - ratio) + _targetHandAngle * ratio;
        double twistAngle = _initalTwistAngle * (1 - ratio) + _targetTwistAngle * ratio;

        Arm.getInstance().setArmPosition(armCoord, handAngle, twistAngle);
        
    }

    @Override
    public void end(boolean interrupted) 
    {

    }

    @Override
    public boolean isFinished() 
    {
        return _step >= _steps && Arm.getInstance().shoulderAtAngle() && Arm.getInstance().extensionAtDistance() && Arm.getInstance().twistAtAngle() && Arm.getInstance().wristAtAngle();
    }
}