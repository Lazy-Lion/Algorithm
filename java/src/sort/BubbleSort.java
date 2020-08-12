package sort;

import util.Utils;

/**
 * 冒泡排序：基于比较的稳定的排序算法 (稳定性：当a[i] == a[j] 时，排好序之后依然保持其先后顺序)
 *
 * 时间复杂度：
 *      最好情况(有序): O(n)
 *      最坏情况(逆序): O(n^2)
 *      平均情况: O(n^2)
 *
 * 空间复杂度：O(1)
 */
public class BubbleSort {
    /**
     * 从头至尾比较相邻元素，如果第一个元素比第二个大则交换。一次遍历完成后，数组的最后一个元素即为最大值；
     * 重复上述操作，将数组倒数第二个元素置为次大值；
     * ...
     *
     * @param array 待排序数组
     * @param n     数组长度
     */
    public static void bubbleSort(int[] array, int n) {
        for (int i = 0; i < n - 1; i++) {
            boolean sorted = true; // 是否已有序
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {  // 相等不交换，保证排序算法的稳定性
                    Utils.swap(array, j, j + 1);
                    sorted = false;
                }
            }
            if (sorted) { // 已有序，返回
                return;
            }
        }
    }
}
