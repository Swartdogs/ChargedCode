package frc.robot.commands;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveBalance extends CommandBase 
{
    private final double BALANCE_TIME = 0.22;
    private final double REVERSE_PITCH_THRESHOLD = 10;
    private final double BALANCED_PITCH_THRESHOLD = 3;

    private PIDControl _pitchPID;
    private int        _timer;

    public CmdDriveBalance() 
    {
        _pitchPID = new PIDControl();
        _pitchPID.setCoefficient(Coefficient.P, 11, 0.005, 0);
        _pitchPID.setCoefficient(Coefficient.I, 4,   0,    0.005);
        _pitchPID.setCoefficient(Coefficient.D, 0,   0,    0);
        _pitchPID.setInputRange(-20, 20);
        _pitchPID.setOutputRange(-0.1, 0.1);
        _pitchPID.setSetpointDeadband(2);

        addRequirements(Drive.getInstance());
    }

    @Override
    public void initialize() 
    {
        _pitchPID.setSetpoint(0);
        _timer = 0;
        Drive.getInstance().setBrakeMode(true);
    }

    @Override
    public void execute() 
    {
        var pitch     = Drive.getInstance().getChassisPitch();
        var direction = Math.signum(pitch);
        var speed     = 0.0;

        if (Math.abs(pitch) < BALANCED_PITCH_THRESHOLD)
        {
            _timer++;
            speed = 0;
        }
        else if (Math.abs(pitch) < REVERSE_PITCH_THRESHOLD)
        {
            _timer++;
            speed = 0.1 * direction;
        }
        else
        {
            _timer = 0;
            speed = -0.15 * direction;
        }

        System.out.println(pitch);

        Drive.getInstance().chassisDrive(speed, 0, 0);
    }

    @Override
    public void end(boolean interrupted) 
    {
        Drive.getInstance().chassisDrive(0, 0, 0);
        Drive.getInstance().setBrakeMode(false);
    }

    @Override
    public boolean isFinished()
    {
        return _timer >= (int)(BALANCE_TIME * Constants.LOOPS_PER_SECOND);
    }
}