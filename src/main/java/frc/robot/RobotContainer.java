package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CmdArmModifyExtensionPosition;
import frc.robot.commands.CmdArmModifyShoulderAngle;
import frc.robot.commands.CmdArmSetExtensionPosition;
import frc.robot.commands.CmdArmSetShoulderAngle;
import frc.robot.commands.CmdAutoRotate;
import frc.robot.commands.CmdDriveResetEncoders;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdManipulatorPlaceGamePiece;
import frc.robot.commands.CmdManipulatorSetTwistAngle;
import frc.robot.commands.CmdManipulatorSetWristAngle;
import frc.robot.groups.GrpManipulatorHandFlip;
import frc.robot.groups.GrpPlaceGamePiece;
import frc.robot.groups.GrpIntakeGamePiece;
import frc.robot.groups.GrpSetArmPosition;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;
import frc.robot.subsystems.Manipulator.HandMode;
import frc.robot.subsystems.drive.Drive;

public class RobotContainer 
{
    private static RobotContainer _instance;

    public static RobotContainer getInstance()
    {
        if(_instance == null)
        {
            _instance = new RobotContainer();
        }

        return _instance;
    }

    private Joystick       _driveJoy;
    private Joystick       _buttonBox;

    private Trigger _buttonBoxHandmodeSwitch;
    private Trigger _buttonBoxArmSideSwitch;

    private JoystickButton _buttonBoxButton1;
    private JoystickButton _buttonBoxButton2;
    private JoystickButton _buttonBoxButton3;
    private JoystickButton _buttonBoxButton4;
    private JoystickButton _buttonBoxButton5;
    private JoystickButton _buttonBoxButton6;
    private JoystickButton _buttonBoxButton7;
    private JoystickButton _buttonBoxButton8;
    private JoystickButton _buttonBoxButton9;
    private JoystickButton _buttonBoxButton10;
    private JoystickButton _buttonBoxButton11;
    private JoystickButton _buttonBoxButton12;

    private JoystickButton _driveJoyButton1;
    private JoystickButton _driveJoyButton2;
    private JoystickButton _driveJoyButton7;
    private JoystickButton _driveJoyButton9;
    private JoystickButton _driveJoyButton11;

    private RobotContainer()
    {
        DriverStation.silenceJoystickConnectionWarning(true);

        RobotLog.getInstance();
        Drive.getInstance();
        Arm.getInstance();
        Manipulator.getInstance();
        //Dashboard.getInstance();

        _driveJoy  = new Joystick(0);
        _buttonBox = new Joystick(1);

        _driveJoyButton1   = new JoystickButton(_driveJoy, 1);
        _driveJoyButton2   = new JoystickButton(_driveJoy, 2);
        _driveJoyButton7   = new JoystickButton(_driveJoy, 7);
        _driveJoyButton9   = new JoystickButton(_driveJoy, 9);
        _driveJoyButton11  = new JoystickButton(_driveJoy, 11);

        _buttonBoxHandmodeSwitch = new Trigger (()-> _buttonBox.getRawAxis(0) < -0.5);
        _buttonBoxArmSideSwitch  = new Trigger(()-> _buttonBox.getRawAxis(1) < -0.5);

        _buttonBoxButton1  = new JoystickButton(_buttonBox, 1);
        _buttonBoxButton2  = new JoystickButton(_buttonBox, 2);
        _buttonBoxButton3  = new JoystickButton(_buttonBox, 3);
        _buttonBoxButton4  = new JoystickButton(_buttonBox, 4);
        _buttonBoxButton5  = new JoystickButton(_buttonBox, 5);
        _buttonBoxButton6  = new JoystickButton(_buttonBox, 6);
        _buttonBoxButton7  = new JoystickButton(_buttonBox, 7);
        _buttonBoxButton8  = new JoystickButton(_buttonBox, 8);
        _buttonBoxButton9  = new JoystickButton(_buttonBox, 9);
        _buttonBoxButton10 = new JoystickButton(_buttonBox, 10);
        _buttonBoxButton11 = new JoystickButton(_buttonBox, 11);
        _buttonBoxButton12 = new JoystickButton(_buttonBox, 12);

        // // Shoulder
        // _buttonBoxButton4.onTrue(new CmdArmSetShoulderAngle(90));
        // _buttonBoxButton8.onTrue(new CmdArmSetShoulderAngle(0));
        // _buttonBoxButton12.onTrue(new CmdArmSetShoulderAngle(-90));
        
        // // Extension
        // _buttonBoxButton3.onTrue(new CmdArmSetExtensionPosition(15));
        // _buttonBoxButton7.onTrue(new CmdArmSetExtensionPosition(8));
        // _buttonBoxButton11.onTrue(new CmdArmSetExtensionPosition(0));

        // // Wrist
        // _buttonBoxButton2.onTrue(new CmdManipulatorSetWristAngle(20));
        // _buttonBoxButton6.onTrue(new CmdManipulatorSetWristAngle(0));
        // _buttonBoxButton10.onTrue(new CmdManipulatorSetWristAngle(-20));

        // // Twist
        // _buttonBoxButton1.onTrue(new CmdManipulatorSetTwistAngle(-90));
        // _buttonBoxButton5.onTrue(new CmdManipulatorSetTwistAngle(0));
        // _buttonBoxButton9.onTrue(new CmdManipulatorSetTwistAngle(90));

        // var moveCommand = Commands.run(() -> Arm.getInstance().modifyExtensionMotorPosition(-0.1 * _driveJoy.getY()));

        // _driveJoyButton1.onTrue(moveCommand);
        // _driveJoyButton1.onFalse(Commands.runOnce(() -> moveCommand.cancel()));
       configureDefaultCommands();
       configureBindings();
    }

    private void configureDefaultCommands()
    {
        Drive.getInstance().setDefaultCommand(new CmdDriveWithJoystick());
    }

    private void configureBindings() 
    {
        CmdAutoRotate      driveAutoRotate90 = new CmdAutoRotate(90);
        // CmdDriveBalance    driveAutoBalance  = new CmdDriveBalance();

        _driveJoyButton2.onTrue
        (
            new InstantCommand(() -> 
            {
                driveAutoRotate90.cancel();
            })
        );
        //_driveJoyButton6.onTrue(new CmdDriveBalance());
        //_driveJoyButton7.onTrue(driveAutoRotate90);
        _driveJoyButton9.onTrue(new CmdDriveResetOdometer());
        _driveJoyButton11.onTrue(new CmdDriveResetEncoders());

        
        _buttonBoxButton1.onTrue(new CmdArmModifyExtensionPosition(3));
        _buttonBoxButton2.onTrue(new CmdArmModifyExtensionPosition(-3));
        _buttonBoxButton3.onTrue(new GrpSetArmPosition(ArmPosition.High)); 
        _buttonBoxButton4.onTrue(new GrpSetArmPosition(ArmPosition.Stow));
        _buttonBoxButton5.onTrue(new CmdArmModifyShoulderAngle(3));
        _buttonBoxButton6.onTrue(new CmdArmModifyShoulderAngle(-3));
        _buttonBoxButton7.onTrue(new GrpSetArmPosition(ArmPosition.Middle));
        _buttonBoxButton8.onTrue(new GrpIntakeGamePiece(ArmPosition.Substation));
        _buttonBoxButton9.onTrue(new GrpPlaceGamePiece());
        _buttonBoxButton10.onTrue(new GrpManipulatorHandFlip());
        _buttonBoxButton11.onTrue(new GrpSetArmPosition(ArmPosition.Low));
        _buttonBoxButton12.onTrue(new GrpIntakeGamePiece(ArmPosition.Ground));

        _buttonBoxHandmodeSwitch.onTrue(new ProxyCommand(()-> new GrpSetArmPosition(Arm.getInstance().getArmPosition())));
        _buttonBoxHandmodeSwitch.onFalse(new ProxyCommand(()-> new GrpSetArmPosition(Arm.getInstance().getArmPosition())));
        
        _buttonBoxArmSideSwitch.onTrue(new GrpSetArmPosition(ArmPosition.Stow));
        _buttonBoxArmSideSwitch.onFalse(new GrpSetArmPosition(ArmPosition.Stow));
    }

    public Command getAutonomousCommand() 
    {
        return Commands.print("No autonomous command configured");
    }

    public ArmSide getArmSide()
    {
        return _buttonBoxArmSideSwitch.getAsBoolean() ? ArmSide.Front : ArmSide.Back;
    }

    public HandMode getHandMode()
    {
        return _buttonBoxHandmodeSwitch.getAsBoolean() ? HandMode.Cube : HandMode.Cone;
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

    public double getDriveJoyX()
    {
        return getJoystickAxis(_driveJoy.getX(), false, false, 0.05);
    }

    public double getDriveJoyY()
    {
        return getJoystickAxis(_driveJoy.getY(), true, false, 0.05);
    }

    public double getDriveJoyZ()
    {
        return getJoystickAxis(_driveJoy.getZ(), false, true, 0.1);
    }

    public boolean driveIsRobotCentric()
    {
        return _driveJoy.getRawButton(1);
    }
}
