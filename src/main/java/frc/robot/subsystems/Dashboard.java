package frc.robot.subsystems;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
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

    private GenericEntry                     _setpoint;
    private GenericEntry                     _invertMotor;
    private GenericEntry                     _enableButton;
    private GenericEntry                     _value;
    private GenericEntry                     _pThreshold;
    private GenericEntry                     _pAbove;
    private GenericEntry                     _pBelow;
    private GenericEntry                     _iThreshold;
    private GenericEntry                     _iAbove;
    private GenericEntry                     _iBelow;
    private GenericEntry                     _dThreshold;
    private GenericEntry                     _dAbove;
    private GenericEntry                     _dBelow;
    private GenericEntry                     _inputMin;
    private GenericEntry                     _inputMax;
    private GenericEntry                     _outputMin;
    private GenericEntry                     _outputMax;
    private SendableChooser<ConfigurablePID> _pidChooser;
    private ConfigurablePID                  _lastSelected;

    private Dashboard() 
    {
        // var tab                = Shuffleboard.getTab("Dashboard");
        
        // var visionLayout       = tab.getLayout("Vision", BuiltInLayouts.kGrid).withPosition(0, 0).withSize(8, 4).withProperties(Map.of("Number of columns", 2, "Number of rows", 1, "Label position", "TOP")); 
        // _hasTargetBox          = visionLayout.add("Has Target", false).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
        // _targetID              = visionLayout.add("Target ID", 0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();

        // var driveBaseLayout    = tab.getLayout("Drive Base", BuiltInLayouts.kGrid).withPosition(0, 4).withSize(4, 5).withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "TOP"));
        // _heading               = driveBaseLayout.add("Heading", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("Min", 0, "Max", 360, "Display value", false)).getEntry();
        // _odometer              = driveBaseLayout.add("Odometer", "(0,0)").withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
       
        // var swerveAnglesLayout = tab.getLayout("Swerve Angles", BuiltInLayouts.kGrid).withPosition(4, 4).withSize(4, 5).withProperties(Map.of("Number of columns", 2, "Number of rows", 2, "Label position", "TOP"));
        // _frAngle               = swerveAnglesLayout.add("FR", 0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        // _flAngle               = swerveAnglesLayout.add("FL", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        // _brAngle               = swerveAnglesLayout.add("BR", 0).withPosition(1, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        // _blAngle               = swerveAnglesLayout.add("BL", 0).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        
        // var armLayout          = tab.getLayout("Arm", BuiltInLayouts.kGrid).withPosition(8, 0).withSize(7, 9).withProperties(Map.of("Number of columns", 1, "Number of rows", 2, "Label position", "TOP"));
        // _shoulderAngle         = armLayout.add("Shoulder Angle", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kDial).withProperties(Map.of("Min", -135, "Max", 135, "Show text", false)).getEntry();
        // _extensionDistance     = armLayout.add("Extension Distance", 0).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kNumberBar).withProperties(Map.of("Min", 0, "Max", 48, "Show text", false)).getEntry();
       
        // _allianceBox           = tab.add("Alliance Color", false).withPosition(15, 0).withSize(6, 1).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("Color when true", "blue", "Color when false", "red")).getEntry();

        // var intakeLayout       = tab.getLayout("Intake", BuiltInLayouts.kGrid).withPosition(15, 2).withSize(6, 7).withProperties(Map.of("Number of columns", 1, "Number of rows", 3, "Label position", "TOP"));
        // _wristAngle            = intakeLayout.add("Wrist Angle", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        // _twistAngle            = intakeLayout.add("Twist Angle", 0).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        // _hasGamePiece          = intakeLayout.add("Has Game Piece", false).withPosition(0, 2).withSize(1, 2).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
        
        // var autonomousOptions  = tab.getLayout("Autonomous", BuiltInLayouts.kGrid).withPosition(21, 0).withSize(7, 9).withProperties(Map.of("Number of columns", 1, "Number of rows", 4, "Label position", "LEFT"));

        // SendableChooser<Integer> delayChooser = new SendableChooser<Integer>();
        // delayChooser.addOption("0", 0);
        // delayChooser.addOption("1", 1);
        // delayChooser.addOption("2", 2);
        // delayChooser.addOption("3", 3);
        // delayChooser.addOption("4", 4);
        // delayChooser.addOption("5", 5);
        // var delay = autonomousOptions.add("Delay Options", delayChooser).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kSplitButtonChooser);

        // SendableChooser<Integer> startPositionChooser = new SendableChooser<Integer>();
        // startPositionChooser.addOption("Position 1", 1);
        // startPositionChooser.addOption("Position 2", 2);
        // startPositionChooser.addOption("Position 3", 3);
        // var startPosition = autonomousOptions.add("Start Position", startPositionChooser).withPosition(0, 1).withSize(1, 1).withWidget(BuiltInWidgets.kComboBoxChooser);
        
        // SendableChooser<Integer> gamePiecesChooser = new SendableChooser<Integer>();
        // gamePiecesChooser.addOption("0", 0);
        // gamePiecesChooser.addOption("1", 1);
        // gamePiecesChooser.addOption("2", 2);
        // gamePiecesChooser.addOption("3", 3);
        // gamePiecesChooser.addOption("4", 4);
        // var gamePieces = autonomousOptions.add("Number Of Pieces", gamePiecesChooser).withPosition(0, 2).withSize(1, 1).withWidget(BuiltInWidgets.kSplitButtonChooser);

        // SendableChooser<Boolean> balanceChooser = new SendableChooser<Boolean>();
        // balanceChooser.addOption("true", true);
        // balanceChooser.addOption("false", false);
        // var balance = autonomousOptions.add("Balance Options", balanceChooser).withPosition(0, 3).withSize(1, 1).withWidget(BuiltInWidgets.kComboBoxChooser);
    
        // _autonomousLog = autonomousOptions.add("Auto Log", "").withPosition(0, 4).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();

        // var settingsTab                 = Shuffleboard.getTab("Settings");

        // //Drive
        // var driveSettingsLayout         = settingsTab.getLayout("Drive Subsystem", BuiltInLayouts.kGrid).withPosition(0, 0).withSize(4, 3);
        // var flOffset                    = driveSettingsLayout.add("FL Offset", 0.0).withPosition(0, 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView).getEntry();
        // var frOffset                    = driveSettingsLayout.add("FR Offset", 0.0).withPosition(2, 0).withSize(2,2).withWidget(BuiltInWidgets.kTextView).getEntry();
        // var blOffset                    = driveSettingsLayout.add("BL Offset", 0.0).withPosition(0, 2).withSize(2,2).withWidget(BuiltInWidgets.kTextView).getEntry();
        // var brOffset                    = driveSettingsLayout.add("BR Offset", 0.0).withPosition(2, 2).withSize(2,2).withWidget(BuiltInWidgets.kTextView).getEntry();
        
        // var resetSwerveOffsetEntry      = settingsTab.add("Reset Swerve Offset", false).withPosition(0, 4).withSize(4, 2).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        // var resetSwerveOffsetButton     = new DashboardButton(resetSwerveOffsetEntry);
        // resetSwerveOffsetButton.whenPressed(() -> {
        //     Drive.getInstance().zeroModuleRotations();
        //     flOffset.setDouble(Drive.getInstance().getSwerveModule(Constants.Drive.FL_INDEX).getRelativeZero());
        //     frOffset.setDouble(Drive.getInstance().getSwerveModule(Constants.Drive.FR_INDEX).getRelativeZero());
        //     blOffset.setDouble(Drive.getInstance().getSwerveModule(Constants.Drive.BL_INDEX).getRelativeZero());
        //     brOffset.setDouble(Drive.getInstance().getSwerveModule(Constants.Drive.BR_INDEX).getRelativeZero());
        // });

        // //Arm
        // var armSettingsLayout           = settingsTab.getLayout("Arm Subsystem", BuiltInLayouts.kGrid).withPosition(13, 0).withSize(4, 2);
        // var armMaxExtension             = armSettingsLayout.add("Arm Extension", 0.0).getEntry();
        // var armMinAngle                 = armSettingsLayout.add("Arm Min Angle", -90.0).getEntry();
        // var armMaxAngle                 = armSettingsLayout.add("Arm Max Angle", 90.0).getEntry();

        // //Manipulator
        // var manipulatorSettingsLayout   = settingsTab.getLayout("Manipulator Subsystem", BuiltInLayouts.kGrid).withPosition(4, 0).withSize(9, 2);
        // var wristMinAngle               = manipulatorSettingsLayout.add("Wrist Min", -120.0).withPosition(0, 2).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        // var wristMaxAngle               = manipulatorSettingsLayout.add("Wrist Max", 120.0).withPosition(0, 0).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        // var twistMinRotation            = manipulatorSettingsLayout.add("Twist Min", -180.0).withPosition(1, 2).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        // var twistMaxRotation            = manipulatorSettingsLayout.add("Twist Max", 180.0).withPosition(1, 0).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        // var ejectTime                   = manipulatorSettingsLayout.add("Eject Time", 0.0).withPosition(2, 2).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        // var intakeSpeed                 = manipulatorSettingsLayout.add("Intake Speed", 0.0).withPosition(2, 0).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();

        // registerCallback(flOffset, () -> Drive.getInstance().getSwerveModule(Constants.Drive.FL_INDEX).setRotationZero(flOffset.getDouble(Constants.Drive.FL_OFFSET)));
        // registerCallback(frOffset, () -> Drive.getInstance().getSwerveModule(Constants.Drive.FR_INDEX).setRotationZero(frOffset.getDouble(Constants.Drive.FR_OFFSET)));
        // registerCallback(blOffset, () -> Drive.getInstance().getSwerveModule(Constants.Drive.BL_INDEX).setRotationZero(blOffset.getDouble(Constants.Drive.BL_OFFSET)));
        // registerCallback(brOffset, () -> Drive.getInstance().getSwerveModule(Constants.Drive.BR_INDEX).setRotationZero(brOffset.getDouble(Constants.Drive.BR_OFFSET)));

        // registerCallback(armMaxExtension, () -> Arm.getInstance().setMaxArmExtension(armMaxExtension.getDouble(Constants.Arm.ARM_MAX_EXTENSION)));
        // registerCallback(armMinAngle, () -> Arm.getInstance().setMinShoulderAngle(armMinAngle.getDouble(Constants.Arm.SHOULDER_MIN_ANGLE)));
        // registerCallback(armMaxAngle, () -> Arm.getInstance().setMaxShoulderAngle(armMaxAngle.getDouble(Constants.Arm.SHOULDER_MAX_ANGLE)));

        // registerCallback(wristMinAngle, () -> Manipulator.getInstance().setWristMinAngle(wristMinAngle.getDouble(Constants.Manipulator.WRIST_MIN_ANGLE)));
        // registerCallback(wristMaxAngle, () -> Manipulator.getInstance().setWristMaxAngle(wristMaxAngle.getDouble(Constants.Manipulator.WRIST_MAX_ANGLE)));
        // registerCallback(twistMinRotation, () -> Manipulator.getInstance().setTwistMinRotation(twistMinRotation.getDouble(Constants.Manipulator.TWIST_MIN_ROTATION)));
        // registerCallback(twistMaxRotation, () -> Manipulator.getInstance().setTwistMaxRotation(twistMaxRotation.getDouble(Constants.Manipulator.TWIST_MAX_ROTATION)));
        // registerCallback(ejectTime, () -> Manipulator.getInstance().setEjectTime(ejectTime.getDouble(Constants.Manipulator.EJECT_TIME)));
        // registerCallback(intakeSpeed, () -> Manipulator.getInstance().setIntakeSpeed(intakeSpeed.getDouble(Constants.Manipulator.INTAKE_SPEED)));

        /*
         * PID Tuning
         */

        var pidTab = Shuffleboard.getTab("PID Tuning");

        _pidChooser = new SendableChooser<ConfigurablePID>();
        _pidChooser.setDefaultOption("None", null);
        pidTab.add("PID Controller", _pidChooser).withPosition(3, 1).withSize(17, 2).withWidget(BuiltInWidgets.kComboBoxChooser);

        _setpoint     = pidTab.add("Setpoint", 0).withPosition(20, 1).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        _invertMotor  = pidTab.add("Invert Motor", false).withPosition(20, 3).withSize(3, 2).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        
        _enableButton = pidTab.add("Enable", false).withPosition(20, 7).withSize(3, 2).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        _value        = pidTab.add("Value", 0).withPosition(20, 9).withSize(3, 2).withWidget(BuiltInWidgets.kTextView).getEntry();

        var configurationLayout = pidTab.getLayout("Configuration", BuiltInLayouts.kGrid).withPosition(3, 3).withSize(17, 8).withProperties(Map.of("Number of columns", 1, "Number of rows", 4, "Label position", "LEFT"));

        var pLayout      = configurationLayout.getLayout("P", BuiltInLayouts.kGrid).withPosition(0, 0).withSize(1, 1).withProperties(Map.of("Number of columns", 3, "Number of rows", 1, "Label position", "TOP"));
        _pThreshold   = pLayout.add("Threshold", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _pAbove       = pLayout.add("Above",     0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _pBelow       = pLayout.add("Below",     0).withPosition(2, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();

        var iLayout      = configurationLayout.getLayout("I", BuiltInLayouts.kGrid).withPosition(0, 1).withSize(1, 1).withProperties(Map.of("Number of columns", 3, "Number of rows", 1, "Label position", "TOP"));
        _iThreshold   = iLayout.add("Threshold", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _iAbove       = iLayout.add("Above",     0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _iBelow       = iLayout.add("Below",     0).withPosition(2, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();

        var dLayout      = configurationLayout.getLayout("D", BuiltInLayouts.kGrid).withPosition(0, 2).withSize(1, 1).withProperties(Map.of("Number of columns", 3, "Number of rows", 1, "Label position", "TOP"));
        _dThreshold   = dLayout.add("Threshold", 0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _dAbove       = dLayout.add("Above",     0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _dBelow       = dLayout.add("Below",     0).withPosition(2, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();

        var limitsLayout = configurationLayout.getLayout(" ", BuiltInLayouts.kGrid).withPosition(0, 3).withSize(1, 1).withProperties(Map.of("Number of columns", 4, "Number of rows", 1, "Label position", "TOP"));
        _inputMin     = limitsLayout.add("Input Min",  0).withPosition(0, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _inputMax     = limitsLayout.add("Input Max",  0).withPosition(1, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _outputMin    = limitsLayout.add("Output Min", 0).withPosition(2, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
        _outputMax    = limitsLayout.add("Output Max", 0).withPosition(3, 0).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();

        registerPIDCallback(_setpoint,    config -> config.pid.setSetpoint(_setpoint.getDouble(0), config.valueGetterFunction.getAsDouble(), true));
        registerPIDCallback(_invertMotor, config -> config.motor.setInverted(_invertMotor.getBoolean(false)));

        registerPIDCallback(_pThreshold, config -> config.pid.setCoefficient(Coefficient.P, _pThreshold.getDouble(0), _pAbove.getDouble(0), _pBelow.getDouble(0)));
        registerPIDCallback(_pAbove,     config -> config.pid.setCoefficient(Coefficient.P, _pThreshold.getDouble(0), _pAbove.getDouble(0), _pBelow.getDouble(0)));
        registerPIDCallback(_pBelow,     config -> config.pid.setCoefficient(Coefficient.P, _pThreshold.getDouble(0), _pAbove.getDouble(0), _pBelow.getDouble(0)));

        registerPIDCallback(_iThreshold, config -> config.pid.setCoefficient(Coefficient.I, _iThreshold.getDouble(0), _iAbove.getDouble(0), _iBelow.getDouble(0)));
        registerPIDCallback(_iAbove,     config -> config.pid.setCoefficient(Coefficient.I, _iThreshold.getDouble(0), _iAbove.getDouble(0), _iBelow.getDouble(0)));
        registerPIDCallback(_iBelow,     config -> config.pid.setCoefficient(Coefficient.I, _iThreshold.getDouble(0), _iAbove.getDouble(0), _iBelow.getDouble(0)));

        registerPIDCallback(_dThreshold, config -> config.pid.setCoefficient(Coefficient.D, _dThreshold.getDouble(0), _dAbove.getDouble(0), _dBelow.getDouble(0)));
        registerPIDCallback(_dAbove,     config -> config.pid.setCoefficient(Coefficient.D, _dThreshold.getDouble(0), _dAbove.getDouble(0), _dBelow.getDouble(0)));
        registerPIDCallback(_dBelow,     config -> config.pid.setCoefficient(Coefficient.D, _dThreshold.getDouble(0), _dAbove.getDouble(0), _dBelow.getDouble(0)));

        registerPIDCallback(_inputMin, config -> config.pid.setInputRange(_inputMin.getDouble(0), _inputMax.getDouble(0)));
        registerPIDCallback(_inputMax, config -> config.pid.setInputRange(_inputMin.getDouble(0), _inputMax.getDouble(0)));

        registerPIDCallback(_outputMin, config -> config.pid.setOutputRange(_outputMin.getDouble(0), _outputMax.getDouble(0)));
        registerPIDCallback(_outputMax, config -> config.pid.setOutputRange(_outputMin.getDouble(0), _outputMax.getDouble(0)));
    }

    public void registerCallback(GenericEntry entry, Runnable callback)
    {
        NetworkTableInstance.getDefault().addListener(entry, EnumSet.of(NetworkTableEvent.Kind.kValueRemote), event->
        {
            callback.run();
        });
    }

    private void registerPIDCallback(GenericEntry entry, Consumer<ConfigurablePID> callback)
    {
        NetworkTableInstance.getDefault().addListener(entry, EnumSet.of(NetworkTableEvent.Kind.kValueRemote), event ->
        {
            var pid = _pidChooser.getSelected();

            if (pid != null)
            {
                callback.accept(pid);
            }

            _enableButton.setBoolean(false);
        });
    }

    public void registerPIDController(String displayName, PIDControl pid, CANSparkMax motor, DoubleSupplier valueGetterFunction)
    {
        _pidChooser.addOption(displayName, new ConfigurablePID(pid, motor, valueGetterFunction));
    }

    @Override
    public void periodic()
    {
    //     _allianceBox.setBoolean(DriverStation.getAlliance() == Alliance.Blue);

    //     _shoulderAngle.setDouble(Double.parseDouble(String.format("%6.2f", Arm.getInstance().getShoulderAngle())));
    //     _extensionDistance.setDouble(Double.parseDouble(String.format("%6.2f", Arm.getInstance().getExtensionPosition())));

    //     _wristAngle.setDouble(Double.parseDouble(String.format("%6.2f", Manipulator.getInstance().getWristAngle())));
    //     _twistAngle.setDouble(Double.parseDouble(String.format("%6.2f", Manipulator.getInstance().getTwistAngle())));

    //     _hasTargetBox.setBoolean(Vision.getInstance().frontCamHasTargets()|| Vision.getInstance().rearCamHasTargets());

    //     _heading.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getHeading())));
    //     _odometer.setString(String.format("%s", Drive.getInstance().getFieldPosition()));

    //     _frAngle.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getSwerveModule(Constants.Drive.FR_INDEX).getHeading())));
    //     _flAngle.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getSwerveModule(Constants.Drive.FL_INDEX).getHeading())));
    //     _brAngle.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getSwerveModule(Constants.Drive.BR_INDEX).getHeading())));
    //     _blAngle.setDouble(Double.parseDouble(String.format("%6.2f", Drive.getInstance().getSwerveModule(Constants.Drive.BL_INDEX).getHeading())));
        
    //     _hasGamePiece.setBoolean(Manipulator.getInstance().isIntakeSensorActive());

        // Only do pid control logic if we're in test mode
        if (DriverStation.isTest())
        {
            var config = _pidChooser.getSelected();

            // A different option was chosen from the dropdown
            if (_lastSelected != config)
            {
                // Shut off execution of the current pid controller
                _enableButton.setBoolean(false);

                if (_lastSelected != null)
                {
                    _lastSelected.motor.setVoltage(0);
                }

                // Only continue if we've actually selected something
                if (config != null)
                {
                    // Update coefficients
                    var p = config.pid.getCoefficient(Coefficient.P);
                    var i = config.pid.getCoefficient(Coefficient.I);
                    var d = config.pid.getCoefficient(Coefficient.D);

                    _pThreshold.setDouble(p.threshold);
                    _pAbove.setDouble(p.kAbove);
                    _pBelow.setDouble(p.kBelow);

                    _iThreshold.setDouble(i.threshold);
                    _iAbove.setDouble(i.kAbove);
                    _iBelow.setDouble(i.kBelow);

                    _dThreshold.setDouble(d.threshold);
                    _dAbove.setDouble(d.kAbove);
                    _dBelow.setDouble(d.kBelow);

                    _inputMin.setDouble(config.pid.getInputMinimum());
                    _inputMax.setDouble(config.pid.getInputMaximum());

                    _outputMin.setDouble(config.pid.getOutputMinimum());
                    _outputMax.setDouble(config.pid.getOutputMaximum());

                    _setpoint.setDouble(0);
                    _invertMotor.setBoolean(config.motor.getInverted());
                    _value.setDouble(0);
                }
            }

            if (config != null)
            {
                _value.setDouble(config.valueGetterFunction.getAsDouble());

                if (_enableButton.getBoolean(false))
                {
                    config.motor.setVoltage(config.pid.calculate(config.valueGetterFunction.getAsDouble()) * Constants.MOTOR_VOLTAGE);
                }
                else
                {
                    config.motor.setVoltage(0);
                }
            }

            _lastSelected = config;
        }
        else
        {
            _setpoint.setDouble(0);
            _enableButton.setBoolean(false);
        }
    }

    private class ConfigurablePID
    {
        public final PIDControl pid;
        public final DoubleSupplier valueGetterFunction;
        public final CANSparkMax motor;

        public ConfigurablePID(PIDControl pid, CANSparkMax motor, DoubleSupplier valueGetterFunction)
        {
            this.pid = pid;
            this.motor = motor;
            this.valueGetterFunction = valueGetterFunction;
        }
    }
}
