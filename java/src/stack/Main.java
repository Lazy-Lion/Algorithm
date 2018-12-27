package stack;

/**
 * test
 */
public class Main {
    public static void main(String[] args){
       // ArrayStack stack = new ArrayStack(3);

        LinkedListStack<String> stack = new LinkedListStack<>(3);

        stack.push("a");
        stack.push("b");
        stack.push("c");
        System.out.print(stack.pop());
        System.out.print(stack.pop());
        System.out.print(stack.pop());
        System.out.print(stack.pop());
        System.out.println();

        System.out.println(ValidParentheses.isValid("{}"));

        System.out.println(DecodeString.decodeAtIndex("leet2code3", 10));
        System.out.println(DecodeString.decodeAtIndex("ha22", 5));
        System.out.println(DecodeString.decodeAtIndex("a2345678999999999999999", 1));
        System.out.println(DecodeString.decodeAtIndex("y959q969u3hb22odq595", 222280369));


        System.out.println("BasicCalculator:");
        System.out.println(String.valueOf(BasicCalculator.calculate(" 0")));
        System.out.println(String.valueOf(BasicCalculator.calculate("(1+(4+5+2)-3)+(6+8)")));
        System.out.println(String.valueOf(BasicCalculator.calculate("(-1+(-4+5+2)-3)+(6+8)")));
        System.out.println(String.valueOf(BasicCalculator.calculate("(1+(4*5+1)/3)+(6+8)")));
        System.out.println(String.valueOf(BasicCalculator.calculate("(1+(-4*5+1)/3)+(6+8)")));
        System.out.println(String.valueOf(BasicCalculator.calculate(" 2-1 + 2 ")));
        System.out.println(String.valueOf(BasicCalculator.calculate(" 21-1 + 2 ")));
        System.out.println(String.valueOf(BasicCalculator.calculate("1 * 2 - 3 / 1")));


        System.out.println(String.valueOf(BasicCalculator.calculateSubAndPlus(" 0")));
        System.out.println(String.valueOf(BasicCalculator.calculateSubAndPlus("(1+(4+5+2)-3)+(6+8)")));
        System.out.println(String.valueOf(BasicCalculator.calculateSubAndPlus(" 2-1 + 2 ")));
        System.out.println(String.valueOf(BasicCalculator.calculateSubAndPlus(" 21-1 + 2 ")));

    }
}
