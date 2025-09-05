package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import scanner.ScanErrorException;
import scanner.Scanner;

public class ParserTest 
{
    /**
     * Main tester method that prints the lexemes of a basic test and an advanced test.
     * It keeps calling the nextToken() method until hasNext() returns false.
     * @param str                       array of String objects
     * @throws FileNotFoundException    no file found from the reader
     * @throws ScanErrorException       unrecognized lexeme or unexpected character
     */
    public static void main(String[] str) throws FileNotFoundException, ScanErrorException
    {
        
        for (int i = 0; i < 6; i++)
        {
            System.out.println("------ TEST " + i + " ------");
            InputStream reader = new FileInputStream(new File(
                    "/Users/brenna/Documents/Compilers/Compiler_Ren_Brenna/parser/parserTest" + i + ".txt"));
            Scanner scanner = new Scanner(reader);
            Parser parser = new Parser(scanner);
            while (scanner.hasNext())
            {
                parser.parseStatement();
            }
        }
    }
}
