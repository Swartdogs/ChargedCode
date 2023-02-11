package frc.robot;

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
import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdManipulatorPlaceGamePiece;
import frc.robot.groups.GrpManipulatorHandFlip;
import frc.robot.groups.GrpIntakeGamePiece;
import frc.robot.groups.GrpSetArmPosition;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Manipulator;
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

    private Trigger _buttonBoxArmSideSwitch;
    private Trigger _buttonBoxHandmodeSwitch;

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

    private JoystickButton _driveJoyButton7;

    private RobotContainer()
    {
        DriverStation.silenceJoystickConnectionWarning(true);

        Manipulator.getInstance();
        Dashboard.getInstance();

        _driveJoy  = new Joystick(0);
        _buttonBox = new Joystick(1);

        _driveJoyButton7   = new JoystickButton(_driveJoy, 7);

        _buttonBoxArmSideSwitch  = new Trigger(()-> _buttonBox.getRawAxis(0) > 0.5);
        _buttonBoxHandmodeSwitch = new Trigger (()-> _buttonBox.getRawAxis(1) < -0.5);

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
        
        //configureDefaultCommands();
        configureBindings();
    }

    private void configureDefaultCommands()
    {
        Drive.getInstance().setDefaultCommand(new CmdDriveWithJoystick());
    }

    private void configureBindings() 
    {
        _buttonBoxButton1.onTrue(new GrpManipulatorHandFlip());
        
        _buttonBoxButton2.onTrue(new GrpSetArmPosition(ArmPosition.Stow));
        _buttonBoxButton3.onTrue(new GrpSetArmPosition(ArmPosition.High)); 
        _buttonBoxButton4.onTrue(new GrpSetArmPosition(ArmPosition.Middle));
        _buttonBoxButton5.onTrue(new GrpSetArmPosition(ArmPosition.Low));

        _buttonBoxButton6.onTrue(new GrpIntakeGamePiece(ArmPosition.Substation));
        _buttonBoxButton7.onTrue(new GrpIntakeGamePiece(ArmPosition.Ground));
    
        _buttonBoxButton9.onTrue(new CmdArmModifyExtensionPosition(3));
        _buttonBoxButton10.onTrue(new CmdArmModifyExtensionPosition(-3));
        _buttonBoxButton11.onTrue(new CmdArmModifyShoulderAngle(3));
        _buttonBoxButton12.onTrue(new CmdArmModifyShoulderAngle(-3));

        //_driveJoyButton7.onTrue(new CmdAutoRotate(90));
        _buttonBoxButton8.onTrue(new CmdManipulatorPlaceGamePiece());

        _buttonBoxArmSideSwitch.onTrue(new GrpSetArmPosition(ArmPosition.Stow));
        _buttonBoxArmSideSwitch.onFalse(new GrpSetArmPosition(ArmPosition.Stow));

        _buttonBoxHandmodeSwitch.onTrue(new ProxyCommand(()-> new GrpSetArmPosition(Arm.getInstance().getArmPosition())));
        _buttonBoxHandmodeSwitch.onFalse(new ProxyCommand(()-> new GrpSetArmPosition(Arm.getInstance().getArmPosition())));
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
