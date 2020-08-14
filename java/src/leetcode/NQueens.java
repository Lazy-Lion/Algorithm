package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 51: N-Queens
 *
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.
 *
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 *
 * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space respectively.
 */
public class NQueens {
    private static final char QUEEN = 'Q';
    private static final char EMPTY = '.';

    public static List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) { // initialization
            for (int j = 0; j < n; j++) {
                board[i][j] = EMPTY;
            }
        }

        List<List<String>> result = new ArrayList<>();
        queen(0, n, board, result);
        return result;
    }

    private static void queen(int row, int n, char[][] board, List<List<String>> result) {
        if (row >= n) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                list.add(new String(board[i]));
            }
            result.add(list);
            return;
        }

        for (int j = 0; j < n; j++) {
            if (canPlace(board, row, j, n)) {
                board[row][j] = QUEEN;
                queen(row + 1, n, board, result);
                board[row][j] = EMPTY;
            }
        }
    }

    // validate row, column, diagonal
    private static boolean canPlace(char[][] board, int row, int column, int n) {
        // 1. the current row must not be placed
        // 2. column
        for (int i = 0; i < row; i++) {
            if (board[i][column] == QUEEN) {
                return false;
            }
        }
        // 3. diagonal
        //  1) upper left
        int i = row - 1;
        int j = column - 1;
        while (i >= 0 && j >= 0) {
            if (board[i--][j--] == QUEEN) {
                return false;
            }
        }
        // 2) upper right
        i = row - 1;
        j = column + 1;
        while (i >= 0 && j < n) {
            if (board[i--][j++] == QUEEN) {
                return false;
            }
        }
        return true;
    }

    /**
     * optimize {@link NQueens#canPlace(char[][], int, int, int)}
     */
    private static boolean canPlace_2(char[][] board, int row, int column, int n) {
        int upperLeft = column - 1;
        int upperRight = column + 1;
        for (int i = row - 1; i >= 0; i--) {
            if (board[i][column] == QUEEN) {
                return false;
            }

            if (upperLeft >= 0 && board[i][upperLeft--] == QUEEN) {
                return false;
            }

            if (upperRight < n && board[i][upperRight++] == QUEEN) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(NQueens.class
                , new HashSet<String>() {
                    {
                        add("solveNQueens");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(4),
                        Arrays.asList(8),
                        Arrays.asList(10)
                ));
    }
}
