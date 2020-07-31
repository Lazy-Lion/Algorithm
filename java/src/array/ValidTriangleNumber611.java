package array;

import java.util.Arrays;

/**
 * 611. Valid Triangle Number
 *   Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the
 * array that can make triangles if we take them as side lengths of a triangle.
 *
 * Example 1:
 *   Input: [2,2,3,4]
 *   Output: 3
 *   Explanation:
 *     Valid combinations are:
 *     2,3,4 (using the first 2)
 *     2,3,4 (using the second 2)
 *     2,2,3
 * Note:
 *   The length of the given array won't exceed 1000.
 *   The integers in the given array are in the range of [0, 1000].
 */
public class ValidTriangleNumber611 {
	/**
	 * a,b,c 可以构成三角形的条件：
	 *   a + b > c && a + c > b && b + c > a
	 * if a,b,c sorted a <= b <= c, just meet the condition a + b > c
	 *
	 * 时间复杂度： O(n^3)
	 */
	public static int triangleNumber(int[] nums) {
		int n = nums.length;
		if(n < 3) return 0;

		Arrays.sort(nums);

		int count = 0;
		for(int i = 0; i < n - 2; i ++) {
			for(int j = i + 1; j < n - 1; j ++) {
				for(int k = j + 1; k < n; k ++) {
					if(nums[i] + nums[j] > nums[k]) {
						count ++;
					} else {
						break;
					}
				}
			}
		}
		return count;
	}

	/**
	 * 时间复杂度： O(n^2)
	 */
	public static int triangleNumberOptimize(int[] nums) {
		int n = nums.length;
		if(n < 3) return 0;

		Arrays.sort(nums);

		int count = 0, k;
		for(int i = 0; i < n - 2; i ++) {
			k = i + 2;
			for(int j = i + 1; j < n - 1 && j <= k; j ++) {  // 裁剪循环次数
				// 当 j == k, 当且仅当 nums[i]==0 或 j == k == n 时, k 不会增加，开始下次循环时 j > k; 无效数据
				while( k < n && nums[i] + nums[j] > nums[k]) {
					k ++;
				}
				if(j < k)
					count += k - j - 1;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(triangleNumber(new int[] {2,2,3,4}));
		System.out.println(triangleNumber(new int[] {24,3,82,22,35,84,19}));
		System.out.println(triangleNumber(new int[] {0,1,0,1}));
		System.out.println(triangleNumber(new int[] {0,0,1,1,1}));
		System.out.println(triangleNumberOptimize(new int[] {2,2,3,4}));
		System.out.println(triangleNumberOptimize(new int[] {24,3,82,22,35,84,19}));
		System.out.println(triangleNumberOptimize(new int[] {0,1,0,1}));
		System.out.println(triangleNumberOptimize(new int[] {0,0,1,1,1}));
	}
}
