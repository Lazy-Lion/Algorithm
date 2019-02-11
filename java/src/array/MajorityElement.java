package array;

import java.util.HashMap;
import java.util.Map;

/**
 * leetcode 169 : Majority Element
 * Given an array of size n, find the majority element. The majority element is the element that appears more than
 *  ⌊ n/2 ⌋ times.
 *
 * You may assume that the array is non-empty and the majority element always exist in the array.
 */
public class MajorityElement {

    /**
     * 时间复杂度： O(n)
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        if (nums.length < 1) return -1;

        int count = nums.length / 2;

        Map<Integer,Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length;i ++){
            if(map.containsKey(nums[i]))
                map.put(nums[i], map.get(nums[i]) + 1);
            else
                map.put(nums[i], 1);
        }

        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            if(entry.getValue() > count)
                return entry.getKey();
        }
        return -1;
    }

    public static void main(String[] args){
        MajorityElement m = new MajorityElement();
        System.out.println(m.majorityElement(new int[]{3,2,3}));
        System.out.println(m.majorityElement(new int[]{2,2,1,1,1,2,2}));
    }
}
