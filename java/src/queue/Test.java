package queue;

import leetcode.ShortestSubarrayWithSumAtLeastK;

/**
 * test
 */
public class Test {

    public static void main(String[] args){
        System.out.println("QueueBaseOnArray:");

        QueueBaseOnArray<Integer> queue = new QueueBaseOnArray<>(3);
        System.out.println(queue.enqueue(1));
        System.out.println(queue.enqueue(2));
        System.out.println(queue.enqueue(3));
        System.out.println(queue.enqueue(4));
        queue.printAll();
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

        System.out.println("CircularQueue:");
        CircularQueue<Integer> cQueue = new CircularQueue<>(3);
        System.out.println(cQueue.enqueue(1));
        System.out.println(cQueue.enqueue(2));
        System.out.println(cQueue.enqueue(3));
        System.out.println(cQueue.enqueue(4));
        cQueue.printAll();
        System.out.println(cQueue.dequeue());
        System.out.println(cQueue.dequeue());
        System.out.println(cQueue.dequeue());
        System.out.println(cQueue.dequeue());

        System.out.println("QueueBaseOnLinkedList:");
        QueueBaseOnLinkedList lQueue = new QueueBaseOnLinkedList();
        System.out.println(lQueue.enqueue(1));
        System.out.println(lQueue.enqueue(2));
        System.out.println(lQueue.enqueue(3));
        System.out.println(lQueue.enqueue(4));
        lQueue.printAll();
        System.out.println(lQueue.dequeue());
        System.out.println(lQueue.dequeue());
        System.out.println(lQueue.dequeue());
        System.out.println(lQueue.dequeue());
//        System.out.println(lQueue.dequeue());


        System.out.println("ShortestSubarrayWithSumAtLeastK:");
        ShortestSubarrayWithSumAtLeastK subarray = new ShortestSubarrayWithSumAtLeastK();

        System.out.println(subarray.shortestSubarray(new int[]{1}, 1));
        System.out.println(subarray.shortestSubarray(new int[]{1,2}, 4));
        System.out.println(subarray.shortestSubarray(new int[]{2,-1,2}, 3));
        System.out.println(subarray.shortestSubarray(new int[]{17,85,93,-45,-21}, 150));

    }
}
