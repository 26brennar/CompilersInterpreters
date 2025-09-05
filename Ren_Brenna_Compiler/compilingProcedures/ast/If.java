package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * If class that is a subclass of the Statement class. It represents an IF statement
 * and contains its respective Condition that must be fulfilled for the first statement
 * to be executed. The second statement (if any) is executed as an ELSE, or when the
 * condition returns false. The compiler does not handle ELSE statements.
 * @author  Brenna Ren
 * @version May 16, 2024
 * 
 * Usage:
 * If ifStatement = new If(<Condition>, <Statement 1>, <Statement 2>);
 * ifStatement.exec(<Environment>);
 * ifStatement.compile(<Emitter>);
 */
public class If extends Statement 
{
    // Condition of the IF-THEN-ELSE statement
    private Condition cond;

    // First Statement that should be executed when the Condition returns true
    private Statement stmt1;

    // Second Statement that should be executed when the Condition returns false
    private Statement stmt2;

    /**
     * Creates a new If with the given condition and Statements.
     * @param cond      Condition of the IF-THEN-ELSE statement
     * @param stmt1     First Statement that should be executed when the Condition 
     *                  returns true
     * @param stmt2     Second Statement that should be executed when the Condition 
     *                  returns false
     */
    public If(Condition cond, Statement stmt1, Statement stmt2)
    {
        this.cond = cond;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    /**
     * Executes the first statement if the Condition returns true when evaluated and
     * executes the second statement if the Condition returns false when evaluated. If
     * there is no second statement, nothing is executed if the Condition returns false.
     * @param env       Environment that the Condition and Statements should be
     *                  evaluated and executed in.
     * @postcondition   The first statement is executed if the Condition is true, and the
     *                  second statement (if any) is executed if the Condition is false.
     */
    @Override
    public void exec(Environment env)
    {
        if (cond.eval(env))
        {
            stmt1.exec(env);
        }
        else if (stmt2 != null)
        {
            stmt2.exec(env);
        }
    }
    
    /**
     * Compiles a If statement by first creating a new Label name using the convention
     * "endIf" + nextLabelID() (a method in Emitter). The condition is then compiled
     * using the given Emitter and label name. Since this compile() method does not
     * handle ELSE statements, only the first statement is compiled as the second
     * one is assumed to be null. Lastly, the method emits the labelName + ":",
     * indicating that the condition should jump to after the first statement.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the If statement into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        String labelName = "endif" + e.nextLabelID();
        cond.compile(e, labelName);
        stmt1.compile(e);
        e.emit(labelName + ":");
    }
}
