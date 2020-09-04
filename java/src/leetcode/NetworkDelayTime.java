package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
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
     * Dijkstra {@link graph.Dijkstra}
     */
    public static int networkDelayTime(int[][] times, int N, int K) {

        Map<Integer, List<int[]>> graph = new HashMap<>();  // 构造有向图结构，key: 起点；value: arr[0] 终点，arr[1] 权重
        List<int[]> list;

        for (int[] arr : times) {
            if (graph.containsKey(arr[0])) {
                list = graph.get(arr[0]);
            } else {
                list = new ArrayList<>();
                graph.put(arr[0], list);
            }
            list.add(new int[]{arr[1], arr[2]});
        }

        int[] dist = new int[N + 1];  // 记录顶点i到起点的距离
        dist[0] = -1;  // 第一个位置不存储数据
        for (int i = 1; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        queue.add(new int[]{K, 0}); // start point: vertex K, distance 0
        dist[K] = 0;

        boolean[] visited = new boolean[N + 1];  // 记录顶点是否已访问

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int vertex = temp[0];
            int distance = temp[1];

            if (visited[vertex]) continue;

            if (graph.containsKey(vertex)) {
                for (int[] edge : graph.get(vertex)) {
                    int endVertex = edge[0];
                    int weight = edge[1];

                    if (distance + weight < dist[endVertex]) {
                        dist[endVertex] = distance + weight;
                        /**
                         * 如果队列中有当前节点，也新增一条顶点数据
                         * 因为新增的数据 distance较小，会先访问到，后续通过 visited[]可筛选掉该顶点之前的无效数据
                         */
                        queue.add(new int[]{endVertex, dist[endVertex]});
                    }
                }
            }
            visited[vertex] = true;
        }

        int max = 0;
        for (int d : dist) {
            if (d == Integer.MAX_VALUE) return -1;
            if (d > max) max = d;
        }
        return max;
    }

    /**
     * 改写 {@link #networkDelayTime(int[][], int, int)}
     */
    public static int networkDelayTime_2(int[][] times, int n, int k) {
        List<int[]>[] adjacencyList = new ArrayList[n + 1]; // 图的邻接表示，int[]存储边数据，两列分别表示指向的顶点和边的权重
        for (int[] time : times) {
            if (adjacencyList[time[0]] == null) {
                adjacencyList[time[0]] = new ArrayList<>();
            }
            adjacencyList[time[0]].add(new int[]{time[1], time[2]});
        }

        Vertex[] vertices = new Vertex[n + 1];  // 定义顶点数组
        for (int i = 1; i < vertices.length; i++) {
            vertices[i] = new Vertex(i, Integer.MAX_VALUE);
        }

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(vertices[k]);
        vertices[k].distance = 0; // 起点距离为0
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            if (!vertex.visited) {
                vertex.visited = true;
                if (adjacencyList[vertex.v] != null) { // 存在边
                    for (int[] edge : adjacencyList[vertex.v]) {
                        int distance = vertex.distance + edge[1];
                        if (distance < vertices[edge[0]].distance) {
                            vertices[edge[0]].distance = distance;
                            queue.remove(vertices[edge[0]]); // 目标顶点可能已存在优先队列，需要更新优先队列里该顶点的distance
                            queue.add(vertices[edge[0]]);
                        }
                    }
                }
            }
        }

        int max = 0;
        for (int i = 1; i <= n; i++) {
            if (!vertices[i].visited) { // 有顶点未被访问到，说明图不连通
                return -1;
            }
            max = Math.max(max, vertices[i].distance);
        }
        return max;
    }

    private static class Vertex implements Comparable {
        private int v; // 顶点id
        private int distance; // 起点到当前顶点的距离
        private boolean visited = false; // 当前顶点是否已访问

        public Vertex(int v, int distance) {
            this.v = v;
            this.distance = distance;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Vertex) {
                return this.distance - ((Vertex) o).distance;
            }
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[][]{
                new int[]{2, 1, 1},
                new int[]{2, 3, 1},
                new int[]{3, 4, 1}
        });
        param.add(4);
        param.add(2);
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[][]{
                new int[]{1, 2, 1},
                new int[]{2, 3, 7},
                new int[]{1, 3, 4},
                new int[]{2, 1, 2}
        });
        param.add(4);
        param.add(1);
        params.add(param);

        Utils.testStaticMethod(NetworkDelayTime.class
                , new HashSet<String>() {
                    {
                        add("networkDelayTime");
                        add("networkDelayTime_2");
                    }
                }, params);
    }
}
