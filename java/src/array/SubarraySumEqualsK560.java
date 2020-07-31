package array;

import java.util.HashMap;
import java.util.Map;

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
public class SubarraySumEqualsK560 {
	/**
	 * way1：枚举，计算出所有连续子数组的和（sum），统计 sum == k的个数
	 * 时间复杂度： O(n^2)
	 */
	public static int subarraySum(int[] nums, int k) {
		int n = nums.length;
		int sum, j, count = 0;

		for(int i = 0; i < n; i ++) {
			j = i;
			sum = 0;
			while(j < n) {
				sum += nums[j ++];
//				if(sum > k) break;   // 数组元素中存在正数、负数和0，即使sum > k 也需要继续计算
				if(sum == k) count ++;
			}
		}

		return count;
	}

	/**
	 * way2:
	 *   sum[i] - 表示nums数组[0,i]下标元素累计和
	 *   if j > i and sum[j] - sum[i] = k  => (i,j]下标元素累计和为 k
	 *
	 * 时间复杂度：O(n)；空间复杂度：O(n)
	 */
	public static int subarraySum_2(int[] nums, int k) {
		int n = nums.length, count = 0;
		Map<Integer, Integer> map = new HashMap<>();  // key: sum; value: key == sum的个数
		map.put(0, 1);

		int sum = 0;
		for(int i = 0; i < n; i ++) {
			sum += nums[i];
			if(map.containsKey(sum - k)) count += map.get(sum - k);
			map.put(sum, map.getOrDefault(sum, 0) + 1);

		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(subarraySum(new int[]{1,1,1}, 2));
		System.out.println(subarraySum_2(new int[]{1,1,1}, 2));
	}
}
