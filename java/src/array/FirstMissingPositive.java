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
     * 思路1：数组 [0,n)存储对应的正整数值 nums[i] == i + 1, 不是[1,n]的数使用-1标记
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

    /**
     * 思路2：如上方法，只进行标记，而不替换实际数据
     * @param nums
     * @return
     */
    public int firstMissingPositive2(int[] nums){
        int len = nums.length;

        if(len < 1) return 1;

        for(int i = 0; i < len; i ++){
            if(nums[i] <= 0 || nums[i] > len)
                nums[i] = len + 1;
        }

        for(int i = 0; i < len; i ++){
            int index = Math.abs(nums[i]);
            if(index < 1 || index > len)
                continue;

            index --;
            if(nums[index] > 0)
                nums[index] = -nums[index];
        }

        for(int i = 0; i < len; i ++){
            if(nums[i] > 0)
                return i + 1;
        }

        return len + 1;
    }

    public static void main(String[] args){
        FirstMissingPositive f = new FirstMissingPositive();
        System.out.println(f.firstMissingPositive(new int[]{1,2}));
        System.out.println(f.firstMissingPositive(new int[]{1,1}));
        System.out.println(f.firstMissingPositive(new int[]{1,2,0}));
        System.out.println(f.firstMissingPositive(new int[]{3,4,-1,1}));
        System.out.println(f.firstMissingPositive(new int[]{7,8,9,11,12}));

        System.out.println(f.firstMissingPositive2(new int[]{1,2}));
        System.out.println(f.firstMissingPositive2(new int[]{1,1}));
        System.out.println(f.firstMissingPositive2(new int[]{1,2,0}));
        System.out.println(f.firstMissingPositive2(new int[]{3,4,-1,1}));
        System.out.println(f.firstMissingPositive2(new int[]{7,8,9,11,12}));
    }
}
