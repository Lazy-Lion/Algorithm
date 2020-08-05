package queue;


import linkedlist.Node;

import java.util.NoSuchElementException;

/**
 * 基于链表实现队列: 无界队列
 *
 * jdk 中 LinkedList 实现了 Deque (双端队列)
 */
public class QueueBaseOnLinkedList {

    private Node head = null;  // 队列头
    private Node tail = null;  // 队列尾

    public QueueBaseOnLinkedList() {
    }

    // 入队
    public boolean enqueue(int item) {
        Node node = new Node(item, null);

        if (head == null) head = node;

        if (tail == null) {
            tail = head;
        } else {
            tail.setNext(node);
            tail = node;
        }


        return true;
    }

    // 出队
    public int dequeue() {
        if (head == null) throw new NoSuchElementException();

        int element = head.getData();
        head = head.getNext();
        return element;
    }

    public void printAll() {
        Node p = head;

        while (p != null) {
            System.out.print(p.getData() + " ");
            p = p.getNext();
        }

        System.out.println();
    }


}
