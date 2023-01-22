package frc.robot.subsystems;

import java.util.EnumSet;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Dashboard extends SubsystemBase 
{
    public Dashboard() 
    {
        var tab = Shuffleboard.getTab("Dashboard");
        var allianceBox = tab.add("allianceColorBox", false).withPosition(10, 0).withSize(18, 1).withWidget(BuiltInWidgets.kBooleanBox);
        var visionLayout = tab.getLayout("VisionBox", BuiltInLayouts.kGrid).withPosition(10, 2).withSize(8, 2);
        var hasTargetBox = visionLayout.add("hasTargetBox", false).withPosition(0, 0).withSize(4, 1).withWidget(BuiltInWidgets.kBooleanBox);
        var targetID = visionLayout.add("TargetID", 0).withSize(4, 1).withPosition(4, 0).withWidget(BuiltInWidgets.kTextView);
        // What is a default value for a camerastream?
        var camera = tab.add("Camera", false).withPosition(0, 0).withSize(10, 10).withWidget(BuiltInWidgets.kCameraStream);
    }
}
