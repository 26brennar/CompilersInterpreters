package ast;

import environment.Environment;

/**
 * Range class that represents the range in a FOR loop. It contains the name of the
 * variable that is incremented in the range and an Expression representing the ending
 * value. When eval() is called, it returns whether or not the variable is still in
 * the range (less than or equal to) the ending value. Note that the range is INCLUSIVE.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * Range r = new Range(<String name of variable>, <Expression>);
 * boolean value = r.eval(<Environment>);
 */

public class Range
{
    // String name of the variable that is incremented in the Range
    private String variable;

    // Expression that represents the ending value of the Range
    private Expression endVal;

    /**
     * Creates a new Range with the given variable name and ending Expression.
     * @param variable      String name of the variable that is incremented in the Range 
     * @param endVal        Expression that represents the ending value of the Range
     */
    public Range(String variable, Expression endVal)
    {
        this.variable = variable;
        this.endVal = endVal;
    }

    /**
     * Returns if the variable is still in the range (less than or equal to)
     * the ending Expression's value.
     * @param env       Environment that the Expressions should be evaluated in.
     * @return          True if the variable is less than or equal to the ending value;
     *                  False otherwise
     */
    public boolean eval(Environment env)
    {
        return env.getVariable(variable) <= endVal.eval(env);
    }

}
