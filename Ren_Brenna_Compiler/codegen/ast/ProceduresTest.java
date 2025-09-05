package ast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import environment.Environment;
import parser.Parser;
import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * ProceduresTest tests the Parser, other AST classes, and Procedures classes
 * by calling parseProgram() and exec() on several different test files.
 * @author  Brenna Ren
 * @version April 9, 2024
 */
public class ProceduresTest 
{
    /**
     * Main tester method that parses and executes the statements of different test files.
     * It creates a global environment and calls parseProgram() once. It then executes
     * the program by passing in the global environment.
     * @param str                       array of String objects
     * @throws FileNotFoundException    no file found from the reader
     * @throws ScanErrorException       unrecognized lexeme or unexpected character
     */
    public static void main(String[] str) throws FileNotFoundException, ScanErrorException
    {
        
        for (int i = 7; i <= 8; i++)
        {
            System.out.println("------ PARSER TEST " + i + " ------");
            InputStream reader = new FileInputStream(new File(
                    "/Users/brenna/Documents/Compilers/Compiler_Ren_Brenna/procedures/parserTest" + i + ".txt"));
            Scanner scanner = new Scanner(reader);
            Parser parser = new Parser(scanner);
            Environment env = new Environment(null);
            Program p = parser.parseProgram();
            p.exec(env);
        }

        for (int i = 5; i <= 7; i++)
        {
            System.out.println("------ TEST " + i + " ------");
            InputStream reader = new FileInputStream(new File(
                    "/Users/brenna/Documents/Compilers/Compiler_Ren_Brenna/procedures/test" + i + ".txt"));
            Scanner scanner = new Scanner(reader);
            Parser parser = new Parser(scanner);
            Environment env = new Environment(null);
            Program p = parser.parseProgram();
            p.exec(env);
        }
    }
}
