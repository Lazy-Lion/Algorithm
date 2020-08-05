package stack;

import linkedlist.SNode;

/**
 * 链表实现栈：链式栈
 */
public class LinkedListStack<T> {
    private final static int DEFAULT_CAPACITY = 10;

    private SNode<T> head;
    private int count = 0;
    private int capacity;

    public LinkedListStack() {
        this.capacity = DEFAULT_CAPACITY;
    }

    public LinkedListStack(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Illegal Capacity: " +
                    capacity);

        this.capacity = capacity;
    }

    public boolean push(T element) {
        if (count >= capacity) {
            return false;
        }

        SNode<T> node = new SNode<>(element, null);
        if (head == null) {
            head = node;
        } else {
            node.setNext(head);
            head = node;
        }
        count++;
        return true;
    }

    public T pop() {
        if (count <= 0) return null;

        T value = head.getElement();
        SNode<T> node = head.getNext();
        head.setNext(null);
        head = node;
        count--;

        return value;
    }
}
