package frc.robot.paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.subsystems.drive.Vector;

public class Trajectory
{
    private ArrayList<TrajectoryFrame> _frames;
    private int _prevFrame;

    public Trajectory()
    {
        _prevFrame = 0;
        _frames = new ArrayList<TrajectoryFrame>();
    }

    public Trajectory(String csvFile)
    {
        this();
        // load path from file
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(Filesystem.getDeployDirectory().toPath().resolve(csvFile)))
            );

            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.substring(0, 1).equals("#"))
                {
                    _frames.add(new TrajectoryFrame(line));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(String.format("Failed to read trajectory from file %s", csvFile));
        }
    }

    public TrajectoryFrame getFrame(double time)
    {
        if (_frames.get(_prevFrame).getTime() > time)
        {
            _prevFrame = 0;
        }

        while (_prevFrame < _frames.size() - 2 && _frames.get(_prevFrame + 1).getTime() < time)
        {
            _prevFrame++;
        }

        TrajectoryFrame before = _frames.get(_prevFrame);
        TrajectoryFrame after = _frames.get(_prevFrame + 1);

        // if you're past the beginning/end, limit the time so that it doesn't break things
        time = Math.max(before.getTime(), Math.min(time, after.getTime()));

        // linear interpolation
        double ratio = (after.getTime() - time) / (after.getTime() - before.getTime());

        Vector position = before.getPosition().multiply(ratio).add(after.getPosition().multiply(1 - ratio));
        Vector velocity = before.getVelocity().multiply(ratio).add(after.getVelocity().multiply(1 - ratio));
        double heading = (before.getHeading() * ratio) + (after.getHeading() * (1 - ratio));
        double angularVelocity = (before.getAngularVelocity() * ratio) + (after.getAngularVelocity() * (1 - ratio));

        return new TrajectoryFrame(time, position, velocity, heading, angularVelocity);
    }
    
    public boolean isFinished(double time)
    {
        return time >= _frames.get(_frames.size() - 1).getTime();
    }
}
