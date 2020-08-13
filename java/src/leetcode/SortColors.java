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
    /***
     * 荷兰国旗问题
     *
     * 3-way-partitioning: 将数组划分成3部分 A1,A2,A3；当 a1∈ A1, a2∈A2, a3∈A3 时，a1 < a2 < a3
     *   {@link sort.QuickSort#three_way_partition(int[], int, int)}
     * two point: p1,p2
     *
     * time complexity: O(n)
     * space complexity: O(1)
     */
    public static void sortColors(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        for (int k = 0; k <= j; ) {
            if (nums[k] == 2) {
                Utils.swap(nums, k, j--);
            } else if (nums[k] == 0) {
                Utils.swap(nums, k++, i++);
            } else {
                k++;
            }
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[]{2, 0, 2, 1, 1, 0};
        sortColors(nums);
        System.out.println(Arrays.toString(nums));
    }
}
