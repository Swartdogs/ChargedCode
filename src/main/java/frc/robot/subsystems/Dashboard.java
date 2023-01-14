package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Dashboard extends SubsystemBase 
{
    private GenericEntry _y;
    private GenericEntry _x;
    public Dashboard() 
    {
        var x = Shuffleboard.getTab("Dashboard");
        _y = x.add("number", 0).getEntry();
        _x = x.add("number2", 0).getEntry();
    }

    @Override
    public void periodic() 
    {
        double x = (_y.getDouble(0));
        System.out.println(x);
        _x.setDouble(x);
    }
}
