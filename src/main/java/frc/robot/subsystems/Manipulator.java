package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DutyCycleEncoderSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Manipulator extends SubsystemBase 
{
    private static Manipulator _instance;

    public static Manipulator getInstance()
    {
        if(_instance == null)
        {
            _instance = new Manipulator();
        }

        return _instance;
    }
   
    public enum HandMode
    {
        Cube,
        Cone
    }

    private enum IntakeState
    {
        On,
        Off,
        Reverse,
        ConeEject
    }
    
    //Wrist Controls
    private CANSparkMax         _wristMotor;
    private DutyCycleEncoder    _wristEncoder;
    private PIDControl          _wristPID;
    private double              _wristSpeed;
    private boolean             _wristOverride;

    //Twist Motion Controls
    private CANSparkMax         _twistMotor;
    private DutyCycleEncoder    _twistEncoder;
    private PIDControl          _twistPID;
    
    //Intake Controls
    private CANSparkMax         _intakeMotor;
    private DigitalInput        _intakeSensor;
    private IntakeState         _intakeState;
    private boolean             _isFlipped;

    //Settings
    private double              _wristMinAngle;
    private double              _wristMaxAngle;
    private double              _twistMinRotation;
    private double              _twistMaxRotation;
    private double              _ejectTime;
    private double              _intakeSpeed;
    private double              _placeSpeed;
    private double              _intakeStopDelay;
    private double              _intakeStowDelay;

    //Simulation Variables
    private DutyCycleEncoderSim _wristEncoderSim;
    private DutyCycleEncoderSim _twistEncoderSim;

    private Manipulator()
    {
        _wristMotor         = new CANSparkMax(Constants.CAN.WRIST_ID, MotorType.kBrushless);
        _wristEncoder       = new DutyCycleEncoder(Constants.DIO.WRIST_PORT);
        _wristPID           = new PIDControl();
        _wristSpeed         = 0;
        _wristOverride      = false;

        _twistMotor         = new CANSparkMax(Constants.CAN.TWIST_ID, MotorType.kBrushless);
        _twistEncoder       = new DutyCycleEncoder(Constants.DIO.TWIST_PORT);
        _twistPID           = new PIDControl();

        _intakeMotor        = new CANSparkMax(Constants.CAN.INTAKE_ID, MotorType.kBrushless);
        _intakeSensor       = new DigitalInput(Constants.DIO.LIGHT_SENSOR_PORT);
        _intakeState        = IntakeState.Off;
        _isFlipped          = false;

        _wristMinAngle      = Constants.Manipulator.WRIST_MAX_ANGLE;
        _wristMaxAngle      = Constants.Manipulator.WRIST_MIN_ANGLE;
        _twistMinRotation   = Constants.Manipulator.TWIST_MIN_ROTATION;
        _twistMaxRotation   = Constants.Manipulator.TWIST_MAX_ROTATION;
        _ejectTime          = Constants.Manipulator.EJECT_TIME;
        _intakeSpeed        = Constants.Manipulator.INTAKE_SPEED;
        _placeSpeed         = Constants.Manipulator.PLACE_SPEED;
        _intakeStopDelay    = Constants.Manipulator.INTAKE_STOP_DELAY;
        _intakeStowDelay    = Constants.Manipulator.INTAKE_STOW_DELAY;

        _wristMotor.restoreFactoryDefaults();
        _twistMotor.restoreFactoryDefaults();
        _intakeMotor.restoreFactoryDefaults();

        _wristMotor.setInverted(true);
        _twistMotor.setInverted(true);
        _intakeMotor.setInverted(true);

        _wristMotor.setIdleMode(IdleMode.kCoast);
        _twistMotor.setIdleMode(IdleMode.kBrake);
        _intakeMotor.setIdleMode(IdleMode.kBrake);

        _wristPID.setCoefficient(Coefficient.P, 0, 0.025, 0);
        _wristPID.setCoefficient(Coefficient.I, 0, 0,    0);
        _wristPID.setCoefficient(Coefficient.D, 0, 0.001, 0);
        _wristPID.setInputRange(Constants.Manipulator.WRIST_MIN_ANGLE, Constants.Manipulator.WRIST_MAX_ANGLE);
        _wristPID.setOutputRange(-1, 1);
        _wristPID.setSetpointDeadband(5);
        _wristPID.setSetpoint(0);

        _twistPID.setCoefficient(Coefficient.P, 0, 0.015, 0);
        _twistPID.setCoefficient(Coefficient.I, 0, 0,     0);
        _twistPID.setCoefficient(Coefficient.D, 0, 0.0006,  0);
        _twistPID.setInputRange(Constants.Manipulator.TWIST_MIN_ROTATION, Constants.Manipulator.TWIST_MAX_ROTATION);
        _twistPID.setOutputRange(-0.25, 0.25);
        _twistPID.setSetpointDeadband(8);
        _twistPID.setSetpoint(90);

        if (RobotBase.isSimulation())
        {
            _wristEncoderSim = new DutyCycleEncoderSim(_wristEncoder);
            _twistEncoderSim = new DutyCycleEncoderSim(_twistEncoder);

            REVPhysicsSim.getInstance().addSparkMax(_wristMotor,  DCMotor.getNeo550(1));
            REVPhysicsSim.getInstance().addSparkMax(_twistMotor,  DCMotor.getNeo550(1));
            REVPhysicsSim.getInstance().addSparkMax(_intakeMotor, DCMotor.getNeo550(1));
        }

        RobotLog.getInstance().log("Created Manipulator Subsystem");
    }

    public void overrideWrist()
    {
        _wristOverride = true;
        _wristMotor.setIdleMode(IdleMode.kBrake);
    }

    public boolean isWristOverridden()
    {
        return _wristOverride;
    }

    public void setWristAngle(double position)
    {
        _wristPID.setSetpoint(position);
    }

    public void setWristSpeed(double speed)
    {
        if (_wristOverride)
        {
            _wristMotor.setVoltage(speed * Constants.MOTOR_VOLTAGE);
        }
    }

    public double getWristAngle()
    {
        return (_wristEncoder.getAbsolutePosition() - Constants.Manipulator.WRIST_OFFSET) * Constants.DEGREES_PER_REVOLUTION;
    }

    public void modifyWristAngle(double modification)
    {
        _wristPID.setSetpoint(_wristPID.getSetpoint() + modification);
    }

    public void enableIntake()
    {
        _intakeState = IntakeState.On;
    }

    public void disableIntake()
    {
        _intakeState = IntakeState.Off;
    }

    public void reverseIntake()
    {
        _intakeState = IntakeState.Reverse;
    }

    public void setIntakeToConeEjectSpeed()
    {
        _intakeState = IntakeState.ConeEject;
    }
    
    public double getIntakeSpeed()
    {
        double speed = 0;
        
        switch(_intakeState)
        {
            case On: 
                speed = _intakeSpeed;
                break;
            
            case Off:    
                speed = 0;
                break;
            
            case Reverse:    
                speed = -_placeSpeed;
                break;

            case ConeEject:
                speed = -Constants.Manipulator.CONE_EJECT_SPEED;
                break;
        }        
        
        return speed;
    }    
        
    public void setTwistAngle(double position)
    {
        _twistPID.setSetpoint(position);
    }    
    
    public double getTwistAngle()
    {
        return MathUtil.inputModulus((_twistEncoder.getAbsolutePosition() - Constants.Manipulator.TWIST_OFFSET) * Constants.DEGREES_PER_REVOLUTION, -Constants.DEGREES_PER_HALF_REVOLUTION, Constants.DEGREES_PER_HALF_REVOLUTION);
    }    
    
    public double getTwistTargetAngle()
    {
        return _twistPID.getSetpoint();
    }    
    
    public void setIsFlipped(boolean isFlipped)
    {
        _isFlipped = isFlipped;
    }    
    
    public boolean isFlipped()
    {
        return _isFlipped;
    }    
    
    public boolean hasGamePiece()
    {
        return !_intakeSensor.get();    
    }
    
    public boolean wristAtAngle()
    {
        return _wristPID.atSetpoint();
    }

    public boolean twistAtAngle()
    {
        return _twistPID.atSetpoint();
    }
    
    //Settings Functions
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
    
    public void setEjectTime(double time)
    {
        _ejectTime = time;
    }    
    
    public double getEjectTime()
    {
        return _ejectTime;
    }    
    
    public void setIntakeSpeed(double speed)
    {
        _intakeSpeed = speed;
    }    

    public void setPlaceSpeed(double speed)
    {
        _placeSpeed = speed;
    }

    public void setIntakeStopDelay(double delay)
    {
        _intakeStopDelay = delay;
    }

    public double getIntakeStopDelay()
    {
        return _intakeStopDelay;
    }

    public void setIntakeStowDelay(double delay)
    {
        _intakeStowDelay = delay;
    }

    public double getIntakeStowDelay()
    {
        return _intakeStowDelay;
    }

    @Override
    public void periodic()
    {
        _wristMotor.setInverted(true);
        _intakeMotor.setInverted(true);

        switch (_intakeState)
        {
            case On:
                _intakeMotor.setVoltage(_intakeSpeed * Constants.MOTOR_VOLTAGE);
                break;

            case Reverse:
                _intakeMotor.setVoltage(-_placeSpeed * Constants.MOTOR_VOLTAGE);
                break;

            case Off:
                if (hasGamePiece())
                {
                    _intakeMotor.setVoltage(Constants.Manipulator.INTAKE_HOLD_SPEED * Constants.MOTOR_VOLTAGE);
                }
                else
                {
                    _intakeMotor.setVoltage(0);
                }
                break;

            case ConeEject:
                _intakeMotor.setVoltage(-Constants.Manipulator.CONE_EJECT_SPEED * Constants.MOTOR_VOLTAGE);
                break;

            default:
                _intakeMotor.setVoltage(0);
                break;
        }

        _twistMotor.setVoltage(_twistPID.calculate(getTwistAngle()) * Constants.MOTOR_VOLTAGE);
        
        if (!_wristOverride)
        {
            _wristSpeed = _wristPID.calculate(getWristAngle());
            _wristMotor.setVoltage(_wristSpeed * Constants.MOTOR_VOLTAGE);
        }
    }

    @Override
    public void simulationPeriodic()
    {
        _wristEncoderSim.set(_wristMotor.getEncoder().getPosition());
        _twistEncoderSim.set(_twistMotor.getEncoder().getPosition());
    }

    public Command printWristHealthCommand()
    {
        return Commands.either
        (
            Commands.print(String.format("Wrist Angle: %6.2f, Wrist Command: %5.2f, Wrist Setpoint: %6.2f", getWristAngle(), _wristSpeed, _wristPID.getSetpoint())),
            Commands.print(String.format("Wrist overridden!")),
            () -> !_wristOverride
        ).repeatedly();
    }
}
