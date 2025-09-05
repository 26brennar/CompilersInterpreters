package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * Assignment class that is a subclass of the Statement class. It executes all
 * variable-assigning, and it contains a String instance variable representing
 * the variable and an Expression instance variable that is stored in the
 * the variable. It implements the exec() method that executes the assignment.
 * It also implements the compile() method that compiles the assignment
 * by printing the sw command.
 * @author  Brenna Ren
 * @version May 24, 2024
 * 
 * Usage:
 * Assignment assignment = new Assignment(<Variable Name>, <Expression>);
 * assignment.exec(<Environment>);
 * assignment.compile(<Emitter>);
 */
public class Assignment extends Statement
{
    // String name of the variable
    private String var;

    // Expression to be stored in the variable
    private Expression exp;

    /**
     * Creates aa new Assignment with the given variable name and Expression.
     * @param var       String that represents the variable's name
     * @param exp       Expression that will be stored in the variable
     */
    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Sets the value of the variable to the Expression by calling the
     * setVariable() method. If the variable is not found in the given
     * environment, then it will search in the parent environment. If
     * the variable is not found in the environment's hierarchy, then
     * a variable with the stored name and value is declared in the
     * given environment.
     * @param env       Environment that the variable will be stored in
     *                  (if not already in the hierarchy)
     * @postcondition   The environment or parent environment contains 
     *                  a variable with the value of the Expression.
     */
    public void exec(Environment env)
    {
        int val = exp.eval(env);
        boolean found = env.setVariable(var, val);
        if (!found)
        {
            env.declareVariable(var, val);
        }
    }

    /**
     * Compiles the assignment by first compiling the expression using
     * its compile() method and then emitting "sw $v0 var<varName>"
     * if it is a global variable. If it is a local variable, it retreives
     * value from the stack instead.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the assignment into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        if (e.isLocalVariable(var))
        {
            int offset = e.getOffset(var);
            e.emit("addu $sp $sp " + offset);
            e.emit("sw $v0 ($sp)");
            e.emit("subu $sp $sp " + offset);
        }
        else
        {
            e.emit("sw $v0 var" + var);
        }
    }
}
