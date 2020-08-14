package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 658: Find K Closest Elements
 *
 *   Given a sorted array arr, two integers k and x, find the k closest elements to x in the array. The result should also be sorted
 * in ascending order. If there is a tie, the smaller elements are always preferred.
 *
 * Example 1:
 *   Input: arr = [1,2,3,4,5], k = 4, x = 3
 *   Output: [1,2,3,4]
 * Example 2:
 *   Input: arr = [1,2,3,4,5], k = 4, x = -1
 *   Output: [1,2,3,4]
 *
 * Constraints:
 *   1 <= k <= arr.length
 *   1 <= arr.length <= 10^4
 *   Absolute value of elements in the array and x will not exceed 10^4
 */
public class FindKClosestElements {
    /**
     * binary search
     * time complexity: O(k + logn)
     * space complexity: O(1) -- ignore return list
     */
    public static List<Integer> findClosestElements(int[] array, int k, int x) {
        List<Integer> result = new ArrayList<>();

        int n = array.length;
        int index = findFirstGreaterOrEqual(array, x);
        if (index == -1) {
            for (int i = 0; i < k; i++) {
                result.add(array[i]);
            }
        } else {
            int i = index - 1, j = index;
            while ((i >= 0 || j < n) && k > 0) {
                if (i >= 0 && j < n) {
                    if (x - array[i] <= array[j] - x) {
                        i--;
                    } else {
                        j++;
                    }
                } else if (i >= 0) {
                    i--;
                } else {
                    j++;
                }
                k--;
            }

            i++;
            j--;
            while (i <= j) {
                result.add(array[i++]);
            }
        }
        return result;
    }

    /**
     * find the first value greater than or equal to x
     * @return the index of value
     */
    private static int findFirstGreaterOrEqual(int[] array, int x) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >>> 1);

            if (array[middle] < x) {
                left = middle + 1;
            } else if (array[middle] >= x) {
                right = middle - 1;
            }
        }
        return left < array.length && array[left] >= x ? left : -1;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 2, 3, 4, 5});
        param.add(4);
        param.add(3);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{1, 2, 3, 4, 5});
        param.add(4);
        param.add(-1);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{1, 1, 1, 10, 10, 10});
        param.add(1);
        param.add(9);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{1, 1});
        param.add(1);
        param.add(1);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{0, 1, 2, 2, 2, 3, 6, 8, 8, 9});
        param.add(5);
        param.add(9);
        params.add(param);

        Utils.testStaticMethod(FindKClosestElements.class
                , new HashSet<String>() {
                    {
                        add("findClosestElements");
                    }
                }, params);
    }
}
