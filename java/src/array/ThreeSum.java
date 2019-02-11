package array;

import java.util.*;

/**
 * leetcode 15 : 3Sum
 *
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0 ?
 * Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 * The solution set must not contain duplicate triplets.
 *
 * Example:
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 *      A solution set is:
 *      [
 *        [-1, 0, 1],
 *        [-1, -1, 2]
 *     ]
 */
public class ThreeSum {

    /**
     * 时间复杂度： O(n^2)
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);

        Set<Integer> set1 = new HashSet<>();

        int value;
        for(int i = 0; i < nums.length - 2; i ++){
            if(set1.contains(value = nums[i]))  // HashSet的contains()方法调用对象的equals()方法进行判断，Integer类的
                                                // equals()方法比较对象的int值
                continue;
            set1.add(value);

            value = 0 - value;
            int j = i + 1;
            int k = nums.length - 1;
            while(j < k){
                if(nums[j] + nums[k] < value)
                    j ++;
                else if(nums[j] + nums[k] > value)
                    k --;
                else {
                    result.add(Arrays.asList(nums[i], nums[j ++], nums[k --]));
                    while( j < k && nums[j - 1] == nums[j])
                        j ++;
                    while( j < k && nums[k] == nums[k + 1])
                        k --;
                }
            }

        }
        return result;
    }

    public static void main(String[] args){
        ThreeSum c = new ThreeSum();
        System.out.println(c.threeSum(new int[]{-1,0,1,2,-1,-4}));
        System.out.println(c.threeSum(new int[]{0,0,0,0}));
        System.out.println(c.threeSum(new int[]{-2,0,1,1,2}));

    }
}
