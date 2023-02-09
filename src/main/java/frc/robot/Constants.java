package frc.robot;

public final class Constants 
{
    public static final int    LOOPS_PER_SECOND            = 50;
    public static final double MOTOR_VOLTAGE               = 12.0;

    public static enum GameMode
    {
        Init,
        Disabled,
        Autonomous,
        Teleop,
        Test
    }

    public static class RobotLog
    {
        public static final int NUM_DECIMAL_PLACES_IN_TIME = 2;
        public static final int NUM_DIGITS_IN_TIME         = 8;
        public static final int HEADING_WIDTH              = 80;
    }

    public static class Drive
    {
        public static final double DRIVE_ENCODER_TO_DISTANCE = 123.0 /* inches */ / 161294.5 /* encoder ticks */;

        public static final double BASE_WIDTH                = 20;
        public static final double BASE_LENGTH               = 28.75;

        public static final double MAX_DRIVE_SPEED           = 180.0; // in/s usually
        public static final double MAX_ROTATE_SPEED          = 400;   // deg/s

        public static final double TYPICAL_MODULE_DIST       = Math.sqrt((BASE_WIDTH/2)*(BASE_WIDTH/2)+(BASE_LENGTH/2)*(BASE_LENGTH/2));

        public static final int FL_INDEX = 0;
        public static final int FR_INDEX = 1;
        public static final int BL_INDEX = 2;
        public static final int BR_INDEX = 3;

        public static final double FL_OFFSET = 37.5;
        public static final double FR_OFFSET = -150.2;
        public static final double BL_OFFSET = -158.2;
        public static final double BR_OFFSET = -53.3;
    }

    public static class Arm
    {
        public static final int LIMIT_SWITCH_CHANNEL        = 0;
        public static final int PITCH_ENCODER_CHANNEL       = 1;
        public static final int LINEAR_MOTOR_CAN_ID         = 0;
        public static final int PITCH_MOTOR_CAN_ID          = 1;  
        public static final int FOLLOWER_PITCH_MOTOR_CAN_ID = 20;
        public static final double ARM_MAX_EXTENSION        = 200.0;
        public static final double SHOULDER_MIN_ANGLE       = -180.0;
        public static final double SHOULDER_MAX_ANGLE       = 180.0;  
    }
 
    public static class Manipulator
    {
        public static final int     WRIST_MOTOR_CAN_ID      =   9;
        public static final int     WRIST_ENCODER_PORT      =   5;
        public static final double  WRIST_MIN_ANGLE         =  -90.0;
        public static final double  WRIST_MAX_ANGLE         =   90.0;
        public static final int     TWIST_MOTOR_CAN_ID      =   10;
        public static final int     TWIST_ENCODER_PORT      =   6;
        public static final double  TWIST_MIN_ROTATION      =  -180.0;
        public static final double  TWIST_MAX_ROTATION      =   180.0;
        public static final int     INTAKE_MOTOR_CAN_ID     =   12;
        public static final int     INTAKE_SENSOR_PORT      =   7;
        public static final double  INTAKE_SPEED            =   0.75;
        public static final double  EJECT_SPEED             =   0.2;
        public static final double  EJECT_TIME              =   0;
    }
}
