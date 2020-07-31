package search;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * test
 */
public class Main {
    public static void main(String[] args){
        BinarySearch binarySearch = new BinarySearch();

        int[] array = new int[]{0, 1, 2, 2, 3, 3, 4, 5, 6, 6, 6, 7, 9, 9, 9, 10, 14, 15, 16, 18};
        System.out.println("数组：" + Arrays.toString(array));

        System.out.println("第一个等于2的index: " + binarySearch.binarySearchFirst(array, array.length, 2));
        System.out.println("最后一个等于2的index: " + binarySearch.binarySearchLast(array, array.length, 2));
        System.out.println("第一个大于等于2的index: " + binarySearch.binarySearchFirstGreater(array,array.length,2));
        System.out.println("最后一个小于等于2的index: " + binarySearch.binarySearchLastLess(array,array.length, 2));

        System.out.println("第一个等于9的index: " + binarySearch.binarySearchFirst(array, array.length, 9));
        System.out.println("最后一个等于9的index: " + binarySearch.binarySearchLast(array, array.length, 9));
        System.out.println("第一个大于等于9的index: " + binarySearch.binarySearchFirstGreater(array,array.length,9));
        System.out.println("最后一个小于等于9的index: " + binarySearch.binarySearchLastLess(array,array.length, 9));

        System.out.println("第一个大于等于8的index: " + binarySearch.binarySearchFirstGreater(array,array.length,8));
        System.out.println("最后一个小于等于8的index: " + binarySearch.binarySearchLastLess(array,array.length, 8));


        RotatedSortedArray rotatedSortedArray = new RotatedSortedArray();
        array = new int[]{4,5,6,7,0,1,2};
        System.out.println(rotatedSortedArray.search(array,0));
        System.out.println(rotatedSortedArray.search(array,4));
        System.out.println(rotatedSortedArray.search(array,7));
        System.out.println(rotatedSortedArray.search(array,3));

        array = new int[]{5,1,2,3,4};
        System.out.println(rotatedSortedArray.search(array,1));


        //skip list
        System.out.println("跳表(skip list):");
        SkipList skipList = new SkipList();

        for(int i = 0; i < 10; i ++){
            skipList.insert(i);
        }
        System.out.println(skipList.getSize());
        skipList.printAll();
        System.out.println(skipList.delete(1));
        System.out.println(skipList.delete(9));
        System.out.println(skipList.delete(12));
        System.out.println(skipList.getSize());
        skipList.printAll();
        System.out.println(skipList.find(3).getData());
        System.out.println(skipList.find(1));


        skipList = new SkipList();

        for(int i = 0; i < 10000; i ++){
            skipList.insert(i);
        }
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 5000; i ++){
            skipList.find((int)(Math.random() * 10000));
        }
        System.out.println("skip list search: " + (System.currentTimeMillis() - startTime) + "ms");

        LinkedList<Integer> list = new LinkedList<>();
        for(int i = 0; i < 10000; i ++){
            list.add(i);
        }
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 5000; i ++){
            list.get((int)(Math.random() * 10000));
        }
        System.out.println("linked list search: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
