package sort;

import java.util.Arrays;

/**
 * test
 */
public class Main2 {
    public static void main(String[] args){
        MergeSort mergeSort = new MergeSort();
        QuickSort quickSort = new QuickSort();
        ShellSort shellSort = new ShellSort();
        KthLargest kthLargest = new KthLargest();
        BucketSort bucketSort = new BucketSort();
        CountingSort countingSort = new CountingSort();

        // 归并排序
        int[] array = new int[]{6,5,4,7,4,3,2,1};
        mergeSort.mergeSort(array, array.length);
        System.out.println(Arrays.toString(array));

        // 快速排序
        array = new int[]{6,5,4,7,4,3,2,1};
        quickSort.quickSort(array, array.length);
        System.out.println(Arrays.toString(array));

        //桶排序
        System.out.println("桶排序：");
        array = new int[]{6,5,4,7,4,3,2,1};
        bucketSort.bucketSort(array, array.length);
        System.out.println(Arrays.toString(array));

        array = Utils.generateArray(20);
        System.out.println("原数组: " + Arrays.toString(array));
        bucketSort.bucketSort(array, array.length);
        System.out.println("排序后: " + Arrays.toString(array));

        // 计数排序
        System.out.println("计数排序");
        array = Utils.generateArray(20,5);
        System.out.println("原数组: " + Arrays.toString(array));
        countingSort.countingSort(array, array.length);
        System.out.println("排序后: " + Arrays.toString(array));

        System.out.println("归并排序、快速排序、希尔排序、桶排序性能比较：");

        for(int i = 0; i < 3; i ++){
            array = Utils.generateArray(1000000);
            long startTime = System.currentTimeMillis();
            mergeSort.mergeSort(array, array.length);
            System.out.println("归并排序：" + (System.currentTimeMillis() - startTime) + "ms");

            array = Utils.generateArray(1000000);
            startTime = System.currentTimeMillis();
            quickSort.quickSort(array, array.length);
            System.out.println("快速排序：" + (System.currentTimeMillis() - startTime) + "ms");

            array = Utils.generateArray(1000000);
            startTime = System.currentTimeMillis();
            shellSort.shellSort(array, array.length);
            System.out.println("希尔排序：" + (System.currentTimeMillis() - startTime) + "ms");

            array = Utils.generateArray(1000000);
            startTime = System.currentTimeMillis();
            bucketSort.bucketSort(array, array.length);
            System.out.println("桶排序：  " + (System.currentTimeMillis() - startTime) + "ms");
            System.out.println();

        }

        // kthLargest
        array = new int[]{3,2,1,5,6,4};
        System.out.println(kthLargest.findKthLargest(array,2));

        System.out.println(kthLargest.findKthLargest_heap(array,2));


    }
}
