package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.groups.GrpSetArmPosition;
//import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDriveResetEncoders;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveToPosition;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class RobotContainer 
{
    private JoystickButton _highButton;
    private JoystickButton _lowButton;
    private JoystickButton _middleButton;

    public RobotContainer()
    {

        

        DriverStation.silenceJoystickConnectionWarning(true);

        Joystick driveJoy = new Joystick(0);

        CmdDriveWithJoystick driveCmd = new CmdDriveWithJoystick(() -> driveJoy.getX(), () -> -driveJoy.getY(), () -> driveJoy.getZ(), () -> driveJoy.getRawButton(1));

        Drive.getInstance().setDefaultCommand(driveCmd);

        new JoystickButton(driveJoy, 2).onTrue(new CmdDriveResetOdometer());
        new JoystickButton(driveJoy, 11).onTrue(new CmdDriveResetEncoders());

        new JoystickButton(driveJoy, 3).onTrue(new CmdDriveToPosition(new Vector(), 0));

        _highButton = new JoystickButton(driveJoy, 12);
        _middleButton = new JoystickButton(driveJoy, 12);
        _lowButton = new JoystickButton(driveJoy, 12);

        //new JoystickButton(driveJoy, 6).onTrue(new CmdDriveBalance());

        configureBindings();
    }

    private void configureBindings() 
    {
        _highButton.onTrue(new GrpSetArmPosition(ArmPosition.High)); 
        _middleButton.onTrue(new GrpSetArmPosition(ArmPosition.Middle));
        _lowButton.onTrue(new GrpSetArmPosition(ArmPosition.Low));
    }

    public Command getAutonomousCommand() 
    {
        return Commands.print("No autonomous command configured");
    }
}
