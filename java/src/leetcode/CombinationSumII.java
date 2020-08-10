package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 40: Combination Sum II
 *
 * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.
 *
 * Each number in candidates may only be used once in the combination.
 *
 * Note:
 *   All numbers (including target) will be positive integers.
 *   The solution set must not contain duplicate combinations.
 *
 * Example 1:
 *   Input: candidates = [10,1,2,7,6,1,5], target = 8,
 *   A solution set is:
 *   [
 *     [1, 7],
 *     [1, 2, 5],
 *     [2, 6],
 *     [1, 1, 6]
 *   ]
 * Example 2:
 *   Input: candidates = [2,5,2,1,2], target = 5,
 *   A solution set is:
 *   [
 *     [1,2,2],
 *     [5]
 *   ]
 */
public class CombinationSumII {
    /**
     * recursion
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates); // for deduplication

        List<List<Integer>> result = new ArrayList<>();
        combination(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private static void combination(int[] candidates, int target, int start, List<Integer> list, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(list));
        } else if (target > 0) {
            for (int i = start; i < candidates.length; i++) {
                if (i > start && candidates[i - 1] == candidates[i]) { // for deduplication
                    continue;
                }
                list.add(candidates[i]);
                combination(candidates, target - candidates[i], i + 1, list, result);
                list.remove(list.size() - 1);
            }
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(new int[]{10, 1, 2, 7, 6, 1, 5});
        param.add(8);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{2, 5, 2, 1, 2});
        param.add(5);
        params.add(param);

        Utils.testStaticMethod(CombinationSumII.class
                , new HashSet<String>() {
                    {
                        add("combinationSum2");
                    }
                }, params);
    }
}
