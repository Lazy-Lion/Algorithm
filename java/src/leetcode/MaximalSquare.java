package leetcode;

/**
 * leetcode 221
 */
public class MaximalSquare {
    /**
     * 动态规划
     * dp[i][j] 表示以 (i,j) 为右下角的最大正方形边长
     *
     * 通过画图分析得出 dp[i][j] = k, 必须满足 martix(i,j) == 1 and dp[i][j-1] >= k-1 and dp[i-1][j-1] >= k-1 and dp[i-1][j] >= k-1
     *
     * => dp[i][j] =
     *              if martix(i,j) == 0: then 0
     *              if martix(i,j) == 1: min(dp[i][j-1], dp[i-1][j-1], dp[i-1][j]) + 1
     *
     * time complexity: O(n * m)
     * space complexity: O(n * m)
     */
    public static int maximalSquare(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[][] dp = new int[n + 1][m + 1];
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '0') {
                    dp[i + 1][j + 1] = 0;
                } else {
                    // matrix[i][j] == '1'
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j], dp[i][j]);
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j + 1]) + 1;
                    max = Math.max(max, dp[i + 1][j + 1]);
                }
            }
        }
        return max * max;
    }

    public static void main(String[] args) {
        System.out.println(
                maximalSquare(new char[][]{
                        new char[]{'1', '0', '1', '0', '0'},
                        new char[]{'1', '0', '1', '1', '1'},
                        new char[]{'1', '1', '1', '1', '1'},
                        new char[]{'1', '0', '0', '1', '0'}
                })
        );
    }
}
