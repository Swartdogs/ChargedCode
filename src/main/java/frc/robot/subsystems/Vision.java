package frc.robot.subsystems;

import java.io.IOException;
import java.nio.file.Path;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
   
    private final PhotonCamera _camera0 = new PhotonCamera("camera1");
    private final PhotonCamera _camera1 = new PhotonCamera("camera2");
    private PhotonPipelineResult _latestResult0 = new PhotonPipelineResult();
    private PhotonPipelineResult _latestResult1 = new PhotonPipelineResult();
    
   
    private Vision()
    {

    } 

    public boolean hasTargets()
    {
        return _latestResult0.hasTargets();
    }
    
  @Override
    public void periodic()
    {
        _latestResult0 = _camera0.getLatestResult();
        _latestResult1 = _camera1.getLatestResult();
    
        double targetRange = PhotonUtils.calculateDistanceToTargetMeters(0.921, 0.2794, 0, Units.degreesToRadians(_latestResult0.getBestTarget().getPitch()));
            
       if (_latestResult0.hasTargets() == true)
       {
        System.out.print(targetRange);
       }
    }    
    public AprilTagFieldLayout aprilTagFieldLayout()
    {
        AprilTagFieldLayout aprilTagFieldLayout = null;
        try
        {
        aprilTagFieldLayout = new AprilTagFieldLayout((Path) AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile));
        }
        catch(IOException e)
        {

        }

        return aprilTagFieldLayout;
    }
    public double skew0()
    {
        PhotonTrackedTarget target0 = _latestResult0.getBestTarget();
        double skew0 = target0.getSkew();
        return skew0;
    }
    public double yaw0()
    {
        PhotonTrackedTarget target0 = _latestResult0.getBestTarget();
        double yaw0 = target0.getYaw();
        return yaw0;
    }
    public double pitch0()
    {
        PhotonTrackedTarget target0 = _latestResult0.getBestTarget();
        double pitch0 = target0.getPitch();
        return pitch0;
    }
    
    public double skew1()
    {
        PhotonTrackedTarget target1 = _latestResult1.getBestTarget();
        double skew1 = target1.getSkew();
        return skew1;
    }
    public double yaw1()
    {
        PhotonTrackedTarget target1 = _latestResult1.getBestTarget();
        double yaw1 = target1.getYaw();
        return yaw1;
    }
    public double pitch1()
    {
        PhotonTrackedTarget target1 = _latestResult1.getBestTarget();
        double pitch1 = target1.getPitch();
        return pitch1;
    }
    
    public Pose3d robotPose1()
    {
        PhotonTrackedTarget target1 = _latestResult1.getBestTarget();
        Transform3d cameraToRobot1 = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0));
        Pose3d robotPose1 = PhotonUtils.estimateFieldToRobotAprilTag(target1.getBestCameraToTarget(), aprilTagFieldLayout().getTagPose(target1.getFiducialId()).get(), cameraToRobot1);
        return robotPose1;
    }


    public void targets()
    {
        PhotonTrackedTarget target0 = _latestResult0.getBestTarget();
        PhotonTrackedTarget target1 = _latestResult1.getBestTarget();
    }
    
    
    


    

    
}


