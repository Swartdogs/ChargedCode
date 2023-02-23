package frc.robot;

import java.util.HashMap;

import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Manipulator.HandMode;

public final class Constants 
{
    public static final double      LOOPS_PER_SECOND                =  50.0;
    public static final double      MOTOR_VOLTAGE                   =  12.0;
    public static final double      DEGREES_PER_REVOLUTION          = 360.0;
    public static final double      DEGREES_PER_HALF_REVOLUTION     = DEGREES_PER_REVOLUTION / 2;

    public static enum GameMode
    {
        Init,
        Disabled,
        Autonomous,
        Teleop,
        Test
    }

    public static class CAN
    {
        public static final int     SHOULDER_ID                     = 10;
        public static final int     SHOULDER_FOLLOWER_ID            = 11;
        public static final int     INTAKE_ID                       = 12;
        public static final int     WRIST_ID                        = 13;
        public static final int     TWIST_ID                        = 14;
        public static final int     EXTENSION_ID                    = 15;
    }

    public static class DIO
    {
        public static final int     SHOULDER_PORT                   = 0;
        public static final int     TWIST_PORT                      = 1;
        public static final int     WRIST_PORT                      = 2;
        public static final int     LIMIT_SWITCH_PORT               = 3;
        public static final int     LIGHT_SENSOR_PORT               = 4;
    } 

    public static class RobotLog
    {
        public static final int     NUM_DECIMAL_PLACES_IN_TIME      = 2;
        public static final int     NUM_DIGITS_IN_TIME              = 8;
        public static final int     HEADING_WIDTH                   = 80;
    }

    public static class Drive
    {
        public static final double  DRIVE_ENCODER_TO_DISTANCE       = 123.0 /* inches */ / 161294.5 /* encoder ticks */;

        public static final double  BASE_WIDTH                      = 20.00;
        public static final double  BASE_LENGTH                     = 28.75;

        public static final double  MAX_DRIVE_SPEED                 = 180.0; // in/s usually
        public static final double  MAX_ROTATE_SPEED                = 400.0;   // deg/s

        public static final double  TYPICAL_MODULE_DIST             = Math.sqrt((BASE_WIDTH/2)*(BASE_WIDTH/2)+(BASE_LENGTH/2)*(BASE_LENGTH/2));

        public static final int     FL_INDEX                        = 0;
        public static final int     FR_INDEX                        = 1;
        public static final int     BL_INDEX                        = 2;
        public static final int     BR_INDEX                        = 3;

        public static final double  FL_OFFSET                       =   37.5;
        public static final double  FR_OFFSET                       = -150.2;
        public static final double  BL_OFFSET                       = -158.2;
        public static final double  BR_OFFSET                       =  -53.3;
    }

    public static class Arm
    {
        /* PID Limits */
        public static final double  ARM_MAX_EXTENSION               =   24.000;
        public static final double  SHOULDER_MIN_ANGLE              = -100.000;
        public static final double  SHOULDER_MAX_ANGLE              =  100.000;  
        public static final double  HORIZONTAL_STAYING_POWER        =    0.036;

        /* Sensor Configuration */
        public static final double  SHOULDER_SENSOR_MIN             =    0.792;
        public static final double  SHOULDER_SENSOR_MAX             =    0.142;
        public static final double  SHOULDER_SCALED_MIN             = -116.000;
        public static final double  SHOULDER_SCALED_MAX             =  116.000;
        public static final double  SHOULDER_SLOPE                  = (SHOULDER_SCALED_MAX-SHOULDER_SCALED_MIN)/(SHOULDER_SENSOR_MAX-SHOULDER_SENSOR_MIN);
        public static final double  EXTENSION_CONVERSION_FACTOR     = 25.625 / 125.0947;
    }
 
    public static class Manipulator
    {
        /* PID Limits */
        public static final double  WRIST_MIN_ANGLE                 =  -40.00;
        public static final double  WRIST_MAX_ANGLE                 =   40.00;
        public static final double  TWIST_MIN_ROTATION              = -170.00;
        public static final double  TWIST_MAX_ROTATION              =  170.00;

        /* Sensor Configuration */
        public static final double  WRIST_OFFSET                    = 0.53;
        public static final double  TWIST_OFFSET                    = 0.73;

        /* Settings */
        public static final double  EJECT_TIME                      =  1.00;
        public static final double  INTAKE_SPEED                    = -0.75;
        public static final double  INTAKE_STOP_DELAY               =  0.2;
    }

    public static class Lookups
    {
        private static final HashMap<ArmTuple, ArmData> _lookup = new HashMap<ArmTuple, ArmData>(){{
            /*               Position                Side           Hand Mode                   Shoulder    Extension   Twist   Wrist */
            put(new ArmTuple(ArmPosition.Low,        ArmSide.Front, HandMode.Cone), new ArmData( 110,       24,           0,     30));
            put(new ArmTuple(ArmPosition.Low,        ArmSide.Front, HandMode.Cube), new ArmData( 110,       24,          90,     30));
            put(new ArmTuple(ArmPosition.Low,        ArmSide.Back,  HandMode.Cone), new ArmData(-110,       24,         180,    -30));
            put(new ArmTuple(ArmPosition.Low,        ArmSide.Back,  HandMode.Cube), new ArmData(-110,       24,          90,    -30));
            put(new ArmTuple(ArmPosition.Middle,     ArmSide.Front, HandMode.Cone), new ArmData(  90,       36,           0,      0));
            put(new ArmTuple(ArmPosition.Middle,     ArmSide.Front, HandMode.Cube), new ArmData(  90,       36,          90,      0));
            put(new ArmTuple(ArmPosition.Middle,     ArmSide.Back,  HandMode.Cone), new ArmData( -90,       36,         180,      0));
            put(new ArmTuple(ArmPosition.Middle,     ArmSide.Back,  HandMode.Cube), new ArmData( -90,       36,          90,      0));
            put(new ArmTuple(ArmPosition.High,       ArmSide.Front, HandMode.Cone), new ArmData(  70,       48,           0,    -30));
            put(new ArmTuple(ArmPosition.High,       ArmSide.Front, HandMode.Cube), new ArmData(  70,       48,          90,    -30));
            put(new ArmTuple(ArmPosition.High,       ArmSide.Back,  HandMode.Cone), new ArmData( -70,       48,         180,    -30));
            put(new ArmTuple(ArmPosition.High,       ArmSide.Back,  HandMode.Cube), new ArmData( -70,       48,          90,    -30));
            put(new ArmTuple(ArmPosition.Substation, ArmSide.Front, HandMode.Cone), new ArmData(  90,       36,           0,      0));
            put(new ArmTuple(ArmPosition.Substation, ArmSide.Front, HandMode.Cube), new ArmData(  90,       36,          90,      0));
            put(new ArmTuple(ArmPosition.Substation, ArmSide.Back,  HandMode.Cone), new ArmData( -90,       36,         180,      0));
            put(new ArmTuple(ArmPosition.Substation, ArmSide.Back,  HandMode.Cube), new ArmData( -90,       36,          90,      0));
            put(new ArmTuple(ArmPosition.Ground,     ArmSide.Front, HandMode.Cone), new ArmData( 110,       24,           0,     30));
            put(new ArmTuple(ArmPosition.Ground,     ArmSide.Front, HandMode.Cube), new ArmData( 110,       24,          90,     30));
            put(new ArmTuple(ArmPosition.Ground,     ArmSide.Back,  HandMode.Cone), new ArmData(-110,       24,         180,     30));
            put(new ArmTuple(ArmPosition.Ground,     ArmSide.Back,  HandMode.Cube), new ArmData(-110,       24,          90,     30));
            put(new ArmTuple(ArmPosition.Stow,       ArmSide.Front, HandMode.Cone), new ArmData(   0,        0,           0,    -90));
            put(new ArmTuple(ArmPosition.Stow,       ArmSide.Front, HandMode.Cube), new ArmData(   0,        0,           0,    -90));
            put(new ArmTuple(ArmPosition.Stow,       ArmSide.Back,  HandMode.Cone), new ArmData(   0,        0,         180,     90));
            put(new ArmTuple(ArmPosition.Stow,       ArmSide.Back,  HandMode.Cube), new ArmData(   0,        0,         180,     90));
        }};

        public static ArmData lookUpArmData(ArmPosition armPosition, ArmSide armSide, HandMode handMode)
        {
            return _lookup.get(new ArmTuple(armPosition, armSide, handMode));
        }
    }
}
