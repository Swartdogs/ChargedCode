package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.RobotLog;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveResetOdometer extends InstantCommand 
{
    @Override
    public void initialize()
    {
        Drive.getInstance().resetOdometer();
        Drive.getInstance().setGyro(0);

        RobotLog.getInstance().log("Reset Odometer");
    }

    @Override
    public boolean runsWhenDisabled()
    {
        return true;
    }

}
