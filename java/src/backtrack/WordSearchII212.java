package backtrack;
import java.util.*;

/**
 * 212. Word Search II
 * Given a 2D board and a list of words from the dictionary, find all words in the board.
 *
 *   Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
 * horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
 * Example:
 *   Input:
 *     board = [
 *       ['o','a','a','n'],
 *       ['e','t','a','e'],
 *       ['i','h','k','r'],
 *       ['i','f','l','v']
 *      ]
 *     words = ["oath","pea","eat","rain"]
 *  Output: ["eat","oath"]
 *
 * Note:
 *   All inputs are consist of lowercase letters a-z.
 *   The values of words are distinct.
 */
public class WordSearchII212 {
	private static char[][] input;
	private static int n;
	private static int m;
	private static boolean[][] visited;

	private static final int COUNT = 26;

	/**
	 * way1: 类似 WordSearch79 递归求解
	 */
	public static List<String> findWords(char[][] board, String[] words) {
		List<String> result =  new ArrayList<>();

		if(words.length == 0 || words.length == 1 && words[0].isEmpty()) return result;

		n = board.length;
		if(n == 0) return result;
		m = board[0].length;

		input = board;

		String word;
		Loop: for(int k = 0; k < words.length; k ++) {
			word = words[k];
			visited = new boolean[n][m];
			for(int i = 0; i < n; i ++) {
				for(int j = 0; j < m; j ++) {
					if(dfs(i, j, 0, word)) {
						result.add(word);
						continue Loop;
					}
				}
			}
		}

		return result;
	}

	private static boolean dfs(int x, int y, int shift, String word) {
		if(x < 0 || x >= n || y < 0 || y >= m || visited[x][y]) return false;

		boolean bool;
		if(shift == word.length() - 1) {
			bool = word.charAt(shift) == input[x][y];
			if(bool) visited[x][y] = true;
			return bool;
		}

		if(word.charAt(shift) != input[x][y])
			return false;
		else {
			visited[x][y] = true;
			bool = dfs(x, y - 1, shift + 1, word) || dfs(x, y + 1, shift + 1, word)
					|| dfs(x - 1, y, shift + 1, word) || dfs(x + 1, y, shift + 1, word);
			visited[x][y] = false;
			return bool;
		}
	}


	/**
	 * way2: 优化时间复杂度
	 * 构造Trie树，前缀字符串不重复计算
	 */
	public static List<String> findWordsOptimize(char[][] board, String[] words) {
		List<String> result =  new ArrayList<>();

		if(words.length == 0 || words.length == 1 && words[0].isEmpty()) return result;

		n = board.length;
		if(n == 0) return result;
		m = board[0].length;

		input = board;

		TrieNode root = constructTrie(words);
		visited = new boolean[n][m];

		for(int i = 0; i < n; i ++) {
			for(int j = 0; j < m;j ++) {
				dfs2(i, j, root, result);
			}
		}
		return result;
	}

	private static void dfs2(int x, int y, TrieNode root, List<String> result) {
		if(root.isEnd && !result.contains(root.word)) {
			result.add(root.word);
		}

		if(x < 0 || x >= n || y < 0 || y >= m || visited[x][y]) return;

		char c = input[x][y];
		TrieNode node = root.next[c - 'a'];
		if(node != null) {
			visited[x][y] = true;
			dfs2(x - 1, y, node, result);
			dfs2(x + 1, y, node, result);
			dfs2(x, y - 1, node, result);
			dfs2(x, y + 1, node, result);
			visited[x][y] = false;
		}
	}

	private static TrieNode constructTrie(String[] words) {
		TrieNode root = new TrieNode();
		for(String word : words) {
			insert(root, word);
		}
		return root;
	}

	private static void insert(TrieNode root, String word) {
		TrieNode node = root;

		int idx;
		for(int i = 0; i < word.length(); i ++) {
			idx = word.charAt(i) - 'a';
			if(node.next[idx] == null)
				node.next[idx] = new TrieNode();
			if(i == word.length() - 1) {
				node.next[idx].isEnd = true;
				node.next[idx].word = word;
			}
			node = node.next[idx];
		}
	}


	static class TrieNode {
		private boolean isEnd = false;
		private String word = ""; // isEnd时有效，表示完整字符串
		private TrieNode[] next = new TrieNode[COUNT];
	}

	public static void main(String[] args) {
		char[][] board = new char[][] {
				{'o','a','a','n'},
				{'e','t','a','e'},
				{'i','h','k','r'},
				{'i','f','l','v'}
		};
		System.out.println(findWords(board, new String[] {"oath","pea","eat","rain"}));
		System.out.println(findWordsOptimize(board, new String[] {"oath","pea","eat","rain"}));

		board = new char[][] {
				{'a'},
				{'a'}
		};
		System.out.println(findWords(board, new String[] {"a"}));
		System.out.println(findWordsOptimize(board, new String[] {"a"}));

		board = new char[][] {
				{'a', 'b'},
				{'c', 'd'}
		};
		System.out.println(findWords(board, new String[] {"ab","cb","ad","bd","ac","ca","da","bc","db","adcb","dabc","abb","acb"}));
		System.out.println(findWordsOptimize(board, new String[] {"ab","cb","ad","bd","ac","ca","da","bc","db","adcb","dabc","abb","acb"}));

		board = new char[][] {
				{'a', 'b'},
				{'a', 'a'}
		};
		System.out.println(findWords(board, new String[] {"aba","baa","bab","aaab","aaa","aaaa","aaba"}));
		System.out.println(findWordsOptimize(board, new String[] {"aba","baa","bab","aaab","aaa","aaaa","aaba"}));
	}
}
