package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CmdAutoRotate;
import frc.robot.commands.CmdDriveWithJoystick;
import frc.robot.commands.CmdDriveResetEncoders;
import frc.robot.commands.CmdDriveResetOdometer;
import frc.robot.commands.CmdDriveToPosition;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class RobotContainer 
{
    private Joystick       _driveJoy;
    private JoystickButton _driveJoyButton2;
    private JoystickButton _driveJoyButton3;
    //private JoystickButton _driveJoyButton6;
    private JoystickButton _driveJoyButton9;
    private JoystickButton _driveJoyButton11;

    private JoystickButton _driveJoyButton7;

    public RobotContainer()
    {
        _driveJoy         = new Joystick(0);

        _driveJoyButton2  = new JoystickButton(_driveJoy, 2);
        _driveJoyButton3  = new JoystickButton(_driveJoy, 3);
        //_driveJoyButton6 = new JoystickButton(_driveJoy, 6);
        _driveJoyButton9  = new JoystickButton(_driveJoy, 9);
        _driveJoyButton11 = new JoystickButton(_driveJoy, 11);
        
        Dashboard.getInstance();
    
        configureDefaultCommands();
        configureBindings();
    }

    private void configureDefaultCommands()
    {
        Drive.getInstance().setDefaultCommand
        (
            new CmdDriveWithJoystick
            (
                this::getDriveJoyX,
                this::getDriveJoyY,
                this::getDriveJoyZ,
                this::driveIsRobotCentric
            )  
        );
    }

    private void configureBindings() 
    {
        CmdDriveToPosition driveToPosition   = new CmdDriveToPosition(new Vector(), 0);
        CmdAutoRotate      driveAutoRotate90 = new CmdAutoRotate(90, this::getDriveJoyX, this::getDriveJoyY, this::driveIsRobotCentric);
        //CmdDriveBalance    driveAutoBalance  = new CmdDriveBalance();

        _driveJoyButton2.onTrue(new CmdDriveResetOdometer());
        _driveJoyButton3.onTrue(driveToPosition);
        //_driveJoyButton6.onTrue(new CmdDriveBalance());
        _driveJoyButton7.onTrue(driveAutoRotate90);
        _driveJoyButton9.onTrue
        (
            new InstantCommand(() -> 
            {
                driveToPosition.cancel();
                driveAutoRotate90.cancel();
            })
        );
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

    private double getDriveJoyX()
    {
        return getJoystickAxis(_driveJoy.getX(), false, false, 0.05);
    }

    private double getDriveJoyY()
    {
        return getJoystickAxis(_driveJoy.getY(), true, false, 0.05);
    }

    private double getDriveJoyZ()
    {
        return getJoystickAxis(_driveJoy.getZ(), false, true, 0.1);
    }

    private boolean driveIsRobotCentric()
    {
        return _driveJoy.getRawButton(1);
    }
}
