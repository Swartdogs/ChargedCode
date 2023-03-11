package frc.robot.leds;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;

public class LEDAnimation 
{
    private Timer                        _frameTimer;
    private ArrayList<LEDAnimationFrame> _frames;
    private int                          _currentFrame;

    public LEDAnimation()
    {
        _frameTimer   = new Timer();
        _frames       = new ArrayList<LEDAnimationFrame>();
        _currentFrame = 0;
    }

    public void start()
    {
        _currentFrame = 0;
        _frameTimer.reset();
        _frameTimer.start();
    }

    public void addFrame(LEDAnimationFrame frame)
    {
        _frames.add(frame);
    }

    public AddressableLEDBuffer getCurrentFramePattern()
    {
        // Get the current frame. If we've already finished, return the last frame instead
        var currentFrame = _frames.get(Math.min(_currentFrame, _frames.size() - 1));

        // If we're done with the current frame advance to the next frame
        if (_frameTimer.hasElapsed(currentFrame.getDuration()) && !isFinished())
        {
            _currentFrame++;
            _frameTimer.reset();

            // If we have another frame to advance to, advance to it
            if (!isFinished())
            {
                currentFrame = _frames.get(_currentFrame);
                _frameTimer.start();
            }
        }

        return currentFrame.getLEDPattern();
    }

    public boolean isFinished()
    {
        return _currentFrame >= _frames.size();
    }
}
