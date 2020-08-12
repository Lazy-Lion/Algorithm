package heap;

import util.Utils;

import java.util.Arrays;

/**
 * test
 */
public class Test {
    public static void main(String[] args){
        Integer[] array = new Integer[]{8,-1,3,8,7,2,5,6,6};
        HeapSort.sort(array);
        System.out.println(Arrays.toString(array));

        int[] a = Utils.generateArray(100);
        array = Arrays.stream(a).boxed().toArray(Integer[] :: new);
        HeapSort.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
