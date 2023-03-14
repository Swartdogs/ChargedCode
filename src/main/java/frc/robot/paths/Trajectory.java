package frc.robot.paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Filesystem;

public class Trajectory
{
    private ArrayList<TrajectoryFrame> _frames;

    public Trajectory()
    {

    }

    public Trajectory(String csvFile)
    {
        // load path from file
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(Filesystem.getDeployDirectory().toPath().resolve(csvFile)))
            );

            String line;
            while ((line = reader.readLine()) != null)
            {
                _frames.add(new TrajectoryFrame(line));
            }
        }
        catch (IOException e)
        {

        }
        
    }

    
}
