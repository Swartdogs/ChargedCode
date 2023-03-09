package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.drive.Vector;

public class CmdArmModifyPosition extends CommandBase 
{
    private Vector _modifyCoordinate;
    private double _modifyHandAngle;
    private double _modifyTwistAngle;

    private Vector _diff;    
    private double _initalHandAngle;
    private double _initalTwistAngle;

    private Vector _targetCoordinate;
    private double _targetHandAngle;
    private double _targetTwistAngle;

    private int    _step;
    private int    _steps;

    public CmdArmModifyPosition(Vector coordinate, double handAngle, double twistAngle, double speed) 
    {
        _modifyCoordinate = coordinate;
        _modifyHandAngle  = handAngle;
        _modifyTwistAngle = twistAngle;

        _steps = Math.max((int) (Constants.LOOPS_PER_SECOND * _modifyCoordinate.getMagnitude() / speed), 1);
    }

    @Override
    public void initialize() 
    {
        Vector initialCoordinate = Arm.getInstance().getCoordinate();
        _initalHandAngle   = Arm.getInstance().getHandAngle();
        _initalTwistAngle  = Arm.getInstance().getTwistTargetAngle();

        _diff = _modifyCoordinate.clone();
        _diff.setX(_diff.getX() * Math.signum(initialCoordinate.getX()));

        _targetCoordinate = initialCoordinate.add(_diff);
        _targetHandAngle  = _initalHandAngle  + _modifyHandAngle  * Math.signum(initialCoordinate.getX());
        _targetTwistAngle = _initalTwistAngle + _modifyTwistAngle * Math.signum(initialCoordinate.getX());

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
