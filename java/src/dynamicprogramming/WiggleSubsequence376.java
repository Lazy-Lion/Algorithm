package dynamicprogramming;

/**
 * leetcode 376. Wiggle Subsequence
 *   A sequence of numbers is called a wiggle sequence if the differences between successive numbers strictly alternate
 * between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence
 * with fewer than two elements is trivially a wiggle sequence.
 *
 *   For example, [1,7,4,9,2,5] is a wiggle sequence because the differences (6,-3,5,-7,3) are alternately positive and
 * negative. In contrast, [1,4,7,2,5] and [1,7,4,5,5] are not wiggle sequences, the first because its first two
 * differences are positive and the second because its last difference is zero.
 *
 *   Given a sequence of integers, return the length of the longest subsequence that is a wiggle sequence. A subsequence
 * is obtained by deleting some number of elements (eventually, also zero) from the original sequence, leaving the
 * remaining elements in their original order.
 *
 * Example 1:
 *   Input: [1,7,4,9,2,5]
 *   Output: 6
 *   Explanation: The entire sequence is a wiggle sequence.
 * Example 2:
 *   Input: [1,17,5,10,13,15,10,5,16,8]
 *   Output: 7
 *   Explanation: There are several subsequences that achieve this length. One is [1,17,10,13,10,16,8].
 * Example 3:
 *   Input: [1,2,3,4,5,6,7,8,9]
 *   Output: 2
 * Follow up:
 *   Can you do it in O(n) time?
 */
public class WiggleSubsequence376 {
	/**
	 * 遍历数组：上行 - 后一个数组元素大于前一个； 下行 - 后一个数组元素小于前一个
	 *   1) 上行： 基于最近的一次下行序列个数 +1
	 *   2) 下行： 基于最近的一次上行序列个数 +1
	 */
	public static int wiggleMaxLength(int[] nums) {
		int n = nums.length;

		if(n <= 1) return n;

		int up = 1;
		int down = 1;
		for(int i = 0; i < n - 1; i ++) {
			if(nums[i + 1] > nums[i])
				up = down + 1;
			else if(nums[i + 1] < nums[i])
				down = up + 1;
		}

		return Math.max(up, down);
	}

	public static void main(String[] args) {
		System.out.println(wiggleMaxLength(new int[] {1,7,4,9,2,5}));
		System.out.println(wiggleMaxLength(new int[] {1,17,5,10,13,15,10,5,16,8}));
		System.out.println(wiggleMaxLength(new int[] {1,2,3,4,5,6,7,8,9}));
		System.out.println(wiggleMaxLength(new int[] {33,53,12,64,50,41,45,21,97,35,47,92,39,0,93,55,40,46,69,42,6,95,51
				,68,72,9,32,84,34,64,6,2,26,98,3,43,30,60,3,68,82,9,97,19,27,98,99,4,30,96,37,9,78,43,64,4,65,30,84,90
				,87,64,18,50,60,1,40,32,48,50,76,100,57,29,63,53,46,57,93,98,42,80,82,9,41,55,69,84,82,79,30,79,18,97
				,67,23,52,38,74,15}));
		System.out.println(wiggleMaxLength(new int[] {1,2}));
		System.out.println(wiggleMaxLength(new int[] {1}));
	}
}
