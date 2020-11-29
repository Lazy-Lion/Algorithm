package temp;

import java.util.Arrays;

/**
 * 593. Valid Square
 * Given the coordinates of four points in 2D space, return whether the four points could construct a square.
 *
 * The coordinate (x,y) of a point is represented by an integer array with two integers.
 *
 * Example:
 *   Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
 *   Output: True
 *
 * Note:
 *   All the input integers are in the range [-10000, 10000].
 *   A valid square has four equal sides with positive length and four equal angles (90-degree angles).
 *   Input points have no order.
 */
public class ValidSquare593 {
	/**
	 * 正方形：四条边相等 => 菱形或正方形，两条对角线相等 => 正方形
	 */
	public static boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
		int[] dist = new int[6];

		dist[0] = dist(p1,p2);
		dist[1] = dist(p1,p3);
		dist[2] = dist(p1,p4);
		dist[3] = dist(p2,p3);
		dist[4] = dist(p2,p4);
		dist[5] = dist(p3,p4);

		Arrays.sort(dist);

		return dist[0] > 0 && dist[0] == dist[1] && dist[1] == dist[2]
				&& dist[2] == dist[3] && dist[4] == dist[5];
	}

	private static int dist(int[] p1, int[] p2) {
		int dist_x = p1[0] - p2[0];
		int dist_y = p1[1] - p2[1];
		return dist_x * dist_x + dist_y * dist_y;
	}
	public static void main(String[] args) {
		System.out.println(validSquare(
				new int[] {0,0},
				new int[] {5,0},
				new int[] {5,4},
				new int[] {0,4}
		));

		System.out.println(validSquare(
				new int[] {1,0},
				new int[] {-1,0},
				new int[] {0,1},
				new int[] {0,-1}
		));
	}
}
