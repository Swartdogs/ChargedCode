package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdAutoRotate;
//import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDriveResetEncoders;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveToPosition;

import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class RobotContainer 
{
    private Joystick _driveJoy;
    private JoystickButton _driveJoyButton1;
    private JoystickButton _driveJoyButton2;
    private JoystickButton _driveJoyButton3;
    //private JoystickButton _driveJoyButton6;
    private JoystickButton _driveJoyButton11;


    public RobotContainer()
    {
        _driveJoy = new Joystick(0);

        _driveJoyButton1 = new JoystickButton(_driveJoy, 1);
        _driveJoyButton2 = new JoystickButton(_driveJoy, 2);
        _driveJoyButton3 = new JoystickButton(_driveJoy, 3);
        //_driveJoyButton6 = new JoystickButton(_driveJoy, 6);
        _driveJoyButton11 = new JoystickButton(_driveJoy, 11);
    
        configureDefaultCommands();
        configureBindings();
    }

    private void configureDefaultCommands()
    {
        Drive.getInstance().setDefaultCommand
        (
            new CmdDriveWithJoystick
            (
                () -> getJoystickAxis(_driveJoy::getX, false, false, 0.05), 
                () -> getJoystickAxis(_driveJoy::getY, true,  false, 0.05), 
                () -> getJoystickAxis(_driveJoy::getZ, false, true,  0.10), 
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
                () -> getJoystickAxis(_driveJoy::getX, false, false, 0.05), 
                () -> getJoystickAxis(_driveJoy::getY, true,  false, 0.05),  
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

    private double getJoystickAxis(DoubleSupplier axis, boolean inverted, boolean squareInput, double deadband)
    {
        double output = axis.getAsDouble();

        if(inverted)
        {
            output *= -1;
        }

        if (squareInput)
        {
            output *= output * Math.signum(output);
        }

        output = applyDeadband(output, deadband);

        return output;
    }
}
