package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LED;

public class CmdLEDAutonomous extends CommandBase 
{
    public CmdLEDAutonomous() 
    {
        addRequirements(LED.getInstance());
    }

    @Override
    public void execute() 
    {
        Color c;
        AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(Constants.LED.NUM_LEDS);

        switch (DriverStation.getAlliance())
        {
            case Red:
                c = Constants.LED.RED;
                break;
            case Blue:
                c = Constants.LED.BLUE;
                break;
            default:
                c = Constants.LED.GREEN;
                break;
        }

        for (int i = 0; i < ledBuffer.getLength(); i++)
        {
            ledBuffer.setLED(i, c);
        }

        LED.getInstance().setLEDs(ledBuffer);
    }
    
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
