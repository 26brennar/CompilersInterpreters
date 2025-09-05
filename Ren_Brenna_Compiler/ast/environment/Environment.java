package environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Environment class that stores all of the variables and maps them to their
 * respective numerical values. It offers methods to set the variable values
 * and retrieve the value from the name of the variable.
 * @author  Brenna Ren
 * @version March 21, 2024
 * 
 * Usage:
 * Environment env = new Environment();
 * env.setVariable(<String name of variable>, 1);
 * int value = env.getVariable(<String name of variable>);
 */

public class Environment 
{
    // Map that stores the variables (Strings mapped to integer values)
    private Map<String, Integer> variables;

    /**
     * Creates a new Environment with an empty HashMap of Strings to integers.
     */
    public Environment()
    {
        variables = new HashMap<String, Integer> ();
    }

    /**
     * Associates the given variable name with the given value by adding an
     * entry with the given variable as the key and the given value to the HashMap.
     * @param variable      String name of the variable to store the value in
     * @param value         Integer value to store in the variable
     * @postcondition       The given value is associated with the variable.
     */
    public void setVariable(String variable, int value)
    {
        variables.put(variable, value);
    }


    /**
     * Returns the value associated with the given variable name.
     * @param variable      String name of the variable to retrieve the value from.
     * @return              The value associated with the variable.
     */
    public int getVariable(String variable)
    {
        return variables.get(variable);
    }
}
