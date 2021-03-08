package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * leetcode 62. Unique Paths
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 *
 *   The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner
 * of the grid (marked 'Finish' in the diagram below).
 *
 * How many possible unique paths are there?
 *
 * Above is a 7 x 3 grid. How many possible unique paths are there?
 *
 * Note: m and n will be at most 100.
 *
 * Example 1:
 *   Input: m = 3, n = 2
 *   Output: 3
 *   Explanation:
 *   From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 *     1. Right -> Right -> Down
 *     2. Right -> Down -> Right
 *     3. Down -> Right -> Right
 * Example 2:
 *   Input: m = 7, n = 3
 *   Output: 28
 */
public class UniquePaths {
    /**
     * 状态方程： dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
     *
     * 时间复杂度： O(n * m)
     * 空间复杂度： O(n * m)
     */
    public static int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0) {
                    dp[i][j] = dp[i - 1][j];
                }
                if (j > 0) {
                    dp[i][j] += dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 空间优化：滚动数组
     *
     * 时间复杂度： O(m * n)
     * 空间复杂度： O(n)
     */
    public static int uniquePaths_2(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }

        int[] dp = new int[n];
        dp[0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j > 0) {
                    dp[j] += dp[j - 1];
                }
            }
        }
        return dp[n - 1];
    }

    /**
     * 数学解法：左上角到右下角需要向下走 m-1 步，向右走 n-1 步，总共移动 (m+n-2) 步
     *
     *   总次数 count = C(m-1, m+n-2)
     */

    /**
     * test
     */
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(UniquePaths.class, Arrays.asList(
                Arrays.asList(3, 2),
                Arrays.asList(7, 3)
        ));
    }
}
