package sort;

/**
 * 计数排序：非基于比较的稳定的排序算法
 *    核心思想：
 *        assume: 待排数组 array 数据个数 n ,数据范围 [min,max]  (non-negative integer)
 *        根据桶排序的思想，在范围不大的情况下设置 max - min + 1 个桶，每个桶里存放的数据都是相等的，所以不需要对桶内数据
 *      进行排序，只要依次遍历桶，输出即为有序;
 *        计数排序在此基础上，桶(bucket[i])内不存放实际元素，只是存放当前桶内元素的个数
 *      如何通过元素个数记录得到有序序列？
 *        1.从左到右遍历 bucket, 进行累加（bucket[i] = bucket[i] + bucket[i-1]）
 *          => 遍历完成后，bucket[i]存储的就是 <= i 的数据的个数
 *        2.从右到左遍历（为了保证稳定性）待排数组 array, 对于数组中的某个元素v, bucket[v]-1即为当前元素应该存放的位置索引；
 *          bucket[v] = bucket[v]-1，新的bucket[v]用于计算下一个值等于v的元素索引
 *
 * 时间复杂度：O(n + m)， 只涉及到遍历
 *
 * 空间复杂度：O(m + n) => O(n) (m - 桶数量, m <= n)
 */
public class CountingSort {

    public static void countingSort(int[] array, int n) {

        if (n <= 1) return;

        // determine the scope
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < n; i++) {
            max = Math.max(max, array[i]);
            min = Math.min(min, array[i]);
        }

        // calculate the number of buckets
        int[] buckets = new int[max - min + 1];

        // count per bucket
        for (int i = 0; i < n; i++) {
            buckets[array[i] - min]++;
        }

        // accumulation
        for (int i = 1; i < buckets.length; i++) {
            buckets[i] = buckets[i] + buckets[i - 1];
        }

        // get sorted result
        int[] result = new int[n];
        for (int i = n - 1; i >= 0; i--) { // for the stability of sorting, traverse from back to front
            int bucketIndex = array[i] - min;
            int index = buckets[bucketIndex] - 1;
            buckets[bucketIndex]--;
            result[index] = array[i];
        }

        // copy result to original array
        for (int i = 0; i < n; i++) {
            array[i] = result[i];
        }
    }
}
