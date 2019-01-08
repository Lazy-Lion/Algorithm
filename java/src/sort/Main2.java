package sort;

import java.util.Arrays;

/**
 * test
 */
public class Main2 {
    public static void main(String[] args){
        MergeSort mergeSort = new MergeSort();

        int[] array = new int[]{6,5,4,4,3,2,1};
        mergeSort.mergeSort(array, array.length);
        System.out.println(Arrays.toString(array));
    }
}
