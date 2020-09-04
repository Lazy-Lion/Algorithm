package graph;

import java.util.*;

/**
 * Minimum spanning tree (最小生成树，针对连通加权无向图)：
 *    In the mathematical field of graph theory, a spanning tree T of an undirected graph G is a subgraph that is a
 *  tree which includes all of the vertices of G, with a minimum possible number of edges (weight sum minimum).
 *  In general, a graph may have several spanning trees, but a graph that is not connected will not contain a spanning
 *  tree. If all of the edges of G are also edges of a spanning tree T of G, then
 *  G is a tree and is identical to T (that is, a tree has a unique spanning tree and it is itself))
 */
public class MinimumSpanningTree {
    private Graph graph;

    public MinimumSpanningTree(Graph graph) {
        this.graph = graph;
    }

    /**
     * Kruskal 算法 (greedy)
     * steps:
     *   1. create a forest F (a set of trees), where each vertex in the graph is a separate tree
     *   2. create a set S containing all the edges in the graph
     *   3. while S is nonempty and F is not yet spanning
     *      1) remove an edge with minimum weight from S
     *      2) if the removed edge connects two different trees then add it to the forest F, combining two trees into a single tree
     *
     *    At the termination of the algorithm, the forest forms a minimum spanning forest of the graph. If the graph is
     *  connected, the forest has a single component and forms a minimum spanning tree.
     *
     * 代码实现：
     *    3- 1) 优先队列可实现
     *    3- 2) 并查集问题 {@link tree.DisjointSet}
     *
     * 如果处理的是非连通无向图，Kruskal算法的结果是最小生成森林。
     *
     * time complexity: O(E alpha(V))  E - 图的边数，V - 图的顶点数， alpha(V) - 并查集操作的时间
     *
     */
    public Graph kruskal() {
        Graph spanningTree = new Graph(graph.n);
        UnionFind unionFind = new UnionFind(graph.n);

        PriorityQueue<Graph.Edge> queue = new PriorityQueue<>();
        for (int i = 0; i < graph.n; i++) {
            List<Graph.Edge> list = graph.table[i];
            if (list != null) {
                for (Graph.Edge edge : list) {
                    queue.add(edge);
                }
            }
        }

        while (!queue.isEmpty()) {
            Graph.Edge edge = queue.poll();
            int v1 = edge.s;
            int v2 = edge.t;
            if (unionFind.find(v1) != unionFind.find(v2)) {
                spanningTree.addEdge(v1, v2, edge.w);
                unionFind.union(v1, v2);
            }
        }

        return spanningTree;
    }

    /**
     * Prim 算法 (greedy)：
     * steps:
     *   1. initialize a tree with a single vertex, chosen arbitrarily from the graph.
     *   2. grow the tree by one edge: of the edges that connect the tree to vertices not yet in the tree, find the
     *      minimum-weight edge, and transfer it to the tree.
     *   3. repeat step 2 (until all vertices are in the tree).
     *
     * time complexity: O(E logV)  E - 边数， V - 顶点数
     */
    public Graph prim() {
        Graph spanningTree = new Graph(graph.n);
        Set<Integer> vertexSet = new HashSet<>();
        PriorityQueue<Graph.Edge> queue = new PriorityQueue<>();

        vertexSet.add(0); // choose any vertex from the graph
        for (Graph.Edge e : graph.table[0]) {
            queue.add(e);
        }

        while (!queue.isEmpty()) {
            Graph.Edge edge = queue.poll();
            if (!vertexSet.contains(edge.t)) {
                spanningTree.addEdge(edge.s, edge.t, edge.w);
                vertexSet.add(edge.t);
                for (Graph.Edge e : graph.table[edge.t]) {
                    queue.add(e);
                }
            }
        }
        return spanningTree;
    }

    /**
     * definition of weighted undirected graph
     */
    private static class Graph {
        private int n; // the numbers of vertices, vertex's id from 0 to n-1
        private List<Edge>[] table;  // adjacency list

        private Graph(int n) {
            this.n = n;
            this.table = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                table[i] = new LinkedList<>();
            }
        }

        private void addEdge(int s, int t, int w) {
            this.table[s].add(new Graph.Edge(s, t, w));
        }

        private static class Edge implements Comparable<Edge> {
            private int s;  // vertex 1
            private int t;  // vertex 2, assert s <= t
            private int w;  // weight of the edge

            public Edge(int s, int t, int w) {
                this.s = s;
                this.t = t;
                this.w = w;
            }

            @Override
            public int compareTo(Edge o) {
                return this.w - o.w;
            }
        }
    }

    private static class UnionFind {
        private int[] parent;
        private int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = i;  // 初始化，每个顶点是一个独立的树，父顶点为它本身
            }
            rank = new int[n];
        }

        private int find(int vertex) {
            if (parent[vertex] != vertex) {
                parent[vertex] = find(parent[vertex]);
            }
            return parent[vertex];
        }

        private void union(int vertex1, int vertex2) {
            int xRoot = find(vertex1);
            int yRoot = find(vertex2);

            if (xRoot != yRoot) {
                if (rank[xRoot] < rank[yRoot]) {
                    parent[xRoot] = yRoot;
                } else if (rank[xRoot] > rank[yRoot]) {
                    parent[yRoot] = xRoot;
                } else {
                    parent[yRoot] = xRoot;
                    rank[xRoot] += 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 3, 5);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 4);
        graph.addEdge(1, 4, 2);

        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(graph);
        Graph kruskal = minimumSpanningTree.kruskal();
        Graph prim = minimumSpanningTree.prim();
        System.out.println();
    }
}
