package frc.robot.paths;

import edu.wpi.first.math.util.Units;
import frc.robot.Constants;
import frc.robot.subsystems.drive.Vector;

public class TrajectoryFrame
{
    private double _time;
    private Vector _position;
    private Vector _velocity;
    private double _heading;
    private double _angularVelocity;

    public TrajectoryFrame(double time, Vector position, Vector velocity, double heading, double angularVelocity)
    {
        _time = time;
        _position = position;
        _velocity = velocity;
        _heading = heading;
        _angularVelocity = angularVelocity;
    }

    public TrajectoryFrame(String csvLine)
    {
        this(csvLine, false);
    }

    public TrajectoryFrame(String csvLine, boolean mirror)
    {
        String[] csvList = csvLine.split(",");
        
        _time = Double.parseDouble(csvList[0]);
        _position = new Vector(Units.metersToInches(Double.parseDouble(csvList[1])), Units.metersToInches(Double.parseDouble(csvList[2])));
        _position = _position.subtract(Constants.Drive.PATH_PLANNER_ORIGIN);
        _velocity = new Vector();
        _velocity.setMagnitude(Units.metersToInches(Double.parseDouble(csvList[4])));
        _velocity.setHeading(-Double.parseDouble(csvList[3]) + 90);

        _heading = -Double.parseDouble(csvList[7]) + 90;
        _angularVelocity = -Double.parseDouble(csvList[9]);

        if (mirror)
        {
            mirror();
        }
    }

    public void mirror()
    {
        _position.setX(-_position.getX());
        _velocity.setX(-_velocity.getX());
        _heading *= -1;
        _angularVelocity *= -1;
    }

    public TrajectoryFrame clone()
    {
        return new TrajectoryFrame(_time, _position.clone(), _velocity.clone(), _heading, _angularVelocity);
    }

    public double getTime()
    {
        return _time;
    }

    public Vector getPosition()
    {
        return _position;
    }

    public Vector getVelocity()
    {
        return _velocity;
    }

    public double getHeading()
    {
        return _heading;
    }

    public double getAngularVelocity()
    {
        return _angularVelocity;
    }
}
