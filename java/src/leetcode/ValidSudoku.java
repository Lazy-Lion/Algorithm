package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 36. Valid Sudoku
 *   Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following
 * rules:
 *   Each row must contain the digits 1-9 without repetition.
 *   Each column must contain the digits 1-9 without repetition.
 *   Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 *
 * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
 *
 * Example 1:
 *   Input:
 *   [
 *     ["5","3",".",".","7",".",".",".","."],
 *     ["6",".",".","1","9","5",".",".","."],
 *     [".","9","8",".",".",".",".","6","."],
 *     ["8",".",".",".","6",".",".",".","3"],
 *     ["4",".",".","8",".","3",".",".","1"],
 *     ["7",".",".",".","2",".",".",".","6"],
 *     [".","6",".",".",".",".","2","8","."],
 *     [".",".",".","4","1","9",".",".","5"],
 *     [".",".",".",".","8",".",".","7","9"]
 *   ]
 *   Output: true
 * Example 2:
 *   Input:
 *   [
 *     ["8","3",".",".","7",".",".",".","."],
 *     ["6",".",".","1","9","5",".",".","."],
 *     [".","9","8",".",".",".",".","6","."],
 *     ["8",".",".",".","6",".",".",".","3"],
 *     ["4",".",".","8",".","3",".",".","1"],
 *     ["7",".",".",".","2",".",".",".","6"],
 *     [".","6",".",".",".",".","2","8","."],
 *     [".",".",".","4","1","9",".",".","5"],
 *     [".",".",".",".","8",".",".","7","9"]
 *   ]
 *   Output: false
 *   Explanation: Same as Example 1, except with the 5 in the top left corner being
 *     modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
 * Note:
 *   A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 *   Only the filled cells need to be validated according to the mentioned rules.
 *   The given board contain only digits 1-9 and the character '.'.
 *   The given board size is always 9x9.
 */
public class ValidSudoku {
    /**
     * @see ValidSudoku#isValidSudoku_1(char[][])
     */
    public static boolean isValidSudoku(char[][] board) {
        boolean[][] row = new boolean[9][10];  // 记录第i行是否存在数字j
        boolean[][] col = new boolean[9][10];  // 记录第i列是否存在数字j
        boolean[][] sub_box = new boolean[9][10];  // 记录第i个sub-box是否存在数字j; (sub-box从上向下从左向右依次排列)

        char c;
        int val, box_index;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                c = board[i][j];
                if (c == '.') continue;
                else {
                    val = c - '0';
                    if (row[i][val] || col[j][val]) return false;
                    box_index = i / 3 * 3 + j / 3;
                    if (sub_box[box_index][val]) return false;
                    sub_box[box_index][val] = row[i][val] = col[j][val] = true;
                }
            }
        }
        return true;
    }

    /**
     * one pass + bit operation
     *
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static boolean isValidSudoku_1(char[][] board) {
        int m, n;
        m = n = 9;

        // can replace by short[]
        int[] row = new int[m]; // row[i]: digital in row i
        int[] col = new int[n]; // col[i]: digital in column i
        int[] subBox = new int[m]; // subBox[i]: digital in sub-box i (index of sub-box increases from left to right and top to bottom)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] < '0' || board[i][j] > '9') {
                    continue;
                }

                int val = 1 << (board[i][j] - '0');

                // map index: row, col, subBox

                // repeat
                if ((row[i] & val) > 0) return false;
                if ((col[j] & val) > 0) return false;
                int boxIndex = (i / 3) * 3 + (j / 3);
                if ((subBox[boxIndex] & val) > 0) return false;

                row[i] = row[i] | val;
                col[j] = col[j] | val;
                subBox[boxIndex] = subBox[boxIndex] | val;
            }
        }
        return true;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(
                new char[][]{
                        {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                        {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                        {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                        {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                        {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                        {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                        {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                        {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                        {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
                }
        );
        params.add(param);

        Utils.testStaticMethod(ValidSudoku.class, params);
    }
}
