package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * leetcode 869: Reordered Power of 2
 *   Starting with a positive integer N, we reorder the digits in any order (including the original order) such
 * that the leading digit is not zero.
 *
 * Return true if and only if we can do this in a way such that the resulting number is a power of 2.
 *
 * Example 1:
 *   Input: 1
 *   Output: true
 * Example 2:
 *   Input: 10
 *   Output: false
 * Example 3:
 *   Input: 16
 *   Output: true
 * Example 4:
 *   Input: 24
 *   Output: false
 * Example 5:
 *   Input: 46
 *   Output: true
 * Note:
 *   1 <= N <= 10^9
 */
public class ReorderedPowerOf2 {
    /**
     * way 1: 全排列问题: full permutation
     *    recursion
     *    依次从集合中选择一个元素作为排列的第一个元素，再对剩余的元素进行全排列
     *
     * time complexity: O(m! * logN),  m为整数的位数， m!为全排列个数，logN为验证一个全排列是否为2的倍数的消耗时间
     * space complexity: O(m)
     */
    public static boolean reorderedPowerOf2(int n) {
        if (n == 0) return false;

        byte[] array = int2ByteArray(n);
        return permutations(array, 0);
    }

    private static byte[] int2ByteArray(int n) {
        assert n > 0;

        byte[] array = new byte[10];
        int count = 0;
        while (n > 0) {
            array[count++] = (byte) (n % 10);
            n = n / 10;
        }

        byte[] result = new byte[count];
        for (int i = 0; i < count; i++) {
            result[count - i - 1] = array[i];
        }

        return result;
    }


    private static boolean permutations(byte[] array, int start) {
        if (start == array.length - 1) {
            return isPowerOfTwo(array);
        }

        for (int i = start; i < array.length; i++) {
            if (start == 0 && array[i] == 0) {
                continue;
            }
            swap(array, start, i);
            if (permutations(array, start + 1)) {
                swap(array, start, i); // recover
                return true;
            }
            swap(array, start, i); // recover
        }
        return false;
    }

    private static void swap(byte[] array, int index1, int index2) {
        if (index1 == index2) return;

        byte temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static boolean isPowerOfTwo(byte[] array) {

        int val = array[0];
        for (int i = 1; i < array.length; i++) {
            val = val * 10 + array[i];
        }

        int count = 0;
        while (val > 0) {
            if ((val & 1) == 1) count++;
            val = val >> 1;
        }
        if (count > 1) return false;
        return true;
    }

    /**
     * way 2: 全排列问题
     *  字典序 dictionary order (lexicographical order):
     *   Specifically, given two partially ordered sets A and B, the lexicographical order on the Cartesian product A × B is defined as
     *        (a,b) ≤ (a′,b′) if and only if a < a′ or (a = a′ and b ≤ b′).
     *
     *  time complexity: O(m! * logN)
     *  space complexity: O(m)
     */
    public static boolean reorderedPowerOf2_2(int n) {
        if (n == 0) return false;

        byte[] array = int2ByteArray(n);
        minPermutation(array);
        if (isPowerOfTwo(array)) {
            return true;
        }

        while (nextPermutation(array)) {
            if (isPowerOfTwo(array)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 字典序最小序列
     */
    private static void minPermutation(byte[] array) {
        if (array.length <= 1) return;

        Arrays.sort(array);
        if (array[0] == 0) { // not start with 0
            int i = 1;
            while (i < array.length && array[i] == 0) {
                i++;
            }
            if (i < array.length) {
                swap(array, 0, i);
            }
        }
    }

    /**
     * find next permutation:
     *   1. find the last ascending pair (array[a],array[a+1]), array[a] > array[a+1]
     *   2. find the last index k (array[k] > array[a] and array[k] is the smallest on the right of the ascending pair)
     *   3. swap array[a] and array[k]
     *   4. reverse set on the right of a
     */
    private static boolean nextPermutation(byte[] array) {
        boolean hasNext = false;

        // step 1
        int i = array.length - 1;
        while (i > 0 && !hasNext) {
            if (array[i] > array[--i]) {
                hasNext = true;
            }
        }

        if (hasNext) {
            // step 2
            int a = i;
            int k = array.length - 1;
            while (k > a) {
                if (array[k] > array[a]) {
                    break;
                }
                k--;
            }

            // step 3
            swap(array, a, k);

            // step 4
            int m = a + 1;
            int n = array.length - 1;
            while (m < n) {
                swap(array, m++, n--);
            }
        }

        return hasNext;
    }

    /**
     * way 3 :
     *  N∈ [1, 10^9],   10^9 < 2^30
     *  统计N中 0,1,2,3,4,5,6,7,8,9 的个数，将其与 pow(2, i)中各个数字的个数做比较 （i ∈ [0,31)）
     *  如果相同则存在数字的组合结果 N'，使得 N' = pow(2, i)
     *
     *  time complexity: O(log_base2(N) * log_base10(N)) => log_base2(N) < 30
     *  space complexity: O(log_base2(N))
     */
    public static boolean reorderedPowerOf2_3(int N) {
        if (N <= 0) return false;

        int[] digits = getDigitsCount(N);

        for (int i = 0; i < 30; i++) {  // 使用移位运算替代 pow(2,i)
            if (Arrays.equals(digits, getDigitsCount(1 << i)))
                return true;
        }

        return false;
    }

    /**
     * 统计整数中各个数字的个数
     * 例:123521, 返回 {0,2,2,1,0,1,0,0,0,0}
     */
    private static int[] getDigitsCount(int n) {
        int[] ans = new int[10];
        while (n > 0) {
            ans[n % 10]++;
            n = n / 10;
        }
        return ans;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        Utils.testStaticMethod(ReorderedPowerOf2.class
                , new HashSet<String>() {
                    {
                        add("reorderedPowerOf2");
                        add("reorderedPowerOf2_3");
                        add("reorderedPowerOf2_2");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(1),
                        Arrays.asList(10),
                        Arrays.asList(16),
                        Arrays.asList(24),
                        Arrays.asList(46),
                        Arrays.asList(218),
                        Arrays.asList(100000842),
                        Arrays.asList(4609),
                        Arrays.asList(1892)
                ));
    }
}
