package frc.robot.groups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.autonomous.Actions;

public class GrpAutonomous extends SequentialCommandGroup
{
    public GrpAutonomous()
    {
        super
        (
            Actions.getInstance().INITIALIZE,
            Actions.getInstance().START_DELAY,
            Actions.getInstance().PLACE_FIRST_GAME_PIECE,
            Actions.getInstance().GET_AND_PLACE_SECOND_GAME_PIECE,
            Actions.getInstance().BALANCE_OR_MOBILITY
        );
    }
}
