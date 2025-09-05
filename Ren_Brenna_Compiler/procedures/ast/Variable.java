package ast;

import environment.Environment;

/**
 * Variable class that is a subclass of the Expression class. It
 * contains the String name of the variable.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * Variable var = new Variable(<String name of variable>);
 * int value = var.eval(<Environment>);
 */

public class Variable extends Expression
{
    // String name of the variable
    private String name;

    /**
     * Creates a new Variable with the given String name.
     * @param name      String name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * Returns the integer value of the variable by retrieving
     * it from the given Environment.
     * @param env       Environment to retrieve the variable value from
     * @return          The integer value of the variable
     */
    @Override
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}
