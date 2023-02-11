package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main
{
    private Main() {}

    public static void main(String... args)
    {
        RobotBase.startRobot(Robot::new);
    }
}
/*
 * Eject Game Piece             DONE
 * Intake Game Piece            DONE
 * Manual Modification          DONE
 * Create Manual Hand Flip      DONE
 * Make Hand Mode Switch Work   DONE
 * Test Arm Flip                DONE
 * 1 piece auto (& balance)     
 */
