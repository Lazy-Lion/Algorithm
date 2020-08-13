package heap;

import util.Utils;

import java.util.Arrays;

/**
 * test
 */
public class Test {
    public static void main(String[] args){
        int[] array = new int[]{8,-1,3,8,7,2,5,6,6};
        HeapSort.heapSort(array, array.length);
        System.out.println(Arrays.toString(array));

        array = Utils.generateArray(100);
        HeapSort.heapSort(array, array.length);
        System.out.println(Arrays.toString(array));
    }
}
