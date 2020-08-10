package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 47: Permutations II
 *
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
 *
 * Example:
 *   Input: [1,1,2]
 *   Output:
 *   [
 *     [1,1,2],
 *     [1,2,1],
 *     [2,1,1]
 *   ]
 */
public class PermutationsII {
    /**
     * recursion
     */
    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permutaion(0, nums, result);
        return result;
    }

    private static void permutaion(int start, int[] nums, List<List<Integer>> result) {
        if (start == nums.length) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                list.add(nums[i]);
            }
            result.add(list);
        } else {
            outer:
            for (int i = start; i < nums.length; i++) {
//                if (i > start && nums[i] == nums[i - 1]) { // even if it is order before, it cannot be guaranteed after exchange
//                    continue;
//                }
                for (int j = start; j < i; j++) {
                    if (nums[i] == nums[j]) {
                        continue outer;
                    }
                }

                Utils.swap(nums, start, i);
                permutaion(start + 1, nums, result);
                Utils.swap(nums, start, i);
            }
        }
    }

    /**
     * dictionary order
     */
    public static List<List<Integer>> permuteUnique_2(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> minPermutation = minPermutation(nums);
        result.add(new ArrayList<>(minPermutation));

        List<Integer> list = minPermutation;
        while (nextPermutation(list)) {
            result.add(new ArrayList<>(list));
        }
        return result;
    }

    private static List<Integer> minPermutation(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }
        return list;
    }

    private static boolean nextPermutation(List<Integer> last) {
        int i = last.size() - 1;
        boolean hasNext = false;
        while (i > 0) {
            if (last.get(i) > last.get(--i)) {
                hasNext = true;
                break;
            }
        }

        if (hasNext) {
            int minIndex = i + 1;
            int j = minIndex + 1;
            while (j < last.size()) {
                if (last.get(j) > last.get(i) && last.get(j) <= last.get(minIndex)) {
                    minIndex = j;
                }
                j++;
            }

            Utils.swap(last, i, minIndex);

            int m = i + 1;
            int n = last.size() - 1;
            while (m < n) {
                Utils.swap(last, m++, n--);
            }
        }
        return hasNext;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(new int[]{1, 1, 2});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{0, 1, 0, 0, 9});
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[]{1, 0, 0});
        params.add(param);

        Utils.testStaticMethod(PermutationsII.class
                , new HashSet<String>() {
                    {
                        add("permuteUnique");
                        add("permuteUnique_2");
                    }
                }, params);
    }
}
