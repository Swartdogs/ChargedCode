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
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { color, color, color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02)
            );
        }
        else
        {
            animation = new LEDAnimation
            (
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, OFF,   OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, OFF,   OFF   }, 0.02),
                new LEDAnimationFrame(new Color[] { OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   OFF,   color, color, color, color, color, color, color, color, color, color, color, color, OFF   }, 0.02),
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
