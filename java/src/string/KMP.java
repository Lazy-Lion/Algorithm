package string;

/**
 * Kunth Morris Pratt算法 (KMP, 由三位作者的名字命名):
 *  keys:
 *    1.不同于Boyer-Moore算法比较字符时从右向左，KMP比较时从左向右
 *    2.坏字符：同 BM算法
 *    3.好前缀
 *    4.好前缀的后缀子串
 *
 *  思路：模式串在主串上滑动，KMP算法类似 BM算法，也是优化滑动距离
 *    1.从左向右比较时，当遇到坏字符时，也可以确定好前缀 prefix (或者没有好前缀，直接滑动一位)
 *    2.如果存在模式串的前缀子串和 prefix的后缀子串相等(取最长)，即滑动模式串的前缀子串到 prefix的后缀子串当前的位置，否则
 *      滑动 m (模式串长度)
 *
 *  如上所述：
 *    前缀子串和后缀子串对应只与模式串有关，可以预处理
 */
public class KMP {
	public int match(String text, String pattern){
		int n = text.length();
		int m = pattern.length();

		if(n < 0 || m < 0 || n < m) return -1;
		if(m == 0) return 0;

		int[] next = getNext(pattern, m);

		// matching 实现：不回溯主串下标i，只改变模式串下标j
		int j = 0;
		for(int i = 0; i < n; i ++){
			while(j > 0 && pattern.charAt(j) != text.charAt(i)){
				j = next[j - 1]	+ 1;
			}
			if(pattern.charAt(j) == text.charAt(i)){
				j ++;
			}
			if(j == m){
				return i - m + 1;
			}
		}
		return -1;
	}

	public int match2(String text, String pattern){
		int n = text.length();
		int m = pattern.length();

		if(n < 0 || m < 0 || n < m) return -1;
		if(m == 0) return 0;

		int[] next = getNext(pattern, m);

		// matching 实现：类似Boyer-Moore算法，滑动窗口方式，相较于match(text,pattern)存在多于的比较
		for(int i = 0; i < n - m + 1;){
			int j = 0;
			for(; j < m; j ++){
				if(pattern.charAt(j) != text.charAt(i + j))
					break;
			}
			if(j == m) return i; // 匹配成功

			if(j == 0){  // 没有好前缀
				i ++;
				continue;
			}
			int v = next[j - 1];
			if( v == -1)
				i += j;
			else
				i += (j - v);
		}
		return -1;
	}

	/**
	 * 失效函数
	 * @param pattern 模式串
	 * @param m 模式串长度
	 * @return int[] next, 描述好前缀中后缀子串和模式串前缀子串之间的关系
	 *         假设模式串 p, next[i]表示好前缀为模式串的子串 p[0,i], 当前好前缀中与模式串前缀子串相等的最长后缀子串对应的好前缀
	 *         结尾下标值为 next[i];
	 *         next[0] = -1 , p[0,0]没有后缀子串
	 *
	 *     例 模式串 a b a b a c d
	 *    好前缀        |   好前缀结尾下标   |  后缀字符串最长匹配的好前缀结尾下标 |  next
	 *    a                      0                   -1 (not exist)              next[0] = -1
	 *    ab                     1                   -1                          next[1] = -1
	 *    aba                    2                   0                           next[2] = 0
	 *    abab                   3                   1                           next[3] = 1
	 *    ababa                  4                   2                           next[4] = 2
	 *    ababac                 5                   -1                          next[5] = -1
	 *
	 *
	 *    next[i] 通过next[i - 1]计算得到：v = next[i-1]
	 *      1. 假设 p[i] == p[v + 1]: next[i] = next[i - 1] + 1
	 *      2. 假设 p[i] != p[v + 1]: 计算 p[0,i-1]的后缀字符串次长匹配的好前缀结尾下标 u, 如果p[i] == p[u + 1]，则
	 *         next[i] = u + 1, 否则再计算 p[0,i-1]的更短的好前缀结尾下标 u'，直到 p[i] == p[u' + 1], 或 u' = -1 (表示不存在)
	 *      3. 对于上述步骤2，求次长匹配的好前缀结尾下标 u，u肯定是 v的子串，所以可以转换成求好前缀 p[0, v]的最长匹配，即 next[v]
	 */
	private int[] getNext(String pattern, int m){
		int[] next = new int[m - 1];
		next[0] = -1;

		int k = -1;
		for(int i = 1; i < m - 1; i ++){
			while(k != -1 && pattern.charAt(k + 1) != pattern.charAt(i)){
				k = next[k];
			}
			if(pattern.charAt(k + 1) == pattern.charAt(i)) {
				k ++;
			}

			next[i] = k;
		}
		return next;
	}
}
