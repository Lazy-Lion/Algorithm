package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * leetcode 1: Two Sum
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class TwoSum {

    /**
     * hashmap + one pass
     * time complexity O(n)
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i ++){
            int value = nums[i];
            Integer index;
            if((index = map.get(target - value)) != null){
                int[] result = new int[2];
                result[0] = Math.min(index,i);
                result[1] = Math.max(index,i);
                return result;
            }else{
                map.put(value, i);
            }
        }
        return new int[0];
    }

    public static void main(String[] args){
        TwoSum t = new TwoSum();
        System.out.println(Arrays.toString(t.twoSum(new int[]{2, 7, 11, 15}, 9)));
    }
}
