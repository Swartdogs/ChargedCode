package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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

    private AddressableLED       _led;
    private AddressableLEDBuffer _ledBuffer;

    private Led()
    {
        _led       = new AddressableLED(0);
        _ledBuffer = new AddressableLEDBuffer(Constants.Led.NUM_LEDS);

        _led.setLength(_ledBuffer.getLength());

        _led.setData(_ledBuffer);
        _led.start();
    }

    public void setLEDs(AddressableLEDBuffer buffer)
    {
        _ledBuffer = buffer;
        _led.setData(_ledBuffer);
    }

    public AddressableLEDBuffer getLEDs()
    {
        return _ledBuffer;
    }

    public void switchDefaultCommand(Command defaultCommand)
    {
        var command = getDefaultCommand();

        if(command != null)
        {
            command.cancel();
        }

        setDefaultCommand(command);
    }
}
