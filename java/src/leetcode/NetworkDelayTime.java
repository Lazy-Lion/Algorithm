package leetcode;

import java.util.*;

/**
 * leetcode 743. Network Delay Time
 *   There are N network nodes, labelled 1 to N.
 *   Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the
 * target node, and w is the time it takes for a signal to travel from source to target.
 * Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is
 * impossible, return -1.
 *
 * Note:
 *   N will be in the range [1, 100].
 *   K will be in the range [1, N].
 *   The length of times will be in the range [1, 6000].
 *   All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 0 <= w <= 100.
 */
public class NetworkDelayTime {

	/**
	 * Dijkstra
	 */
	public static int networkDelayTime(int[][] times, int N, int K) {

		Map<Integer, List<int[]>> graph = new HashMap<>();  // 构造有向图结构，key: 起点；value: arr[0] 终点，arr[1] 权重
		List<int[]> list;

		for(int[] arr : times){
			if(graph.containsKey(arr[0])){
				list = graph.get(arr[0]);
			}else{
				list = new ArrayList<>();
				graph.put(arr[0], list);
			}
			list.add(new int[]{arr[1], arr[2]});
		}

		int[] dist = new int[N + 1];  // 记录顶点i到起点的距离
		dist[0] = -1;  // 第一个位置不存储数据
		for(int i = 1; i < dist.length; i ++){
			dist[i] = Integer.MAX_VALUE;
		}

		PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
		queue.add(new int[]{K, 0}); // start point: vertex K, distance 0
		dist[K] = 0;

		boolean[] visited = new boolean[N + 1];  // 记录顶点是否已访问

		while(!queue.isEmpty()){
			int[] temp = queue.poll();
			int vertex = temp[0];
			int distance = temp[1];

			if(visited[vertex]) continue;

			if(graph.containsKey(vertex)) {
				for (int[] edge : graph.get(vertex)) {
					int endVertex = edge[0];
					int weight = edge[1];

					if (distance + weight < dist[endVertex]) {
						dist[endVertex] = distance + weight;
						queue.add(new int[]{endVertex, dist[endVertex]});
					}
				}
			}
			visited[vertex] = true;
		}

		int max = 0;
		for(int d : dist){
			if(d == Integer.MAX_VALUE) return -1;
			if(d > max) max = d;
		}
		return max;
	}

	public static void main(String[] args){
		int result = networkDelayTime(new int[][]{new int[]{2,1,1},new int[]{2,3,1},new int[]{3,4,1}}, 4, 2);
		System.out.println(result);

		result = networkDelayTime(new int[][]{new int[]{1,2,1},new int[]{2,3,7},new int[]{1,3,4},new int[]{2,1,2}}, 4, 1);
		System.out.println(result);
	}
}
