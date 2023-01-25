package frc.robot.commands;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.Drive;

public class CmdDriveBalance extends CommandBase 
{
    private PIDControl _pitchPID;
    private PIDControl _rollPID;
    private PIDControl _rotatePID;

    public CmdDriveBalance() 
    {
        _pitchPID = new PIDControl();
        _rollPID = new PIDControl();
        _rotatePID = new PIDControl();

        _pitchPID.setCoefficient(Coefficient.P, 0.0, 0.0095, 0.0);// based on previous code
        _pitchPID.setCoefficient(Coefficient.I, 4, 0.0, 0.0015);
        _pitchPID.setCoefficient(Coefficient.D, 0.0, 0.01, 0.0);// like 0.1 may be better. Needs testing
        _pitchPID.setInputRange(-15.0, 15.0);// degrees
        _pitchPID.setOutputRange(-0.3, 0.3);
        _pitchPID.setSetpointDeadband(1.75);

        _rollPID.setCoefficient(Coefficient.P, 0.0, 0.0095, 0.0);// these should all be the same as the first PID
        _rollPID.setCoefficient(Coefficient.I, 4, 0.0, 0.0015);
        _rollPID.setCoefficient(Coefficient.D, 0.0, 0.01, 0.0);
        _rollPID.setInputRange(-15.0, 15.0);// degrees
        _rollPID.setOutputRange(-0.3, 0.3);
        _rollPID.setSetpointDeadband(1.75);

        _rotatePID.setCoefficient(Coefficient.P, 0.0, 0.008, 0.0);
        _rotatePID.setCoefficient(Coefficient.I, 20.0, 0.0, 0.00027);
        _rotatePID.setCoefficient(Coefficient.D, 0.0, 0.001, 0.0);
        _rotatePID.setInputRange(-180.0, 180.0);
        _rotatePID.setContinuous(true);
        _rotatePID.setOutputRange(-0.3, 0.3);
        _rotatePID.setOutputRamp(0.1, 0.05);
        _rotatePID.setSetpointDeadband(2.0);

        addRequirements(Drive.getInstance());
    }

    @Override
    public void initialize() 
    {        
        _pitchPID.setSetpoint(0, Drive.getInstance().getChassisRoll(), true);
        _rollPID.setSetpoint(0, Drive.getInstance().getChassisPitch(), true);
        _rotatePID.setSetpoint(Drive.getInstance().getHeading(), Drive.getInstance().getHeading(), true);
    }

    @Override
    public void execute() 
    {
        double outputDrive = _pitchPID.calculate(Drive.getInstance().getChassisPitch());
        double outputStrafe = _rollPID.calculate(Drive.getInstance().getChassisRoll());
        double outputR = _rotatePID.calculate(Drive.getInstance().getHeading());

        if (_pitchPID.atSetpoint())
        {
            outputDrive = 0;
        }
        
        if (_rollPID.atSetpoint())
        {
            outputStrafe = 0;
        }
        

        if (_pitchPID.atSetpoint() && _rollPID.atSetpoint())
        {
            Drive.getInstance().rotateModules(90);
        }
        else
        {
            Drive.getInstance().chassisDrive(outputDrive, /*outputStrafe*/0, outputR);
            //Drive.getInstance().rotateModules(90);
        }

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