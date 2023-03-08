package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveStrafeWithJoystick extends DriveCommand
{
    private Drive _drive = Drive.getInstance(); 

    public CmdDriveStrafeWithJoystick()
    {
        _rotatePID.setInputRange(0.0, 180.0);

        addRequirements(_drive);
    }

    @Override
    public void initialize() 
    {
        _rotatePID.setSetpoint(_drive.getAllianceAngle(), true);
    }

    @Override
    public void execute()
    {
        // get our inputs from the joystick
        double x = RobotContainer.getInstance().getDriveJoyX();
        double y = 0.0;
        double r = _rotatePID.calculate(_drive.getHeading());

        
        // actually drive the robot
        
        _drive.driverDrive(x, y, r);
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
