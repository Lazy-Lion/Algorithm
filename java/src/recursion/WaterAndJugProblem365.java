package recursion;

/**
 * leetcode 365. Water and Jug Problem
 *   You are given two jugs with capacities x and y litres. There is an infinite amount of water supply available.
 * You need to determine whether it is possible to measure exactly z litres using these two jugs.
 *
 * If z liters of water is measurable, you must have z liters of water contained within one or both buckets by the end.
 *
 * Operations allowed:
 *   Fill any of the jugs completely with water.
 *   Empty any of the jugs.
 *   Pour water from one jug into another till the other jug is completely full or the first jug itself is empty.
 * Example 1: (From the famous "Die Hard" example)
 *   Input: x = 3, y = 5, z = 4
 *   Output: True
 * Example 2:
 *   Input: x = 2, y = 6, z = 5
 *   Output: False
 */
public class WaterAndJugProblem365 {
	/**
	 * 数学题：
	 * 问题转换：用两个杯子(x,y)向指定容器倒水和舀水，判断容器内的水是否刚好为 z (z最大为 x + y, 两个杯子都装满)
	 *  m * x + n * y = z  (x + y >= z)
	 *
	 *  裴蜀定理： m * x + n * y = z 有整数解时当且仅当m是a及b的最大公约数d的倍数
	 */
	public static boolean canMeasureWater(int x, int y, int z) {
		return z == 0 || (x + y >= z && z % gcd(x,y) == 0);
	}

	/**
	 * 求最大公约数 greatest common divisor
	 * 辗转相除法
	 */
	private static int gcd(int x, int y) {
		return y == 0 ? x : gcd(y, x % y);
	}

	public static void main(String[] args) {
		System.out.println(canMeasureWater(3,5,4));
		System.out.println(canMeasureWater(3,5,7));
		System.out.println(canMeasureWater(2,6,5));
	}
}
