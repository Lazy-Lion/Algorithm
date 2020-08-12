package sort;

/**
 * 插入排序：基于比较的稳定的排序算法
 *
 * 时间复杂度：
 *      最好情况 (有序)：O(n)
 *      最坏情况 (逆序)：O(n^2)
 *      平均情况：O(n^2)
 *
 * 空间复杂度： O(1)
 */
public class InsertionSort {
    /**
     * 将数组分为两个区间： 已排序区间和未排序区间
     *      已排序区间初始为一个元素（数组第一个元素）
     *      取未排序区间中的元素，在已排序区间中找到合适的位置插入，保证已排序区间有序，直到未排序区间元素为空
     *
     * @param array 待排序数组
     * @param n     数组长度
     */
    public static void insertionSort(int[] array, int n) {
        for (int i = 1; i < n; i++) {
            int j = i - 1;
            int temp = array[i];
            while (j >= 0 && array[j] > temp) {
                array[j + 1] = array[j]; //只需要一个赋值，而不需要swap, 这是优于冒泡排序的地方
                j--;
            }
            array[j + 1] = temp;
        }
    }
}
