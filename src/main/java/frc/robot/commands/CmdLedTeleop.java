package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Led;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;
import frc.robot.RobotContainer;

public class CmdLedTeleop extends CommandBase 
{
    public CmdLedTeleop()
    {
        addRequirements(Led.getInstance());
    }

    @Override
    public void execute() 
    {
        AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(Constants.Led.NUM_LEDS);
        ArmSide              armSide   = RobotContainer.getInstance().getArmSide();
        HandMode             handMode  = RobotContainer.getInstance().getHandMode();

        for (int i = 0; i < ledBuffer.getLength(); i++)
        {
            if ((i <= 12 && armSide == ArmSide.Front) || (i >= 12 && armSide == ArmSide.Back))
            {
                if (handMode == HandMode.Cone)
                {
                    ledBuffer.setLED(i, Constants.Led.YELLOW);
                }
                else if (handMode == HandMode.Cube)
                {
                    ledBuffer.setLED(i, Constants.Led.PURPLE);
                }
            }
            else
            {
                ledBuffer.setLED(i, Constants.Led.OFF);
            }
        }
    }


    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
