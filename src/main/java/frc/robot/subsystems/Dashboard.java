package frc.robot.subsystems;

import java.util.EnumSet;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Dashboard extends SubsystemBase 
{
    public Dashboard() 
    {   
        // What is a default value for a camerastream?
        //var camera = tab.add("Camera", false).withPosition(0, 0).withSize(10, 10).withWidget(BuiltInWidgets.kCameraStream);

        var settingsTab                 = Shuffleboard.getTab("Settings");

        //Drive
        var driveSettingsLayout         = settingsTab.getLayout("Drive Subsystem", BuiltInLayouts.kGrid).withPosition(0, 0).withSize(4, 2);
        var flOffset                    = driveSettingsLayout.add("FL Offset", 0.0).withPosition(0, 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView).getEntry();
        var frOffset                    = driveSettingsLayout.add("FR Offset", 0.0).withPosition(2, 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView).getEntry();
        var blOffset                    = driveSettingsLayout.add("BL Offset", 0.0).withPosition(0, 2).withSize(2,2).withWidget(BuiltInWidgets.kTextView).getEntry();
        var brOffset                    = driveSettingsLayout.add("BR Offset", 0.0).withPosition(2, 2).withSize(2,2).withWidget(BuiltInWidgets.kTextView).getEntry();
        var resetSwerveModulesEntry     = driveSettingsLayout.add("Reset Swerve", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        var resetSwerveOffsetButton     = new DashboardButton(resetSwerveModulesEntry);
        resetSwerveOffsetButton.whenPressed(() -> {});

        //Arm
        var armSettingsLayout           = settingsTab.getLayout("Arm Subsystem", BuiltInLayouts.kGrid).withPosition(0, 4).withSize(4, 2);
        var armMaxExtension             = armSettingsLayout.add("Arm Extension", 0.0).getEntry();
        var armMinAngle                 = armSettingsLayout.add("Arm Min Angle", -90.0).getEntry();
        var armMaxAngle                 = armSettingsLayout.add("Arm Max Angle", 90.0).getEntry();

        //Manipulator
        var manipulatorSettingsLayout   = settingsTab.getLayout("Manipulator Subsystem", BuiltInLayouts.kGrid).withPosition(4, 0).withSize(9, 2);
        var wristMinAngle               = manipulatorSettingsLayout.add("Wrist Min", -120.0).withPosition(0, 2).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        var wristMaxAngle               = manipulatorSettingsLayout.add("Wrist Max", 120.0).withPosition(0, 0).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        var twistMinRotation            = manipulatorSettingsLayout.add("Twist Min", -180.0).withPosition(1, 2).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        var twistMaxRotation            = manipulatorSettingsLayout.add("Twist Max", 180.0).withPosition(1, 0).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        var ejectTime                   = manipulatorSettingsLayout.add("Eject Time", 0.0).withPosition(2, 2).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        var intakeSpeed                 = manipulatorSettingsLayout.add("Intake Speed", 0.0).withPosition(2, 0).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        
        registerCallback(armMaxExtension, () -> Arm.getInstance().setMaxArmPosition(armMaxExtension.getDouble(Constants.Arm.ARM_MAX_EXTENSION)));
        registerCallback(armMinAngle, () -> Arm.getInstance().setMinShoulderAngle(armMinAngle.getDouble(Constants.Arm.SHOULDER_MIN_ANGLE)));
        registerCallback(armMaxAngle, () -> Arm.getInstance().setMaxShoulderAngle(armMaxAngle.getDouble(Constants.Arm.SHOULDER_MAX_ANGLE)));

        registerCallback(wristMinAngle, () -> Manipulator.getInstance().setWristMinAngle(wristMinAngle.getDouble(Constants.Manipulator.WRIST_MIN_ANGLE)));
        registerCallback(wristMaxAngle, () -> Manipulator.getInstance().setWristMaxAngle(wristMaxAngle.getDouble(Constants.Manipulator.WRIST_MAX_ANGLE)));
        registerCallback(twistMinRotation, () -> Manipulator.getInstance().setTwistMinRotation(twistMinRotation.getDouble(Constants.Manipulator.TWIST_MIN_ROTATION)));
        registerCallback(twistMaxRotation, () -> Manipulator.getInstance().setTwistMaxRotation(twistMaxRotation.getDouble(Constants.Manipulator.TWIST_MAX_ROTATION)));
        registerCallback(ejectTime, () -> Manipulator.getInstance().setEjectTime(ejectTime.getDouble(Constants.Manipulator.EJECT_TIME)));
        registerCallback(intakeSpeed, () -> Manipulator.getInstance().setIntakeSpeed(intakeSpeed.getDouble(Constants.Manipulator.INTAKE_SPEED)));
    }

    public void registerCallback(GenericEntry entry, Runnable callback)
    {
        NetworkTableInstance.getDefault().addListener(entry, EnumSet.of(NetworkTableEvent.Kind.kValueRemote), event->
        {
            callback.run();
        });
    }
}
