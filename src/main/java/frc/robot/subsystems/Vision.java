package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.Vector;

public class Vision extends SubsystemBase
{
    private static Vision  _instance;
    
    public static Vision getInstance()
    {
        if (_instance == null) 
        {
            _instance = new Vision();
        }
       
        return _instance;
    }
   
    private final PhotonCamera   _frontCam;
    private final PhotonCamera   _rearCam;
    private PhotonPipelineResult _frontLatestResult;
    private PhotonPipelineResult _rearLatestResult;

    
    public Vision()
    {
        _frontCam          = new PhotonCamera("lifecam");
        _rearCam           = new PhotonCamera("lifecam");// FIXME: needs name of second camera
        _frontLatestResult = new PhotonPipelineResult(); 
        _rearLatestResult  = new PhotonPipelineResult();

        RobotLog.getInstance().log("Created Vision Subsystem");
    } 

    public PhotonPipelineResult getFrontResult()
    {
        return _frontLatestResult;
    }

    public PhotonPipelineResult getRearResult()
    {
        return _rearLatestResult;
    }

    private double getTargetHeight(int id)
    {
        double height = 0.0;

        switch (id)
        {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
                height = Constants.Vision.GRID_TAG_HEIGHT;
                break;
            case 4:
            case 5:
                height = Constants.Vision.SUBSTATION_TAG_HEIGHT;
        }

        return height;
    }
    
    public double getTargetAngle(int id)
    {
        double targetAngle;

        switch (id)
        {
            case 1:
            case 2:
            case 3:
            case 4:
                targetAngle = -90.0;
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            default:
                targetAngle = 90.0;
        }

        return targetAngle;
    }

    public Vector getTargetFieldPosition(int id)
    {
        Vector position = new Vector();
        
        // set x
        switch (id)
        {
            case 1:
            case 2:
            case 3:
                position.setX(Constants.Vision.GRID_TAG_X);
                break;
            case 4:
                position.setX(Constants.Vision.SUBSTATION_TAG_X);
                break;
            case 5:
                position.setX(-Constants.Vision.SUBSTATION_TAG_X);
                break;
            case 6:
            case 7:
            case 8:
                position.setX(-Constants.Vision.GRID_TAG_X);
                break;
        }

        // set y
        switch (id)
        {
            case 1:
            case 8:
                position.setY(Constants.Vision.GRID_TAG_1_Y);
                break;
            case 2:
            case 7:
                position.setY(Constants.Vision.GRID_TAG_2_Y);
                break;
            case 3:
            case 6:
                position.setY(Constants.Vision.GRID_TAG_3_Y);
                break;
            case 4:
            case 5:
                position.setY(Constants.Vision.SUBSTATION_TAG_Y);
                break;
        }

        return position;
    }

    public Vector positionFromTarget(int id, double pitch, double skew, double cameraHeight)
    {
        double heightDiff = getTargetHeight(id) - cameraHeight;// opposite of pitch angle

        double distance = heightDiff / Math.tan(Math.toRadians(pitch));// tan(pitch) = height / dist

        Vector position = new Vector();

        position.setPolarPosition(distance, skew);

        return position;
    }
    
    public Vector getCameraFieldPosition(int id, double pitch, double skew, double cameraHeight)
    {
        double targetAngle = getTargetAngle(id);

        Vector targetPosition = getTargetFieldPosition(id);
        Vector robotFromTarget = positionFromTarget(id, pitch, skew, cameraHeight);

        robotFromTarget.translatePolarPosition(0, targetAngle);
        Vector fieldPosition = targetPosition.add(robotFromTarget);

        return fieldPosition;
    }

    public double getCameraHeading(int id, double yaw, double skew)
    {
        double targetAngle = getTargetAngle(id);

        System.out.println(String.format("%6.2f", skew));
        return -skew + yaw - targetAngle;
    }

    @Override
    public void periodic()
    {
        _frontLatestResult = _frontCam.getLatestResult();
        _rearLatestResult = _rearCam.getLatestResult();
    }
}
