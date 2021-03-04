package leetcode;

/**
 * leetcode 10. Regular Expression Matching
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
 *     1.'.' Matches any single character.
 *     2.'*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 *
 * Note:
 *    1.s could be empty and contains only lowercase letters a-z.
 *    2.p could be empty and contains only lowercase letters a-z, and characters like . or *.
 *
 * Ex:
 *    Input:
 *    s = "mississippi"
 *    p = "mis*is*p*."
 *    Output: false
 */
public class RegularExpression {
    private static final char POUND_SIGN = '*';
    private static final char DOT_SIGN = '.';

    private static boolean isEnd = false;

    public static boolean isMatch(String s, String p) {
        if (p.isEmpty())
            return s.isEmpty();

        isEnd = false;
        return isMatch(s.toCharArray(), p.toCharArray(), 0, 0);
    }

    /**
     * (c*): 将 *和 *前的字符看做一个整体，确定匹配与否
     * @param text  匹配字符数组
     * @param pattern 正则表达式
     * @param ti  字符数组匹配到的位置 index
     * @param pi  正则表达式匹配到的位置 index
     */
    private static boolean isMatch(char[] text, char[] pattern, int ti, int pi) {
        //已匹配成功，不再进行递归
        if (isEnd) return true;

        /**
         * 递归结束条件：正则表达式已搜索结束：如果字符数组也已结束返回 true, 否则返回 false
         *             只当字符数组搜索结束时，并没有完成匹配，不能作为结束条件
         *
         */
        if (pi == pattern.length) {
            if (ti == text.length) {
                // 剪枝：已经匹配成功，后续就无需匹配
                isEnd = true;
                return true;
            }
            return false;
        }

        boolean firstMatch = (ti != text.length) && (text[ti] == pattern[pi] || pattern[pi] == DOT_SIGN);

        if (pattern.length > pi + 1 && pattern[pi + 1] == POUND_SIGN) {
            return (firstMatch && isMatch(text, pattern, ti + 1, pi))
                    || isMatch(text, pattern, ti, pi + 2);
        } else {
            return firstMatch && isMatch(text, pattern, ti + 1, pi + 1);
        }

    }

    public static boolean isMatch_2(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        isEnd = false;

        return isMatch_2(s, p, 0, 0);
    }

    /**
     * 回溯
     * @see #isMatch(String, String)
     */
    private static boolean isMatch_2(String s, String p, int si, int pi) {
        int n = s.length();
        int m = p.length();
        if (pi == m) {
            if (si == n) {
                isEnd = true;
                return true;
            }
            return false;
        }

        if (pi + 1 < m && p.charAt(pi + 1) == POUND_SIGN) {
            if (si >= n || (p.charAt(pi) != DOT_SIGN && p.charAt(pi) != s.charAt(si))) {
                return isMatch_2(s, p, si, pi + 2);
            } else {
                return isMatch_2(s, p, si, pi + 2) || isMatch_2(s, p, si + 1, pi);
            }
        } else {
            if (si < n && (p.charAt(pi) == DOT_SIGN || p.charAt(pi) == s.charAt(si))) {
                return isMatch_2(s, p, si + 1, pi + 1);
            }
            return false;
        }
    }

    /**
     * 动态规划: f(i,j) 表示 s[0...i) 和 p[0...j) 是否匹配
     *
     *  1) p[j-1] 是字符或'.':
     *    if (s[i-1] == p[j-1] || p[j-1] == '.'), f(i,j) = f(i-1,j-1)
     *    else ,                                  f(i,j) = false
     *  2) p[j-1] 是 '*'
     *    if (s[i-1] == p[j-2] || p[j-2] == '.'), f(i,j) = f(i,j-2) || f(i-1,j)
     *    else ,                                f(i,j) = f(i,j-2)
     *
     *  f(0,0) = true;
     */
    public static boolean isMatch_3(String s, String p) {
        int n = s.length();
        int m = p.length();

        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;

        for (int i = 0; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (p.charAt(j - 1) == POUND_SIGN) {
                    dp[i][j] = dp[i][j - 2];
                    if (i > 0 && match(s, p, i - 1, j - 2)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    if (i > 0 && match(s, p, i - 1, j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        return dp[n][m];
    }

    private static boolean match(String s, String p, int i, int j) {
        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == DOT_SIGN) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isMatch("mississippi", "mis*is*p*."));  // false
        System.out.println(isMatch("mississippi", "mis*is*ip*.")); // true
        System.out.println(isMatch("aa", "a*"));       // true
        System.out.println(isMatch("ab", ".*"));       // true
        System.out.println(isMatch("ab", ".*b"));      // true
        System.out.println(isMatch("aab", "c*a*b"));   // true
        System.out.println(isMatch("a", "ab*"));       // true
        System.out.println(isMatch("bbbba", ".*a*a")); // true
        System.out.println(isMatch("ab", ".*c")); // false

        System.out.println(isMatch_2("mississippi", "mis*is*p*."));  // false
        System.out.println(isMatch_2("mississippi", "mis*is*ip*.")); // true
        System.out.println(isMatch_2("aa", "a*"));       // true
        System.out.println(isMatch_2("ab", ".*"));       // true
        System.out.println(isMatch_2("ab", ".*b"));      // true
        System.out.println(isMatch_2("aab", "c*a*b"));   // true
        System.out.println(isMatch_2("a", "ab*"));       // true
        System.out.println(isMatch_2("bbbba", ".*a*a")); // true
        System.out.println(isMatch_2("ab", ".*c")); // false

        System.out.println(isMatch_3("mississippi", "mis*is*p*."));  // false
        System.out.println(isMatch_3("mississippi", "mis*is*ip*.")); // true
        System.out.println(isMatch_3("aa", "a*"));       // true
        System.out.println(isMatch_3("ab", ".*"));       // true
        System.out.println(isMatch_3("ab", ".*b"));      // true
        System.out.println(isMatch_3("aab", "c*a*b"));   // true
        System.out.println(isMatch_3("a", "ab*"));       // true
        System.out.println(isMatch_3("bbbba", ".*a*a")); // true
        System.out.println(isMatch_3("ab", ".*c")); // false
    }
}
