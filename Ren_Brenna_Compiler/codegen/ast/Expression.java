package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * Abstract Expression class that contains abstract methods eval() and compile()
 * that all Expressions need to implement. This class is the superclass
 * of BinOp, Number, and Variable.
 * @author  Brenna Ren
 * @version May 16, 2024
 */

public abstract class Expression
{
    /**
     * Evaluates the expression and returns its numerical value.
     * @param env   Environment that the Expression should be evaluated in.
     * @return      Number representing the value of the Expression.
     */
    public abstract int eval(Environment env);

    /**
     * Compiles the Expression and emits the necessary MIPS Asssembly code.
     *  @param e        Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the Expression into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
