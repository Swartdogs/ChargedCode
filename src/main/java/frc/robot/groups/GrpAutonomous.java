package frc.robot.groups;

import java.util.Map;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ArmData;
import frc.robot.Constants;
import frc.robot.commands.CmdArmSetPosition;
import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDrivePath;
import frc.robot.commands.CmdDriveToPosition;
import frc.robot.paths.Trajectory;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public class GrpAutonomous extends SequentialCommandGroup
{
    public enum DrivePosition
    {
        // Start positions
        SubstationStart(new Vector()),
        MiddleStart(new Vector()),
        WallStart(new Vector()),

        // Substation-side charge station alignment positions
        SubstationInsideCommunityChargeStationAlign(new Vector(-15, -45)),
        SubstationInsideCommunityChargeStationDocked(new Vector(-78, -45)),

        SubstationOutsideCommunity(new Vector(-150, 5)),
        SubstationOutsideCommunityChargeStationAlign(new Vector(-150, -45)),
        SubstationOutsideCommunityChargeStationDocked(new Vector(-87, -45)),

        // Middle-side charge station alignment positions
        MiddleInsideCommunityChargeStationAlign(new Vector()),
        MiddleInsideCommunityChargeStationDocked(new Vector(-78, 0)),

        MiddleOutsideCommunity(new Vector()),
        MiddleOutsideCommunityChargeStationAlign(new Vector()),
        MiddleOutsideCommunityChargeStationDocked(new Vector()),

        // Wall-side charge station alignment positions
        WallInsideCommunityChargeStationAlign(new Vector(-15, 45)),
        WallInsideCommunityChargeStationDocked(new Vector(-78, 45)),

        WallOutsideCommunity(new Vector(-150, -5)),
        WallOutsideCommunityChargeStationAlign(new Vector(-150, 45)),
        WallOutsideCommunityChargeStationDocked(new Vector(-87, 45));


        private Vector _blueAlliancePosition;
        private Vector _redAlliancePosition;

        private DrivePosition(Vector position)
        {
            _redAlliancePosition = position;
            _blueAlliancePosition  = new Vector(-_redAlliancePosition.getX(), _redAlliancePosition.getY());
        }

        public Vector getPosition()
        {
            return DriverStation.getAlliance() == Alliance.Blue ? _blueAlliancePosition : _redAlliancePosition;
        }
    }

    private int           _delay         = 0;
    private int           _numGamePieces = 0;
    private boolean       _balance       = false;
    private DrivePosition _startPosition = DrivePosition.SubstationStart;

    public GrpAutonomous()
    {
        addCommands
        (
            // Get our settings
            Commands.runOnce(() -> 
            {
                _delay         = Dashboard.getInstance().getAutoDelay();
                _numGamePieces = Dashboard.getInstance().getAutoGamePieceCount();
                _balance       = Dashboard.getInstance().getAutoBalance();
                _startPosition = Dashboard.getInstance().getAutoStartPosition();

                Drive.getInstance().setPosition(_startPosition.getPosition());
                Drive.getInstance().setGyro(Drive.getInstance().getAllianceAngle());
            }),

            // Wait if necessary
            new ProxyCommand(() -> Commands.waitSeconds(_delay)),

            // Place the first game piece if necessary
            new SequentialCommandGroup(
                new GrpSetArmPosition(ArmPosition.High, () -> ArmSide.Front, () -> HandMode.Cube),
                new GrpPlaceGamePiece(() -> HandMode.Cube)
            ).unless(() -> _numGamePieces == 0),

            Commands.select
            (
                Map.of
                (
                    DrivePosition.SubstationStart, getSubstationSecondPieceCommand(),
                    DrivePosition.WallStart,       getWallSecondPieceCommand(),
                    DrivePosition.MiddleStart,     getMiddleSecondPieceCommand()
                ),
                () -> _startPosition
            ).unless(() -> _numGamePieces < 2),

            // Balance on the charge station OR get the mobility bonus
            Commands.either
            (
                // 
                new SelectCommand
                (
                    Map.of
                    (
                        DrivePosition.SubstationStart, getSubstationStartChargeStationAlignCommand(),
                        DrivePosition.WallStart,       getWallStartChargeStationAlignCommand(),
                        DrivePosition.MiddleStart,     getMiddleStartChargeStationAlignCommand()
                    ), 
                    () -> _startPosition
                )
                .andThen
                (
                    new CmdDriveBalance(),
                    Commands.run(() -> Drive.getInstance().rotateModules(90))
                ),
                new SelectCommand
                (
                    Map.of
                    (
                        DrivePosition.SubstationStart, new CmdDrivePath(new Trajectory("pathplanner/generatedCSV/Substation Mobility.csv")),
                        DrivePosition.WallStart,       new CmdDrivePath(new Trajectory("pathplanner/generatedCSV/Wall Mobility.csv")),
                        DrivePosition.MiddleStart,     Commands.none()
                    ),
                    () -> _startPosition
                ),
                () -> _balance
            )
        );
    }

    private Command getSubstationStartChargeStationAlignCommand()
    {
        return Commands.either
        (
            // We've placed more than a single game piece, so we already have mobility. Take the shortest route to the charge station
            new SequentialCommandGroup
            (
                new CmdDriveToPosition(DrivePosition.SubstationInsideCommunityChargeStationAlign.getPosition(),  Drive.getInstance().getAllianceAngle(), 0.2),
                new CmdDriveToPosition(DrivePosition.SubstationInsideCommunityChargeStationDocked.getPosition(), Drive.getInstance().getAllianceAngle(), 0.3)
            ),
            // We haven't left the community yet, so we need to go around the charge station to get mobility before we balance
            new CmdDrivePath(new Trajectory("pathplanner/generatedCSV/Substation Charging.csv")),
            () -> _numGamePieces > 1
        );
    }

    private Command getWallStartChargeStationAlignCommand()
    {
        return Commands.either
        (
            // We've placed more than a single game piece, so we already have mobility. Take the shortest route to the charge station
            new SequentialCommandGroup
            (
                new CmdDriveToPosition(DrivePosition.WallInsideCommunityChargeStationAlign.getPosition(),  Drive.getInstance().getAllianceAngle(), 0.2),
                new CmdDriveToPosition(DrivePosition.WallInsideCommunityChargeStationDocked.getPosition(), Drive.getInstance().getAllianceAngle(), 0.3)
            ),
            // We haven't left the community yet, so we need to go around the charge station to get mobility before we balance
            new CmdDrivePath(new Trajectory("pathplanner/generatedCSV/Wall Charging.csv")),
            () -> _numGamePieces > 1
        );
        
    }

    private Command getMiddleStartChargeStationAlignCommand()
    {
        // return Commands.either
        // (
        //     // We've placed more than a single game piece, so we already have mobility. Take the shortest route to the charge station
        //     new SequentialCommandGroup
        //     (
        //         new CmdDriveToPosition(DrivePosition.MiddleInsideCommunityChargeStationAlign.getPosition(),  Drive.getInstance().getAllianceAngle(), 0.2),
        //         new CmdDriveToPosition(DrivePosition.MiddleInsideCommunityChargeStationDocked.getPosition(), Drive.getInstance().getAllianceAngle(), 0.3)
        //     ),
        //     // We haven't left the community yet, so we need to go around the charge station to get mobility before we balance
        //     new SequentialCommandGroup
        //     (
        //         new CmdDriveToPosition(DrivePosition.MiddleOutsideCommunity.getPosition(),                    Drive.getInstance().getAllianceAngle(), 0.4),
        //         new CmdDriveToPosition(DrivePosition.MiddleOutsideCommunityChargeStationAlign.getPosition(),  Drive.getInstance().getAllianceAngle(), 0.2),
        //         new CmdDriveToPosition(DrivePosition.MiddleOutsideCommunityChargeStationDocked.getPosition(), Drive.getInstance().getAllianceAngle(), 0.3)
        //     ),
        //     () -> _numGamePieces > 1
        // );

        return new CmdDriveToPosition(DrivePosition.MiddleInsideCommunityChargeStationDocked.getPosition(), Drive.getInstance().getAllianceAngle(), 0.3);
    }

    public Command getSubstationSecondPieceCommand()
    {
        return new SequentialCommandGroup(
            new ParallelDeadlineGroup(
                new CmdDrivePath(new Trajectory("pathplanner/generatedCSV/Substation Pickup.csv"), true),
                new GrpIntakeGamePiece(ArmPosition.Ground, ()-> ArmSide.Front, ()-> HandMode.Cone)
            ),
            new ParallelCommandGroup(
                new CmdArmSetPosition(Constants.Lookups.STOW_FRONT_CONE, Constants.Arm.PRESET_MOTION_RATE, true),
                new CmdDrivePath(new Trajectory("pathplanner/generatedCSV/Substation Place.csv"))
            ),
            new CmdArmSetPosition(Constants.Lookups.MID_FRONT_CUBE, Constants.Arm.PRESET_MOTION_RATE, true),
            new GrpPlaceGamePiece(() -> Arm.HandMode.Cube)
        );
    }

    public Command getWallSecondPieceCommand()
    {
        return new SequentialCommandGroup(
            new ParallelDeadlineGroup(
                new CmdDrivePath(new Trajectory("pathplanner/generatedCSV/Wall Pickup.csv"), true),
                new GrpIntakeGamePiece(ArmPosition.Ground, ()-> ArmSide.Front, ()-> HandMode.Cone)
            ),
            new ParallelCommandGroup(
                new CmdArmSetPosition(Constants.Lookups.STOW_FRONT_CONE, Constants.Arm.PRESET_MOTION_RATE, true),
                new CmdDrivePath(new Trajectory("pathplanner/generatedCSV/Wall Place.csv"))
            ),
            new CmdArmSetPosition(Constants.Lookups.MID_FRONT_CUBE, Constants.Arm.PRESET_MOTION_RATE, true),
            new GrpPlaceGamePiece(() -> Arm.HandMode.Cube)
        );
    }

    public Command getMiddleSecondPieceCommand()
    {
        return new SequentialCommandGroup();
    }
}
