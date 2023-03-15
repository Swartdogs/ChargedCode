package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.RobotContainer;

public class CmdDriveVelocity extends DriveCommand
{
    @Override
    public void execute()
    {
        double throttle = -RobotContainer.Controller.DriveJoystick.joystick().getRawAxis(3);
        double velocity = throttle * Constants.Drive.MAX_ROTATE_SPEED;

        _rotateVelocityPID.setSetpoint(velocity, false);
        double output = _rotateVelocityPID.calculate(_drive.getHeadingVelocity(), true);
        System.out.println(String.format("Setpoint: %6.2f, Current: %6.2f, Output: %6.2f", velocity, _drive.getHeadingVelocity(), output));
        _drive.chassisDrive(0, 0, output);
    }
}
