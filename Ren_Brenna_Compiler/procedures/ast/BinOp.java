package ast;

import environment.Environment;

/**
 * BinOp class that is a subclass of the Expression class. It represents
 * all expressions that contain two expressions joined by a binary operator
 * (+, -, *, /, %). When eval() is called, BinOp evaluates the Expression
 * by using its respective binary operator.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * BinOp bExp = new BinOp(<String of the operator>, <Expression 1>, <Expression 2>);
 * int value = bExp.eval(<Environment>);
 */
public class BinOp extends Expression
{
    // String representing the binary operator (+, -, *, /, %)
    private String op;

    // First expression of the operation
    private Expression exp1;

    // Second expression of the operation
    private Expression exp2;

    /**
     * Creates a BinOp with the given binary operator and two expressions.
     * @param op        String representing the binary operator
     * @param exp1      First expression of the operation
     * @param exp2      Second expression of the operation
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluates the BinOp expression by applying its respective binary
     * operator to the two expressions. Returns the value of the expression.
     * @param env   The environment that the expressions are evaluated in.
     * @return      The value of the expression after applying the operation.
     */
    @Override
    public int eval(Environment env)
    {
        if (op.equals("+"))
        {
            return exp1.eval(env) + exp2.eval(env);
        }
        else if (op.equals("-"))
        {
            return exp1.eval(env) - exp2.eval(env);
        }
        else if (op.equals("*"))
        {
            return exp1.eval(env) * exp2.eval(env);
        }
        else if (op.equals("/"))
        {
            return exp1.eval(env) / exp2.eval(env);
        }
        else
        {
            return exp1.eval(env) % exp2.eval(env);
        }
    }
}
