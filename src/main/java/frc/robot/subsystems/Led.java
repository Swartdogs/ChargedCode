package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;

public class Led extends SubsystemBase 
{
    private static Led _instance;

    public static Led getInstance()
    {
        if (_instance == null)
        {
            _instance = new Led();
        }

        return _instance;
    }

    private AddressableLED _led;
    private AddressableLEDBuffer _ledBuffer;

    private Led()
    {
        _led = new AddressableLED(0);
        _ledBuffer = new AddressableLEDBuffer(25);

        _led.setLength(_ledBuffer.getLength());

        _led.setData(_ledBuffer);
        _led.start();
    } 

    @Override
    public void periodic()
    {
        ArmSide armSide = RobotContainer.getInstance().getArmSide();
        HandMode handMode = RobotContainer.getInstance().getHandMode();

        if (DriverStation.isTeleopEnabled())
        {
            for (int i = 0; i < _ledBuffer.getLength(); i++)
            {
                if ((i <= 12 && armSide == ArmSide.Front) || (i >= 12 && armSide == ArmSide.Back))
                {
                    if (handMode == HandMode.Cone)
                    {
                        _ledBuffer.setRGB(i, 255, 115, 0);
                    }
                    else if (handMode == HandMode.Cube)
                    {
                        _ledBuffer.setRGB(i, 127, 0, 255);
                    }
                }
                else
                {
                    _ledBuffer.setRGB(i, 0, 0, 0);
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

            for (int i = 0; i < _ledBuffer.getLength(); i++)
            {
                _ledBuffer.setLED(i, c);;
            }
        }

        if (DriverStation.isDisabled())
        {
            for (int i = 0; i < _ledBuffer.getLength(); i++)
            {
                _ledBuffer.setLED(i, new Color(255, 50, 0));
            }
        }

        _led.setData(_ledBuffer);
    }
}
