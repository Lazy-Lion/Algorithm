package leetcode;

/**
 * leetcode 72
 */
public class EditDistance {
    /**
     * 状态方程：
     *   dp[i][j] 表示 word1[1,i] 和 word2[1,j] 转换的最小距离
     *
     *   dp[i][j] 取下列情况的最小值
     *      dp[i-1][j] + 1     (删除word1)
     *      dp[i][j-1] + 1     (删除word2)
     *      dp[i-1][j-1] + 1   (如果 word1[i] != word2[j]，替换操作)
     *      dp[i-1][j-1]       (如果 word1[i] == word2[j]，无需操作)
     *      dp[i][j-1] + 1     (插入word1)
     *      dp[i-1][j] + 1     (插入word2)
     *
     *   初始化： dp[0][i] = i, dp[i][0] = i;  只做删除操作
     *
     * time complexity: O(n * m)
     * space complexity: O(n * m)
     */
    public static int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        if (n == 0 || m == 0) {
            return n + m;
        }

        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= m; i++) {
            dp[0][i] = i;
        }
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
            }
        }
        return dp[n][m];
    }

    public static void main(String[] args) {
        System.out.println(minDistance("horse", "ros"));
    }
}
