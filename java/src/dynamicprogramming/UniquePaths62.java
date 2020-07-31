package dynamicprogramming;

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
public class UniquePaths62 {
	/**
	 * 状态方程： dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
	 */
	public static int uniquePaths(int m, int n) {
		if(m <= 0 || n <= 0) return 0;

		int[][] dp = new int[m + 1][n + 1];

		for(int i = 1; i <= m; i ++) {
			dp[i][1] = 1;
		}

		for(int j = 1; j <= n; j ++) {
			dp[1][j] = 1;
		}

		for(int i = 2; i <= m; i ++) {
			for(int j = 2; j <= n; j ++) {
				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
			}
		}

		return dp[m][n];
	}

	/**
	 * test
	 */
	public static void main(String[] args) {
		System.out.println(uniquePaths(3,2));
		System.out.println(uniquePaths(7,3));
	}
}
