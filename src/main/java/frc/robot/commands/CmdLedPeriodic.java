package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Led;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;
import frc.robot.RobotContainer;

public class CmdLedPeriodic extends CommandBase 
{
    public CmdLedPeriodic()
    {
        addRequirements(Led.getInstance());
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        ArmSide armSide = RobotContainer.getInstance().getArmSide();
        HandMode handMode = RobotContainer.getInstance().getHandMode();

        AddressableLEDBuffer ledBuffer = Led.getInstance().getLEDs();

        if (DriverStation.isTeleopEnabled())
        {
            for (int i = 0; i < ledBuffer.getLength(); i++)
            {
                if ((i <= 12 && armSide == ArmSide.Front) || (i >= 12 && armSide == ArmSide.Back))
                {
                    if (handMode == HandMode.Cone)
                    {
                        ledBuffer.setRGB(i, 255, 115, 0);
                    }
                    else if (handMode == HandMode.Cube)
                    {
                        ledBuffer.setRGB(i, 127, 0, 255);
                    }
                }
                else
                {
                    ledBuffer.setRGB(i, 0, 0, 0);
                }
            }
        }

        if (DriverStation.isAutonomousEnabled())
        {
            Color c;

            switch (DriverStation.getAlliance())
            {
                case Red:
                    c = new Color(255, 0, 0);
                    break;
                case Blue:
                    c = new Color(0, 0, 255);
                    break;
                default:
                    c = new Color(0, 115, 0);
                    break;
            }

            for (int i = 0; i < ledBuffer.getLength(); i++)
            {
                ledBuffer.setLED(i, c);;
            }
        }

        if (DriverStation.isDisabled())
        {
            for (int i = 0; i < ledBuffer.getLength(); i++)
            {
                ledBuffer.setLED(i, new Color(255, 50, 0));
            }
        }

        Led.getInstance().setLEDs(ledBuffer);

    }

    @Override
    public boolean runsWhenDisabled()
    {
        return true;
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
