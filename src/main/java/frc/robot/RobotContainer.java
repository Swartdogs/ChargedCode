package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.groups.GrpSetArmPosition;
import frc.robot.commands.CmdArmFlipSides;
//import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDriveResetEncoders;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveToPosition;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Manipulator.HandMode;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class RobotContainer 
{
    private JoystickButton _highButton;
    private JoystickButton _lowButton;
    private JoystickButton _middleButton;
    private JoystickButton _armSideSwitch;
    private JoystickButton _handModeSwitch;

    public RobotContainer()
    {
        DriverStation.silenceJoystickConnectionWarning(true);

        Manipulator.getInstance();
        Dashboard.getInstance();

        Joystick driveJoy  = new Joystick(0);
        Joystick buttonBox = new Joystick(1);

        // CmdDriveWithJoystick driveCmd = new CmdDriveWithJoystick(() -> driveJoy.getX(), () -> -driveJoy.getY(), () -> driveJoy.getZ(), () -> driveJoy.getRawButton(1));

        // Drive.getInstance().setDefaultCommand(driveCmd);

        // new JoystickButton(driveJoy, 2).onTrue(new CmdDriveResetOdometer());
        // new JoystickButton(driveJoy, 11).onTrue(new CmdDriveResetEncoders());

        // new JoystickButton(driveJoy, 3).onTrue(new CmdDriveToPosition(new Vector(), 0));

        _highButton     = new JoystickButton(buttonBox, 4);
        _middleButton   = new JoystickButton(buttonBox, 2);
        _lowButton      = new JoystickButton(buttonBox, 5);
        _armSideSwitch  = new JoystickButton(buttonBox, 1);
        _handModeSwitch = new JoystickButton(buttonBox, 3);

        //new JoystickButton(driveJoy, 6).onTrue(new CmdDriveBalance());

        configureBindings();
    }

    private void configureBindings() 
    {
        var flipSidesCommand = new CmdArmFlipSides(this::getArmSide, this::getHandMode);

        _highButton.onTrue(new GrpSetArmPosition(ArmPosition.High, ()->ArmSide.Front, this::getHandMode)); 
        //_middleButton.onTrue(new GrpSetArmPosition(ArmPosition.Middle, this::getArmSide, this::getHandMode));
        _lowButton.onTrue(new GrpSetArmPosition(ArmPosition.Low, ()->ArmSide.Back, this::getHandMode));
        
        //_armSideSwitch.onTrue(flipSidesCommand);
        //_armSideSwitch.onFalse(flipSidesCommand);
    }

    public Command getAutonomousCommand() 
    {
        return Commands.print("No autonomous command configured");
    }

    public ArmSide getArmSide()
    {
        return _armSideSwitch.getAsBoolean() ? ArmSide.Front : ArmSide.Back;
    }

    public HandMode getHandMode()
    {
        return _handModeSwitch.getAsBoolean() ? HandMode.Cube : HandMode.Cone;
    }
}
