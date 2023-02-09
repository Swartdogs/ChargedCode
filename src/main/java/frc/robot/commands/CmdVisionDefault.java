package frc.robot.commands;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class CmdVisionDefault extends CommandBase
{
    Vision _vision;
    Drive _drive;

    private class Camera
    {
        public Vector position;
        public double yaw;
        public double pitch;
        public double height;
    }

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

        Camera targetCamera = new Camera();

        if (_vision.getFrontResult().hasTargets())
        {
            target = _vision.getFrontResult().getBestTarget();
            targetCamera.position = new Vector(Constants.Vision.FRONT_CAMERA_X, Constants.Vision.FRONT_CAMERA_Y);
            targetCamera.yaw = Constants.Vision.FRONT_CAMERA_YAW;
            targetCamera.pitch = Constants.Vision.FRONT_CAMERA_TILT;
            targetCamera.height = Constants.Vision.FRONT_CAMERA_HEIGHT;
        }

        if (_vision.getRearResult().hasTargets())
        {
            PhotonTrackedTarget rearTarget =  _vision.getRearResult().getBestTarget();
            
            if (target == null || rearTarget.getArea() > target.getArea())
            {
                target = rearTarget;
                targetCamera.position = new Vector(Constants.Vision.REAR_CAMERA_X, Constants.Vision.REAR_CAMERA_Y);
                targetCamera.yaw = Constants.Vision.REAR_CAMERA_YAW;
                targetCamera.pitch = Constants.Vision.REAR_CAMERA_TILT;
                targetCamera.height = Constants.Vision.REAR_CAMERA_HEIGHT;
            }
        }

        // now that we have the camera and target, do math with it

        if (target != null)
        {
            targetCamera.position.translatePolarPosition(0, _drive.getHeading());

            Vector cameraFieldPosition =  _vision.getCameraFieldPosition(target.getFiducialId(), targetCamera.pitch + target.getPitch(), target.getSkew(), targetCamera.height);
            Vector fieldPosition = cameraFieldPosition.subtract(targetCamera.position);

            double fieldHeading = _vision.getCameraHeading(target.getFiducialId(), target.getYaw(), target.getSkew()) + targetCamera.yaw;

            _drive.fusePosition(fieldPosition, 12);
            _drive.fuseHeading(fieldHeading, 12);
        }
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
