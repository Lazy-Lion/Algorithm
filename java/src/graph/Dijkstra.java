package graph;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Dijkstra's shortest path algorithm:
 *    Single-Source shortest path algorithm: 单源最短路径算法
 *      find shortest paths from a source vertex v to all other vertices in the graph
 * 该算法可以处理连通的 有向图或无向图，图中各边必须有 非负权重。
 *
 * 如果图比较大，Dijkstra算法会比较耗时。A*搜索算法是对Dijkstra的优化，求出的路径不一定是最优解，但是比较高效。
 */
public class Dijkstra {
    private Graph g;

    public Dijkstra(int n) {
        g = new Graph(n);
    }

    /**
     * 添加有向边
     */
    public void addEdge(int s, int t, int w) {
        g.addEdge(s, t, w);
    }

    /**
     * 思路：最优子结构，通过子问题的最优解可以推导出问题的最优解
     * 实现：
     *   1. 定义顶点数组vertices： 每个顶点包括如下字段
     *      1) v: 顶点id, 与数组index对应
     *      2) distance: 从源顶点s到 v的最小距离，默认无穷大, vertices[s].distance = 0;
     *      3) predecessor: 表示路径长度为distance时，当前顶点的前驱顶点。默认-1表示没有前驱。用于回溯具体路径。
     *      4) visited: 判断顶点是否被访问过 (访问过指的是已遍历顶点的邻接顶点)
     *   2. 定义优先队列 (最小堆)
     *      时间复杂度： time = O(E * logV)   E - 边数,  V - 顶点数
     *
     *      分析代码： 1.外层 while循环 O(V); 2. 遍历顶点的边,每次不同：分别为 E1,E2,...
     *        => 这两层循环的总时间复杂度为 O(E);
     *                3.优先队列操作，执行remove,add操作，时间复杂度为 O(logV)
     *        => 总的时间复杂度为 O(E*logV)
     *
     * @param s 开始顶点
     * @param t 目标顶点
     * @return s → t 的最短路径长度
     */
    public int dijkstra(int s, int t) {
        Graph.Vertex[] vertices = new Graph.Vertex[this.g.n];   // 定义n个顶点
        for (int i = 0; i < this.g.n; i++) {
            vertices[i] = new Graph.Vertex(i, Integer.MAX_VALUE);
        }
        vertices[s].distance = 0;  // source vertex: distance = 0

        PriorityQueue<Graph.Vertex> queue = new PriorityQueue<>();  // 优先队列
        queue.add(vertices[s]);

        while (!queue.isEmpty()) {
            /**
             * 访问distance最小的顶点
             *
             * 每次选 distance最小的顶点的理由：
             *   1. 初始化时，起点 distance = 0, 其他顶点 distance 为无穷大，是无效数据
             *   2. 即使不考虑起点和其他无效数据：假设起点处理完成之后对2个邻接顶点{v1,v2}赋值分别为 {3,5}
             *      对于最小距离的顶点 v1, 由于Dijkstra 算法仅适用于非负权重边的图，不会存在其他路径到v1的距离小于3，
             *      但是可能会存在到V2的距离小于5的情况（当v1→v2有边，且权重小于2时），所以如果先处理v2，其实此时处理过程的赋值都是无效的，
             *      当v2的距离通过v1更新成新值时，需要用新值重新处理v2的邻接点，而 visited数组在v2处理完成之后更新成了true,不会再次
             *      处理，导致出错。所以每次选取最小distance的顶点计算是必要条件。
             */
            Graph.Vertex vertex = queue.poll();

            if (vertex.visited) continue;

            for (Graph.Edge edge : g.table[vertex.v]) {
                int v = edge.t;

                int distance = vertex.distance + edge.w;
                if (distance < vertices[v].distance) {
                    /**
                     * 顶点v可能之前已经加入优先队列，需要更新distance。
                     * 优先队列没有提供update操作，所以先删除，再添加，以维持最小堆特性。
                     */
                    queue.remove(vertices[v]);
                    vertices[v].distance = distance;
                    vertices[v].predecessor = vertex.v;
                    queue.add(vertices[v]);
                }
            }
            vertex.visited = true;
        }

        System.out.println(getShortPath(s, t, vertices));  // 回溯路径

        return vertices[t].distance;
    }


    private String getShortPath(int s, int t, Graph.Vertex[] vertices) {
        int n = vertices.length;
        if (s < 0 || s >= n || t < 0 || t >= n) return "";

        StringBuilder builder = new StringBuilder();
        builder.append(t);
        int v = t;

        while (v != s && v != -1) {
            v = vertices[v].predecessor;
            builder.append(",").append(v);
        }
        if (v == -1) return "";

        return builder.reverse().toString();
    }

    // definition of weighted directed graph
    private static class Graph {
        private int n; // the numbers of vertices
        private LinkedList<Edge>[] table;  // adjacency list

        private Graph(int n) {
            this.n = n;
            this.table = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                table[i] = new LinkedList<>();
            }
        }

        private void addEdge(int s, int t, int w) {
            this.table[s].add(new Edge(s, t, w));
        }

        private static class Edge {
            private int s;  // source vertex
            private int t;  // target vertex
            private int w;  // weight of the edge

            private Edge(int s, int t, int w) {
                this.s = s;
                this.t = t;
                this.w = w;
            }
        }

        private static class Vertex implements Comparable<Vertex> {
            private int v; // vertex's id
            private int distance;  // the min distance from source vertex to v
            private int predecessor = -1;
            private boolean visited = false;

            private Vertex(int v, int distance) {
                this.v = v;
                this.distance = distance;
            }

            @Override
            public int compareTo(Vertex o) {   // 用于优先队列
                return this.distance - o.distance;
            }
        }
    }
}


