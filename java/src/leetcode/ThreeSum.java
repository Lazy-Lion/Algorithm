package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * leetcode 15 : 3Sum
 *
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0 ?
 * Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 * The solution set must not contain duplicate triplets.
 *
 * Example:
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 *      A solution set is:
 *      [
 *        [-1, 0, 1],
 *        [-1, -1, 2]
 *     ]
 */
public class ThreeSum {

    /**
     * ignore sorting:
     *   time complexity: O(n^2)
     *   space complexity: O(1)
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        if (nums.length < 3) {
            return result;
        }

        Arrays.sort(nums); // for duplicate removal

        int len = nums.length;
        for (int i = 0; i < len - 2; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) { // for duplicate removal
                continue;
            }

            int remaining = -nums[i];

            /**
             * compare to {@link TwoSum}
             * sorted or unsorted
             */
            int left = i + 1;
            int right = len - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum < remaining) {
                    left++;
                } else if (sum > remaining) {
                    right--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // for duplicate removal
                    while (left < right && nums[left++] == nums[left]) ;
                    while (right > left && nums[right--] == nums[right]) ;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(threeSum(new int[]{0, 0, 0, 0}));
        System.out.println(threeSum(new int[]{-2, 0, 1, 1, 2}));
    }
}
