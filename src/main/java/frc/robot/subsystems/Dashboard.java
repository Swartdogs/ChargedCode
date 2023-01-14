package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
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
    }

    @Override
    public void periodic() 
    {
        boolean x = (_x.getBoolean(false));
        
        if (x)
        {
         System.out.println("Pressed");
         _x.setBoolean(false);
        }
    }
}
