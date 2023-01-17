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

    private double  _deadband;
    private boolean _errorIsPositive;
    private double  _errorPrev;
    private double  _errorTotal;
    private double  _inputMax;
    private double  _inputMin;
    private double  _outputMax;
    private double  _outputMin;
    private double  _outputLowErrorMin;
    private double  _rampMin;
    private double  _rampNow;
    private double  _rampStep;
    private double  _setpoint;
    private boolean _useRamp;
    private boolean _continuous;

    private CoefficientValues _kP;
    private CoefficientValues _kI;
    private CoefficientValues _kD;

    private IFeedForward m_feedForward;

    public PIDControl()
    {
        _kP           = new CoefficientValues();
        _kP.threshold = 0;
        _kP.kAbove    = 0;
        _kP.kBelow    = 0;
        _kP.kNow      = 0;

        _kI           = new CoefficientValues();
        _kI.threshold = 0;
        _kI.kAbove    = 0;
        _kI.kBelow    = 0;
        _kI.kNow      = 0;

        _kD           = new CoefficientValues();
        _kD.threshold = 0;
        _kD.kAbove    = 0;
        _kD.kBelow    = 0;
        _kD.kNow      = 0;

        m_feedForward = null;

        _deadband          =  0.0;
        _errorIsPositive   =  false;
        _errorPrev         =  0.0;
        _errorTotal        =  0.0;
        _inputMax          =  0.0;
        _inputMin          =  0.0;
        _outputMax         =  1.0;
        _outputMin         = -1.0;
        _outputLowErrorMin =  0.0;
        _rampMin           =  0.0;
        _rampNow           =  0.0;
        _rampStep          =  0.0;
        _setpoint          =  0.0;
        _useRamp           =  false;

        _continuous =  false;
    }

    public boolean atSetpoint()
    {
        return Math.abs(_errorPrev) <= _deadband;
    }

    public double calculate(double input)
    {
        return calculate(input, false);
    }

    public double calculate(double input, boolean print)
    {
        double error = _setpoint - input;

        // apply input range
        if (_inputMax > _inputMin)
        {
            // continuous input based on wpilib pid implementation
            if (_continuous)
            {
                double errorBound = (_inputMax - _inputMin) / 2.0;
                // make the error the shortest direction from the setpoint
                error = wrapLimit(_setpoint - input, -errorBound, errorBound);
            }
            else
            {
                error = _setpoint - limit(input, _inputMin, _inputMax);
            }
        }

        double errorDiff = error - _errorPrev;

        if (_kP.threshold > 0.0)
        {
            _kP.kNow = getAppropriateCoefficient(error, _kP.threshold, _kP.kAbove, _kP.kBelow);
        }

        if (_kI.threshold > 0.0)
        {
            _kI.kNow = getAppropriateCoefficient(error, _kI.threshold, _kI.kAbove, _kI.kBelow);
        }

        if (_kD.threshold > 0.0)
        {
            _kD.kNow = getAppropriateCoefficient(error, _kD.threshold, _kD.kAbove, _kD.kBelow);
        }

        if (_kI.kNow == 0.0)
        {
            _errorTotal = 0.0;
        }

        else
        {
            _errorTotal = limit(_errorTotal + error, _outputMin / _kI.kNow, _outputMax / _kI.kNow);
        }

        double output = (_kP.kNow * error) +
                        (_kI.kNow * _errorTotal) +
                        (_kD.kNow * errorDiff);

        if (print)
        {
            System.out.println(String.format("P: %4.2f, I: %4.2f, D: %4.2f", _kP.kNow * error, _kI.kNow * _errorTotal, _kD.kNow * errorDiff));
        }

        if (m_feedForward != null)
        {
            output += m_feedForward.calculate(_setpoint);
        }

        if (_useRamp)
        {
            _rampNow = Math.min(_rampNow + _rampStep, 1.0);
            _useRamp = (_rampNow < output) && (_rampNow < 1.0);

            output = limit(output, -_rampNow, _rampNow);
        }

        if (output > _outputMax)
        {
            output = _outputMax;
        }

        else if (output < _outputMin)
        {
            output = _outputMin;
        }

        else
        {
            if (Math.abs(output) < _outputLowErrorMin)
            {
                output = _errorIsPositive ? _outputLowErrorMin : -_outputLowErrorMin;
            }
        }

        _errorPrev = error;

        return output;
    }

    public double getError()
    {
        return _errorPrev;
    }

    public void reset()
    {
        _errorTotal = 0.0;
        _rampNow = _rampMin;
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
                _kP.threshold = Math.abs(errorThreshold);
                _kP.kAbove    = Math.abs(kAbove);
                _kP.kBelow    = Math.abs(kBelow);
                _kP.kNow      = _kP.kAbove;
                break;

            case I:
                _kI.threshold = Math.abs(errorThreshold);
                _kI.kAbove    = Math.abs(kAbove);
                _kI.kBelow    = Math.abs(kBelow);
                _kI.kNow      = _kI.kAbove;
                break;

            case D:
                _kD.threshold = Math.abs(errorThreshold);
                _kD.kAbove    = Math.abs(kAbove);
                _kD.kBelow    = Math.abs(kBelow);
                _kD.kNow      = _kD.kAbove;
                break;

            default:;
        }
    }

    public void setInputRange(double inputMinimum, double inputMaximum)
    {
        _inputMin = inputMinimum;
        _inputMax = inputMaximum;
    }

    public void setOutputRange(double outputMinimum, double outputMaximum)
    {
        setOutputRange(outputMinimum, outputMaximum, 0.0);
    }

    public void setOutputRange(double outputMinimum, double outputMaximum, double lowErrorMinimum)
    {
        _outputMin = outputMinimum;
        _outputMax = outputMaximum;
        _outputLowErrorMin = Math.abs(lowErrorMinimum);
    }

    public void setOutputRamp(double rampMinimum, double rampStep)
    {
        _rampMin  = Math.abs(rampMinimum);
        _rampStep = Math.abs(rampStep);
    }

    public void setSetpoint(double setpoint, double inputNow)
    {
        setSetpoint(setpoint, inputNow, true);
    }

    public void setSetpoint(double setpoint, double inputNow, boolean resetPID)
    {
        if (_inputMax > _inputMin)
        {
            if (_continuous)
            {
                setpoint = wrapLimit(setpoint, _inputMin, _inputMax);
            }
            else
            {
                setpoint = limit(setpoint, _inputMin, _inputMax);
            }
        }

        _setpoint = setpoint;
        _errorPrev = _setpoint - inputNow;
        _errorIsPositive = _errorPrev >= 0.0;

        if (resetPID)
        {
            _errorTotal = 0.0;
            _rampNow = _rampMin;
            _useRamp = _rampMin > 0.0;
        }
    }

    public void setSetpointDeadband(double deadband)
    {
        _deadband = Math.abs(deadband);
    }

    public void setContinuous(boolean continuous)
    {
        _continuous = continuous;
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

    //  based on edu.wpi.first.math.MathUtil.inputModulus
    private double wrapLimit(double number, double minimum, double maximum)
    {
        double modulus = maximum - minimum;

        int numMax = (int) ((number - minimum) / modulus);
        number -= numMax * modulus;

        int numMin = (int) ((number - maximum) / modulus);
        number -= numMin * modulus;

        return number;
    }
}