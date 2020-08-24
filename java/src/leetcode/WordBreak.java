package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * leetcode 139. Word Break
 *   Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be
 * segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note:
 *   The same word in the dictionary may be reused multiple times in the segmentation.
 *   You may assume the dictionary does not contain duplicate words.
 *
 * Example 1:
 *   Input: s = "leetcode", wordDict = ["leet", "code"]
 *   Output: true
 *   Explanation: Return true because "leetcode" can be segmented as "leet code".
 *
 * Example 2:
 *   Input: s = "applepenapple", wordDict = ["apple", "pen"]
 *   Output: true
 *   Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 *                Note that you are allowed to reuse a dictionary word.
 * Example 3:
 *   Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 *   Output: false
 */
public class WordBreak {
    /**
     * 类似 完全背包问题，可参照 dynamicprogramming.KnapsackComplete
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        int m = wordDict.size();

        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                int size = wordDict.get(j).length();
                if (i - size >= 0) {
                    dp[i] = (dp[i - size] && s.substring(i - size, i).equals(wordDict.get(j))) || dp[i];
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(WordBreak.class
                , Arrays.asList(
                        Arrays.asList("leetcode", Arrays.asList("leet", "code")),
                        Arrays.asList("applepenapple", Arrays.asList("apple", "pen")),
                        Arrays.asList("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat"))
                ));
    }
}
