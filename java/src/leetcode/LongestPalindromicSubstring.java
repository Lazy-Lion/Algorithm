package leetcode;

/**
 * leetcode 5: Longest Palindromic Substring
 *   Given a string s, find the longest palindromic substring in s. You may assume
 * that the maximum length of s is 1000.
 *
 * Example 1:
 *   Input: "babad"
 *   Output: "bab"
 *   Note: "aba" is also a valid answer.
 * Example 2:
 *   Input: "cbbd"
 *   Output: "bb"
 */
public class LongestPalindromicSubstring {
    /**
     * 动态规划：
     *   状态转移方程：f(i,j) 表示 s[i...j] 是否是回文字符串
     *
     *      f(i,j) = f(i+1,j-1) && ( s(i) == s(j) )
     *      f(i,i) = true
     *      f(i,i+1) = s(i)==s(i+1)
     *
     *      状态转移从长度为 1 的字符串开始，从中间向两边扩展。
     *
     *   time complexity: O(n^2)
     *   space complexity: O(n^2)
     */
    public static String longestPalindrome(String s) {
        int n = s.length();
        if (n <= 1) {
            return s;
        }

        boolean[][] dp = new boolean[n][n];
        String ans = "";
        // len 表示回文字符串长度 - 1
        for (int len = 0; len < n; len++) {
            // i 表示从左向右遍历字符串
            for (int i = 0; i < n - len; i++) {
                int j = i + len;
                if (len == 0) {
                    dp[i][j] = true;
                } else if (len == 1) {
                    dp[i][j] = s.charAt(i) == s.charAt(i + 1);
                } else {
                    dp[i][j] = dp[i + 1][j - 1] && (s.charAt(i) == s.charAt(j));
                }
                if (dp[i][j] && (len + 1) > ans.length()) {
                    ans = s.substring(i, j + 1);
                }
            }
        }

        return ans;
    }

    /**
     * 中心扩展
     *   思路：
     *     回文串特殊情况：
     *        1. 长度为 1 的字符串是回文串
     *        2. 长度为 2 的字符串，当两个字符相同时是回文串
     *     其他回文串可以从上述两种情况向左右扩展构成回文串，当左右扩展时两字符不同，则后续都不能构成回文串，扩展停止。
     *
     * time complexity: O(n^2)
     * space complexity: O(1)
     */
    public static String longestPalindrome_2(String s) {
        int n = s.length();
        if (n <= 1) {
            return s;
        }

        String ans = "";
        int size, half;
        for (int i = 0; i < n; i++) {
            /**
             * 长度为 1 的字符串扩展
             */
            if ((size = expansion(s, i, i)) > ans.length()) {
                half = (size - 1) >> 1;
                ans = s.substring(i - half, i + half + 1);
            }
            /**
             * 长度为 2 的字符串（两个字符相同）扩展
             */
            if (i + 1 < n && s.charAt(i) == s.charAt(i + 1)
                    && (size = expansion(s, i, i + 1)) > ans.length()) {
                half = (size - 2) >> 1;
                ans = s.substring(i - half, i + half + 2);
            }
        }
        return ans;
    }

    private static int expansion(String s, int left, int right) {
        int n = s.length();
        while (left > 0 && right < n - 1 && s.charAt(left - 1) == s.charAt(right + 1)) {
            left--;
            right++;
        }
        return right - left + 1;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome_2("babad"));
        System.out.println(longestPalindrome_2("cbbd"));
        System.out.println(longestPalindrome_2("aaaa"));
    }
}
