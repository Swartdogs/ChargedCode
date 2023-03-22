package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotContainer;
import frc.robot.leds.LEDAnimation;
import frc.robot.leds.LEDAnimationCommand;
import frc.robot.leds.LEDAnimationFrame;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;

import static frc.robot.Constants.LED.*;

public class CmdLEDTeleopInit extends LEDAnimationCommand 
{
    @Override
    public void initialize()
    {
        var armSide  = RobotContainer.getInstance().getArmSide();
        var handMode = RobotContainer.getInstance().getHandMode();

        var color = handMode == HandMode.Cone ? YELLOW : PURPLE;

        LEDAnimation animation;

        if (armSide == ArmSide.Front)
        {
            animation = new LEDAnimation
            (
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  color, null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  color, color, OFF,   null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  color, color, color, OFF,   OFF,   null,  null,  null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  color, color, color, color, OFF,   OFF,   OFF,   null,  null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   null  }),
                new LEDAnimationFrame(new Color[] { color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   })
            );
        }
        else
        {
            animation = new LEDAnimation
            (
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  color, null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  OFF,   color, color, null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  OFF,   OFF,   color, color, color, null,  null,  null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  OFF,   OFF,   OFF,   color, color, color, color, null,  null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, null,  null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, null,  null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, null,  null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, null,  null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, null,  null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, null,  null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  null,  OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, null,  null  }),
                new LEDAnimationFrame(new Color[] { null,  OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, null  }),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color })
            );
        }

        setAnimation(animation);
        animation.start();
    }

    @Override
    public boolean runsWhenDisabled()
    {
        return false;
    }
}
