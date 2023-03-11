package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ArmData;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.drive.Vector;

public class CmdArmSetPosition extends CommandBase
{
    private Vector  _givenCoordinate;
    private double  _givenHandAngle;
    private double  _givenTwistAngle;

    private Vector  _targetCoordinate;
    private double  _targetHandAngle;
    private double  _targetTwistAngle;

    private Vector  _initialCoordinate;
    private double  _initialHandAngle;
    private double  _initialTwistAngle;

    private double  _speed;
    private int     _step;
    private int     _steps;
    private boolean _absolute;

    public CmdArmSetPosition(Vector coordinate, double handAngle, double twistAngle, double speed, boolean absolute)
    {
        _givenCoordinate = coordinate;
        _givenHandAngle  = handAngle;
        _givenTwistAngle = twistAngle;

        _targetCoordinate = _givenCoordinate;
        _targetHandAngle  = _givenHandAngle;
        _targetTwistAngle = _givenTwistAngle;

        _initialCoordinate = null;
        _initialHandAngle  = 0;
        _initialTwistAngle = 0;

        _speed    = speed;
        _step     = 0;
        _steps    = 0;
        _absolute = absolute;
    }

    public CmdArmSetPosition(ArmData data, double speed, boolean absolute)
    {
        this(data.getCoordinate(), data.getHandAngle(), data.getTwistAngle(), speed, absolute);

        if (!data.preserveHandFlip())
        {
            Arm.getInstance().setHandIsFlipped(false);
        }
    }

    @Override
    public void initialize()
    {
        Vector diff;

        _initialCoordinate = Arm.getInstance().getCoordinate();
        _initialHandAngle = Arm.getInstance().getHandAngle();
        _initialTwistAngle = Arm.getInstance().getTwistTargetAngle();

        if (_absolute)
        {
            _targetCoordinate = _givenCoordinate;
            _targetHandAngle  = _givenHandAngle;
            _targetTwistAngle = _givenTwistAngle;

            diff = _initialCoordinate.subtract(_targetCoordinate);
        }
        else
        {
            var coordSign = Math.signum(_initialCoordinate.getX());

            diff = _givenCoordinate.clone();
            diff.setX(diff.getX() * coordSign);

            _targetCoordinate = _initialCoordinate.add(diff);
            _targetHandAngle  = _initialHandAngle  + _givenHandAngle  * coordSign;
            _targetTwistAngle = _initialTwistAngle + _givenTwistAngle * coordSign;
        }

        _steps = Math.max((int)(Constants.LOOPS_PER_SECOND * diff.getMagnitude() / _speed), 1);
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

        Vector armCoord   = _initialCoordinate.multiply(1 - ratio).add(_targetCoordinate.multiply(ratio));
        double handAngle  = _initialHandAngle  * (1 - ratio) + _targetHandAngle  * ratio;
        double twistAngle = _initialTwistAngle * (1 - ratio) + _targetTwistAngle * ratio;

        Arm.getInstance().setArmPosition(armCoord, handAngle, twistAngle);
    }

    @Override
    public boolean isFinished()
    {
        return _step >= _steps                         &&
               Arm.getInstance().shoulderAtAngle()     &&
               Arm.getInstance().extensionAtDistance() &&
               Arm.getInstance().twistAtAngle()        &&
               Arm.getInstance().wristAtAngle();
    }
}
