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

    public Transform2d getTargetPosition()
    {
        Transform3d transformToTarget = _latestResult0.getBestTarget().getBestCameraToTarget();

        return new Transform2d();
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
    public Pose3d robotPose0()
    {
        PhotonTrackedTarget target0 = _latestResult0.getBestTarget();
        Transform3d cameraToRobot0 = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0));
        Pose3d robotPose0 = PhotonUtils.estimateFieldToRobotAprilTag(target0.getBestCameraToTarget(), aprilTagFieldLayout().getTagPose(target0.getFiducialId()).get(), cameraToRobot0);
        return robotPose0;
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


