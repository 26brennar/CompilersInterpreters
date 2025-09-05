package ast;

import java.util.List;

import codegen.Emitter;
import environment.Environment;

/**
 * ProcedureCall class that is a subclass of the Expression class and contains
 * the String name of the procedure and a List of Strings of the arguments. 
 * When the eval() method is called, a new child environment is created and
 * the parameters are created with their respective argument values. Then,
 * the statement in the procedure is executed and a value is returned.
 * 
 * Usage:
 * ProcedureCall pc = new ProcedureCall("foo", <List of Expressions>);
 * int val = pc.eval(<Environment>);
 * pc.compile(<Emitter>);
 * 
 * @author  Brenna Ren
 * @version May 24, 2024
 */
public class ProcedureCall extends Expression
{
    // String name of the procedure
    private String id;

    // List of Expressions that represent the procedure's arguments
    private List<Expression> args;

    /**
     * Creates a ProcedureCall with the given String name of the procedure
     * and a given List of Expressions that represent the procedure's arguments
     * @param id    String name of the procedure
     * @param args  List of Expressions that represent the procedure's arguments
     */
    public ProcedureCall(String id, List<Expression> args)
    {
        this.id = id;
        this.args = args;
    }

    /**
     * Returns the value from the procedure's execution. This method first creates
     * a child environment from the given environment. It then declares the
     * parameter variables using the expressions from the arguments. It then executes
     * the statement in the procedure before finally returning the value of the variable
     * associated with the procedure's name. If no argument is given, the value 0 is
     * passed in to the parameters.
     * @param env       Given environment to execute the ProcedureCall in.
     * @return          The value of the variable associated with the procedure's name.
     */
    public int eval(Environment env)
    {
        Environment child = new Environment(env);
        List<String> parms = child.getProcedure(id).getParms();
        for (int i = 0; i < parms.size(); i++)
        {
            if (i < args.size())
            {
                child.declareVariable(parms.get(i), args.get(i).eval(env));
            }
            else
            {
                child.declareVariable(parms.get(i), 0);
            }
        }
        child.getProcedure(id).getStmt().exec(child);
        return child.getVariable(id);
    }

    /**
     * Compiles the ProcedureCall by first making space in the stack for the
     * return address of the subroutine and then compiling all of the arguments
     * and then jump-and-link to the procedure. It then pops all the arguments
     * from the stack and loads the return address of the procedure from sthe
     * stack as well.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the ProcedureCall into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e)
    {
        e.emit("subu $sp $sp 4");
        e.emit("sw $ra ($sp)");

        for (int i = 0; i < args.size(); i++)
        {
            args.get(i).compile(e);
            e.emitPush("$v0");
        }

        e.emit("jal proc" + id);

        for (int i = 0; i < args.size(); i++)
        {
            e.emitPop("$t0");
        }

        e.emit("lw $ra ($sp)");
        e.emit("addu $sp $sp 4");
    }
}
