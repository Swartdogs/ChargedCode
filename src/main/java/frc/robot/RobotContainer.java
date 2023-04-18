package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CmdAutoRotate;
import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveRotateModules;
import frc.robot.commands.CmdDriveStrafeWithJoystick;
import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdLEDChangeHandMode;
import frc.robot.commands.CmdLEDCycleWave;
import frc.robot.commands.CmdLEDTeleopSwapSides;
import frc.robot.commands.CmdArmAdjustContinuous;
import frc.robot.commands.CmdArmSetPosition;
import frc.robot.commands.CmdLEDWaterfallSolidColor;
import frc.robot.groups.GrpPlaceGamePiece;
import frc.robot.groups.GrpAutonomous;
import frc.robot.groups.GrpIntakeGamePiece;
import frc.robot.groups.GrpSetArmPosition;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;
import frc.robot.subsystems.Arm.HandMode;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

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

    public enum Controller
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

    private Command _auto;

    private RobotContainer()
    {
        DriverStation.silenceJoystickConnectionWarning(true);

        RobotLog.getInstance();
        Drive.getInstance();
        Arm.getInstance();
        Manipulator.getInstance();
        LED.getInstance();
        Dashboard.getInstance();

        _auto = new GrpAutonomous();

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

        CmdArmAdjustContinuous operatorHeightAdjustment = new CmdArmAdjustContinuous(()-> 0.0, ()-> getOperatorY() * Constants.Arm.HEIGHT_JOYSTICK_RATE, ()-> 0.0, ()-> 0.0);
        CmdArmAdjustContinuous operatorReachAdjustment  = new CmdArmAdjustContinuous(()-> getOperatorY() * Constants.Arm.REACH_JOYSTICK_RATE, ()-> 0.0, ()-> 0.0, ()-> 0.0);
        CmdArmAdjustContinuous operatorWristAdjustment  = new CmdArmAdjustContinuous(()-> 0.0, ()-> 0.0, ()-> getOperatorY() * Constants.Arm.WRIST_JOYSTICK_RATE, ()-> 0.0);

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

        Controller.OperatorJoystick.button( 1).onTrue(operatorHeightAdjustment);
        Controller.OperatorJoystick.button( 1).onFalse(Commands.runOnce(operatorHeightAdjustment::cancel));
        Controller.OperatorJoystick.button( 2).onTrue(Commands.runOnce(Arm.getInstance()::flipHand));
        Controller.OperatorJoystick.button( 3).onTrue(operatorReachAdjustment);
        Controller.OperatorJoystick.button( 3).onFalse(Commands.runOnce(operatorReachAdjustment::cancel));
        Controller.OperatorJoystick.button( 4).onTrue(operatorWristAdjustment);
        Controller.OperatorJoystick.button( 4).onFalse(Commands.runOnce(operatorWristAdjustment::cancel));
        Controller.OperatorJoystick.button( 5).onTrue(new CmdLEDWaterfallSolidColor(new Color(255, 0, 0)).ignoringDisable(true));
        Controller.OperatorJoystick.button( 6).onTrue(new CmdLEDWaterfallSolidColor(new Color(0, 0, 255)).ignoringDisable(true));
        Controller.OperatorJoystick.button(11).toggleOnTrue(new CmdLEDCycleWave(Constants.LED.YELLOW, Constants.LED.PINK, Constants.LED.BLUE).repeatedly());
        Controller.OperatorJoystick.button(12).toggleOnTrue(new CmdLEDCycleWave(Constants.LED.GREEN, Constants.LED.PINK, Constants.LED.PURPLE).repeatedly());
        Controller.OperatorJoystick.button(10).onTrue(Commands.runOnce(()->{ Manipulator.getInstance().setGamePiece(!Manipulator.getInstance().hasGamePiece()); }));

        Controller.ButtonBox.button( 1).onTrue(new GrpPlaceGamePiece());
        Controller.ButtonBox.button( 2).onTrue(intakeAdjustment);
        Controller.ButtonBox.button( 2).onFalse(Commands.runOnce(intakeAdjustment::cancel));
        Controller.ButtonBox.button( 3).onTrue(new GrpSetArmPosition(ArmPosition.High)); 
        Controller.ButtonBox.button( 4).onTrue(new GrpSetArmPosition(ArmPosition.Stow));
        Controller.ButtonBox.button( 5).onTrue(new CmdArmSetPosition(new Vector(3.0, 0.0), 0.0, 0.0, Constants.Arm.ADJUST_MOTION_RATE, false));
        Controller.ButtonBox.button( 6).onTrue(new CmdArmSetPosition(new Vector(0.0, 3.0), 0.0, 0.0, Constants.Arm.ADJUST_MOTION_RATE, false));
        Controller.ButtonBox.button( 7).onTrue(new GrpSetArmPosition(ArmPosition.Middle));
        Controller.ButtonBox.button( 8).onTrue(new GrpIntakeGamePiece(ArmPosition.Substation));
        Controller.ButtonBox.button( 9).onTrue(new CmdArmSetPosition(new Vector(-3.0,  0.0), 0.0, 0.0, Constants.Arm.ADJUST_MOTION_RATE, false));
        Controller.ButtonBox.button(10).onTrue(new CmdArmSetPosition(new Vector( 0.0, -3.0), 0.0, 0.0, Constants.Arm.ADJUST_MOTION_RATE, false));
        Controller.ButtonBox.button(11).onTrue(new GrpSetArmPosition(ArmPosition.Low));
        Controller.ButtonBox.button(12).onTrue(new GrpIntakeGamePiece(ArmPosition.Ground));

        _buttonBoxHandmodeSwitch.onTrue(new ProxyCommand(()-> new GrpSetArmPosition(Arm.getInstance().getTargetArmPreset())));
        _buttonBoxHandmodeSwitch.onFalse(new ProxyCommand(()-> new GrpSetArmPosition(Arm.getInstance().getTargetArmPreset())));
        _buttonBoxHandmodeSwitch.onTrue(new CmdLEDChangeHandMode());
        _buttonBoxHandmodeSwitch.onFalse(new CmdLEDChangeHandMode());
        
        _buttonBoxArmSideSwitch.onTrue(new GrpSetArmPosition(ArmPosition.Stow));
        _buttonBoxArmSideSwitch.onFalse(new GrpSetArmPosition(ArmPosition.Stow));
        _buttonBoxArmSideSwitch.onTrue(new CmdLEDTeleopSwapSides());
        _buttonBoxArmSideSwitch.onFalse(new CmdLEDTeleopSwapSides()); 
    }

    public Command getAutonomousCommand() 
    {
        return _auto;
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
        return getJoystickAxis(Controller.DriveJoystick.joystick().getZ(), false, 4, 0.05) * 0.8;  // for sean
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
