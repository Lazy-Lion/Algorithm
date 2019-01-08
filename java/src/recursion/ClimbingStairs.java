package recursion;

import java.util.HashMap;

/**
 * leetcode 70
 * 问题描述：
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

    private HashMap<Integer, Integer> map = new HashMap<>();
    /**
     * 递归：
     *  1. 递归公式： f(n) = f(n-1) + f(n-2)
     *  2. 终止条件： f(1) = 1, f(2) = 2
     * @param n
     * @return
     */
    public int climbStairs(int n){

        if(n == 1) return 1;
        if(n == 2) return 2;

        return climbStairs(n - 1) + climbStairs(n - 2);
    }


    /**
     * 递归改进：
     *   避免重复计算
     * @param n
     * @return
     */
    public int climbStairsImprove(int n){

        if(n == 1) return 1;
        if(n == 2) return 2;

        if(map.containsKey(n)) return map.get(n);

        int result = climbStairs(n - 1) + climbStairs(n - 2);
        map.put(n, result);

        return result;
    }

    /**
     * 迭代方式: 递归改写
     * @param n
     * @return
     */
    public int climbStairsIteration(int n){
        if(n == 1) return 1;
        if(n == 2) return 2;

        int ret1 = 1;
        int ret2 = 2;
        int result = 0;
        for(int i = 3; i <= n; i ++){
            result = ret1 + ret2;
            ret1 = ret2;
            ret2 = result;
        }

        return result;
    }

}
