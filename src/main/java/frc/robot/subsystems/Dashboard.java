package frc.robot.subsystems;

import java.util.EnumSet;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Dashboard extends SubsystemBase 
{
    private GenericEntry _x;
    public Dashboard() 
    {
        var x = Shuffleboard.getTab("Dashboard");
        _x = x.add("button", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();

        var y = NetworkTableInstance.getDefault();
        y.addListener(_x, EnumSet.of(NetworkTableEvent.Kind.kValueRemote), z->
        {
            boolean a = (_x.getBoolean(false));
        
            if (a)
            {
             _x.setBoolean(false);
             System.out.println("Pressed");
            }
        });
    }
}
