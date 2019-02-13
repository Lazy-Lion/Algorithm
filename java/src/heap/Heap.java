package heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * 堆 (heap): 一种特殊的树
 *   二叉堆(binary heap)满足如下两点：
 *     1. 二叉堆是完全二叉树；
 *     2. 堆中每个节点的值都大于等于(最大堆，max heap)或小于等于(最小堆，min heap)其子节点的值
 *
 *   如下代码实现一个二叉堆 (也即优先队列, priority queue)，使用数组存储堆元素，index从0开始
 *      1.下标为 i 节点, 其左右子节点下标分别为 2 * i + 1 , 2 * (i + 1)
 *      2.下标为 i 节点, 其父节点下标为 (i - 1) / 2
 *      3. 父节点值恒小于子节点值(最小堆，注：java中优先队列(priority queue)默认也是按照自然序，即最小堆生成的)
 */
public class Heap<K> implements Iterable<K>{

    private Object[] queue;  // store items from index 0 to size - 1
    private int size = 0;        // numbers of items
    private Comparator<K> comparator;

    private final static int DEFAULT_CAPACITY = 16;

    /**
     * Constructors
     */
    public Heap(){
        this(DEFAULT_CAPACITY);
    }

    public Heap(int capacity){
        this.queue = new Object[capacity];
    }

    public Heap(Comparator<K> comparator){
        this(DEFAULT_CAPACITY, comparator); // call this() must be first statement in constructor bod
    }

    public Heap(int capacity, Comparator<K> comparator){
        this.comparator = comparator;
        this.queue = new Object[capacity];
    }

    public Heap(K[] keys){

        int len = keys.length;
        if(len > 1) {
            int k = (len - 1) >>> 1;
            for (int i = k; i >= 0; i--) {
                sink(i);
            }
        }
        this.queue = Arrays.copyOf(keys, len, Object[].class);
        this.size = len;
    }

    private void swim(int index){
        if(size <= 1) return;

        if(comparator != null){
            swimComparator(index);
        }else{
            swimComparable(index);
        }
    }

    private void swimComparator(int index){
        K key = (K)queue[index];

        while(index > 0){
            int parent = (index - 1) >>> 1; // 无符号右移
            if(comparator.compare((K) queue[parent], key) <= 0)
                break;
            queue[index] = queue[parent];
            index = parent;
        }
        queue[index] = key;
    }

    private void swimComparable(int index){
        K key = (K)queue[index];

        while(index > 0){
            int parent = (index - 1) >>> 1;
            if(((Comparable<K>)queue[parent]).compareTo(key) <= 0)
                break;
            queue[index] = queue[parent];
            index = parent;
        }
        queue[index] = key;
    }

    private void sink(int index){
        if(size <= 1) return;

        if(comparator != null){
            sinkComparator(index);
        }else{
            sinkComparable(index);
        }
    }

    private void sinkComparator(int index){
        K key = (K)queue[index];
        int c = (size - 1) >>> 1;

        while(index <= c){
            int l = 2 * index + 1;
            int r = 2 * (index + 1);

            int cmp = r >= size ? -1 : comparator.compare( (K)queue[l], (K)queue[r]);
            int min = cmp < 0 ? l : r;

            if(comparator.compare( (K)queue[index], (K)queue[min]) <= 0)
                break;

            queue[index] = queue[min];
            index = min;
        }
        queue[index] = key;
    }

    private void sinkComparable(int index){
        Comparable<K> key = (Comparable<K>)queue[index];
        int c = (size - 1) >>> 1;

        while(index <= c){
            int l = 2 * index + 1;
            int r = 2 * (index + 1);

            int cmp = r >= size ? -1 : ((Comparable<K>)queue[l]).compareTo((K)queue[r]);
            int min = cmp < 0 ? l : r;

            if(key.compareTo((K)queue[min]) <= 0)
                break;

            queue[index] = queue[min];
            index = min;
        }
        queue[index] = key;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void insert(K key){
        if(size == queue.length - 1)
            grow();
        size ++;
        swim(size - 1);
    }

    //数组扩容
    private void grow(){
        int oldCapacity = queue.length;

        int newCapacity = oldCapacity << 1;

        if(newCapacity < oldCapacity){  // overflow
            if(oldCapacity < Integer.MAX_VALUE - 8)
                newCapacity = Integer.MAX_VALUE - 8;
            else
                throw new OutOfMemoryError();
        }

        queue = Arrays.copyOf(queue, newCapacity);
    }

    public K delete(){
        if(isEmpty())
            return null;

        Object v = queue[0];
        queue[0] = queue[--size];
        queue[size] = null;
        sink(0);
        return (K)v;
    }

    //TODO
    @Override
    public Iterator<K> iterator() {
        return null;
    }

}
