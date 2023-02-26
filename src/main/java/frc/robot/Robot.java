package frc.robot;

// import java.util.jar.JarOutputStream;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVPhysicsSim;
// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.CANSparkMax.IdleMode;

// import PIDControl.PIDControl;
// import PIDControl.PIDControl.Coefficient;
// import edu.wpi.first.math.MathUtil;
// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.DutyCycleEncoder;
// import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import frc.robot.subsystems.Manipulator;
// import frc.robot.subsystems.Arm;

public class Robot extends TimedRobot 
{
    private Command _autonomousCommand;

    private RobotContainer _robotContainer;

    // private DutyCycleEncoder _shoulder;
    // private DutyCycleEncoder _wrist;
    // private DutyCycleEncoder _twist;
    // private DigitalInput     _limitSwitch;
    // private DigitalInput     _lightSensor;

    // private CANSparkMax      _exMotor;
    // private CANSparkMax      _wristMotor;
    // private CANSparkMax      _twistMotor;
    // private CANSparkMax      _pitchMotor;
    // private CANSparkMax      _inMotor;

    // private RelativeEncoder  _encoder;

    // private PIDControl       _wristPid;
    // private PIDControl       _twistPid;
    // private PIDControl       _exPid;
    // private PIDControl       _shoulderPid;

    // private boolean          _reset;

    // private static final double EX_CONVERSION_FACTOR = (25+5/8)/125.0947;

    @Override
    public void robotInit() 
    {
        _robotContainer = RobotContainer.getInstance();

        // _shoulder = new DutyCycleEncoder(0);
        // _twist = new DutyCycleEncoder(1);
        // _wrist = new DutyCycleEncoder(2);
        // _limitSwitch = new DigitalInput(3);
        // _lightSensor = new DigitalInput(4);

        // _exMotor = new CANSparkMax(15, MotorType.kBrushless);
        // _wristMotor = new CANSparkMax(13, MotorType.kBrushless);
        // _twistMotor = new CANSparkMax(14, MotorType.kBrushless);
        // _inMotor = new CANSparkMax(Constants.CAN.INTAKE_ID, MotorType.kBrushless);

        // CANSparkMax followerPitchMotor  = new CANSparkMax(Constants.CAN.SHOULDER_FOLLOWER_ID, MotorType.kBrushless);
        // _pitchMotor         = new CANSparkMax(Constants.CAN.SHOULDER_ID, MotorType.kBrushless);

        // _pitchMotor.restoreFactoryDefaults();
        // followerPitchMotor.restoreFactoryDefaults();

        // _pitchMotor.setIdleMode(IdleMode.kBrake);
        // followerPitchMotor.setIdleMode(IdleMode.kBrake);

        // _exMotor.restoreFactoryDefaults();
        // _encoder = _exMotor.getEncoder();  
        
        // _inMotor.restoreFactoryDefaults();

        // _exMotor.setInverted(true);
        // _wristMotor.setInverted(true);
        // _twistMotor.setInverted(true);
        // _pitchMotor.setInverted(true);
        // _inMotor.setInverted(true);
        // followerPitchMotor.setInverted(true);

        // followerPitchMotor.follow(_pitchMotor, true);

        // Joystick driveJoy = new Joystick(0);
        // JoystickButton button1 = new JoystickButton(driveJoy, 1);
        // JoystickButton button2 = new JoystickButton(driveJoy, 2);    
        // JoystickButton button3 = new JoystickButton(driveJoy, 3);
        // JoystickButton button4 = new JoystickButton(driveJoy, 4);
        // JoystickButton button5 = new JoystickButton(driveJoy, 5);
        // JoystickButton button6 = new JoystickButton(driveJoy, 6);
        // JoystickButton button7 = new JoystickButton(driveJoy, 7);
        // JoystickButton button8 = new JoystickButton(driveJoy, 8);
        // JoystickButton button9 = new JoystickButton(driveJoy, 9);
        // JoystickButton button10 = new JoystickButton(driveJoy, 10);
        // JoystickButton button11 = new JoystickButton(driveJoy, 11);

        // button1.onTrue(Commands.runOnce(() -> _encoder.setPosition(0)).ignoringDisable(true));
        // button2.onTrue(Commands.runOnce(() -> _wristPid.setSetpoint(-20, getWristPosition())));
        // button3.onTrue(Commands.runOnce(() -> _wristPid.setSetpoint(20, getWristPosition())));
        // button4.onTrue(Commands.runOnce(() -> _twistPid.setSetpoint(-60, getTwistPosition())));
        // button5.onTrue(Commands.runOnce(() -> _twistPid.setSetpoint(60, getTwistPosition())));
        // button6.onTrue(Commands.runOnce(()  -> _exPid.setSetpoint(5, getExtensionPosition())));
        // button7.onTrue(Commands.runOnce(()  -> _exPid.setSetpoint(15, getExtensionPosition())));
        // button8.onTrue(Commands.run(() -> _inMotor.setVoltage(Constants.MOTOR_VOLTAGE * 0.5)).until(() -> !_lightSensor.get()).andThen(Commands.waitSeconds(0.3)).finallyDo(end -> _inMotor.setVoltage(0)));
        // button9.onTrue(Commands.run(() -> _inMotor.setVoltage(-Constants.MOTOR_VOLTAGE)).withTimeout(Constants.Manipulator.EJECT_TIME).finallyDo(end -> _inMotor.setVoltage(0)));
        // button10.onTrue(Commands.runOnce(() -> _shoulderPid.setSetpoint(15, getShoulderPosition())));
        // button11.onTrue(Commands.runOnce(() -> _shoulderPid.setSetpoint(90, getShoulderPosition())));

        // _wristPid = new PIDControl();

        // _wristPid.setCoefficient(Coefficient.P, 0, 0.25, 0);
        // _wristPid.setCoefficient(Coefficient.I, 0, 0, 0);
        // _wristPid.setCoefficient(Coefficient.D, 0, 0.15, 0);
        // _wristPid.setInputRange(-40, 40);
        // _wristPid.setOutputRange(-1, 1);
        // _wristPid.setSetpointDeadband(2);
        // _wristPid.setSetpoint(0, getWristPosition());

        // _twistPid = new PIDControl();

        // _twistPid.setCoefficient(Coefficient.P, 0, 0.015, 0);
        // _twistPid.setCoefficient(Coefficient.I, 0, 0, 0);
        // _twistPid.setCoefficient(Coefficient.D, 0, 0.03, 0); 
        // _twistPid.setInputRange(-160, 160);
        // _twistPid.setOutputRange(-0.1, 0.1);
        // _twistPid.setSetpointDeadband(2);
        // _twistPid.setSetpoint(-90, getTwistPosition());

        // _exPid = new PIDControl();

        // _exPid.setCoefficient(Coefficient.P, 0, 0.5, 0);
        // _exPid.setCoefficient(Coefficient.I, 0, 0, 0);
        // _exPid.setCoefficient(Coefficient.D, 0, 0, 0); 
        // _exPid.setInputRange(0, 24);
        // _exPid.setOutputRange(-0.25, 0.25);
        // _exPid.setSetpointDeadband(2);
        // _exPid.setSetpoint(0, getExtensionPosition());
        // _encoder.setPosition(24 / EX_CONVERSION_FACTOR);

        // _reset = false;

        // _shoulderPid = new PIDControl();

        // _shoulderPid.setCoefficient(Coefficient.P, 0, 0.0225, 0);
        // _shoulderPid.setCoefficient(Coefficient.I, 0, 0, 0);
        // _shoulderPid.setCoefficient(Coefficient.D, 0, 0.05, 0);
        // _shoulderPid.setInputRange(Constants.Arm.SHOULDER_MIN_ANGLE, Constants.Arm.SHOULDER_MAX_ANGLE);
        // _shoulderPid.setOutputRange(-0.7, 0.7);
        // _shoulderPid.setOutputRamp(0.05, 0.02);
        // _shoulderPid.setSetpointDeadband(1);
        // _shoulderPid.setSetpoint(0, getShoulderPosition());

        // _shoulderPid.setFeedForward(setpoint -> -0.036 * Math.sin(Math.toRadians(setpoint)));
    }

    @Override
    public void robotPeriodic()
    {
        CommandScheduler.getInstance().run();

        // if (!_limitSwitch.get())
        // {
        //     resetExtension();
        // }

        // System.out.println
        // (
        //     String.format
        //     (
        //         "Shoulder: %7.2f, Wrist: %5.2f, Twist: %5.2f, Extension: %9.4f",
        //         getShoulderPosition(),
        //         getWristPosition(),
        //         getTwistPosition(),
        //         0.0
        //         // getExtensionPosition()
        //         // Arm.getInstance().getShoulderAngle(),
        //         // Manipulator.getInstance().getWristAngle(),
        //         // Manipulator.getInstance().getTwistAngle(),
        //         // Arm.getInstance().getExtensionPosition()
        //     )
        // );
    }

  
    @Override
    public void autonomousInit() 
    {
        _autonomousCommand = _robotContainer.getAutonomousCommand();

        if (_autonomousCommand != null) 
        {
            _autonomousCommand.schedule();
        }
    }

    @Override
    public void teleopInit() 
    {
        if (_autonomousCommand != null) 
        {
            _autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic()
    {
        // //_wristMotor.setVoltage(_wristPid.calculate(getWristPosition()) * Constants.MOTOR_VOLTAGE);
        // double twistPosition = getTwistPosition();
        // double twistMotorOutput = _twistPid.calculate(twistPosition);
        // _twistMotor.setVoltage(twistMotorOutput * Constants.MOTOR_VOLTAGE);
        // double pitchMotorOutput = _shoulderPid.calculate(getShoulderPosition());
        // _exMotor.setVoltage(_exPid.calculate(getExtensionPosition()) * Constants.MOTOR_VOLTAGE);
        // _pitchMotor.setVoltage(pitchMotorOutput * Constants.MOTOR_VOLTAGE);
        // // System.out.println(String.format("Motor: %6.3f, Sensor: %6.3f", twistMotorOutput, twistPosition));
    }

    @Override
    public void testInit() 
    {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void simulationPeriodic()
    {
        REVPhysicsSim.getInstance().run();
    }

    // public double getTwistPosition()
    // {
    //     return MathUtil.inputModulus(360*(_twist.getAbsolutePosition()-0.73), -180, 180);
    // }

    // public double getWristPosition()
    // {
    //     return 360*(_wrist.getAbsolutePosition()-0.53);
    // }

    // public double getExtensionPosition()
    // {
    //     return EX_CONVERSION_FACTOR * _encoder.getPosition();
    // }

    // public void resetExtension()
    // {
    //     if(!_reset)
    //     {
    //         _reset = true;
    //         _encoder.setPosition(0);
    //         _exPid.setOutputRange(-1.0, 1.0);
    //     }
    // }

    // public double getShoulderPosition()
    // {
    //     return (_shoulder.get() - Constants.Arm.SHOULDER_SENSOR_MIN) * Constants.Arm.SHOULDER_SLOPE + Constants.Arm.SHOULDER_SCALED_MIN;
    // }
}
