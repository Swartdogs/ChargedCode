package frc.robot.autonomous;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import frc.robot.Constants;
import frc.robot.commands.CmdArmSetPosition;
import frc.robot.commands.CmdDriveBalance;
import frc.robot.commands.CmdDrivePath;
import frc.robot.commands.CmdDriveToPosition;
import frc.robot.groups.GrpIntakeGamePiece;
import frc.robot.groups.GrpPlaceGamePiece;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Arm.ArmSide;
import frc.robot.subsystems.Arm.HandMode;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.Vector;

public final class Actions
{
    // Singleton setup
    private static Actions _instance;

    public static Actions getInstance()
    {
        if (_instance == null)
        {
            _instance = new Actions();
        }

        return _instance;
    }

    // DrivePosition enumeration
    public enum DrivePosition
    {
        // Start positions
        SubstationStart(new Vector(-250.30, 16.4732)),
        MiddleStart(new Vector()),
        WallStart(new Vector(-249.94, -115.98)),

        // Middle-side charge station alignment positions
        MiddleInsideCommunityChargeStationDocked(new Vector(78, 0));

        private Vector _blueAlliancePosition;
        private Vector _redAlliancePosition;

        private DrivePosition(Vector position)
        {
            _blueAlliancePosition = position;
            _redAlliancePosition  = new Vector(-_blueAlliancePosition.getX(), _blueAlliancePosition.getY());
        }
        public Vector getBlueAlliancePosition()
        {
            return _blueAlliancePosition;
        }

        public Vector getRedAlliancePosition()
        {
            return _redAlliancePosition;
        }
    }

    // Member variables
    private int           _delay         = 0;
    private int           _numGamePieces = 0;
    private boolean       _balance       = false;
    private DrivePosition _startPosition = DrivePosition.MiddleStart;

    private Actions() {}

    public void readSettingsFromDashboard()
    {
        _delay         = Dashboard.getInstance().getAutoDelay();
        _numGamePieces = Dashboard.getInstance().getAutoGamePieceCount();
        _balance       = Dashboard.getInstance().getAutoBalance();
        _startPosition = Dashboard.getInstance().getAutoStartPosition();
    }

    // Constants
    private final double BLUE_ALLIANCE_ANGLE =  90.0;
    private final double RED_ALLIANCE_ANGLE  = -90.0;

    // Conditions
    private final BooleanSupplier IS_BLUE_ALLIANCE      = () -> DriverStation.getAlliance() == Alliance.Blue;
    private final BooleanSupplier NO_GAME_PIECES        = () -> _numGamePieces == 0;
    private final BooleanSupplier NO_SECOND_GAME_PIECE  = () -> _numGamePieces < 2;
    private final BooleanSupplier BALANCE               = () -> _balance && NO_SECOND_GAME_PIECE.getAsBoolean();

    // Trajectories
    private final Trajectory BLUE_SUBSTATION_MOBILITY_TRAJECTORY    = new Trajectory("pathplanner/generatedCSV/Substation Mobility.csv");
    private final Trajectory BLUE_SUBSTATION_CHARGING_TRAJECTORY    = new Trajectory("pathplanner/generatedCSV/Substation Charging.csv");
    private final Trajectory BLUE_SUBSTATION_PICKUP_TRAJECTORY      = new Trajectory("pathplanner/generatedCSV/Substation Pickup.csv");
    private final Trajectory BLUE_SUBSTATION_PLACE_TRAJECTORY       = new Trajectory("pathplanner/generatedCSV/Substation Place.csv");
    private final Trajectory BLUE_WALL_MOBILITY_TRAJECTORY          = new Trajectory("pathplanner/generatedCSV/Wall Mobility.csv");
    private final Trajectory BLUE_WALL_CHARGING_TRAJECTORY          = new Trajectory("pathplanner/generatedCSV/Wall Charging.csv");
    private final Trajectory BLUE_WALL_PICKUP_TRAJECTORY            = new Trajectory("pathplanner/generatedCSV/Wall Pickup.csv");
    private final Trajectory BLUE_WALL_PLACE_TRAJECTORY             = new Trajectory("pathplanner/generatedCSV/Wall Place.csv");

    private final Trajectory RED_SUBSTATION_MOBILITY_TRAJECTORY     = BLUE_SUBSTATION_MOBILITY_TRAJECTORY.mirror();
    private final Trajectory RED_SUBSTATION_CHARGING_TRAJECTORY     = BLUE_SUBSTATION_CHARGING_TRAJECTORY.mirror();
    private final Trajectory RED_SUBSTATION_PICKUP_TRAJECTORY       = BLUE_SUBSTATION_PICKUP_TRAJECTORY.mirror();
    private final Trajectory RED_SUBSTATION_PLACE_TRAJECTORY        = BLUE_SUBSTATION_PLACE_TRAJECTORY.mirror();
    private final Trajectory RED_WALL_MOBILITY_TRAJECTORY           = BLUE_WALL_MOBILITY_TRAJECTORY.mirror();
    private final Trajectory RED_WALL_CHARGING_TRAJECTORY           = BLUE_WALL_CHARGING_TRAJECTORY.mirror();
    private final Trajectory RED_WALL_PICKUP_TRAJECTORY             = BLUE_WALL_PICKUP_TRAJECTORY.mirror();
    private final Trajectory RED_WALL_PLACE_TRAJECTORY              = BLUE_WALL_PLACE_TRAJECTORY.mirror();

    // Blue alliance-specific commands
    public final Command BLUE_SUBSTATION_MOBILITY    = new CmdDrivePath(BLUE_SUBSTATION_MOBILITY_TRAJECTORY);
    public final Command BLUE_SUBSTATION_CHARGING    = new CmdDrivePath(BLUE_SUBSTATION_CHARGING_TRAJECTORY);
    public final Command BLUE_SUBSTATION_PICKUP      = new CmdDrivePath(BLUE_SUBSTATION_PICKUP_TRAJECTORY);
    public final Command BLUE_SUBSTATION_PLACE       = new CmdDrivePath(BLUE_SUBSTATION_PLACE_TRAJECTORY);
    public final Command BLUE_WALL_MOBILITY          = new CmdDrivePath(BLUE_WALL_MOBILITY_TRAJECTORY);
    public final Command BLUE_WALL_CHARGING          = new CmdDrivePath(BLUE_WALL_CHARGING_TRAJECTORY);
    public final Command BLUE_WALL_PICKUP            = new CmdDrivePath(BLUE_WALL_PICKUP_TRAJECTORY);
    public final Command BLUE_WALL_PLACE             = new CmdDrivePath(BLUE_WALL_PLACE_TRAJECTORY);
    public final Command BLUE_MIDDLE_CHARGING        = new CmdDriveToPosition(DrivePosition.MiddleInsideCommunityChargeStationDocked.getBlueAlliancePosition(), BLUE_ALLIANCE_ANGLE, 0.3);

    // Red alliance-specific commands
    public final Command RED_SUBSTATION_MOBILITY     = new CmdDrivePath(RED_SUBSTATION_MOBILITY_TRAJECTORY);
    public final Command RED_SUBSTATION_CHARGING     = new CmdDrivePath(RED_SUBSTATION_CHARGING_TRAJECTORY);
    public final Command RED_SUBSTATION_PICKUP       = new CmdDrivePath(RED_SUBSTATION_PICKUP_TRAJECTORY);
    public final Command RED_SUBSTATION_PLACE        = new CmdDrivePath(RED_SUBSTATION_PLACE_TRAJECTORY);
    public final Command RED_WALL_MOBILITY           = new CmdDrivePath(RED_WALL_MOBILITY_TRAJECTORY);
    public final Command RED_WALL_CHARGING           = new CmdDrivePath(RED_WALL_CHARGING_TRAJECTORY);
    public final Command RED_WALL_PICKUP             = new CmdDrivePath(RED_WALL_PICKUP_TRAJECTORY);
    public final Command RED_WALL_PLACE              = new CmdDrivePath(RED_WALL_PLACE_TRAJECTORY);
    public final Command RED_MIDDLE_CHARGING         = new CmdDriveToPosition(DrivePosition.MiddleInsideCommunityChargeStationDocked.getRedAlliancePosition(),  RED_ALLIANCE_ANGLE,  0.3);

    // Movement commands
    public final Command DRIVE_SUBSTATION_MOBILITY   = Commands.either(BLUE_SUBSTATION_MOBILITY, RED_SUBSTATION_MOBILITY, IS_BLUE_ALLIANCE);
    public final Command DRIVE_SUBSTATION_CHARGING   = Commands.either(BLUE_SUBSTATION_CHARGING, RED_SUBSTATION_CHARGING, IS_BLUE_ALLIANCE);
    public final Command DRIVE_SUBSTATION_PICKUP     = Commands.either(BLUE_SUBSTATION_PICKUP,   RED_SUBSTATION_PICKUP,   IS_BLUE_ALLIANCE);
    public final Command DRIVE_SUBSTATION_PLACE      = Commands.either(BLUE_SUBSTATION_PLACE,    RED_SUBSTATION_PLACE,    IS_BLUE_ALLIANCE);
    public final Command DRIVE_WALL_MOBILITY         = Commands.either(BLUE_WALL_MOBILITY,       RED_WALL_MOBILITY,       IS_BLUE_ALLIANCE);
    public final Command DRIVE_WALL_CHARGING         = Commands.either(BLUE_WALL_CHARGING,       RED_WALL_CHARGING,       IS_BLUE_ALLIANCE);
    public final Command DRIVE_WALL_PICKUP           = Commands.either(BLUE_WALL_PICKUP,         RED_WALL_PICKUP,         IS_BLUE_ALLIANCE);
    public final Command DRIVE_WALL_PLACE            = Commands.either(BLUE_WALL_PLACE,          RED_WALL_PLACE,          IS_BLUE_ALLIANCE);
    public final Command DRIVE_MIDDLE_CHARGING       = Commands.either(BLUE_MIDDLE_CHARGING,     RED_MIDDLE_CHARGING,     IS_BLUE_ALLIANCE);
    public final Command DRIVE_MIDDLE_MOBILITY       = Commands.none();

    // Arm movement commands
    public final Supplier<Command> PLACE_CUBE = () -> new GrpPlaceGamePiece(() -> HandMode.Cube);

    public final Supplier<Command> PLACE_CUBE_MID = () -> Commands.sequence
    (
        new CmdArmSetPosition(Constants.Lookups.MID_FRONT_CUBE, Constants.Arm.PRESET_MOTION_RATE, true),
        PLACE_CUBE.get()
    );

    public final Supplier<Command> PLACE_CUBE_HIGH = () -> Commands.sequence
    (
        new CmdArmSetPosition(Constants.Lookups.HIGH_FRONT_CUBE, Constants.Arm.PRESET_MOTION_RATE, true),
        PLACE_CUBE.get()
    );

    public final Supplier<Command> PICKUP_CUBE = () -> new GrpIntakeGamePiece(ArmPosition.Ground, () -> ArmSide.Front, () -> HandMode.Cone); // Cone is intentional to pick up the cube from the sides
    public final Supplier<Command> STOW        = () -> new CmdArmSetPosition(Constants.Lookups.STOW_FRONT_CONE, Constants.Arm.PRESET_MOTION_RATE, true);

    // Second game piece commands
    public final Command SUBSTATION_SECOND_PIECE_PICKUP = Commands.deadline
    (
        DRIVE_SUBSTATION_PICKUP,
        PICKUP_CUBE.get()
    );

    public final Command SUBSTATION_SECOND_PIECE_DRIVE_TO_PLACE = Commands.parallel
    (
        DRIVE_SUBSTATION_PLACE,
        STOW.get()
    );
    
    public final Command SUBSTATION_SECOND_PIECE = Commands.sequence
    (
        SUBSTATION_SECOND_PIECE_PICKUP,
        SUBSTATION_SECOND_PIECE_DRIVE_TO_PLACE,
        PLACE_CUBE_MID.get()
    );

    public final Command WALL_SECOND_PIECE_PICKUP = Commands.deadline
    (
        DRIVE_WALL_PICKUP,
        PICKUP_CUBE.get()
    );

    public final Command WALL_SECOND_PIECE_DRIVE_TO_PLACE = Commands.parallel
    (
        DRIVE_WALL_PLACE,
        STOW.get()
    );

    public final Command WALL_SECOND_PIECE = Commands.sequence
    (
        WALL_SECOND_PIECE_PICKUP,
        WALL_SECOND_PIECE_DRIVE_TO_PLACE,
        PLACE_CUBE_MID.get()
    );

    public final Command MIDDLE_SECOND_PIECE = Commands.none(); // We can't place a second piece from the middle

    // Mobility commands
    public final Command MOBILITY = Commands.select
    (
        Map.of
        (
            DrivePosition.SubstationStart, DRIVE_SUBSTATION_MOBILITY,
            DrivePosition.WallStart,       DRIVE_WALL_MOBILITY,
            DrivePosition.MiddleStart,     DRIVE_MIDDLE_MOBILITY
        ),
        () -> _startPosition
    );

    // Balancing commands
    public final Command DRIVE_TO_BALANCE = Commands.select
    (
        Map.of
        (
            DrivePosition.SubstationStart, DRIVE_SUBSTATION_CHARGING,
            DrivePosition.WallStart,       DRIVE_WALL_CHARGING,
            DrivePosition.MiddleStart,     DRIVE_MIDDLE_CHARGING
        ),
        () -> _startPosition
    );

    public final Command BALANCE_ROBOT = Commands.sequence
    (
        new CmdDriveBalance(),
        Commands.run(() -> Drive.getInstance().rotateModules(90))
    );

    public final Command BALANCE_SEQUENCE = Commands.sequence
    (
        DRIVE_TO_BALANCE,
        BALANCE_ROBOT
    );

    /* BELOW THIS POINT, COMMANDS ARE LISTED IN THE ORDER WE INTEND TO EXECUTE THEM */

    // Initialization command
    public final Command INITIALIZE = Commands.runOnce(() ->
    {
        readSettingsFromDashboard();

        if (IS_BLUE_ALLIANCE.getAsBoolean())
        {
            Drive.getInstance().setPosition(_startPosition.getBlueAlliancePosition());
            Drive.getInstance().setGyro(BLUE_ALLIANCE_ANGLE);
        }
        else
        {
            Drive.getInstance().setPosition(_startPosition.getRedAlliancePosition());
            Drive.getInstance().setGyro(RED_ALLIANCE_ANGLE);
        }
    });

    // Delay command
    public final Command START_DELAY = new ProxyCommand(() -> Commands.waitSeconds(_delay));

    // Place first game piece command
    public final Command PLACE_FIRST_GAME_PIECE = PLACE_CUBE_HIGH.get().unless(NO_GAME_PIECES);

    // Get and place second game piece command
    public final Command GET_AND_PLACE_SECOND_GAME_PIECE = Commands.select
    (
        Map.of
        (
            DrivePosition.SubstationStart, SUBSTATION_SECOND_PIECE,
            DrivePosition.WallStart,       WALL_SECOND_PIECE,
            DrivePosition.MiddleStart,     MIDDLE_SECOND_PIECE
        ),
        () -> _startPosition
    ).unless(NO_SECOND_GAME_PIECE);

    // Either balance or drive out of the community (mobility bonus or positioning for teleop)
    public final Command BALANCE_OR_MOBILITY = Commands.either
    (
        BALANCE_SEQUENCE,
        MOBILITY,
        BALANCE
    );
}
