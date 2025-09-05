package scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * ScannerTest tests the Scanner by printing the results of nextToken() for both a basic test
 * and advanced test.
 * @author  Brenna Ren
 * @version January 26, 2024
 */
public class ScannerTest
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
        InputStream reader = new FileInputStream(new File(
                "/Users/brenna/Documents/Compilers/Compiler_Ren_Brenna/scanner/ScannerTest.txt"));
        InputStream advancedReader = new FileInputStream(new File(
                "/Users/brenna/Documents/Compilers/Compiler_Ren_Brenna/scanner/ScannerTestAdvanced.txt"));
        Scanner scanner = new Scanner(reader);
        Scanner advancedScanner = new Scanner(advancedReader);

        System.out.println("-------------BASIC TEST------------");
        while (scanner.hasNext())
        {
            System.out.println(scanner.nextToken());
        }

        System.out.println("-------------ADVANCED TEST------------");
        while (advancedScanner.hasNext())
        {
            System.out.println(advancedScanner.nextToken());
        }
    }
}