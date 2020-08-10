package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 90: Subsets II
 *
 * Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).
 *
 * Note: The solution set must not contain duplicate subsets.
 *
 * Example:
 *   Input: [1,2,2]
 *   Output:
 *   [
 *     [2],
 *     [1],
 *     [1,2,2],
 *     [2,2],
 *     [1,2],
 *     []
 *   ]
 */
public class SubsetsII {
    /**
     * @see Subsets#subsets_2(int[])
     */
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());

        Arrays.sort(nums);
        int prevLength = 0;
        for (int i = 0; i < nums.length; i++) {
            int length = result.size();
            int start = 0;
            if (i > 0 && nums[i] == nums[i - 1]) { // for deduplication
                start = prevLength;
            }
            for (int j = start; j < length; j++) {
                List<Integer> temp = new ArrayList<>(result.get(j).size() + 1);
                temp.addAll(result.get(j));
                temp.add(nums[i]);
                result.add(temp);
                prevLength = length;
            }
        }
        return result;
    }

    /**
     * recursion
     */
    public static List<List<Integer>> subsetsWithDup_2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int k = 0; k <= nums.length; k++) {
            combinations(nums, new ArrayList<>(), result, 0, k);
        }

        return result;
    }

    private static void combinations(int[] nums, List<Integer> list, List<List<Integer>> result, int start, int k) {
        if (k == 0) {
            result.add(new ArrayList<>(list));
        } else { // k > 0
            for (int i = start; i < nums.length - k + 1; i++) {
                if (i > start && nums[i] == nums[i - 1]) {
                    continue;
                }
                list.add(nums[i]);
                combinations(nums, list, result, i + 1, k - 1);
                list.remove(list.size() - 1);
            }
        }
    }


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 2, 2});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{1, 2, 3});
        params.add(param);

        Utils.testStaticMethod(SubsetsII.class
                , new HashSet<String>() {
                    {
                        add("subsetsWithDup");
                        add("subsetsWithDup_2");
                    }
                }, params);
    }
}
