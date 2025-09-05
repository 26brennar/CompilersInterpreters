package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * Variable class that is a subclass of the Expression class. It
 * contains the String name of the variable.
 * @author  Brenna Ren
 * @version May 16, 2024
 * 
 * Usage:
 * Variable var = new Variable(<String name of variable>);
 * int value = var.eval(<Environment>);
 * var.compile(<Emitter>);
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

    /**
     * Compiles the given variable by loading its value into $v0
     * (emits the necessary MIPS Assembly code).
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of loading the Variable into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        e.emit("lw $v0 var" + name);
    }
}
