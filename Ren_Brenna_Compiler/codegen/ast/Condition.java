package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * Condition class that represents the conditional statement in an IF statement.
 * It contains two Expressions and an operator (=, <>, <, >, <=, >=) and evaluates 
 * if the condition is true, which is then used to determine if the IF statement 
 * should be executed. It also implements the compile() method and emits the 
 * MIPS Assembly statements that represent the Condition.
 * @author Brenna Ren
 * @version May 16, 2024
 * 
 * Usage:
 * Condition cond = new Condition(<Expression 1>, <String of the operator>, <Expression 2>)
 * boolean value = cond.eval(<Environment>);
 * cond.compile(<Emitter>);
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

    /**
     * Compiles the Condition by first compiling the first expression and pushing
     * its value into the stack. Then, it compiles the second expression with its
     * value in $v0. It then pops the value of the first expression from the stack
     * and stores it in $t0. Based on the operator in the Condition, the method
     * then emits the necessary MIPS Assembly code (bne, bqe, bge, ble, bgt, blt).
     * If the Condition is true, the program will jump to the given label.
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @param label     String name of the label to jump to if the Condition is true.
     * @postcondition   The translation of the assignment into MIPS Assembly
     *                  has been printed to the output file.
     */
    public void compile(Emitter e, String label)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        if (relop.equals("="))
        {
            e.emit("bne $t0 $v0 " + label);
        }
        else if (relop.equals("<>"))
        {
            e.emit("beq $t0 $v0 " + label);
        }
        else if (relop.equals("<"))
        {
            e.emit("bge $t0 $v0 " + label);
        }
        else if (relop.equals(">"))
        {
            e.emit("ble $t0 $v0 " + label);
        }
        else if (relop.equals("<="))
        {
            e.emit("bgt $t0 $v0 " + label);
        }
        else
        {
            e.emit("blt $t0 $v0 " + label);
        }
    }
}
