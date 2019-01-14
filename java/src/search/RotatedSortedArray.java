package search;

/**
 * leetcode 33: Search in Rotated Sorted Array
 *
 * 问题描述：
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 *
 * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 *
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 *
 * You may assume no duplicate exists in the array.
 *
 * Your algorithm's runtime complexity must be in the order of O(log n).
 *
 * Example:
 *
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 *
 */
public class RotatedSortedArray {
    /**
     * 二分查找
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while(left <= right){
            int middle = left + ((right - left) >> 1);

            if(nums[middle] == target){
                return middle;
            }

            // 循环数组，划分之后至少有半边是单调递增的
            if(nums[left] <= nums[middle]){  // 左边单调
                if(nums[middle] > target && nums[left] <= target) {
                    right = middle - 1;
                }else{
                    left = middle + 1;
                }
            }else{  // 右边单调
                if(nums[middle] < target && nums[right] >= target){
                    left = middle + 1;
                }else{
                    right = middle - 1;
                }
            }

        }
        return -1;
    }
}
