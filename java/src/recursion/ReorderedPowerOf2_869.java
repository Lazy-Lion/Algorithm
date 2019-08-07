package recursion;

import sort.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 869. Reordered Power of 2
 *   Starting with a positive integer N, we reorder the digits in any order (including the original order) such
 * that the leading digit is not zero.
 *
 * Return true if and only if we can do this in a way such that the resulting number is a power of 2.
 *
 * Example 1:
 *   Input: 1
 *   Output: true
 * Example 2:
 *   Input: 10
 *   Output: false
 * Example 3:
 *   Input: 16
 *   Output: true
 * Example 4:
 *   Input: 24
 *   Output: false
 * Example 5:
 *   Input: 46
 *   Output: true
 * Note:
 *   1 <= N <= 10^9
 */
public class ReorderedPowerOf2_869 {
	static Set<Integer> power;  // 记录已计算的2^power
	static int current_max_power; // 记录已计算的2^power的最大值

	// 排列后的整数位数必然与原整数相同，且不以0开头，所以可以设置power存储的上下界，减少无效数据
	static int upper_bound;  // power中存储数据的上界
	static int lower_bound;  // power中存储数据的下界

	/**
	 * way 1:
	 * 全排列问题
	 * 时间复杂度： m! * logN , m为整数的位数， m!为全排列个数，logN为验证一个全排列是否为2的倍数的消耗时间
	 */
	public static boolean reorderedPowerOf2(int N) {
		if(N <= 0) return false;

		int[] digits = getDigits(N);
		power = new HashSet<>();
		current_max_power = 1;
		power.add(1);

		// 计算上下界
		lower_bound = 1;
		upper_bound = 9;
		for(int i = 0; i < digits.length - 1; i ++) {
			lower_bound = lower_bound * 10;
			upper_bound = upper_bound * 10 + 9;
		}

		if(upper_bound < 0) upper_bound = Integer.MAX_VALUE;  // 整数越界处理

		return permutations(digits, 0);
	}

	private static int[] getDigits(int n) {
		int[] temp = new int[10];
		int val, i = 0;
		while(n > 0) {
			val = n % 10;
			n = n / 10;
			temp[i ++] = val;
		}

		int[] digits = new int[i];
		for(int k = i - 1; k >= 0; k --) {
			digits[i - k - 1] = temp[k];
		}
		return digits;
	}

	private static boolean permutations(int[] digits, int start) {
		if(start == digits.length) {
			return isPowerOf2(digits);
		}

		for(int i = start; i < digits.length; i ++) {
			Utils.swap(digits, i, start);
			if(permutations(digits, start + 1))
				return true;
			Utils.swap(digits, i, start);
		}
		return false;
	}

	private static boolean isPowerOf2(int[] digits) {
		if(digits[0] == 0) return false;

		int val = 0;
		for(int i = 0; i < digits.length; i ++) {
			val = val * 10 + digits[i];
		}

		if(val <= current_max_power) return power.contains(val);

		while(current_max_power < val) {
			current_max_power = current_max_power << 1;
			if(current_max_power >= lower_bound && current_max_power <= upper_bound)
				power.add(current_max_power);
		}

		return current_max_power == val;
	}

	/**
	 * way 2 :
	 *  N∈ [1, 10^9],   10^9 < 2^30
	 *  统计N中 0,1,2,3,4,5,6,7,8,9 的个数，将其与 pow(2, i)中各个数字的个数做比较 （i ∈ [0,31)）
	 *  如果相同则存在数字的组合结果 N'，使得 N' = pow(2, i)
	 */
	public static boolean reorderedPowerOf2_2(int N) {
		if(N <= 0) return false;

		int[] digits = getDigitsCount(N);
		for(int i = 0; i < 31; i ++) {  // 使用移位运算替代 pow(2,i)
			if(Arrays.equals(digits, getDigitsCount(1 << i)))
				return true;
		}

		return false;
	}

	// 统计整数中各个数字的个数
	// 例:123521, 返回 {0,2,2,1,0,1,0,0,0,0}
	private static int[] getDigitsCount(int n) {
		int[] ans = new int[10];
		while(n > 0) {
			ans[n % 10] ++;
			n = n / 10;
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println(reorderedPowerOf2(1));
		System.out.println(reorderedPowerOf2(10));
		System.out.println(reorderedPowerOf2(16));
		System.out.println(reorderedPowerOf2(24));
		System.out.println(reorderedPowerOf2(46));
		System.out.println(reorderedPowerOf2(218));
		System.out.println(reorderedPowerOf2(100000842));

		System.out.println(reorderedPowerOf2_2(1));
		System.out.println(reorderedPowerOf2_2(10));
		System.out.println(reorderedPowerOf2_2(16));
		System.out.println(reorderedPowerOf2_2(24));
		System.out.println(reorderedPowerOf2_2(46));
		System.out.println(reorderedPowerOf2_2(218));
		System.out.println(reorderedPowerOf2_2(100000842));
	}
}
