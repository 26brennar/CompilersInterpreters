package ast;

import java.util.Scanner;

import environment.Environment;

/**
 * Readln class that is a subclass of the Statement class. It waits for a user input
 * and stores that value in a variable.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * Readln read = new Readln(<String name of variable>);
 * read.exec(<Environment>);
 */

public class Readln extends Statement
{
    // String name of the variable to store the input value
    private String variable;

    /**
     * Creates a new Readln with the given variable name.
     * @param variable      String name of the variable to store the input value.
     */
    public Readln(String variable)
    {
        this.variable = variable;
    }

    /**
     * Waits for a user input and stores the value of the input in its variable.
     * It makes a new Assignment to do the assigning of the variable in the given
     * Environment.
     * @param env       Environment that the variable should be assigned in.
     * @postcondition   The user input is stored in the variable in the given Environment.
     */
    @Override
    public void exec(Environment env) 
    {
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();

        Assignment assignment = new Assignment(variable, new Number(num));
        assignment.exec(env);
    }
    
}
