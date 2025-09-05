package ast;

import codegen.Emitter;
import environment.Environment;

/**
 * BinOp class that is a subclass of the Expression class. It represents
 * all expressions that contain two expressions joined by a binary operator
 * (+, -, *, /, %). When eval() is called, BinOp evaluates the Expression
 * by using its respective binary operator. BinOp also extends the compile()
 * method that emits the correct MIPS Assembly code based on the BinOp's
 * binary operator.
 * @author  Brenna Ren
 * @version May 16, 2024
 * 
 * Usage:
 * BinOp bExp = new BinOp(<String of the operator>, <Expression 1>, <Expression 2>);
 * int value = bExp.eval(<Environment>);
 * bExp.compile(<Emitter>);
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

    /**
     * Compiles the BinOp expression by first compiling the first Expression
     * and pushing the value onto the stack. Then, the second Expression is
     * compiled, with its value being stored on the $v0 register. The value
     * of the first Expression is then popped from the stack and stored in the
     * $t0 register. The method calls the emitter's methods, emitPush and emitPop
     * to handle these changes. Based on the BinOp's binary operator, the method
     * then emits the needed MIPS Assembly Code (addu, subu, mul, div, mflo, mfhi).
     * @param e         Emitter that will print the emitted statements
     *                  to the output file.
     * @postcondition   The translation of the BinOp into MIPS Assembly
     *                  has been printed to the output file. The value of
     *                  the BinOp resides in $v0.
     */
    public void compile(Emitter e)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");

        if (op.equals("+"))
        {
            e.emit("addu $v0 $t0 $v0");
        }
        else if (op.equals("-"))
        {
            e.emit("subu $v0 $t0 $v0");
        }
        else if (op.equals("*"))
        {
            e.emit("mul $v0 $t0 $v0");
        }
        else if (op.equals("/"))
        {
            e.emit("div $t0 $v0");
            e.emit("mflo $v0");
        }
        else
        {
            e.emit("div $t0 $v0");
            e.emit("mfhi $v0");
        }
    }
}
