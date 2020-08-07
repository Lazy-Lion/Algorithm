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

    private static final char O = 'O';
    private static final char X = 'X';
    private static final char F = 'F';

    /**
     * way1: {@link SurroundedRegions#solve(char[][])},以边界的'O'为起点，广度优先遍历(BFS)
     *
     * way2: 以边界的'O'为起点，深度优先遍历(DFS)，未实现
     *
     */

    /**
     * level traversal: BFS
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static void solve(char[][] board) {
        int m = board.length;
        if(m == 0) return;
        int n = board[0].length;

        // up left down right
        int[] direction_x = {-1, 0, 1, 0};
        int[] direction_y = {0, -1, 0, 1};

        Queue<Location> queue = new LinkedList<>();

        // add start point
        for (int j = 0; j < n; j++) {
            if (board[0][j] == O) {
                board[0][j] = F;  // mark: won't be flipped to 'X'; Use here to avoid visited[][]
                queue.offer(new Location(0, j));
            }

            if (board[m - 1][j] == O) {
                board[m - 1][j] =F;
                queue.offer(new Location(m - 1, j));
            }
        }

        for (int i = 1; i < m - 1; i++) {
            if (board[i][0] == O) {
                board[i][0] = F;
                queue.offer(new Location(i, 0));
            }

            if (board[i][n - 1] == O) {
                board[i][n - 1] = F;
                queue.offer(new Location(i, n - 1));
            }
        }

        int x, y;
        while (!queue.isEmpty()) {
            Location location = queue.poll();

            // up
            x = location.x + direction_x[0];
            y = location.y + direction_y[0];
            if (x > 0 && board[x][y] == O) {
                queue.offer(new Location(x, y));
                board[x][y] = F;
            }

            // left
            x = location.x + direction_x[1];
            y = location.y + direction_y[1];

            if (y > 0 && board[x][y] == O) {
                queue.offer(new Location(x, y));
                board[x][y] = F;
            }

            // down
            x = location.x + direction_x[2];
            y = location.y + direction_y[2];

            if (x < m && board[x][y] == O) {
                queue.offer(new Location(x, y));
                board[x][y] = F;
            }

            // right
            x = location.x + direction_x[3];
            y = location.y + direction_y[3];
            if (y < n && board[x][y] == O) {
                queue.offer(new Location(x, y));
                board[x][y] = F;
            }

        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == F) {
                    board[i][j] = O;
                } else {
                    board[i][j] = X;
                }
            }
        }
    }

    static class Location {
        int x, y;

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
        public int hashCode() {
            return x & y;
        }
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'X', 'X', 'X', 'X'},
                {'X', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'X'}
        };
        board = new char[][]{
                {'O', 'X', 'X', 'O', 'X'},
                {'X', 'O', 'O', 'X', 'O'},
                {'X', 'O', 'X', 'O', 'X'},
                {'O', 'X', 'O', 'O', 'O'},
                {'X', 'X', 'O', 'X', 'O'}
        };

		solve(board);
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
    }
}
