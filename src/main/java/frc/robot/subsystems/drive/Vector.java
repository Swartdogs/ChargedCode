package frc.robot.subsystems.drive;

public class Vector
{
    private double _x;
    private double _y;
    
    private double _magnitude;
    private double _heading;

    public Vector()
    {
        this(0, 0);
    }

    public Vector(double x, double y)
    {
        _x = x;
        _y = y;

        updatePolar();
    }

    public Vector clone()
    {
        return new Vector(_x, _y);
    }

    public double getX()
    {
        return _x;
    }

    public void setX(double x)
    {
        _x = x;

        updatePolar();
    }

    public double getY()
    {
        return _y;
    }

    public void setY(double y)
    {
        _y = y;

        updatePolar();
    }

    public double getMagnitude()
    {
        return _magnitude;
    }

    public void setMagnitude(double magnitude)
    {
        _magnitude = magnitude;

        updateCartesian();
    }

    public double getHeading()
    {
        return _heading;
    }

    public void setHeading(double heading)
    {
        _heading = Math.IEEEremainder(heading, 360);

        updateCartesian();
    }

    public void setCartesianPosition(double x, double y)
    {
        _x = x;
        _y = y;

        updatePolar();
    }

    public void translateCartesianPosition(double dx, double dy)
    {
        setCartesianPosition(_x + dx, _y + dy);
    }

    public void setPolarPosition(double magnitude, double heading)
    {
        _magnitude = magnitude;
        _heading   = Math.IEEEremainder(heading, 360);

        updateCartesian();
    }

    public void translatePolarPosition(double dMagnitude, double dHeading)
    {
        setPolarPosition(_magnitude + dMagnitude, _heading + dHeading);
    }

    public Vector multiply(double scalar)
    {
        Vector v = clone();

        v._magnitude *= scalar;

        v.updateCartesian();

        return v;
    }

    public Vector divide(double scalar)
    {
        Vector v = clone();

        v._magnitude /= scalar;

        v.updateCartesian();

        return v;
    }

    public Vector add(Vector other)
    {
        return new Vector(getX() + other.getX(), getY() + other.getY());
    }

    public Vector subtract(Vector other)
    {
        return new Vector(getX() - other.getX(), getY() - other.getY());
    }

    private void updateCartesian()
    {
        _y = _magnitude * Math.cos(Math.toRadians(_heading));
        _x = _magnitude * Math.sin(Math.toRadians(_heading));
    }

    private void updatePolar()
    {
        _magnitude     = Math.sqrt((_x * _x) + (_y * _y));
        _heading = Math.IEEEremainder(Math.toDegrees(Math.atan2(_x, _y)), 360);
    }

    @Override
    public String toString()
    {
        return String.format("(%6.2f, %6.2f)", getX(), getY());
    }
}
