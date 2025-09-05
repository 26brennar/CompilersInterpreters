package ast;

import environment.Environment;

/**
 * Number class that is a subclass of the Expression class. It represents a number
 * and contains an integer instance variable. When it is evaluated, it returns this value.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * Number num = new Number(1);
 * int value = num.eval(<Environment>);
 */

public class Number extends Expression
{
    // Integer representing the value of the number
    private int value;

    /**
     * Creates a new Number with the given integer value
     * @param value     Integer representing the value of the number
     */
    public Number(int value)
    {
        this.value = value;
    }

    /**
     * Returns the integer value of the Number.
     * @param env       Environment that the Expression should be evaluated in.
     * @return          The integer value of the Number.
     */
    @Override
    public int eval(Environment env)
    {
        return value;
    }
}
