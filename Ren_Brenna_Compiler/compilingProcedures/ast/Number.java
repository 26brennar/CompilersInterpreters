package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * Number class that is a subclass of the Expression class. It represents a number
 * and contains an integer instance variable. When it is evaluated, it returns this value.
 * It also extends the compile() method in which it loads an integer of
 * the stored value into $v0.
 * @author  Brenna Ren
 * @version May 16, 2024
 * 
 * Usage:
 * Number num = new Number(1);
 * int value = num.eval(<Environment>);
 * num.compile(<Emitter>);
 */

public class Number extends Expression
{
    // Integer representing the value of the number
    private int value;

    /**
     * Creates a new Number with the given integer value
     * @param value     Integer representing the value of the number
     */
    public Number(int value)
    {
        this.value = value;
    }

    /**
     * Returns the integer value of the Number.
     * @param env       Environment that the Expression should be evaluated in.
     * @return          The integer value of the Number.
     */
    @Override
    public int eval(Environment env)
    {
        return value;
    }

    /**
     * Compiles the Number by emitting the MIPS Assembly statement
     * that loads the stored value into $v0.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the Number into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }
}
