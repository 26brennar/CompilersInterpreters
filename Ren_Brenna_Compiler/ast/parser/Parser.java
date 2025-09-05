package parser;

import java.util.ArrayList;
import java.util.List;

import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Condition;
import ast.Expression;
import ast.For;
import ast.If;
import ast.Number;
import ast.Range;
import ast.Readln;
import ast.Statement;
import ast.Variable;
import ast.While;
import ast.Writeln;
import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * Parser is a simple parser that parses the stream of tokens from the Scanner
 * and returns Statements of the Pascal-like statements. It deals with BEGIN..END statements,
 * WRITELN statements, variable assignment, binary operations, FOR loops, IF-THEN-ELSE
 * statements, WHILE loops, and Readln statements.
 * @author  Brenna Ren
 * @version March 11, 2024
 * 
 * Usage:
 * Parser p = new Parser(<Scanner>);
 * Statement s = p.parseStatement();
 */
public class Parser 
{
    // Scanner to tokenize the input with
    private Scanner scanner;

    // String that represents the current token to be parsed
    private String curToken;

    // List representing variables that are used in the text
    private List<String> variables;
    
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

        variables = new ArrayList<String> ();
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
     * Parses and returns a Number with the number's value by calling the
     * Integer.parseInt() method.
     * @precondition    Current token is an integer
     * @postcondition   Number token has been eaten
     * @return          A Number with the number's value
    */
    private Number parseNumber()
    {
        int num = Integer.parseInt(curToken);
        eat(curToken);
        return new Number(num);
    }

    /**
     * Parses and returns an Expression with the value of the factor (any expression that may
     * be multiplied or divided by). Factors have can be surrounded by parentheses
     * or have a negative sign in front of it. They can also be the name of a
     * variable that is mapped to an integer value.
     * @precondition    Current token is an integer, (, ), -, or a variable name.
     * @postcondition   All tokens of the factor have been eaten.
     * @return          An Expression containing the value of the Factor.
     */
    private Expression parseFactor()
    {
        if (curToken.equals("("))
        {
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            return exp;
        }
        else if (curToken.equals("-"))
        {
            eat("-");
            return new BinOp("-", new Number(0), parseFactor());
        }
        else if (variables.contains(curToken))
        {
            Variable exp = new Variable(curToken);
            eat(curToken);
            return exp;
        }
        else
        {
            return parseNumber();
        }
    }

    /**
     * Parses and returns an Expression with the value of the term (any expression that may
     * be added or subtracted). Terms may contain factors, so the parseFactor()
     * method is called within parseTerm(). 
     * @precondition    The current token is the start of a factor.
     * @postcondition   All tokens of the term have been eaten.
     * @return          An Expression containing the value of the term.
     */
    private Expression parseTerm()
    {
        Expression exp1 = parseFactor();
        while (curToken.equals("*") || curToken.equals("/") || curToken.equals("mod"))
        {
            if (curToken.equals("*"))
            {
                eat("*");
                Expression exp2 = parseFactor();
                exp1 = new BinOp("*", exp1, exp2);
            }
            else if (curToken.equals("/"))
            {
                eat("/");
                Expression exp2 = parseFactor();
                exp1 = new BinOp("/", exp1, exp2);
            }
            else if (curToken.equals("mod"))
            {
                eat("mod");
                Expression exp2 = parseFactor();
                exp1 = new BinOp("%", exp1, exp2);
            }
        }
        return exp1;
    }

    /**
     * Parses and returns an Expression with the value of the expression (including both addition
     * and subtract expressions). Expressions may contain terms, factors, or
     * numbers. Since parseTerm() contains all of those types, parseTerm() is
     * called by parseExpression().
     * @precondition    The current token is the start of a term.
     * @postcondition   All of the tokens in the expression have been eaten.
     * @return          An Expression containing the value of the expression.
     */
    private Expression parseExpression()
    {
        Expression exp1 = parseTerm();
        while (curToken.equals("+") || curToken.equals("-"))
        {
            if (curToken.equals("+"))
            {
                eat("+");
                Expression exp2 = parseTerm();
                exp1 = new BinOp("+", exp1, exp2);
            }
            else if (curToken.equals("-"))
            {
                eat("-");
                Expression exp2 = parseTerm();
                exp1 = new BinOp("-", exp1, exp2);
            }
        }
        return exp1;
    }

    /**
     * Parses and returns a Condition with the parsed operator and two Expressions of the
     * Condition. Conditions contain two Expressions separated by =, <>, <, >, <=, >=.
     * @return      A Condition with the parsed operator and two 
     *              Expressions of the Condition.
     */
    private Condition parseCondition()
    {
        Expression exp1 = parseExpression();
        if (curToken.equals("="))
        {
            eat("=");
            Expression exp2 = parseExpression();
            return new Condition(exp1, "=", exp2);
        }
        else if (curToken.equals("<>"))
        {
            eat("<>");
            Expression exp2 = parseExpression();
            return new Condition(exp1, "<>", exp2);
        }
        else if (curToken.equals("<"))
        {
            eat("<");
            Expression exp2 = parseExpression();
            return new Condition(exp1, "<", exp2);
        }
        else if (curToken.equals(">"))
        {
            eat(">");
            Expression exp2 = parseExpression();
            return new Condition(exp1, ">", exp2);
        }
        else if (curToken.equals("<="))
        {
            eat("<=");
            Expression exp2 = parseExpression();
            return new Condition(exp1, "<=", exp2);
        }
        else
        {
            eat(">=");
            Expression exp2 = parseExpression();
            return new Condition(exp1, ">=", exp2);
        }
        
    }

    /**
     * Parses a statement and returns a Statement representing it. The statements may contain
     * BEGIN...END blocks, WRITELN(), variable assignment, FOR loops, IF-THEN-ELSE
     * statements, WHILE loops, and READLN(). Each statement ends with a semicolon,
     * and a period concludes all the statements, signalling EOF.
     * @precondition    The statements to be parsed are valid PASCAL statements.
     * @postcondition   The statement has been parsed and a Statement representing it
     *                  has been returned. All of the tokens in the statement have been eaten.
     * @return          A Statement representing the statement that was parsed.
     */
    public Statement parseStatement()
    {
        List<Statement> stmts = new ArrayList<Statement>();

        if (curToken.equals("WRITELN"))
        {
            eat("WRITELN"); // eats the WRITELN
            eat("("); // eats the (
            Expression exp = parseExpression();
            eat(")"); // eats the )
            eat(";");  // eats the ;
            return new Writeln(exp);
        }
        else if(curToken.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            String variable = curToken;
            eat(curToken);
            eat(")");
            eat(";");
            variables.add(variable);
            return new Readln(variable);

        }
        else if(curToken.equals("BEGIN"))
        {
            eat("BEGIN");
            while (!curToken.equals("END"))
            {
                stmts.add(parseStatement());
            }
            eat("END");
            eat(";");
            return new Block(stmts);
        }

        else if (curToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            Statement stmt1 = parseStatement();
            Statement stmt2 = null;
            if (curToken.equals("ELSE"))
            {
                eat("ELSE");
                stmt2 = parseStatement();
            }
            return new If(cond, stmt1, stmt2);
        }
        else if (curToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            Statement stmt = parseStatement();
            return new While(cond, stmt);
        }
        else if (curToken.equals("FOR"))
        {
            eat("FOR");

            String varName = curToken;
            variables.add(varName);
            eat(varName);
            eat(":=");
            Expression exp = parseExpression();
            Assignment assignment = new Assignment(varName, exp);

            eat("TO");

            Expression endVal = parseExpression();
            Range range = new Range(varName, endVal);

            eat("DO");

            Statement stmt = parseStatement();

            return new For(assignment, range, varName, stmt);
        }
        else
        {
            String varName = curToken;
            variables.add(varName);
            eat(varName);
            eat(":=");
            Expression exp = parseExpression();
            eat(";");
            return new Assignment(varName, exp);
        }
    }
}
