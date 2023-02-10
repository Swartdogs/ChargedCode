package frc.robot.commands;

import frc.robot.RobotContainer;

public class CmdAutoRotate extends DriveCommand 
{
    private double          _desiredAngle;

    public CmdAutoRotate(double desiredAngle) 
    {
        _desiredAngle      = desiredAngle;

        _rotatePID.setInputRange(0.0, 180.0);

        addRequirements(_drive);
    }

    @Override
    public void initialize() 
    {
        _rotatePID.setSetpoint(_desiredAngle, _drive.getHeading(), true);
    }

    @Override
    public void execute() 
    {
        double outputR = _rotatePID.calculate(_drive.getHeading());
        double x       =  RobotContainer.getInstance().getDriveJoyX();
        double y       =  RobotContainer.getInstance().getDriveJoyY();

        boolean robotCentric =  RobotContainer.getInstance().driveIsRobotCentric();

        if (robotCentric)
        {
            _drive.chassisDrive(y, x, outputR);
        }
        else
        {
            _drive.fieldDrive(x, y, outputR);
        }
    }

    @Override
    public void end(boolean interrupted) 
    {
        _drive.chassisDrive(0, 0, 0);
    }

    @Override
    public boolean isFinished() 
    {
        return _rotatePID.atSetpoint();
    }
}
