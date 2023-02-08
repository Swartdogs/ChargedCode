package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
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
    
    //Wrist Controls
    private CANSparkMax         _wristMotor;
    private DutyCycleEncoder    _wristEncoder;
    private PIDControl          _wristPID;

    //Twist Motion Controls
    private CANSparkMax         _twistMotor;
    private DutyCycleEncoder    _twistEncoder;
    private PIDControl          _twistPID;
    
    //Intake Controls
    private CANSparkMax         _intakeMotor;
    private DigitalInput        _intakeSensor;

    //Settings
    private double              _wristMinAngle;
    private double              _wristMaxAngle;
    private double              _twistMinRotation;
    private double              _twistMaxRotation;
    private double              _ejectTime;
    private double              _intakeSpeed;

    //Simulation Variables
    private DutyCycleEncoderSim _wristEncoderSim;
    private DutyCycleEncoderSim _twistEncoderSim;
    private DIOSim              _intakeSensorSim;

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
        _intakeSpeed        = Constants.Manipulator.INTAKE_SPEED;

        _wristPID.setCoefficient(Coefficient.P, 0, 0.001, 0);
        _wristPID.setCoefficient(Coefficient.I, 0, 0, 0);
        _wristPID.setCoefficient(Coefficient.D, 0, 0, 0);
        _wristPID.setInputRange(Constants.Manipulator.WRIST_MIN_ANGLE, Constants.Manipulator.WRIST_MAX_ANGLE);
        _wristPID.setOutputRange(-1, 1);
        _wristPID.setSetpointDeadband(1);

        _twistPID.setCoefficient(Coefficient.P, 0, 0.001, 0);
        _twistPID.setCoefficient(Coefficient.I, 0, 0, 0);
        _twistPID.setCoefficient(Coefficient.D, 0, 0, 0);
        _twistPID.setInputRange(Constants.Manipulator.TWIST_MIN_ROTATION, Constants.Manipulator.TWIST_MAX_ROTATION);
        _twistPID.setOutputRange(-1, 1);
        _twistPID.setSetpointDeadband(1);

        if (RobotBase.isSimulation())
        {
            _wristEncoderSim = new DutyCycleEncoderSim(_wristEncoder);
            _twistEncoderSim = new DutyCycleEncoderSim(_twistEncoder);
            _intakeSensorSim = new DIOSim(_intakeSensor);

            REVPhysicsSim.getInstance().addSparkMax(_wristMotor,  DCMotor.getNeo550(1));
            REVPhysicsSim.getInstance().addSparkMax(_twistMotor,  DCMotor.getNeo550(1));
            REVPhysicsSim.getInstance().addSparkMax(_intakeMotor, DCMotor.getNeo550(1));
        }

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
        _intakeMotor.setVoltage(_intakeSpeed * Constants.MOTOR_VOLTAGE);
    }

    public void disableIntake()
    {
        _intakeMotor.setVoltage(0);
    }

    public void reverseIntake()
    {
        _intakeMotor.setVoltage(-_intakeSpeed * Constants.MOTOR_VOLTAGE);
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

    public boolean wristAtAngle()
    {
        return _wristPID.atSetpoint();
    }

    public boolean twistAtAngle()
    {
        return _twistPID.atSetpoint();
    }

    @Override
    public void periodic()
    {
        _twistMotor.setVoltage(_twistPID.calculate(getTwistAngle()) * Constants.MOTOR_VOLTAGE);
        _wristMotor.setVoltage(_wristPID.calculate(getWristAngle()) * Constants.MOTOR_VOLTAGE);
    }

    @Override
    public void simulationPeriodic()
    {
        _wristEncoderSim.set(_wristMotor.getEncoder().getPosition());
        _twistEncoderSim.set(_twistMotor.getEncoder().getPosition());
    }
}
