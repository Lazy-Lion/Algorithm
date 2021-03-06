package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 801. Minimum Swaps To Make Sequences Increasing
 *
 *   We have two integer sequences A and B of the same non-zero length.
 *   We are allowed to swap elements A[i] and B[i].  Note that both elements are in the same index position in their
 * respective sequences.
 *   At the end of some number of swaps, A and B are both strictly increasing.  (A sequence is strictly increasing if
 * and only if A[0] < A[1] < A[2] < ... < A[A.length - 1].)
 *   Given A and B, return the minimum number of swaps to make both sequences strictly increasing.  It is guaranteed
 * that the given input always makes it possible.
 *
 * Example:
 *   Input: A = [1,3,5,4], B = [1,2,3,7]
 *   Output: 1
 * Explanation:
 *   Swap A[3] and B[3].  Then the sequences are:
 *   A = [1, 3, 5, 7] and B = [1, 2, 3, 4]
 *   which are both strictly increasing.
 * Note:
 *   A, B are arrays with the same length, and that length will be in the range [1, 1000].
 *   A[i], B[i] are integer values in the range [0, 2000].
 */
public class MinimumSwapsToMakeSequencesIncreasing {
    /**
     * 动态规划
     */
    public static int minSwap(int[] A, int[] B) {
        int n = A.length;
        if (n <= 1) return 0;

        int swapRecord = 1; // 记录交换当前元素且维持当前及之前元素有序需要交换的最小次数，当前元素从index=0开始
        int fixRecord = 0; // 记录不交换当前元素且维持当前及之前元素有序需要交换的最小次数，当前元素从index=0开始

        for (int i = 1; i < n; i++) {
            if (A[i - 1] >= B[i] || B[i - 1] >= A[i]) {
                // 第i个元素与第i-1个元素操作需保持一致，同时交换或不交换
                // fixRecord = fixRecord;
                swapRecord++;
            } else if (A[i - 1] >= A[i] || B[i - 1] >= B[i]) {
                // 第i个元素与第i-1个元素操作需相反，i交换则i-1不交换，i不交换则i-1交换
                int temp = swapRecord;
                swapRecord = fixRecord + 1;
                fixRecord = temp;
            } else {
                // 第i个元素可以交换也可以不交换, 选取第i-1个元素操作完成后交换次数的最小值
                int min = Math.min(swapRecord, fixRecord);
                swapRecord = min + 1;
                fixRecord = min;
            }
        }

        return Math.min(swapRecord, fixRecord);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 3, 5, 4});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{1, 2, 3, 7});
        params.add(param);

        Utils.testStaticMethod(MinimumSwapsToMakeSequencesIncreasing.class
                , new HashSet<String>() {
                    {
                        add("minSwap");
                    }
                }, params);
    }
}
