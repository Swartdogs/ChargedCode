package PIDControl;

public class PIDControl
{
    public enum Coefficient
    {
        P,
        I,
        D
    }

    private class CoefficientValues
    {
        public double threshold;
        public double kAbove;
        public double kBelow;
        public double kNow;
    }

    private double  m_deadband;
    private boolean m_errorIsPositive;
    private double  m_errorPrev;
    private double  m_errorTotal;
    private double  m_inputMax;
    private double  m_inputMin;
    private double  m_outputMax;
    private double  m_outputMin;
    private double  m_outputLowErrorMin;
    private double  m_rampMin;
    private double  m_rampNow;
    private double  m_rampStep;
    private double  m_setpoint;
    private boolean m_useRamp;

    private CoefficientValues m_P;
    private CoefficientValues m_I;
    private CoefficientValues m_D;

    private IFeedForward m_feedForward;

    public PIDControl()
    {
        m_P           = new CoefficientValues();
        m_P.threshold = 0;
        m_P.kAbove    = 0;
        m_P.kBelow    = 0;
        m_P.kNow      = 0;

        m_I           = new CoefficientValues();
        m_I.threshold = 0;
        m_I.kAbove    = 0;
        m_I.kBelow    = 0;
        m_I.kNow      = 0;

        m_D           = new CoefficientValues();
        m_D.threshold = 0;
        m_D.kAbove    = 0;
        m_D.kBelow    = 0;
        m_D.kNow      = 0;

        m_feedForward = null;

        m_deadband          =  0.0;
        m_errorIsPositive   =  false;
        m_errorPrev         =  0.0;
        m_errorTotal        =  0.0;
        m_inputMax          =  0.0;
        m_inputMin          =  0.0;
        m_outputMax         =  1.0;
        m_outputMin         = -1.0;
        m_outputLowErrorMin =  0.0;
        m_rampMin           =  0.0;
        m_rampNow           =  0.0;
        m_rampStep          =  0.0;
        m_setpoint          =  0.0;
        m_useRamp           =  false;
    }

    public boolean atSetpoint()
    {
        return Math.abs(m_errorPrev) <= m_deadband;
    }

    public double calculate(double input)
    {
        return calculate(input, false);
    }

    public double calculate(double input, boolean print)
    {
        double error = m_setpoint - input;
        double errorDiff = error - m_errorPrev;

        if (m_P.threshold > 0.0)
        {
            m_P.kNow = getAppropriateCoefficient(error, m_P.threshold, m_P.kAbove, m_P.kBelow);
        }

        if (m_I.threshold > 0.0)
        {
            m_I.kNow = getAppropriateCoefficient(error, m_I.threshold, m_I.kAbove, m_I.kBelow);
        }

        if (m_D.threshold > 0.0)
        {
            m_D.kNow = getAppropriateCoefficient(error, m_D.threshold, m_D.kAbove, m_D.kBelow);
        }

        if (m_I.kNow == 0.0)
        {
            m_errorTotal = 0.0;
        }

        else
        {
            m_errorTotal = limit(m_errorTotal + error, m_outputMin / m_I.kNow, m_outputMax / m_I.kNow);
        }

        double output = (m_P.kNow * error) +
                        (m_I.kNow * m_errorTotal) +
                        (m_D.kNow * errorDiff);

        if (print)
        {
            System.out.println(String.format("P: %4.2f, I: %4.2f, D: %4.2f", m_P.kNow * error, m_I.kNow * m_errorTotal, m_D.kNow * errorDiff));
        }

        if (m_feedForward != null)
        {
            output += m_feedForward.calculateFeedForward();
        }

        if (m_useRamp)
        {
            m_rampNow = Math.min(m_rampNow + m_rampStep, 1.0);
            m_useRamp = (m_rampNow < output) && (m_rampNow < 1.0);

            output = limit(output, 0.0, m_rampNow);
        }

        if (output > m_outputMax)
        {
            output = m_outputMax;
        }

        else if (output < m_outputMin)
        {
            output = m_outputMin;
        }

        else
        {
            if (Math.abs(output) < m_outputLowErrorMin)
            {
                output = m_errorIsPositive ? m_outputLowErrorMin : -m_outputLowErrorMin;
            }
        }

        m_errorPrev = error;

        return output;
    }

    public double getError()
    {
        return m_errorPrev;
    }

    public void reset()
    {
        m_errorTotal = 0.0;
        m_rampNow = m_rampMin;
    }

    public void setFeedForward(IFeedForward feedForward)
    {
        m_feedForward = feedForward;
    }

    public void setCoefficient(Coefficient kWhich, double errorThreshold, double kAbove, double kBelow)
    {
        switch(kWhich)
        {
            case P:
                m_P.threshold = Math.abs(errorThreshold);
                m_P.kAbove    = Math.abs(kAbove);
                m_P.kBelow    = Math.abs(kBelow);
                m_P.kNow      = m_P.kAbove;
                break;

            case I:
                m_I.threshold = Math.abs(errorThreshold);
                m_I.kAbove    = Math.abs(kAbove);
                m_I.kBelow    = Math.abs(kBelow);
                m_I.kNow      = m_I.kAbove;
                break;

            case D:
                m_D.threshold = Math.abs(errorThreshold);
                m_D.kAbove    = Math.abs(kAbove);
                m_D.kBelow    = Math.abs(kBelow);
                m_D.kNow      = m_D.kAbove;
                break;

            default:;
        }
    }

    public void setInputRange(double inputMinimum, double inputMaximum)
    {
        m_inputMin = inputMinimum;
        m_inputMax = inputMaximum;
    }

    public void setOutputRange(double outputMinimum, double outputMaximum)
    {
        setOutputRange(outputMinimum, outputMaximum, 0.0);
    }

    public void setOutputRange(double outputMinimum, double outputMaximum, double lowErrorMinimum)
    {
        m_outputMin = limit(outputMinimum, -1.0, 1.0);
        m_outputMax = limit(outputMaximum, -1.0, 1.0);
        m_outputLowErrorMin = Math.abs(lowErrorMinimum);
    }

    public void setOutputRamp(double rampMinimum, double rampStep)
    {
        m_rampMin  = Math.abs(rampMinimum);
        m_rampStep = Math.abs(rampStep);
    }

    public void setSetpoint(double setpoint, double inputNow)
    {
        setSetpoint(setpoint, inputNow, true);
    }

    public void setSetpoint(double setpoint, double inputNow, boolean resetPID)
    {
        if (m_inputMax > m_inputMin)
        {
            setpoint = limit(setpoint, m_inputMin, m_inputMax);
        }

        m_setpoint = setpoint;
        m_errorPrev = m_setpoint - inputNow;
        m_errorIsPositive = m_errorPrev >= 0.0;

        if (resetPID)
        {
            m_errorTotal = 0.0;
            m_rampNow = m_rampMin;
            m_useRamp = m_rampMin > 0.0;
        }
    }

    public void setSetpointDeadband(double deadband)
    {
        m_deadband = Math.abs(deadband);
    }

    private double getAppropriateCoefficient(double error, double threshold, double kAbove, double kBelow)
    {
        double kNow = 0.0;

        if (Math.abs(error) < threshold)
        {
            kNow = kBelow;
        }

        else
        {
            kNow = kAbove;
        }

        return kNow;
    }

    private double limit(double number, double minimum, double maximum)
    {
        return Math.min(Math.max(number, minimum), maximum);
    }
}