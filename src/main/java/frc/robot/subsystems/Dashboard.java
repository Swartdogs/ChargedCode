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
    public Dashboard() 
    {
        var tab = Shuffleboard.getTab("Dashboard");
        var buttonEntry = tab.add("button", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        var button2Entry = tab.add("button2", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();


        var button = new DashboardButton(buttonEntry);
        button.whenPressed(()-> System.out.println("pressed"));

        var button2 = new DashboardButton(button2Entry);
        button2.whenPressed(()-> System.out.println("hello"));
    }
}
