package dynamicprogramming;

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
public class UniquePathsII63 {
	public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
		int m = obstacleGrid.length;
		if(m <= 0) return 0;

		int n = obstacleGrid[0].length;
		if(n <= 0) return 0;

		int[][] dp = new int[m][n];

		boolean obstacle = false;
		for(int i = 0; i < m; i ++) {
			if(!obstacle && obstacleGrid[i][0] == 1)
				obstacle = true;
			dp[i][0] = obstacle ? 0 : 1;
		}

		obstacle = false;
		for(int j = 0; j < n; j ++) {
			if(!obstacle && obstacleGrid[0][j] == 1)
				obstacle = true;
			dp[0][j] = obstacle ? 0 : 1;
		}

		for(int i = 1; i < m; i ++) {
			for(int j = 1; j < n; j ++) {
				dp[i][j] = obstacleGrid[i][j] == 1 ? 0 : dp[i - 1][j] + dp[i][j - 1];
			}
		}

		return dp[m - 1][n - 1];
	}

	public static void main(String[] args) {
		System.out.println(uniquePathsWithObstacles(new int[][]{{0,0,0}, {0,1,0}, {0,0,0}}));
		System.out.println(uniquePathsWithObstacles(new int[][]{{1, 0}}));
	}
}
