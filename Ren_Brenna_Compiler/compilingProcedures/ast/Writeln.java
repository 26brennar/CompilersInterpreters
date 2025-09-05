package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * Writeln class that is a subclass of the Statement class. It prints the
 * value of the given Expression in the terminal if evaluated. 
 * If compiled, it emits the necessary MIPS Assembly code in the output file
 * to print the value of the Expression.
 * @author  Brenna Ren
 * @version May 16, 2024
 * 
 * Usage:
 * Writeln write = new Writeln(<Expression>);
 * write.exec(<Environment>);
 * write.comiple(<Emitter>);
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

    /**
     * Compiles the Writeln expression by emitting the translation of the
     * Writeln in MIPS Assembly to the output file. Note that this
     * compile method only handles expressions with integer values. It also
     * prints a new line after printing the expression's value.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the Writeln into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");

        e.emit("li $v0 11");
        e.emit("li $a0 10");
        e.emit("syscall");
    }
}
