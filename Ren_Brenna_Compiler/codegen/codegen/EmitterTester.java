package codegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ast.Program;
import parser.Parser;
import scanner.Scanner;

/**
 * EmitterTester class for the CodeGen Lab and testing the Emitter. 
 * Can adjust loop values for different tests.
 * @author  Brenna Ren
 * @version May 16, 2024
 */
public class EmitterTester 
{
    /**
     * Main tester method that parses and executes the statements of different test files.
     * It calls parseProgram() once and then compiles the program into an file called
     * "output.asm". Results are found in the output.asm file after the 
     * program finishes running.
     * @param str                       array of String objects
     * @throws FileNotFoundException    no file found from the reader
     */
    public static void main(String[] str) throws FileNotFoundException
    {
        for (int i = 9; i < 10; i++)
        {
            System.out.println("------ PARSER TEST " + i + " ------");
            InputStream reader = new FileInputStream(new File(
                    "/Users/brennaren/Documents/Academics/Compilers/Ren_Brenna_Compiler/codegen/parserTest" + i + ".txt"));
            Scanner scanner = new Scanner(reader);
            Parser parser = new Parser(scanner);
            Program p = parser.parseProgram();
            p.compile("output.asm");
        }
    }
}
