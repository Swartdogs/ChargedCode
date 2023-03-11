package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Led;

public class CmdLedDisabled extends CommandBase 
{
    public CmdLedDisabled() 
    {
        addRequirements(Led.getInstance());
    }

    @Override
    public void execute() 
    {
        var ledBuffer = new AddressableLEDBuffer(Constants.Led.NUM_LEDS);

        for (int i = 0; i < ledBuffer.getLength(); i++)
        {
            ledBuffer.setLED(i, Constants.Led.ORANGE);
        }

        Led.getInstance().setLEDs(ledBuffer);
    }

    @Override
    public boolean runsWhenDisabled()
    {
        return true;
    }

    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
