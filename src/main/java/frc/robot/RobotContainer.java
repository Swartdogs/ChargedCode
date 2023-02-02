package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDriveResetEncoders;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveToPosition;

import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;
import frc.robot.subsystems.Dashboard;

public class RobotContainer 
{
    public RobotContainer()
    {

        DriverStation.silenceJoystickConnectionWarning(true);

        Joystick driveJoy = new Joystick(0);

        CmdDriveWithJoystick driveCmd = new CmdDriveWithJoystick(() -> driveJoy.getX(), () -> -driveJoy.getY(), () -> driveJoy.getZ(), () -> driveJoy.getRawButton(1));

        Drive.getInstance().setDefaultCommand(driveCmd);

        new JoystickButton(driveJoy, 11).onTrue(new CmdDriveResetOdometer());
        new JoystickButton(driveJoy, 8).onTrue(new CmdDriveResetEncoders());

        CmdDriveToPosition driveTestCommand = new CmdDriveToPosition(new Vector(), 0);
        CmdDriveBalance balanceCommand = new CmdDriveBalance();

        new JoystickButton(driveJoy, 5).onTrue(driveTestCommand);

        new JoystickButton(driveJoy, 4).onTrue(balanceCommand);

        JoystickButton cancelButton = new JoystickButton(driveJoy, 2);
        cancelButton.onTrue(new InstantCommand(()->driveTestCommand.cancel()));
        cancelButton.onTrue(new InstantCommand(()->balanceCommand.cancel()));

        configureBindings();
    }

    private void configureBindings() 
    {
        Dashboard.getInstance();
    }

    public Command getAutonomousCommand() 
    {
        return Commands.print("No autonomous command configured");
    }
}
