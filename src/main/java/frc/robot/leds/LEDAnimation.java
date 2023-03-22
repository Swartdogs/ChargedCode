package frc.robot.leds;

import java.util.ArrayList;

public class LEDAnimation 
{
    private ArrayList<LEDAnimationFrame> _frames;
    private int                          _currentFrame;

    public LEDAnimation()
    {
        _frames       = new ArrayList<LEDAnimationFrame>();
        _currentFrame = -1;
    }

    public LEDAnimation(LEDAnimationFrame... animationFrames)
    {
        this();

        for (var frame : animationFrames)
        {
            addFrame(frame);
        }
    }

    private void startFrame(int frame)
    {
        _currentFrame = frame;
    }

    public void start()
    {
        startFrame(0);
    }

    public void play()
    {
        if (_currentFrame < _frames.size())
        {
            startFrame(_currentFrame + 1);
        }
    }

    public LEDAnimationFrame getCurrentFrame()
    {
        return _frames.get(Math.min(_currentFrame, _frames.size() - 1));
    }

    public void addFrame(LEDAnimationFrame frame)
    {
        _frames.add(frame);
    }

    public boolean isFinished()
    {
        return _currentFrame >= _frames.size();
    }
}
