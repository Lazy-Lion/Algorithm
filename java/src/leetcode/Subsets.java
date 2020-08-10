package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 78: Subsets
 *
 * Given a set of distinct integers, nums, return all possible subsets (the power set).
 *
 * Note: The solution set must not contain duplicate subsets.
 *
 * Example:
 *   Input: nums = [1,2,3]
 *   Output:
 *   [
 *     [3],
 *     [1],
 *     [2],
 *     [1,2,3],
 *     [1,3],
 *     [2,3],
 *     [1,2],
 *     []
 *   ]
 */
public class Subsets {
    /**
     * way 1: recursion: Subset problem can be transformed into combination problem
     *   time complexity: O(n * (2^n))
     *   space complexity: O(n * (2^n))
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        for (int k = 0; k <= nums.length; k++) {
            combinations(0, k, nums, new ArrayList<>(), result);
        }
        return result;
    }

    private static void combinations(int start, int k, int[] nums, List<Integer> list, List<List<Integer>> result) {
        if (k == 0) {
            result.add(new ArrayList<>(list));
        } else if (k > 0) {
            for (int i = start; i < nums.length - k + 1; i++) {
                list.add(nums[i]);
                combinations(i + 1, k - 1, nums, list, result);
                list.remove(list.size() - 1);
            }
        }
    }

    /**
     * way 2: get new subset by previous subset
     *
     *   steps:
     *     1. start from empty subset
     *     2. take 1 into consideration and add new subsets by updating existing one
     *     3. take 2 into consideration and add new subsets by updating existing one
     *     ...
     *
     *   time complexity: O(n * (2^n))
     *   space complexity: O(n * (2^n))
     */
    public static List<List<Integer>> subsets_2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        for (int num : nums) {
            int length = result.size();
            for (int i = 0; i < length; i++) {
                List<Integer> temp = new ArrayList<>(result.get(i).size() + 1);
                temp.addAll(result.get(i));
                temp.add(num);
                result.add(temp);
            }
        }
        return result;
    }

    /**
     * way 3: bit mask
     *   time complexity: O(n*(2^n))
     *   space complexity: O(n*(2^n))
     */
    public static List<List<Integer>> subsets_3(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int length = nums.length;
        assert length < 64;
        for (long i = 0; i < Math.pow(2, length); i++) {
            result.add(long2Subset(nums, i));
        }
        return result;
    }

    private static List<Integer> long2Subset(int[] nums, long value) {
        List<Integer> list = new ArrayList<>();

        int count = 0;
        while (value > 0) {
            if ((value & 1) == 1) {
                list.add(nums[count]); // if the current bit is 1, it means that the value is included
            }
            value >>= 1;
            count++;
        }
        return list;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 2, 3});
        params.add(param);

        Utils.testStaticMethod(Subsets.class
                , new HashSet<String>() {
                    {
                        add("subsets");
                        add("subsets_2");
                        add("subsets_3");
                    }
                }, params);
    }
}
