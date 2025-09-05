package ast;

import environment.Environment;

/**
 * For that is a subclass of the Statement class. It represents a FOR loop
 * and contains an Assignment, Range, variable name, and Statement to execute in
 * the loop. The For class mimics the execution of the FOR loop and continues to
 * execute the Statement until the Range returns false. It also instantiates the
 * variable in the Range inside the given Environment.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * For forLoop = new For(<Assignment>, <Range>, <String name of variable>, <Statement>);
 * forLoop.exec(<Environment>);
 */

public class For extends Statement
{
    // Assignment for the variable in the Range
    private Assignment assignment;

    // Range of the FOR loop
    private Range range;

    // String name of the variable
    private String variable;

    // Statement to execute in the FOR loop
    private Statement stmt;

    /**
     * Creates a new For class with the given Assignment, Range, variable name, and statement.
     * @param assignment    Assignment for the variable in the Range
     * @param cond          Range of the FOR loop
     * @param variable      String name of the variable
     * @param stmt          Statement to execute in the FOR loop
     */
    public For(Assignment assignment, Range cond, String variable, Statement stmt)
    {
        this.assignment = assignment;
        this.range = cond;
        this.variable = variable;
        this.stmt = stmt;
    }

    /**
     * Executes the Statement while the Range condition is met. It also assigns the
     * variable to its value (keeps incrementing it) in the given Environment. It calls
     * the exec() method on the Statement to execute it repeatedly.
     * @param env   Environment that the variable should be stored in and where the
     *              Statements should be evaluated in.
     */
    @Override
    public void exec(Environment env)
    {
        assignment.exec(env);
        while (range.eval(env))
        {
            stmt.exec(env);
            env.setVariable(variable, env.getVariable(variable) + 1);
        }
    }
}
