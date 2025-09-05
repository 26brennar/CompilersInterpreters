package ast;

import environment.Environment;

/**
 * While class that is a subclass of the Statement class. It represents
 * a WHILE loop and contains a Condition and a Statement that should keep
 * executing while the Condition returns true.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * While whileLoop = new While(<Condition>, <Statement>);
 * whileLoop.exec(<Environment>);
 */

public class While extends Statement
{
    // Condition of the WHILE loop that must be true for the Statement to be executed
    private Condition cond;

    // Statement that will be executed while the Condition returns true
    private Statement stmt;

    /**
     * Creates a new While with the given Condition and Statement
     * @param cond      Condition of the WHILE loop
     * @param stmt      Statement that will be executed while the Condition returns true
     */
    public While(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * Executes the Statement while the Condition evaluates to true. It
     * repeatedly calls the exec() method on the Statement.
     * @param env       Environment that the Condition and Statement should be
     *                  evaluated and executed in.
     * @postcondition   The Statement is executed as many times as the Condition
     *                  is evaluated to true.
     */
    @Override
    public void exec(Environment env)
    {
        while (cond.eval(env))
        {
            stmt.exec(env);
        }
    }
}
