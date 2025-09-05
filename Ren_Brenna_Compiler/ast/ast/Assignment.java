package ast;

import environment.Environment;

/**
 * Assignment class that is a subclass of the Statement class. It executes all
 * variable-assigning, and it contains a String instance variable representing
 * the variable and an Expression instance variable that is stored in the
 * the variable. It implements the exec() method that executes the assignment.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * Assignment assignment = new Assignment(<Variable Name>, <Expression>);
 * assignment.exec(<Environment>);
 */
public class Assignment extends Statement
{
    // String name of the variable
    private String var;

    // Expression to be stored in the variable
    private Expression exp;

    /**
     * Creates aa new Assignment with the given variable name and Expression.
     * @param var       String that represents the variable's name
     * @param exp       Expression that will be stored in the variable
     */
    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Sets the value of the variable to the Expression in the given environment.
     * @param env       Environment that the variable will be stored in
     * @postcondition   The environment contains a variable with the value 
     *                  of the Expression.
     */
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }
}
