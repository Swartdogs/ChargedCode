package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Led;

public class CmdLEDWaterfallSolidColor extends CommandBase
{
    private final int FRAME_DURATION = (int)(Constants.LOOPS_PER_SECOND * 0.02);

    private Color _color;
    private int   _numLEDsColored;
    private int   _timer;

    public CmdLEDWaterfallSolidColor(Color color)
    {
        _color          = color;
        _numLEDsColored = 0;
        _timer          = 0;

        addRequirements(Led.getInstance());
    }

    @Override
    public void initialize()
    {
        _numLEDsColored = 0;
        _timer          = 0;
    }

    @Override
    public void execute()
    {
        _timer++;

        if (_timer % FRAME_DURATION == 0)
        {
            _numLEDsColored++;
        }

        var curBuffer = Led.getInstance().getLEDs();

        for (int i = 0; i < curBuffer.getLength(); i++)
        {
            if (i >= ((Constants.Led.NUM_LEDS / 2) - _numLEDsColored) && i <= ((Constants.Led.NUM_LEDS / 2) + _numLEDsColored))
            {
                curBuffer.setLED(i, _color);
            } 
        }

        Led.getInstance().setLEDs(curBuffer);
    }

    @Override
    public boolean runsWhenDisabled()
    {
        return true;
    }

    @Override
    public boolean isFinished()
    {
        return _numLEDsColored - 1 > Constants.Led.NUM_LEDS / 2;
    }
}
