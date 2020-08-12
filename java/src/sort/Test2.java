package sort;

import java.util.Arrays;

import leetcode.KthLargestElementInAnArray;
import util.Utils;

/**
 * test
 */
public class Test2 {
    public static void main(String[] args){
        KthLargestElementInAnArray kthLargest = new KthLargestElementInAnArray();
        BucketSort bucketSort = new BucketSort();
        CountingSort countingSort = new CountingSort();

        // 归并排序
        int[] array = new int[]{6,5,4,7,4,3,2,1};
        MergeSort.mergeSort(array, array.length);
        System.out.println("merge sort : " + Arrays.toString(array));

        // 快速排序
        array = new int[]{6,5,4,7,4,3,2,1};
        QuickSort.quickSort(array, array.length);
        System.out.println("quick sort : " + Arrays.toString(array));

        //桶排序
        array = new int[]{6,5,4,7,4,3,2,1};
        bucketSort.bucketSort(array, array.length);
        System.out.println("bucket sort: " + Arrays.toString(array));

        array = Utils.generateArray(20);
        System.out.println("before bucket sorting: " + Arrays.toString(array));
        bucketSort.bucketSort(array, array.length);
        System.out.println("after bucket sorting : " + Arrays.toString(array));

        // 计数排序
        array = Utils.generateArray(20,5);
        System.out.println("before counting sorting: " + Arrays.toString(array));
        countingSort.countingSort(array, array.length);
        System.out.println("after counting sorting : " + Arrays.toString(array));

        System.out.println("归并排序、快速排序、希尔排序、桶排序性能比较：");

        for(int i = 0; i < 3; i ++){
            array = Utils.generateArray(1000000);
            long startTime = System.currentTimeMillis();
            MergeSort.mergeSort(array, array.length);
            System.out.println("merge sort : " + (System.currentTimeMillis() - startTime) + "ms");

            array = Utils.generateArray(1000000);
            startTime = System.currentTimeMillis();
            QuickSort.quickSort(array, array.length);
            System.out.println("quick sort : " + (System.currentTimeMillis() - startTime) + "ms");

            array = Utils.generateArray(1000000);
            startTime = System.currentTimeMillis();
            ShellSort.shellSort(array, array.length);
            System.out.println("shell sort : " + (System.currentTimeMillis() - startTime) + "ms");

            array = Utils.generateArray(1000000);
            startTime = System.currentTimeMillis();
            bucketSort.bucketSort(array, array.length);
            System.out.println("bucket sort: " + (System.currentTimeMillis() - startTime) + "ms");
            System.out.println();

        }

        // kthLargest
        array = new int[]{3,2,1,5,6,4};
        System.out.println(kthLargest.findKthLargest(array,2));

        System.out.println(kthLargest.findKthLargest_heap(array,2));


    }
}
