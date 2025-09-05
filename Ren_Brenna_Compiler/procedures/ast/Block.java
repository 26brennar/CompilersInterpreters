package ast;

import java.util.List;

import environment.Environment;

/**
 * Block class that is a subclass of the Statement class. It represents
 * a block of statements, which are inclosed by a "BEGIN" and "END". It
 * executes them line by line in the order that they appeared in the text.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * Block b = new Block(<List of Statements>);
 * b.exec(<Environment>);
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
}
