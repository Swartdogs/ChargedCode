package frc.robot;

import frc.robot.subsystems.drive.Vector;

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

        public static final int FL_INDEX = 0;
        public static final int FR_INDEX = 1;
        public static final int BL_INDEX = 2;
        public static final int BR_INDEX = 3;

        public static final double FL_OFFSET = 37.5;
        public static final double FR_OFFSET = -150.2;
        public static final double BL_OFFSET = -158.2;
        public static final double BR_OFFSET = -53.3;
    }

    public static class Vision
    {
        public static final double FRONT_CAMERA_HEIGHT = 0.0;   // height from the floor
        public static final double FRONT_CAMERA_YAW = 0.0;      // heading
        public static final double FRONT_CAMERA_PITCH = 0.0;     // angle of elevation
        public static final double FRONT_CAMERA_X = 2.0;        // inches right on the robot frame
        public static final double FRONT_CAMERA_Y = 3.0;        // inches forward on the robot from

        public static final double REAR_CAMERA_HEIGHT = 0.0;   // height from the floor
        public static final double REAR_CAMERA_YAW = 0.0;      // heading
        public static final double REAR_CAMERA_PITCH = 0.0;     // angle of elevation
        public static final double REAR_CAMERA_X = 2.0;        // inches right on the robot frame
        public static final double REAR_CAMERA_Y = 3.0;        // inches forward on the robot from

        public enum Camera
        {
            Front(new Vector(Constants.Vision.FRONT_CAMERA_X, Constants.Vision.FRONT_CAMERA_Y), Constants.Vision.FRONT_CAMERA_YAW, Constants.Vision.FRONT_CAMERA_PITCH, Constants.Vision.FRONT_CAMERA_HEIGHT),
            Rear( new Vector( Constants.Vision.REAR_CAMERA_X,  Constants.Vision.REAR_CAMERA_Y),  Constants.Vision.REAR_CAMERA_YAW,  Constants.Vision.REAR_CAMERA_PITCH,  Constants.Vision.REAR_CAMERA_HEIGHT);

            public final Vector position;
            public final double yaw;
            public final double pitch;
            public final double height;

            private Camera(Vector position, double yaw, double pitch, double height)
            {
                this.position = position;
                this.yaw = yaw;
                this.pitch = pitch;
                this.height = height;
            }
        }

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
        public static final double  EJECT_TIME              =   0;
        public static final double  INTAKE_SPEED            =   0;
    }
}
