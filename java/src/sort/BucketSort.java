package sort;

import java.util.Arrays;

/**
 * 桶排序：非基于比较的排序算法
 *      核心思想： 将待排序的数据分到几个有序的桶中，每个桶里的数据再单独进行排序，桶内排完序之后，
 *              再把每个桶里的数据按次序依次取出，组成有序序列。
 *
 * 时间复杂度： 线性排序(linear sort)
 *      assume: 待排个数 n , 桶个数 m , 均匀划分到 m 个桶内，每个桶里数据 n/m
 *              每个桶里使用快排进行排序<由于使用了快排，所以桶内不是稳定排序，但是入桶是稳定的> T(n/m) = (n/m)*log(n/m)
 *              m个桶 T = m * T(n/m) = n*log(n/m)
 *              当 m 接近 n, log(n/m) 是较小的常量，T = O(n)
 *
 * 桶排序时间复杂度较低，但是有前提条件：
 *      1. 可以很容易划分m个桶，并且桶之间有天然的顺序
 *      2. 各个桶之间数据的分别要比较均匀，极端情况下所有数据都在一个桶内，时间复杂度退化成快排的 O(nlogn)
 *
 * 空间复杂度：不超过 O(n + m),内存消耗很大
 *
 * 桶排序关于桶数量的选择影响到排序的效率，桶数量的选择需要权衡空间和时间复杂度
 *
 * 桶排序比较适合用在外部排序中：数据存储在外部磁盘，数据量比较大，内存有限，无法将数据全部加载到内存中。
 *    Ex : 10 GB 订单，希望按订单金额 (>0) 排序，内存只有几百 MB.
 *         思路：1.扫描全部文件，确定订单最小金额和最大金额， assume min = 1, max = 100000
 *              2.划分成 100 个桶，分别存储订单金额为 [1-1001),[1001,2001),...的订单
 *              3.理想情况，每个桶的数据文件大小为 100M, 依次读入各个桶的数据并进行排序，将桶内数据写入文件中，再读取并排序下
 *                一个桶的数据。(有可能某个区间数据量特别多，需要对这个区间再进行桶划分，划分成范围更小的桶)
 */
public class BucketSort {
    private int[] array = new int[100];
    private int size = 0;

    public void bucketSort(int[] a, int n){

        int MagicNumber = 100; // 假设桶存储数据的范围是100

        if(n <= 1) return;

        int max = a[0];
        int min = a[0];
        for(int i = 1; i < n; i ++){
            if(a[i] < min) min = a[i];
            if(a[i] > max) max = a[i];
        }

        int count = (max - min) / MagicNumber + 1;   // 桶的数量

        BucketSort[] bucket = new BucketSort[count];

        for(int i = 0; i < n; i ++){   // 数据分配到桶中
            int index = (a[i] - min) / MagicNumber;    // 数据映射到桶

            BucketSort obj = bucket[index];

            if(obj == null){
                obj = new BucketSort();
                bucket[index] = obj;
            }

            if(obj.size >= array.length){
                obj.array = Arrays.copyOf(obj.array, (int)(obj.size * 1.5));  // 数组动态扩容
            }
            obj.array[obj.size ++] = a[i];
        }


        QuickSort quickSort = new QuickSort();
        for(int i = 0; i < count; i ++){
            BucketSort obj = bucket[i];

            if(obj == null || obj.size <= 1) continue;

            quickSort.quickSort(obj.array, obj.size);   // 桶内进行快排

        }

        int idx = 0;
        for(int i = 0; i < count; i ++){

            BucketSort obj = bucket[i];

            if(obj == null) continue;

            for(int j = 0; j < obj.size; j ++){
                a[idx ++] = obj.array[j];
            }
        }
    }

}
