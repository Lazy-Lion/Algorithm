package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 39: Combination Sum
 *
 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.
 *
 * The same repeated number may be chosen from candidates unlimited number of times.
 *
 * Note:
 *   All numbers (including target) will be positive integers.
 *   The solution set must not contain duplicate combinations.
 *
 * Example 1:
 *   Input: candidates = [2,3,6,7], target = 7,
 *   A solution set is:
 *   [
 *     [7],
 *     [2,2,3]
 *   ]
 *
 * Example 2:
 *   Input: candidates = [2,3,5], target = 8,
 *   A solution set is:
 *   [
 *     [2,2,2,2],
 *     [2,3,3],
 *     [3,5]
 *   ]
 *
 * Constraints:
 *   1 <= candidates.length <= 30
 *   1 <= candidates[i] <= 200
 *   Each element of candidate is unique.
 *   1 <= target <= 500
 */
public class CombinationSum {
    /**
     * recursion
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        combination(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    /**
     * start for deduplication
     */
    private static void combination(int[] candidates, int target, int start, List<Integer> list, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(list));
        } else if (target > 0) {
            for (int i = start; i < candidates.length; i++) {
                list.add(candidates[i]);

                combination(candidates, target - candidates[i], i, list, result);

                list.remove(list.size() - 1);
            }
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(new int[]{2, 3, 6, 7});
        param.add(7);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{2, 3, 5});
        param.add(8);
        params.add(param);

        Utils.testStaticMethod(CombinationSum.class
                , new HashSet<String>() {
                    {
                        add("combinationSum");
                        add("combinationSum_2");
                    }
                }, params);
    }
}
