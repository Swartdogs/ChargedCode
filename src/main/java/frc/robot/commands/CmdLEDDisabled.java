package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LED;

public class CmdLEDDisabled extends CommandBase 
{
    public CmdLEDDisabled() 
    {
        addRequirements(LED.getInstance());
    }

    @Override
    public void execute() 
    {
        var ledBuffer = new AddressableLEDBuffer(Constants.LED.NUM_LEDS);

        for (int i = 0; i < ledBuffer.getLength(); i++)
        {
            ledBuffer.setLED(i, Constants.LED.ORANGE);
        }

        LED.getInstance().setLEDs(ledBuffer);
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
