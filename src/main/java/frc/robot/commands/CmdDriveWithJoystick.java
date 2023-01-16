package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.*;
import frc.robot.subsystems.Drive;

public class CmdDriveWithJoystick extends CommandBase 
{
    private final DoubleSupplier  _xSpdFunction;
    private final DoubleSupplier  _ySpdFunction;
    private final DoubleSupplier  _rotateSpdFunction;
    private final BooleanSupplier _fieldOrientedFunction;
    private final Drive           _driveSubsystem;

    public CmdDriveWithJoystick(Drive driveSubsystem, DoubleSupplier xSpdFunction, DoubleSupplier ySpdFunction, 
    DoubleSupplier rotateSpdFunction, BooleanSupplier fieldOrientedFunction) 
    {
        _driveSubsystem        = Drive.getInstance();
        _xSpdFunction          = xSpdFunction;
        _ySpdFunction          = ySpdFunction;
        _rotateSpdFunction     = rotateSpdFunction;
        _fieldOrientedFunction = fieldOrientedFunction;
    }

    @Override
    public void execute() 
    {
        // Driver Joysticks
        double xSpeed      = _xSpdFunction.getAsDouble();
        double ySpeed      = _ySpdFunction.getAsDouble();
        double rotateSpeed = _rotateSpdFunction.getAsDouble();
    
        // Chassis Speed
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(xSpeed, ySpeed, rotateSpeed);

        if (_fieldOrientedFunction.getAsBoolean())
        {
            // Relative to field
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotateSpeed, _driveSubsystem.getRotation2d());
        }

        // Convert to inividual modules
        SwerveModuleState[] moduleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(chassisSpeeds);

        // Output to wheels
        _driveSubsystem.setModuleStates(moduleStates);
    }

    @Override
    public void end(boolean interrupted) 
    {
        _driveSubsystem.stopMotors();
    }
}
