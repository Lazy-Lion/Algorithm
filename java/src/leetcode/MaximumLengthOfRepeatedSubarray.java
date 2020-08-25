package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
     *   状态转移方程：
     */
    public static int findLength(int[] array1, int[] array2) {
        return 0;
    }

    /**
     * way2: 滑动窗口
     * @see <a href="https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/solution/wu-li-jie-fa-by-stg-2/"></a>
     */
    public static int findLength_2(int[] array1, int[] array2) {
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

        Utils.testStaticMethod(MaximumLengthOfRepeatedSubarray.class, params);
    }
}
