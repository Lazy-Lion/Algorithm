package array;

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
public class ContinuousSubarraySum523 {
	/**
	 * way1: 同 SubarraySumEqualsK560  way1
	 * 时间复杂度：O(n^2)
	 */
	public static boolean checkSubarraySum(int[] nums, int k) {
		int n = nums.length, sum, j;

		for(int i = 0; i < n - 1; i ++) {
			sum = nums[i];
			j = i + 1;
			while(j < n) {
				sum += nums[j ++];
				if(k == 0 && sum == 0
						|| k != 0 && sum % k == 0) return true;  // -4 % 2 == 0
			}
		}
		return false;
	}

	/**
	 * way2 :
	 *  Theorem: if c != 0 and a % c == b % c => (a - b) % c == 0
	 *
	 *  prove:
	 *     a % c == b % c == k
	 *     =>  a = n1 * c + k,  b = n2 * c + k
	 *     =>  a - b = (n1 - n2) * c
	 *     =>  (a - b) % c == 0
	 *
	 *  so just like SubarraySumEqualsK560's  way2
	 *  Time Complexity: O(n)
	 */
	public static boolean checkSubarraySum_2(int[] nums, int k) {
		int n = nums.length;
		if(n <= 1) return false;
		int sum;
		if(n == 2) {
			sum = nums[0] + nums[1];
			return k == 0 && sum == 0
					|| k != 0 && sum % k == 0;
		}

		Set<Integer> set = new HashSet<>();
		sum = 0;
		int mod;
		Integer prev = null;

		// 要求子数组元素个数大于1
		// prev变量的目的是在i处理完成之后再将i-1处理结果添加至set集合
		// 该循环无法判断只有两个元素的情况
		for(int i = 0; i < n; i ++) {
			sum += nums[i];
			mod = k == 0 ? sum : sum % k;
			if(set.contains(mod) || (k != 0 && mod == 0 && i > 0)) return true;

			if(prev != null) set.add(prev);
			prev = mod;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(checkSubarraySum(new int[]{23, 2, 4, 6, 7}, 6));
		System.out.println(checkSubarraySum(new int[]{23, 2, 6, 4, 7}, 6));
		System.out.println(checkSubarraySum_2(new int[]{23, 2, 4, 6, 7}, 6));
		System.out.println(checkSubarraySum_2(new int[]{23, 2, 6, 4, 7}, 6));
		System.out.println(checkSubarraySum_2(new int[]{0, 1, 0}, 0));
		System.out.println(checkSubarraySum_2(new int[]{5, 0, 0}, 0));
		System.out.println(checkSubarraySum_2(new int[]{0, 0}, 0));
		System.out.println(checkSubarraySum_2(new int[]{1, 2, 3}, 6));
	}
}
