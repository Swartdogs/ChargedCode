package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.DutyCycleEncoderSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

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

    private enum ResetStatus
    {
        NotReset,
        Resetting,
        Reset
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

    // Shoulder Motor
    private DutyCycleEncoder    _shoulderEncoder;
    private CANSparkMax         _shoulderMotor;
    private PIDControl          _shoulderPid;
    private double              _shoulderSetpoint;
    
    //Wrist Controls
    private CANSparkMax         _wristMotor;
    private DutyCycleEncoder    _wristEncoder;
    private PIDControl          _wristPID;

    //Twist Motion Controls
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
    private ResetStatus         _reset;
    private Vector              _coordinate;
    private double              _handAngle;
    private boolean             _handFlipped;

    // Simulation
    private DIOSim              _limitSwitchSim;
    private DutyCycleEncoderSim _pitchEncoderSim;
    private DutyCycleEncoderSim _wristEncoderSim;
    private DutyCycleEncoderSim _twistEncoderSim;
    

    @SuppressWarnings("resource")
    private Arm() 
    {
        CANSparkMax followerShoulderMotor;

        /* Actuators */
        _extensionMotor         = new CANSparkMax(Constants.CAN.EXTENSION_ID, MotorType.kBrushless);
        _shoulderMotor          = new CANSparkMax(Constants.CAN.SHOULDER_ID, MotorType.kBrushless);
        followerShoulderMotor   = new CANSparkMax(Constants.CAN.SHOULDER_FOLLOWER_ID, MotorType.kBrushless);
        _wristMotor             = new CANSparkMax(Constants.CAN.WRIST_ID, MotorType.kBrushless);
        _twistMotor             = new CANSparkMax(Constants.CAN.TWIST_ID, MotorType.kBrushless);
        
        /* Sensors */
        _limitSwitch            = new DigitalInput(Constants.DIO.LIMIT_SWITCH_PORT);
        _shoulderEncoder        = new DutyCycleEncoder(Constants.DIO.SHOULDER_PORT);
        _wristEncoder           = new DutyCycleEncoder(Constants.DIO.WRIST_PORT);
        _twistEncoder           = new DutyCycleEncoder(Constants.DIO.TWIST_PORT);

        /* Control variables */
        _shoulderPid            = new PIDControl();
        _extensionPid           = new PIDControl();
        _wristPID               = new PIDControl();
        _twistPID               = new PIDControl();
        _armPosition            = ArmPosition.Stow;
        _reset                  = ResetStatus.NotReset;
        _wristMinAngle          = Constants.Arm.WRIST_MAX_ANGLE;
        _wristMaxAngle          = Constants.Arm.WRIST_MIN_ANGLE;
        _twistMinRotation       = Constants.Arm.TWIST_MIN_ROTATION;
        _twistMaxRotation       = Constants.Arm.TWIST_MAX_ROTATION;

        _coordinate             = Constants.Lookups.STOW_FRONT_CONE.getCoordinate();
        _handAngle              = Constants.Lookups.STOW_FRONT_CONE.getHandAngle();
        _handFlipped            = false;
        
        /* Settings */
        _minShoulderAngle       = Constants.Arm.SHOULDER_MIN_ANGLE;
        _maxShoulderAngle       = Constants.Arm.SHOULDER_MAX_ANGLE;
        _maxArmExtension        = Constants.Arm.ARM_MAX_EXTENSION;

        _extensionMotor.restoreFactoryDefaults();
        _shoulderMotor.restoreFactoryDefaults();
        _wristMotor.restoreFactoryDefaults();
        _twistMotor.restoreFactoryDefaults();
        followerShoulderMotor.restoreFactoryDefaults();
        
        _extensionEncoder = _extensionMotor.getEncoder();
        
        _extensionMotor.setIdleMode(IdleMode.kBrake);
        _shoulderMotor.setIdleMode(IdleMode.kBrake);
        _wristMotor.setIdleMode(IdleMode.kCoast);
        _twistMotor.setIdleMode(IdleMode.kBrake);
        followerShoulderMotor.setIdleMode(IdleMode.kBrake);

        _extensionMotor.setInverted(true);
        _shoulderMotor.setInverted(true);
        _wristMotor.setInverted(true);
        _twistMotor.setInverted(true);
        followerShoulderMotor.setInverted(true);

        followerShoulderMotor.follow(_shoulderMotor, true);

        _shoulderPid.setCoefficient(Coefficient.P, 0, 0.015, 0);
        _shoulderPid.setCoefficient(Coefficient.I, 0, 0, 0);
        _shoulderPid.setCoefficient(Coefficient.D, 0, 0.001, 0);
        _shoulderPid.setInputRange(Constants.Arm.SHOULDER_MIN_ANGLE, Constants.Arm.SHOULDER_MAX_ANGLE);
        _shoulderPid.setOutputRange(-0.62, 0.62);
        _shoulderPid.setOutputRamp(0.05, 0.02);
        _shoulderPid.setSetpointDeadband(4.5);
        _shoulderPid.setFeedForward(setpoint -> -Constants.Arm.HORIZONTAL_STAYING_POWER * Math.sin(Math.toRadians(setpoint)));
        _shoulderPid.setSetpoint(0);

        _extensionPid.setCoefficient(Coefficient.P, 0, 0.4, 0);
        _extensionPid.setCoefficient(Coefficient.I, 0, 0, 0);
        _extensionPid.setCoefficient(Coefficient.D, 0, 0, 0);
        _extensionPid.setInputRange(-1, Constants.Arm.ARM_MAX_EXTENSION);
        _extensionPid.setOutputRange(-0.25, 0.25);
        _extensionPid.setSetpointDeadband(2);
        _extensionPid.setFeedForward(setpoint -> Math.cos(Math.toRadians(getShoulderAngle())) * Constants.Arm.EXTENSION_STAYING_POWER);
        _extensionPid.setSetpoint(0);
        _extensionEncoder.setPosition(Constants.Arm.ARM_MAX_EXTENSION / Constants.Arm.EXTENSION_CONVERSION_FACTOR);

        _wristPID.setCoefficient(Coefficient.P, 0, 0.025, 0);
        _wristPID.setCoefficient(Coefficient.I, 0, 0,    0);
        _wristPID.setCoefficient(Coefficient.D, 0, 0.001, 0);
        _wristPID.setInputRange(Constants.Arm.WRIST_MIN_ANGLE, Constants.Arm.WRIST_MAX_ANGLE);
        _wristPID.setOutputRange(-1, 1);
        _wristPID.setSetpointDeadband(5);
        _wristPID.setSetpoint(0);

        _twistPID.setCoefficient(Coefficient.P, 0, 0.015, 0);
        _twistPID.setCoefficient(Coefficient.I, 0, 0,     0);
        _twistPID.setCoefficient(Coefficient.D, 0, 0.0006,  0);
        _twistPID.setInputRange(Constants.Arm.TWIST_MIN_ROTATION, Constants.Arm.TWIST_MAX_ROTATION);
        _twistPID.setOutputRange(-0.25, 0.25);
        _twistPID.setSetpointDeadband(8);
        _twistPID.setSetpoint(90);

        if (RobotBase.isSimulation())
        {
            _extensionMotor.setInverted(false);
            _limitSwitchSim  = new DIOSim(_limitSwitch);
            _pitchEncoderSim = new DutyCycleEncoderSim(_shoulderEncoder);
            _wristEncoderSim = new DutyCycleEncoderSim(_wristEncoder);
            _twistEncoderSim = new DutyCycleEncoderSim(_twistEncoder);

            REVPhysicsSim.getInstance().addSparkMax(_extensionMotor, DCMotor.getNEO(1));
            REVPhysicsSim.getInstance().addSparkMax(_shoulderMotor, DCMotor.getNeo550(2));
            REVPhysicsSim.getInstance().addSparkMax(_wristMotor,  DCMotor.getNeo550(1));
            REVPhysicsSim.getInstance().addSparkMax(_twistMotor,  DCMotor.getNeo550(1));
        }

        RobotLog.getInstance().log("Created Arm Subsystem");
    }

    public boolean isLimitSwitchPressed()
    {
        return !_limitSwitch.get();
    }

    public void setExtensionEncoderPosition(double positon)
    {
        _extensionEncoder.setPosition(positon);
    }

    public double getShoulderAngle()
    {
        return (_shoulderEncoder.getAbsolutePosition() - Constants.Arm.SHOULDER_SENSOR_MIN) * Constants.Arm.SHOULDER_SLOPE + Constants.Arm.SHOULDER_SCALED_MIN;
    }

    public double getExtensionDistance()
    {
        return _extensionEncoder.getPosition() * Constants.Arm.EXTENSION_CONVERSION_FACTOR;
    }

    public double getWristAngle()
    {
        return (_wristEncoder.getAbsolutePosition() - Constants.Arm.WRIST_OFFSET) * Constants.DEGREES_PER_REVOLUTION;
    }

    public double getTwistAngle()
    {
        return MathUtil.inputModulus((_twistEncoder.getAbsolutePosition() - Constants.Arm.TWIST_OFFSET) * Constants.DEGREES_PER_REVOLUTION, -Constants.DEGREES_PER_HALF_REVOLUTION, Constants.DEGREES_PER_HALF_REVOLUTION);
    }
    
    public double getShoulderTargetAngle()
    {
        return _shoulderPid.getSetpoint();
    }

    public double getExtensionTargetDistance()
    {
        return _extensionPid.getSetpoint();
    }

    public double getWristTargetAngle()
    {
        return _wristPID.getSetpoint();
    }

    public double getTwistTargetAngle()
    {
        return _twistPID.getSetpoint();
    }
    
    public boolean shoulderAtAngle()
    {
        return _shoulderPid.atSetpoint();
    }

    public boolean extensionAtDistance()
    {
        return _extensionPid.atSetpoint();
    }

    public boolean wristAtAngle()
    {
        return _wristPID.atSetpoint();
    }

    public boolean twistAtAngle()
    {
        return _twistPID.atSetpoint();
    }

    private void updateArmSetpoints()
    {
        double sin = Math.sin(Math.toRadians(_handAngle));
        double cos = Math.cos(Math.toRadians(_handAngle));

        double x = _coordinate.getX() - Constants.Arm.HAND_LENGTH * sin;
        double y = _coordinate.getY() - Constants.Arm.HAND_LENGTH * cos - Constants.Arm.SHOULDER_HEIGHT; 

        double a = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double s = Math.toDegrees(Math.atan2(x, y));
        double w = s - _handAngle;

        _shoulderSetpoint = s;
        _extensionPid.setSetpoint(a - Constants.Arm.ARM_RETRACTED_LENGTH);
        _wristPID.setSetpoint(w);
    }

    public void setArmPosition(Vector coordinate, double handAngle, double twistAngle)
    {
        _coordinate = coordinate;
        _handAngle  = handAngle;

        setTwistAngle(twistAngle);
        updateArmSetpoints();
    }

    public Vector getCoordinate()
    {
        return _coordinate;
    }

    public double getHandAngle()
    {
        return _handAngle;
    }

    public void setTargetArmPreset(ArmPosition position)
    {
        _armPosition = position;
    }

    public ArmPosition getTargetArmPreset()
    {
        return _armPosition;
    }

    public void setTwistAngle(double position)
    {
        if (_handFlipped)
        {
            position *= -1;
        }

        _twistPID.setSetpoint(position);
    }

    public void setHandIsFlipped(boolean flipped)
    {
        if(flipped != handIsFlipped())
        {
            flipHand();
        }
    }

    public boolean handIsFlipped()
    {
        return _handFlipped;
    }

    public void flipHand()
    {
        _handFlipped = !_handFlipped;

        _twistPID.setSetpoint(-_twistPID.getSetpoint());
    }

    //Settings Functions
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
        _wristMotor.setInverted(true);

        switch (_reset)
        {
            case NotReset:
                if (isLimitSwitchPressed())
                {
                    _reset = ResetStatus.Resetting;
                    _extensionPid.setOutputRange(-1.0, 1.0);
                    _extensionEncoder.setPosition(-0.5);
                    
                }
                break;

            case Resetting:
                if (isLimitSwitchPressed())
                {
                    _extensionEncoder.setPosition(-0.5);
                }
                else
                {
                    _reset = ResetStatus.Reset;
                }
                break;

            case Reset:
            default:
                break;
        }
        
        _shoulderPid.setSetpoint(_shoulderSetpoint - Math.min(Math.max(Drive.getInstance().getChassisPitch(), -20.0), 20.0), false); // preserve input range
        
        _shoulderMotor.setVoltage(_shoulderPid.calculate(getShoulderAngle()) * Constants.MOTOR_VOLTAGE);
        _extensionMotor.setVoltage(_extensionPid.calculate(getExtensionDistance()) * Constants.MOTOR_VOLTAGE);
        _wristMotor.setVoltage(_wristPID.calculate(getWristAngle()) * Constants.MOTOR_VOLTAGE);
        _twistMotor.setVoltage(_twistPID.calculate(getTwistAngle()) * Constants.MOTOR_VOLTAGE);

        //System.out.println(String.format("Shoulder Setpoint: %6.2f, Shoulder Current: %6.2f, Shoulder Out: %6.2f", _shoulderPid.getSetpoint(), getShoulderAngle(), shoulderOutput));
        //System.out.println(String.format("Shoulder: %b, Extension: %b, Wrist: %b, Twist: %b, %s", shoulderAtAngle(), extensionAtDistance(), wristAtAngle(), twistAtAngle(), _reset));
    }

    @Override
    public void simulationPeriodic()
    {
        _limitSwitchSim.setValue(_extensionEncoder.getPosition() <= 0);
        _pitchEncoderSim.set(_shoulderMotor.getEncoder().getPosition());
        _wristEncoderSim.set(_wristMotor.getEncoder().getPosition());
        _twistEncoderSim.set(_twistMotor.getEncoder().getPosition());
    }
}
