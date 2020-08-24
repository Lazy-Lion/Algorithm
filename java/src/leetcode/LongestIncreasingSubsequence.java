package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 300. Longest Increasing Subsequence
 *   Given an unsorted array of integers, find the length of longest increasing subsequence.
 * Example:
 *   Input: [10,9,2,5,3,7,101,18]
 *   Output: 4
 *   Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 *
 * Note:
 *   There may be more than one LIS combination, it is only necessary for you to return the length.
 *   Your algorithm should run in O(n2) complexity.
 *
 * Follow up: Could you improve it to O(n log n) time complexity?
 */
public class LongestIncreasingSubsequence {
    /**
     * 动态规划：
     *  1. 最优子结构
     *  2. 重复子问题
     *  3. 无后效性
     * 状态方程：
     *   f(i) = max { nums[j] < nums[i] ? f(j) : 0 } + 1  j ∈ [0,i)
     *     f(i)  -- 包含 nums[i]的最长递增子序列长度
     *
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(n)
     */
    public static int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if (n <= 1) return n;

        int result = 0;

        int[] dp = new int[n];
        dp[0] = 1;

        for (int i = 1; i < n; i++) {
            int max = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < nums[i])
                    max = Math.max(dp[j], max);
            }
            dp[i] = max + 1;
            result = Math.max(dp[i], result);
        }

        return result;
    }

    /**
     * 时间复杂度: O(nlogn)
     * f(i)  -- 长度为 i的子序列结尾数值(多个取最小); i大小不确定，从 1开始往后赋值，最大的 i即为最长的 length
     *       -- f(i)是一个递增序列
     * f(i) 赋值过程:
     *     f(1) = nums[0]
     *     len = 1
     *     j ← 1 to n - 1
     *       if nums[j] > f(len):   nums[j]可以直接放置到当前最大长度后面
     *          len ++
     *          f(len) = nums[j]
     *       else if nums[j] < f(len):  nums[j]不可以直接放置到当前最大长度后面，替换已赋值的f(i),使f(i)多个时取最小
     *          k = binarySearch(dp) from 0 to len
     *          if exists dp[k] < nums[j] < dp[k + 1]
     *              dp[k + 1] = nums[j]
     *          else
     *              dp[0] = nums[j]
     */
    public static int lengthOfLIS_2(int[] nums) {
        int n = nums.length;
        if (n <= 1) return n;

        int[] dp = new int[n + 1];
        int len = 1;
        dp[1] = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] > dp[len]) {
                dp[++len] = nums[i];
            } else if (nums[i] < dp[len]) {
                int l = 0;
                int r = len;
                while (l < r) {
                    int m = l + (r - l) / 2;
                    if (dp[m] >= nums[i])
                        r = m;
                    else
                        l = m + 1;
                }
                dp[l] = nums[i];
            }
        }

        for (int i = 1; i < len + 1; i++) {  // 输出最长递增子序列
            System.out.print(dp[i] + " ");
        }
        System.out.println();
        return len;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[]{10, 9, 2, 5, 3, 7, 101, 18});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{4, 10, 4, 3, 8, 9});
        params.add(param);

        Utils.testStaticMethod(LongestIncreasingSubsequence.class
                , new HashSet<String>() {
                    {
                        add("lengthOfLIS");
                        add("lengthOfLIS_2");
                    }
                }, params);
    }
}
