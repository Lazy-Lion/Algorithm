package iteration;

public class BinarySearchImp {

    /**
     * 二分查找
     * 时间复杂度： O(logn)
     * @param array  sorted array
     * @param keyword value to find
     * @return index of the value, if not found return -1
     */
    public static int binarySearch(String[] array, String keyword){

        if( array == null || array.length == 0){
            return -1;
        }

        int left = 0;
        int right = array.length - 1;

        while(left <= right){   // 循环条件 left <= right

            // int middle = (left + right) / 2; // 可能会存在逸出问题
            int middle = left + (right - left) / 2;  // 偶数个数据，取中间靠左数据

            if(array[middle].equals(keyword)) {
                return middle;
            }else if(array[middle].compareTo(keyword) > 0){
                right = middle - 1;
            }else{
                left = middle + 1;
            }
        }

        return -1;
    }
}
