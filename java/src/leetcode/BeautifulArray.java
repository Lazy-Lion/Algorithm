package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * leetcode 932: Beautiful Array
 *
 * For some fixed N, an array A is beautiful if it is a permutation of the integers 1, 2, ..., N, such that:
 *
 * For every i < j, there is no k with i < k < j such that A[k] * 2 = A[i] + A[j].
 *
 * Given N, return any beautiful array A.  (It is guaranteed that one exists.)
 *
 * Example 1:
 *   Input: 4
 *   Output: [2,1,4,3]
 * Example 2:
 *   Input: 5
 *   Output: [3,1,2,5,4]
 * Note:
 *   1 <= N <= 1000
 */
public class BeautifulArray {
    public static int[] beautifulArray(int n) {
        return null;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(BeautifulArray.class
                , new HashSet<String>() {
                    {
                        add("beautifulArray");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(4),
                        Arrays.asList(5)
                ));
    }
}
