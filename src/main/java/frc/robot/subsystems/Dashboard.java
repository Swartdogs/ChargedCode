package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Dashboard extends SubsystemBase 
{
    private GenericEntry _y;
    public Dashboard() 
    {
        var x = Shuffleboard.getTab("Dashboard");
        _y = x.add("number", 0).getEntry();
    }

    @Override
    public void periodic() 
    {
     System.out.println(_y.getDouble(0));
    }
}
