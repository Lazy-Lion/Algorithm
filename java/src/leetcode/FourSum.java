package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * leetcode 18 : 4Sum
 * Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that
 * a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
 *
 * Note:
 * The solution set must not contain duplicate quadruplets.
 *
 * Example:
 * Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.
 * A solution set is:
 * [
 *   [-1,  0, 0, 1],
 *   [-2, -1, 1, 2],
 *   [-2,  0, 0, 2]
 * ]
 */
public class FourSum {

    /**
     * @see ThreeSum
     *
     * ignore sorting:
     *   time complexity: O(n^3)
     *   space complexity: O(1)
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        int len = nums.length;

        List<List<Integer>> result = new ArrayList<>();
        if (len < 4)
            return result;

        Arrays.sort(nums);
        for (int i = 0; i < len - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;

            for (int j = i + 1; j < len - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1])
                    continue;

                int value = target - nums[i] - nums[j];
                int left = j + 1;
                int right = len - 1;
                while (left < right) {
                    int sum = nums[left] + nums[right];
                    if (value == sum) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left++] == nums[left]) ;
                        while (left < right && nums[right--] == nums[right]) ;
                    } else if (value > sum) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
    }
}
