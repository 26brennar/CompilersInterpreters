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
 * ASTTest tests the Parser and other AST classes by calling parseStatement() and exec()
 * on several different test files.
 * @author  Brenna Ren
 * @version March 21, 2024
 */
public class ASTTest 
{
    /**
     * Main tester method that parses and executes the statements of different test files.
     * It keeps calling the parseStatement() method until hasNext() returns false.
     * @param str                       array of String objects
     * @throws FileNotFoundException    no file found from the reader
     * @throws ScanErrorException       unrecognized lexeme or unexpected character
     */
    public static void main(String[] str) throws FileNotFoundException, ScanErrorException
    {
        
        for (int i = 0; i < 9; i++)
        {
            System.out.println("------ TEST " + i + " ------");
            InputStream reader = new FileInputStream(new File(
                    "/Users/brenna/Documents/Compilers/Compiler_Ren_Brenna/ast/parserTest" + i + ".txt"));
            Scanner scanner = new Scanner(reader);
            Parser parser = new Parser(scanner);
            Environment env = new Environment();
            while (scanner.hasNext())
            {
                Statement s = parser.parseStatement();
                s.exec(env);
            }
        }
    }
}
