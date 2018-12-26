package stack;

import java.util.Stack;

/**
 * 问题描述：leetcode 224
 *
 * Implement a basic calculator to evaluate a simple expression string.
 *
 * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
 *
 * assume that the given expression is always valid.
 *
 * Input: "1 + 1"
 * Output: 2
 *
 * Input: " 2-1 + 2 "
 * Output: 3
 *
 * Input: "(1+(4+5+2)-3)+(6+8)"
 * Output: 23
 */
public class BasicCalculator {
    public int calculate(String s){
        Stack<Integer> operand = new Stack<>(); // 操作数
        Stack<Character> operator = new Stack<>(); // 操作符

        return 0;
    }
}
