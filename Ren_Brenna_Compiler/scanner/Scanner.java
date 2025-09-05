package scanner;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1.
 * It breaks the input stream into lexemes consisting of numbers, identifiers, operands,
 * terminators, and separators.
 * @author  Brenna Ren
 * @version January 26, 2024
 *  
 * Usage:
 * Scanner s = new Scanner(<FileInputStream>);
 * boolean moreChars = s.hasNext();
 * String lexeme = s.nextToken();
 * boolean digit = s.isDigit(<currentChar>);
 * boolean letter = s.isLetter(<currentChar>);
 * boolean whitespace = s.isWhitespace(<currentChar>);
 * boolean operand = s.isOperand(<currentChar>);
 * boolean term = s.isTerminator(<currentChar>);
 * boolean sep = s.isSeparator(<currentChar>);
 */
public class Scanner
{
    private BufferedReader in; // Reader for the input file
    private char currentChar; // Current character to process
    private boolean eof; // End of file flag

    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    
    /**
     * The getNextChar method attempts to get the next character from the input
     * stream.  It sets the eof flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a char object.
     * @postcondition   The input stream is advanced one character if it is not at
     *                  end of file and the currentChar instance field is set to the char
     *                  representation of the character read from the input stream.  The flag
     *                  eof is set true if the input stream is exhausted.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1) 
                eof = true;
            else 
                currentChar = (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    /**
     * "Eats" the currentChar by moving past it and retrieving the
     * next character. It first compares if the parameter is equal
     * to currentChar, and if so, it gets the next character.
     * Otherwise, it throws a ScanErrorException() with
     * additional information.
     * @param expected              the character to "eat", it should be
     *                              equal to currentChar
     * @throws ScanErrorException   Expected character does not match current character.
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException("Illegal character - expected" + expected 
                + " and found " + currentChar);
        }
    }
    /**
     * Returns whether the input stream is at the end of file.
     * @return      True if the input stream has more values to scan
     *              False if the input stream is at end of file
     */
    public boolean hasNext()
    {
        return !eof;
    }


    /**
     * Examines the value of currentToken and returns a String representing 
     * the lexeme found (if any). This method skips over whitespaces and returns
     * "EOF" if the input stream is at end-of-file when nextToken is called. It
     * also skips over single line comments.
     * @return      a String representing the lexeme found. If at the end-of-file,
     *              returns "EOF". 
     * @throws ScanErrorException   No lexeme is recognized.
     */
    public String nextToken() throws ScanErrorException
    {
        try 
        {
            if (eof || currentChar == '.')
            {
                eof = true;
                return "EOF";
            }
            else if (currentChar == '/') // removing comments
            {
                char tempChar = currentChar;
                eat(currentChar);
                if (currentChar == '/')
                {
                    while (!eof && !isTerminator(currentChar) && currentChar != '\n' 
                            && currentChar != '\r')
                    {
                        eat(currentChar);
                    }
                    return nextToken();
                }
                else
                {
                    return "" + tempChar;
                }
            }
            else if (currentChar == ':' || currentChar == '<' || currentChar == '>')
            {
                char tempChar = currentChar;
                eat(currentChar);
                if (currentChar == '=' || (tempChar == '<' && currentChar == '>'))
                {
                    char tempChar2 = currentChar;
                    eat(currentChar);
                    return "" + tempChar + tempChar2;
                }
                else
                {
                    return "" + tempChar;
                }
            }
            else if (isDigit(currentChar))
            {
                return scanNumber();
            }
            else if (isLetter(currentChar))
            {
                return scanIdentifier();
            }
            else if (isOperand(currentChar) || isTerminator(currentChar) 
                    || isSeparator(currentChar))
            {
                return scanOperand();
            }
            else if (isWhiteSpace(currentChar))
            {
                eat(currentChar);
                return nextToken();
            }
            else
            {
                throw new ScanErrorException("Unrecognized character.");
            }
        }
        catch (ScanErrorException e)
        {
            eat(currentChar);
            return e.getMessage();
        }
    }

    /**
     * Scans the lexeme and returns a String representing a number.
     * A number lexeme is defined as one or more digits.
     * @precondition                currentChar is a number
     * @return                      String representing a number (the lexeme)
     * @throws ScanErrorException   No lexeme recognized.
     */
    private String scanNumber() throws ScanErrorException
    {   
        char tempChar = currentChar;
        eat(currentChar);

        if (isWhiteSpace(currentChar) || isTerminator(currentChar) || isSeparator(currentChar))
        {
            return "" + tempChar;
        }
        else if (!isDigit(currentChar))
        {
            if (isOperand(currentChar) || currentChar == '<' || currentChar == '>')  
            {
                return "" + tempChar;
            }
            else
            {
                return "No lexeme recognized.";
            }
        }
        return tempChar + scanNumber();
    }

    /**
     * Scans the lexeme and returns a String representing an identifier.
     * An identifier is defined as a letter followed by letters or digits.
     * @precondition                currentChar is an identifier
     * @return                      String representing an identifier (the lexeme)
     * @throws ScanErrorException   No lexeme recognized.
     */
    private String scanIdentifier() throws ScanErrorException
    {
        char tempChar = currentChar;
        eat(currentChar);

        if (isWhiteSpace(currentChar) || isTerminator(currentChar) || isSeparator(currentChar))
        {
            return "" + tempChar;
        }
        else if (!isDigit(currentChar) && !isLetter(currentChar))
        {
            if (isOperand(currentChar) || currentChar == ':' || 
                    currentChar == '<' || currentChar == '>')  
            {
                return "" + tempChar;
            }
            else
            {
                return "No lexeme recognized.";
            }
        }
        return tempChar + scanIdentifier();
    }
    /**
     * Scans the lexeme and returns a String representing an operand.
     * An operand lexeme is defined as a singular operand.
     * @precondition                currentChar is an operand
     * @return                      String representing an operand (the lexeme)
     * @throws ScanErrorException   No lexeme recognized.
     */
    private String scanOperand() throws ScanErrorException 
    {
        char tempChar = currentChar;
        eat(currentChar);

        return "" + tempChar;
    }

    /**
     * Helper method that determines if the given character is a digit (0-9)
     * @param checkChar     given character
     * @return              true if the given character is a digit,
     *                      false otherwise
     */
    public static boolean isDigit(char checkChar)
    {
        return checkChar >= '0' && checkChar <= '9';
    }

    /**
     * Helper method that determines if the given character is a letter
     * (a-z and A-Z)
     * @param checkChar     given character
     * @return              true if the given character is a letter,
     *                      false otherwise
     */
    public static boolean isLetter(char checkChar)
    {
        return (checkChar >= 'A' && checkChar <= 'Z')
            || (checkChar >= 'a' && checkChar <= 'z');
    }

    /**
     * Helper method that determines if the given character is
     * white space (' ', '\n', 't', '\r')
     * @param checkChar     given character
     * @return              true if the given character is white space
     *                      false otherwise
     */
    public static boolean isWhiteSpace(char checkChar)
    {
        return checkChar == ' ' || checkChar == '\n' || checkChar == '\t' || checkChar == '\r';
    }
    
    /**
     * Helper method that determines if a given character is
     * an operand ('=', '+', '-', '*', '/', '%', '(', ')')
     * @param checkChar     given character
     * @return              true if the given character is an operand
     *                      false otherwise
     */
    public static boolean isOperand(char checkChar)
    {
        return checkChar == '=' || checkChar == '+' || checkChar == '-' || checkChar == '*' || 
            checkChar == '/' || checkChar == '%' || checkChar == '(' || checkChar == ')';
    }

    /**
     * Helper method that determines if a given character is a 
     * terminator (period or semicolon).
     * @param checkChar     given character
     * @return              true if the given character is a terminator
     *                      false otherwise
     */
    public static boolean isTerminator(char checkChar)
    {
        return checkChar == '.' || checkChar == ';';
    }
    /**
     * Helper method that determines if a given character is a 
     * separator (comma or colon).
     * @param checkChar     given character
     * @return              true if the given character is a separator
     *                      false otherwise
     */
    public static boolean isSeparator(char checkChar)
    {
        return checkChar == ':' || checkChar == ',';
    }
}
