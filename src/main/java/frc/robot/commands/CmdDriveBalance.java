package frc.robot.commands;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveBalance extends CommandBase 
{
    private PIDControl _pitchPID;

    public CmdDriveBalance() 
    {
        _pitchPID = new PIDControl();
        _pitchPID.setCoefficient(Coefficient.P, 9.5, 0.05, 0);
        _pitchPID.setCoefficient(Coefficient.I, 0,   0,    0);
        _pitchPID.setCoefficient(Coefficient.D, 0,   0,    0);
        _pitchPID.setInputRange(-20, 20);
        _pitchPID.setOutputRange(-0.1, 0.1);
        _pitchPID.setSetpointDeadband(2);

        addRequirements(Drive.getInstance());
    }

    @Override
    public void initialize() 
    {
        _pitchPID.setSetpoint(0, Drive.getInstance().getChassisPitch());
    }

    @Override
    public void execute() 
    {
        Drive.getInstance().chassisDrive(_pitchPID.calculate(Drive.getInstance().getChassisPitch()), 0, 0);
    }

    @Override
    public void end(boolean interrupted) 
    {
        Drive.getInstance().chassisDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished()
    {
        return _pitchPID.atSetpoint();
    }
}