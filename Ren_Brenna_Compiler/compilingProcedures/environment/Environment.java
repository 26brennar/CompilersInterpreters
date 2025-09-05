package environment;

import java.util.HashMap;
import java.util.Map;

import ast.ProcedureDeclaration;

/**
 * Environment class that stores all of the variables and procedures by mapping 
 * them to their respective numerical values and ProcedureDeclarations. 
 * It offers methods to set the variable values, declare variables with a value,
 * retrieve the value from the name of the variable, set the procedure's definition,
 * and retrieve the ProcedureDeclaration from the procedure name.
 * @author  Brenna Ren
 * @version April 9, 2024
 * 
 * Usage:
 * Environment env = new Environment();
 * env.declareVariable(<String name of variable>, 1);
 * boolean found = env.setVariable(<String name of variable>, 1);
 * int value = env.getVariable(<String name of variable>);
 * env.setProcedure(<String name of procedure>, <ProcedureDeclaration>);
 * ProcedureDeclaration stmt = env.getProcedure(<String name of procedure>);
 */

public class Environment 
{
    // Map that stores the variables (Strings mapped to integer values)
    private Map<String, Integer> variables;

    // Map that stores the procedures (Strings mapped to ProcedureDeclarations)
    private Map<String, ProcedureDeclaration> procedures;

    // Instance variable that stores the parent environment
    private Environment parent;

    /**
     * Creates a new Environment with the given parent environment, 
     * an empty HashMap of Strings to integers, and an empty HashMap of
     * Strings to ProcedureDeclarations.
     * @param parent        Given parent environment
     */
    public Environment(Environment parent)
    {
        variables = new HashMap<String, Integer> ();
        procedures = new HashMap<String, ProcedureDeclaration> ();
        this.parent = parent;
    }

    /**
     * Associates the given variable name with the given value by adding an
     * entry with the given variable as the key and the given value to the HashMap.
     * @param variable      String name of the variable to store the value in
     * @param value         Integer value to store in the variable
     * @postcondition       The given value is associated with the variable.
     */
    public void declareVariable(String variable, int value)
    {
        variables.put(variable, value);
    }

    /**
     * Sets the variable to the given value by using the following rules:
     * - If the variable exists in the current environment, then only the
     * variable in the current environment is changed to the new value.
     * - If the variable does not exist in the current environment, then
     * the method backtracks using the parent environments until the
     * variable is found. At that point, the variable is modified in that
     * environment.
     * - If the variable is found at one point in the environment's hierarchy,
     * then the method returns true. Otherwise, it returns false.
     * @param variable      String name of the variable to set the value of
     * @param value         Value to replace the variable with
     * @return              True if the variable is found at one point in
     *                      the environment's hierarchy; False otherwise.
     */
    public boolean setVariable(String variable, int value)
    {
        if (variables.containsKey(variable))
        {
            variables.put(variable, value);
            return true;
        }
        else
        {
            if (parent != null)
            {
                return parent.setVariable(variable, value);
            }
            else
            {
                return false;
            }
        }
    }



    /**
     * Returns the value associated with the given variable name.
     * Each variable is 0 by default (undeclared), and if the variable
     * is not found in the current environment, the method backtracks
     * using the parent environments until the variable is found.
     * @param variable      String name of the variable to retrieve the value from.
     * @return              The value associated with the variable.
     */
    public int getVariable(String variable)
    {
        if (variables.get(variable) == null)
        {
            if (parent != null)
            {
                return parent.getVariable(variable);
            }
            else
            {
                return 0;
            }
        }
        return variables.get(variable);
    }

    /**
     * Associates the given procedure name with the given ProcedureDeclaration by adding an
     * entry with the given procedure as the key and the given ProcedureDeclaration as the
     * value to the procedures HashMap.
     * @param id            String name of the procedure to store the value in
     * @param stmt          ProcedureDeclaration statement to store in the variable
     * @postcondition       The given ProcedureDeclaration is associated with the procedure.
     */
    public void setProcedure(String id, ProcedureDeclaration stmt)
    {
        if (parent != null)
        {
            parent.setProcedure(id, stmt);
        }
        else
        {
            procedures.put(id, stmt);
        }
    }

    /**
     * Returns the ProcedureDeclaration associated with the given procedure name.
     * If the procedure is not found in the current environment, the method 
     * backtracks using the parent environments until the procedure is found.
     * @param id            String name of the procedure to retrieve the 
     *                      ProcedureDeclaration from.
     * @return              The ProcedureDeclaration associated with the procedure.
     */
    public ProcedureDeclaration getProcedure(String id)
    {
        if (parent != null)
        {
            return parent.getProcedure(id);
        }
        else
        {
            return procedures.get(id);
        }
    }
}
