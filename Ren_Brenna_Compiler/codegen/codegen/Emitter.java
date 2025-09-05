package codegen;
import java.io.*;

/**
 * Emitter class that prints the PASCAL code translated into MIPS Assembly code in
 * an output file.
 * @author 	Brenna Ren
 * @version	May 16, 2024
 * 
 * Usage:
 * Emitter e = new Emitter("output");
 * e.emit("Test");
 * e.close();
 * e.emitPush("$v0");
 * e.emitPop("$t0");
 * int val = e.nextLabelID();
 */
public class Emitter
{
    // Writes the output into the .asm file
    private PrintWriter out;

	// Accumulating label ID counter
    private int labelID;


	/**
	 * Creates a new Emitter for writing code to a file with the given name.
	 * @param outputFileName		given output file name (string).
	 */
    public Emitter(String outputFileName)
    {
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
            labelID = 1;
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
	 * @param reg			given register to push onto the stack.
	 * @postcondition		value of the given register is pushed
	 * 						onto the stack.
	 */
    public void emitPush(String reg)
    {
        emit("subu $sp $sp 4");
        emit("sw " + reg + " ($sp)");
    }

	/**
	 * Pops the top value in the stack and stores it in the given register.
	 * @param reg			given register to store the top value of the stack.
	 * @postcondition		the top value of the stack has been popped off
	 * 						and is now stored in the given register.
	 */
    public void emitPop(String reg)
    {
        emit("lw " + reg + " ($sp)");
        emit("addu $sp $sp 4");
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
}