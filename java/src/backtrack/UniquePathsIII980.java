package backtrack;

/**
 * leetcode 980. Unique Paths III
 * On a 2-dimensional grid, there are 4 types of squares:
 *
 * 1 represents the starting square.  There is exactly one starting square.
 * 2 represents the ending square.  There is exactly one ending square.
 * 0 represents empty squares we can walk over.
 * -1 represents obstacles that we cannot walk over.
 *   Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle
 * square exactly once.
 *
 * Example 1:
 *   Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
 *   Output: 2
 *   Explanation: We have the following two paths:
 *    1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
 *    2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
 * Example 2:
 *   Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
 *   Output: 4
 *   Explanation: We have the following four paths:
 *    1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
 *    2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
 *    3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
 *    4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)
 * Example 3:
 *   Input: [[0,1],[2,0]]
 *   Output: 0
 *   Explanation:
 *    There is no path that walks over every empty square exactly once.
 *    Note that the starting and ending square can be anywhere in the grid.
 * Note:
 *   1 <= grid.length * grid[0].length <= 20
 */
public class UniquePathsIII980 {
	int count = 0;
	int n;
	int m;
	int canWalkCount;

	/**
	 * 回溯法
	 */
	public int uniquePathsIII(int[][] grid) {
		n = grid.length;
		m = grid[0].length;

		boolean[][] visited = new boolean[n][m];

		int[] startindex = getStartIndex(grid);

		dfs(grid, visited, startindex[0], startindex[1]);

		int result = count;
		count = 0;
		return result;
	}

	private void dfs(int[][] grid, boolean[][] visited, int i, int j) {
		if(i < 0 || i >= n || j < 0 || j >= m) return;

		if(grid[i][j] == -1) return;

		if(visited[i][j]) return;

		visited[i][j] = true;
		canWalkCount --;
		if (grid[i][j] == 2) {
			if(canWalkCount == 0)
				count ++;
		}

		dfs(grid, visited, i - 1, j);
		dfs(grid, visited, i + 1, j);
		dfs(grid, visited, i, j - 1);
		dfs(grid, visited, i, j + 1);

		// 回溯
		visited[i][j] = false;
		canWalkCount ++;
	}

	private int[] getStartIndex(int[][] grid) {
		canWalkCount = 0;
		int starti = 0;
		int startj = 0;
		for(int i = 0; i < n; i ++ ) {   // 找到起始位置
			for(int j = 0; j < m; j ++) {
				if(grid[i][j] == 1) {
					starti = i;
					startj = j;
					canWalkCount ++;
				} else if (grid[i][j] == 0 || grid[i][j] == 2) {
					canWalkCount ++;
				}
			}
		}
		return new int[] { starti, startj };
	}

	public static void main(String[] args) {
		UniquePathsIII980 u = new UniquePathsIII980();
		System.out.println(u.uniquePathsIII(new int[][] {{1,0,0,0},{0,0,0,0},{0,0,2,-1}}));
		System.out.println(u.uniquePathsIII(new int[][] {{0,1},{2,0}}));
		System.out.println(u.uniquePathsIII(new int[][] {{1,0,0,0},{0,0,0,0},{0,0,0,2}}));
	}
}
