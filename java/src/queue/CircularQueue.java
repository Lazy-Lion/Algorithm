package queue;

/**
 * 基于数组的循环队列
 * 相比于 QueueBaseOnArray, 不需要进行数组元素的迁移
 */
public class CircularQueue<T> {
    private static final int DEFAULT_CAPACITY = 11;

    private Object[] items;
    private int capacity;       // 容量

    // 队列头用于出队操作，队列尾用于入队操作
    private int head = 0;       // 队列头下标
    private int tail = 0;       // 指向队列最后一个元素的下一个位置

    public CircularQueue(){
        items = new Object[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
    }

    public CircularQueue(int capacity){
        if(capacity <= 0)
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);

        items = new Object[capacity + 1];  // 最后一个位置不存储元素，原因见enqueue
        this.capacity = capacity + 1;
    }


    // 入队
    public boolean enqueue(T item){
        if( (tail + 1) % capacity == head )      // 队满,为了区分队满和队空条件，数组的最后一位不存储元素
            return false;

        items[tail] = item;
        tail = (tail + 1) % capacity;
        return true;
    }

    // 出队
    public T dequeue(){
        if( head == tail )  // 队空
            return  null;

        T item = (T)items[head];
        head = (head + 1) % capacity;
        return item;
    }

    public void printAll(){

        for(int i = head; i % capacity != tail; i ++){
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
}
