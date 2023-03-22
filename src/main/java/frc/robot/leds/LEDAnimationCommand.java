package frc.robot.leds;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LED;

public abstract class LEDAnimationCommand extends CommandBase
{
    private LEDAnimation _animation;

    public LEDAnimationCommand()
    {
        addRequirements(LED.getInstance());
    }

    public LEDAnimationCommand(LEDAnimation animation)
    {
        this();

        _animation = animation;
    }

    protected void setAnimation(LEDAnimation animation)
    {
        _animation = animation;
    }

    @Override
    public void initialize()
    {
        _animation.start();
    }

    @Override
    public void execute()
    {
        _animation.play();

        LED.getInstance().applyAnimationFrame(_animation.getCurrentFrame());
    }

    @Override
    public boolean runsWhenDisabled()
    {
        return true;
    }

    @Override
    public boolean isFinished()
    {
        return _animation.isFinished();
    }
}
