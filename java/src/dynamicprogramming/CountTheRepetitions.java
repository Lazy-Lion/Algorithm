package dynamicprogramming;

/**
 * 466. Count The Repetitions
 *   Define S = [s,n] as the string S which consists of n connected strings s. For example, ["abc", 3] ="abcabcabc".
 * On the other hand, we define that string s1 can be obtained from string s2 if we can remove some characters from s2
 * such that it becomes s1. For example, “abc” can be obtained from “abdbec” based on our definition, but it can not be
 * obtained from “acbbe”.
 * You are given two non-empty strings s1 and s2 (each at most 100 characters long) and two integers 0 ≤ n1 ≤ 106 and
 * 1 ≤ n2 ≤ 106. Now consider the strings S1 and S2, where S1=[s1,n1] and S2=[s2,n2]. Find the maximum integer M such
 * that [S2,M] can be obtained from S1.
 *
 * Example:
 *   Input:
 *    s1="acb", n1=4
 *    s2="ab", n2=2
 *   Return:
 *    2
 */
public class CountTheRepetitions {
	/**
	 * brute force
	 * Time Limit Exceeded
	 */
	public static int getMaxRepetitionsBF(String s1, int n1, String s2, int n2) {
		int len1 = s1.length(), len2 = s2.length();
		if(n1 * len1 < n2 * len2) return 0;
		int i = 0, j = 0, k = 0;
		int result = 0;
		int p = 0;

		while(i < n1 * len1){
			for(; k < len2 && i < n1 * len1;){
				if(s1.charAt(i % len1) == s2.charAt(k)){
					k ++;
				}
				i ++;
			}
			j ++;
			if(k == len2 && j % n2 == 0){
				p ++;
			}
			if(i % len1 == 0 && k == len2){
				result = n1 / (i / len1) * j / n2;
				break;
			}
			k = 0;
		}

		if(result == 0) return p;

		i = result * (n2 * len2);
		j = 0;
		while(i < n1 * len1){
			k = 0;
			for(; k < len2 && i < n1 * len1;){
				if(s1.charAt(i % len1) == s2.charAt(k)){
					k ++;
				}
				i ++;
			}
			j ++;
			if(k == len2 && j % n2 == 0){
				result ++;
			}
		}
		return result;
	}


	/**
	 * dp + 倍增思想（利用二进制思想, https://blog.csdn.net/JarjingX/article/details/8180560）
	 * 状态方程 dp[i][j] = dp[i][j - 1] + dp[ (i + dp[i][j - 1]) % len1 ][j - 1];
	 *   dp[i][j] 表示 从s1第i个位置开始匹配 2^j 个 s2 需要的长度
	 *   len1 = s1.length()
	 */
	public static int getMaxRepetitions(String s1, int n1, String s2, int n2){

		int len1 = s1.length(), len2 = s2.length();
		int total = n1 * len1;

		int col = (int)Math.ceil(Math.log(total / len2)) + 1;

		if(len1 <= 0 || col <= 0) return 0;
		int[][] dp = new int[len1][col];

		int k, m, c;
		boolean flag = false;
		for(int i = 0; i < len1; i ++){  // 初始化dp[i][0]

			k = i;  // s1的开始位置，可扩展多个s1
			m = 0;  // s2的开始位置，只匹配一个s2
			c = 0;  // count, s1从开始位置匹配一个s2需要的长度
			while(m < len2 && k < total){
				if(s1.charAt(k % len1) == s2.charAt(m)){
					m ++;
				}
				k ++;
				c ++;
			}
			if(m == len2){  // 成功匹配一个s2
				flag = true;
				dp[i][0] = c;
			}
		}


		if(!flag) return 0;  // 如果没有成功匹配过一个s2，直接返回

		for(int j = 1; j < col; j ++){  // 根据状态方程计算状态数组
			for(int i = 0; i < len1; i ++){
				int index = (i + dp[i][j - 1]) % len1;
				if(dp[i][j - 1] != 0 && dp[index][j - 1] !=0)  // 如果存在值为0，表示s1*len1长度的字符串已无法匹配
											                   // 2^(j-1)个s2
					dp[i][j] = dp[i][j - 1] + dp[index][j - 1];
			}
		}

		// 1. 从位置i=0开始, 消耗字符数c = 0
		// 2. 找到最大的消耗字符数dp[i][j]，使得c + dp[i][j] <= s1*n1，此时匹配s2个数为 2^j
		// 3. 将开始位置i设置为(i + dp[i][j]) % len1，消耗字符数 c = c + dp[i][j]， 重复步骤2
		int index = 0, result = 0;
		for(int j = col - 1; j >= 0; j --){
			while(dp[index % len1][j] > 0 && dp[index % len1][j] + index <= total){
				result += Math.pow(2, j);
				index = index + dp[index % len1][j];
			}
		}
		return result / n2;
	}


	public static void main(String[] args){
		System.out.println(getMaxRepetitions("acb", 4, "ab", 2));
		System.out.println(getMaxRepetitions("aaa", 3, "aa", 1));
		System.out.println(getMaxRepetitions("niconiconi",99981, "nico", 81));
		System.out.println(getMaxRepetitions("ecbafedcba",3, "abcdef", 1));
		System.out.println(getMaxRepetitions("lovelive",0, "lovelive", 10));

	}
}
