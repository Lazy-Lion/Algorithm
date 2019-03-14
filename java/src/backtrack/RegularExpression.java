package backtrack;

/**
 * leetcode 10. Regular Expression Matching
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
 *     1.'.' Matches any single character.
 *     2.'*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 *
 * Note:
 *    1.s could be empty and contains only lowercase letters a-z.
 *    2.p could be empty and contains only lowercase letters a-z, and characters like . or *.
 *
 * Ex:
 *    Input:
 *    s = "mississippi"
 *    p = "mis*is*p*."
 *    Output: false
 */
public class RegularExpression {
	private boolean isEnd = false;

	public boolean isMatch(String s, String p) {
		if(p.isEmpty())
			return s.isEmpty();

		isEnd = false;
		return isMatch(s.toCharArray(), p.toCharArray(), 0, 0);
	}

	/**
	 * (c*): 将 *和 *前的字符看做一个整体，确定匹配与否
	 * @param text  匹配字符数组
	 * @param pattern 正则表达式
	 * @param ti  字符数组匹配到的位置 index
	 * @param pi  正则表达式匹配到的位置 index
	 */
	private boolean isMatch(char[] text, char[] pattern, int ti, int pi){

		if(isEnd) return true;  //已匹配成功，不再进行递归

		/*
		* 递归结束条件：正则表达式已搜索结束：如果字符数组也已结束返回 true, 否则返回 false
		*   只当字符数组搜索结束时，并没有完成匹配，不能作为结束条件
 		*/
		if(pi == pattern.length) {
			if (ti == text.length) {
				isEnd = true;
				return true;
			}
			return false;
		}

		boolean firstMatch = (ti != text.length) && (text[ti] == pattern[pi] || pattern[pi] == '.') ;

		if(pattern.length > pi + 1 && pattern[pi + 1] == '*') {
			return (firstMatch && isMatch(text, pattern, ti + 1, pi))   // note: 使用 ti + 1 而不使用 ++ ti, 为了不影响
					                                                       //   逻辑运算后半部分里的值传递
					|| isMatch(text, pattern, ti, pi + 2);
		}else{
			return firstMatch && isMatch(text, pattern, ti + 1, pi + 1);
		}

	}

	public static void main(String[] args){
		RegularExpression r = new RegularExpression();
		System.out.println(r.isMatch("mississippi", "mis*is*p*."));  // false
		System.out.println(r.isMatch("mississippi", "mis*is*ip*.")); // true
		System.out.println(r.isMatch("aa", "a*"));       // true
		System.out.println(r.isMatch("ab", ".*"));       // true
		System.out.println(r.isMatch("ab", ".*b"));      // true
		System.out.println(r.isMatch("aab", "c*a*b"));   // true
		System.out.println(r.isMatch("a", "ab*"));       // true
		System.out.println(r.isMatch("bbbba", ".*a*a")); // true
	}
}
