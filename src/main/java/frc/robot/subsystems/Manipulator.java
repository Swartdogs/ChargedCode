package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import PIDControl.PIDControl;
import PIDControl.PIDControl.Coefficient;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
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

    //Wrist Controls
    private CANSparkMax      _wristMotor;
    private DutyCycleEncoder _wristEncoder;
    private PIDControl       _wristPID;

    //Twist Motion Controls
    private CANSparkMax      _twistMotor;
    private DutyCycleEncoder _twistEncoder;
    private PIDControl       _twistPID;
    
    //Intake Controls
    private CANSparkMax      _intakeMotor;
    private DigitalInput     _intakeSensor;

    //Settings
    private double           _wristMinAngle;
    private double           _wristMaxAngle;
    private double           _twistMinRotation;
    private double           _twistMaxRotation;
    private double           _ejectTime;
    private double           _ejectSpeed;
    private double           _intakeSpeed;

    private Manipulator()
    {
        _wristMotor         = new CANSparkMax(Constants.Manipulator.WRIST_MOTOR_CAN_ID, MotorType.kBrushless);
        _wristEncoder       = new DutyCycleEncoder(Constants.Manipulator.WRIST_ENCODER_PORT);
        _wristPID           = new PIDControl();

        _twistMotor         = new CANSparkMax(Constants.Manipulator.TWIST_MOTOR_CAN_ID, MotorType.kBrushless);
        _twistEncoder       = new DutyCycleEncoder(Constants.Manipulator.TWIST_ENCODER_PORT);
        _twistPID           = new PIDControl();

        _intakeMotor        = new CANSparkMax(Constants.Manipulator.INTAKE_MOTOR_CAN_ID, MotorType.kBrushless);
        _intakeSensor       = new DigitalInput(Constants.Manipulator.INTAKE_SENSOR_PORT);

        _wristMinAngle      = Constants.Manipulator.WRIST_MAX_ANGLE;
        _wristMaxAngle      = Constants.Manipulator.WRIST_MIN_ANGLE;
        _twistMinRotation   = Constants.Manipulator.TWIST_MIN_ROTATION;
        _twistMaxRotation   = Constants.Manipulator.TWIST_MAX_ROTATION;
        _ejectTime          = Constants.Manipulator.EJECT_TIME;
        _ejectSpeed         = Constants.Manipulator.EJECT_SPEED;
        _intakeSpeed        = Constants.Manipulator.INTAKE_SPEED;

        _wristPID.setCoefficient(Coefficient.P, 0, 0, 0);
        _wristPID.setCoefficient(Coefficient.I, 0, 0, 0);
        _wristPID.setCoefficient(Coefficient.D, 0, 0, 0);
        _wristPID.setInputRange(Constants.Manipulator.WRIST_MIN_ANGLE, Constants.Manipulator.WRIST_MAX_ANGLE);
        _wristPID.setOutputRange(-1, 1);
        _wristPID.setSetpointDeadband(1);

        _twistPID.setCoefficient(Coefficient.P, 0, 0, 0);
        _twistPID.setCoefficient(Coefficient.I, 0, 0, 0);
        _twistPID.setCoefficient(Coefficient.D, 0, 0, 0);
        _twistPID.setInputRange(Constants.Manipulator.TWIST_MIN_ROTATION, Constants.Manipulator.TWIST_MAX_ROTATION);
        _twistPID.setOutputRange(-1, 1);
        _twistPID.setSetpointDeadband(1);

        RobotLog.getInstance().log("Created Manipulator Subsystem");
    }

    public void setWristAngle(double position)
    {
        _wristPID.setSetpoint(position, getWristAngle());
    }

    public double getWristAngle()
    {
        return _wristEncoder.get();
    }

    public void enableIntake()
    {
        _intakeMotor.set(-_intakeSpeed);
    }

    public void disableIntake()
    {
        _intakeMotor.set(0);
    }

    public void reverseIntake()
    {
        _intakeMotor.set(_ejectSpeed);
    }

    public void setTwistAngle(double position)
    {
        _twistPID.setSetpoint(position, getTwistAngle());
    }

    public double getTwistAngle()
    {
        return _twistEncoder.get();
    }

    public boolean isIntakeSensorActive()
    {
        return !_intakeSensor.get();    
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

    @Override
    public void periodic()
    {
        _twistMotor.set(_twistPID.calculate(getTwistAngle()));
        _wristMotor.set(_wristPID.calculate(getWristAngle()));
    }
}
