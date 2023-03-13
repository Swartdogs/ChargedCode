package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotContainer;
import frc.robot.leds.LEDAnimation;
import frc.robot.leds.LEDAnimationCommand;
import frc.robot.leds.LEDAnimationFrame;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;

import static frc.robot.Constants.LED.*;

public class CmdLEDTeleopSwapSides extends LEDAnimationCommand
{
    @Override
    public void initialize()
    {
        ArmSide  curSide     = RobotContainer.getInstance().getArmSide();
        HandMode curHandMode = RobotContainer.getInstance().getHandMode();
        Color    color       = curHandMode == HandMode.Cone ? YELLOW : PURPLE;
        
        LEDAnimation animation;

        // If arm side is front, we're coming from the back
        if (curSide == ArmSide.Front)
        {
            animation = new LEDAnimation
            (
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { null,  color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02)
            );
        }
        // If arm side is back, we're coming from the front
        else
        {
            animation = new LEDAnimation
            (
                new LEDAnimationFrame(new Color[] { color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null,  null,  null,  null,  null,  null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null,  null,  null,  null,  null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null,  null,  null,  null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null,  null,  null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null,  null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null,  null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color, null  }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, color }, 0.02)
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
