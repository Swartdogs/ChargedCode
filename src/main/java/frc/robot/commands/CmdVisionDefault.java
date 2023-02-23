package frc.robot.commands;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Vision.Camera;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class CmdVisionDefault extends CommandBase
{
    private Vision _vision;
    private Drive _drive;

    public CmdVisionDefault()
    {
        _vision = Vision.getInstance();
        _drive = Drive.getInstance();

        addRequirements(_vision);
    }

    @Override
    public void initialize()
    {
        // logging
    }

    @Override
    public void execute()
    {
        PhotonTrackedTarget target = null;

        Camera targetCamera = null;

        if (_vision.getFrontResult().hasTargets())
        {
            target = _vision.getFrontResult().getBestTarget();
            targetCamera = Camera.Front;
        }

        if (_vision.getRearResult().hasTargets())
        {
            PhotonTrackedTarget rearTarget =  _vision.getRearResult().getBestTarget();
            
            if (target == null || rearTarget.getArea() > target.getArea())
            {
                target = rearTarget;
                targetCamera = Camera.Rear;
            }
        }

        // now that we have the camera and target, do math with it

        if (target != null)
        {
            targetCamera.position.translatePolarPosition(0, _drive.getHeading());

            Vector cameraFieldPosition =  _vision.getCameraFieldPosition(target.getFiducialId(), targetCamera.pitch + target.getPitch(), target.getSkew(), targetCamera.height);
            Vector fieldPosition = cameraFieldPosition.subtract(targetCamera.position);

            double skew = Math.toDegrees(Math.atan((target.getDetectedCorners().get(0).y - target.getDetectedCorners().get(3).y) / (target.getDetectedCorners().get(0).x - target.getDetectedCorners().get(3).x)));

            double fieldHeading = _vision.getCameraHeading(target.getFiducialId(), target.getYaw(), skew) + targetCamera.yaw;

            double positionVariance = fieldPosition.subtract(_drive.getFieldPosition()).getMagnitude();
            double headingVariance = Math.abs(fieldHeading - _drive.getHeading());

            //_drive.fusePosition(fieldPosition, positionVariance);
            //_drive.fuseHeading(fieldHeading, headingVariance);
        }
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public boolean runsWhenDisabled()
    {
        return true;
    }
}
