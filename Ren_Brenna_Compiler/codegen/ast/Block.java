package ast;

import java.util.List;

import codegen.Emitter;
import environment.Environment;

/**
 * Block class that is a subclass of the Statement class. It represents
 * a block of statements, which are inclosed by a "BEGIN" and "END". It
 * executes them line by line in the order that they appeared in the text.
 * It also implements the compile() method which compiles the block by
 * calling the compile() method on each of the statements.
 * @author  Brenna Ren
 * @version May 16, 2024
 * 
 * Usage:
 * Block b = new Block(<List of Statements>);
 * b.exec(<Environment>);
 * b.compile(<Emitter>);
 */
public class Block extends Statement
{
    // List of Statements in the Block
    private List<Statement> stmts;

    /**
     * Creates a new Block that contains the given List of Statements.
     * @param stmts     List of Statements that should be executed in the Block.
     */
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Executes the Statements stored in the Block line by line by calling the
     * exec() method on each of the Statements.
     * @param env       Environment that the Statements are executed in.
     * @postcondition   Every statement contained in the Block is executed.
     */
    public void exec(Environment env)
    {
        for (Statement s : stmts)
        {
            s.exec(env);
        }
    }

    /**
     * Compiles the Block of Statements by calling the compile() method
     * on each of the Statements.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the Block into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        for (Statement s : stmts)
        {
            s.compile(e);
        }
    }
}
