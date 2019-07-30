package sort;

import java.util.Arrays;

/**
 * leetcode 324. Wiggle Sort II
 * Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
 * Example 1:
 *   Input: nums = [1, 5, 1, 1, 6, 4]
 *   Output: One possible answer is [1, 4, 1, 5, 1, 6].
 * Example 2:
 *   Input: nums = [1, 3, 2, 2, 3, 1]
 *   Output: One possible answer is [2, 3, 1, 3, 1, 2].
 * Note:
 * You may assume all input has valid answer.
 *
 * Follow Up:
 * Can you do it in O(n) time and/or in-place with O(1) extra space?
 */
public class WiggleSortII324 {
	/**
	 * 时间复杂度 ：依赖于排序的复杂度，O(nlogn)
	 * 空间复杂度： O(n)
	 */
	public static void wiggleSort(int[] nums) {
		int n = nums.length;

		if(n <= 1) return;

		int[] temp = Arrays.copyOf(nums, n);
		Arrays.sort(temp);
		int middle = (n - 1) / 2;
		for(int i = middle; i >= 0 ; i --){
			nums[2 * (middle - i)] = temp[i];
			if(n - 1 - i > middle)
				nums[2 * i + 1] = temp[n - 1 - i];
		}
	}

	/**
	 * TODO: 时间复杂度： O(n)
	 */
	public static void wiggleSortOptimize(int[] nums) {
		int n = nums.length;
		if(n <= 1) return;


	}

	public static void main(String[] args){
		int[] nums = new int[]{1, 5, 1, 1, 6, 4};
		wiggleSort(nums);
		System.out.println(Arrays.toString(nums));

		nums = new int[] {1, 3, 2, 2, 3, 1};
		wiggleSort(nums);
		System.out.println(Arrays.toString(nums));

		nums = new int[] {2, 1};
		wiggleSort(nums);
		System.out.println(Arrays.toString(nums));

		nums = new int[] {4, 5, 5, 6};
		wiggleSort(nums);
		System.out.println(Arrays.toString(nums));

		nums = new int[]{1, 5, 1, 4, 2};
		wiggleSort(nums);
		System.out.println(Arrays.toString(nums));
	}
}
