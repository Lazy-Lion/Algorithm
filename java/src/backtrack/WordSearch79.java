package backtrack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * leetcode 79. Word Search
 *
 * Given a 2D board and a word, find if the word exists in the grid.
 *
 *   The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
 * horizontally or vertically neighboring. The same letter cell may not be used more than once.
 *
 * Example:
 *   board =
 *    [
 *     ['A','B','C','E'],
 *     ['S','F','C','S'],
 *     ['A','D','E','E']
 *   ]
 *
 * Given word = "ABCCED", return true.
 * Given word = "SEE", return true.
 * Given word = "ABCB", return false.
 */
public class WordSearch79 {
	private static final String SEPARATOR = "/";
	private static final int[] UPPER = {-1, 0};
	private static final int[] DOWN = {1, 0};
	private static final int[] LEFT = {0, -1};
	private static final int[] RIGHT = {0, 1};
	private static boolean result;

	private static boolean[][] visited;

	/**
	 * dfs
	 */
	public static boolean exist(char[][] board, String word) {
		result = false;

		int n = board.length;
		if(n == 0 || word.isEmpty()) return false;
		int m = board[0].length;

		List<int[]> startList = getStart(board, word.charAt(0), n, m);

		if(word.length() == 1) return !startList.isEmpty();

		Set<String> set = new HashSet<>();

		for(int[] start : startList) {
			dfs(start, 0, board, word, set, n, m);
		}

		return result;
	}

	private static void dfs(int[] point, int idx, char[][] board, String word, Set<String> set, int n, int m) {
		if(point[0] >= n || point[0] < 0 || point[1] >= m || point[1] < 0)
			return;
		if(idx >= word.length() || result) return;

		String s = point[0] + SEPARATOR + point[1];
		if(set.contains(s)) return;

		boolean isEqual = word.charAt(idx) ==  board[point[0]][point[1]];

		if(idx == word.length() - 1 && isEqual)
			result = true;

		if(!isEqual) return;

		set.add(s);
		dfs(new int[] {point[0] + UPPER[0], point[1] + UPPER[1]}, idx + 1, board, word, set, n, m);
		dfs(new int[] {point[0] + DOWN[0], point[1] + DOWN[1]}, idx + 1, board, word, set, n, m);
		dfs(new int[] {point[0] + LEFT[0], point[1] + LEFT[1]}, idx + 1, board, word, set, n, m);
		dfs(new int[] {point[0] + RIGHT[0], point[1] + RIGHT[1]}, idx + 1, board, word, set, n, m);
		set.remove(s);
	}

	private static List<int[]> getStart(char[][] board, char c, int n, int m) {
		List<int[]> list = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j ++) {
				if(board[i][j] == c)
					list.add(new int[] {i, j});
			}
		}
		return list;
	}


	/**
	 * 对exist()进行代码简化
	 */
	public static boolean exist2(char[][] board, String word) {
		result = false;

		int n = board.length;
		if(n == 0 || word.isEmpty()) return false;
		int m = board[0].length;

		visited = new boolean[n][m];

		boolean flag = false;
		for(int i = 0; i < n; i ++) {
			for(int j = 0; j < m; j ++) {
				if(word.charAt(0) == board[i][j]) {
					flag = true;
					dfs2(i, j, 0, board, word, n, m);
				}
			}
		}

		if(word.length() == 1) return flag;
		return result;
	}

	private static void dfs2(int x, int y, int idx, char[][] board, String word, int n, int m) {
		if(result || x >= n || x < 0 || y >= m || y < 0 || visited[x][y] || idx >= word.length())
			return;

		boolean isEqual = word.charAt(idx) ==  board[x][y];

		if(!isEqual) return;

		if(idx == word.length() - 1) {
			result = true;
			return;
		}

		visited[x][y] = true;
		dfs2(x - 1, y, idx + 1, board, word, n, m);
		dfs2(x + 1, y, idx + 1, board, word, n, m);
		dfs2(x, y - 1, idx + 1, board, word, n, m);
		dfs2(x, y + 1, idx + 1, board, word, n, m);
		visited[x][y] = false;
	}

	public static void main(String[] args) {
		char[][] board = new char[][] {
				{'A','B','C','E'},
				{'S','F','C','S'},
				{'A','D','E','E'}
		};
		System.out.println(exist(board, "ABCCED"));
		System.out.println(exist(board, "SEE"));
		System.out.println(exist(board, "ABCB"));
		board = new char[][] {
				{'a'},
				{'a'}
		};
		System.out.println(exist(board, "aaa"));
	}
}
