package ast;

import java.util.List;

import environment.Environment;

/**
 * ProcedureDeclaration class that is a subclass of the Statement class and contains
 * the String name of the procedure, the Statement in its definition, and
 * a List of the String names of the variables in its parameters.
 * There are also getters for the Statement and the List of parameters.
 * 
 * Usage:
 * ProcedureDeclaration pd = new ProcedureDeclaration("foo", <Statement>, <List of Strings>);
 * pd.exec(<Environment>);
 * Statement s = pd.getStmt();
 * List<String> p = pd.getParms();
 * 
 * @author  Brenna Ren
 * @version April 9, 2024
 */
public class ProcedureDeclaration extends Statement
{
    // String name of the procedure
    private String id;

    // Statement in the procedure's definition
    private Statement stmt;

    // List of the String names of the variables in the procedure's parameters
    private List<String> parms;

    /**
     * Creates a new ProcedureDeclaration with the given String name of the procedure,
     * Statement in the procedure's definition, and List of the String names of the 
     * variables in the procedure's parameters.
     * @param id    String name of the procedure
     * @param stmt  Statement in the procedure's definition
     * @param parms List of the String names of the variables in the procedure's parameters
     */
    public ProcedureDeclaration(String id, Statement stmt, List<String> parms)
    {
        this.id = id;
        this.stmt = stmt;
        this.parms = parms;
    }

    /**
     * Executes the procedure declaration by calling the setProcedure()
     * method in the given Environment.
     * @param env       Given Environment to set the procedure in.
     */
    @Override
    public void exec(Environment env)
    {
        env.setProcedure(id, this);
    }

    /**
     * Returns the Statement in the procedure
     * @return  Statement in the procedure's definition
     */
    public Statement getStmt()
    {
        return stmt;
    }

    /**
     * Returns the List of the String names of the variables in the procedure's parameters.
     * @return  List of the String names of the variables in the procedure's parameters
     */
    public List<String> getParms()
    {
        return parms;
    }
}