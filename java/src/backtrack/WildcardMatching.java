package backtrack;

/**
 * leetcode 44. Wildcard Matching
 * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*'.
 *   1.'?' Matches any single character.
 *   2.'*' Matches any sequence of characters (including the empty sequence).
 * The matching should cover the entire input string (not partial).
 *
 * Note:
 *   1.s could be empty and contains only lowercase letters a-z.
 *   2.p could be empty and contains only lowercase letters a-z, and characters like ? or *.
 */
public class WildcardMatching {
	private boolean isEnd = false;

	/**
	 * 回溯法
	 *  Time Limit Exceeded
	 */
	public boolean isMatch(String s, String p) {
		isEnd = false;
		return isMatch(s.toCharArray(), p.toCharArray(), 0, 0);
	}

	private boolean isMatch(char[] text, char[] pattern, int ti, int pi){
		if(isEnd) return true;  //已匹配成功，不再进行递归

		if(pi == pattern.length) {
			if (ti == text.length) {
				isEnd = true;
			}
			return isEnd;
		}

		if(pattern[pi] == '?'){
			return isMatch(text, pattern, ti + 1, pi + 1);
		}else if(pattern[pi] == '*'){
			boolean isMatch = false;
			for(int k = 0; k <= text.length - ti; k ++){
				isMatch = isMatch || isMatch(text, pattern, ti + k, pi + 1);
			}
			return isMatch;
		}else if(ti < text.length && pattern[pi] == text[ti]){
			return isMatch(text, pattern, ti + 1, pi + 1);
		}
		return false;
	}

	public static void main(String[] args){
		WildcardMatching w = new WildcardMatching();
		System.out.println(w.isMatch("aa","a"));
		System.out.println(w.isMatch("aa","*"));
		System.out.println(w.isMatch("cb","?a"));
		System.out.println(w.isMatch("adceb","a*b"));
		System.out.println(w.isMatch("acdcb","a*c?b"));
//		System.out.println(w.isMatch("bbbababbbbabbbbababbaaabbaababbbaabbbaaaabbbaaaabb","*b********bb*b*bbbbb*ba"));

	}
}
