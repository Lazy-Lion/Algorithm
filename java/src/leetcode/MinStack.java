package leetcode;

/**
 * leetcode 155. Min Stack
 *
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *   push(x) -- Push element x onto stack.
 *   pop() -- Removes the element on top of the stack.
 *   top() -- Get the top element.
 *   getMin() -- Retrieve the minimum element in the stack.
 *
 * Example 1:
 *   Input
 *     ["MinStack","push","push","push","getMin","pop","top","getMin"]
 *     [[],[-2],[0],[-3],[],[],[],[]]
 *
 *   Output
 *     [null,null,null,null,-3,null,0,-2]
 *
 * Explanation
 *   MinStack minStack = new MinStack();
 *   minStack.push(-2);
 *   minStack.push(0);
 *   minStack.push(-3);
 *   minStack.getMin(); // return -3
 *   minStack.pop();
 *   minStack.top();    // return 0
 *   minStack.getMin(); // return -2
 *
 * Constraints:
 *   Methods pop, top and getMin operations will always be called on non-empty stacks.
 */

public class MinStack {
    private static final int DEFAULT_CAPACITY = 5;

    private int[] element;
    private int size;
    private int min;

    public MinStack() {
        element = new int[DEFAULT_CAPACITY];
        size = 0;
    }

    public void push(int x) {
        if (size == element.length) {
            expansion();
        }

        min = size == 0 ? x : Math.min(x, min);
        element[size++] = x;
    }

    private void expansion() {
        int oldCapacity = element.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        int[] newElement = new int[newCapacity];
        for (int i = 0; i < element.length; i++) {
            newElement[i] = element[i];
        }
        element = newElement;
    }

    public void pop() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (element[--size] == min) {
            calculateMin();
        }
    }

    private void calculateMin() {
        if (size == 0) return;

        int minVal = element[0];
        for (int i = 1; i < size; i++) {
            minVal = Math.min(minVal, element[i]);
        }
        min = minVal;
    }

    public int top() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return element[size - 1];
    }

    public int getMin() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return min;
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(2);
        minStack.push(0);
        minStack.push(3);
        minStack.push(0);
        minStack.push(0);
        minStack.push(3);
        minStack.push(0);
//        System.out.println(minStack.getMin());
//        minStack.pop();
//        System.out.println(minStack.getMin());
//        minStack.pop();
//        System.out.println(minStack.getMin());
//        minStack.pop();
//        System.out.println(minStack.getMin());
    }
}
