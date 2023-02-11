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
    private CANSparkMax         _linearMotor;
    private RelativeEncoder     _extensionEncoder;
    private PIDControl          _extensionPid;
    private boolean             _extensionPidActive;

    // Shoulder Motor
    private DutyCycleEncoder    _pitchEncoder;
    private CANSparkMax         _pitchMotor;
    private PIDControl          _shoulderPid;
    
    // Settings
    private double              _minShoulderAngle;
    private double              _maxShoulderAngle;
    private double              _maxArmExtension;

    // Controls
    private ArmPosition         _armPosition;

    // Simulation
    private DIOSim              _limitSwitchSim;
    private DutyCycleEncoderSim _pitchEncoderSim;

    
    @SuppressWarnings("resource")
    private Arm() 
    {
        CANSparkMax followerPitchMotor  = new CANSparkMax(Constants.Arm.FOLLOWER_PITCH_MOTOR_CAN_ID, MotorType.kBrushless);
        
        _limitSwitch        = new DigitalInput(Constants.Arm.LIMIT_SWITCH_CHANNEL);
        _pitchEncoder       = new DutyCycleEncoder(Constants.Arm.PITCH_ENCODER_CHANNEL);
        _linearMotor        = new CANSparkMax(Constants.Arm.LINEAR_MOTOR_CAN_ID, MotorType.kBrushless);
        _pitchMotor         = new CANSparkMax(Constants.Arm.PITCH_MOTOR_CAN_ID, MotorType.kBrushless);
        _extensionPidActive = false; 
        _extensionPid       = new PIDControl();
        _shoulderPid        = new PIDControl();
        _extensionEncoder   = _linearMotor.getEncoder();
        _armPosition        = ArmPosition.Stow;

        _minShoulderAngle   = Constants.Arm.SHOULDER_MIN_ANGLE;
        _maxShoulderAngle   = Constants.Arm.SHOULDER_MAX_ANGLE;
        _maxArmExtension    = Constants.Arm.ARM_MAX_EXTENSION;

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

        _linearMotor.setInverted(true);

        _extensionEncoder.setPositionConversionFactor(1);

        followerPitchMotor.follow(_pitchMotor, true);

        if (RobotBase.isSimulation())
        {
            _linearMotor.setInverted(false);
            _limitSwitchSim = new DIOSim(_limitSwitch);
            _pitchEncoderSim = new DutyCycleEncoderSim(_pitchEncoder);

            REVPhysicsSim.getInstance().addSparkMax(_linearMotor, DCMotor.getNEO(1));
            REVPhysicsSim.getInstance().addSparkMax(_pitchMotor, DCMotor.getNeo550(2));
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

    public void setExtensionMotorSpeed(double speed)
    {
        _linearMotor.setVoltage(speed * Constants.MOTOR_VOLTAGE);
        _extensionPidActive = false;
    }

    public void setExtensionMotorPosition(double position)
    {
       _extensionPidActive = true;
       _extensionPid.setSetpoint(position, getExtensionPosition());
    }
  
    public void modifyExtensionMotorPosition(double modification)
    {
        _extensionPid.setSetpoint(_extensionPid.getSetpoint()+modification, getExtensionPosition());
    }

    public void setShoulderAngle(double angle)
    {
        _shoulderPid.setSetpoint(angle, getShoulderAngle());
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

    @Override 
    public void periodic()
    {
        _pitchMotor.setVoltage(_shoulderPid.calculate(getShoulderAngle()) * Constants.MOTOR_VOLTAGE);

        if (_extensionPidActive) 
        {
            _linearMotor.setVoltage(_extensionPid.calculate(getExtensionPosition()) * Constants.MOTOR_VOLTAGE);
        }
    }

    @Override
    public void simulationPeriodic()
    {
        _limitSwitchSim.setValue(_extensionEncoder.getPosition() <= 0);
        _pitchEncoderSim.set(_pitchMotor.getEncoder().getPosition());
    }
}
