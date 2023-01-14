package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Dashboard;

public class RobotContainer 
{
    public RobotContainer() 
    {
        configureBindings();
    }

    private void configureBindings() {
        var D = new Dashboard();
    }

    public Command getAutonomousCommand() 
    {
        return Commands.print("No autonomous command configured");
    }
}
