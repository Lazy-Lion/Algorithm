package array;

/**
 * 458. Poor Pigs
 *   There are 1000 buckets, one and only one of them is poisonous, while the rest are filled with water. They all
 * look identical. If a pig drinks the poison it will die within 15 minutes. What is the minimum amount of pigs you
 * need to figure out which bucket is poisonous within one hour?
 *
 * Answer this question, and write an algorithm for the general case.
 *
 * General case:
 *   If there are n buckets and a pig drinking poison will die within m minutes, how many pigs (x) you need to figure
 * out the poisonous bucket within p minutes? There is exactly one bucket with poison.
 *
 * Note:
 *   A pig can be allowed to drink simultaneously on as many buckets as one would like, and the feeding takes no time.
 *   After a pig has instantly finished drinking buckets, there has to be a cool down time of m minutes. During this
 * time, only observation is allowed and no feedings at all.
 *   Any given bucket can be sampled an infinite number of times (by an unlimited number of pigs).
 */
public class PoorPigs458 {
	/**
	 * 解题思路： 1000个桶(b)，中毒时间(m) 15min, 测试时间(p) 60min
	 *    1.一只猪可以判断5个桶中哪个有毒 (b m b m b m b m b), 60 / 15 + 1 = 5
	 *    2.两只猪的情况：	两只猪同时进行
	 *       第一只猪判断哪一行有毒：喝下第一行桶里的水，15min后喝下第二行桶里的水，...
	 *       第二只猪判断哪一列有毒：喝下第一列桶里的水，15min后喝下第二列桶里的水, ...
	 *      => 两只猪可以判断 5 * 5 = 25个桶
	 *            1 2 3 4 5
	 *         1  ○ ○ ○ ○ ○
	 *         2  ○ ○ ○ ○ ○
	 *         3  ○ ○ ○ ○ ○
	 *         4  ○ ○ ○ ○ ○
	 *         5  ○ ○ ○ ○ ○
	 *   3.k只猪可以判断 5^k个桶
	 *   4.归纳：(p/m + 1) ^ k >= b， 求 k
	 */
	public static int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
		int n = minutesToTest / minutesToDie + 1;
		int c = 0, sum = 1;

		while(sum < buckets) {
			c ++;
			sum = sum * n;
		}

		return c;
	}

	public static void main(String[] args) {
		System.out.println(poorPigs(1000, 15, 60));
	}
}
