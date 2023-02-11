package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Dashboard;

public class GrpAutonomous extends SequentialCommandGroup 
{
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