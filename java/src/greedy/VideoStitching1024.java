package greedy;

/**
 * leetcode 1024. Video Stitching
 *
 *   You are given a series of video clips from a sporting event that lasted T seconds.  These video clips can be
 * overlapping with each other and have varied lengths.
 *
 *   Each video clip clips[i] is an interval: it starts at time clips[i][0] and ends at time clips[i][1].  We can cut
 * these clips into segments freely: for example, a clip [0, 7] can be cut into segments [0, 1] + [1, 3] + [3, 7].
 *
 *   Return the minimum number of clips needed so that we can cut the clips into segments that cover the entire sporting
 * event ([0, T]).  If the task is impossible, return -1.
 *
 * Example 1:
 *   Input: clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
 *   Output: 3
 *   Explanation:
 *     We take the clips [0,2], [8,10], [1,9]; a total of 3 clips.
 *     Then, we can reconstruct the sporting event as follows:
 *     We cut [1,9] into segments [1,2] + [2,8] + [8,9].
 *     Now we have segments [0,2] + [2,8] + [8,10] which cover the sporting event [0, 10].
 * Example 2:
 *   Input: clips = [[0,1],[1,2]], T = 5
 *   Output: -1
 *   Explanation:
 *     We can't cover [0,5] with only [0,1] and [0,2].
 * Example 3:
 *   Input: clips = [[0,1],[6,8],[0,2],[5,6],[0,4],[0,3],[6,7],[1,3],[4,7],[1,4],[2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], T = 9
 *   Output: 3
 *   Explanation:
 *     We can take clips [0,4], [4,7], and [6,9].
 * Example 4:
 *   Input: clips = [[0,4],[2,8]], T = 5
 *   Output: 2
 *   Explanation:
 *     Notice you can have extra video after the event ends.
 * Note:
 *   1 <= clips.length <= 100
 *   0 <= clips[i][0], clips[i][1] <= 100
 *   0 <= T <= 100
 */
public class VideoStitching1024 {
	private static int n;

	/**
	 * 贪心策略
	 */
	public static int videoStitching(int[][] clips, int T) {
		n = clips.length;
		int count = 0;

		int s = 0, e = T;
		int[] start, end;
		while(s < e) {
			start = getClip(clips, s, false);  // 选择包含起点位置，且结尾最靠后的节点

			if(start == null) return -1;

			if(start[1] >= e) return ++count;

			end = getClip(clips, e, true);  // 选择包含终点位置，且起点最靠前的节点

			if(end == null) return -1;

			s = start[1];
			e = end[0];
			count = count + 2;
		}
		return count;
	}

	private static int[] getClip(int[][] clips, int t, boolean isEnd) {
		int[] clip = new int[2];  // clip[0] - clips[i][0]; clip[1] - clips[i][1]
		boolean flag = false;
		int diff1, diff2;
		for(int i = 0; i < n; i ++) {
			if(!isEnd && clips[i][0] <= t && clips[i][1] > t
				|| isEnd && clips[i][0] < t && clips[i][1] >= t ) { // 即使存在clips[i][0] == clips[i][1]，选用无法推进进度
				if(!flag) {
					flag = true;
					clip = new int[] { clips[i][0], clips[i][1] };
				} else {
					diff1 = isEnd ? t - clips[i][0] : clips[i][1] - t;
					diff2 = isEnd ? t - clip[0] : clip[1] - t;
					if(diff1 > diff2) {
						clip[0] = clips[i][0];
						clip[1] = clips[i][1];
					}
				}
			}
		}
		return flag ? clip : null;
	}

	public static void main(String[] args) {
		int[][] clips = new int[][] {
				{0,2},{4,6},{8,10},{1,9},{1,5},{5,9}
		};
		System.out.println(videoStitching(clips, 10));

		clips = new int[][] {
				{0,1},{1,2}
		};
		System.out.println(videoStitching(clips, 5));

		clips = new int[][] {
				{0,1},{6,8},{0,2},{5,6},{0,4},{0,3},{6,7},{1,3},{4,7},{1,4},{2,5},{2,6},{3,4},{4,5},{5,7},{6,9}
		};
		System.out.println(videoStitching(clips, 9));

		clips = new int[][] {
				{0,4},{2,8}
		};
		System.out.println(videoStitching(clips, 5));

		clips = new int[][] {
				{0,5},{6,8}
		};
		System.out.println(videoStitching(clips, 7));
	}
}
