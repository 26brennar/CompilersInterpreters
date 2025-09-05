package codegen;
import java.io.*;
import java.util.List;

import ast.ProcedureDeclaration;

/**
 * Emitter class that prints the PASCAL code translated into MIPS Assembly code in
 * an output file.
 * @author 	Brenna Ren
 * @version	May 24, 2024
 * 
 * Usage:
 * Emitter e = new Emitter("output");
 * e.emit("Test");
 * e.close();
 * e.emitPush("$v0");
 * e.emitPop("$t0");
 * int val = e.nextLabelID();
 * e.setProcedureContext(<ProcedureDeclaration>);
 * e.clearProcedureContext();
 * bool l = e.isLocalVariable("test");
 * int offset = e.getOffset();
 */
public class Emitter
{
    // Writes the output into the .asm file
    private PrintWriter out;

	// Accumulating label ID counter
    private int labelID;

    // Stores the current procedure being compiled (if any)
    private ProcedureDeclaration curProc;

    // Additional stack height from compiled expressions
    private int excessStackHeight;


	/**
	 * Creates a new Emitter for writing code to a file with the given name.
     * Sets the label ID to 1, the current ProcedureDeclaration to null,
     * and the excessStackHeight to 0.
	 * @param outputFileName		given output file name (string).
	 */
    public Emitter(String outputFileName)
    {
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
            labelID = 1;
            curProc = null;
            excessStackHeight = 0;
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

	/**
	 * Prints one line of code to file (with non-labels indented)
	 * @param code			line of code to print (string)
	 */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
			code = "\t" + code;
        out.println(code);
    }

	/**
	 * Closes the file. Should be called after all calls to emit.
	 * @postcondition		The output file is closed.
	 */
    public void close()
    {
        out.close();
    }

	/**
	 * Pushes the value in the given register onto the stack.
     * Adds 4 to the excessStackHeight.
	 * @param reg			given register to push onto the stack.
	 * @postcondition		value of the given register is pushed
	 * 						onto the stack.
	 */
    public void emitPush(String reg)
    {
        emit("subu $sp $sp 4");
        emit("sw " + reg + " ($sp)");
        
        excessStackHeight += 4;
    }

	/**
	 * Pops the top value in the stack and stores it in the given register.
     * Subtracts 4 from the excessStackHeight.
	 * @param reg			given register to store the top value of the stack.
	 * @postcondition		the top value of the stack has been popped off
	 * 						and is now stored in the given register.
	 */
    public void emitPop(String reg)
    {
        emit("lw " + reg + " ($sp)");
        emit("addu $sp $sp 4");

        excessStackHeight -= 4;
    }

	/**
	 * Increments the label ID counter and returns its new value.
	 * @return				the value of the label ID counter
	 * @postcondition		the label ID counter has been incremented by 1.
	 */
    public int nextLabelID()
    {
        labelID++;
        return labelID;
    }

    /**
     * Sets the current procedure to the given procedure and ensures
     * that the Emitter knows the context of all the compiling. Sets
     * the excess stack height to 0.
     * @param proc          given ProcedureDeclaration that will
     *                      be compiled
     */
    public void setProcedureContext(ProcedureDeclaration proc)
    {
        curProc = proc;
        excessStackHeight = 0;
    }
    
    /**
     * Clears the procedure context by setting it to null.
     * @postcondition       curProc is set to null.
     */
    public void clearProcedureContext()
    {
        curProc = null;
    }

    /**
     * Returns whether the given variable name corresponds to a local variable
     * or a global variable. If the variable has the same name as the
     * procedure, it also counts as a local variable. Local variables can either
     * be locally declared or an argument of the method.
     * @param varName       given variable String name to analyze
     * @return              true if the given variable name is a local variable,
     *                      false otherwise
     */
    public boolean isLocalVariable(String varName)
    {
        if (curProc == null)
        {
            return false;
        }

        if (varName.equals(curProc.getID()))
        {
            return true;
        }

        List<String> parms = curProc.getParms();

        for (String p : parms)
        {
            if (p.equals(varName))
            {
                return true;
            }
        }

        for (String v : curProc.getLocalVars())
        {
            if (v.equals(varName))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the stack offset for retrieving the given variable.
     * Order of the stack:
     * - Local variables
     * - Return value
     * - Parameters
     * - Return address
     * @param localVarName          given variable name to find stack offset of
     * @return                      stack offset of the given variable
     */
    public int getOffset(String localVarName)
    {
        if (localVarName.equals(curProc.getID()))
        {
            return 0;
        }

        List <String> parms = curProc.getParms();
        
        int index = 0;

        List<String> localVars = curProc.getLocalVars();
        for (int i = 0; i < localVars.size(); i++)
        {
            if (localVars.get(i).equals(localVarName))
            {
                return 4 * i;
            }
        }

        for (int i = 0; i < parms.size(); i++)
        {
            if (parms.get(i).equals(localVarName))
            {
                index = i;
            }
        }

        return 4 * (parms.size() - index - 1) + excessStackHeight + 4;
    }
}