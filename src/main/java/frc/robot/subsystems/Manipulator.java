package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
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

    private enum IntakeState
    {
        On,
        Off,
        Reverse,
        ConeEject
    }
    
    //Intake Controls
    private CANSparkMax         _intakeMotor;
    private IntakeState         _intakeState;

    //Settings
    private double              _ejectTime;
    private double              _intakeSpeed;
    private double              _placeSpeed;
    private double              _intakeStopDelay;
    private double              _intakeStowDelay;

    private boolean             _hasGamePiece;
    private double              _maxIntakeVelocity;

    private Manipulator()
    {
        _intakeMotor        = new CANSparkMax(Constants.CAN.INTAKE_ID, MotorType.kBrushless);
        _intakeState        = IntakeState.Off;

        _ejectTime          = Constants.Manipulator.EJECT_TIME;
        _intakeSpeed        = Constants.Manipulator.INTAKE_SPEED;
        _placeSpeed         = Constants.Manipulator.PLACE_SPEED;
        _intakeStopDelay    = Constants.Manipulator.INTAKE_STOP_DELAY;
        _intakeStowDelay    = Constants.Manipulator.INTAKE_STOW_DELAY;

        _intakeMotor.restoreFactoryDefaults();

        _intakeMotor.setInverted(true);

        _intakeMotor.setIdleMode(IdleMode.kBrake);

        if (RobotBase.isSimulation())
        {
            REVPhysicsSim.getInstance().addSparkMax(_intakeMotor, DCMotor.getNeo550(1));
        }

        RobotLog.getInstance().log("Created Manipulator Subsystem");
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
                speed = hasGamePiece() ? Constants.Manipulator.INTAKE_HOLD_SPEED : 0;
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
    
    public boolean hasGamePiece()
    {
        return _hasGamePiece;    
    }
    
    //Settings Functions
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

    public double getIntakeVelocity()
    {
        return _intakeMotor.getEncoder().getVelocity();
    }

    @Override
    public void periodic()
    {
        _intakeMotor.setInverted(true);

        double intakeSpeed = getIntakeSpeed();
        double intakeVelocity = getIntakeVelocity();

        if (intakeSpeed == 0.0)
        {
            
        }

        if (hasGamePiece())
        {

        }
        else
        {

        }

        _intakeMotor.setVoltage(intakeSpeed * Constants.MOTOR_VOLTAGE);
    }
}
