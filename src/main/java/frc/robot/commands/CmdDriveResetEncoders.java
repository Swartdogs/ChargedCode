package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveResetEncoders extends CommandBase 
{

    public CmdDriveResetEncoders()
    {
        
    }

    @Override
    public void initialize()
    {
        Drive.getInstance().zeroModuleRotations();
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
