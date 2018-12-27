package stack;

import java.util.HashMap;
import java.util.Map;
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

    // 运算符优先级
    private static final Map<Character,Integer> priority = new HashMap<Character,Integer>(){
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
     * 模拟操作：操作数stack, 操作符stack
     * 支持负数计算, 支持加减乘除
     * 支持负数运算：思路,对输入表达式进行预处理 : 如 -1+(-2*2+1)+(-1-1+3) ==> 0-1+(0-2*2+1)+(0-1-1+3)
     *
     * 以下实现直接模拟运算过程
     *
     * ~ 还可以将输入的中缀表达式转换成后缀表达式(不包括括号)，再进行运算：
     *  ex: 1+(2-(3+5)*6/7+8-9*100)  =>  1 2 3 5 + 6 * 7 / - 8 + 9 100 * - + (这里使用空格分割，是为了能区分数字)
     * 思路：遍历中缀表达式
     *   1. 遇到数字直接添加到结果
     *   2. 遇到符号：'(' :添加到栈顶
     *               ')' :出栈，直到栈顶符号为'(','('出栈
     *               当前符号的优先级大于栈顶,当前符号入栈
     *               当前符号的优先级小于等于栈顶符号(同级从左到右)，出栈，直到当前符号优先级大于栈顶符号或栈顶为'('，当前符号入栈
     *
     * @param s
     * @return
     */
    public static int calculate(String s){

        s = preTreatment(s);

        Stack<Integer> operand = new Stack<>(); // 操作数
        Stack<Character> operator = new Stack<>(); // 操作符


        for(int i = 0; i < s.length(); ){
            int value = 0;
            char ch = s.charAt(i);

            // Digit push operand stack
            boolean isDigit = false;
            while(Character.isDigit(ch) && i < s.length()){
                isDigit = true;
                value = value * 10 + ch - '0';
                i ++;
                if(i < s.length()) {
                    ch = s.charAt(i);
                }
            }

            if(isDigit){
                operand.push(value);
                continue;
            }

            if(ch == 32){ // 空格
                i ++;
                continue;
            }

            // Operator
            if(operator.isEmpty()) {
                operator.push(ch);
            }else{
                char top = operator.peek();
                if(ch == ')'){
                    while(top != '('){
                        top = operator.pop();

                        operandStack(operand, top);

                        if(operator.isEmpty()) break;

                        top = operator.peek();
                    }
                    operator.pop();
                }else if(top != '(' && priority.get(top) >= priority.get(ch)){

                    while(top != '(' && priority.get(top) >= priority.get(ch)){  // 同级运算符，从左到右执行
                        top = operator.pop();

                        operandStack(operand, top);

                        if(operator.isEmpty()) break;

                        top = operator.peek();
                    }
                    operator.push(ch);
                }else{
                    operator.push(ch);
                }
            }
            i ++;

        }

        while(!operand.isEmpty() && !operator.isEmpty()) {
            operandStack(operand, operator.pop());
        }
        return operand.pop();
    }

    private static int calculate(int value1, int value2, char op){
        int result = 0;

        switch (op){
            case '+':
                result = value1 + value2;break;
            case '-':
                result = value1 - value2;break;
            case '*':
                result = value1 * value2;break;
            case '/':
                result = value1 / value2;break;
        }

        return result;
    }

    private static void operandStack(Stack<Integer> operand, char op){
        int value2 = operand.pop();
        int value1 = operand.pop();

        operand.push(calculate(value1, value2, op));
    }

    private static String preTreatment(String s){
        if(s == null) return "";  // assume that the given expression is valid

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < s.length(); i ++){   // 去空格
            if(s.charAt(i)!= ' ') builder.append(s.charAt(i));
        }

        s = builder.toString();
        builder = new StringBuilder();

        if(s.charAt(0) == '-'){  // 有效的表达式开头为 + or - or digit or (
            builder.append("0-");
        }else if(Character.isDigit(s.charAt(0)) || s.charAt(0) == '(') {
            builder.append(s.charAt(0));
        }

        for(int i = 1; i < s.length(); i ++){
            if(s.charAt(i) == '-' && !Character.isDigit(s.charAt(i - 1)) && s.charAt(i - 1) != ')'){
                builder.append("0-");
            }else{
                builder.append(s.charAt(i));
            }
        }


        return builder.toString();

    }


    /**
     * 只支持 + -, 支持负数
     * 由于加减运算符级别一致，只需要使用一个栈保存()外的结果和运算符
     */
    public static int calculateSubAndPlus(String s){
        int result = 0;
        int sign = 1;

        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < s.length(); i ++){
            char ch = s.charAt(i);
            if(Character.isDigit(ch)){
                int value = ch - '0';

                while( i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))){
                    value = value * 10 + s.charAt(++i) - '0';
                }

                result += value * sign;
            }else if(ch == '+'){
                sign = 1;
            }else if(ch == '-'){
                sign = -1;
            }else if(ch == '('){
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            }else if(ch == ')'){
                result = result * stack.pop() + stack.pop();
            }
        }
        return result;

    }

}
