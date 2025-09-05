package ast;

import java.util.List;

import environment.Environment;

/**
 * Program class that contains a List of ProcedureDeclarations and a List of Statements
 * that will be executed when the exec() method is called.
 * 
 * Usage:
 * Program p = new Program(<List of ProcedureDeclarations>, <List of Statements>);
 * p.exec(<Environment>);
 * 
 * @author Brenna Ren
 * @version April 9, 2024
 */
public class Program
{
    // List of ProcedureDeclarations
    private List<ProcedureDeclaration> procedures;

    // List of Statements
    private List<Statement> stmts;

    /**
     * Creates a new program with the given List of ProcedureDeclarations
     * and given List of Statements.
     * @param procedures    given List of ProcedureDeclarations
     * @param stmts         given List of Statements
     */
    public Program(List<ProcedureDeclaration> procedures, List<Statement> stmts)
    {
        this.procedures = procedures;
        this.stmts = stmts;
    }

    /**
     * Executes the ProcedureDeclarations and then executes the Statements
     * by calling their respective exec() methods.
     * @param env       Given Environment to execute the Statements in.
     */
    public void exec(Environment env)
    {
        for (ProcedureDeclaration p : procedures)
        {
            p.exec(env);
        }
        for (Statement s : stmts)
        {
            s.exec(env);
        }
    }
}
