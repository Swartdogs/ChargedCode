package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveWithJoystick extends CommandBase
{
    private Drive _drive = Drive.getInstance(); 

    public CmdDriveWithJoystick()
    {
        addRequirements(_drive);
    }

    @Override
    public void execute()
    {
        // get our inputs from the joystick
        double x = RobotContainer.getInstance().getDriveJoyX();
        double y = RobotContainer.getInstance().getDriveJoyY();
        double r = RobotContainer.getInstance().getDriveJoyZ();

        boolean robotCentric =  RobotContainer.getInstance().driveIsRobotCentric();
        
        // actually drive the robot
        if (robotCentric)
        {
            _drive.chassisDrive(y, x, r);
        }
        else
        {
            _drive.fieldDrive(x, y, r);
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        _drive.chassisDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
