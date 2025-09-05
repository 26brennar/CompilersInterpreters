package ast;

import environment.Environment;

/**
 * Writeln class that is a subclass of the Statement class. It prints the
 * value of the given Expression in the terminal.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * Writeln write = new Writeln(<Expression>);
 * write.exec(<Environment>);
 */

public class Writeln extends Statement
{
    // Expression to print the value of
    private Expression exp;

    /**
     * Creates a new Writeln with the given Expression.
     * @param exp       Expression to print the value of
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }
    
    /**
     * Evaluates the value of the Expression and prints it to the terminal.
     * @param env       Environment that the Expression should be evaluated in.
     * @postcondition   The value of the Expression is printed to the terminal.
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
}
