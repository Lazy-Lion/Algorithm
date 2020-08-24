package dynamicprogramming;

import dynamicprogramming.definition.Item;

/**
 * 0/1 背包问题：
 *   问题描述：
 *     Input: 物品数目 N和 背包容量 V, 每个物品对应大小 C(i) 和 价值 W(i);
 *     Output: 装入物品使得背包总价值最大
 *   Key： 每个物品只有两种状态: 放入，不放入 ({@link backtrack.Knapsack01} 回溯解法)
 *   状态转移方程： f(i, v) = max{ f(i - 1, v), f(i - 1, v - ci) + wi }
 *        f(i,v) 将 i个物品放入容量为 v的背包，可获得的最大总价值 ( i 取值范围 [1,n] )
 */
public class Knapsack01 {
    /**
     *
     * time complexity: O(n * capacity)
     * space complexity: O(n * capacity)
     *
     * @param items 物品列表
     * @param n 物品个数
     * @param capacity 背包容量
     * @return 背包可达最大价值
     */
    public static int knapsack(Item[] items, int n, int capacity) {
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 0; i < capacity + 1; i++) {
            dp[0][i] = 0;
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < capacity + 1; j++) {
                int cv = j - items[i - 1].cost;
                if (cv >= 0) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][cv] + items[i - 1].weight);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][capacity];
    }

    /**
     * time complexity: O(n * v)
     * space complexity: O(v)
     * 基于 {@link #knapsack(Item[], int, int)}方法进行空间优化：
     *    {@link #knapsack(Item[], int, int)}中定义状态数组：int[][] dp = new int[n + 1][v + 1];
     *    可以看到 dp[i][v] 总是基于 dp[i-1][v']生成 (v' <= v)，且不依赖于 dp[i-k][v''] (k > 1), 返回结果也只需要 dp[n][v]
     *    所以可以将 状态数组优化成一维数组 int[] dp = new int[v + 1];
     */
    public static int knapsack_2(Item[] items, int n, int capacity) {
        int[] dp = new int[capacity + 1];
        for (int i = 0; i < capacity + 1; i++) {
            dp[i] = 0;
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = capacity; j >= 0; j--) {  // 逆序生成，否则 dp[i][v']就会基于dp[i][v'']计算
                // 内部j循环可以进行优化: j ← v to items[i - 1].cost
                int cv = j - items[i - 1].cost;
                if (cv >= 0) {
                    dp[j] = Math.max(dp[j], dp[cv] + items[i - 1].weight);
                }
            }
        }

        return dp[capacity];
    }

    public static void main(String[] args) {
        Knapsack01 k = new Knapsack01();
        Item[] items = new Item[]{new Item(1, 1), new Item(2, 1), new Item(5, 3), new Item(6, 4)};
        System.out.println(k.knapsack(items, 4, 10));
        System.out.println(k.knapsack_2(items, 4, 10));
    }
}
