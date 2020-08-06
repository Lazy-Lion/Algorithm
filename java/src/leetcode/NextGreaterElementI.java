package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 496: Next Greater Element I
 *
 * You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. Find all the next greater numbers for nums1's elements in the corresponding places of nums2.
 * The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. If it does not exist, output -1 for this number.
 *
 * Example 1:
 *   Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
 *   Output: [-1,3,-1]
 *   Explanation:
 *     For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
 *     For number 1 in the first array, the next greater number for it in the second array is 3.
 *     For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
 *
 * Example 2:
 *   Input: nums1 = [2,4], nums2 = [1,2,3,4].
 *   Output: [3,-1]
 *   Explanation:
 *     For number 2 in the first array, the next greater number for it in the second array is 3.
 *     For number 4 in the first array, there is no next greater number for it in the second array, so output -1.
 *
 * Note:
 *   1. All elements in nums1 and nums2 are unique.
 *   2. The length of both nums1 and nums2 would not exceed 1000.
 */
public class NextGreaterElementI {
    /**
     * stack + map
     *
     * time complexity: O(m + n)
     * space complexity: O(n) - do not consider return array
     */
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        assert m <= n;
        if (m == 0) return new int[0];

        Map<Integer, Integer> map = constructMap(nums2);
        int[] result = new int[m];

        for (int i = 0; i < m; i++) {
            result[i] = map.getOrDefault(nums1[i], -1);
        }

        return result;
    }

    /**
     * construct next greater number map: key - nums[i], value - the next greater number
     */
    private static Map<Integer, Integer> constructMap(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                map.put(stack.pop(), nums[i]);
            }
            stack.push(nums[i]);
        }
        return map;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 3, 5, 2, 4});
        param.add(new int[]{6, 5, 4, 3, 2, 1, 7});
        params.add(param);

        Utils.testStaticMethod(NextGreaterElementI.class
                , new HashSet<String>() {
                    {
                        add("nextGreaterElement");
                    }
                }
                , params);
    }
}
