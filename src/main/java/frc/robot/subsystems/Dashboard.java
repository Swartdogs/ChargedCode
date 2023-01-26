package frc.robot.subsystems;

import java.util.EnumSet;
import java.util.Map;

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
        var allianceBox = tab.add("allianceColorBox", false).withPosition(0, 0).withSize(28, 1).withWidget(BuiltInWidgets.kBooleanBox);
        var visionLayout = tab.getLayout("VisionBox", BuiltInLayouts.kGrid).withPosition(0, 2).withSize(8, 2);
        var hasTargetBox = visionLayout.add("hasTargetBox", false).withPosition(0, 0).withSize(4, 1).withWidget(BuiltInWidgets.kBooleanBox);
        var targetID = visionLayout.add("TargetID", 0).withSize(4, 1).withPosition(4, 0).withWidget(BuiltInWidgets.kTextView);
        var driveBaseLayout = tab.getLayout("DriveBaseLayout", BuiltInLayouts.kGrid).withPosition(0, 5).withSize(4, 4);
        //var heading = driveBaseLayout.add("Heading", 0).withProperties(Map.of("Min", -2, "Max", 12)).withSize(4, 1).withPosition(0, 0).withWidget(BuiltInWidgets.kNumberBar);
        var heading = driveBaseLayout.add("Heading", 0).withSize(4, 1).withPosition(0, 0).withWidget(BuiltInWidgets.kTextView);
        var swerveAnglesLayout = tab.getLayout("SwerveAngles", BuiltInLayouts.kGrid).withPosition(4, 5).withSize(4, 4);
        var frAngle = swerveAnglesLayout.add("FR", 0).withPosition(2, 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView);
        var flAngle = swerveAnglesLayout.add("FL", 0).withPosition(0, 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView);
        var brAngle = swerveAnglesLayout.add("BR", 0).withPosition(2, 2).withSize(2,2).withWidget(BuiltInWidgets.kTextView);
        var blAngle = swerveAnglesLayout.add("BL", 0).withPosition(0, 2).withSize(2,2).withWidget(BuiltInWidgets.kTextView);
        var odometer = driveBaseLayout.add("Odometer", "(0,0)").withPosition(0, 2).withSize(4,1).withProperties(null).withWidget(BuiltInWidgets.kTextView);
        var armLayout = tab.getLayout("Arm", BuiltInLayouts.kGrid).withPosition(9, 2).withSize(8,1);
        var shoulderAngle = armLayout.add("ShoulderAngle", 0).withPosition(0, 0).withSize(4,1).withWidget(BuiltInWidgets.kTextView);
        var extensionDistance = armLayout.add("ExtensionDistance", 0).withPosition(4, 0).withSize(4, 1).withWidget(BuiltInWidgets.kTextView);
        var intakeLayout = tab.getLayout("IntakeLayout", BuiltInLayouts.kGrid).withPosition(9, 5).withSize(8, 4);
        var wristAngle = armLayout.add("WristAngle", 0).withPosition(0, 0).withSize(4, 1).withWidget(BuiltInWidgets.kTextView);
        var twistAngle = armLayout.add("TwistAngle", 0).withPosition(0, 2).withSize(4, 1).withWidget(BuiltInWidgets.kTextView);
        var hasGamePiece = armLayout.add("HasGamePiece", false).withPosition(4, 0).withSize(4, 1).withWidget(BuiltInWidgets.kBooleanBox);
        
        // What is a default value for a camerastream?
        //var camera = tab.add("Camera", false).withPosition(0, 0).withSize(10, 10).withWidget(BuiltInWidgets.kCameraStream);
    }
}
