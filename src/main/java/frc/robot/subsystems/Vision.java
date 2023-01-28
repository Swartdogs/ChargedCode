package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
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
   
    private final PhotonCamera   _frontCam;
    private final PhotonCamera   _rearCam;
    private PhotonPipelineResult _frontLatestResult;
    private PhotonPipelineResult _rearLatestResult;

    
    public Vision()
    {
        _frontCam          = new PhotonCamera("camera1");
        _rearCam           = new PhotonCamera("camera2");
        _frontLatestResult = new PhotonPipelineResult(); 
        _rearLatestResult  = new PhotonPipelineResult();
    } 

    public boolean frontCamHasTargets()
    {
        return _frontLatestResult.hasTargets();
    }
    
    public boolean rearCamHasTargets()
    {   
        return _rearLatestResult.hasTargets();
    }
    
    public double frontSkew()
    {
        return _frontLatestResult.getBestTarget().getSkew();
    }
 
    public double frontYaw()
    {
        return _frontLatestResult.getBestTarget().getYaw();
    }
   
    public double frontPitch()
    {
        return _frontLatestResult.getBestTarget().getPitch();
    }
    
    public double rearSkew()
    {
        return _rearLatestResult.getBestTarget().getSkew();
    }
  
    public double rearYaw()
    {
        return _rearLatestResult.getBestTarget().getYaw();
    }
   
    public double rearPitch()
    {
        return _rearLatestResult.getBestTarget().getPitch();
    } 
   
    @Override
    public void periodic()
    {
        _frontLatestResult = _frontCam.getLatestResult();
        _rearLatestResult = _rearCam.getLatestResult();
    }    
}
