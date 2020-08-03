package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * leetcode 523. Continuous Subarray Sum
 *   Given a list of non-negative numbers and a target integer k, write a function to check if the array has a continuous
 * subarray of size at least 2 that sums up to a multiple of k, that is, sums up to n*k where n is also an integer.
 *
 * Example 1:
 *   Input: [23, 2, 4, 6, 7],  k=6
 *   Output: True
 *   Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.
 * Example 2:
 *   Input: [23, 2, 6, 4, 7],  k=6
 *   Output: True
 *   Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.
 * Note:
 *   The length of the array won't exceed 10,000.
 *   You may assume the sum of all the numbers is in the range of a signed 32-bit integer.
 */
public class ContinuousSubarraySum {

    /**
     * way 1:
     *   enumerate all sum:
     *     sum[i][j] equals sum(nums[i], nums[i+1], ... , nums[j])
     *     => sum[i][j] = sum[i - 1][j - 1] - nums[i - 1] + nums[j] (i < j)
     *
     *   time complexity: O(n^2)
     *   space complexity: O(n^2) -> Memory Limit Exceeded
     */
    public static boolean checkSubarraySum(int[] nums, int k) {
        int length = nums.length;

        if (length < 2) {
            return false;
        }

        int[][] sum = new int[length][length];
        sum[0][0] = nums[0];
        for (int i = 1; i < length; i++) {
            sum[0][i] = sum[0][i - 1] + nums[i];
        }

        for (int i = 1; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                sum[i][j] = sum[i - 1][j - 1] - nums[i - 1] + nums[j];
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if ((k == 0 && sum[i][j] == 0) || (k != 0 && sum[i][j] % k == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * way 2:
     *   {@link ContinuousSubarraySum#checkSubarraySum(int[], int)} optimize space =>
     *   time complexity: O(n^2)
     *   space complexity: O(1)
     *
     *   like {@link SubarraySumEqualsK#subarraySum(int[], int)}
     */
    public static boolean checkSubarraySum_2(int[] nums, int k) {
        int length;

        if ((length = nums.length) < 2) {
            return false;
        }

        int sum;
        for (int i = 0; i < length - 1; i++) {
            sum = nums[i];
            for (int j = i + 1; j < length; j++) {
                sum += nums[j];
                if ((k == 0 && sum == 0)
                        || (k != 0 && sum % k == 0)) { // -4 % 2 == 0
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * way3 :
     *  Theorem: if c != 0 and a % c == b % c => (a - b) % c == 0
     *
     *  prove:
     *     a % c == b % c == k
     *     =>  a = n1 * c + k,  b = n2 * c + k
     *     =>  a - b = (n1 - n2) * c
     *     =>  (a - b) % c == 0
     *
     *  so just like {@link SubarraySumEqualsK#subarraySum_3(int[], int)}
     *  Time Complexity: O(n)
     *  space complexity: O(n)
     */
    public static boolean checkSubarraySum_3(int[] nums, int k) {
        int n = nums.length;
        if (n < 2) return false;
        int sum = 0;

        Set<Integer> set = new HashSet<>();
        int mod;
        int prev = 0;

        // 要求子数组元素个数大于1
        // prev变量的目的是在i处理完成之后再将i-1处理结果添加至set集合
        // 该循环无法判断只有两个元素的情况
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            mod = k == 0 ? sum : sum % k;
            if (set.contains(mod))
                return true;

            set.add(prev);
            prev = mod;
        }
        return false;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(ContinuousSubarraySum.class, Arrays.asList(
                Arrays.asList(new int[]{23, 2, 4, 6, 7}, 6),
                Arrays.asList(new int[]{23, 2, 6, 4, 7}, 6),
                Arrays.asList(new int[]{0, 1, 0}, 0),
                Arrays.asList(new int[]{5, 0, 0}, 0),
                Arrays.asList(new int[]{0, 0}, 0),
                Arrays.asList(new int[]{1, 2, 3}, 6),
                Arrays.asList(new int[]{1, 0}, 2),
                Arrays.asList(new int[]{23, 2, 4, 0, 0}, 0)
        ));
    }
}
