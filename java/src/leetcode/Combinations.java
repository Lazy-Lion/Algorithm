package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 77: Combinations
 *
 * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
 *
 * Example:
 *   Input: n = 4, k = 2
 *   Output:
 *   [
 *     [2,4],
 *     [3,4],
 *     [2,3],
 *     [1,2],
 *     [1,3],
 *     [1,4],
 *   ]
 */
public class Combinations {
    private static int upper_bound;

    /**
     * way 1: recursion
     * time complexity: O( k* (n! / ( (n-k)!k! ) ) )
     * space complexity: O( (n! / ( (n-k)!k! ) ) )
     */
    public static List<List<Integer>> combine(int n, int k) {
        if (k <= 0 || n <= 0) return new ArrayList<>();

        upper_bound = n;
        List<List<Integer>> result = new ArrayList<>();
        combines_2(1, k, new ArrayList<>(), result);
        return result;
    }

    /**
     * recursion
     */
    private static void combines(int current, int k, List<Integer> list, List<List<Integer>> result) {
        if (current > upper_bound + 1) {
            return;
        }
        if (k == 0) {
            result.add(new ArrayList<>(list));
            return;
        }

        // choose current
        list.add(current);
        combines(current + 1, k - 1, list, result);
        // not choose current
        list.remove(list.size() - 1);
        combines(current + 1, k, list, result);
    }

    /**
     * optimize recursion
     * @see Combinations#combines(int, int, List, List)
     */
    private static void combines_2(int current, int k, List<Integer> list, List<List<Integer>> result) {
        if (k == 0) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = current; i <= (upper_bound - k + 1); i++) {
            list.add(i);
            combines_2(i + 1, k - 1, list, result);
            list.remove(list.size() - 1);
        }
    }


    /**
     * dictionary order
     */
    public static List<List<Integer>> combine_2(int n, int k) {
        if (k <= 0 || n <= 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> minCombination = minCombination(n, k);
        result.add(minCombination);

        List<Integer> list = minCombination;
        while (nextCombination(n, list, result)) {
            list = result.get(result.size() - 1);
        }

        return result;
    }

    private static List<Integer> minCombination(int n, int k) {
        List<Integer> minCombination = new ArrayList<>();
        for (int i = 1; i <= k; i++) {
            minCombination.add(i);
        }
        return minCombination;
    }

    private static boolean nextCombination(int n, List<Integer> last, List<List<Integer>> result) {
        // todo
        return false;
    }


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(Combinations.class
                , new HashSet<String>() {
                    {
                        add("combine");
                        add("combine_2");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(4, 2)
                ));
    }
}
