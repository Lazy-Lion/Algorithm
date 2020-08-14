package iteration;

/**
 * 二分查找：
 *   局限性：
 *     1. 二分查找针对的是有序集合；
 *     2. 二分查找依赖顺序表结构(数组)，由于数组需要连续的空间分配，所以当数据量太大时，需要分配连续的空间就比较吃力，此时
 *          二分查找就不适用了；
 *     3. 数据量太小时，二分查找的优势就不明显。
 *
 * time complexity： O(logn)
 * space complexity: O(1)
 *
 */
public class BinarySearchImp {

    /**
     * 二分查找
     * @param array  sorted array
     * @param keyword value to find
     * @return index of the value, if not found return -1
     */
    public static int binarySearch(String[] array, String keyword) {

        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {   // 循环条件 left <= right

            // int middle = (left + right) / 2; // 可能会存在逸出问题
            int middle = left + ((right - left) >>> 1);  // 偶数个数据，取中间靠左数据

            if (array[middle].equals(keyword)) {
                return middle;
            } else if (array[middle].compareTo(keyword) > 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return -1;
    }
}
