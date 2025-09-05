package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * Abstract Statement class that contains an abstract method exec()
 * that all Statements need to implement. This class is the superclass
 * of Assignment, Block, For, If, Readln, While, and Writeln.
 * It also contains the compile() method for emitting the MIPS Assembly
 * translation of the Statements.
 * @author  Brenna Ren
 * @version May 16, 2024
 */
public abstract class Statement
{
    /**
     * Executes the Statements as needed in the given Environment.
     * @param env       Environment to execute the Statements in.
     * @postcondition   All needed Statements are executed.
     */
    public abstract void exec(Environment env);

    /**
     * Compiles the Statements by emitting the needed MIPS Assembly code.
     * Throws a RuntimeException if the method is not overridden in its
     * subclasses.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the Block into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
