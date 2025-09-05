package parser;

import java.util.HashMap;
import java.util.Map;

import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * Parser is a simple parser that parses the stream of tokens from the Scanner
 * and executes the Pascal-like statements. It deals with BEGIN..END statements,
 * WRITELN statements, and using variables.
 * @author  Brenna Ren
 * @version March 11, 2024
 * 
 * Usage:
 * Parser p = new Parser(<Scanner>);
 * p.parseStatement();
 */
public class Parser 
{
    // Scanner to tokenize the input with
    private Scanner scanner;

    // String that represents the current token to be parsed
    private String curToken;

    // Map that stores the variables (strings mapped to integer values)
    private Map<String, Integer> variables;
    
    /**
     * Creates a new Parser with the given Scanner. Catches a ScanErrorException()
     * if the scanner does not have a next token.
     * 
     * Usage:
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * Parser p = new Parser(lex);
     * 
     * @param s     Given Scanner to tokenize the input with
     */
    public Parser(Scanner s)
    {
        scanner = s;
        try
        {
            curToken = scanner.nextToken();
        } 
        catch (ScanErrorException e) 
        {
            e.printStackTrace();
        }

        variables = new HashMap<String, Integer> ();
    }

    /**
     * "Eats" the current token by moving past it and retrieving the
     * next token from the scanner. It first compares if the parameter is equal
     * to curToken, and if so, it gets the next token. Otherwise, it throws 
     * a IllegalArgumentException() with additional information.
     * @param str       The token to "eat", should be equal to curToken
     */
    private void eat(String str)
    {
        if (curToken.equals(str))
        {
            try 
            {
                curToken = scanner.nextToken();
            } 
            catch (ScanErrorException e) 
            {
                e.printStackTrace();
            }
        }
        else
        {
            throw new IllegalArgumentException("Found " + curToken + " --- Expected " + str);
        }
    }

    /**
     * Parses and returns the current token (an integer) by calling the
     * Integer.parseInt() method.
     * @precondition    Current token is an integer
     * @postcondition   Number token has been eaten
     * @return          The value of the parsed integer
    */
    private int parseNumber()
    {
        int num = Integer.parseInt(curToken);
        eat(curToken);
        return num;
    }

    /**
     * Parses and returns the value of the factor (any expression that may 
     * be multiplied or divided). Factors have can be surrounded by parentheses
     * or have a negative sign in front of it. They can also be the name of a
     * variable that is mapped to an integer value.
     * @precondition    Current token is an integer, (, ), -, or a variable name.
     * @postcondition   All tokens of the factor have been eaten.
     * @return          The value of the factor.
     */
    private int parseFactor()
    {
        if (curToken.equals("("))
        {
            eat("(");
            int num = parseExpression();
            eat(")");
            return num;
        }
        else if (curToken.equals("-"))
        {
            eat("-");
            return -parseFactor();
        }
        else if (variables.containsKey(curToken))
        {
            int num = variables.get(curToken);
            eat(curToken);
            return num;
        }
        else
        {
            return parseNumber();
        }
    }

    /**
     * Parses and returns the value of the term (any expression that may
     * be added or subtracted). Terms may contain factors, so the parseFactor()
     * method is called within parseTerm(). 
     * @precondition    The current token is the start of a factor.
     * @postcondition   All tokens of the term have been eaten.
     * @return          The value of the term.
     */
    private int parseTerm()
    {
        int val = parseFactor();
        while (curToken.equals("*") || curToken.equals("/") || curToken.equals("mod"))
        {
            if (curToken.equals("*"))
            {
                eat("*");
                int num = parseFactor();
                val *= num;
            }
            else if (curToken.equals("/"))
            {
                eat("/");
                int num = parseFactor();
                val /= num;
            }
            else if (curToken.equals("mod"))
            {
                eat("mod");
                int num = parseFactor();
                val %= num;
            }
        }
        return val;
    }

    /**
     * Parses and returns the value of the expression (including both addition
     * and subtract expressions). Expressions may contain terms, factors, or
     * numbers. Since parseTerm() contains all of those types, parseTerm() is
     * called by parseExpression().
     * @precondition    The current token is the start of a term.
     * @postcondition   All of the tokens in the expression have been eaten.
     * @return          The value of the expression.
     */
    private int parseExpression()
    {
        int val = parseTerm();
        while (curToken.equals("+") || curToken.equals("-"))
        {
            if (curToken.equals("+"))
            {
                eat("+");
                int num = parseTerm();
                val += num;
            }
            else if (curToken.equals("-"))
            {
                eat("-");
                int num = parseTerm();
                val -= num;
            }
        }
        return val;
    }



    /**
     * Parses the statements and executes them. The statements may contain
     * BEGIN...END blocks, WRITELN(), or variable declaration. It also handles
     * nested BEGIN...END blocks. Each statement (one line) ends with a semicolon,
     * and a period concludes all the statements, signalling EOF.
     * @precondition    The statements to be parsed are valid PASCAL statements.
     * @postcondition   All the statements have been parsed and executed. All of the
     *                  tokens in the statements have been eaten.
     */
    public void parseStatement()
    {
        if (curToken.equals("WRITELN"))
        {
            eat("WRITELN"); // eats the WRITELN
            eat("("); // eats the (
            int num = parseExpression();
            eat(")"); // eats the )
            eat(";");  // eats the ;
            System.out.println(num);
        }
        while (curToken.equals("END") || curToken.equals("BEGIN"))
        {
            if (curToken.equals("END"))
            {
                eat("END");
                eat(";");
            }
            else if (curToken.equals("BEGIN"))
            {
                eat("BEGIN");
                parseStatement();
            }
        }

        if (curToken.equals("EOF"))
        {
            return;
        }

        if (!curToken.equals("WRITELN") && !curToken.equals("END") && !curToken.equals("BEGIN"))
        {
            String varName = curToken;
            eat(varName);
            eat(":=");
            int num = parseExpression();
            variables.put(varName, num);
            eat(";");
        }
    }
}
