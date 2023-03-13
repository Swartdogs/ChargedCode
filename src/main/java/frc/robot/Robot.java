package frc.robot;

import com.revrobotics.REVPhysicsSim;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.CmdLEDDripSolidColor;
import frc.robot.commands.CmdLEDWaterfallSolidColor;
import frc.robot.commands.CmdLedAutonomous;
import frc.robot.commands.CmdLedDisabled;
import frc.robot.commands.CmdLedTeleop;
import frc.robot.subsystems.Led;

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
        Led.getInstance().switchDefaultCommand(new CmdLedDisabled());

        new CmdLEDDripSolidColor(Constants.Led.ORANGE).schedule();
    }
  
    @Override
    public void autonomousInit() 
    {
        _autonomousCommand = _robotContainer.getAutonomousCommand();

        if (_autonomousCommand != null) 
        {
            _autonomousCommand.schedule();
        }

        Led.getInstance().switchDefaultCommand(new CmdLedAutonomous());

        Color c;

        switch(DriverStation.getAlliance())
        {
            case Red:
                c = Constants.Led.RED;
                break;

            case Blue:
                c = Constants.Led.BLUE;
                break;

            default:
                c = Constants.Led.GREEN;
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

        Led.getInstance().switchDefaultCommand(new CmdLedTeleop());
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
