package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 718: Maximum Length of Repeated Subarray
 *
 * Given two integer arrays A and B, return the maximum length of an subarray that appears in both arrays.
 *
 * Example 1:
 *   Input:
 *     A: [1,2,3,2,1]
 *     B: [3,2,1,4,7]
 *   Output: 3
 *   Explanation:
 *     The repeated subarray with maximum length is [3, 2, 1].
 *
 * Note:
 *   1 <= len(A), len(B) <= 1000
 *   0 <= A[i], B[i] < 100
 */
public class MaximumLengthOfRepeatedSubarray {
    /**
     * way1: DP
     *   状态转移方程：dp[i][j] 表示 array[0,i]和 array[0,j]最长公共后缀的长度
     *      dp[i][j] =  dp[i-1][j-1] + 1                  if array[i] == array[j]
     *      dp[i][j] =  0                                 if array[i] != array[j]
     *
     *      dp[i][j] 只依赖于dp[i-1][j-1],故可使用一维数组，数组长度可以选择 m,n的较小值
     *
     *   time complexity: O(m * n)
     *   space complexity: O(n)
     */
    public static int findLength(int[] array1, int[] array2) {
        int m, n;
        if (array1 == null || array2 == null
                || (m = array1.length) == 0 || (n = array2.length) == 0) {
            return 0;
        }

        int[] dp = new int[n + 1]; // default 0
        int max = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = n; j >= 1; j--) {
                if (array1[i - 1] == array2[j - 1]) {
                    dp[j] = dp[j - 1] + 1;
                    max = Math.max(max, dp[j]);
                } else {
                    dp[j] = 0;
                }
            }
        }

        return max;
    }

    /**
     * way2: 滑动窗口
     * @see <a href="https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/solution/wu-li-jie-fa-by-stg-2/"></a>
     *
     * time complexity: O( (m+n) * min(m,n) )
     * space complexity: O(1)
     */
    public static int findLength_2(int[] array1, int[] array2) {
        int m, n;
        if (array1 == null || array2 == null) {
            return 0;
        }

        if (array1.length > array2.length) { // 长度小的作为滑动窗口
            int[] temp = array1;
            array1 = array2;
            array2 = temp;
        }

        m = array1.length;
        n = array2.length;

        int max = 0;
        for (int i = 1; i < m + n - 1; i++) {
            /**
             *  固定array2, 将array1从左向右滑动，共需滑动 (m+n-2)次
             *  滑动过程中会经历3种情况：
             *    1. array1的尾和array2的头重叠，且array1头有部分不与array2重叠
             *    2. array1完全重叠到array2
             *    3. array1的头和array2的尾重叠，且array1尾有部分不与array2重叠
             */
            if (i <= m) {
                max = Math.max(max, max(array1, array2, n - i, 0, i));
            } else if (i <= n) {
                max = Math.max(max, max(array1, array2, 0, i - m, m));
            } else {
                max = Math.max(max, max(array1, array2, 0, i - m, (n + m - i)));
            }
        }

        return max;
    }

    /**
     *
     * @param start1 重叠部分在array1的开始位置
     * @param start2 重叠部分在array2的开始位置
     * @param size   重叠部分的长度
     * @return 重叠部分重复子数组的最大长度
     */
    private static int max(int[] array1, int[] array2, int start1, int start2, int size) { // size <= min(m,n)
        int result = 0, count = 0;
        for (int i = 0; i < size; i++) {
            if (array1[i + start1] == array2[i + start2]) {
                count++;
            } else {
                count = 0;
            }
            result = Math.max(result, count);
        }
        return result;
    }

    /**
     * 二分查找 + hash map
     */
    public static int findLength_3(int[] array1, int[] array2) {
        int m, n;
        if (array1 == null || array2 == null
                || (m = array1.length) == 0 || (n = array2.length) == 0) {
            return 0;
        }

        return 0;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 2, 3, 2, 1});
        param.add(new int[]{3, 2, 1, 4, 7});
        params.add(param);

        Utils.testStaticMethod(MaximumLengthOfRepeatedSubarray.class
                , new HashSet<String>() {
                    {
                        add("findLength");
                        add("findLength_2");
                        add("findLength_3");
                    }
                }, params);
    }
}
