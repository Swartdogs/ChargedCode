package frc.robot.leds;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDAnimationFrame
{
    private AddressableLEDBuffer _leds;
    private double               _time;
    
    public LEDAnimationFrame(AddressableLEDBuffer leds, double time)
    {
        _leds = leds;
        _time = time;
    }

    public AddressableLEDBuffer getLEDPattern()
    {
        return _leds;
    }

    public double getDuration()
    {
        return _time;
    }
}
