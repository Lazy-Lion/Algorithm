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

    /**
     * Boyer-Moore Voting Algorithm (摩尔投票算法)：
     *
     * wikipedia :
     *   The Boyer–Moore majority vote algorithm is an algorithm for finding the majority of a sequence of elements
     * using linear time and constant space.In its simplest form, the algorithm finds a majority element, if there is
     * one: that is, an element that occurs repeatedly for more than half of the elements of the input. However, if
     * there is no majority, the algorithm will not detect that fact, and will still output one of the elements.
     * A version of the algorithm that makes a second pass through the data can be used to verify that the element
     * found in the first pass really is a majority.
     *
     * step :
     *  1.initialize the element m and a counter i with i = 0
     *  2.for each element x of the input sequence:
     *    1) if i == 0, then assign m = x and i = 1
     *    2) else if m == x,the assign i = i + 1
     *    3) else assign i = i - 1
     *  3.return m
     *
     * @param nums
     * @return
     */
    public int majorityElement2(int[] nums){
        int len = nums.length;

        if(len == 0) return -1;
        int result = nums[0];
        int c = 1;
        for(int i = 1; i < nums.length; i ++){
            if(c == 0){
                c = 1;
                result = nums[i];
            }else if(nums[i] == result) {
                c++;
            }else{
                c --;
            }
        }

        c = 0;
        for(int v : nums){
            if(v == result)
                c ++;
        }

        if(c > len / 2)
            return result;
        return -1;
    }

    public static void main(String[] args){
        MajorityElement m = new MajorityElement();
        System.out.println(m.majorityElement(new int[]{3,2,3}));
        System.out.println(m.majorityElement(new int[]{2,2,1,1,1,2,2}));
        System.out.println(m.majorityElement2(new int[]{3,2,3}));
        System.out.println(m.majorityElement2(new int[]{2,2,1,1,1,2,2}));
    }
}
