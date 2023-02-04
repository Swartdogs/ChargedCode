package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.Drive;

public class Dashboard extends SubsystemBase 
{
    private static Dashboard _instance;

    public static Dashboard getInstance()
    {
        if (_instance == null)
        {
            _instance = new Dashboard();
        }

        return _instance;
    }

    private GenericEntry _allianceBox;
    private GenericEntry _shoulderAngle;
    private GenericEntry _extensionDistance;
    private GenericEntry _wristAngle;
    private GenericEntry _twistAngle;
    private GenericEntry _hasTargetBox;
    private GenericEntry _targetID;
    private GenericEntry _heading;
    private GenericEntry _odometer;
    private GenericEntry _frAngle;
    private GenericEntry _flAngle;
    private GenericEntry _brAngle;
    private GenericEntry _blAngle;
    private GenericEntry _hasGamePiece;
    private GenericEntry _autonomousLog;

    private Dashboard() 
    {
        var tab                = Shuffleboard.getTab("Dashboard");
        
        var visionLayout       = tab.getLayout("Vision", BuiltInLayouts.kGrid).withPosition(0, 0).withSize(8, 4).withProperties(Map.of("Number of columns", 2, "Number of rows", 1, "Label position", "TOP")); 
        _hasTargetBox          = visionLayout.add("Has Target", false).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
        _targetID              = visionLayout.add("Target ID", 0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();

        var driveBaseLayout    = tab.getLayout("Drive Base", BuiltInLayouts.kGrid).withPosition(0, 4).withSize(4, 5).withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "TOP"));
        _heading               = driveBaseLayout.add("Heading", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("Min", 0, "Max", 360, "Display value", false)).getEntry();
        _odometer              = driveBaseLayout.add("Odometer", "(0,0)").withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
       
        var swerveAnglesLayout = tab.getLayout("Swerve Angles", BuiltInLayouts.kGrid).withPosition(4, 4).withSize(4, 5).withProperties(Map.of("Number of columns", 2, "Number of rows", 2, "Label position", "TOP"));
        _frAngle               = swerveAnglesLayout.add("FR", 0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _flAngle               = swerveAnglesLayout.add("FL", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _brAngle               = swerveAnglesLayout.add("BR", 0).withPosition(1, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _blAngle               = swerveAnglesLayout.add("BL", 0).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        
        var armLayout          = tab.getLayout("Arm", BuiltInLayouts.kGrid).withPosition(8, 0).withSize(7, 9).withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "TOP"));
        _shoulderAngle         = armLayout.add("Shoulder Angle", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kDial).withProperties(Map.of("Min", -135, "Max", 135, "Show text", false)).getEntry();
        _extensionDistance     = armLayout.add("Extension Distance", 0).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kNumberBar).withProperties(Map.of("Min", 0, "Max", 48, "Show text", false)).getEntry();
       
        _allianceBox           = tab.add("Alliance Color", false).withPosition(15, 0).withSize(6, 1).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("Color when true", "blue", "Color when false", "red")).getEntry();

        var intakeLayout       = tab.getLayout("Intake", BuiltInLayouts.kGrid).withPosition(15, 2).withSize(6, 7).withProperties(Map.of("Number of columns", 1, "Number of rows", 3, "Label position", "TOP"));
        _wristAngle            = intakeLayout.add("Wrist Angle", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _twistAngle            = intakeLayout.add("Twist Angle", 0).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _hasGamePiece          = intakeLayout.add("Has Game Piece", false).withPosition(0, 2).withSize(1, 2).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
        
        var autonomousOptions  = tab.getLayout("Autonomous", BuiltInLayouts.kGrid).withPosition(21, 0).withSize(7, 9).withProperties(Map.of("Number of columns", 1, "Number of rows", 4, "Label position", "LEFT"));

        SendableChooser<Integer> delayChooser = new SendableChooser<Integer>();
        delayChooser.addOption("0", 0);
        delayChooser.addOption("1", 1);
        delayChooser.addOption("2", 2);
        delayChooser.addOption("3", 3);
        delayChooser.addOption("4", 4);
        delayChooser.addOption("5", 5);
        var delay = autonomousOptions.add("Delay Options", delayChooser).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kSplitButtonChooser);

        SendableChooser<Integer> startPositionChooser = new SendableChooser<Integer>();
        startPositionChooser.addOption("Position 1", 1);
        startPositionChooser.addOption("Position 2", 2);
        startPositionChooser.addOption("Position 3", 3);
        var startPosition = autonomousOptions.add("Start Position", startPositionChooser).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kComboBoxChooser);
        
        SendableChooser<Integer> gamePiecesChooser = new SendableChooser<Integer>();
        gamePiecesChooser.addOption("0", 0);
        gamePiecesChooser.addOption("1", 1);
        gamePiecesChooser.addOption("2", 2);
        gamePiecesChooser.addOption("3", 3);
        gamePiecesChooser.addOption("4", 4);
        var gamePieces = autonomousOptions.add("Number Of Pieces", gamePiecesChooser).withPosition(0, 2).withSize(1, 1).withWidget(BuiltInWidgets.kSplitButtonChooser);

        SendableChooser<Boolean> balanceChooser = new SendableChooser<Boolean>();
        balanceChooser.addOption("true", true);
        balanceChooser.addOption("false", false);
        var balance = autonomousOptions.add("Balance Options", balanceChooser).withPosition(0, 3).withSize(1, 1).withWidget(BuiltInWidgets.kComboBoxChooser);
    
        _autonomousLog = autonomousOptions.add("Auto Log", "").withPosition(0, 4).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();

        var settingsTab = Shuffleboard.getTab("settings");

        var resetModuleZeroButtonEntry = settingsTab.add("reset modules", false).withWidget(BuiltInWidgets.kToggleButton).withPosition(0, 0).withSize(4, 4).getEntry();
        var resetModuleZeroButton = new DashboardButton(resetModuleZeroButtonEntry);
        resetModuleZeroButton.whenPressed(() -> {
            Drive.getInstance().zeroModuleRotations();
        });
    }

    @Override
    public void periodic()
    {
        // _allianceBox.setBoolean(DriverStation.getAlliance() == Alliance.Blue);

        // _shoulderAngle.setDouble(Double.parseDouble(String.format("%6.2f", Arm.getInstance().getShoulderAngle())));
        // _extensionDistance.setDouble(Double.parseDouble(String.format("%6.2f", Arm.getInstance().getExtensionPosition())));

        // _wristAngle.setDouble(Double.parseDouble(String.format("%6.2f", Manipulator.getInstance().getWristAngle())));
        // _twistAngle.setDouble(Double.parseDouble(String.format("%6.2f", Manipulator.getInstance().getTwistAngle())));

        // _hasTargetBox.setBoolean(Vision.getInstance().frontCamHasTargets()|| Vision.getInstance().rearCamHasTargets());

        // _heading.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getHeading())));
        // _odometer.setString(String.format("%s", Drive.getInstance().getFieldPosition()));

        _frAngle.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getModuleHeading(Constants.Drive.FR_INDEX))));
        _flAngle.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getModuleHeading(Constants.Drive.FL_INDEX))));
        _brAngle.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getModuleHeading(Constants.Drive.BR_INDEX))));
        _blAngle.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getModuleHeading(Constants.Drive.BL_INDEX))));
        
        // _hasGamePiece.setBoolean(Manipulator.getInstance().isIntakeSensorActive());
    }
}
