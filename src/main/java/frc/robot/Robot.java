package frc.robot;

import com.revrobotics.REVPhysicsSim;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot 
{
    private Command _autonomousCommand;

    private RobotContainer _robotContainer;

    @Override
    public void robotInit() 
    {
        _robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() 
    {
        CommandScheduler.getInstance().run();
    }

  
    @Override
    public void autonomousInit() 
    {
        _autonomousCommand = _robotContainer.getAutonomousCommand();

        if (_autonomousCommand != null) 
        {
            _autonomousCommand.schedule();
        }
    }

    @Override
    public void teleopInit() 
    {
        if (_autonomousCommand != null) 
        {
            _autonomousCommand.cancel();
        }
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
