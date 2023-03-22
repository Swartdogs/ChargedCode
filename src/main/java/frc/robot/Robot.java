package frc.robot;

import com.revrobotics.REVPhysicsSim;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.CmdLEDDripSolidColor;
import frc.robot.commands.CmdLEDWaterfallSolidColor;
import frc.robot.commands.CmdLEDAutonomous;
import frc.robot.commands.CmdLEDDisabled;
import frc.robot.commands.CmdLEDTeleop;
import frc.robot.commands.CmdLEDTeleopInit;
import frc.robot.subsystems.LED;

public class Robot extends TimedRobot 
{
    private Command _autonomousCommand;

    private RobotContainer _robotContainer;

    @Override
    public void robotInit() 
    {
        _robotContainer = RobotContainer.getInstance();
    }

    @Override
    public void robotPeriodic()
    {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit()
    {
        LED.getInstance().switchDefaultCommand(new CmdLEDDisabled());

        new CmdLEDDripSolidColor(Constants.LED.ORANGE).schedule();
    }
  
    @Override
    public void autonomousInit() 
    {
        _autonomousCommand = _robotContainer.getAutonomousCommand();

        if (_autonomousCommand != null) 
        {
            _autonomousCommand.schedule();
        }

        LED.getInstance().switchDefaultCommand(new CmdLEDAutonomous());

        Color c;

        switch(DriverStation.getAlliance())
        {
            case Red:
                c = Constants.LED.RED;
                break;

            case Blue:
                c = Constants.LED.BLUE;
                break;

            default:
                c = Constants.LED.GREEN;
                break;
        }

        new CmdLEDWaterfallSolidColor(c).schedule();
    }

    @Override
    public void teleopInit() 
    {
        if (_autonomousCommand != null) 
        {
            _autonomousCommand.cancel();
        }

        LED.getInstance().switchDefaultCommand(new CmdLEDTeleop());
        new CmdLEDTeleopInit().schedule();
    }

    @Override
    public void testInit() 
    {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void simulationPeriodic()
    {
        REVPhysicsSim.getInstance().run();
    }
}
