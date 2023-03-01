package frc.robot;

import java.util.HashMap;

import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Manipulator.HandMode;
import frc.robot.subsystems.drive.Vector;

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
        public static final double DRIVE_GEAR_RATIO          = (14.0 / 36.0) * (15.0 / 45.0);
        public static final double DRIVE_WHEEL_DIAMETER      = 4.0;
        public static final double DRIVE_ENCODER_TO_DISTANCE = DRIVE_GEAR_RATIO * Math.PI * DRIVE_WHEEL_DIAMETER;

        public static final double  BASE_WIDTH                      = 20.00;
        public static final double  BASE_LENGTH                     = 28.75;

        public static final double  MAX_DRIVE_SPEED                 = 180.0; // in/s usually
        public static final double  MAX_ROTATE_SPEED                = 400.0;   // deg/s

        public static final double  TYPICAL_MODULE_DIST             = Math.sqrt((BASE_WIDTH/2)*(BASE_WIDTH/2)+(BASE_LENGTH/2)*(BASE_LENGTH/2));

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

        public static final double FL_OFFSET = -113.2;
        public static final double FR_OFFSET = 26.0;
        public static final double BL_OFFSET = -212.5;
        public static final double BR_OFFSET = 146.2;
    }

    public static class Vision
    {
        public static final double FRONT_CAMERA_HEIGHT = 0.75;   // height from the floor
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
        /* PID Limits */
        public static final double  ARM_MAX_EXTENSION               =   26.500;
        public static final double  SHOULDER_MIN_ANGLE              = -115.000;
        public static final double  SHOULDER_MAX_ANGLE              =  115.000;  
        public static final double  HORIZONTAL_STAYING_POWER        =    0.036;
        public static final double  EXTENSION_STAYING_POWER         =    0.040;

        /* Sensor Configuration */
        public static final double  SHOULDER_SENSOR_MIN             =    0.792;
        public static final double  SHOULDER_SENSOR_MAX             =    0.142;
        public static final double  SHOULDER_SCALED_MIN             = -116.000;
        public static final double  SHOULDER_SCALED_MAX             =  116.000;
        public static final double  SHOULDER_SLOPE                  = (SHOULDER_SCALED_MAX-SHOULDER_SCALED_MIN)/(SHOULDER_SENSOR_MAX-SHOULDER_SENSOR_MIN);
        public static final double  EXTENSION_CONVERSION_FACTOR     = 25.625 / 125.0947;

        public static final double  EXTENSION_JOYSTICK_RATE         = 10.0 / Constants.LOOPS_PER_SECOND; // inches per second
        public static final double  SHOULDER_JOYSTICK_RATE          = 30.0 / Constants.LOOPS_PER_SECOND; // degrees per second
    }
 
    public static class Manipulator
    {
        /* PID Limits */
        public static final double  WRIST_MIN_ANGLE                 = -100.00;
        public static final double  WRIST_MAX_ANGLE                 =  100.00;
        public static final double  TWIST_MIN_ROTATION              = -116.00;
        public static final double  TWIST_MAX_ROTATION              =  116.00;

        /* Sensor Configuration */
        public static final double  WRIST_OFFSET                    = 0.69;
        public static final double  TWIST_OFFSET                    = 0.73;

        /* Settings */
        public static final double  EJECT_TIME                      = 0.50;
        public static final double  INTAKE_SPEED                    = 0.75;
        public static final double  PLACE_SPEED                     = 0.20;
        public static final double  INTAKE_STOP_DELAY               = 0.30;
        public static final double  INTAKE_STOW_DELAY               = 0.50;

        public static final double  WRIST_JOYSTICK_RATE             = 60.0 / Constants.LOOPS_PER_SECOND; // Degrees per second
    }

    public static class Lookups
    {
        private static final HashMap<ArmTuple, ArmData> _lookup = new HashMap<ArmTuple, ArmData>(){{
            /*               Position                Side           Hand Mode                   Shoulder    Extension   Twist   Wrist */
            put(new ArmTuple(ArmPosition.Low,        ArmSide.Front, HandMode.Cone), new ArmData(-105,        0,          90,    -15));
            put(new ArmTuple(ArmPosition.Low,        ArmSide.Front, HandMode.Cube), new ArmData(-105,        0,          90,    -15));
            put(new ArmTuple(ArmPosition.Low,        ArmSide.Back,  HandMode.Cone), new ArmData( 105,        0,         -90,     15));
            put(new ArmTuple(ArmPosition.Low,        ArmSide.Back,  HandMode.Cube), new ArmData( 105,        0,         -90,     15));
            put(new ArmTuple(ArmPosition.Middle,     ArmSide.Front, HandMode.Cone), new ArmData( -60,        0,          90,     -0));
            put(new ArmTuple(ArmPosition.Middle,     ArmSide.Front, HandMode.Cube), new ArmData( -60,        0,          90,     40));
            put(new ArmTuple(ArmPosition.Middle,     ArmSide.Back,  HandMode.Cone), new ArmData(  60,        0,         -90,      0));
            put(new ArmTuple(ArmPosition.Middle,     ArmSide.Back,  HandMode.Cube), new ArmData(  60,        0,         -90,    -40));
            put(new ArmTuple(ArmPosition.High,       ArmSide.Front, HandMode.Cone), new ArmData( -52,       26,          90,      5));
            put(new ArmTuple(ArmPosition.High,       ArmSide.Front, HandMode.Cube), new ArmData( -55,       18,          90,     35));
            put(new ArmTuple(ArmPosition.High,       ArmSide.Back,  HandMode.Cone), new ArmData(  52,       26,         -90,     -5));
            put(new ArmTuple(ArmPosition.High,       ArmSide.Back,  HandMode.Cube), new ArmData(  55,       18,         -90,    -35));
            put(new ArmTuple(ArmPosition.Substation, ArmSide.Front, HandMode.Cone), new ArmData(  35,        0,         -90,    -45));
            put(new ArmTuple(ArmPosition.Substation, ArmSide.Front, HandMode.Cube), new ArmData(  35,        0,         -90,    -45));
            put(new ArmTuple(ArmPosition.Substation, ArmSide.Back,  HandMode.Cone), new ArmData( -35,        0,          90,     45));
            put(new ArmTuple(ArmPosition.Substation, ArmSide.Back,  HandMode.Cube), new ArmData( -35,        0,          90,     45));
            put(new ArmTuple(ArmPosition.Ground,     ArmSide.Front, HandMode.Cone), new ArmData( 110,        0,         -90,     15));
            put(new ArmTuple(ArmPosition.Ground,     ArmSide.Front, HandMode.Cube), new ArmData(  90,        0,           0,    -45));
            put(new ArmTuple(ArmPosition.Ground,     ArmSide.Back,  HandMode.Cone), new ArmData(-110,        0,          90,    -15));
            put(new ArmTuple(ArmPosition.Ground,     ArmSide.Back,  HandMode.Cube), new ArmData( -90,        0,          -0,     45));
            put(new ArmTuple(ArmPosition.Stow,       ArmSide.Front, HandMode.Cone), new ArmData(   0,        0,          90,      0));
            put(new ArmTuple(ArmPosition.Stow,       ArmSide.Front, HandMode.Cube), new ArmData(   0,        0,          90,      0));
            put(new ArmTuple(ArmPosition.Stow,       ArmSide.Back,  HandMode.Cone), new ArmData(  -0,        0,         -90,     -0));
            put(new ArmTuple(ArmPosition.Stow,       ArmSide.Back,  HandMode.Cube), new ArmData(  -0,        0,         -90,     -0));
        }};

        public static ArmData lookUpArmData(ArmPosition armPosition, ArmSide armSide, HandMode handMode)
        {
            return _lookup.get(new ArmTuple(armPosition, armSide, handMode));
        }
    }
}
