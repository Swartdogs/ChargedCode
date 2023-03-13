package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.leds.LEDAnimation;
import frc.robot.leds.LEDAnimationCommand;
import frc.robot.leds.LEDAnimationFrame;
import frc.robot.subsystems.Led;

public class CmdLEDDripSolidColor extends LEDAnimationCommand
{
    private Color _c;

    public CmdLEDDripSolidColor(Color color)
    {
        _c = color;
    }

    @Override
    public void initialize()
    {
        var initialFrame = Led.getInstance().getLEDs();
        
        var _0  = initialFrame.getLED(0);
        var _1  = initialFrame.getLED(1);
        var _2  = initialFrame.getLED(2);
        var _3  = initialFrame.getLED(3);
        var _4  = initialFrame.getLED(4);
        var _5  = initialFrame.getLED(5);
        var _6  = initialFrame.getLED(6);
        var _7  = initialFrame.getLED(7);
        var _8  = initialFrame.getLED(8);
        var _9  = initialFrame.getLED(9);
        var _10 = initialFrame.getLED(10);
        var _11 = initialFrame.getLED(11);
        var _12 = initialFrame.getLED(12);
        var _13 = initialFrame.getLED(13);
        var _14 = initialFrame.getLED(14);
        var _15 = initialFrame.getLED(15);
        var _16 = initialFrame.getLED(16);
        var _17 = initialFrame.getLED(17);
        var _18 = initialFrame.getLED(18);
        var _19 = initialFrame.getLED(19);
        var _20 = initialFrame.getLED(20);
        var _21 = initialFrame.getLED(21);
        var _22 = initialFrame.getLED(22);
        var _23 = initialFrame.getLED(23);
        var _24 = initialFrame.getLED(24);

        var animation = new LEDAnimation
        (
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _c,  _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _c,  _12, _c,  _14, _15, _16, _17, _18, _19, _20, _21, _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _c,  _11, _12, _13, _c,  _15, _16, _17, _18, _19, _20, _21, _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _c,  _10, _11, _12, _13, _14, _c,  _16, _17, _18, _19, _20, _21, _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _c,  _9,  _10, _11, _12, _13, _14, _15, _c,  _17, _18, _19, _20, _21, _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _3,  _4,  _5,  _6,  _c,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _c,  _18, _19, _20, _21, _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _3,  _4,  _5,  _c,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _c,  _19, _20, _21, _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _3,  _4,  _c,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _c,  _20, _21, _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _3,  _c,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _c,  _21, _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _2,  _c,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _c,  _22, _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _1,  _c,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _c,  _23, _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _0,  _c,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _c,  _24 }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _c,  _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _c,  _12, _c,  _14, _15, _16, _17, _18, _19, _20, _21, _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _c,  _11, _12, _13, _c,  _15, _16, _17, _18, _19, _20, _21, _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _c,  _10, _11, _12, _13, _14, _c,  _16, _17, _18, _19, _20, _21, _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _4,  _5,  _6,  _7,  _c,  _9,  _10, _11, _12, _13, _14, _15, _c,  _17, _18, _19, _20, _21, _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _4,  _5,  _6,  _c,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _c,  _18, _19, _20, _21, _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _4,  _5,  _c,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _c,  _19, _20, _21, _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _4,  _c,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _c,  _20, _21, _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _3,  _c,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _c,  _21, _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _2,  _c,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _c,  _22, _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _1,  _c,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _c,  _23, _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _c,  _13, _14, _15, _16, _17, _18, _19, _20, _21, _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _c,  _12, _c,  _14, _15, _16, _17, _18, _19, _20, _21, _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _c,  _11, _12, _13, _c,  _15, _16, _17, _18, _19, _20, _21, _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _4,  _5,  _6,  _7,  _8,  _c,  _10, _11, _12, _13, _14, _c,  _16, _17, _18, _19, _20, _21, _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _4,  _5,  _6,  _7,  _c,  _9,  _10, _11, _12, _13, _14, _15, _c,  _17, _18, _19, _20, _21, _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _4,  _5,  _6,  _c,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _c,  _18, _19, _20, _21, _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _4,  _5,  _c,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _c,  _19, _20, _21, _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _4,  _c,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _c,  _20, _21, _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _3,  _c,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _c,  _21, _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _2,  _c,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _c,  _22, _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _21, _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _c,  _13, _14, _15, _16, _17, _18, _19, _20, _21, _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _c,  _12, _c,  _14, _15, _16, _17, _18, _19, _20, _21, _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _4,  _5,  _6,  _7,  _8,  _9,  _c,  _11, _12, _13, _c,  _15, _16, _17, _18, _19, _20, _21, _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _4,  _5,  _6,  _7,  _8,  _c,  _10, _11, _12, _13, _14, _c,  _16, _17, _18, _19, _20, _21, _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _4,  _5,  _6,  _7,  _c,  _9,  _10, _11, _12, _13, _14, _15, _c,  _17, _18, _19, _20, _21, _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _4,  _5,  _6,  _c,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _c,  _18, _19, _20, _21, _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _4,  _5,  _c,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _c,  _19, _20, _21, _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _4,  _c,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _c,  _20, _21, _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _3,  _c,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _c,  _21, _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, _c,  _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _11, _c,  _13, _14, _15, _16, _17, _18, _19, _20, _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _4,  _5,  _6,  _7,  _8,  _9,  _10, _c,  _12, _c,  _14, _15, _16, _17, _18, _19, _20, _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _4,  _5,  _6,  _7,  _8,  _9,  _c,  _11, _12, _13, _c,  _15, _16, _17, _18, _19, _20, _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _4,  _5,  _6,  _7,  _8,  _c,  _10, _11, _12, _13, _14, _c,  _16, _17, _18, _19, _20, _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _4,  _5,  _6,  _7,  _c,  _9,  _10, _11, _12, _13, _14, _15, _c,  _17, _18, _19, _20, _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _4,  _5,  _6,  _c,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _c,  _18, _19, _20, _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _4,  _5,  _c,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _c,  _19, _20, _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _4,  _c,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _c,  _20, _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _5,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _c,  _c,  _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _5,  _6,  _7,  _8,  _9,  _10, _11, _c,  _13, _14, _15, _16, _17, _18, _19, _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _5,  _6,  _7,  _8,  _9,  _10, _c,  _12, _c,  _14, _15, _16, _17, _18, _19, _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _5,  _6,  _7,  _8,  _9,  _c,  _11, _12, _13, _c,  _15, _16, _17, _18, _19, _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _5,  _6,  _7,  _8,  _c,  _10, _11, _12, _13, _14, _c,  _16, _17, _18, _19, _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _5,  _6,  _7,  _c,  _9,  _10, _11, _12, _13, _14, _15, _c,  _17, _18, _19, _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _5,  _6,  _c,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _c,  _18, _19, _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _5,  _c,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _c,  _19, _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _6,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _18, _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _6,  _7,  _8,  _9,  _10, _11, _c,  _13, _14, _15, _16, _17, _18, _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _6,  _7,  _8,  _9,  _10, _c,  _12, _c,  _14, _15, _16, _17, _18, _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _6,  _7,  _8,  _9,  _c,  _11, _12, _13, _c,  _15, _16, _17, _18, _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _6,  _7,  _8,  _c,  _10, _11, _12, _13, _14, _c,  _16, _17, _18, _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _6,  _7,  _c,  _9,  _10, _11, _12, _13, _14, _15, _c,  _17, _18, _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _6,  _c,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _c,  _18, _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _7,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _17, _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _7,  _8,  _9,  _10, _11, _c,  _13, _14, _15, _16, _17, _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _7,  _8,  _9,  _10, _c,  _12, _c,  _14, _15, _16, _17, _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _7,  _8,  _9,  _c,  _11, _12, _13, _c,  _15, _16, _17, _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _7,  _8,  _c,  _10, _11, _12, _13, _14, _c,  _16, _17, _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _7,  _c,  _9,  _10, _11, _12, _13, _14, _15, _c,  _17, _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _8,  _9,  _10, _11, _12, _13, _14, _15, _16, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _8,  _9,  _10, _11, _c,  _13, _14, _15, _16, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _8,  _9,  _10, _c,  _12, _c,  _14, _15, _16, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _8,  _9,  _c,  _11, _12, _13, _c,  _15, _16, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _8,  _c,  _10, _11, _12, _13, _14, _c,  _16, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _9,  _10, _11, _12, _13, _14, _15, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _9,  _10, _11, _c,  _13, _14, _15, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _9,  _10, _c,  _12, _c,  _14, _15, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _9,  _c,  _11, _12, _13, _c,  _15, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _10, _11, _12, _13, _14, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _10, _11, _c,  _13, _14, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _10, _c,  _12, _c,  _14, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _11, _12, _13, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _11, _c,  _13, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),
            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _12, _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02),

            new LEDAnimationFrame(new Color[] { _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c,  _c  }, 0.02)
        );

        animation.start();

        setAnimation(animation);
    }
}
