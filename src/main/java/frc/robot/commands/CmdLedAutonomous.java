package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Led;

public class CmdLedAutonomous extends CommandBase 
{
    public CmdLedAutonomous() 
    {
        addRequirements(Led.getInstance());
    }

    @Override
    public void execute() 
    {
        Color c;
        AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(Constants.Led.NUM_LEDS);

        switch (DriverStation.getAlliance())
        {
            case Red:
                c = Constants.Led.RED;
                break;
            case Blue:
                c = Constants.Led.BLUE;
                break;
            default:
                c = Constants.Led.GREEN;
                break;
        }

        for (int i = 0; i < ledBuffer.getLength(); i++)
        {
            ledBuffer.setLED(i, c);
        }

        Led.getInstance().setLEDs(ledBuffer);
    }
    
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
