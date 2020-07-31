package array;

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
public class SpiralMatrix54 {
	/**
	 * TODO: 实现优化
	 * 模拟过程
	 */
	public static List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> result = new ArrayList<>();

		int n = matrix.length;
		if(n <= 0) return result;
		int m = matrix[0].length;

		// 每一层访问顺序：right → bottom → left → top
		int row = 0, col = 0;
		int remain_row = n, remain_col = m; // 未遍历过的行、列数
		while(remain_row > 0 && remain_col > 0) {
			int r = row, c = col;  // 记录循环起始位置(r,c)
			int i = row,  j = col;
			boolean flag = false;

			for(; j < m - c; j ++) { // right
				result.add(matrix[r][j]);
			}
			// 当前行遍历完成：未遍历行数-1，下次循环开始位置行号 +1
			remain_row --;
			row ++;
			if(--j > c) flag = true;  // 表示列号有增加，即上述for循环遍历了超过一列的数据

			if(flag) {
				for(i = i + 1; i < n - r; i ++) {  // bottom
					result.add(matrix[i][j]);
				}
				// 当前行遍历完成：未遍历列数-1，下次循环开始位置列号 +1
				remain_col --;
				col ++;
			}

			if(--i > r) {
				for(j = j - 1; j >= c; j --) { // left
					result.add(matrix[i][j]);
				}
				remain_row --;
			}

			j ++;
			if(flag) {
				for(i = i - 1; i >= r + 1; i --) {  // top
					result.add(matrix[i][j]);
				}
				remain_col --;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int[][] matrix = new int[][] {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9}
		};
		System.out.println(spiralOrder(matrix));

		matrix = new int[][] {
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9,10,11,12}
		};
		System.out.println(spiralOrder(matrix));

		matrix = new int[][] {
				{1, 2},
				{5, 6},
				{9,10}
		};
		System.out.println(spiralOrder(matrix));

		matrix = new int[][] {
				{1, 2, 3, 4}
		};
		System.out.println(spiralOrder(matrix));

		matrix = new int[][] {
				{1},
				{2},
				{3}
		};
		System.out.println(spiralOrder(matrix));
	}
}
