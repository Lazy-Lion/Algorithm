package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * leetcode 287: Find the Duplicate Number
 *
 *   Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate
 * number must exist. Assume that there is only one duplicate number, find the duplicate one.
 *
 * Example 1:
 *   Input: [1,3,4,2,2]
 *   Output: 2
 * Example 2:
 *   Input: [3,1,3,4,2]
 *   Output: 3
 *
 * Note:
 *   You must not modify the array (assume the array is read only).
 *   You must use only constant, O(1) extra space.
 *   Your runtime complexity should be less than O(n^2).
 *   There is only one duplicate number in the array, but it could be repeated more than once.
 */
public class FindTheDuplicateNumber {
    /**
     * only one duplicate number => cycle detection
     * @see LinkedListCycleII
     *
     * time complexity: O(n)
     * space complexity: O(1)
     */
    public static int findDuplicate(int[] nums) {
        int n = nums.length;

        assert n >= 2;

        int p1 = 0;
        int p2 = 0;
        do {
            p1 = nums[p1];
            p2 = nums[nums[p2]];  // must exist cycle
        } while (p1 != p2);

        // find the start of the cycle
        p1 = 0;
        while (p1 != p2) {
            p1 = nums[p1];
            p2 = nums[p2];
        }
        return p1;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 3, 4, 2, 2});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{3, 1, 3, 4, 2});
        params.add(param);

        Utils.testStaticMethod(FindTheDuplicateNumber.class, params);
    }
}
