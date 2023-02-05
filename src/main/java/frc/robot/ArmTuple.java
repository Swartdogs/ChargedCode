package frc.robot;

import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Manipulator.HandMode;

public class ArmTuple 
{
    public final ArmPosition armPosition;
    public final ArmSide     armSide;
    public final HandMode    handMode;

    public ArmTuple(ArmPosition armPosition, ArmSide armSide, HandMode handMode) 
    {
        this.armPosition = armPosition;
        this.armSide     = armSide;
        this.handMode    = handMode;
    }

    @Override 
    public boolean equals(Object object)
    {
        if(object == this)
        {
            return true;
        }

        if(!(object instanceof ArmTuple))
        {
            return false;
        }

        ArmTuple otherTuple = (ArmTuple) object;

        return armPosition == otherTuple.armPosition &&
               armSide     == otherTuple.armSide     &&
               handMode    == otherTuple.handMode;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;

        hash = hash * 13 + armPosition.ordinal();
        hash = hash * 13 + armSide.ordinal();
        hash = hash * 13 + handMode.ordinal();

        return hash;
    }
}
