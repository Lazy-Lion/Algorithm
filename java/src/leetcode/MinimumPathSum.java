package leetcode;

/**
 * leetcode 64
 */
public class MinimumPathSum {
    /**
     * 状态方程：
     *  dp[i][j] = min(dp[i-1][j] + grid[i][j], dp[i][j-1] + grid[i][j])
     *
     * @see UniquePaths
     *
     * time coplexity: O(n * m)
     * space complexity: O(m)
     */
    public static int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[] dp = new int[m];

        int min;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                min = 0;
                if (i > 0 && j > 0) {
                    min = Math.min(dp[j], dp[j - 1]);
                } else if (i > 0) {
                    min = dp[j];
                } else if (j > 0) {
                    min = dp[j - 1];
                }
                dp[j] = min + grid[i][j];
            }
        }
        return dp[m - 1];
    }

    public static void main(String[] args) {
        System.out.println(minPathSum(new int[][]{
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        }));
    }

}
