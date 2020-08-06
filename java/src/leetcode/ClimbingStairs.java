package leetcode;

import java.util.HashMap;

/**
 * leetcode 70: Climbing Stairs
 *
 * You are climbing a stair case. It takes n steps to reach to the top.
 *
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * Note: Given n will be a positive integer.
 *
 * Example:
 *
 * Input: 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 */
public class ClimbingStairs {

    private static HashMap<Integer, Integer> map = new HashMap<>();

    /**
     * Recursion：
     *  1. recursion formula： f(n) = f(n-1) + f(n-2)
     *  2. termination condition： f(1) = 1, f(2) = 2
     *
     * time complexity: O(n)
     * space complexity: O(n) - considering recursive stack
     */
    public static int climbStairs(int n) {

        if (n == 1) return 1;
        if (n == 2) return 2;

        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    /**
     * recursion improvement：
     *   avoid double counting
     */
    public static int climbStairs_2(int n) {

        if (n == 1) return 1;
        if (n == 2) return 2;

        if (map.containsKey(n)) return map.get(n);

        int result = climbStairs(n - 1) + climbStairs(n - 2);
        map.put(n, result);

        return result;
    }

    /**
     * iterative method
     *
     * time complexity: O(n)
     * space complexity: O(1)
     */
    public static int climbStairs_3(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        int v1 = 1;
        int v2 = 2;
        int result = 0;
        for (int i = 3; i <= n; i++) {
            result = v1 + v2;
            v1 = v2;
            v2 = result;
        }
        return result;
    }


}
