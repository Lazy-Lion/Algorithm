package leetcode;

import java.util.Stack;

/**
 * leetcode 232: Implement Queue using Stacks
 *
 * Implement the following operations of a queue using stacks.
 *   push(x) -- Push element x to the back of queue.
 *   pop() -- Removes the element from in front of queue.
 *   peek() -- Get the front element.
 *   empty() -- Return whether the queue is empty.
 *
 * Notes:
 *   You must use only standard operations of a stack -- which means only push to top, peek/pop from top, size, and is empty operations are valid.
 *   Depending on your language, stack may not be supported natively.You may simulate a stack by using a list or deque (double-ended queue),as long as you use only standard operations of a stack.
 *   You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).
 */
public class ImplementQueueUsingStacks {
    private Stack<Integer> in;
    private Stack<Integer> out;

    /** Initialize your data structure here. */
    public ImplementQueueUsingStacks() {
        in = new Stack<>();
        out = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        in.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        pushOutStack();
        return out.pop();
    }

    /** Get the front element. */
    public int peek() {
        pushOutStack();
        return out.peek();
    }

    private void pushOutStack() {
        if (in.isEmpty() && out.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }
}
