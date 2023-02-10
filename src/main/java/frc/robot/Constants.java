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
        public static final double DRIVE_GEAR_RATIO          = (14.0 / 36.0) * (15.0 / 45.0);
        public static final double DRIVE_WHEEL_DIAMETER      = 4.0;
        public static final double DRIVE_ENCODER_TO_DISTANCE = DRIVE_GEAR_RATIO * Math.PI * DRIVE_WHEEL_DIAMETER;

        public static final double BASE_WIDTH                = 20;
        public static final double BASE_LENGTH               = 28.75;

        public static final double MAX_DRIVE_SPEED           = 180.0; // in/s usually
        public static final double MAX_ROTATE_SPEED          = 400;   // deg/s

        public static final double TYPICAL_MODULE_DIST       = Math.sqrt((BASE_WIDTH/2)*(BASE_WIDTH/2)+(BASE_LENGTH/2)*(BASE_LENGTH/2));

        public static final int FL_INDEX = 0;
        public static final int FR_INDEX = 1;
        public static final int BL_INDEX = 2;
        public static final int BR_INDEX = 3;

        public static final int    FL_DRIVE_CAN_ID           = 1;
        public static final int    FL_ROTATE_CAN_ID          = 2;
        public static final int    FR_DRIVE_CAN_ID           = 3;
        public static final int    FR_ROTATE_CAN_ID          = 4;
        public static final int    BR_DRIVE_CAN_ID           = 5;
        public static final int    BR_ROTATE_CAN_ID          = 6;
        public static final int    BL_DRIVE_CAN_ID           = 7;
        public static final int    BL_ROTATE_CAN_ID          = 8;

        public static final int    FL_ROTATE_SENSOR_PORT     = 3;
        public static final int    FR_ROTATE_SENSOR_PORT     = 2;
        public static final int    BR_ROTATE_SENSOR_PORT     = 1;
        public static final int    BL_ROTATE_SENSOR_PORT     = 0;
    }

    public static class Vision
    {
        public static final double FRONT_CAMERA_HEIGHT = 0.0;   // height from the floor
        public static final double FRONT_CAMERA_YAW = 0.0;      // heading
        public static final double FRONT_CAMERA_TILT = 0.0;     // angle of elevation
        public static final double FRONT_CAMERA_X = 2.0;        // inches right on the robot frame
        public static final double FRONT_CAMERA_Y = 3.0;        // inches forward on the robot from

        public static final double REAR_CAMERA_HEIGHT = 0.0;   // height from the floor
        public static final double REAR_CAMERA_YAW = 0.0;      // heading
        public static final double REAR_CAMERA_TILT = 0.0;     // angle of elevation
        public static final double REAR_CAMERA_X = 2.0;        // inches right on the robot frame
        public static final double REAR_CAMERA_Y = 3.0;        // inches forward on the robot from


        // field layout https://firstfrc.blob.core.windows.net/frc2023/FieldAssets/2023LayoutMarkingDiagram.pdf
        public static final double GRID_TAG_HEIGHT       = 18.22;
        public static final double SUBSTATION_TAG_HEIGHT = 27.38;
        public static final double GRID_TAG_1_Y          = -116.0;   // found about 1/4" difference between 2 different calculations
        public static final double GRID_TAG_2_Y          = -50.0;
        public static final double GRID_TAG_3_Y          = 16.0;
        public static final double SUBSTATION_TAG_X      = 311.35;
        public static final double GRID_TAG_X            = 285.16;  // assumes the center line is 0, 40.45" from DS wall
        public static final double SUBSTATION_TAG_Y      = 14.25;
    }

    public static class Arm
    {
        public static final int LIMIT_SWITCH_CHANNEL        = 0;
        public static final int PITCH_ENCODER_CHANNEL       = 1;
        public static final int LINEAR_MOTOR_CAN_ID         = 0;
        public static final int PITCH_MOTOR_CAN_ID          = 1;  
        public static final int FOLLOWER_PITCH_MOTOR_CAN_ID = 2;  
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
        public static final double  EJECT_TIME              =   0;
        public static final double  INTAKE_SPEED            =   0;
    }
}
