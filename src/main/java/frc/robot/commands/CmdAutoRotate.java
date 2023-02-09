package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class CmdAutoRotate extends DriveCommand 
{
    private double          _desiredAngle;
    private DoubleSupplier  _xInput;
    private DoubleSupplier  _yInput; 
    private BooleanSupplier _robotCentricInput;

    public CmdAutoRotate(double desiredAngle, DoubleSupplier xInput, DoubleSupplier yInput, BooleanSupplier robotCentricInput) 
    {
        _desiredAngle      = desiredAngle;
        _xInput            = xInput;
        _yInput            = yInput;
        _robotCentricInput = robotCentricInput;

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
        double x       = _xInput.getAsDouble();
        double y       = _yInput.getAsDouble();

        boolean robotCentric = _robotCentricInput.getAsBoolean();

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
