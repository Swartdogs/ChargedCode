package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.*;
import frc.robot.subsystems.DriveSubsystem;

public class CmdDriveWithJoystick extends CommandBase 
{
    private final DriveSubsystem driveSubsystem;
    private final Supplier<Double> xSpdFunction, ySpdFunction, rotateSpdFunction;
    private final Supplier<Boolean> fieldOrientedFunction;
    private final SlewRateLimiter xLimiter, yLimiter, turningLimiter;

    public CmdDriveWithJoystick(DriveSubsystem driveSubsystem, Supplier<Double> xSpdFunction, Supplier<Double> ySpdFunction, 
                                Supplier<Double> rotateSpdFunction, Supplier<Boolean> fieldOrientedFunction) 
    {
        this.driveSubsystem = driveSubsystem;
        this.xSpdFunction = xSpdFunction;
        this.ySpdFunction = ySpdFunction;
        this.rotateSpdFunction = rotateSpdFunction;
        this.fieldOrientedFunction = fieldOrientedFunction;
        this.xLimiter = new SlewRateLimiter(DriveConstants.kTeleDriveMaxAccelerationUnitsPerSecond);
        this.yLimiter = new SlewRateLimiter(DriveConstants.kTeleDriveMaxAccelerationUnitsPerSecond);
        this.turningLimiter = new SlewRateLimiter(DriveConstants.kTeleDriveMaxAngularAccelerationUnitsPerSecond);
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() 
    {
    }

    @Override
    public void execute() 
    {
        // Driver Joysticks
        double xSpeed      = xSpdFunction.get();
        double ySpeed      = ySpdFunction.get();
        double rotateSpeed = rotateSpdFunction.get();

        // Deadband
        xSpeed = Math.abs(xSpeed) > OIConstants.kDeadband ? xSpeed : 0.0;
        ySpeed = Math.abs(ySpeed) > OIConstants.kDeadband ? ySpeed : 0.0;
        rotateSpeed = Math.abs(rotateSpeed) > OIConstants.kDeadband ? rotateSpeed : 0.0;

        // Smooth Driving
        xSpeed = xLimiter.calculate(xSpeed) * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
        ySpeed = yLimiter.calculate(ySpeed) * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond;
        rotateSpeed = turningLimiter.calculate(rotateSpeed) * DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond;
    
        // Chassis Speed
        ChassisSpeeds chassisSpeeds;
        if (fieldOrientedFunction.get()) {
            // Relative to field
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                    xSpeed, ySpeed, rotateSpeed, driveSubsystem.getRotation2d());
        } else {
            // Relative to robot
            chassisSpeeds = new ChassisSpeeds(xSpeed, ySpeed, rotateSpeed);
        }

        // Convert to inividual modules
        SwerveModuleState[] moduleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(chassisSpeeds);

        // Output to wheels
        driveSubsystem.setDriveMotorState(moduleStates);
    }

    @Override
    public void end(boolean interrupted) 
    {
        driveSubsystem.stopMotors();
    }

    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
