package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdVisionDefault;
import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDriveResetEncoders;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveToPosition;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class RobotContainer 
{
    public RobotContainer()
    {

        DriverStation.silenceJoystickConnectionWarning(true);

        Joystick driveJoy = new Joystick(0);

        Drive.getInstance().setDefaultCommand
        (
            new CmdDriveWithJoystick
            (
                () -> getJoystickAxis(driveJoy.getX(), false, false, 0.05), 
                () -> getJoystickAxis(driveJoy.getY(), true,  false, 0.05), 
                () -> getJoystickAxis(driveJoy.getZ(), false, true,  0.10), 
                () -> driveJoy.getRawButton(1)
            )  
        );

        Vision.getInstance().setDefaultCommand(new CmdVisionDefault());

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

    private void configureBindings() {}

    public Command getAutonomousCommand() 
    {
        return Commands.print("No autonomous command configured");
    }

    private double applyDeadband(double input, double deadband)
    {
        double output = 0;

        if (Math.abs(input) > deadband)
        {
            output = (input - (deadband * Math.signum(input))) / (1.0 - deadband);
        }

        return output;
    }

    private double getJoystickAxis(double input, boolean inverted, boolean squareInput, double deadband)
    {
        if(inverted)
        {
            input *= -1;
        }

        if (squareInput)
        {
            input *= input * Math.signum(input);
        }

        return applyDeadband(input, deadband);
    }
}
