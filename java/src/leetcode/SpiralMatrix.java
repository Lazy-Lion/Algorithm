package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * leetcode 54. Spiral Matrix
 *   Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
 *
 * Example 1:
 *   Input:
 *   [
 *    [ 1, 2, 3 ],
 *    [ 4, 5, 6 ],
 *    [ 7, 8, 9 ]
 *   ]
 *   Output: [1,2,3,6,9,8,7,4,5]
 * Example 2:
 *   Input:
 *   [
 *     [1, 2, 3, 4],
 *     [5, 6, 7, 8],
 *     [9,10,11,12]
 *   ]
 *   Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 */
public class SpiralMatrix {
    /**
     * @see SpiralMatrix#spiralOrder_1(int[][])
     */
    @Deprecated
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();

        int n = matrix.length;
        if (n <= 0) return result;
        int m = matrix[0].length;

        // 每一层访问顺序：right → bottom → left → top
        int row = 0, col = 0;
        int remain_row = n, remain_col = m; // 未遍历过的行、列数
        while (remain_row > 0 && remain_col > 0) {
            int r = row, c = col;  // 记录循环起始位置(r,c)
            int i = row, j = col;
            boolean flag = false;

            for (; j < m - c; j++) { // right
                result.add(matrix[r][j]);
            }
            // 当前行遍历完成：未遍历行数-1，下次循环开始位置行号 +1
            remain_row--;
            row++;
            if (--j > c) flag = true;  // 表示列号有增加，即上述for循环遍历了超过一列的数据

            if (flag) {
                for (i = i + 1; i < n - r; i++) {  // bottom
                    result.add(matrix[i][j]);
                }
                // 当前行遍历完成：未遍历列数-1，下次循环开始位置列号 +1
                remain_col--;
                col++;
            }

            if (--i > r) {
                for (j = j - 1; j >= c; j--) { // left
                    result.add(matrix[i][j]);
                }
                remain_row--;
            }

            j++;
            if (flag) {
                for (i = i - 1; i >= r + 1; i--) {  // top
                    result.add(matrix[i][j]);
                }
                remain_col--;
            }
        }
        return result;
    }

    /**
     * simulation process
     * time complexity: O(n)
     * space complexity: O(n) - without considering the output list
     */
    public static List<Integer> spiralOrder_1(int[][] matrix) {
        // traverse order: right → down → left → up
        int[] directions = new int[]{0, 1, 2, 3}; // 0 - right, 1 - down, 2 - left, 3 - up
        int[][] adjustment = new int[][]{
                new int[]{0, 1}, // right: x' = x; y' = y + 1
                new int[]{1, 0}, // down: x' = x + 1; y' = y
                new int[]{0, -1}, // left: x' = x; y' = y - 1
                new int[]{-1, 0} // up: x' = x - 1; y' = y
        };

        List<Integer> result = new ArrayList<>();

        int m = matrix.length;
        if (m <= 0) {
            return result;
        }
        int n = matrix[0].length;

        boolean[][] visited = new boolean[m][n]; // visited flag
        int i = 0, j = 0; // start point
        int direction = 0; // init direction: right
        visited[0][0] = true;
        result.add(matrix[i][j]);
        int count = m * n; // number of martix[][] elements
        int visitCount = 1; // count that has been visited
        while (visitCount < count) {
            int x = i + adjustment[directions[direction]][0];
            int y = j + adjustment[directions[direction]][1];
            if (x >= m || x < 0 || y >= n || y < 0 || visited[x][y]) {
                direction = (direction + 1) % 4; // change direction

                // get next coordinate
                i += adjustment[directions[direction]][0];
                j += adjustment[directions[direction]][1];
            } else {
                i = x;
                j = y;
            }

            result.add(matrix[i][j]);

            // visited deal
            visitCount++;
            visited[i][j] = true;
        }
        return result;
    }

    /**
     * layer by layer
     * time complexity: O(n)
     * space complexity: O(1) - without considering the output list
     */
    public static List<Integer> spiralOrder_2(int[][] matrix) {
        List<Integer> result = new ArrayList<>();

        int m = matrix.length;
        if (m <= 0) {
            return result;
        }
        int n = matrix[0].length;

        int x1 = 0, y1 = 0;  // left-top
        int x2 = m - 1, y2 = n - 1; // right-bottom

        while (x1 <= x2 && y1 <= y2) {

            // traverse one layer
            for (int j = y1; j <= y2; j++) result.add(matrix[x1][j]);
            for (int i = x1 + 1; i <= x2; i++) result.add(matrix[i][y2]);

            if (y2 > y1 && x2 > x1) {
                for (int j = y2 - 1; j >= y1; j--) result.add(matrix[x2][j]);
                for (int i = x2 - 1; i > x1; i--) result.add(matrix[i][y1]);
            }

            // next layer's bounds
            x1++;
            y1++;
            x2--;
            y2--;
        }


        return result;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        });
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[][]{
                {1, 2},
                {5, 6},
                {9, 10}
        });
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[][]{
                {1, 2, 3, 4}
        });
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[][]{
                {1},
                {2},
                {3}
        });
        params.add(param);

        Utils.testStaticMethod(SpiralMatrix.class, params);
    }
}
