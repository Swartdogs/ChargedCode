package frc.robot.subsystems;

import java.util.EnumSet;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DashboardButton
{
    private GenericEntry _entry;
    public DashboardButton(GenericEntry entry)
    {
        _entry = entry;
    }

    public void whenPressed(Runnable callback)
    {
        NetworkTableInstance.getDefault().addListener(_entry, EnumSet.of(NetworkTableEvent.Kind.kValueRemote), event->
        {
            boolean value = _entry.getBoolean(false);
        
            if (value)
            {
                _entry.setBoolean(false);
                callback.run();
            }
        });
    }
}

