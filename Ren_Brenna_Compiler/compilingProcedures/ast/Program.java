package ast;

import java.util.List;

import codegen.Emitter;
import environment.Environment;

/**
 * Program class that contains a List of ProcedureDeclarations and a List of Statements
 * that will be executed when the exec() method is called. It also contains a compile()
 * method that will translate the PASCAL code into MIPS Assembly and emit the statements
 * onto an output file.
 * 
 * Usage:
 * Program p = new Program(<List of ProcedureDeclarations>, <List of Statements>);
 * p.exec(<Environment>);
 * p.compile(<Emitter>);
 * 
 * @author Brenna Ren
 * @version May 24, 2024
 */
public class Program
{
    // List of ProcedureDeclarations
    private List<ProcedureDeclaration> procedures;

    // List of Statements
    private List<Statement> stmts;

    // List of Variable names
    private List<String> vars;

    /**
     * Creates a new program with the given List of ProcedureDeclarations, 
     * given List of Statements, and given List of Variables.
     * @param procedures    given List of ProcedureDeclarations
     * @param stmts         given List of Statements
     * @param vars          givne List of Variables
     */
    public Program(List<ProcedureDeclaration> procedures, List<Statement> stmts, List <String> vars)
    {
        this.procedures = procedures;
        this.stmts = stmts;
        this.vars = vars;
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

    /**
     * Compiles the program and emits the necessary MIPS Assembly Statements. 
     * It creates a new Emitter with the given file output name and uses it
     * as the parameter for all later compile() method calls. It then 
     * declares the variables using ".data", and then compiles
     * the Statements, which will be under the main method, and then compiles 
     * the procedures (if any) At the end of the program, it emits the
     * normal MIPS Assembly termination and closes the Emitter.
     * @param outputFileName        given name for the Emitter file
     * @postcondition               The entire program is translated to MIPS Assembly
     *                              and the necessary statements are printed in
     *                              the output file.
     */
    public void compile(String outputFileName)
    {
        Emitter e = new Emitter(outputFileName);
        e.emit(".data");
        for (String varName : vars)
        {
            e.emit("var" + varName + ":\t.word\t0");
        }
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");

        
        for (Statement s : stmts)
        {
            s.compile(e);
        }

        e.emit("li $v0,  10	# Normal termination");
        e.emit("syscall");

        for (ProcedureDeclaration p : procedures)
        {
            p.compile(e);
        }

        e.close();
    }
}
