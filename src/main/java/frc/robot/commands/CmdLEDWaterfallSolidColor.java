package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.leds.LEDAnimation;
import frc.robot.leds.LEDAnimationFrame;
import frc.robot.leds.LEDAnimationCommand;

public class CmdLEDWaterfallSolidColor extends LEDAnimationCommand
{
    public CmdLEDWaterfallSolidColor(Color c)
    {
        super(new LEDAnimation
        (
            new LEDAnimationFrame(new Color[] { null, null, null, null, null, null, null, null, null, null, null, null, c,    null, null, null, null, null, null, null, null, null, null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, null, null, null, null, null, null, null, null, null, c,    c,    c,    null, null, null, null, null, null, null, null, null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, null, null, null, null, null, null, null, null, c,    c,    c,    c,    c,    null, null, null, null, null, null, null, null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, null, null, null, null, null, null, null, c,    c,    c,    c,    c,    c,    c,    null, null, null, null, null, null, null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, null, null, null, null, null, null, c,    c,    c,    c,    c,    c,    c,    c,    c,    null, null, null, null, null, null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, null, null, null, null, null, c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    null, null, null, null, null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, null, null, null, null, c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    null, null, null, null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, null, null, null, c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    null, null, null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, null, null, c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    null, null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, null, c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    null, null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, null, c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    null, null }, 0.02),
            new LEDAnimationFrame(new Color[] { null, c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    null }, 0.02),
            new LEDAnimationFrame(new Color[] { c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c,    c    }, 0.02)
        ));
    }
}
