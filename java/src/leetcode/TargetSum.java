package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 494: Target Sum
 *
 * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.
 *
 * Find out how many ways to assign symbols to make sum of integers equal to target S.
 *
 * Example 1:
 *   Input: nums is [1, 1, 1, 1, 1], S is 3.
 *   Output: 5
 *   Explanation:
 *     -1+1+1+1+1 = 3
 *     +1-1+1+1+1 = 3
 *     +1+1-1+1+1 = 3
 *    +1+1+1-1+1 = 3
 *    +1+1+1+1-1 = 3
 *
 * There are 5 ways to assign symbols to make the sum of nums be target 3.
 *
 * Constraints:
 *   The length of the given array is positive and will not exceed 20.
 *   The sum of elements in the given array will not exceed 1000.
 *   Your output answer is guaranteed to be fitted in a 32-bit integer.
 */
public class TargetSum {
    /**
     * way1 : 0/1 knapsack {@link dynamicprogramming.Knapsack01}
     * DP
     * 状态方程: dp[i][j]： 前i个元素，组合成j有dp[i][j]种方式
     *
     *    dp[i][j] =  dp[i-1][j-nums[i-1]] + dp[i-1][j+nums[i-1]]
     *
     * time complexity: O(n * m) - n: length of nums[], m - sum of nums[]
     * space complexity: O(n * m)
     */
    public static int findTargetSumWays(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (Math.abs(target) > sum) { // target 可能为负
            return 0;
        }

        /**
         * 若元素全部添加'-',则和为-sum
         * => 和的取值范围[-sum, sum]
         * => 数组整体向右移动sum位 => int[] dp = new int[2*sum + 1];
         * 由于数组元素为非负整数，所以数组的任意子集和的取值范围为[-sum,sum]
         */
        int[][] dp = new int[nums.length + 1][2 * sum + 2]; // default 0
        dp[0][0 + sum] = 1; // 0

        for (int i = 1; i <= nums.length; i++) {
            for (int j = dp[0].length - 1; j >= 0; j--) {
                // +
                if (j >= nums[i - 1]) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
                // -
                if (j + nums[i - 1] < dp[0].length) {
                    dp[i][j] += dp[i - 1][j + nums[i - 1]];
                }
            }
        }

        return dp[nums.length][sum + target];
    }

    /**
     * way2 : backtrack
     * time complexity: O(2^n)
     * space complexity: O(n)
     */
    public static int findTargetSumWays_2(int[] nums, int target) {
        return backtrack(nums, 0, target);
    }

    private static int backtrack(int[] nums, int start, int target) {
        if (start == nums.length) {
            return target == 0 ? 1 : 0;
        }

        return backtrack(nums, start + 1, target - nums[start])
                + backtrack(nums, start + 1, target + nums[start]);
    }

    /**
     * way3 : optimize {@link #findTargetSumWays_2(int[], int)} with memoization
     */
    private static Map<String, Integer> map; // key - start + "," + target, value - numbers

    public static int findTargetSumWays_3(int[] nums, int target) {
        map = new HashMap<>();
        return backtrack2(nums, 0, target);
    }

    private static int backtrack2(int[] nums, int start, int target) {
        if (start == nums.length) {
            return target == 0 ? 1 : 0;
        }

        int next = start + 1;
        int count = 0;

        int remaining = target - nums[start];
        String key = next + "," + remaining;
        count += (map.containsKey(key) ? map.get(key) : backtrack2(nums, next, remaining));

        remaining = target + nums[start];
        key = next + "," + remaining;
        count += (map.containsKey(key) ? map.get(key) : backtrack2(nums, next, remaining));

        map.put(start + "," + target, count);
        return count;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 1, 1, 1, 1});
        param.add(3);
        params.add(param);

        Utils.testStaticMethod(TargetSum.class
                , new HashSet<String>() {
                    {
                        add("findTargetSumWays");
                        add("findTargetSumWays_2");
                        add("findTargetSumWays_3");
                    }
                }, params);
    }
}
