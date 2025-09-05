package ast;

import java.util.List;

import codegen.Emitter;
import environment.Environment;

/**
 * ProcedureDeclaration class that is a subclass of the Statement class and contains
 * the String name of the procedure, the Statement in its definition, and
 * a List of the String names of the variables in its parameters.
 * There are also getters for the id, the Statement, the List of parameters, 
 * and the List of local variables.
 * 
 * Usage:
 * ProcedureDeclaration pd = new ProcedureDeclaration("foo", <Statement>, <List of Strings>);
 * pd.exec(<Environment>);
 * int i = pd.getID();
 * Statement s = pd.getStmt();
 * List<String> p = pd.getParms();
 * List<String> v = pd.getLocalVars();
 * pd.compile(<Emitter>);
 * 
 * @author  Brenna Ren
 * @version May 24, 2024
 */
public class ProcedureDeclaration extends Statement
{
    // String name of the procedure
    private String id;

    // Statement in the procedure's definition
    private Statement stmt;

    // List of the String names of the variables in the procedure's parameters
    private List<String> parms;

    // List of the String names of the variables declared in the procedure
    private List<String> localVars;

    /**
     * Creates a new ProcedureDeclaration with the given String name of the procedure,
     * Statement in the procedure's definition, List of the String names of the 
     * variables in the procedure's parameters, and List of the String names of the
     * variables declared in the procedure.
     * @param id        String name of the procedure
     * @param stmt      Statement in the procedure's definition
     * @param parms     List of the String names of the variables in the 
     *                  procedure's parameters
     * @param localVars List of the String names of the variables declared in
     *                  the procedure
     */
    public ProcedureDeclaration(String id, Statement stmt, List<String> parms, List<String> localVars)
    {
        this.id = id;
        this.stmt = stmt;
        this.parms = parms;
        this.localVars = localVars;
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
     * Returns the String name of the procedure
     * @return  String name of the procedure
     */
    public String getID()
    {
        return id;
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

    /**
     * Returns the List of String names of the variables declared in the procedure.
     * @return  List of String name of the variable declared in the procedure
     */
    public List<String> getLocalVars()
    {
        return localVars;
    }

    /**
     * Compiles the ProcedureDeclaration by first designating memory
     * in the stack for the return value and the local declared variables
     * and then compiling the statemenet of the procedure and then
     * using the jump return command to return to where the procedure
     * was called. It also sets and clears the procedure context when needed.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the ProcedureDeclaration into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        e.emit("proc" + id + ":");

        e.emit("li $v0 0");
        e.emitPush("$v0");

        e.setProcedureContext(this);

        for (int i = 0; i < localVars.size(); i++)
        {
            e.emitPush("$v0");
        }
        stmt.compile(e);

        e.emit("jr $ra");
        e.clearProcedureContext();
       
        e.emitPop("$v0");
    }
}