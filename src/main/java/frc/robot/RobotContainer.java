package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CmdArmModifyExtensionPosition;
import frc.robot.commands.CmdArmModifyShoulderAngle;
import frc.robot.commands.CmdAutoRotate;
import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveRotateModules;
import frc.robot.commands.CmdDriveStrafeWithJoystick;
import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdManipulatorModifyWrist;
import frc.robot.groups.GrpManipulatorHandFlip;
import frc.robot.groups.GrpPlaceGamePiece;
import frc.robot.groups.GrpAutonomous;
import frc.robot.groups.GrpIntakeGamePiece;
import frc.robot.groups.GrpSetArmPosition;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Led;
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

    private enum Controller
    {
        DriveJoystick(0),
        OperatorJoystick(1),
        ButtonBox(2);

        private Joystick                  _joystick;
        private ArrayList<JoystickButton> _buttons;

        private Controller(int port)
        {
            _joystick = new Joystick(port);
            _buttons = new ArrayList<JoystickButton>();

            for (int i = 0; i < 12; i++)
            {
                _buttons.add(new JoystickButton(_joystick, i + 1));
            }
        }

        public Joystick joystick()
        {
            return _joystick;
        }

        public JoystickButton button(int button)
        {
            return _buttons.get(button - 1);
        }
    }

    private Trigger _buttonBoxHandmodeSwitch;
    private Trigger _buttonBoxArmSideSwitch;

    private RobotContainer()
    {
        DriverStation.silenceJoystickConnectionWarning(true);

        RobotLog.getInstance();
        Drive.getInstance();
        Arm.getInstance();
        Manipulator.getInstance();
        Dashboard.getInstance();
        Led.getInstance();

        _buttonBoxHandmodeSwitch = new Trigger (()-> Controller.ButtonBox.joystick().getRawAxis(0) < -0.5);
        _buttonBoxArmSideSwitch  = new Trigger(()-> Controller.ButtonBox.joystick().getRawAxis(1) < -0.5);

       configureDefaultCommands();
       configureBindings();
    }

    private void configureDefaultCommands()
    {
        Drive.getInstance().setDefaultCommand(new CmdDriveWithJoystick());
    }

    private void configureBindings() 
    {
        CmdDriveStrafeWithJoystick driveStrafeWithJoystick = new CmdDriveStrafeWithJoystick();
        CmdAutoRotate              driveAutoRotate90       = new CmdAutoRotate(90, false);
        CmdDriveRotateModules      driveRotateModules      = new CmdDriveRotateModules(90);

        CmdArmModifyShoulderAngle     operatorShoulderAdjustment   = new CmdArmModifyShoulderAngle();
        CmdArmModifyExtensionPosition operatorExtenstionAdjustment = new CmdArmModifyExtensionPosition();
        CmdManipulatorModifyWrist     operatorWristAdjustment      = new CmdManipulatorModifyWrist();

        Command driveAutoBalance  = new CmdDriveBalance().andThen(Commands.run(() -> Drive.getInstance().rotateModules(90)));
        Command intakeAdjustment  = Commands.startEnd(Manipulator.getInstance()::enableIntake, Manipulator.getInstance()::disableIntake);

        Controller.DriveJoystick.button(2).onTrue
        (
            Commands.runOnce(() -> 
            {
                driveAutoRotate90.cancel();
                driveAutoBalance.cancel();
            })
        );

        Controller.DriveJoystick.button( 3).onTrue(driveStrafeWithJoystick);
        Controller.DriveJoystick.button( 3).onFalse(Commands.runOnce(driveStrafeWithJoystick::cancel));
        Controller.DriveJoystick.button( 4).onTrue(driveAutoRotate90);
        Controller.DriveJoystick.button( 7).onTrue(driveRotateModules);
        Controller.DriveJoystick.button( 7).onFalse(Commands.runOnce(driveRotateModules::cancel));
        Controller.DriveJoystick.button( 8).onTrue(driveAutoBalance);
        Controller.DriveJoystick.button( 8).onFalse(Commands.runOnce(driveAutoBalance::cancel));
        Controller.DriveJoystick.button(11).onTrue(new CmdDriveResetOdometer());

        Controller.OperatorJoystick.button( 1).onTrue(operatorShoulderAdjustment);
        Controller.OperatorJoystick.button( 1).onFalse(Commands.runOnce(operatorShoulderAdjustment::cancel));
        Controller.OperatorJoystick.button( 2).onTrue(new GrpManipulatorHandFlip());
        Controller.OperatorJoystick.button( 3).onTrue(operatorExtenstionAdjustment);
        Controller.OperatorJoystick.button( 3).onFalse(Commands.runOnce(operatorExtenstionAdjustment::cancel));
        Controller.OperatorJoystick.button( 4).onTrue(operatorWristAdjustment);
        Controller.OperatorJoystick.button( 4).onFalse(Commands.runOnce(operatorWristAdjustment::cancel));
        Controller.OperatorJoystick.button(11).toggleOnTrue(Manipulator.getInstance().printWristHealthCommand());
        Controller.OperatorJoystick.button(12).onTrue(Commands.runOnce(Manipulator.getInstance()::overrideWrist));
        
        Controller.ButtonBox.button( 1).onTrue(new GrpPlaceGamePiece());
        Controller.ButtonBox.button( 2).onTrue(intakeAdjustment);
        Controller.ButtonBox.button( 2).onFalse(Commands.runOnce(intakeAdjustment::cancel));
        Controller.ButtonBox.button( 3).onTrue(new GrpSetArmPosition(ArmPosition.High)); 
        Controller.ButtonBox.button( 4).onTrue(new GrpSetArmPosition(ArmPosition.Stow));
        Controller.ButtonBox.button( 5).onTrue(new CmdArmModifyExtensionPosition(3));
        Controller.ButtonBox.button( 6).onTrue(new CmdArmModifyShoulderAngle(-3));
        Controller.ButtonBox.button( 7).onTrue(new GrpSetArmPosition(ArmPosition.Middle));
        Controller.ButtonBox.button( 8).onTrue(new GrpIntakeGamePiece(ArmPosition.Substation));
        Controller.ButtonBox.button( 9).onTrue(new CmdArmModifyExtensionPosition(-3));
        Controller.ButtonBox.button(10).onTrue(new CmdArmModifyShoulderAngle(3));
        Controller.ButtonBox.button(11).onTrue(new GrpSetArmPosition(ArmPosition.Low));
        Controller.ButtonBox.button(12).onTrue(new GrpIntakeGamePiece(ArmPosition.Ground));

        _buttonBoxHandmodeSwitch.onTrue(new ProxyCommand(()-> new GrpSetArmPosition(Arm.getInstance().getArmPosition())));
        _buttonBoxHandmodeSwitch.onFalse(new ProxyCommand(()-> new GrpSetArmPosition(Arm.getInstance().getArmPosition())));
        
        _buttonBoxArmSideSwitch.onTrue(new GrpSetArmPosition(ArmPosition.Stow));
        _buttonBoxArmSideSwitch.onFalse(new GrpSetArmPosition(ArmPosition.Stow));
    }

    public Command getAutonomousCommand() 
    {
        return new GrpAutonomous();
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

    private double getJoystickAxis(double input, boolean inverted, int inputExponent, double deadband)
    {
        if(inverted)
        {
            input *= -1;
        }

        input = Math.pow(Math.abs(input), inputExponent) * Math.signum(input);

        return applyDeadband(input, deadband);
    }

    public double getDriveJoyX()
    {
        return getJoystickAxis(Controller.DriveJoystick.joystick().getX(), false, 1, 0.05);
    }

    public double getDriveJoyY()
    {
        return getJoystickAxis(Controller.DriveJoystick.joystick().getY(), true, 1, 0.05);
    }

    public double getDriveJoyZ()
    {
        return getJoystickAxis(Controller.DriveJoystick.joystick().getZ(), false, 4, 0.1);
    }

    public boolean driveIsRobotCentric()
    {
        return Controller.DriveJoystick.button(1).getAsBoolean();
    }

    public double getOperatorY()
    {
        return getJoystickAxis(Controller.OperatorJoystick.joystick().getY(), true, 1, 0.05);
    }
}
