package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;
import frc.robot.RobotContainer;

public class CmdLEDTeleopResetting extends CommandBase 
{
    public CmdLEDTeleopResetting()
    {
        addRequirements(LED.getInstance());
    }

    @Override
    public void execute() 
    {
        AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(Constants.LED.NUM_LEDS);
        ArmSide              armSide   = RobotContainer.getInstance().getArmSide();
        HandMode             handMode  = RobotContainer.getInstance().getHandMode();

        for (int i = 0; i < ledBuffer.getLength(); i++)
        {
            if (i >= 8 && i <= 16)
            {
                ledBuffer.setLED(i, Constants.LED.ORANGE);
            }
            else if ((i <= 12 && armSide == ArmSide.Front) || (i >= 12 && armSide == ArmSide.Back))
            {
                if (handMode == HandMode.Cone)
                {
                    ledBuffer.setLED(i, Constants.LED.YELLOW);
                }
                else if (handMode == HandMode.Cube)
                {
                    ledBuffer.setLED(i, Constants.LED.PURPLE);
                }
            }
            else
            {
                ledBuffer.setLED(i, Constants.LED.OFF);
            }
        }

        LED.getInstance().setLEDs(ledBuffer);
    }


    @Override
    public boolean isFinished() 
    {
        return Arm.getInstance().isReset();
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() 
    {
        return InterruptionBehavior.kCancelIncoming;
    }
}
