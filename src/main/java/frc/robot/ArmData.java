package frc.robot;

import frc.robot.subsystems.drive.Vector;

public class ArmData 
{
    private Vector _coordinate;
    private double _handAngle;
    private double _twistAngle;
    
    public ArmData(Vector coordinate, double handAngle, double twistAngle) 
    {
        _coordinate = coordinate;
        _handAngle  = handAngle;
        _twistAngle = twistAngle;
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
}
