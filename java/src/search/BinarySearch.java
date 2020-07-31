package search;


/**
 * 二分查找
 * 简单的二分查找及其实现参照： iteration.BinarySearchImp
 *
 * assume : ascending order
 */
public class BinarySearch {

    /**
     * 查找第一个值等于给定值的元素
     * @param a sorted array
     * @param n length
     * @param value search value
     * @return index of value
     */
    public static int binarySearchFirst(int[] a, int n, int value){
        int left = 0;
        int right = n - 1;

        while(left <= right){
            int middle = left + ( (right - left) >> 1 );   // note: 运算符优先级

            if(a[middle] >= value)
                right = middle - 1;
            else if(a[middle] < value)
                left = middle + 1;
        }

        if(left < n && a[left] == value)
            return left;
        else
            return -1;
    }

    /**
     * 查找最后一个等于给定值的元素
     * @param a
     * @param n
     * @param value
     * @return index of value
     */
    public static int binarySearchLast(int[] a, int n, int value){
        int left = 0;
        int right = n - 1;

        while(left <= right){
            int middle = left + ( (right - left) >> 1 );

            if(a[middle] > value)
                right = middle - 1;
            else if(a[middle] <= value)
                left = middle + 1;
        }

        if(right >= 0 && a[right] == value)
            return right;
        else
            return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素
     * @param a
     * @param n
     * @param value
     * @return index of value
     */
    public static int binarySearchFirstGreater(int[] a, int n, int value){
        int left = 0;
        int right = n - 1;

        while(left <= right){
            int middle = left + ( (right - left) >> 1 );

            if(a[middle] < value)
                left = middle + 1;
            else if(a[middle] >= value)
                right = middle - 1;
        }

        if(left < n && a[left] >= value)
            return left;
        else
            return -1;
    }

    /**
     * 查找最后一个小于等于给定值的元素
     * @param a
     * @param n
     * @param value
     * @return index of value
     */
    public static int binarySearchLastLess(int[] a, int n, int value){
        int left = 0;
        int right = n - 1;

        while(left <= right ){
            int middle = left + ((right - left ) >> 1);

            if(a[middle] <= value)
                left = middle + 1;
            else if(a[middle] > value)
                right = middle - 1;
        }

        if(right >= 0 && a[right] <= value)
            return right;
        else
            return -1;
    }
}
