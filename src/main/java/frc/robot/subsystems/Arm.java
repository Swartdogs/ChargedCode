package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.DutyCycleEncoderSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.groups.GrpSetArmPosition;

public class Arm extends SubsystemBase 
{
    private static Arm _instance;

    public static Arm getInstance()
    {
        if(_instance == null) 
        {
            _instance = new Arm();
        }

        return _instance;
    }

    public enum ArmPosition
    {
        High,
        Middle,
        Low,
        Substation,
        Ground,
        Stow
    }

    public enum ArmSide
    {
        Front,
        Back
    }

    public enum HandMode
    {
        Cube,
        Cone
    }
    
    // Extension Motor
    private DigitalInput        _limitSwitch;
    private CANSparkMax         _extensionMotor;
    private RelativeEncoder     _extensionEncoder;
    private PIDControl          _extensionPid;
    private boolean             _extensionPidActive;

    // Shoulder Motor
    private DutyCycleEncoder    _pitchEncoder;
    private CANSparkMax         _pitchMotor;
    private PIDControl          _shoulderPid;

    // Wrist Controls
    private CANSparkMax         _wristMotor;
    private DutyCycleEncoder    _wristEncoder;
    private PIDControl          _wristPID;

    // Twist Motion Controls
    private CANSparkMax         _twistMotor;
    private DutyCycleEncoder    _twistEncoder;
    private PIDControl          _twistPID;
    
    // Settings
    private double              _minShoulderAngle;
    private double              _maxShoulderAngle;
    private double              _maxArmExtension;
    private double              _wristMinAngle;
    private double              _wristMaxAngle;
    private double              _twistMinRotation;
    private double              _twistMaxRotation;
    
    // Controls
    private ArmPosition         _armPosition;
    private boolean             _isFlipped;

    // Simulation
    private DIOSim              _limitSwitchSim;
    private DutyCycleEncoderSim _pitchEncoderSim;
    private DutyCycleEncoderSim _wristEncoderSim;
    private DutyCycleEncoderSim _twistEncoderSim;
    
    @SuppressWarnings("resource")
    private Arm() 
    {
        CANSparkMax followerPitchMotor  = new CANSparkMax(Constants.Arm.FOLLOWER_PITCH_MOTOR_CAN_ID, MotorType.kBrushless);
        
        _limitSwitch        = new DigitalInput(Constants.Arm.LIMIT_SWITCH_CHANNEL);
        _extensionMotor     = new CANSparkMax(Constants.Arm.LINEAR_MOTOR_CAN_ID, MotorType.kBrushless);
        _extensionEncoder   = _extensionMotor.getEncoder();
        _extensionPid       = new PIDControl();
        _extensionPidActive = true; 

        _pitchEncoder       = new DutyCycleEncoder(Constants.Arm.PITCH_ENCODER_CHANNEL);
        _pitchMotor         = new CANSparkMax(Constants.Arm.PITCH_MOTOR_CAN_ID, MotorType.kBrushless);
        _shoulderPid        = new PIDControl();

        _wristMotor         = new CANSparkMax(Constants.Arm.WRIST_MOTOR_CAN_ID, MotorType.kBrushless);
        _wristEncoder       = new DutyCycleEncoder(Constants.Arm.WRIST_ENCODER_PORT);
        _wristPID           = new PIDControl();

        _twistMotor         = new CANSparkMax(Constants.Arm.TWIST_MOTOR_CAN_ID, MotorType.kBrushless);
        _twistEncoder       = new DutyCycleEncoder(Constants.Arm.TWIST_ENCODER_PORT);
        _twistPID           = new PIDControl();

        _minShoulderAngle   = Constants.Arm.SHOULDER_MIN_ANGLE;
        _maxShoulderAngle   = Constants.Arm.SHOULDER_MAX_ANGLE;
        _maxArmExtension    = Constants.Arm.ARM_MAX_EXTENSION;
        _wristMinAngle      = Constants.Arm.WRIST_MAX_ANGLE;
        _wristMaxAngle      = Constants.Arm.WRIST_MIN_ANGLE;
        _twistMinRotation   = Constants.Arm.TWIST_MIN_ROTATION;
        _twistMaxRotation   = Constants.Arm.TWIST_MAX_ROTATION;
        
        _armPosition        = ArmPosition.Stow;
        _isFlipped          = false;

        _shoulderPid.setCoefficient(Coefficient.P, 0, 0.001, 0);
        _shoulderPid.setCoefficient(Coefficient.I, 0, 0, 0);
        _shoulderPid.setCoefficient(Coefficient.D, 0, 0, 0);
        _shoulderPid.setInputRange(-135, 135);
        _shoulderPid.setOutputRange(-1, 1);
        _shoulderPid.setSetpointDeadband(1);

        _extensionPid.setCoefficient(Coefficient.P, 0, 0.001, 0);
        _extensionPid.setCoefficient(Coefficient.I, 0, 0, 0);
        _extensionPid.setCoefficient(Coefficient.D, 0, 0, 0);
        _extensionPid.setInputRange(0, 50);
        _extensionPid.setOutputRange(-1, 1);
        _extensionPid.setSetpointDeadband(1);

        _wristPID.setCoefficient(Coefficient.P, 0, 0.001, 0);
        _wristPID.setCoefficient(Coefficient.I, 0, 0, 0);
        _wristPID.setCoefficient(Coefficient.D, 0, 0, 0);
        _wristPID.setInputRange(Constants.Arm.WRIST_MIN_ANGLE, Constants.Arm.WRIST_MAX_ANGLE);
        _wristPID.setOutputRange(-1, 1);
        _wristPID.setSetpointDeadband(1);

        _twistPID.setCoefficient(Coefficient.P, 0, 0.001, 0);
        _twistPID.setCoefficient(Coefficient.I, 0, 0, 0);
        _twistPID.setCoefficient(Coefficient.D, 0, 0, 0);
        _twistPID.setInputRange(Constants.Arm.TWIST_MIN_ROTATION, Constants.Arm.TWIST_MAX_ROTATION);
        _twistPID.setOutputRange(-1, 1);
        _twistPID.setSetpointDeadband(1);

        _extensionMotor.setInverted(true);

        _extensionEncoder.setPositionConversionFactor(1);

        followerPitchMotor.follow(_pitchMotor, true);

        if (RobotBase.isSimulation())
        {
            _extensionMotor.setInverted(false);

            _limitSwitchSim  = new DIOSim(_limitSwitch);
            _pitchEncoderSim = new DutyCycleEncoderSim(_pitchEncoder);
            _wristEncoderSim = new DutyCycleEncoderSim(_wristEncoder);
            _twistEncoderSim = new DutyCycleEncoderSim(_twistEncoder);

            REVPhysicsSim.getInstance().addSparkMax(_extensionMotor, DCMotor.getNEO(1));
            REVPhysicsSim.getInstance().addSparkMax(_pitchMotor, DCMotor.getNeo550(2));
            REVPhysicsSim.getInstance().addSparkMax(_wristMotor,  DCMotor.getNeo550(1));
            REVPhysicsSim.getInstance().addSparkMax(_twistMotor,  DCMotor.getNeo550(1));
        }

        RobotLog.getInstance().log("Created Arm Subsystem");
    }

    public boolean isLimitSwitchPressed()
    {
        return _limitSwitch.get();
    }

    public double getExtensionPosition()
    {
        return _extensionEncoder.getPosition();
    }

    public double getShoulderAngle()
    {
        return _pitchEncoder.get();
    }

    public void setArmPosition(ArmPosition position)
    {
        _armPosition = position;
    }

    public ArmPosition getArmPosition()
    {
        return _armPosition;
    }

    public double getWristAngle()
    {
        return _wristEncoder.get();
    }

    public double getTwistAngle()
    {
        return _twistEncoder.get();
    }

    // Settings Functions
    public void setMinShoulderAngle(double angle)
    {
        _minShoulderAngle = angle;
        _shoulderPid.setInputRange(_minShoulderAngle, _maxShoulderAngle);
    }

    public void setMaxShoulderAngle(double angle)
    {
        _maxShoulderAngle = angle;
        _shoulderPid.setInputRange(_minShoulderAngle, _maxShoulderAngle);
    }

    public void setMaxArmExtension(double position)
    {
        _maxArmExtension = position;
        _extensionPid.setInputRange(0, _maxArmExtension);
    }

    public void setWristMinAngle(double angle)
    {
        _wristMinAngle = angle;
        _wristPID.setInputRange(_wristMinAngle, _wristMaxAngle);
    }

    public void setWristMaxAngle(double angle)
    {
        _wristMaxAngle = angle;
        _wristPID.setInputRange(_wristMinAngle, _wristMaxAngle);
    }

    public void setTwistMinRotation(double rotation)
    {
        _twistMinRotation = rotation;
        _twistPID.setInputRange(_twistMinRotation, _twistMaxRotation);
    }

    public void setTwistMaxRotation(double rotation)
    {
        _twistMaxRotation = rotation;
        _twistPID.setInputRange(_twistMinRotation, _twistMaxRotation);
    }

    @Override 
    public void periodic()
    {
        _pitchMotor.setVoltage(_shoulderPid.calculate(getShoulderAngle()) * Constants.MOTOR_VOLTAGE);
        _twistMotor.setVoltage(_twistPID.calculate(getTwistAngle()) * Constants.MOTOR_VOLTAGE);
        _wristMotor.setVoltage(_wristPID.calculate(getWristAngle()) * Constants.MOTOR_VOLTAGE);

        if (_extensionPidActive) 
        {
            _extensionMotor.setVoltage(_extensionPid.calculate(getExtensionPosition()) * Constants.MOTOR_VOLTAGE);
        }
    }

    @Override
    public void simulationPeriodic()
    {
        _pitchMotor.getEncoder().setPosition(clamp(_pitchMotor.getEncoder().getPosition(), Constants.Arm.SHOULDER_MIN_ANGLE, Constants.Arm.SHOULDER_MAX_ANGLE));
        _wristMotor.getEncoder().setPosition(clamp(_wristMotor.getEncoder().getPosition(), Constants.Arm.WRIST_MIN_ANGLE, Constants.Arm.WRIST_MAX_ANGLE));
        _twistMotor.getEncoder().setPosition(clamp(_twistMotor.getEncoder().getPosition(), Constants.Arm.TWIST_MIN_ROTATION, Constants.Arm.TWIST_MAX_ROTATION));
        
        _extensionEncoder.setPosition(clamp(_extensionEncoder.getPosition(), 0, Constants.Arm.ARM_MAX_EXTENSION));

        _limitSwitchSim.setValue(_extensionEncoder.getPosition() <= 0);
        _pitchEncoderSim.set(_pitchMotor.getEncoder().getPosition());
        _wristEncoderSim.set(_wristMotor.getEncoder().getPosition());
        _twistEncoderSim.set(_twistMotor.getEncoder().getPosition());
    }

    private double clamp(double in, double min, double max)
    {
        return Math.max(min, Math.min(max, in));
    }

    // Commands
    public Command modifyExtensionPositionCommand(double modification)
    {
        return this
            .runOnce(() -> _extensionPid.setSetpoint(_extensionPid.getSetpoint() + modification, getExtensionPosition()))
            .andThen(Commands.waitUntil(_extensionPid::atSetpoint));
    }

    public Command modifyShoulderAngleCommand(double modification)
    {
        return this
            .runOnce(() -> _shoulderPid.setSetpoint(_shoulderPid.getSetpoint() + modification, getShoulderAngle()))
            .andThen(Commands.waitUntil(_shoulderPid::atSetpoint));
    }

    public Command resetCommand()
    {
        return this
            .run(() -> _extensionMotor.setVoltage(-0.5 * Constants.MOTOR_VOLTAGE))
            .until(() -> _limitSwitch.get())
            .finallyDo(interrupted -> {
                _extensionMotor.setVoltage(0);
                _extensionEncoder.setPosition(0); 
           });
    }

    public Command setExtensionPositionCommand(double position)
    {
        return Commands
            .runOnce(() -> _extensionPid.setSetpoint(position, getExtensionPosition()))
            .andThen(Commands.waitUntil(_extensionPid::atSetpoint));
    }

    public Command setShoulderAngleCommand(double angle)
    {
        return Commands
            .runOnce(() -> _shoulderPid.setSetpoint(angle, getShoulderAngle()))
            .andThen(Commands.waitUntil(_shoulderPid::atSetpoint));
    }

    public Command setTwistAngleCommand(double angle)
    {
        return Commands
            .either
            (
                Commands.runOnce(() -> _twistPID.setSetpoint(180 - angle, getTwistAngle())),
                Commands.runOnce(() -> _twistPID.setSetpoint(angle, getTwistAngle())),
                () -> _isFlipped
            )
            .andThen(Commands.waitUntil(_twistPID::atSetpoint));
    }

    public Command setWristAngleCommand(double angle)
    {
        return Commands
            .runOnce(() -> _wristPID.setSetpoint(angle, getWristAngle()))
            .andThen(Commands.waitUntil(_wristPID::atSetpoint));
    }

    public Command handFlipCommand()
    {
        return
            new ProxyCommand(() -> setTwistAngleCommand(_twistPID.getSetpoint()))
            .beforeStarting(() -> _isFlipped = !_isFlipped);
    }

    public Command stowCommand()
    {
        return 
            new GrpSetArmPosition(ArmPosition.Stow)
            .beforeStarting(() -> _isFlipped = false);
    }
}
