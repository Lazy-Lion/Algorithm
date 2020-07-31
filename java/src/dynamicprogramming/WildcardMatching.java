package dynamicprogramming;

/**
 * leetcode 44: 参照 backtrack.WildcardMatching 使用回溯法求解，Time Limit Exceeded
 *   通过对回溯法分析，可以看出会进行很多重复计算 (可自行画出递归树分析)
 *   以下通过动态规划实现
 *
 *   状态方程： f(i,j) - i表示 pattern string的第 i个字符, j表示 input string的第 j个字符, f(i,j)表示当前是否匹配
 *
 *                f(i - 1, j - 1)    if p[i] == s[j] or p[i] == '?'
 *   f(i, j) =
 *               f(i - 1, j) || f(i, j - 1)  if p[i] == '*'
 */
public class WildcardMatching {
	/**
	 * 时间复杂度 O(n * m)
	 * 空间复杂度 O(n * m)
	 * @param s  input string, s.length() == n
 	 * @param p  pattern string, p.length == m
	 */
	public boolean isMatch(String s, String p) {
		int n = s.length();
		int m = p.length();

		boolean[][] dp = new boolean[m + 1][n + 1];
		dp[0][0] = true;

		for(int i = 1; i < m + 1; i ++){
			for(int j = 0; j < n + 1; j ++){
				if(j >= 1 && (p.charAt(i - 1) == s.charAt(j - 1) || p.charAt(i - 1) == '?')){
						dp[i][j] = dp[i - 1][j - 1];
				}else if(p.charAt(i - 1) == '*'){
					if (j >= 1)
						dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
					else
						dp[i][j] = dp[i - 1][j];
				}
			}
		}
		return dp[m][n];
	}

	/**
	 * 贪心求解，先假设 * 不匹配字符，继续往后匹配，如果无法匹配则回退到最近的一个 *，使得其匹配字符 +1,重复执行
	 *    (由于 * 可以匹配 0至多个任意字符，所以前面 * 少匹配的字符都可以使用最近的一个 * 匹配)
	 *
	 * 时间复杂度 低于 O(n * m)
	 * 空间复杂度 O(1)
	 */
	public boolean isMatch2(String s, String p) {
		int n = s.length();
		int m = p.length();

		if(p.isEmpty())
			return s.isEmpty();

		int i = 0, j = 0;
		int asteriski = 0;  // 当前匹配到模式串的最后一个 '*', 对应输入串位置
		int asteriskj = 0;  // 当前匹配到模式串的最后一个 '*', 对应模式串位置
		boolean hasAsterisk = false;  //标记模式串是否有 '*'
		while(i < n){
			if(j < m && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')){  // 一对一匹配
				i ++;
				j ++;
			}else if(j < m && p.charAt(j) == '*'){  // 模式串字符为 '*',默认不匹配字符，并记录'*'位置
				hasAsterisk = true;
				asteriski = i;
				asteriskj = j ++;
			}else if(hasAsterisk){  // 模式串无法匹配输入串，回退到最近的 '*',使 '*'多匹配一个字符
				i = ++ asteriski;
				j = asteriskj + 1;
			}else{
				break;
			}
		}

		while(j < m && p.charAt(j) == '*')  // 忽略模式串中多余的 *
			j ++;

		if(i == n && j == m)
			return true;
		return false;
	}

	public static void main(String[] args){
		WildcardMatching w = new WildcardMatching();
		System.out.println("动态规划解法：");
		System.out.println(w.isMatch("aa","a"));
		System.out.println(w.isMatch("aa","*"));
		System.out.println(w.isMatch("cb","?a"));
		System.out.println(w.isMatch("adceb","a*b"));
		System.out.println(w.isMatch("acdcb","a*c?b"));
		System.out.println(w.isMatch("aaaa","**a"));
		System.out.println(w.isMatch("","*"));
		System.out.println(w.isMatch("bbbababbbbabbbbababbaaabbaababbbaabbbaaaabbbaaaabb","*b********bb*b*bbbbb*ba"));

		System.out.println("贪心解法：");
		System.out.println(w.isMatch2("aa","a"));
		System.out.println(w.isMatch2("aa","*"));
		System.out.println(w.isMatch2("cb","?a"));
		System.out.println(w.isMatch2("adceb","a*b"));
		System.out.println(w.isMatch2("acdcb","a*c?b"));
		System.out.println(w.isMatch2("aaaa","**a"));
		System.out.println(w.isMatch2("","*"));
		System.out.println(w.isMatch2("bbbababbbbabbbbababbaaabbaababbbaabbbaaaabbbaaaabb","*b********bb*b*bbbbb*ba"));

	}
}
