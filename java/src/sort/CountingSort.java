package sort;

/**
 * 计数排序：非基于比较的稳定的排序算法
 *      核心思想：assume 待排数组 a 数据个数 n ,数据范围 [min,max]  (not negative integer)
 *           参照桶排序的思想，在范围不大的情况下设置 max - min + 1 个桶，每个桶里存放的数据都是相等的，所以不需要对桶内数据
 *             进行排序，只要依次遍历桶，输出即为有序; 计数排序再此基础上，桶(bucket[i])内不存放实际元素，而是
 *             存放当前桶元素的个数
 *           如何得到有序序列？
 *              1.从左到右遍历 bucket, 进行累加，bucket[i] 存储的就是 <= i 的数据的个数
 *              2.从右到左遍历待排数组 a, 找到对应的 bucket[v]，bucket[v]-1即为当前元素应该存放的位置，bucket[v] = bucket[v]-1
 *
 * 时间复杂度：O(n)， 只涉及到遍历
 *
 * 空间复杂度：O(m + n) => O(n) (m - 桶数量较小)
 */
public class CountingSort {

    public void countingSort(int[] a, int n){

        if(n <= 1) return;

        // 确定范围
        int max = a[0];
        int min = a[0];
        for(int i = 1; i < n; i ++){
            max = Math.max(max, a[i]);
            min = Math.min(min, a[i]);
        }


        int[] bucket = new int[max - min + 1];

        // 桶计数
        for(int i = 0; i < n; i ++){
            bucket[ a[i] - min ] ++;
        }

        // 桶计数累加
        for(int i = 1; i < bucket.length; i ++){
            bucket[i] = bucket[i] + bucket[i - 1];
        }

        // 获取排序结果
        int[] result = new int[n];
        for(int i = n - 1; i >= 0; i --){
            int index = bucket[ a[i] ] - 1;
            bucket[ a[i] ] --;
            result[index] = a[i];
        }

        // 结果复制到原数组
        for(int i = 0; i < n; i ++){
            a[i] = result[i];
        }
    }
}
