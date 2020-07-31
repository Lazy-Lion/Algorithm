package dynamicprogramming;

/**
 * 完全背包问题：
 *   问题描述：
 *     Input: 物品种类 N, 背包容量 V, 每种物品对应大小 Ci 和 价值 Wi;
 *     Output: 装入物品使得背包总价值最大
 *   Key: 每种物品有无数多个, 所以每种物品的状态有 (0,1,2,...)
 *   状态方程: f(i, v) = max{ f(i - 1, v - k * ci) + k * wi } ( 0 =< k * ci <= v)
 *
 *   根据上述状态方程可以推出 时间复杂度为 O(V * ∑(V/Ci))
 *
 *   优化1: 每种物品最多有 V/Ci 个,可以将其转换成 0/1背包问题；时间复杂度 O(V * ∑(V/Ci))
 *   优化2：二进制思想：每种物品划分成 花费(2^k)Ci, 价值 (2^k)*Wi的物品 ((2^k)Ci <= V且 k >= 0),
 *                 所以每种物品被拆分成 log(V/Ci)个，然后转换成 0/1 背包问题；时间复杂度 O(V * ∑(log(V/Ci))
 *   优化3: f(i, v) = max{ f(i - 1, v),f(i, v - ci) + wi } ;时间复杂度 O(N*V)
 */
public class KnapsackComplete {
	/**
	 * 基于优化3的实现
	 * @param items 物品种类列表
	 * @param n 种类数
	 * @param v 背包容量
	 * @return 背包可达最大价值
	 */
	public int knapsack(Item[] items, int n, int v){
		int[] dp = new int[v + 1];

		for(int i = 0; i < v + 1; i ++){
			dp[i] = 0;
		}

		for(int i = 0; i < n; i ++){
			int cost = items[i].c;
			for(int j = cost; j < v + 1;j ++){
				dp[j] = Math.max(dp[j], dp[j - items[i].c] + items[i].w);
			}
		}

		return dp[v];
	}

	public static void main(String[] args){
		KnapsackComplete k = new KnapsackComplete();

		Item[] items = new Item[]{new Item(1,0), new Item(3,1), new Item(5,3), new Item(6,4)};
		System.out.println(k.knapsack(items, 4, 10));
	}
}
