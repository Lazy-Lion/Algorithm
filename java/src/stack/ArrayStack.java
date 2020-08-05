package stack;

/**
 * 数组实现栈：顺序栈
 */
public class ArrayStack {
    private final static int DEFAULT_CAPACITY = 10;

    private String[] items;
    private int count = 0;      // 栈中元素个数
    private int capacity;  // 栈大小

    public ArrayStack() {
        items = new String[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
    }

    public ArrayStack(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    capacity);
        }

        items = new String[capacity];
        this.capacity = capacity;
    }

    /**
     * 入栈操作
     */
    public boolean push(String item) {
        if (count >= capacity) {
            return false;
        }

        items[count] = item;
        count++;
        return true;
    }

    /**
     * 出栈操作
     */
    public String pop() {
        if (count == 0) {
            return null;
        }

        return items[--count];
    }
}
