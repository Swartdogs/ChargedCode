package frc.robot.subsystems;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.GameMode;

public class RobotLog extends SubsystemBase
{
    private static RobotLog _instance;

    public static RobotLog getInstance()
    {
        if(_instance == null) 
        {
            _instance = new RobotLog(String.format("/home/lvuser/Log_%d.txt", Calendar.getInstance().getTime().getTime()));
        }

        return _instance;
    }

    private static final String TIME_LINE           = "-".repeat(Constants.RobotLog.NUM_DIGITS_IN_TIME + 2);

    private static final String TIME_BOUNDARY       = "+" + TIME_LINE + "+" + TIME_LINE;
    private static final String EMPTY_TIME_BOUNDARY = " ".repeat(2 * TIME_LINE.length() + 2);
    private static final String COL_HEADERS         = "| Game" + " ".repeat(Constants.RobotLog.NUM_DIGITS_IN_TIME - 3) + "| Mode" + " ".repeat(Constants.RobotLog.NUM_DIGITS_IN_TIME - 3);

    private static final String HEAD_BOUNDARY       = "+" + "-".repeat(Constants.RobotLog.HEADING_WIDTH - 2) + "+";

    private int         _robotTime;
    private int         _modeTime;

    private PrintWriter _writer;

    private boolean     _fileOpen;

    private String      _filePath;

    public RobotLog(String filePath)
    {
        _robotTime = 0;
        _modeTime  = 0;

        _fileOpen  = false;

        _filePath  = filePath;
    }

    @Override
    public void periodic()
    {
        _robotTime++;
        _modeTime++;
    }

    public void open()
    {
        if (!_fileOpen)
        {
            try
            {
                Path file = Path.of(_filePath);

                if (!Files.exists(file))
                {
                    Files.createFile(file);
                }

                _writer   = new PrintWriter(new FileWriter(_filePath, true));
                
                _fileOpen = true;
            }
            catch (IOException e)
            {
                System.out.println("Error opening robot log!");
            }
        }
    }

    public void save()
    {
        if (_fileOpen)
        {
            _writer.flush();
        }
    }

    public void close()
    {
        if (_fileOpen)
        {
            _writer.close();

            _fileOpen = false;
        }
    }

    public void log(String message)
    {
        if (!_fileOpen)
        {
            open();
        }

        if (_fileOpen)
        {
            _writer.println(String.format("| %s | %s | %s", toSeconds(_robotTime), toSeconds(_modeTime), message));
        }
    }

    public void setGameMode(GameMode mode)
    {
        _modeTime = 0;

        printHeading("Game Mode: " + mode.toString(), true);
    }

    public void printHeading(String heading)
    {
        printHeading(heading, false);
    }

    public void printHeading(String heading, boolean useColumnHeaders)
    {
        if (!_fileOpen)
        {
            open();
        }
        
        if (_fileOpen)
        {
            int len         = heading.length();
            int frontSpaces = (Constants.RobotLog.HEADING_WIDTH - 2 - len) / 2;
            int backSpaces  = frontSpaces;

            if (len % 2 == 1)
            {
                backSpaces++;
            }

            _writer.println(String.format("%s%s", useColumnHeaders ? TIME_BOUNDARY : EMPTY_TIME_BOUNDARY, HEAD_BOUNDARY));
            _writer.println(String.format("%s%s", useColumnHeaders ? COL_HEADERS   : EMPTY_TIME_BOUNDARY, "|" + " ".repeat(frontSpaces) + heading + " ".repeat(backSpaces) + "|"));
            _writer.println(String.format("%s%s", useColumnHeaders ? TIME_BOUNDARY : EMPTY_TIME_BOUNDARY, HEAD_BOUNDARY));
        }
    }

    private String toSeconds(int counter)
    {
        return String.format("%" + Constants.RobotLog.NUM_DIGITS_IN_TIME + "." + Constants.RobotLog.NUM_DECIMAL_PLACES_IN_TIME + "f", counter / Constants.LOOPS_PER_SECOND);
    }
}
