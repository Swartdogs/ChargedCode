package frc.robot;

import com.revrobotics.REVPhysicsSim;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RobotLog;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drive.Drive;

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
    public void testPeriodic()
    {
        // Tuning PID controllers requires the dashboard to run its periodic
        Dashboard.getInstance().periodic();

        // During simulation, the other subsystems need to run their simulation periodics
        // to ensure sensor values are being updated correctly. Entering test mode disables
        // the CommandScheduler, so we need to explicitly call the periodics
        //
        // We do NOT need to call the subsystem periodics because motor motion will be
        // handled by the dashboard, not the parent subsystem of the motor
        if (RobotBase.isSimulation())
        {
            Drive.getInstance().simulationPeriodic();
            Arm.getInstance().simulationPeriodic();
            Manipulator.getInstance().simulationPeriodic();
            Vision.getInstance().simulationPeriodic();
            RobotLog.getInstance().simulationPeriodic();
        }
    }

    @Override
    public void simulationPeriodic()
    {
        REVPhysicsSim.getInstance().run();
    }
}
