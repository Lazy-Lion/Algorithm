package leetcode;

import java.util.*;

/**
 * leetcode 210. Course Schedule II
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 *
 *   Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is
 * expressed as a pair: [0,1]
 *
 *   Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should
 * take to finish all courses.
 *
 *   There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all
 * courses, return an empty array.
 *
 * Example 1:
 *   Input: 2, [[1,0]]
 *   Output: [0,1]
 *   Explanation: There are a total of 2 courses to take. To take course 1 you should have finished
 *                course 0. So the correct course order is [0,1] .
 * Example 2:
 *   Input: 4, [[1,0],[2,0],[3,1],[3,2]]
 *   Output: [0,1,2,3] or [0,2,1,3]
 *   Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
 *                courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
 *                So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
 * Note:
 *   The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how
 * a graph is represented.
 *   You may assume that there are no duplicate edges in the input prerequisites.
 */
public class CourseScheduleII {
	/**
	 * 拓扑排序
	 */
	public static int[] findOrder(int numCourses, int[][] prerequisites) {
		int[] result = new int[numCourses];  // 记录拓扑排序结果
		int c = 0;  // 记录拓扑排序访问到的顶点个数

		int[] inDegree = new int[numCourses];  // 各个顶点的入度统计
		Map<Integer, List<Integer>> adjacent = getAdjacent(prerequisites, inDegree);  // 邻接表表示图

		Queue<Integer> queue = new LinkedList<>();
		for(int i = 0; i < numCourses; i ++) {
			if(inDegree[i] == 0) {
				queue.offer(i);
				c ++;
			}
		}

		int v1, i = 0;
		List<Integer> list;
		while(!queue.isEmpty()) {
			v1 = queue.poll();
			result[i ++] = v1;
			if(adjacent.containsKey(v1)) {
				list = adjacent.get(v1);
				for(int v2 : list) {
					inDegree[v2] --;
					if(inDegree[v2] == 0) {
						queue.offer(v2);
						c ++;
					}
				}
			}
		}

		return c == numCourses ? result : new int[0];
	}

	private static Map<Integer, List<Integer>> getAdjacent(int[][] prerequisites, int[] inDegree) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		int v1, v2;
		List<Integer> list;
		for(int[] edge : prerequisites) {
			v1 = edge[0];
			v2 = edge[1];
			inDegree[v1] ++;
			if(map.containsKey(v2)) {
				list = map.get(v2);
			} else {
				list = new ArrayList<>();
				map.put(v2, list);
			}
			list.add(v1);
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println(findOrder(2, new int[][] {
				{1,0}
		}));
		System.out.println(findOrder(4, new int[][] {
				{1,0},
				{2,0},
				{3,1},
				{3,2}
		}));
	}
}
