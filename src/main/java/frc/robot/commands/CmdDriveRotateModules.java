package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveRotateModules extends CommandBase 
{
    private double _angle;

    public CmdDriveRotateModules(double angle) 
    {
        _angle = angle;
        addRequirements(Drive.getInstance());
    }

    @Override
    public void execute() 
    {
        Drive.getInstance().rotateModules(_angle);
    }

    @Override
    public void end(boolean interrupted) 
    {
        Drive.getInstance().chassisDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
