package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
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

    public boolean hasTargets0()
    {
        return _latestResult0.hasTargets();
    }
    
    public boolean hasTargets1()
    {   
        return _latestResult1.hasTargets();
    }
    
  @Override
    public void periodic()
    {
        _latestResult0 = _camera0.getLatestResult();
        _latestResult1 = _camera1.getLatestResult();
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
}
