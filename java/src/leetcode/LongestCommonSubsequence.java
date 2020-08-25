package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * leetcode 1143. Longest Common Subsequence
 *
 * Given two strings text1 and text2, return the length of their longest common subsequence.
 *
 * A subsequence of a string is a new string generated from the original string with some characters(can be none) deleted without changing the relative order of the remaining characters. (eg, "ace" is a subsequence of "abcde" while "aec" is not). A common subsequence of two strings is a subsequence that is common to both strings.
 *
 * If there is no common subsequence, return 0.
 *
 * Example 1:
 *   Input: text1 = "abcde", text2 = "ace"
 *   Output: 3
 *   Explanation: The longest common subsequence is "ace" and its length is 3.
 * Example 2:
 *   Input: text1 = "abc", text2 = "abc"
 *   Output: 3
 *   Explanation: The longest common subsequence is "abc" and its length is 3.
 * Example 3:
 *   Input: text1 = "abc", text2 = "def"
 *   Output: 0
 *   Explanation: There is no such common subsequence, so the result is 0.
 * Constraints:
 *   1 <= text1.length <= 1000
 *   1 <= text2.length <= 1000
 *   The input strings consist of lowercase English characters only.
 */
public class LongestCommonSubsequence {

    /**
     * DP
     * 状态转移方程 dp[i][j] = dp[i-1][j-1] + 1                                   if text1[i] = text2[j]
     *                        max(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])     if text1[i] != text2[j]
     *
     *      dp[i][j]: text1的子串[0,i]与text2的子串[0,j]的最长公共子序列长度
     *
     * time complexity: O(m * n)
     * space complexity: O(m * n)
     *
     * {@link #longestCommonSubsequence_2(String, String)}: use sentinel to optimize code
     */
    public static int longestCommonSubsequence(String text1, String text2) {
        int m, n;

        if (text1 == null || text2 == null
                || (m = text1.length()) == 0 || (n = text2.length()) == 0) {
            return 0;
        }

        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    if (i > 0 && j > 0) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = 1;
                    }
                } else {
                    int max = 0;
                    if (i > 0) {
                        max = Math.max(max, dp[i - 1][j]);
                    }
                    if (j > 0) {
                        max = Math.max(max, dp[i][j - 1]);
                    }
                    if (i > 0 && j > 0) {
                        max = Math.max(max, dp[i - 1][j - 1]);
                    }
                    dp[i][j] = max;
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * optimize code {@link #longestCommonSubsequence(String, String)}
     */
    public static int longestCommonSubsequence_2(String text1, String text2) {
        int m, n;

        if (text1 == null || text2 == null
                || (m = text1.length()) == 0 || (n = text2.length()) == 0) {
            return 0;
        }

        int[][] dp = new int[m + 1][n + 1]; //default 0
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    /**
                     *  int max = dp[i - 1][j - 1];
                     *  max = Math.max(max, dp[i - 1][j]);
                     *  max = Math.max(max, dp[i][j - 1]);
                     *  dp[i][j] = max;
                     *
                     *  dp[i-1][j-1] must be the minimum of the three
                     *  => dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])
                     */
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(LongestCommonSubsequence.class, Arrays.asList(
                Arrays.asList("abcde", "ace"),
                Arrays.asList("abc", "abc"),
                Arrays.asList("abc", "def"),
                Arrays.asList("bl", "yby")
        ));
    }
}
