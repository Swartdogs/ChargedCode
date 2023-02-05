package frc.robot;

public class ArmData 
{
    private double _armAngle;
    private double _armExtension;
    private double _twistAngle;
    private double _wristAngle;
    
    public ArmData(double armAngle, double armExtension, double twistAngle, double wristAngle) 
    {
        _armAngle     = armAngle;
        _armExtension = armExtension;
        _twistAngle   = twistAngle;
        _wristAngle   = wristAngle;
    }

    public double getArmAngle()
    {
        return _armAngle;
    }

    public void setArmAngle(double angle)
    {
        _armAngle = angle;
    }

    public double getArmExtension()
    {
        return _armExtension;
    }

    public void setArmExtension(double extension)
    {
        _armExtension = extension;
    }

    public double getTwistAngle()
    {
        return _twistAngle;
    }

    public void setTwistAngle(double angle)
    {
        _twistAngle = angle;
    }

    public double getWristAngle()
    {
        return _wristAngle;
    }

    public void setWristAngle(double angle)
    {
        _wristAngle = angle;
    }
}
