package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * leetcode 416: Partition Equal Subset Sum
 *
 * Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
 *
 * Note:
 *   Each of the array element will not exceed 100.
 *   The array size will not exceed 200.
 *
 * Example 1:
 *   Input: [1, 5, 11, 5]
 *   Output: true
 *   Explanation: The array can be partitioned as [1, 5, 5] and [11].
 *
 * Example 2:
 *   Input: [1, 2, 3, 5]
 *   Output: false
 *   Explanation: The array cannot be partitioned into equal sum subsets.
 */
public class PartitionEqualSubsetSum {
    /**
     * 0/1 背包问题 {@link dynamicprogramming.Knapsack01}
     *
     * way 1 :
     * time complexity: O(n * v)
     * space complexity: O(v)
     *     v - sum of nums[]
     */
    public static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        if ((sum & 1) == 1) { // sum % 2 == 1
            return false;
        }

        sum = sum >>> 1;

        int[] dp = new int[sum + 1];
        for (int j = 0; j <= sum; j++) {
            dp[j] = 0;
        }

        for (int i = 1; i <= nums.length; i++) {
            for (int j = sum; j >= nums[i - 1]; j--) {
                dp[j] = Math.max(dp[j], dp[j - nums[i - 1]] + nums[i - 1]);
            }
        }

        return dp[sum] == sum;
    }

    /**
     * way 2 : optimize {@link #canPartition(int[])} in space
     */
    public static boolean canPartition_2(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        if ((sum & 1) == 1) { // sum % 2 == 1
            return false;
        }

        sum = sum >>> 1;

        boolean[] dp = new boolean[sum + 1];
//        Arrays.fill(dp, false);  // java: boolean[] is false by default
        dp[0] = true;

        for (int i = 1; i <= nums.length; i++) {
            for (int j = sum; j >= nums[i - 1]; j--) {
                if (dp[sum] == true) { // early termination
                    return true;
                }
                dp[j] = dp[j] || dp[j - nums[i - 1]];
            }
        }
        return dp[sum];
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 5, 11, 5});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{1, 2, 3, 5});
        params.add(param);

        Utils.testStaticMethod(PartitionEqualSubsetSum.class, params);
    }
}
