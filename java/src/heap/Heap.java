package heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
public class Heap<K> implements Iterable<K> {

    private Object[] items;  // store items from index 0 to size - 1
    private int size = 0;        // numbers of items
    private Comparator<K> comparator;

    private final static int DEFAULT_CAPACITY = 16;
    private final static int MAX_CAPACITY = Integer.MAX_VALUE - 8;

    /**
     * Constructors
     */
    public Heap() {
        this(DEFAULT_CAPACITY);
    }

    public Heap(int capacity) {
        this.items = new Object[capacity];
    }

    public Heap(Comparator<K> comparator) {
        this(DEFAULT_CAPACITY, comparator); // call this() must be first statement in constructor bod
    }

    public Heap(int capacity, Comparator<K> comparator) {
        this.comparator = comparator;
        this.items = new Object[capacity];
    }

    public Heap(K[] keys) {
        int length = keys.length;
        this.items = Arrays.copyOf(keys, length, Object[].class);
        this.size = length;

        /**
         * heapify: Time cost < O(n*logn), in fact T = O(n)
         *   {@link HeapSort}
         */
        if (length > 1) {
            // 当前节点的比较次数与节点所在高度有关，最大高度为logn
            int k = (length - 1 - 1) >>> 1; // the parent of the last node
            for (int i = k; i >= 0; i--) {
                sink(i);
            }
        }
    }

    /**
     * @return the number of keys on this heap
     */
    public int size() {
        return size;
    }

    /**
     * @return true if heap is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * add a new key to this heap
     */
    public void add(K key) {
        if (size == items.length)
            grow();

        items[size++] = key;
        swim(size - 1);
    }

    /**
     * heapify from bottom to top
     * for insert
     */
    private void swim(int index) {
        if (size <= 1) {
            return;
        }

        K key = (K) items[index];

        while (index > 0) {
            int parent = (index - 1) >>> 1;
            if (compare((K) items[parent], key) <= 0) {
                break;
            }
            items[index] = items[parent];
            index = parent;
        }
        items[index] = key;
    }

    /**
     * heapify from top to bottom
     * for delete
     */
    private void sink(int index) {
        if (size <= 1) {
            return;
        }

        K key = (K) items[index];
        int c = (size - 1 - 1) >>> 1;  // the last index is size - 1, so parent index is (size - 2) / 2

        while (index <= c) {
            int l = 2 * index + 1;
            int r = 2 * (index + 1);

            int cmp = r >= size ? -1 : compare((K) items[l], (K) items[r]);
            int min = cmp < 0 ? l : r;

            if (compare(key, (K) items[min]) <= 0) {
                break;
            }

            items[index] = items[min];
            index = min;
        }
        items[index] = key;
    }

    private int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        } else {
            return ((Comparable<K>) k1).compareTo(k2);
        }
    }

    /**
     * double the length of the heap array
     */
    private void grow() {
        int oldCapacity = items.length;

        int newCapacity = oldCapacity << 1;

        if (newCapacity < oldCapacity) {  // overflow
            if (oldCapacity < MAX_CAPACITY) {
                newCapacity = MAX_CAPACITY;
            } else {
                throw new OutOfMemoryError();
            }
        }

        items = Arrays.copyOf(items, newCapacity);
    }

    /**
     * remove and return the top element on this heap
     */
    public K poll() {
        if (isEmpty()) return null;

        Object v = items[0];
        items[0] = items[--size];
        items[size] = null;
        sink(0);
        return (K) v;
    }


    @Override
    public Iterator<K> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<K> {

        private Heap<K> copy;

        public Iter() {
            if (comparator == null) {
                copy = new Heap<>(size());
            } else {
                copy = new Heap<>(size(), comparator);
            }

            //不要使用foreach方式,会访问到items中未被使用的位置，导致ArrayIndexOutOfBoundsException
            for (int i = 0; i < size(); i++) {
                copy.add((K) items[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.poll();
        }
    }

    public static void main(String[] args) {
        Heap<Integer> h = new Heap<>(new Integer[]{8, 6, 21, 3, 2, 1});
        h.add(10);
        h.add(-1);
        h.add(1);

        System.out.println(h.isEmpty());
        System.out.println(h.size);
        System.out.println(h.poll());
        System.out.println(h.poll());
        System.out.println(h.poll());
        System.out.println(h.size);


        for (Integer i : h) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}
