package array;

import sort.Utils;

/**
 * leetcode 41 : First Missing Positive
 *
 * Given an unsorted integer array, find the smallest missing positive integer.
 *
 * Example 1:
 * Input: [1,2,0]
 * Output: 3
 *
 * Example 2:
 * Input: [3,4,-1,1]
 * Output: 2
 *
 * Example 3:
 * Input: [7,8,9,11,12]
 * Output: 1
 *
 * Note:
 * Your algorithm should run in O(n) time and uses constant extra space.
 */
public class FirstMissingPositive {

    /**
     * 时间复杂度： O(n) -- 每个数最多访问两次：2n
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        int len = nums.length;

        if(len < 1)
            return 1;

        for(int i = 0; i < len;){
            if(nums[i] == i + 1){  // appropriate location
                i ++;
                continue;
            }

            if(nums[i] <= 0 || nums[i] > len) {
                nums[i] = -1;
                i ++;
            }
            else{
                if(nums[nums[i] - 1] == nums[i]){  // duplicate integer
                    nums[i ++] = -1;
                }else {
                    Utils.swap(nums, i, nums[i] - 1);
                }
            }
        }

        for(int i = 0; i < len; i ++){
            if(nums[i] == -1)
                return i + 1;
        }

        return len + 1;   // array full
    }

    public static void main(String[] args){
        FirstMissingPositive f = new FirstMissingPositive();
        System.out.println(f.firstMissingPositive(new int[]{1,2}));
        System.out.println(f.firstMissingPositive(new int[]{1,1}));
        System.out.println(f.firstMissingPositive(new int[]{1,2,0}));
        System.out.println(f.firstMissingPositive(new int[]{3,4,-1,1}));
        System.out.println(f.firstMissingPositive(new int[]{7,8,9,11,12}));
    }
}
