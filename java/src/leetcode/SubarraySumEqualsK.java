package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 560. Subarray Sum Equals K
 *   Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum
 * equals to k.
 *
 * Example 1:
 *   Input:nums = [1,1,1], k = 2
 *   Output: 2
 * Note:
 *   The length of the array is in range [1, 20,000].
 *   The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
 */
public class SubarraySumEqualsK {
    /**
     * way1：
     *   enumerate all sub array sum，count (sum == k)
     * time complexity： O(n^2)
     * space complexity: O(1)
     */
    public static int subarraySum(int[] nums, int k) {
        int length = nums.length;
        int sum, j, count = 0;

        for (int i = 0; i < length; i++) {
            j = i; // allow a element of sub array
            sum = 0;
            while (j < length) {
                sum += nums[j++];
//				if(sum > k) break;   // 数组元素中存在正数、负数和0，即使sum > k 也需要继续计算
                if (sum == k) count++;
            }
        }

        return count;
    }

    /**
     * way2:
     *   sum[i] = sum(nums[0], nums[1], ..., nums[i])
     *   if j > i and sum[j] - sum[i] = k  => k = sum(nums[i + 1], nums[i + 2], ..., nums[j])
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static int subarraySum_2(int[] nums, int k) {

        int length = nums.length;
        int count = 0;
        int[] sum = new int[length];  // construct sum[]
        sum[0] = nums[0];
        for (int i = 1; i < length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }

        Map<Integer, Integer> map = new HashMap<>(); // key - sum[i], value - number of sum[i]

        for (int i = length - 1; i >= 0; i--) { // from right to left
            map.put(sum[i], map.getOrDefault(sum[i], 0) + 1);  // avoid duplicate counter

            if (sum[i] == k) {
                count++;
            }

            if (map.containsKey(sum[i] + k)) {
                if (k == 0) {
                    count += map.get(sum[i]) - 1; // remove itself
                } else {
                    count += map.get(sum[i] + k);
                }
            }
        }

        return count;
    }

    /**
     * optimize code : {@link SubarraySumEqualsK#subarraySum_2(int[], int)}
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static int subarraySum_3(int[] nums, int k) {
        int length = nums.length;
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>(); // key - sum[i], value - number of sum[i]

        int sum = 0;
        map.put(sum, 1); // for sum[i] == k
        for (int i = 0; i < length; i++) {
            sum += nums[i];

            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }

            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(SubarraySumEqualsK.class, Arrays.asList(
                Arrays.asList(new int[]{-1, -1, 1}, 0),
                Arrays.asList(new int[]{1}, 0),
                Arrays.asList(new int[]{1, 2, 3}, 3),
                Arrays.asList(new int[]{1, 1, 1}, 2),
                Arrays.asList(new int[]{0, 1}, 0)
        ));
    }
}
