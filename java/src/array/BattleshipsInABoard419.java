package array;

/**
 * leetcode 419. Battleships in a Board
 *
 *   Given an 2D board, count how many battleships are in it. The battleships are represented with 'X's, empty slots
 * are represented with '.'s. You may assume the following rules:
 *   You receive a valid board, made of only battleships or empty slots.
 *   Battleships can only be placed horizontally or vertically. In other words, they can only be made of the shape
 * 1xN (1 row, N columns) or Nx1 (N rows, 1 column), where N can be of any size.
 * At least one horizontal or vertical cell separates between two battleships - there are no adjacent battleships.
 *
 * Example:
 *   X..X
 *   ...X
 *   ...X
 *   In the above board there are 2 battleships.
 * Invalid Example:
 *   ...X
 *   XXXX
 *   ...X
 *   This is an invalid board that you will not receive - as battleships will always have a cell separating between them.
 * Follow up:
 *   Could you do it in one-pass, using only O(1) extra memory and without modifying the value of the board?
 */
public class BattleshipsInABoard419 {
	private static final char BATTLESHIP = 'X';
	private static final char EMPTYSLOT = '.';

	/**
	 * way1: 数组遍历
	 */
	public static int countBattleships(char[][] board) {
		// 有效输入，n > 0 && m > 0
		int n = board.length;
		int m = board[0].length;

		boolean[][] visited = new boolean[n][m]; // 访问标记

		int count = 0;
		for(int i = 0; i < n; i ++) {
			for(int j = 0; j < m; j ++) {
				if(visited[i][j]) continue;

				char c = board[i][j];
				visited[i][j] = true;
				if(c == EMPTYSLOT) {
					visited[i][j] = true; continue;
				}
				if(c == BATTLESHIP) {
					count ++;
					int x = i, y = j;
					// 有效输入，右、下两个方向只会存在一种
					// right
					while(y + 1 < m && board[x][y + 1] == 'X') {
						visited[x][++ y] = true;
					}
					// bottom
					y = j;
					while(x + 1 < n && board[x + 1][y] == 'X') {
						visited[++ x][y] = true;
					}
				}
			}
		}
		return count;
	}

	/**
	 * way2: one pass, O(1) extra memory, without modify the value of the board
	 *
	 * way1在遇到占用多格的战舰时，计数并遍历战舰使用的所有格子、标记
	 * 实际上在遇到占用多格战舰时，只需要在战舰的结尾处计数即可
	 */
	public static int countBattleshipsOptimize(char[][] board) {
		int n = board.length;
		int m = board[0].length;

		int count = 0;
		for(int i = 0; i < n; i ++) {
			for(int j = 0; j < m; j ++) {
				if(board[i][j] == EMPTYSLOT
						|| j + 1 < m && board[i][j + 1] == BATTLESHIP
						|| i + 1 < n && board[i + 1][j] == BATTLESHIP) {
					continue;
				}
				count ++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		char[][] board = new char[][]{
				{'X', '.', '.', 'X'},
				{'.', '.', '.', 'X'},
				{'.', '.', '.', 'X'}
		};
		System.out.println(countBattleships(board));
		System.out.println(countBattleshipsOptimize(board));


		board = new char[][] {
				{'X', '.', 'X'},
				{'X', '.', 'X'}
		};
		System.out.println(countBattleships(board));
		System.out.println(countBattleshipsOptimize(board));
	}
}
