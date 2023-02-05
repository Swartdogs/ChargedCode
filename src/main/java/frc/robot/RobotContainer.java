package frc.robot;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdArmReset;
import frc.robot.commands.CmdArmSetExtensionPosition;
import frc.robot.commands.CmdArmSetShoulderAngle;
import frc.robot.commands.CmdAutoRotate;
//import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDriveResetEncoders;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveToPosition;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class RobotContainer 
{
    private Joystick _driveJoy;
    private JoystickButton _driveJoyButton1;
    private JoystickButton _driveJoyButton2;
    private JoystickButton _driveJoyButton3;
    private JoystickButton _driveJoyButton4;
    private JoystickButton _driveJoyButton5;
    //private JoystickButton _driveJoyButton6;
    private JoystickButton _driveJoyButton11;

    public RobotContainer()
    {
        _driveJoy = new Joystick(0);

        _driveJoyButton1 = new JoystickButton(_driveJoy, 1);
        _driveJoyButton2 = new JoystickButton(_driveJoy, 2);
        _driveJoyButton3 = new JoystickButton(_driveJoy, 3);
        _driveJoyButton4 = new JoystickButton(_driveJoy, 4);
        _driveJoyButton5 = new JoystickButton(_driveJoy, 5);
        //_driveJoyButton6 = new JoystickButton(_driveJoy, 6);
        _driveJoyButton11 = new JoystickButton(_driveJoy, 11);
        
        Dashboard.getInstance();
    
        // configureDefaultCommands();
        // configureBindings();

        _driveJoyButton1.onTrue(new CmdArmSetShoulderAngle(-120));
        _driveJoyButton2.onTrue(new CmdArmSetShoulderAngle(120));
        _driveJoyButton3.onTrue(new CmdArmSetExtensionPosition(45));
        _driveJoyButton4.onTrue(new CmdArmSetExtensionPosition(10));
        _driveJoyButton5.onTrue(new CmdArmReset());
    }

    private void configureDefaultCommands()
    {
        Drive.getInstance().setDefaultCommand
        (
            new CmdDriveWithJoystick
            (
                () -> getJoystickAxis(_driveJoy.getX(), false, false, 0.05), 
                () -> getJoystickAxis(_driveJoy.getY(), true,  false, 0.05), 
                () -> getJoystickAxis(_driveJoy.getZ(), false, true,  0.10), 
                () -> _driveJoy.getRawButton(1)
            )  
        );
    }

    private void configureBindings() 
    {
        _driveJoyButton1.onTrue
        (
            new CmdAutoRotate
            (
                90,  
                () -> getJoystickAxis(_driveJoy.getX(), false, false, 0.05), 
                () -> getJoystickAxis(_driveJoy.getY(), true,  false, 0.05),  
                () -> _driveJoy.getRawButton(1)
            )
        );
        _driveJoyButton2.onTrue(new CmdDriveResetOdometer());
        _driveJoyButton3.onTrue(new CmdDriveToPosition(new Vector(), 0));
        //_driveJoyButton6.onTrue(new CmdDriveBalance());
        _driveJoyButton11.onTrue(new CmdDriveResetEncoders());
    }

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

    public static void setSimulationCoefficients(PIDControl pidControl)
    {
        pidControl.setCoefficient(Coefficient.P, 0, 0.001, 0);
        pidControl.setCoefficient(Coefficient.I, 0, 0, 0);
        pidControl.setCoefficient(Coefficient.D, 0, 0, 0);
    }
}
