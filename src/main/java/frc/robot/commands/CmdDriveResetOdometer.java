package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveResetOdometer extends CommandBase 
{

    public CmdDriveResetOdometer()
    {
        
    }

    @Override
    public void initialize()
    {
        Drive.getInstance().resetOdometer();
        Drive.getInstance().setGyro(0);
    }

    @Override
    public boolean isFinished() 
    {
        return true;
    }

    @Override
    public boolean runsWhenDisabled()
    {
        return true;
    }

}
