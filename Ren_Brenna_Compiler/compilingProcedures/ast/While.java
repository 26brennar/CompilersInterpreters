package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * While class that is a subclass of the Statement class. It represents
 * a WHILE loop and contains a Condition and a Statement that should keep
 * executing while the Condition returns true.
 * @author  Brenna Ren
 * @version May 16, 2024
 * 
 * Usage:
 * While whileLoop = new While(<Condition>, <Statement>);
 * whileLoop.exec(<Environment>);
 * whileLoop.compile(<Emitter>);
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

    /**
     * Compiles the While statement by emitting the necessary lines of MIPS
     * Assembly Code. It begins by using the Emitter's method to retrieve a
     * new label number and creates a loop for the While statement. It
     * then compiles the condition and the statement and inserts a jump back
     * to the start of the loop. It also includes an endLoop label at the end
     * of the whileLoop.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the While into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        String labelNumber = "" + e.nextLabelID();
        e.emit("loop" + labelNumber + ":");
        cond.compile(e, "endLoop" + labelNumber);
        stmt.compile(e);
        e.emit("j loop" + labelNumber);
        e.emit("endLoop" + labelNumber + ":");
    }
}
