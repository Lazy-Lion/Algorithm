package backtrack;

/**
 * 0/1背包问题:
 *  Input:
 *    1.n items: each with a weight wi
 *    2.max capacity W of the knapsack
 *  Output:
 *    max weight that the knapsack can hold
 */
public class Knapsack01 {
	private int maxWeight = 0;

	/**
	 * 0/1背包 回溯法求解
	 * @param i 处理 item的 index, [0,n)
	 * @param cw 当前背包的总负重
	 * @param items
	 * @param n items.length
	 * @param W 背包容量
	 */
	public void knapsack(int i, int cw, int[] items, int n, int W){
		if(cw == W || i == n){
			if(cw > maxWeight)
				maxWeight = cw;
			return;
		}

		knapsack(i + 1, cw, items, n, W);  // 不装入 item[i]

		if(cw + items[i] <= W){
			knapsack(i + 1, cw + items[i], items, n, W);  // 装入 item[i]
		}
	}

	public static void main(String[] args){
		Knapsack01 k = new Knapsack01();

		int[] items = new int[]{1,2,5,6};
		k.knapsack(0, 0, items, items.length, 10);
		System.out.println(k.maxWeight);
	}
}
