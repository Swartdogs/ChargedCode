package frc.robot.subsystems.drive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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

    private AHRS           _gyro; // navX2
    private double         _gyroOffset; // angle to adjust gyro by for zeroing

    private Vector         _origin; // origin of rotation relative to the center of the robot frame

    private SwerveModule[] _swerveModules; // all of the swerve modules

    private double         _oldRotationHeading; // used to calculate angular velocity
    private double         _rotationHeading; // current direction (heading) that the front of the robot is facing

    private double         _rotationVelocity; // how fast are we turning?

    private Vector         _oldPosition; // used to calculate the translation velocity
    private Vector         _position; // current odometer reading

    private Vector         _velocity; // change in odometer reading

    private Drive()
    {
        _origin             = new Vector();

        //_gyro = new AHRS(SPI.Port.kMXP);

        SwerveModule fl = new SwerveModule(-Constants.Drive.BASE_WIDTH / 2,  Constants.Drive.BASE_LENGTH / 2, 1, 2, 0);// FIXME
        SwerveModule fr = new SwerveModule( Constants.Drive.BASE_WIDTH / 2,  Constants.Drive.BASE_LENGTH / 2, 5, 6, 3);
        SwerveModule bl = new SwerveModule(-Constants.Drive.BASE_WIDTH / 2, -Constants.Drive.BASE_LENGTH / 2, 3, 4, 1);
        SwerveModule br = new SwerveModule( Constants.Drive.BASE_WIDTH / 2, -Constants.Drive.BASE_LENGTH / 2, 7, 8, 2);

        _swerveModules = new SwerveModule[]// turn the swerve modules into an array, so they can be easily accessed, with an arbitrary number of them
        { 
            fl, 
            fr,
            bl,
            br 
        };

        setOrigin(0, 0);// rotate around the center of the robot, by default
        resetOdometer();// initialize the odometer to 0, 0
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
            Vector rotateVector = new Vector(modulePosition.getY(), -modulePosition.getX());// clockwise perp.
            rotateVector = rotateVector.multiply(rotate / 180 * Math.PI);
            Vector outputVector = translateVector.add(rotateVector);

            moduleCommands[i] = outputVector;

            maxSpeed = Math.max(maxSpeed, moduleCommands[i].getMagnitude());
        }

        for (int i = 0; i < moduleCommands.length; i++)
        {
            if (maxSpeed > Constants.Drive.MAX_DRIVE_SPEED)
            {
                moduleCommands[i] = moduleCommands[i].divide(maxSpeed / Constants.Drive.MAX_DRIVE_SPEED);// divide by canstant to keep scale max at constant
            }

            _swerveModules[i].drive(moduleCommands[i]);
        }
    }

    public void zeroModuleRotations()
    {
        for (int i = 0; i < _swerveModules.length; i++)
        {
            _swerveModules[i].zeroEncoder();
        }
    }

    public void setOrigin(double x, double y)
    {
        setOrigin(new Vector(x, y));   
    }

    public void setOrigin(Vector newOrigin)
    {
        _origin = newOrigin;

        for (int i = 0; i < _swerveModules.length; i++)
        {
            Vector modulePosition = _swerveModules[i].clone();
            modulePosition = modulePosition.subtract(_origin);
        }
    }

    public Vector getOrigin() 
    {
        return _origin;
    }

    public double getHeading()
    {
        //return Math.IEEEremainder(_gyro.getAngle() + _gyroOffset, 360);
        return 0;
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

    @Override
    public void periodic()
    {
        updateOdometry();

        System.out.println(getPosition());
    }

    public void resetOdometer()
    {
        setPosition(new Vector());
    }

    public void setPosition(Vector newPosition)
    {
        _position = newPosition;

        for (int i = 0; i < _swerveModules.length; i++)
        {
            _swerveModules[i].resetDrivePosition();
        }
    }

    public Vector getPosition()
    {
        return _position;
    }

    public Vector getVelocity()
    {
        return _velocity;
    }

    public void updateOdometry()
    {
        _oldRotationHeading = _rotationHeading;
        _rotationHeading = getHeading();

        _rotationVelocity = (_rotationHeading - _oldRotationHeading) * Constants.LOOPS_PER_SECOND;

        _oldPosition = _position;

        Vector change = new Vector();

        for (int i = 0; i < _swerveModules.length; i++)
        {
            change = change.add(_swerveModules[i].getOffset());
        }

        change.setCartesianPosition(change.getX() / _swerveModules.length, change.getY() / _swerveModules.length);

        change.translatePolarPosition(0.0, getHeading());

        _position = _position.add(change);

        _velocity = new Vector((_position.getX() - _oldPosition.getX()) * Constants.LOOPS_PER_SECOND, (_position.getY() - _oldPosition.getY()) * Constants.LOOPS_PER_SECOND);
    }
}