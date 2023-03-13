package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.leds.LEDAnimation;
import frc.robot.leds.LEDAnimationCommand;
import frc.robot.leds.LEDAnimationFrame;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;

public class CmdLEDChangeHandMode extends LEDAnimationCommand
{
    private static final double CHANGE_TIME = 0.16;

    private static final int STEPS = (int)(Constants.LOOPS_PER_SECOND * CHANGE_TIME);

    @Override
    public void initialize()
    {
        var armSide  = RobotContainer.getInstance().getArmSide();
        var handMode = RobotContainer.getInstance().getHandMode();

        Color from;
        Color to;

        int start;
        int end;

        if (handMode == HandMode.Cone)
        {
            from = Constants.LED.PURPLE;
            to   = Constants.LED.YELLOW;
        }
        else
        {
            from = Constants.LED.YELLOW;
            to   = Constants.LED.PURPLE;
        }

        if (armSide == ArmSide.Front)
        {
            start = 0;
            end   = 12;
        }
        else
        {
            start = 12;
            end   = 24;
        }

        var animation = new LEDAnimation();

        for (int step = 1; step <= STEPS; step++)
        {
            var ratio = (double)step / STEPS;
            var colors = new Color[Constants.LED.NUM_LEDS];

            for (int i = 0; i < Constants.LED.NUM_LEDS; i++)
            {
                if (i >= start && i <= end)
                {
                    colors[i] = new Color
                    (
                        blend(from.red,   to.red,   ratio),
                        blend(from.green, to.green, ratio),
                        blend(from.blue,  to.blue,  ratio)
                    );
                }
                else
                {
                    colors[i] = Constants.LED.OFF;
                }
            }

            animation.addFrame(new LEDAnimationFrame(colors, 0.02));
        }

        setAnimation(animation);
        animation.start();
    }

    @Override
    public boolean runsWhenDisabled()
    {
        return false;
    }

    private double blend(double from, double to, double ratio)
    {
        return from * (1 - ratio) + to * ratio;
    }
}
