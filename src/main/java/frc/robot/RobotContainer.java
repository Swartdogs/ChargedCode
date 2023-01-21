package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdDriveResetEncoders;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.subsystems.drive.Drive;

public class RobotContainer 
{
    public RobotContainer()
    {

        DriverStation.silenceJoystickConnectionWarning(true);

        Joystick driveJoy = new Joystick(0);

        CmdDriveWithJoystick driveCmd = new CmdDriveWithJoystick(() -> driveJoy.getX(), () -> -driveJoy.getY(), () -> driveJoy.getZ(), () -> driveJoy.getRawButton(1));

        Drive.getInstance().setDefaultCommand(driveCmd);

        new JoystickButton(driveJoy, 2).onTrue(new CmdDriveResetOdometer());
        new JoystickButton(driveJoy, 11).onTrue(new CmdDriveResetEncoders());

        configureBindings();
    }

    private void configureBindings() {}

    public Command getAutonomousCommand() 
    {
        return Commands.print("No autonomous command configured");
    }
}
