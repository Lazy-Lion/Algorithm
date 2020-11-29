package temp;

/**
 * 794. Valid Tic-Tac-Toe State
 *   A Tic-Tac-Toe board is given as a string array board. Return True if and only if it is possible to reach this
 * board position during the course of a valid tic-tac-toe game.
 *
 *   The board is a 3 x 3 array, and consists of characters " ", "X", and "O".  The " " character represents an empty
 * square.
 *
 * Here are the rules of Tic-Tac-Toe:
 *   Players take turns placing characters into empty squares (" ").
 *   The first player always places "X" characters, while the second player always places "O" characters.
 *   "X" and "O" characters are always placed into empty squares, never filled ones.
 *   The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
 *   The game also ends if all squares are non-empty.
 *   No more moves can be played if the game is over.
 *
 * Example 1:
 *   Input: board = ["O  ", "   ", "   "]
 *   Output: false
 *   Explanation: The first player always plays "X".
 *
 * Example 2:
 *   Input: board = ["XOX", " X ", "   "]
 *   Output: false
 *   Explanation: Players take turns making moves.
 *
 * Example 3:
 *   Input: board = ["XXX", "   ", "OOO"]
 *   Output: false
 *
 * Example 4:
 *   Input: board = ["XOX", "O O", "XOX"]
 *   Output: true
 *
 * Note:
 *   board is a length-3 array of strings, where each string board[i] has length 3.
 *   Each board[i][j] is a character in the set {" ", "X", "O"}.
 */
public class VTTTS794 {
	public static boolean validTicTacToe(String[] board) {
		int xc = 0, oc = 0;  // xc - 'X'的个数；oc - 'O'的个数
		char c;
		for(int i = 0; i < 3; i ++) {
			for(int j = 0; j < 3; j ++) {
				c = board[i].charAt(j);
				if(c == 'X') xc ++;
				else if(c == 'O') oc ++;
			}
		}

		if(!(xc == oc || xc - oc == 1)) return false;  // 'X'的个数等于'O'的个数或比'O'的个数多1  => 'X'最多5个, 'O'最多4个

		int xs = 0, os = 0; // xs - 'X'行、列或对角线相等的数目；os - 'O'行、列、对角线相等的数目
		for(int i = 0; i < 3; i ++) {
			if(board[i].charAt(0) == board[i].charAt(1)   // 行
					&& board[i].charAt(0) == board[i].charAt(2)) {
				if (board[i].charAt(0) == 'X') xs ++;
				else if (board[i].charAt(0) == 'O') os ++;
			}

			if(board[0].charAt(i) == board[1].charAt(i)
					&& board[0].charAt(i) == board[2].charAt(i)) {  // 列
				if (board[0].charAt(i) == 'X') xs ++;
				else if (board[0].charAt(i) == 'O') os ++;
			}
		}

		if(board[0].charAt(0) == board[1].charAt(1)
				&& board[0].charAt(0) == board[2].charAt(2)) {  // 主对角线
			if (board[0].charAt(0) == 'X') xs ++;
			else if (board[0].charAt(0) == 'O') os ++;
		}

		if(board[0].charAt(2) == board[1].charAt(1)
				&& board[0].charAt(2) == board[2].charAt(0)) {  // 副对角线
			if (board[0].charAt(2) == 'X') xs ++;
			else if (board[0].charAt(2) == 'O') os ++;
		}

		if(xc <= 2) return true;

		if(xc == 3 && (oc == 2 || oc == 3 && xs == 0)
				|| xc == 4 && (oc == 4 && xs == 0 || oc == 3 && os == 0)
				|| xc == 5 && oc == 4 && os == 0) return true;

		return false;
	}

	/**
	 * 解题思路：
	 *   1. 统计'O'和'X'的个数，记为 oc, xc; 需满足 0 =< xc - oc <= 1
	 *   2. 记录行、列和对角线是否满足全为'X'或全为'O'条件 (-3表示全为'O',3表示全为'X')
	 *   3. 第2步的记录，如果存在-3表示'O'优先满足条件owin = true, 存在3表示'X'优先满足条件xwin = true
	 *      1) owin, xwin 同时为 true, 不符合条件
	 *      2) owin, xwin 同时为 false, 符合条件
	 *      3) xwin 为 true: 需要 xc - oc = 1
	 *      4) owin 为 true: 需要 xc == oc
	 *      5) 其他情况均不符合条件
	 */
	public static boolean validTicTacToe_2(String[] board) {
		int[] row = new int[3];  // 记录第i行，'X'比'O'多的个数
		int[] col = new int[3];  // 记录第i列，'X'比'O'多的个数
		int[] diag = new int[2]; // diag[0] - 正对角线'X'比'O'多的个数；diag[1] - 副对角线'X'比'O'多的个数

		char c;
		int xc = 0, oc = 0;
		for(int i = 0; i < 3; i ++) {
			for(int j = 0; j < 3; j ++) {
				c = board[i].charAt(j);
				if(c == 'X') {
					xc ++;
					row[i] ++;
					col[j] ++;
					if(i == j) diag[0] ++;
					if (i + j == 2) diag[1] ++;
				} else if (c == 'O') {
					oc ++;
					row[i] --;
					col[j] --;
					if(i == j) diag[0] --;
					if (i + j == 2) diag[1] --;
				}
			}
		}

		if(!(xc == oc || xc - oc == 1)) return false;

		boolean xwin = false, owin = false;
		for(int i = 0; i < 3; i ++) {
			if(row[i] == 3 || col[i] == 3) xwin = true;
			if(row[i] == -3 || col[i] == -3) owin = true;

			if(i < 2) {
				if(diag[0] == 3 || diag[1] == 3) xwin = true;
				if(diag[0] == -3 || diag[1] == -3) owin = true;
			}
		}

		if(xwin && owin) return false;

		if(!xwin && !owin
				|| xwin && (xc - oc == 1)
				|| owin && xc == oc) return true;

		return false;
	}

	public static void main(String[] args) {
		System.out.println(validTicTacToe(new String[] {"O  ", "   ", "   "}));
		System.out.println(validTicTacToe(new String[] {"XOX", " X ", "   "}));
		System.out.println(validTicTacToe(new String[] {"XXX", "   ", "OOO"}));
		System.out.println(validTicTacToe(new String[] {"XOX", "O O", "XOX"}));
		System.out.println(validTicTacToe(new String[] {"XO ","XO ","XO "}));
		System.out.println(validTicTacToe(new String[] {"XXX ","OOX","OOX"}));
		System.out.println("-----------------");
		System.out.println(validTicTacToe_2(new String[] {"O  ", "   ", "   "}));
		System.out.println(validTicTacToe_2(new String[] {"XOX", " X ", "   "}));
		System.out.println(validTicTacToe_2(new String[] {"XXX", "   ", "OOO"}));
		System.out.println(validTicTacToe_2(new String[] {"XOX", "O O", "XOX"}));
		System.out.println(validTicTacToe_2(new String[] {"XO ","XO ","XO "}));
		System.out.println(validTicTacToe_2(new String[] {"XXX ","OOX","OOX"}));
		System.out.println(validTicTacToe_2(new String[] {"XXO","XOX","OXO"}));
	}
}

