package frc.robot.subsystems;

import java.io.IOException;
import java.nio.file.Path;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
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
   
    private final PhotonCamera _camera = new PhotonCamera("camera1");
    private PhotonPipelineResult _latestResult = new PhotonPipelineResult();
    
   
    private Vision()
    {

    } 

    public boolean hasTargets()
    {
        return _latestResult.hasTargets();
    }

    public Transform2d getTargetPosition()
    {
        Transform3d transformToTarget = _latestResult.getBestTarget().getBestCameraToTarget();

        return new Transform2d();
    }

  @Override
    public void periodic()
    {
        _latestResult = _camera.getLatestResult();
       
        PhotonTrackedTarget target = _latestResult.getBestTarget();
    
        double targetRange = PhotonUtils.calculateDistanceToTargetMeters(0.921, 0.2794, 0, Units.degreesToRadians(_latestResult.getBestTarget().getPitch()));
            
       if (_latestResult.hasTargets() == true)
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
    
    


    

    
}


