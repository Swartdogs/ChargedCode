package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.RobotLog;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveResetEncoders extends InstantCommand 
{
    @Override
    public void initialize()
    {
        Drive.getInstance().zeroModuleRotations();

        RobotLog.getInstance().log("Reset Encoders");
    }

    @Override
    public boolean runsWhenDisabled()
    {
        return true;
    }
}
