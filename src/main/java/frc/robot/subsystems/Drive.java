package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

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

    private final SwerveModule _frontLeft = new SwerveModule(
            DriveConstants.kFrontLeftDriveMotorPort,
            DriveConstants.kFrontLeftTurningMotorPort,
            DriveConstants.kFrontLeftDriveEncoderReversed,
            DriveConstants.kFrontLeftTurningEncoderReversed,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderPort,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule _frontRight = new SwerveModule(
            DriveConstants.kFrontRightDriveMotorPort,
            DriveConstants.kFrontRightTurningMotorPort,
            DriveConstants.kFrontRightDriveEncoderReversed,
            DriveConstants.kFrontRightTurningEncoderReversed,
            DriveConstants.kFrontRightDriveAbsoluteEncoderPort,
            DriveConstants.kFrontRightDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kFrontRightDriveAbsoluteEncoderReversed);

    private final SwerveModule _backLeft = new SwerveModule(
            DriveConstants.kBackLeftDriveMotorPort,
            DriveConstants.kBackLeftTurningMotorPort,
            DriveConstants.kBackLeftDriveEncoderReversed,
            DriveConstants.kBackLeftTurningEncoderReversed,
            DriveConstants.kBackLeftDriveAbsoluteEncoderPort,
            DriveConstants.kBackLeftDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kBackLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule _backRight = new SwerveModule(
            DriveConstants.kBackRightDriveMotorPort,
            DriveConstants.kBackRightTurningMotorPort,
            DriveConstants.kBackRightDriveEncoderReversed,
            DriveConstants.kBackRightTurningEncoderReversed,
            DriveConstants.kBackRightDriveAbsoluteEncoderPort,
            DriveConstants.kBackRightDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kBackRightDriveAbsoluteEncoderReversed);

    private final AHRS _gyro = new AHRS(SPI.Port.kMXP);
    private final SwerveDriveOdometry _odometer = new SwerveDriveOdometry(DriveConstants.kDriveKinematics, new Rotation2d(0), getSwerveModulePositions());

    private Drive() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                zeroHeading();
            } catch (Exception e) {
            }
        }).start();
    }

    public SwerveModulePosition[] getSwerveModulePositions()
    {
        return new SwerveModulePosition[]{
            _frontLeft.getPosition(),
            _frontRight.getPosition(),
            _backLeft.getPosition(),
            _backRight.getPosition()
        };
    }

    public void zeroHeading()
    {
        _gyro.reset();
    }

    public double getHeading()
    {
        return Math.IEEEremainder(_gyro.getAngle(), 360);
    }

    public Rotation2d getRotation2d()
    {
        return Rotation2d.fromDegrees(getHeading());
    }

    public Pose2d getPose()
    {
        return _odometer.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose)
    {
        _odometer.resetPosition(getRotation2d(), getSwerveModulePositions(), pose);
    }

    @Override
    public void periodic()
    {
        _odometer.update(getRotation2d(), getSwerveModulePositions());
    }

    public void stopMotors()
    {
        _frontLeft.stop();
        _frontRight.stop();
        _backLeft.stop();
        _backRight.stop();
    }

    public void setModuleStates(SwerveModuleState[] state)
    {
        SwerveDriveKinematics.desaturateWheelSpeeds(state, DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        _frontLeft.setDesiredState(state[0]);
        _frontRight.setDesiredState(state[1]);
        _backLeft.setDesiredState(state[2]);
        _backRight.setDesiredState(state[3]);
    } 
}
