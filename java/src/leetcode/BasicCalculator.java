package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 224: Basic Calculator
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
    // 运算符优先级
    private static final Map<Character, Integer> priority = new HashMap<Character, Integer>() {
        {
            put(')', 0);  // 优先级最低
            put('+', 1);
            put('-', 1);
            put('*', 2);
            put('/', 2);
            put('(', 5);  // 优先级最高
        }
    };

    /**
     * way 1 ：模拟运算过程, 遍历中缀表达式: 操作数stack, 操作符stack
     *     支持负数计算, 支持加减乘除
     *     支持负数运算：
     *       对输入表达式进行预处理 : 如 -1+(-2*2+1)+(-1-1+3) ==> 0-1+(0-2*2+1)+(0-1-1+3)
     *
     *   steps:
     *   1. 遇到数字直接添加到操作数栈栈顶
     *   2. 遇到符号：'(' :添加到操作符栈栈顶
     *               ')' :操作符、操作数出栈，直到操作符栈顶符号为'(','('出操作符栈
     *               当前符号的优先级大于操作符栈顶,当前符号入操作符栈
     *               当前符号的优先级小于等于操作符栈顶符号(同级从左到右)，操作符、操作数出栈并计算，直到当前符号优先级大于操作符栈顶符号或栈顶为'('，当前符号入操作符栈
     *
     * time complexity: O(n)
     * space complexity: O(n)
     *
     * ~ 还可以将输入的中缀表达式转换成后缀表达式(逆波兰式，不包括括号), {@link BasicCalculator#getPolish(String)}, 再进行运算
     * ex: 1+(2-(3+5)*6/7+8-9*100)  =>  1 2 3 5 + 6 * 7 / - 8 + 9 100 * - + (这里使用空格分割，是为了能区分数字)
     */
    public static int calculate(String s) {
        if (s == null) return 0;
        s = s.trim();
        if (s.isEmpty()) return 0;

        Stack<Integer> operandStack = new Stack<>(); // 操作数栈
        Stack<Character> operatorStack = new Stack<>(); //  操作符栈

        int k = 0;
        while (k < s.length() || !operatorStack.isEmpty()) {
            if (k == s.length()) {
                int v1 = operandStack.pop();
                int v2 = operandStack.pop();

                operandStack.push(arithmetic(operatorStack.pop(), v1, v2));
                continue;
            }

            char ch = s.charAt(k);
            if (ch == ' ') { // empty character
                // do nothing
            } else if (ch >= '0' && ch <= '9') { // operand
                int val = ch - '0';
                char c;
                while (++k < s.length()) {
                    c = s.charAt(k);
                    if (c >= '0' && c <= '9') {
                        val = val * 10 + (c - '0');
                    } else if (c == ' ') {
                        // do nothing
                    } else { // operator
                        break;
                    }
                }
                k--;
                operandStack.push(val);
            } else { // operator
                if (operatorStack.isEmpty()) {
                    operatorStack.push(ch);
                } else {
                    char topOperator = operatorStack.peek();
                    while (!operatorStack.isEmpty()
                            && priority.get(ch) <= priority.get(topOperator)) {
                        if (topOperator == '(') {
                            if (ch == ')') { // remove a pair of brackets
                                operatorStack.pop();
                            }
                            break;
                        } else { // calculate value
                            int v1 = operandStack.pop();
                            int v2 = 0;
                            if (!operandStack.isEmpty()) { // start with '+' or '-' or (-, etc
                                v2 = operandStack.pop();
                            }
                            operatorStack.pop();

                            operandStack.push(arithmetic(topOperator, v1, v2));
                        }

                        if (!operatorStack.isEmpty()) {
                            topOperator = operatorStack.peek();
                        }
                    }

                    if (ch != ')') {  //  ')' never in the stack
                        operatorStack.push(ch);
                    }
                }
            }
            k++;
        }
        return operandStack.pop();
    }

    private static int arithmetic(char operator, int v1, int v2) {
        assert operator == '+' || operator == '-' || operator == '*' || operator == '/';

        switch (operator) {
            case '+':
                return v2 + v1;
            case '-':
                return v2 - v1;
            case '*':
                return v2 * v1;
            case '/':
                return v2 / v1;
        }
        return 0; // never
    }

    /**
     * 逆波兰式
     */
    public static List<Object> getPolish(String s) {
        if (s == null) return new ArrayList<>();
        s = s.trim();
        if (s.isEmpty()) return new ArrayList<>();

        if (s.charAt(0) == '+') s = s.substring(1);
        if (s.charAt(0) == '-') s = '0' + s;

        Stack<Character> stack = new Stack<>();
        List<Object> polish = new ArrayList<>(s.length());
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == ' ') {
                // do nothing
            } else if (Character.isDigit(s.charAt(i))) {
                int val = ch - '0';
                while (++i < s.length()) {
                    if (s.charAt(i) == ' ') {
                        // do nothing
                    } else if (Character.isDigit(s.charAt(i))) {
                        val = val * 10 + (s.charAt(i) - '0');
                    } else {
                        break;
                    }
                }
                polish.add(val);
                i--;
            } else {
                if (stack.isEmpty()) {
                    stack.push(ch);
                } else {
                    char topOperator = stack.peek();
                    while (!stack.isEmpty() && priority.get(ch) <= priority.get(topOperator)) {
                        if (topOperator == '(') {
                            if (ch == ')') {
                                stack.pop();
                            }
                            break;
                        }
                        stack.pop();
                        polish.add(topOperator);
                        if (!stack.isEmpty()) {
                            topOperator = stack.peek();
                        }
                    }
                    if (ch != ')') {
                        stack.push(ch);
                    }
                }
            }
        }

        while (!stack.isEmpty()) {
            polish.add(stack.pop());
        }

        return polish;
    }

    /**
     * 只支持 + -, 支持负数
     * 由于加减运算符级别一致，只需要使用一个栈保存()外的结果和运算符
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static int calculate_3(String s) {
        int result = 0;
        int sign = 1;

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                int value = ch - '0';

                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    value = value * 10 + (s.charAt(++i) - '0');
                }

                result += value * sign;
            } else if (ch == '+') {
                sign = 1;
            } else if (ch == '-') {
                sign = -1;
            } else if (ch == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (ch == ')') {
                result = result * stack.pop() + stack.pop();
            }
        }
        return result;

    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(BasicCalculator.class
                , new HashSet<String>() {
                    {
                        add("calculate");
                        add("getPolish");
                        add("calculate_3"); // only support + - ()
                    }
                }, Arrays.asList(
                        Arrays.asList("1 + 1"),
                        Arrays.asList(" 2-1 + 2 "),
                        Arrays.asList("1+(2-(3+5)*6/7+8-9*100)"),
                        Arrays.asList("(1+(4+5+2)-3)+(6+8)"),
                        Arrays.asList("2147483647"),
                        Arrays.asList("21+22"),
                        Arrays.asList("2*(3+5)/2*3-12"),
                        Arrays.asList("(-1+(-4+5+2)-3)+(6+8)")
                ));
    }
}
