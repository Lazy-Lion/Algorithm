package leetcode;

import util.Utils;

import java.util.Arrays;

/**
 * leetcode 75: Sort Colors
 *   Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are
 * adjacent, with the colors in the order red, white and blue.
 *
 * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
 *
 * Note: You are not suppose to use the library's sort function for this problem.
 *
 * Example:
 *   Input: [2,0,2,1,1,0]
 *   Output: [0,0,1,1,2,2]
 *
 * Follow up:
 *   A rather straight forward solution is a two-pass algorithm using counting sort.
 *
 *   First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's,
 * then 1's and followed by 2's.
 *
 *   Could you come up with a one-pass algorithm using only constant space?
 */
public class SortColors {
	/**
	 * 3-way-partitioning: 将数组划分成3部分 A1,A2,A3；当 a1∈ A1, a2∈A2, a3∈A3 时，a1 < a2 < a3
	 *
	 * two point: p1,p2
	 *
	 * 时间复杂度： O(n); 空间复杂度： O(1)
	 */
	public static void sortColors(int[] nums) {
		int n = nums.length;
		if(n <= 1) return;

		int p1 = 0, p2 = n - 1;
		for(int i = 0; i <= p2;) {
			if(nums[i] == 0) {
				Utils.swap(nums, i, p1);
				p1 ++;
				i ++;
			} else if(nums[i] == 2) {
				Utils.swap(nums, i, p2);
				p2 --;
			} else {
				i ++;
			}
		}
	}

	public static void main(String[] args) {
		int[] nums = new int[] {2,0,2,1,1,0};
		sortColors(nums);
		System.out.println(Arrays.toString(nums));
	}
}
