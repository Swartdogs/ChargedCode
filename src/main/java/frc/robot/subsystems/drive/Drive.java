package frc.robot.subsystems.drive;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.subsystems.RobotLog;

public class Drive extends SubsystemBase
{
    private static Drive _instance;

    public static Drive getInstance()
    {
        if (_instance == null)
        {
            _instance = new Drive();
        }

        return _instance;
    }

    private AHRS           _gyro;            // navX2
    private double         _gyroOffset;      // angle to adjust gyro by for zeroing

    private Vector         _origin;          // origin of rotation relative to the center of the robot frame

    private SwerveModule[] _swerveModules;   // all of the swerve modules

    // odometry
    private double         _rotationHeading; // used to calculate angular velocity
    private double         _rotationVelocity;// how fast are we turning?

    private Vector         _position;        // current odometer reading
    private Vector         _velocity;        // change in odometer reading

    // TODO: kalman filtering for odometry 
    /* Learn about Kalman filters here: (a long read)
    * https://www.alanzucconi.com/2022/07/24/kalman-filter-1/
    *
    * see a graph of some simple math for them here: 
    * https://www.desmos.com/calculator/rfa3bzdv3c
    */

    private double _rotationHeadingVariance;
    private double _rotationVelocityVariance;

    private double _positionVariance;
    private double _velocityVariance;

    private Drive()
    {
        _origin = new Vector();

        _gyro   = new AHRS(SPI.Port.kMXP);

        SwerveModule fl = new SwerveModule(-Constants.Drive.BASE_WIDTH / 2,  Constants.Drive.BASE_LENGTH / 2, Constants.Drive.FL_OFFSET, -90.0, Constants.Drive.FL_DRIVE_CAN_ID, Constants.Drive.FL_ROTATE_CAN_ID, Constants.Drive.FL_ROTATE_SENSOR_PORT);
        SwerveModule fr = new SwerveModule( Constants.Drive.BASE_WIDTH / 2,  Constants.Drive.BASE_LENGTH / 2, Constants.Drive.FR_OFFSET,  90.0, Constants.Drive.FR_DRIVE_CAN_ID, Constants.Drive.FR_ROTATE_CAN_ID, Constants.Drive.FR_ROTATE_SENSOR_PORT);
        SwerveModule bl = new SwerveModule(-Constants.Drive.BASE_WIDTH / 2, -Constants.Drive.BASE_LENGTH / 2, Constants.Drive.BL_OFFSET, -90.0, Constants.Drive.BL_DRIVE_CAN_ID, Constants.Drive.BL_ROTATE_CAN_ID, Constants.Drive.BL_ROTATE_SENSOR_PORT);
        SwerveModule br = new SwerveModule( Constants.Drive.BASE_WIDTH / 2, -Constants.Drive.BASE_LENGTH / 2, Constants.Drive.BR_OFFSET,  90.0, Constants.Drive.BR_DRIVE_CAN_ID, Constants.Drive.BR_ROTATE_CAN_ID, Constants.Drive.BR_ROTATE_SENSOR_PORT);

        _swerveModules = new SwerveModule[]
        { 
            fl, 
            fr,
            bl,
            br 
        };

        setOrigin(0, 0);    // rotate around the center of the robot, by default
        resetOdometer();    // initialize the odometer to 0, 0

        RobotLog.getInstance().log("Created Drive Subsystem");
    }

    public void chassisDrive(double drive, double strafe, double rotate)
    {
        Vector translateVector = new Vector(strafe, drive);
        
        drive(translateVector, rotate);
    }

    public void fieldDrive(double x, double y, double rotate)
    {
        Vector translateVector = new Vector(x, y);

        translateVector.translatePolarPosition(0, -getHeading());
        
        drive(translateVector, rotate);
    }

    private void drive(Vector translateVector, double rotate)
    {
        Vector[] moduleCommands = new Vector[_swerveModules.length];

        double maxSpeed = 0;

        for (int i = 0; i < _swerveModules.length; i++)
        {
            Vector modulePosition = _swerveModules[i].subtract(_origin);
            Vector rotateVector = new Vector(modulePosition.getY(), -modulePosition.getX());    // clockwise 90 deg.
            rotateVector = rotateVector.multiply(rotate / Constants.Drive.TYPICAL_MODULE_DIST); // FIXME: calculate max module dist
            Vector outputVector = translateVector.add(rotateVector);

            moduleCommands[i] = outputVector;

            maxSpeed = Math.max(maxSpeed, moduleCommands[i].getMagnitude());
        }

        for (int i = 0; i < moduleCommands.length; i++)
        {
            if (maxSpeed > 1)
            {
                moduleCommands[i] = moduleCommands[i].divide(maxSpeed);
            }

            _swerveModules[i].drive(moduleCommands[i]);
        }
    }

    public void zeroModuleRotations()
    {
        for (int i = 0; i < _swerveModules.length; i++)
        {
            _swerveModules[i].zeroRotation();
        }
    }

    public void setOrigin(double x, double y)
    {
        setOrigin(new Vector(x, y));   
    }

    public void setOrigin(Vector newOrigin)
    {
        _origin = newOrigin;

    }

    public void rotateModules(double heading)
    {
        for (int i = 0; i < _swerveModules.length; i++)
        {
            Vector moduleCommand = new Vector();
            moduleCommand.setHeading(heading);
            _swerveModules[i].drive(moduleCommand);
        }
    }

    public Vector getOrigin()
    {
        return _origin;
    }

    public double getChassisRoll()
    {
        return -_gyro.getPitch();// pitch axis is the robot roll
    }

    public double getChassisPitch()
    {
        return _gyro.getRoll();
    }

    public double getHeading()
    {
        return Math.IEEEremainder(_gyro.getAngle() + _gyroOffset, 360);
    }

    public double getHeadingVelocity()
    {
        return _rotationVelocity;
    }

    public void setGyro(double heading) 
    {
        _gyro.reset();
        _gyroOffset = heading;
    }

    public SwerveModule getSwerveModule(int index)
    {
        return _swerveModules[index];
    }

    @Override
    public void periodic()
    {
        updateOdometry();

        //System.out.println(String.format("Yaw: %6.2f, Pitch: %6.2f, Roll: %6.2f; Velocity: %s", _gyro.getYaw(), _gyro.getPitch(), _gyro.getRoll(), getChassisVelocity()));
        //System.out.println(String.format("Gyro: %6.2f, Field Position: %s, Chassis Velocity: %s", getHeading(), getFieldPosition(), getChassisVelocity()));
    }

    public void resetOdometer()
    {
        setPosition(new Vector());
        _velocity = new Vector();
    }

    public void setPosition(Vector newPosition)
    {
        _position = newPosition;

        for (int i = 0; i < _swerveModules.length; i++)
        {
            _swerveModules[i].updateOdometry();
        }
    }

    public Vector getFieldPosition()
    {
        return _position;
    }

    public Vector getFieldVelocity()
    {
        return _velocity;
    }

    public Vector getChassisVelocity()
    {
        Vector chassisVelocity = _velocity.clone();
        chassisVelocity.translatePolarPosition(0, _rotationHeading);

        return chassisVelocity;
    }

    public void fusePosition(Vector inputPosition, double inputVariance)
    {
        if (inputVariance >= 0)// prevent negative variances
        {
            double newX = _position.getX() * inputVariance + inputPosition.getX() * _positionVariance;
            double newY = _position.getY() * inputVariance + inputPosition.getY() * _positionVariance;

            newX /= (_positionVariance + inputVariance);
            newY /= (_positionVariance + inputVariance);

            _positionVariance = (_positionVariance * inputVariance) / (_positionVariance + inputVariance);
            _position = new Vector(newX, newY);
        }
    }

    public void fuseVelocity(Vector inputVelocity, double inputVariance)
    {
        if (inputVariance >= 0)// prevent negative variances
        {
            double newX = _velocity.getX() * inputVariance + inputVelocity.getX() * _velocityVariance;
            double newY = _velocity.getY() * inputVariance + inputVelocity.getY() * _velocityVariance;

            newX /= (_velocityVariance + inputVariance);
            newY /= (_velocityVariance + inputVariance);

            _velocityVariance = (_velocityVariance * inputVariance) / (_velocityVariance + inputVariance);
            _velocity = new Vector(newX, newY);
        }
    }

    public void fuseHeading(double inputHeading, double inputVariance)
    {
        if (inputVariance >= 0)// prevent negative variances
        {
            double diff = (inputHeading - getHeading());
            diff = (diff * _rotationHeadingVariance) / (_rotationHeadingVariance + inputVariance);
            
            _rotationHeadingVariance = (_rotationHeadingVariance * inputVariance) / (_rotationHeadingVariance + inputVariance);
            _gyroOffset += diff;
        }
    }

    public void fuseRotationVelocity(double inputRotation, double inputVariance)
    {
        if (inputVariance >= 0)// prevent negative variances
        {
            _rotationVelocity = (inputRotation * _rotationVelocityVariance + _rotationVelocity * inputVariance) / (_rotationVelocityVariance + inputVariance);
            _rotationVelocityVariance = (_rotationVelocityVariance * inputVariance) / (_rotationVelocityVariance + inputVariance);
        }
    }

    public void updateOdometry()
    {
        //generate the process values
         Vector processPosition = _position.add(_velocity.divide(Constants.LOOPS_PER_SECOND));

        // read sensors
        // calculate angular velocity
        double newRotationHeading = getHeading();

        double sensorRotationVelocity = (newRotationHeading - _rotationHeading) * Constants.LOOPS_PER_SECOND;
        double sensorRotationVariance = Math.abs(sensorRotationVelocity - _rotationVelocity);

        _rotationHeading = newRotationHeading;

        // calculate new position and velocity
        Vector change = new Vector();
        Vector squaredChange = new Vector();

        for (int i = 0; i < _swerveModules.length; i++)
        {
            Vector moduleChange = _swerveModules[i].updateOdometry();
            change = change.add(moduleChange);
            squaredChange = squaredChange.add(new Vector(moduleChange.getX() * moduleChange.getX(), moduleChange.getY() * moduleChange.getY()));
        }

        change = change.divide(_swerveModules.length);// to average (same as dividing each component)
        squaredChange = squaredChange.divide(_swerveModules.length);

        change.translatePolarPosition(0.0, _rotationHeading);// field centric change
        squaredChange.translatePolarPosition(0.0, _rotationHeading);
        
        double sensorPositionVariance = squaredChange.subtract(new Vector(change.getX() * change.getX(), change.getY() * change.getY()).divide(_swerveModules.length)).getMagnitude();

        Vector sensorPosition = _position.add(change);
        Vector sensorVelocity = change.multiply(Constants.LOOPS_PER_SECOND);

        double sensorVelocityVariance = sensorVelocity.subtract(_velocity).getMagnitude();

        // fuse process and sensor values

        // TODO: test variances for position input and for odometry calculation
        // System.out.println(String.format("Process: %s; Sensor: %s; Sensor Variance: %6.2f, Heading: %6.2f", _rotationVelocity, sensorRotationVelocity, sensorRotationVariance, _rotationHeading));

        _positionVariance += 1 + (_velocityVariance / Constants.LOOPS_PER_SECOND);// process variance
        _velocityVariance += 1;// we can play with the costant addition

        _rotationHeadingVariance += 0.001;
        _rotationVelocityVariance += 1;

        _position = processPosition;

        fusePosition(sensorPosition, sensorPositionVariance + 1);
        fuseVelocity(sensorVelocity, sensorVelocityVariance + 1);
        fuseRotationVelocity(sensorRotationVelocity, sensorRotationVariance + 1);
        // System.out.println(String.format("Odometer: %s; Position Variance: %6.2f; Heading: %6.2f; Heading Variance: %6.2f", _position, _positionVariance, _rotationHeading, _rotationHeadingVariance));

    }
}
