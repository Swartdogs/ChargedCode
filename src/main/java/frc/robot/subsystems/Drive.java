package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;

public class Drive 
{
    private final CANSparkMax        _driveMotor;
    private final CANSparkMax        _rotateMotor;

    private final RelativeEncoder    _driveEncoder;
    private final RelativeEncoder    _rotateEncoder;

    private final PIDController      _rotatePIDController;

    private final AnalogInput        _absoluteEncoder;
    private final boolean            _absoluteEncoderReversed;
    private final double             _absoluteEncoderOffsetRad;

    public Drive(int driveMotorID, int rotateMotorID, boolean driveMotorReversed, boolean rotateMotorReversed, 
                 int absoluteEncoderID, double absoluteEncoderOffset, boolean absoluteEncoderReversed)
    {
        this._absoluteEncoderOffsetRad = absoluteEncoderOffset;
        this._absoluteEncoderReversed  = absoluteEncoderReversed;
        _absoluteEncoder               = new AnalogInput(absoluteEncoderID);

        _driveMotor                    = new CANSparkMax(driveMotorID, MotorType.kBrushless);
        _rotateMotor                   = new CANSparkMax(rotateMotorID, MotorType.kBrushless);

        _driveMotor.setInverted(driveMotorReversed);
        _rotateMotor.setInverted(rotateMotorReversed);

        _driveEncoder                  = _driveMotor.getEncoder();
        _rotateEncoder                 = _rotateMotor.getEncoder();

        _driveEncoder.setPositionConversionFactor(ModuleConstants.kDriveEncoderRot2Meter);
        _driveEncoder.setVelocityConversionFactor(ModuleConstants.kDriveEncoderRPM2MeterPerSec);
        _rotateEncoder.setPositionConversionFactor(ModuleConstants.kRotateEncoderRot2Rad);
        _rotateEncoder.setVelocityConversionFactor(ModuleConstants.kRotateEncoderRPM2RadPerSec);

        _rotatePIDController           = new PIDController(ModuleConstants.kPRotate, 0, 0);
        _rotatePIDController.enableContinuousInput(-Math.PI, Math.PI);

        resetEncoders();
    }

    public double getDrivePosition()
    {
        return _driveEncoder.getPosition();
    }

    public double getRotatePosition()
    {
        return _rotateEncoder.getPosition();
    }

    public double getDriveVelocity()
    {
        return _driveEncoder.getVelocity();
    }

    public double getRotateVelocity()
    {
        return _rotateEncoder.getVelocity();
    }

    public double getAbsoluteEncoderRad()
    {
        double angle = _absoluteEncoder.getVoltage() / RobotController.getCurrent5V();
        angle *= 2.0 * Math.PI;
        angle -= _absoluteEncoderOffsetRad;

        return angle * (_absoluteEncoderReversed ? -1.0 : 1.0);
    }

    public void resetEncoders()
    {
        _driveEncoder.setPosition(0);
        _rotateEncoder.setPosition(getAbsoluteEncoderRad());
    }

    public SwerveModuleState getState()
    {
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getRotatePosition()));
    }

    public void setDesiredState(SwerveModuleState state)
    {
        if(Math.abs(state.speedMetersPerSecond) < 0.001)
        {
            stop();
            return;
        }

        state = SwerveModuleState.optimize(state, getState().angle);
        _driveMotor.set(state.speedMetersPerSecond / DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        _rotateMotor.set(_rotatePIDController.calculate(getRotatePosition(), state.angle.getRadians()));
        SmartDashboard.putString("Swerve[" + _absoluteEncoder.getChannel() + "] state", state.toString());
    }

    public void stop()
    {
        _driveMotor.set(0);
        _rotateMotor.set(0);
    }
}
