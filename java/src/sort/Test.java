package sort;

import util.Utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * test
 */
public class Test {

    public static void main(String[] args) {
        int[] array = new int[]{6, 5, 4, 4, 3, 2, 1};
        BubbleSort.bubbleSort(array, array.length);
        System.out.println("bubble sort   : " + Arrays.toString(array));

        array = new int[]{6, 5, 4, 4, 3, 2, 1};
        InsertionSort.insertionSort(array, array.length);
        System.out.println("insertion sort: " + Arrays.toString(array));

        array = new int[]{6, 5, 4, 4, 3, 2, 1};
        SelectionSort.selectionSort(array, array.length);
        System.out.println("selection sort: " + Arrays.toString(array));

        array = new int[]{6, 5, 4, 4, 3, 2, 1};
        ShellSort.shellSort(array, array.length);
        System.out.println("shell sort    : " + Arrays.toString(array));


        System.out.println("比较插入排序和冒泡排序性能: 多次执行，插入排序明显优于冒泡排序");
        for (int k = 0; k < 3; k++) {
            List<int[]> list = new LinkedList<>();

            for (int i = 0; i < 10000; i++) {
                int[] a = Utils.generateArray(300);
                list.add(a);
            }

            long startTime = System.currentTimeMillis();

            for (int[] a : list) {
                BubbleSort.bubbleSort(a, a.length);
            }

            System.out.println("冒泡排序时间：" + (System.currentTimeMillis() - startTime) + "ms");


            list = new LinkedList<>();

            for (int i = 0; i < 10000; i++) {
                int[] a = Utils.generateArray(300);
                list.add(a);
            }

            startTime = System.currentTimeMillis();

            for (int[] a : list) {
                InsertionSort.insertionSort(a, a.length);
            }

            System.out.println("插入排序时间：" + (System.currentTimeMillis() - startTime) + "ms");
            System.out.println();
        }

        System.out.println("比较插入排序和希尔排序性能: ");

        for (int i = 0; i < 5; i++) {
            array = Utils.generateArray(100000);

            long startTime = System.currentTimeMillis();
            InsertionSort.insertionSort(array, array.length);
            System.out.println("插入排序时间：" + (System.currentTimeMillis() - startTime) + "ms");


            array = Utils.generateArray(100000);  // 数据量达到百万级别，插入排序已无法短时间完成

            startTime = System.currentTimeMillis();
            ShellSort.shellSort(array, array.length);
            System.out.println("希尔排序时间：" + (System.currentTimeMillis() - startTime) + "ms");
            System.out.println();
        }
    }
}
