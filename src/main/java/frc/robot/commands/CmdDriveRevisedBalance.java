package frc.robot.commands;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveRevisedBalance extends CommandBase 
{
    private PIDControl _velocityPID;

    public CmdDriveRevisedBalance() 
    {
        _velocityPID = new PIDControl();

        _velocityPID.setCoefficient(Coefficient.P, 0.0, 0.0065, 0.0);
        _velocityPID.setCoefficient(Coefficient.I, 0.0, 0.0, 0.0);
        _velocityPID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);
        _velocityPID.setInputRange(-300, 300);
        _velocityPID.setOutputRange(-0.3, 0.3);
        _velocityPID.setOutputRamp(0.05, 0.01);

        addRequirements(Drive.getInstance());
    }

    @Override
    public void initialize() 
    {

    }

    @Override
    public void execute() 
    {
        double chassisPitch    = Drive.getInstance().getChassisPitch();
        double chassisVelocity = Drive.getInstance().getChassisVelocity().getY();

        if (Math.abs(chassisPitch) > 5.0)
        {
            _velocityPID.setSetpoint(Math.signum(chassisPitch) * -10.0, chassisVelocity);
        }
        else if (Math.abs(chassisPitch) > 2.0)
        {
            _velocityPID.setSetpoint(Math.signum(chassisPitch) * -5.0, chassisVelocity);
        }   

        if (Math.abs(chassisPitch) <= 2.0)
        {
            Drive.getInstance().chassisDrive(0, 0, 0);
            Drive.getInstance().rotateModules(90);
        } 
        else
        {
            double velocityOutput = _velocityPID.calculate(chassisVelocity);
            Drive.getInstance().chassisDrive(velocityOutput, 0, 0);
        }
    }

    @Override
    public void end(boolean interrupted) 
    {

    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
