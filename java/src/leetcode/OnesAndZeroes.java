package leetcode;

import dynamicprogramming.definition.Item;
import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 474: Ones and Zeroes
 *
 * Given an array, strs, with strings consisting of only 0s and 1s. Also two integers m and n.
 * Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s. Each 0 and 1 can be used at most once.
 *
 * Example 1:
 *   Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
 *   Output: 4
 *   Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are "10","0001","1","0".
 * Example 2:
 *   Input: strs = ["10","0","1"], m = 1, n = 1
 *   Output: 2
 *   Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
 *
 * Constraints:
 *   1 <= strs.length <= 600
 *   1 <= strs[i].length <= 100
 *   strs[i] consists only of digits '0' and '1'.
 *   1 <= m, n <= 100
 */
public class OnesAndZeroes {
    /**
     * 0/1 knapsack
     * @see dynamicprogramming.Knapsack01#knapsack_2(Item[], int, int)
     *
     * time complexity: O(m * n * k) - k: length of strs[]
     * space complexity: O(m * n)
     */
    public static int findMaxForm(String[] strs, int m, int n) {
        if (strs == null || strs.length == 0 || (m <= 0 && n <= 0)) {
            return 0;
        }

        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= strs.length; i++) {
            int[] count = countZeroAndOne(strs[i - 1]);

            for (int x = m; x >= count[0]; x--) {
                for (int y = n; y >= count[1]; y--) {
                    dp[x][y] = Math.max(dp[x][y], dp[x - count[0]][y - count[1]] + 1);
                }
            }
        }

        return dp[m][n];
    }

    private static int[] countZeroAndOne(String str) {
        if (str == null || str.isEmpty()) {
            return new int[]{0, 0};
        }
        int count0 = 0, count1 = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                count1++;
            } else if (str.charAt(i) == '0') {
                count0++;
            }
        }
        return new int[]{count0, count1};
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new String[]{"10", "0001", "111001", "1", "0"});
        param.add(5);
        param.add(3);
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"10", "0", "1"});
        param.add(1);
        param.add(1);
        params.add(param);

        Utils.testStaticMethod(OnesAndZeroes.class
                , new HashSet<String>() {
                    {
                        add("findMaxForm");
                    }
                }
                , params);
    }
}
