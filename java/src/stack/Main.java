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

    }
}
