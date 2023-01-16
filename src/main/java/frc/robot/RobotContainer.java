package frc.robot;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.*;
import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.subsystems.Drive;

public class RobotContainer 
{
    private final Drive _drive             = Drive.getInstance();

    private final Joystick _driverJoystick = new Joystick(OIConstants.kDriverControllerPort);

    public RobotContainer() 
    {
        _drive.setDefaultCommand(new CmdDriveWithJoystick(_drive, 
                        () -> -_driverJoystick.getRawAxis(OIConstants.kDriverYAxis), 
                        () -> _driverJoystick.getRawAxis(OIConstants.kDriverXAxis), 
                        () -> _driverJoystick.getRawAxis(OIConstants.kDriverRotAxis), 
                        () -> !_driverJoystick.getRawButton(OIConstants.kDriverFieldOrientedButtonIdx)));
        configureBindings();
    }

    private void configureBindings() 
    {
        new JoystickButton(_driverJoystick, 2).onTrue(new InstantCommand(() -> _drive.zeroHeading()));
    }

    public Command getAutonomousCommand() 
    {
        // 1. Create trajectory settings
        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        .setKinematics(DriveConstants.kDriveKinematics);

        // 2. Generate trajectory
        Trajectory _trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, new Rotation2d(0)),
                List.of(
                        new Translation2d(1, 0),
                        new Translation2d(1, -1)),
                new Pose2d(2, -1, Rotation2d.fromDegrees(180)),
                trajectoryConfig);

        // 3. Define PID controllers for tracking trajectory
        PIDController _xController              = new PIDController(AutoConstants.kPXController, 0, 0);
        PIDController _yController              = new PIDController(AutoConstants.kPYController, 0, 0);
        ProfiledPIDController _thetaController  = new ProfiledPIDController(
                AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
        _thetaController.enableContinuousInput(-Math.PI, Math.PI);

        // 4. Construct command to follow trajectory
        SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
                _trajectory,
                _drive::getPose,
                DriveConstants.kDriveKinematics,
                _xController,
                _yController,
                _thetaController,
                _drive::setModuleStates,
                _drive);


        // 5. Add some init and wrap-up, and return everything
        return new SequentialCommandGroup(
                new InstantCommand(() -> _drive.resetOdometry(_trajectory.getInitialPose())),
                swerveControllerCommand,
                new InstantCommand(() -> _drive.stopMotors()));
    }
}
