package frc.robot.leds;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;

public class LEDAnimationFrame
{
    private ArrayList<Color> _pattern;

    public LEDAnimationFrame(Color[] pattern)
    {
        if (pattern.length != Constants.LED.NUM_LEDS)
        {
            throw new IllegalArgumentException(String.format("Each LED animation frame must contain %d LEDs!", Constants.LED.NUM_LEDS));
        }

        _pattern = new ArrayList<Color>();

        for (int i = 0; i < pattern.length; i++)
        {
            _pattern.add(pattern[i]);
        }
    }

    public List<Color> getLEDPattern()
    {
        return _pattern;
    }
}
