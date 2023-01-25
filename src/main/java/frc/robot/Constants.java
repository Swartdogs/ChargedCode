package frc.robot;

public final class Constants 
{
    public static final int LOOPS_PER_SECOND            = 50;

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

        public static final double BASE_WIDTH  = 20;
        public static final double BASE_LENGTH = 28.75;

        public static final double MAX_DRIVE_SPEED = 180.0;// in/s usually
        public static final double MAX_ROTATE_SPEED = 400;// deg/s

        public static final double TYPICAL_MODULE_DIST = Math.sqrt((BASE_WIDTH/2)*(BASE_WIDTH/2)+(BASE_LENGTH/2)*(BASE_LENGTH/2));
    }
}
