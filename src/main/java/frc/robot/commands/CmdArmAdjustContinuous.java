package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.drive.Vector;

public class CmdArmAdjustContinuous extends CommandBase 
{
    private DoubleSupplier _modifyX;
    private DoubleSupplier _modifyY;
    private DoubleSupplier _modifyHandAngle;
    private DoubleSupplier _modifyTwistAngle;

    public CmdArmAdjustContinuous(DoubleSupplier x, DoubleSupplier y, DoubleSupplier handAngle, DoubleSupplier twistAngle) 
    {
        _modifyX = x;
        _modifyY = y;
        _modifyHandAngle  = handAngle;
        _modifyTwistAngle = twistAngle;
    }

    @Override
    public void initialize() 
    {

    }

    @Override
    public void execute() 
    {
        Vector initialCoordinate = Arm.getInstance().getCoordinate();
        double coordSign         = Math.signum(initialCoordinate.getX());

        Vector coord = initialCoordinate.add(new Vector(_modifyX.getAsDouble() * coordSign, _modifyY.getAsDouble()));

        if (Math.signum(coord.getX()) != coordSign)
        {
            coord.setX(0);
        }

        double handAngle  = Arm.getInstance().getHandAngle()        + _modifyHandAngle.getAsDouble()  * coordSign;
        double twistAngle = Arm.getInstance().getTwistTargetAngle() + _modifyTwistAngle.getAsDouble() * coordSign; 

        Arm.getInstance().setArmPosition(coord, handAngle, twistAngle);
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
