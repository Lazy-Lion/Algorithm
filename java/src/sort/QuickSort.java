package sort;

import util.Utils;

/**
 * 快速排序：不稳定的排序算法
 *      核心思想: Divide and Conquer, 对数组下标从l到r的数据进行排序，选择l~r之间的任一数据作为pivot,
 *               遍历l到r的数据(除了pivot, pivot单独处理)，将小于pivot的放左边，大于等于pivot的放右边，pivot放中间。
 *
 * 时间复杂度：递归树求解
 *      最好情况(分区平衡，即将区间等分): O(nlogn)
 *      最坏情况(划分区间集中在一部分): O(n^2),
 *              优化pivot的选取，可以减少最坏情况出现的概率：
 *                  1. 三数取中法：从区间的首、尾、中间，分别取一个数，取这3个数的中间值作为分区点；(或取更多数的中间值)
 *                  2. 随机法：随机选择一个元素作为分区点
 *      平均情况: O(nlogn)
 *
 * 空间复杂度： O(1) ,  归并排序空间复杂度为O(n), 所以快排更为常用
 *
 * JDK 8 中排序的实现: Arrays.sort() :
 *                      插入排序改进 => pair insertion sort
 *                      快速排序改进 => 双轴快排(pivot1, pivot2 =>  < pivot1, pivot1 <= && <= pivot2, >= pivot2)
 */
public class QuickSort {
    /**
     * 递归：
     *    递推公式： quickSort_recursion(l,r) = { quickSort_recursion(l, p - 1), quickSort_recursion(p + 1, r) }
     *    终止条件： l == r
     *    数组index: [l,r] 左右均包含
     *
     * @param array 待排数组
     * @param n 数组长度
     */
    public static void quickSort(int[] array, int n) {
        recursion(array, 0, n - 1);
    }

    private static void recursion(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int index = partition(array, start, end);

        recursion(array, start, index - 1);
        recursion(array, index + 1, end);
    }

    /**
     * 分区函数
     */
    private static int partition(int[] array, int start, int end) {
        int index = getPivotIndex(array, start, end);
        int pivot = array[index];

        // 将数组分为两部分：已处理a[start, k)和未处理a[k, end),遍历未处理部分
        // 满足条件加入到已处理末尾(使用交换的方式)
        Utils.swap(array, index, end);
        int k = start;
        for (int i = start; i < end; i++) {
            if (array[i] < pivot) {
                Utils.swap(array, k++, i);
            }
        }
        Utils.swap(array, end, k); // pivot加到已处理部分末尾

        return k;
    }

    /**
     * 分区函数 2:
     *   3-way-partitioning: 将数组划分成3部分 A1,A2,A3；当 a1∈ A1, a2∈A2, a3∈A3 时，a1 < a2 < a3
     */
    private static int three_way_partition(int[] array, int start, int end) {
        int index = getPivotIndex(array, start, end);
        int pivot = array[index];

        int i = start, j = end;
        for (int k = start; k <= j; ) {
            if (array[k] > pivot) {
                Utils.swap(array, k, j--);
            } else if (array[k] < pivot) {
                Utils.swap(array, k++, i++);
            } else {
                k++;
            }
        }
        return i;
    }

    /**
     * 三数取中法，可以扩展多个数
     */
    private static int getPivotIndex(int[] array, int start, int end) {
        if (end - start == 1) {
            return start;
        }

        int[] sorted = new int[3];

        int middle = start + ((end - start) >> 1);
        sorted[0] = start;
        sorted[1] = middle;
        sorted[2] = end;

        // insertion sort
        for (int i = 1; i < sorted.length; i++) {
            int temp = sorted[i];
            int j = i - 1;
            while (j >= 0 && array[temp] < array[sorted[j]]) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = temp;
        }

        return sorted[1];
    }

}
