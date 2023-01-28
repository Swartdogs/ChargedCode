package frc.robot.subsystems;

import java.util.EnumSet;
import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Dashboard extends SubsystemBase 
{
    private GenericEntry _allianceBox;

    public Dashboard() 
    {
        var tab = Shuffleboard.getTab("Dashboard");
        
        _allianceBox = tab.add("AllianceColorBox", false).withPosition(0, 0).withSize(28, 1).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("Color when true", "blue", "Color when false", "red")).getEntry();
        
        var visionLayout = tab.getLayout("VisionBox", BuiltInLayouts.kGrid).withPosition(0, 2).withSize(8, 2).withProperties(Map.of("Number of columns", 2, "Number of rows", 1, "Label position", "TOP")); 
        var hasTargetBox = visionLayout.add("hasTargetBox", false).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox);
        var targetID = visionLayout.add("TargetID", 0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);

        var driveBaseLayout = tab.getLayout("DriveBaseLayout", BuiltInLayouts.kGrid).withPosition(0, 5).withSize(4, 4).withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "TOP"));
        //var heading = driveBaseLayout.add("Heading", 0).withProperties(Map.of("Min", -2, "Max", 12)).withSize(4, 1).withPosition(0, 0).withWidget(BuiltInWidgets.kNumberBar);
        var heading = driveBaseLayout.add("Heading", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
        var odometer = driveBaseLayout.add("Odometer", "(0,0)").withPosition(0, 1).withSize(1,1).withWidget(BuiltInWidgets.kTextView);
       
        var swerveAnglesLayout = tab.getLayout("SwerveAngles", BuiltInLayouts.kGrid).withPosition(4, 5).withSize(4, 4).withProperties(Map.of("Number of columns", 2, "Number of rows", 2, "Label position", "TOP"));
        var frAngle = swerveAnglesLayout.add("FR", 0).withPosition(1, 0).withSize(1,1).withWidget(BuiltInWidgets.kTextView);
        var flAngle = swerveAnglesLayout.add("FL", 0).withPosition(0, 0).withSize(1,1).withWidget(BuiltInWidgets.kTextView);
        var brAngle = swerveAnglesLayout.add("BR", 0).withPosition(1, 1).withSize(1,1).withWidget(BuiltInWidgets.kTextView);
        var blAngle = swerveAnglesLayout.add("BL", 0).withPosition(0, 1).withSize(1,1).withWidget(BuiltInWidgets.kTextView);
        
        var armLayout = tab.getLayout("Arm", BuiltInLayouts.kGrid).withPosition(8, 2).withSize(8,1).withProperties(Map.of("Number of columns", 2, "Number of rows", 1, "Label position", "TOP"));
        var shoulderAngle = armLayout.add("ShoulderAngle", 0).withPosition(0, 0).withSize(1,1).withWidget(BuiltInWidgets.kTextView);
        var extensionDistance = armLayout.add("ExtensionDistance", 0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
       
        var intakeLayout = tab.getLayout("IntakeLayout", BuiltInLayouts.kGrid).withPosition(8, 5).withSize(8, 4).withProperties(Map.of("Number of columns", 2, "Number of rows", 2, "Label position", "TOP"));
        var wristAngle = intakeLayout.add("WristAngle", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
        var twistAngle = intakeLayout.add("TwistAngle", 0).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView);
        var hasGamePiece = intakeLayout.add("HasGamePiece", false).withPosition(1, 0).withSize(1, 2).withWidget(BuiltInWidgets.kBooleanBox);
        
        var autonomousOptions = tab.getLayout("Autonomous", BuiltInLayouts.kGrid).withPosition(16, 2).withSize(12,7).withProperties(Map.of("Number of columns", 1, "Number of rows", 4, "Label position", "LEFT"));
        SendableChooser<Integer> startPositionChooser = new SendableChooser<Integer>();
        startPositionChooser.addOption("Position 1", 1);
        startPositionChooser.addOption("Position 2", 2);
        startPositionChooser.addOption("Position 3", 3);
        var startPosition = autonomousOptions.add("Start Position", startPositionChooser).withPosition(0, 0).withSize(1,1).withWidget(BuiltInWidgets.kComboBoxChooser);
        
        SendableChooser<Integer> gamePiecesChooser = new SendableChooser<Integer>();
        gamePiecesChooser.addOption("0", 0);
        gamePiecesChooser.addOption("1", 1);
        gamePiecesChooser.addOption("2", 2);
        gamePiecesChooser.addOption("3", 3);
        gamePiecesChooser.addOption("4", 4);
        var gamePieces = autonomousOptions.add("Number Of Pieces", gamePiecesChooser).withPosition(0, 1).withSize(1,1).withWidget(BuiltInWidgets.kSplitButtonChooser);

        SendableChooser<Boolean> balanceChooser = new SendableChooser<Boolean>();
        balanceChooser.addOption("true", true);
        balanceChooser.addOption("false", false);
        var balance = autonomousOptions.add("Balance Options", balanceChooser).withPosition(0, 2).withSize(1,1).withWidget(BuiltInWidgets.kComboBoxChooser);

        SendableChooser<Integer> delayChooser = new SendableChooser<Integer>();
        delayChooser.addOption("0", 0);
        delayChooser.addOption("1", 1);
        delayChooser.addOption("2", 2);
        delayChooser.addOption("3", 3);
        delayChooser.addOption("4", 4);
        delayChooser.addOption("5", 5);
        var delay = autonomousOptions.add("Delay Options", delayChooser).withPosition(0, 3).withSize(1,1).withWidget(BuiltInWidgets.kSplitButtonChooser);

        // What is a default value for a camerastream?
        //var camera = tab.add("Camera", false).withPosition(0, 0).withSize(10, 10).withWidget(BuiltInWidgets.kCameraStream);
    }

    @Override
    public void periodic()
    {
        _allianceBox.setBoolean(DriverStation.getAlliance() == Alliance.Blue);
    }

}
