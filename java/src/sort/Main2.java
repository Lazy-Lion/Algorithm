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

        // 归并排序
        int[] array = new int[]{6,5,4,7,4,3,2,1};
        mergeSort.mergeSort(array, array.length);
        System.out.println(Arrays.toString(array));

        // 快速排序
        array = new int[]{6,5,4,7,4,3,2,1};
        quickSort.quickSort(array, array.length);
        System.out.println(Arrays.toString(array));

        System.out.println("归并排序、快速排序、希尔排序排序性能比较：");

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
            System.out.println();

        }
    }
}
