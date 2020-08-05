package leetcode;

import java.util.*;

/**
 * leetcode 130. Surrounded Regions
 * Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.
 *
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.
 *
 * Example:
 *     X X X X
 *     X O O X
 *     X X O X
 *     X O X X
 * After running your function, the board should be:
 *     X X X X
 *     X X X X
 *     X X X X
 *     X O X X
 * Explanation:
 *   Surrounded regions shouldn’t be on the border, which means that any 'O' on the border of the board are not flipped
 * to 'X'. Any 'O' that is not on the border and it is not connected to an 'O' on the border will be flipped to 'X'.
 * Two cells are connected if they are adjacent cells connected horizontally or vertically.
 */
public class SurroundedRegions {

	// todo
	/**
	 * way1: solve(),以边界的'O'为起点，广度优先遍历(BFS)
	 *   solve_Optimize()针对该版本做了代码优化
	 *
	 * way2: 以边界的'O'为起点，深度优先遍历(DFS)，未实现
	 */
	public static void solve(char[][] board) {
		int n = board.length;
		if(n <= 0) return;
		int m = board[0].length;
		Set<Location> visited = new HashSet<>();

		Queue<Location> queue = new LinkedList<>();
		for(int i = 0; i < n; i ++) {  // 第一列和最后一列
			if(board[i][0] == 'O') addLocation(i, 0, queue, visited);
			if(board[i][m - 1] == 'O') addLocation(i, m - 1, queue, visited);
		}

		for(int j = 1; j < m - 1; j ++) {  // 第一行和最后一行除四个角
			if(board[0][j] == 'O') addLocation(0, j, queue, visited);
			if(board[n - 1][j] == 'O') addLocation(n - 1, j, queue, visited);
		}

		Location lct1, lct2;
		while(!queue.isEmpty()) {
			lct1 = queue.poll();
			board[lct1.x][lct1.y] = '1';

			// up
			if(lct1.x >= 1) {
				if(board[lct1.x - 1][lct1.y] == 'O') {
					lct2 = new Location(lct1.x - 1, lct1.y);
					if(!visited.contains(lct2)) addLocation(lct2.x, lct2.y, queue, visited);
				}
			}

			// down
			if(lct1.x < n - 1) {
				if(board[lct1.x + 1][lct1.y] == 'O') {
					lct2 = new Location(lct1.x + 1, lct1.y);
					if(!visited.contains(lct2)) addLocation(lct2.x, lct2.y, queue, visited);
				}
			}

			// left
			if(lct1.y >= 1) {
				if(board[lct1.x][lct1.y - 1] == 'O') {
					lct2 = new Location(lct1.x, lct1.y - 1);
					if(!visited.contains(lct2)) addLocation(lct2.x, lct2.y, queue, visited);
				}
			}

			// right
			if(lct1.y < m - 1) {
				if(board[lct1.x][lct1.y + 1] == 'O') {
					lct2 = new Location(lct1.x, lct1.y + 1);
					if(!visited.contains(lct2)) addLocation(lct2.x, lct2.y, queue, visited);
				}
			}
		}

		for(int i = 0; i < n; i ++) {
			for(int j = 0; j < m; j ++) {
				board[i][j] = board[i][j] == '1' ? 'O' : 'X';
			}
		}
	}

	private static void addLocation(int x, int y, Queue<Location> queue, Set<Location> visited) {
		Location lct = new Location(x, y);
		queue.offer(lct);
		visited.add(lct);
	}

	static class Location {
		int x,y;
		Location(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			Location that = (Location) obj;
			return this.x == that.x && this.y == that.y;
		}

		@Override
		public int hashCode() {  // 使用HashSet判重，每个Location对象的Hash Code都需要相同，退化成链表或红黑树
			return 1;
		}
	}

	/**
	 * 针对slove()进行代码优化：
	 *   通过改变加入队列位置的字符来避免重复添加
	 */
	public static void solve_Optimize(char[][] board) {
		int n = board.length;
		if(n <= 0) return;
		int m = board[0].length;

		Queue<Location> queue = new LinkedList<>();
		for(int i = 0; i < n; i ++) {  // 第一列和最后一列
			if(board[i][0] == 'O') addLocation_Optimize(i, 0, queue, board);
			if(board[i][m - 1] == 'O') addLocation_Optimize(i, m - 1, queue, board);
		}

		for(int j = 1; j < m - 1; j ++) {  // 第一行和最后一行除四个角
			if(board[0][j] == 'O') addLocation_Optimize(0, j, queue, board);
			if(board[n - 1][j] == 'O') addLocation_Optimize(n - 1, j, queue, board);
		}

		Location lct1;
		while(!queue.isEmpty()) {
			lct1 = queue.poll();
			board[lct1.x][lct1.y] = '1';

			// up
			if(lct1.x >= 1 && board[lct1.x - 1][lct1.y] == 'O') {
				addLocation_Optimize(lct1.x - 1, lct1.y, queue, board);
			}

			// down
			if(lct1.x < n - 1 && board[lct1.x + 1][lct1.y] == 'O') {
				addLocation_Optimize(lct1.x + 1, lct1.y, queue, board);
			}

			// left
			if(lct1.y >= 1 && board[lct1.x][lct1.y - 1] == 'O') {
				addLocation_Optimize(lct1.x, lct1.y - 1, queue, board);
			}

			// right
			if(lct1.y < m - 1 && board[lct1.x][lct1.y + 1] == 'O') {
				addLocation_Optimize(lct1.x, lct1.y + 1, queue, board);
			}
		}

		for(int i = 0; i < n; i ++) {
			for(int j = 0; j < m; j ++) {
				board[i][j] = board[i][j] == '1' ? 'O' : 'X';
			}
		}
	}

	private static void addLocation_Optimize(int x, int y, Queue<Location> queue, char[][] board) {
		Location lct = new Location(x, y);
		queue.offer(lct);
		board[x][y] = '2';
	}

	public static void main(String[] args) {
		char[][] board = new char[][]{
				{'X','X','X','X'},
				{'X','O','O','X'},
				{'X','X','O','X'},
				{'X','O','X','X'}
		};
		board = new char[][] {
				{'O','X','X','O','X'},
				{'X','O','O','X','O'},
				{'X','O','X','O','X'},
				{'O','X','O','O','O'},
				{'X','X','O','X','O'}
		};

//		solve(board);
		solve_Optimize(board);
		for(int i = 0; i < board.length; i ++) {
			System.out.println(Arrays.toString(board[i]));
		}
	}
}
