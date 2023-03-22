package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.leds.LEDAnimation;
import frc.robot.leds.LEDAnimationCommand;
import frc.robot.leds.LEDAnimationFrame;

public class CmdLEDCycleWave extends LEDAnimationCommand
{
    private Color _1;
    private Color _2;
    private Color _3;

    public CmdLEDCycleWave(Color color1, Color color2, Color color3)
    {
        _1 = color1;
        _2 = color2;
        _3 = color3;

        var animation = new LEDAnimation();

        animation.addFrame(new LEDAnimationFrame(new Color[] { _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3 }));
        animation.addFrame(new LEDAnimationFrame(new Color[] { _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3 }));
        animation.addFrame(new LEDAnimationFrame(new Color[] { _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3 }));
        animation.addFrame(new LEDAnimationFrame(new Color[] { _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1 }));
        animation.addFrame(new LEDAnimationFrame(new Color[] { _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1 }));
        animation.addFrame(new LEDAnimationFrame(new Color[] { _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1 }));
        animation.addFrame(new LEDAnimationFrame(new Color[] { _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2 }));
        animation.addFrame(new LEDAnimationFrame(new Color[] { _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2 }));
        animation.addFrame(new LEDAnimationFrame(new Color[] { _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2, _3, _3, _3, _1, _1, _1, _2, _2, _2 }));

        setAnimation(animation);
    }
}
