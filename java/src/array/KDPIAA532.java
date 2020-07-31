package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 532. K-diff Pairs in an Array
 *
 *   Given an array of integers and an integer k, you need to find the number of unique k-diff pairs in the array. Here
 * a k-diff pair is defined as an integer pair (i, j), where i and j are both numbers in the array and their absolute
 * difference is k.
 *
 * Example 1:
 *   Input: [3, 1, 4, 1, 5], k = 2
 *   Output: 2
 *   Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
 *   Although we have two 1s in the input, we should only return the number of unique pairs.
 * Example 2:
 *   Input:[1, 2, 3, 4, 5], k = 1
 *   Output: 4
 *   Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
 * Example 3:
 *   Input: [1, 3, 1, 5, 4], k = 0
 *   Output: 1
 *   Explanation: There is one 0-diff pair in the array, (1, 1).
 * Note:
 *   The pairs (i, j) and (j, i) count as the same pair.
 *   The length of the array won't exceed 10,000.
 *   All the integers in the given input belong to the range: [-1e7, 1e7].
 */
public class KDPIAA532 {
	/**
	 * 数组遍历：时间复杂度 O(n^2)
	 */
	public static int findPairs(int[] nums, int k) {
		Arrays.sort(nums);

		int count = 0;
		for(int i = 0; i < nums.length - 1; i ++) {
			if(i > 0 && nums[i] == nums[i - 1]) continue;

			for(int j = i + 1; j < nums.length; j ++) {
				if(j > i + 1 && nums[j] == nums[j - 1]) continue;

				int diff = nums[j] - nums[i] - k;
				if(diff == 0) count ++;
				else if (diff > 0) break;
			}
		}

		return count;
	}

	/**
	 * 优化时间复杂度， HashMap: key - nums元素，value - key元素出现的次数
	 * 时间复杂度： O(n)
	 *
	 * 优化点： one pass
	 */
	public static int findPairsOptimize(int[] nums, int k) {
		if(k < 0) return 0;  // k is absolute difference

		Map<Integer, Integer> map = new HashMap<>();
		for(int i = 0; i < nums.length; i ++) {
			map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
		}

		int count = 0;
		for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if(k == 0) {
				if(entry.getValue() > 1) count ++;
			} else{
				int key = entry.getKey();

				if(map.containsKey(key + k)) {
					count ++;
				}
			}
		}

		return count;
	}

	public static void main(String[] args) {
		int[] nums = new int[] {3, 1, 4, 1, 5};
		System.out.println(findPairs(nums, 2));

		nums = new int[] {1, 2, 3, 4, 5};
		System.out.println(findPairs(nums, 1));

		nums = new int[] {1, 3, 1, 5, 4};
		System.out.println(findPairs(nums, 0));

		nums = new int[] {3, 1, 4, 1, 5};
		System.out.println(findPairsOptimize(nums, 2));

		nums = new int[] {1, 2, 3, 4, 5};
		System.out.println(findPairsOptimize(nums, 1));

		nums = new int[] {1, 3, 1, 5, 4};
		System.out.println(findPairsOptimize(nums, 0));
	}
}
