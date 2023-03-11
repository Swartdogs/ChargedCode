package frc.robot;

import frc.robot.subsystems.drive.Vector;

public class ArmData 
{
    private Vector  _coordinate;
    private double  _handAngle;
    private double  _twistAngle;
    private boolean _preserveHandFlip;
    
    public ArmData(Vector coordinate, double handAngle, double twistAngle, boolean preserveHandFlip) 
    {
        _coordinate       = coordinate;
        _handAngle        = handAngle;
        _twistAngle       = twistAngle;
        _preserveHandFlip = preserveHandFlip;
    }

    public Vector getCoordinate()
    {
        return _coordinate;
    }

    public void setCoordinate(Vector coordinate)
    {
        _coordinate = coordinate;
    }

    public double getHandAngle()
    {
        return _handAngle;
    }

    public void setHandAngle(double handAngle)
    {
        _handAngle = handAngle;
    }

    public double getTwistAngle()
    {
        return _twistAngle;
    }

    public void setTwistAngle(double angle)
    {
        _twistAngle = angle;
    }

    public boolean preserveHandFlip()
    {
        return _preserveHandFlip;
    }

    public void setPreserveHandFlip(boolean preserveHandFlip)
    {
        _preserveHandFlip = preserveHandFlip;
    }

    public ArmData opposite()
    {
        return new ArmData(new Vector(-_coordinate.getX(), _coordinate.getY()), -_handAngle, -_twistAngle, _preserveHandFlip);
    }
}
