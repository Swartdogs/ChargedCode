package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.AddressableLEDSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.leds.LEDAnimationFrame;

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

    // Simulation
    private AddressableLEDSim    _ledSim;

    private Led()
    {
        _led       = new AddressableLED(0);
        _ledBuffer = new AddressableLEDBuffer(Constants.Led.NUM_LEDS);

        _led.setLength(_ledBuffer.getLength());

        _led.setData(_ledBuffer);
        _led.start();

        if (RobotBase.isSimulation())
        {
            _ledSim = new AddressableLEDSim(_led);
            _ledSim.setRunning(true);
        }
    }

    public void applyAnimationFrame(LEDAnimationFrame frame)
    {
        var pattern = frame.getLEDPattern();

        for (int i = 0; i < pattern.size(); i++)
        {
            var color = pattern.get(i);

            if (color != null)
            {
                _ledBuffer.setLED(i, color);
            }
        }

        _led.setData(_ledBuffer);
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

        setDefaultCommand(defaultCommand);
    }
}
