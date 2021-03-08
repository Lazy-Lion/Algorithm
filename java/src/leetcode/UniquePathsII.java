package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * leetcode 63. Unique Paths II
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 *
 *   The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner
 * of the grid (marked 'Finish' in the diagram below).
 *
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
 *
 * Note: m and n will be at most 100.
 *
 * Example:
 *   Input:
 *   [
 *     [0,0,0],
 *     [0,1,0],
 *     [0,0,0]
 *   ]
 *   Output: 2
 *   Explanation:
 *   There is one obstacle in the middle of the 3x3 grid above.
 *   There are two ways to reach the bottom-right corner:
 *   1. Right -> Right -> Down -> Down
 *   2. Down -> Down -> Right -> Right
 */
public class UniquePathsII {
    /**
     * 状态方程：
     *     if grid[i][j] == 1, dp[i][j] = 0
     *     if grid[i][j] == 0, dp[i][j] = dp[i-1][j] + dp[i][j-1]
     *
     * 时间复杂度：O(n * m)
     * 空间复杂度：O(n * m)
     */
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int n = obstacleGrid.length;
        int m = obstacleGrid[0].length;

        // 起点就有障碍物，直接返回
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }

        int[][] dp = new int[n][m];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (obstacleGrid[i][j] == 0) {
                    if (i > 0) {
                        dp[i][j] = dp[i - 1][j];
                    }
                    if (j > 0) {
                        dp[i][j] += dp[i][j - 1];
                    }
                }
            }
        }
        return dp[n - 1][m - 1];
    }

    /**
     * 空间优化：滚动数组
     *
     * 时间复杂度：O(n * m)
     * 空间复杂度：O(m)
     */
    public static int uniquePathsWithObstacles_2(int[][] obstacleGrid) {
        int n = obstacleGrid.length;
        int m = obstacleGrid[0].length;

        if (obstacleGrid[0][0] == 1) {
            return 0;
        }
        int[] dp = new int[m];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (obstacleGrid[i][j] == 0) {
                    if (j > 0) {
                        dp[j] += dp[j - 1];
                    }
                } else {
                    dp[j] = 0;
                }
            }
        }
        return dp[m - 1];
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[][]{{1, 0}});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[][]{{0, 1}});
        params.add(param);

        Utils.testStaticMethod(UniquePathsII.class, params);
    }


}
