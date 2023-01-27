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
        // What is a default value for a camerastream?
        //var camera = tab.add("Camera", false).withPosition(0, 0).withSize(10, 10).withWidget(BuiltInWidgets.kCameraStream);

        var settingsTab                 = Shuffleboard.getTab("Settings");

        //Drive
        var driveSettingsLayout         = settingsTab.getLayout("Drive Subsystem", BuiltInLayouts.kGrid).withPosition(0, 0).withSize(4, 2);
        var flAngle                     = driveSettingsLayout.add("FL Angle", 0.0).withPosition(0, 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView);
        var frAngle                     = driveSettingsLayout.add("FR Angle", 0.0).withPosition(2, 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView);
        var blAngle                     = driveSettingsLayout.add("BL Angle", 0.0).withPosition(0, 2).withSize(2,2).withWidget(BuiltInWidgets.kTextView);
        var brAngle                     = driveSettingsLayout.add("BR Angle", 0.0).withPosition(2, 2).withSize(2,2).withWidget(BuiltInWidgets.kTextView);
        var resetSwerveModules          = driveSettingsLayout.add("Reset Swerve", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();

        //Vision

        //Arm
        var armSettingsLayout           = settingsTab.getLayout("Arm Subsystem", BuiltInLayouts.kGrid).withPosition(0, 4).withSize(4, 2);
        var armMaxExtension             = armSettingsLayout.add("Arm Extension", 0.0);
        var armMinAngle                 = armSettingsLayout.add("Arm Min Angle", -90.0);
        var armMaxAngle                 = armSettingsLayout.add("Arm Max Angle", 90.0);

        //Manipulator
        var manipulatorSettingsLayout   = settingsTab.getLayout("Manipulator Subsystem", BuiltInLayouts.kGrid).withPosition(4, 0).withSize(9, 2);
        var WristMinAngle               = manipulatorSettingsLayout.add("Wrist Min", -120.0).withPosition(0, 2).withSize(3, 2).withWidget(BuiltInWidgets.kTextView);
        var WristMaxAngle               = manipulatorSettingsLayout.add("Wrist Max", 120.0).withPosition(0, 0).withSize(3, 2).withWidget(BuiltInWidgets.kTextView);
        var TwistMinRotation            = manipulatorSettingsLayout.add("Twist Min", -180.0).withPosition(1, 2).withSize(3, 2).withWidget(BuiltInWidgets.kTextView);
        var TwistMaxRotation            = manipulatorSettingsLayout.add("Twist Max", 180.0).withPosition(1, 0).withSize(3, 2).withWidget(BuiltInWidgets.kTextView);
        var EjectTime                   = manipulatorSettingsLayout.add("Eject Time", 0.0).withPosition(2, 2).withSize(3, 2).withWidget(BuiltInWidgets.kTextView);
        var IntakeSpeed                 = manipulatorSettingsLayout.add("Intake Speed", 0.0).withPosition(2, 0).withSize(3, 2).withWidget(BuiltInWidgets.kTextView);
    }
}
