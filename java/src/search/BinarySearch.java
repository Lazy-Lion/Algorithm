package search;


/**
 * 二分查找 (有序集合)
 * 简单的二分查找及其实现： {@link iteration.BinarySearchImp}
 *
 * assume : ascending order
 */
public class BinarySearch {

    /**
     * 查找第一个值等于给定值的元素
     * @param array sorted array
     * @param n length
     * @param value search value
     * @return index of value
     */
    public static int binarySearchFirst(int[] array, int n, int value) {
        int left = 0;
        int right = n - 1;

        while (left <= right) {
            int middle = left + ((right - left) >> 1);   // note: 运算符优先级

            if (array[middle] >= value) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        if (left < n && array[left] == value) {
            return left;
        } else {
            return -1;
        }
    }

    /**
     * 查找最后一个等于给定值的元素
     * @return index of value
     */
    public static int binarySearchLast(int[] array, int n, int value) {
        int left = 0;
        int right = n - 1;

        while (left <= right) {
            int middle = left + ((right - left) >> 1);

            if (array[middle] > value) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        if (right >= 0 && array[right] == value) {
            return right;
        } else {
            return -1;
        }
    }

    /**
     * 查找第一个大于等于给定值的元素
     * @return index of value
     */
    public static int binarySearchFirstGreater(int[] array, int n, int value) {
        int left = 0;
        int right = n - 1;

        while (left <= right) {
            int middle = left + ((right - left) >> 1);

            if (array[middle] < value) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        if (left < n && array[left] >= value) {
            return left;
        } else {
            return -1;
        }
    }

    /**
     * 查找最后一个小于等于给定值的元素
     * @return index of value
     */
    public static int binarySearchLastLess(int[] array, int n, int value) {
        int left = 0;
        int right = n - 1;

        while (left <= right) {
            int middle = left + ((right - left) >> 1);

            if (array[middle] <= value) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        if (right >= 0 && array[right] <= value) {
            return right;
        } else {
            return -1;
        }
    }
}
