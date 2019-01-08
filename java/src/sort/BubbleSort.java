package sort;

/**
 * 冒泡排序：基于比较的稳定的排序算法 (稳定性：当a[i] == a[j] 时，排好序之后依然保持其先后顺序)
 *
 * 时间复杂度：
 *      最好情况(已排好序，只需一次冒泡): O(n)
 *      最坏情况(逆序): O(n^2)
 *      平均情况: O(n^2)
 *
 * 空间复杂度：O(1)
 */
public class BubbleSort {

    /**
     * 每次冒泡可以保证一个元素在正确的位置上
     * @param a 待排序数组
     * @param n 数组长度
     */
    public void bubbleSort(int[] a, int n){
        if(n <= 1) return;

        for(int i = 0; i < n; i ++){
            boolean hasSwap = false;

            for(int j = 0; j < n - i - 1; j ++){
                if(a[j] > a[j + 1]){    // 相等不交换，保证排序算法的稳定性
                    Utils.swap(a, j, j + 1);
                    hasSwap = true;
                }
            }

            if(!hasSwap) break; // 没有数据交换，退出
        }
    }

}
