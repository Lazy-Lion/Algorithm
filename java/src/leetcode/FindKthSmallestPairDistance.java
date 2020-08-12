package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * leetcode 719: Find K-th Smallest Pair Distance
 *   Given an integer array, return the k-th smallest distance among all the pairs. The distance of a pair (A, B) is
 * defined as the absolute difference between A and B.
 *
 * Example 1:
 *   Input:
 *     nums = [1,3,1]
 *     k = 1
 *   Output: 0
 *   Explanation:
 *     Here are all the pairs:
 *     (1,3) -> 2
 *     (1,1) -> 0
 *     (3,1) -> 2
 *     Then the 1st smallest distance pair is (1,1), and its distance is 0.
 * Note:
 *   2 <= len(nums) <= 10000.
 *   0 <= nums[i] < 1000000.
 *   1 <= k <= len(nums) * (len(nums) - 1) / 2.
 */
public class FindKthSmallestPairDistance {
	/**
	 * way 1 : 利用最大堆
	 *  时间复杂度: O(n^2 * logk)
	 * Time Limit Exceeded
	 */
	public static int smallestDistancePair(int[] nums, int k) {
		int n = nums.length;

		PriorityQueue<Integer> maxHeap = new PriorityQueue<>((i1,i2) -> i2 - i1);
		int count = 0, diff;
		for(int i = 0; i < n - 1; i ++) {
			for(int j = i + 1; j < n; j ++) {
				diff = Math.abs(nums[i] - nums[j]);
				if(count < k) {
					maxHeap.offer(diff);
					count ++;
				} else if(count == k) {
					if(diff < maxHeap.peek()) {
						maxHeap.poll();
						maxHeap.offer(diff);
					}
				}
			}
		}
		return maxHeap.poll();
	}

	/**
	 * 二分查找: Arrays.sort(nums);
	 *                             left middle right
	 *   left从0开始，right从最大的距离开始(nums[nums.length - 1] - nums[0])
	 *   middle是left、right的中间值 (middle = left + (right - left) / 2，偶数个元素时middle靠左 => middle通过公式计算可以
	 *     取到left，却无法取到right， 所以2中为了避免死循环，循环递进条件 left = middle + 1)
	 *   count表示距离小于等于middle的个数
	 *     1. count >= k，表示实际返回值应小于或等于middle => right=middle重新计算middle
	 *     2. count < k, 表示实际返回值应大于middle => left=middle+1 重新计算middle
	 *     3. 终止条件 left == right
	 */
	public static int smallestDistancePair_2(int[] nums, int k) {
		Arrays.sort(nums);
		int n = nums.length;
		int left = 0;
		int right = nums[nums.length - 1] - nums[0];

		while(left < right) {
			int middle = left + (right - left) / 2;
			int start = 0, count = 0;
			for(int i = 0; i < n; i ++) {  // 计算小于等于middle的个数
				while(start < i && nums[i] - nums[start] > middle) start ++;
				count += i - start;
			}
			if(count < k) left = middle + 1;
			else right = middle;
		}
		return left;
	}

	public static void main(String[] args) {
		System.out.println(smallestDistancePair(new int[]{1,3,1}, 1));
		System.out.println(smallestDistancePair_2(new int[]{1,3,1,1}, 2));
		System.out.println(smallestDistancePair_2(new int[]{1,6,122}, 3));
	}
}
