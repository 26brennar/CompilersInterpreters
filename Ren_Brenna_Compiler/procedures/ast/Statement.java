package ast;

import environment.Environment;

/**
 * Abstract Statement class that contains an abstract method exec()
 * that all Statements need to implement. This class is the superclass
 * of Assignment, Block, For, If, Readln, While, and Writeln.
 * @author  Brenna Ren
 * @version March 21, 2024
 */
public abstract class Statement
{
    /**
     * Executes the Statements as needed in the given Environment.
     * @param env       Environment to execute the Statements in.
     * @postcondition   All needed Statements are executed.
     */
    public abstract void exec(Environment env);
}
