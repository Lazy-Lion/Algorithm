package leetcode;

/**
 * leetcode 240. Search a 2D Matrix II
 *
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 *   1.Integers in each row are sorted in ascending from left to right.
 *   2.Integers in each column are sorted in ascending from top to bottom.
 * Example:
 *   Consider the following matrix:
 *   [
 *     [1,   4,  7, 11, 15],
 *     [2,   5,  8, 12, 19],
 *     [3,   6,  9, 16, 22],
 *     [10, 13, 14, 17, 24],
 *     [18, 21, 23, 26, 30]
 *   ]
 *   Given target = 5, return true.
 *   Given target = 20, return false.
 */
public class SearchA2DMatrixII {

    /**
     * time complexity: O(m+n)
     * space complexity: O(1)
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) {
            return false;
        }
        int n = matrix[0].length;

        // 选择从右上开始访问，对于 > target 和 < target 的情况都分别只有一个路径
        int i = 0, j = n - 1;
        while (i < m && j >= 0) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] < target) {
                i++;
            } else {
                j--;
            }
        }

        return false;
    }
}
