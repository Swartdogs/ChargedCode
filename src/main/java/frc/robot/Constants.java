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

    public static class Arm
    {
        public static final int LIMIT_SWITCH_CHANNEL = 0;
        public static final int PITCH_ENCODER_CHANNEL = 1;
        public static final int LINEAR_MOTOR_CAN_ID = 0;
        public static final int PITCH_MOTOR_CAN_ID = 1;  
        public static final int FOLLOWER_PITCH_MOTOR_CAN_ID = 2;  
    }

}
