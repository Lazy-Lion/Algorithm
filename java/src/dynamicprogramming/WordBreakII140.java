package dynamicprogramming;

import java.util.*;

/**
 * leetcode 140. Word Break II
 *
 *   Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to
 * construct a sentence where each word is a valid dictionary word. Return all such possible sentences.
 *
 * Note:
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 * Example 1:
 *   Input:
 *     s = "catsanddog"
 *     wordDict = ["cat", "cats", "and", "sand", "dog"]
 *   Output:
 *     [
 *       "cats and dog",
 *       "cat sand dog"
 *     ]
 * Example 2:
 *   Input:
 *     s = "pineapplepenapple"
 *     wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
 *   Output:
 *     [
 *       "pine apple pen apple",
 *       "pineapple pen apple",
 *       "pine applepen apple"
 *    ]
 *   Explanation: Note that you are allowed to reuse a dictionary word.
 * Example 3:
 *   Input:
 *     s = "catsandog"
 *     wordDict = ["cats", "dog", "sand", "and", "cat"]
 *   Output:
 *     []
 */
public class WordBreakII140 {
	List<String> result;

	/**
	 * way 1: dfs, Time Limit Exceeded
	 * wordBreak_dfs3针对此方法进行优化
	 */
	public List<String> wordBreak_dfs(String s, List<String> wordDict) {
		int n = s.length();

		// contains[i]记录从当前位置i到达下一个位置，需要填充wordDict中字符串长度，可以有多个，若不存在则为null
		List<Integer>[] contains = new ArrayList[n];

		Set<String> dict = getDict(wordDict);
		// [i,j] 左右全包括
		for(int i = 0; i < n; i ++) {
			for (int j = i; j < n; j ++) {
				if(isContains(s, i, j, dict))
					if(contains[i] == null) {
						List<Integer> temp = new ArrayList<>();
						temp.add(j - i + 1);
						contains[i] = temp;
					} else {
						contains[i].add(j - i + 1);
					}
			}
		}

		result = new ArrayList<>();
		dfs(s, 0, n, contains, new ArrayList<>());
		return result;
	}

	private void dfs(String str, int s, int n, List<Integer>[] contains, List<String> list) {
		if(s >= n) {
			StringBuilder builder = new StringBuilder();
			for(String string : list) {
				builder.append(string);
				builder.append(" ");
			}
			builder.deleteCharAt(builder.length() - 1);
			result.add(builder.toString());
			return;
		}

		if(contains[s] != null) {
			for(int l : contains[s]) {
				list.add(str.substring(s, s + l));
				dfs(str, s + l, n, contains, list);
				list.remove(list.size() - 1);
			}
		}
	}

	private Set<String> getDict(List<String> wordDict) {
		Set<String> set = new HashSet<>();
		for(String s : wordDict) {
			set.add(s);
		}
		return set;
	}

	private boolean isContains(String str, int s, int t, Set<String> dict) {
		String temp = str.substring(s, t + 1);
		return dict.contains(temp);
	}


	/**
	 * way 2: dfs, (from network)
	 */
	HashMap<Integer, List<String>> map;
	public List<String> wordBreak_dfs2(String s, List<String> wordDict) {
		map = new HashMap<>();
		return dfs2(s, s.length(), wordDict);
	}

	private List<String> dfs2(String s, int target, List<String> wordDict) {
		List<String> result = new ArrayList<>();
		if(target == 0) return new ArrayList<>(Arrays.asList(""));
		if(map.containsKey(target)) return map.get(target);    // 取消多余递归

		for(int i = 0; i < target; i ++) {
			String str = s.substring(i, target);
			if(wordDict.contains(str)) {
				List<String> temp = dfs2(s, i, wordDict);
				for(String si : temp) {
					result.add(si.length() == 0 ? str : si + " " + str);
				}
			}
		}
		map.put(target, result);
		return result;
	}


	/***
	 * way 3: dfs, (from network)
	 */
	public List<String> wordBreak_dp(String s, List<String> wordDict) {
		// 长度为n的字符串有n+1个隔板
		boolean[] f = new boolean[s.length() + 1];
		// prev[i][j]为true，表示s[j, i)是一个合法单词，可以从j处切开
		// 第一行未用
		boolean[][] prev = new boolean[s.length() + 1][s.length()];
		f[0] = true;
		for (int i = 1; i <= s.length(); ++i) {
			for (int j = i - 1; j >= 0; --j) {
				if (f[j] && wordDict.contains(s.substring(j, i))) {
					f[i] = true;
					prev[i][j] = true;
				}
			}
		}
		List<String> result = new ArrayList<>();
		List<String> path = new ArrayList<>();
		gen_path(s, prev, s.length(), path, result);
		return result;

	}
	// DFS遍历树，生成路径
	private static void gen_path(String s, boolean[][] prev,
								 int cur, List<String> path, List<String> result) {
		if (cur == 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = path.size() - 1; i >= 0; --i)
				sb.append(path.get(i)).append(' ');
			sb.deleteCharAt(sb.length()-1);
			result.add(sb.toString());
		}
		for (int i = 0; i < s.length(); ++i) {
			if (prev[cur][i]) {   // 去除无效递归
				path.add(s.substring(i, cur));
				gen_path(s, prev, i, path, result);
				path.remove(path.size()-1);
			}
		}
	}


	/**
	 * way 4: 根据 wordBreak_dp 对超时递归 wordBreak_dfs 进行改动
	 * 去除不能匹配到最后一个字符的无效递归，即递归生成路径时从最后一个字符下标开始，同时记录信息需要做相应的改动
	 */
	public List<String> wordBreak_dfs3(String s, List<String> wordDict) {
		int n = s.length();

		// contains[j]记录从前一个起始位置到位置j，需要填充wordDict中字符串长度，可以有多个，若不存在则为null
		List<Integer>[] contains = new ArrayList[n];

		Set<String> dict = getDict(wordDict);

		boolean[] canStart = new boolean[n + 1];  // 表示当前位置是否可以作为起点，用于减少无效记录数
		canStart[0] = true;
		// [i,j] 左右全包括
		for(int i = 0; i < n; i ++) {
			for (int j = i; j < n; j ++) {
				if(canStart[i] && isContains(s, i, j, dict)) {
					canStart[j + 1] = true;
					if (contains[j] == null) {
						List<Integer> temp = new ArrayList<>();
						temp.add(j - i + 1);
						contains[j] = temp;
					} else {
						contains[j].add(j - i + 1);
					}
				}
			}
		}

		result = new ArrayList<>();
		dfs3(s, n - 1, n, contains, new ArrayList<>());
		return result;
	}

	private void dfs3(String str, int s, int n, List<Integer>[] contains, List<String> list) {
		if(s < -1) return;

		if(s == -1) {
			StringBuilder builder = new StringBuilder();
			for(int i = list.size() - 1; i >= 0; i --) {
				builder.append(list.get(i)).append(" ");
			}
			builder.deleteCharAt(builder.length() - 1);
			result.add(builder.toString());
			return;
		}

		if(contains[s] != null) {
			for(int l : contains[s]) {
				list.add(str.substring(s - l + 1, s + 1));
				dfs3(str, s - l, n, contains, list);
				list.remove(list.size() - 1);
			}
		}
	}

	public static void main(String[] args) {
		WordBreakII140 w = new WordBreakII140();

		System.out.println(w.wordBreak_dfs("catsanddog", Arrays.asList("cat", "cats", "and", "sand", "dog")));
		System.out.println(w.wordBreak_dfs("pineapplepenapple", Arrays.asList("apple", "pen", "applepen", "pine", "pineapple")));
		System.out.println(w.wordBreak_dfs("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")));

		System.out.println(w.wordBreak_dfs2("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa")));


		System.out.println(w.wordBreak_dfs2("catsanddog", Arrays.asList("cat", "cats", "and", "sand", "dog")));
		System.out.println(w.wordBreak_dfs2("pineapplepenapple", Arrays.asList("apple", "pen", "applepen", "pine", "pineapple")));
		System.out.println(w.wordBreak_dfs2("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")));

		System.out.println(w.wordBreak_dp("catsanddog", Arrays.asList("cat", "cats", "and", "sand", "dog")));
		
		System.out.println(w.wordBreak_dfs3("catsanddog", Arrays.asList("cat", "cats", "and", "sand", "dog")));
		System.out.println(w.wordBreak_dfs3("pineapplepenapple", Arrays.asList("apple", "pen", "applepen", "pine", "pineapple")));
		System.out.println(w.wordBreak_dfs3("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")));

		System.out.println(w.wordBreak_dfs3("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa")));

	}
}
