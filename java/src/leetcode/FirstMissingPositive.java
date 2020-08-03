package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * leetcode 41 : First Missing Positive
 *
 * Given an unsorted integer array, find the smallest missing positive integer.
 *
 * Example 1:
 * Input: [1,2,0]
 * Output: 3
 *
 * Example 2:
 * Input: [3,4,-1,1]
 * Output: 2
 *
 * Example 3:
 * Input: [7,8,9,11,12]
 * Output: 1
 *
 * Note:
 * Your algorithm should run in O(n) time and uses constant extra space.
 */
public class FirstMissingPositive {

    /**
     * 思路1：数组 [0,n)存储对应的正整数值，使得 nums[i] == i + 1
     * time complexity： O(n)
     * space complexity: O(1)
     */
    public static int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i + 1) continue;

            while (nums[i] > 0 && nums[i] <= nums.length) {
                if (nums[nums[i] - 1] == nums[i])
                    break;

                Utils.swap(nums, i, nums[i] - 1); // swap
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1)
                return i + 1;
        }
        return nums.length + 1;
    }

    /**
     * {@link FirstMissingPositive#firstMissingPositive(int[])} 换种方式实现
     */
    public static int firstMissingPositive_2(int[] nums) {
        boolean notOutOfBound;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i + 1  // nums[i] is in the right location
                    || ((notOutOfBound = nums[i] > 0 && nums[i] <= nums.length) && nums[nums[i] - 1] == nums[i])) {  // have same value, no need to exchange
                continue;
            }

            if (notOutOfBound) {
                Utils.swap(nums, i, nums[i] - 1);
                i--; // offset i++
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1)
                return i + 1;
        }

        return nums.length + 1;   // array full
    }

    /**
     * 思路2：like {@link FirstMissingPositive#firstMissingPositive(int[])}，just mark, do not exchange actual data
     */
    public static int firstMissingPositive_3(int[] nums) {
        if (nums.length < 1)
            return 1;

        int invalidMark = nums.length + 1; // mark a invalid value, abs(invalidMark) must beyond the bounds
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0 || nums[i] > nums.length) {
                nums[i] = invalidMark;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            int value = Math.abs(nums[i]); // if nums[i] is negative, means existence value equal to (i+1)
            if (value == invalidMark) {  // value just has values in ([1, nums.length] ∪ {invalidMark})
                continue;
            }

            value = value - 1;
            if (nums[value] > 0) { // avoid duplicate mark
                nums[value] = -nums[value]; // mark negative
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0)
                return i + 1;
        }

        return nums.length + 1;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(FirstMissingPositive.class, Arrays.asList(
                Arrays.asList(new int[]{1, 2}),
                Arrays.asList(new int[]{1, 1}),
                Arrays.asList(new int[]{1, 2, 0}),
                Arrays.asList(new int[]{3, 4, -1, 1}),
                Arrays.asList(new int[]{7, 8, 9, 11, 12})
        ));
    }
}
