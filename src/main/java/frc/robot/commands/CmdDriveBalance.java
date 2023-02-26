package frc.robot.commands;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

// FIXME fix me
public class CmdDriveBalance extends CommandBase 
{
    private PIDControl _pitchPID;
    private PIDControl _velocityPID;

    public CmdDriveBalance() 
    {
        // _pitchPID = new PIDControl();

        // _pitchPID.setCoefficient(Coefficient.P, 0.0, 0.0095, 0.0);
        // _pitchPID.setCoefficient(Coefficient.I, 4.0, 0.0, 0.0007);
        // _pitchPID.setCoefficient(Coefficient.D, 0.0, 0.01, 0.0);
        // _pitchPID.setInputRange(-15.0, 15.0);// degrees
        // _pitchPID.setOutputRange(-0.20, 0.20);
        // _pitchPID.setSetpointDeadband(1.75);

        _velocityPID = new PIDControl();

        _velocityPID.setCoefficient(Coefficient.P, 0.0, 0.0065, 0.0);
        _velocityPID.setCoefficient(Coefficient.I, 0.0, 0.0, 0.0);
        _velocityPID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);
        _velocityPID.setFeedForward((setpoint)->{return Math.signum(setpoint) * 0.05 + setpoint * 0.002;});
        _velocityPID.setInputRange(-600, 600);
        _velocityPID.setOutputRange(-0.40, 0.40);

        addRequirements(Drive.getInstance());
    }

    @Override
    public void initialize() 
    {
        //_pitchPID.setSetpoint(0, Drive.getInstance().getChassisRoll(), true);
    }

    @Override
    public void execute() 
    {
        double pitch = Drive.getInstance().getChassisPitch();
        pitch = 2.5;
        //double outputDrive = _pitchPID.calculate(Drive.getInstance().getChassisPitch());
        //outputDrive += Math.signum(outputDrive) * 0.05;
        double velocitySetpoint = 0.0;

        if (Math.abs(pitch) > 5.0)
        {
            velocitySetpoint = Math.signum(pitch) * -20.0;
        }
        
        else if (Math.abs(pitch) > 2.0)
        {
            velocitySetpoint = Math.signum(pitch) * -20.0;
        }

        Vector driveVelocity = Drive.getInstance().getChassisVelocity();

        _velocityPID.setSetpoint(velocitySetpoint, driveVelocity.getY());
        
        double outputDrive = _velocityPID.calculate(driveVelocity.getY());
        
        if (/*_pitchPID.atSetpoint()*/ Math.abs(pitch) <= 2.0)
        {
            Drive.getInstance().rotateModules(90);
        }
        else
        {
            System.out.println(outputDrive);
            Drive.getInstance().chassisDrive(outputDrive, /*outputStrafe*/0, /*outputR*/ 0);
            //Drive.getInstance().rotateModules(90);
        }

        // double drive = -0.03 * Math.signum(pitch);

        // if (pitch > 4.0 && Math.abs(_oldPitch - pitch) < 0.45)
        // {
        //     drive += -0.1;
        // }

        // if (pitch < -4.0 && Math.abs(_oldPitch - pitch) < 0.45)
        // {
        //     drive += 0.1;
        // }

        // if (Math.abs(pitch) > 2.0)
        // {
        //     Drive.getInstance().chassisDrive(drive, 0, 0);
        // }

        // else
        // {
        //     Drive.getInstance().rotateModules(90);
        // }

        // _oldPitch = pitch;
    }

    @Override
    public void end(boolean interrupted) 
    {

    }

    @Override
    public boolean isFinished()
    {
        //return _pitchPID.atSetpoint() && _rollPID.atSetpoint();
        return false;
    }
}