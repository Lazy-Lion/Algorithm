package heap;

import util.Utils;

/**
 * 堆排序: 待排数组为 a[0,n-1], 不稳定的排序
 *   1.建堆 ：时间复杂度 O(n), 分析如下：假设为满二叉树(h = logn)
 *                              高 度      节点个数
 *               A                h         2^0
 *           ↙     ↘
 *         B          C         h - 1       2^1
 *      ↙  ↘      ↙  ↘
 *    D      F    G      H
 *    ......                    h - k       2^k
 *    ......                    0           2^h
 *
 *
 *    建堆从倒数第二层开始(最后一层都是叶子节点，不需要堆化)，即
 *      T(n) = h + (h-1)*(2^1) + (h-2)*(2^2) + ... + 1*(2^(h-1))
 *      2T(n)=       2h          (h-1)*(2^2) + ... + 2*(2^(h-1)) + 2^h
 *
 *      => T(n) = -h + 2 + 2^2 + .. + 2^(h-2) + 2^(h-1) + 2^h
 *              = -h + (2^(h+1) - 2) = -h - 2 + 2*(2^h)
 *              = -logn - 2 + 2*n = O(n)
 *
 *   2.排序：时间复杂度：O(n*logn),第一步构造最大堆 a[0,n-1]
 *    while(n - 1 > 0)
 *      swap(a[0],a[n-1])  // 交换
 *      n = n - 1;
 *      heapify(a[0,n-1])  // 维持最大堆
 *
 */
public class HeapSort {
    /**
     * @param array 待排序数组
     * @param n     数组长度
     */
    public static void heapSort(int[] array, int n) {
        if (n <= 1) {
            return;
        }
        buildHeap(array, n);
        while (n > 1) {
            Utils.swap(array, 0, n - 1);
            heapify(array, 0, --n);
        }
    }

    private static void buildHeap(int[] array, int n) {
        for (int i = ((n - 2) >>> 1); i >= 0; i--) {
            heapify(array, i, n);
        }
    }

    private static void heapify(int[] array, int index, int length) {
        if (length <= 1) {
            return;
        }

        int key = array[index];
        while (index <= ((length - 2) >>> 1)) {
            int l = 2 * index + 1;
            int r = 2 * index + 2;

            int max = l;
            if (r < length && array[l] < array[r]) {
                max = r;
            }
            if (key >= array[max]) { // compare to key, not array[index]
                break;
            }

            array[index] = array[max];
            index = max;
        }
        array[index] = key;
    }

}
