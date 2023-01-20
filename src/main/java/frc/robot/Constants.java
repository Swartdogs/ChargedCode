package frc.robot;

public final class Constants 
{
    public static final double LOOPS_PER_SECOND            = 50;

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

    public static class Manipulator
    {
        public static final int WRIST_MOTOR_CAN_ID         =      0;
        public static final int WRIST_ENCODER_PORT         =      0;
        public static final double WRIST_MIN_ANGLE         =  -90.0;
        public static final double WRIST_MAX_ANGLE         =   90.0;
        public static final int TWIST_MOTOR_CAN_ID         =      0;
        public static final int TWIST_ENCODER_PORT         =      0;
        public static final double TWIST_MIN_ROTATION      = -180.0;
        public static final double TWIST_MAX_ROTATION      =  180.0;
        public static final int GRASP_MOTOR_CAN_ID         =      0;
        public static final int GRASP_ENCODER_PORT         =      0;
        public static final double GRASP_MIN_POSITION      =    0.0;
        public static final double GRASP_MAX_POSITION      =    0.0;
        public static final int INTAKE_MOTOR_CAN_ID        =      0;
        public static final int INTAKE_SENSOR_PORT         =      0;
        public static final double INTAKE_HOLD_SPEED       =    0.0;
    }
}
