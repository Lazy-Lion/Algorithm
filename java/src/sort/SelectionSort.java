package sort;

/**
 * 选择排序：基于比较的不稳定的排序算法（由于其不稳定，逊色于插入排序和冒泡排序）
 *
 * 时间复杂度：
 *      最好情况： O(n^2)
 *      最坏情况： O(n^2)
 *      平均情况:  O(n^2)
 *
 * 空间复杂度：O(1)
 */
public class SelectionSort {

    /**
     * 类似插入排序，分为已排序区间和未排序区间
     *      每次从未排序区间中找到最小的元素，将其放到已排序区间的末尾
     * @param a 待排序数组
     * @param n 数组长度
     */
    public void selectionSort(int[] a, int n){
        for(int i = 0; i < n - 1; i ++){

            int minIndex = i;
            for(int j = i + 1; j < n; j ++){
                if(a[minIndex] > a[j]) minIndex = j;
            }

            Utils.swap(a, i, minIndex);
        }
    }
}
