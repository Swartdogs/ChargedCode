package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.DutyCycleEncoderSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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
    
    // Extension Motor
    private DigitalInput        _limitSwitch;
    private CANSparkMax         _extensionMotor;
    private RelativeEncoder     _extensionEncoder;
    private PIDControl          _extensionPid;

    // Shoulder Motor
    private DutyCycleEncoder    _shoulderEncoder;
    private CANSparkMax         _shoulderMotor;
    private PIDControl          _shoulderPid;
    
    // Settings
    private double              _minShoulderAngle;
    private double              _maxShoulderAngle;
    private double              _maxArmExtension;

    // Controls
    private ArmPosition         _armPosition;
    private boolean             _reset;

    // Simulation
    private DIOSim              _limitSwitchSim;
    private DutyCycleEncoderSim _pitchEncoderSim;
    
    @SuppressWarnings("resource")
    private Arm() 
    {
        CANSparkMax followerShoulderMotor;

        /* Actuators */
        _extensionMotor         = new CANSparkMax(Constants.CAN.EXTENSION_ID, MotorType.kBrushless);
        _shoulderMotor          = new CANSparkMax(Constants.CAN.SHOULDER_ID, MotorType.kBrushless);
        followerShoulderMotor   = new CANSparkMax(Constants.CAN.SHOULDER_FOLLOWER_ID, MotorType.kBrushless);
        
        /* Sensors */
        _limitSwitch            = new DigitalInput(Constants.DIO.LIMIT_SWITCH_PORT);
        _shoulderEncoder        = new DutyCycleEncoder(Constants.DIO.SHOULDER_PORT);

        /* Control variables */
        _shoulderPid            = new PIDControl();
        _extensionPid           = new PIDControl();
        _armPosition            = ArmPosition.Stow;
        _reset                  = false;
        
        /* Settings */
        _minShoulderAngle       = Constants.Arm.SHOULDER_MIN_ANGLE;
        _maxShoulderAngle       = Constants.Arm.SHOULDER_MAX_ANGLE;
        _maxArmExtension        = Constants.Arm.ARM_MAX_EXTENSION;
        
        _extensionMotor.restoreFactoryDefaults();
        _shoulderMotor.restoreFactoryDefaults();
        followerShoulderMotor.restoreFactoryDefaults();
        
        _extensionEncoder = _extensionMotor.getEncoder();
        
        _shoulderMotor.setIdleMode(IdleMode.kBrake);
        followerShoulderMotor.setIdleMode(IdleMode.kBrake);

        _extensionMotor.setInverted(true);
        _shoulderMotor.setInverted(true);
        followerShoulderMotor.setInverted(true);

        followerShoulderMotor.follow(_shoulderMotor, true);

        _shoulderPid.setCoefficient(Coefficient.P, 0, 0.0225, 0);
        _shoulderPid.setCoefficient(Coefficient.I, 0, 0, 0);
        _shoulderPid.setCoefficient(Coefficient.D, 0, 0.05, 0);
        _shoulderPid.setInputRange(Constants.Arm.SHOULDER_MIN_ANGLE, Constants.Arm.SHOULDER_MAX_ANGLE);
        _shoulderPid.setOutputRange(-0.7, 0.7);
        _shoulderPid.setOutputRamp(0.05, 0.02);
        _shoulderPid.setSetpointDeadband(2);
        _shoulderPid.setFeedForward(setpoint -> -Constants.Arm.HORIZONTAL_STAYING_POWER * Math.sin(Math.toRadians(setpoint)));
        _shoulderPid.setSetpoint(0, getShoulderAngle());

        _extensionPid.setCoefficient(Coefficient.P, 0, 0.5, 0);
        _extensionPid.setCoefficient(Coefficient.I, 0, 0, 0);
        _extensionPid.setCoefficient(Coefficient.D, 0, 0, 0);
        _extensionPid.setInputRange(0, Constants.Arm.ARM_MAX_EXTENSION);
        _extensionPid.setOutputRange(-0.25, 0.25);
        _extensionPid.setSetpointDeadband(2);
        _extensionPid.setSetpoint(0, getExtensionPosition());
        _extensionEncoder.setPosition(Constants.Arm.ARM_MAX_EXTENSION / Constants.Arm.EXTENSION_CONVERSION_FACTOR);

        if (RobotBase.isSimulation())
        {
            _extensionMotor.setInverted(false);
            _limitSwitchSim = new DIOSim(_limitSwitch);
            _pitchEncoderSim = new DutyCycleEncoderSim(_shoulderEncoder);

            REVPhysicsSim.getInstance().addSparkMax(_extensionMotor, DCMotor.getNEO(1));
            REVPhysicsSim.getInstance().addSparkMax(_shoulderMotor, DCMotor.getNeo550(2));
            
        }

        RobotLog.getInstance().log("Created Arm Subsystem");
    }

    public boolean isLimitSwitchPressed()
    {
        return !_limitSwitch.get();
    }

    public double getExtensionPosition()
    {
        return _extensionEncoder.getPosition() * Constants.Arm.EXTENSION_CONVERSION_FACTOR;
    }

    public double getShoulderAngle()
    {
        return (_shoulderEncoder.getAbsolutePosition() - Constants.Arm.SHOULDER_SENSOR_MIN) * Constants.Arm.SHOULDER_SLOPE + Constants.Arm.SHOULDER_SCALED_MIN;
    }

    public void setExtensionMotorPosition(double position)
    {
       _extensionPid.setSetpoint(position, getExtensionPosition());
    }
  
    public void modifyExtensionMotorPosition(double modification)
    {
        _extensionPid.setSetpoint(_extensionPid.getSetpoint()+modification, getExtensionPosition());
    }

    public void setShoulderAngle(double angle)
    {
        _shoulderPid.setSetpoint(angle, getShoulderAngle(), true);
    }

    public void modifyShoulderAngle(double modification)
    {
        _shoulderPid.setSetpoint(_shoulderPid.getSetpoint()+modification, getShoulderAngle());
    }

    public boolean shoulderAtAngle()
    {
        return _shoulderPid.atSetpoint();
    }

    public boolean extensionAtDistance()
    {
        return _extensionPid.atSetpoint();
    }

    public void setArmPosition(ArmPosition position)
    {
        _armPosition = position;
    }

    public ArmPosition getArmPosition()
    {
        return _armPosition;
    }

    public void setExtensionEncoderPosition(double positon)
    {
        _extensionEncoder.setPosition(positon);
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

    private void resetExtension()
    {
        _extensionEncoder.setPosition(0);
        _extensionPid.setOutputRange(-1.0, 1.0);
    }

    @Override 
    public void periodic()
    {
        if (!_reset && isLimitSwitchPressed())
        {
            _reset = true;
            resetExtension();
        }

        _shoulderMotor.setVoltage(_shoulderPid.calculate(getShoulderAngle()) * Constants.MOTOR_VOLTAGE);
        _extensionMotor.setVoltage(_extensionPid.calculate(getExtensionPosition()) * Constants.MOTOR_VOLTAGE);
    }

    @Override
    public void simulationPeriodic()
    {
        _limitSwitchSim.setValue(_extensionEncoder.getPosition() <= 0);
        _pitchEncoderSim.set(_shoulderMotor.getEncoder().getPosition());
    }
}
