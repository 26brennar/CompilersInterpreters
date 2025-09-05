package ast;

import environment.Environment;

/**
 * Condition class that represents the conditional statement in an IF statement.
 * It contains two Expressions and an operator (=, <>, <, >, <=, >=) and evaluates 
 * if the condition is true, which is then used to determine if the IF statement 
 * should be executed.
 * @author Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * Condition cond = new Condition(<Expression 1>, <String of the operator>, <Expression 2>)
 * boolean value = cond.eval(<Environment>);
 */

public class Condition
{
    // First Expression in the Condition
    private Expression exp1;

    // Operator that compares the two Expressions (=, <>, <, >, <=, >=)
    private String relop;

    // Second Expression in the Condition
    private Expression exp2;

    /**
     * Creates a new Condition with the given operator and two Expressions.
     * @param exp1      First Expression to be compared
     * @param relop     Operator that compares the two Expressions
     * @param exp2      Second Expression to be compared
     */
    public Condition(Expression exp1, String relop, Expression exp2)
    {
        this.exp1 = exp1;
        this.relop = relop;
        this.exp2 = exp2;
    }

    /**
     * Returns a boolean that represents if the condition is true or false. It uses
     * the operator to compare the two Expressions.
     * @param env       Environment that the Expressions are evaluated in.
     * @return          True if the Condition is true; 
     *                  False otherwise
     */
    public boolean eval(Environment env) 
    {
        if (relop.equals("="))
        {
            return exp1.eval(env) == exp2.eval(env);
        }
        else if (relop.equals("<>"))
        {
            return exp1.eval(env) != exp2.eval(env);
        }
        else if (relop.equals("<"))
        {
            return exp1.eval(env) < exp2.eval(env);
        }
        else if (relop.equals(">"))
        {
            return exp1.eval(env) > exp2.eval(env);
        }
        else if (relop.equals("<="))
        {
            return exp1.eval(env) <= exp2.eval(env);
        }
        else
        {
            return exp1.eval(env) >= exp2.eval(env);
        }
    }
    
}
