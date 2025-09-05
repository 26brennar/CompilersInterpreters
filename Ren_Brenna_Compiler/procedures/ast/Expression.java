package ast;

import environment.Environment;

/**
 * Abstract Expression class that contains an abstract method eval()
 * that all Expressions need to implement. This class is the superclass
 * of BinOp, Number, and Variable.
 * @author  Brenna Ren
 * @version March 21, 2024
 */

public abstract class Expression
{
    /**
     * Evaluates the expression and returns its numerical value.
     * @param env   Environment that the Expression should be evaluated in.
     * @return      Number representing the value of the Expression.
     */
    public abstract int eval(Environment env);
}
