package dynamicprogramming;

import dynamicprogramming.definition.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * 多重背包问题：
 *   问题描述：
 *     Input: 物品种类 N, 背包容量 V, 每种物品对应大小 Ci 、价值 Wi 、个数 Mi;
 *     Output: 装入物品使得背包总价值最大
 *   Key: 每种物品有 Mi个, 所以每种物品的状态有 (0,1,2,...,Mi)
 *   状态方程: f(i, v) = max{ f(i - 1, v - k * ci) + k * wi } ( 0 =< k <= Mi ); 时间复杂度： O(V * ∑Mi)
 *
 *   优化1: 每种物品 Mi 个,转换成 0/1 背包问题; 时间复杂度: O(V * ∑Mi)
 *   优化2: 二进制思想转换成 0/1 背包问题; 时间复杂度: O(V * ∑logMi)
 */
public class KnapsackMultiple {
	/**
	 * 基于优化2 实现
	 * 每种物品划分成 c w 别为 tc, tw的物品
	 *   t ∈ ( 2^0, 2^1, 2^2, 2^k)
	 *   且 1 + 2^1 + ... + 2^k <= Mi => 2^(k+1) - 1 <= Mi => k <= log(Mi + 1) -1
	 *  可能求和不足 Mi,所以 t的取值还要加上 Mi + 1 - 2^(k + 1);
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

		List<Item> list = new ArrayList<>();
		// 转换成 0/1 背包问题
		for(int i = 0; i < n; i ++){
			int k = 1;
			while(k * 2 - 1 <= items[i].count){
				list.add(new Item(k * items[i].cost, k * items[i].weight));
				k = k * 2;
			}
			int temp = items[i].count + 1 - k;
			if(temp > 0){
				list.add(new Item(temp * items[i].cost, temp * items[i].weight));
			}
		}

		for(int i = 0; i < list.size(); i ++){
			int cv = list.get(i).cost;
			for(int j = v; j >= cv; j --){
				dp[j] = Math.max(dp[j], dp[j - cv] + list.get(i).weight);
			}
		}

		return dp[v];
	}

	public static void main(String[] args){
		KnapsackMultiple k = new KnapsackMultiple();

		Item[] items = new Item[]{new Item(1,2, 1), new Item(3,1, 1)
				, new Item(5,3, 2), new Item(6,4,2)};
		System.out.println(k.knapsack(items, 4, 12));

		items = new Item[]{new Item(2,100, 4), new Item(4,100, 2)};
		System.out.println(k.knapsack(items, 2, 8));

		items = new Item[]{new Item(1,1, 3), new Item(1,2, 2)};
		System.out.println(k.knapsack(items, 2, 3));
	}
}
