package queue;

/**
 * 基于数组实现队列： 有界队列
 *
 * jdk中 PriorityQueue 基于数组实现
 */
public class QueueBaseOnArray<T> {

    private static final int DEFAULT_CAPACITY = 10;  // 默认容量

    private Object[] items;
    private int capacity;      // 容量

    // 队列头用于出队操作，队列尾用于入队操作
    private int head = 0;      // 队列头下标
    private int tail = 0;      // 指向队列最后一个元素的下一个位置

    public QueueBaseOnArray(){
        items = new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    public QueueBaseOnArray(int capacity){
        if(capacity <= 0)
            throw new IllegalArgumentException("Illegal Capacity:" + capacity);

        items = new Object[capacity];
        this.capacity = capacity;
    }

    // 入队
    public boolean enqueue(T item){

        if(tail >= capacity){
            if(head == 0) return false;  // 队满

            // 数据迁移, 当空间不足时统一迁移，减少复杂度
            for(int i = head; i < tail; i ++){
                items[i - head] = items[i];
            }
            tail = tail - head;
            head = 0;
        }

        items[tail ++] = item;
        return true;
    }

    // 出队
    public T dequeue(){
        if(head == tail) return null;  // 队空

        return (T)items[head ++];
    }

    public void printAll(){
        for(int i = head; i < tail; i ++){
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
}

