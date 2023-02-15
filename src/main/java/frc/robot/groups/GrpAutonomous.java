package frc.robot.groups;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.drive.Vector;

public class GrpAutonomous extends SequentialCommandGroup 
{
    // private enum AutoCoords
    // {
    //     GamePiece1 (new Vector (-47.36, -121.61), new Vector (47.36, -121.61)),
    //     GamePiece2 (new Vector (-47.36, -73.61), new Vector (47.36, -73.61)),
    //     GamePiece3 (new Vector (-47.36, -25.61), new Vector (47.36, -25.61)),
    //     GamePiece4 (new Vector (-47.36, 22.39), new Vector (47.36, 22.39)),
        
    //     ChargeStation1 (new Vector (-174.61, -73.61), new Vector (174.61, -73.61)),
    //     ChargeStation2 (new Vector (-174.61, -49.61), new Vector (174.61, -49.61)),
    //     ChargeStation3 (new Vector (-174.61, -25.61), new Vector (174.61, -25.61)),

    //     StartPosition1 (new Vector (-264.91, -115.61), new Vector (264.91, -115.61)),
    //     StartPosition2 (new Vector (-264.91, -49.61), new Vector (264.91, -49.61)),
    //     StartPosition3 (new Vector (-264.91, 16.39), new Vector (264.91, 16.39)),

    //     Grid1 (new Vector (-264.91, -137.61), new Vector (264.91, -137.61)),
    //     Grid2 (new Vector (-264.91, -115.61), new Vector (264.91, -115.61)),
    //     Grid3 (new Vector (-264.91, -93.61), new Vector (264.91, -93.61)),
    //     Grid4 (new Vector (-264.91, -71.61), new Vector (264.91, -71.61)),
    //     Grid5 (new Vector (-264.91, -49.61), new Vector (264.91, -49.61)),
    //     Grid6 (new Vector (-264.91, -27.61), new Vector (264.91, -27.61)),
    //     Grid7 (new Vector (-264.91, -5.61), new Vector (264.91, -5.61)),
    //     Grid8 (new Vector (-264.91, 16.39), new Vector (264.91, 16.39)),
    //     Grid9 (new Vector (-264.91, 38.39), new Vector (264.91, 38.39));
           //All of the y-values could be + 1.15

    //     private Vector _red;
    //     private Vector _blue;

    //     private AutoCoords(Vector blue, Vector red)
    //     {
    //         _red = red;
    //         _blue = blue;
    //     }

    //     public Vector get()
    //     {
    //         if (DriverStation.getAlliance() == Alliance.Blue)
    //         {
    //             return _blue;
    //         }
    //         else
    //         {
    //             return _red;
    //         }
            
    //     }
    // }

    public GrpAutonomous()
    {
        addCommands
        (
            Commands.waitSeconds(Dashboard.getInstance().getAutoDelay())

            /*
             * Determine Alliance
             * Determine If Holding Game Piece
             * If so
             * Place it
             * Command Version: For _ Times
             * Pick up Game Piece
             * Place it
             * Balance if Selected
             */

        );
    }
}