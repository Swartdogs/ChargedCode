package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.geometry.Transform2d;
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
        var transformToTarget = _latestResult.getBestTarget().getBestCameraToTarget();

        return new Transform2d()
    }

    @Override
    public void periodic()
    {
        _latestResult = _camera.getLatestResult();

    }    
}
