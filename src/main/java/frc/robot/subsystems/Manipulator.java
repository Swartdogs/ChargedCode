package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.groups.GrpSetArmPosition;
import frc.robot.subsystems.Arm.ArmPosition;

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

    private enum IntakeState
    {
        On,
        Off,
        Reverse
    }

    // Intake Controls
    private CANSparkMax  _intakeMotor;
    private DigitalInput _intakeSensor;
    private IntakeState  _intakeState;

    // Settings
    private double       _ejectTime;
    private double       _intakeSpeed;

    // Simulation
    private DIOSim       _intakeSensorSim;

    private Manipulator()
    {
        _intakeMotor        = new CANSparkMax(Constants.Manipulator.INTAKE_MOTOR_CAN_ID, MotorType.kBrushless);
        _intakeSensor       = new DigitalInput(Constants.Manipulator.INTAKE_SENSOR_PORT);
        _intakeState        = IntakeState.Off;

        _ejectTime          = Constants.Manipulator.EJECT_TIME;
        _intakeSpeed        = Constants.Manipulator.INTAKE_SPEED;

        if (RobotBase.isSimulation())
        {
            _intakeSensorSim = new DIOSim(_intakeSensor);

            REVPhysicsSim.getInstance().addSparkMax(_intakeMotor, DCMotor.getNeo550(1));
        }

        RobotLog.getInstance().log("Created Manipulator Subsystem");
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
                speed = -_intakeSpeed;
                break;
        }

        return speed;
    }

    public boolean hasGamePiece()
    {
        return !_intakeSensor.get();    
    }

    //Settings Functions
    public void setEjectTime(double time)
    {
        _ejectTime = time;
    }

    public void setIntakeSpeed(double speed)
    {
        _intakeSpeed = speed;
    }

    // Commands
    public Command intakeGamePieceCommand()
    {
        return this
            .runOnce(() -> {
                _intakeMotor.setVoltage(_intakeSpeed * Constants.MOTOR_VOLTAGE);
                _intakeState = IntakeState.On;
            })
            .andThen
            (
                Commands.either
                (
                    Commands.waitUntil(this::hasGamePiece),
                    Commands.waitSeconds(_ejectTime),
                    RobotBase::isReal
                )
            )
            .finallyDo(interrupted -> {
                _intakeMotor.setVoltage(0);
                _intakeState = IntakeState.Off;

                if (RobotBase.isSimulation() && !interrupted)
                {
                    _intakeSensorSim.setValue(false);
                }
            });
    }

    public Command placeGamePieceCommand()
    {
        return this
            .runOnce(() -> {
                _intakeMotor.setVoltage(-_intakeSpeed * Constants.MOTOR_VOLTAGE);
                _intakeState = IntakeState.Reverse;        
            })
            .andThen(Commands.waitSeconds(_ejectTime))
            .finallyDo(interrupted -> {
                _intakeMotor.setVoltage(0);
                _intakeState = IntakeState.Off;      
                
                if (RobotBase.isSimulation())
                {
                    _intakeSensorSim.setValue(true);
                }
            });
    }

    public Command pickupCommand(ArmPosition position)
    {
        return
            new GrpSetArmPosition(position)
            .andThen
            (
                intakeGamePieceCommand(),
                Arm.getInstance().stowCommand()
            )
            .unless(this::hasGamePiece);
    }
}
