package frc.robot.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class CmdDrivePath extends CommandBase {
    double _time = 0;

    PIDControl _xPID;
    PIDControl _yPID;
    PIDControl _rotatePID;

    public CmdDrivePath(String file) {
        // initialize PID controllers
        _xPID = new PIDControl();
        _yPID = new PIDControl();
        _rotatePID = new PIDControl();

        _rotatePID.setInputRange(0, 360);
        _rotatePID.setContinuous(true);

        _xPID.setOutputRange(-Constants.Drive.MAX_DRIVE_SPEED, -Constants.Drive.MAX_DRIVE_SPEED);
        _yPID.setOutputRange(-Constants.Drive.MAX_DRIVE_SPEED, -Constants.Drive.MAX_DRIVE_SPEED);
        _rotatePID.setOutputRange(-Constants.Drive.MAX_ROTATE_SPEED, Constants.Drive.MAX_ROTATE_SPEED);

        _xPID.setCoefficient(Coefficient.P, 0.0, 0.8, 0.0);
        _xPID.setCoefficient(Coefficient.I, 0.0, 0.0, 0.0);
        _xPID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);
        
        _yPID.setCoefficient(Coefficient.P, 0.0, 0.8, 0.0);
        _yPID.setCoefficient(Coefficient.I, 0.0, 0.0, 0.0);
        _yPID.setCoefficient(Coefficient.D, 0.0, 0.0, 0.0);

        _rotatePID.setCoefficient(Coefficient.P, 0.0, 0.8, 0.0);
        _rotatePID.setCoefficient(Coefficient.P, 0.0, 0.0, 0.0);
        _rotatePID.setCoefficient(Coefficient.P, 0.0, 0.0, 0.0);


        // load path from file
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(Filesystem.getDeployDirectory().toPath().resolve(file)))
            );
        }
        catch (IOException e)
        {

        }
        
        addRequirements(Drive.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        // update the time 

        
        // get the current target speed and position





    }

    @Override
    public void end(boolean interrupted) {
        Drive.getInstance().chassisDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private Vector metersToInches(double x, double y)
    {
        return new Vector();
    }

}
