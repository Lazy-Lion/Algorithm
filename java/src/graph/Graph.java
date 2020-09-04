package graph;

import java.util.*;

/**
 * 一、图 (graph): 无向图 (Undirected Graphs, 边没有方向), 有向图 (Directed Graphs, 边有方向),带权图 (Weighted Graphs, 每条
 *    边有一个权重)
 *
 *
 *   < 引用自 https://algs4.cs.princeton.edu/41graph/ >
 *   A graph is a set of vertices and a collection of edges that each connect a pair of vertices. We use the
 *   names 0 through V-1 for the vertices in a V-vertex graph.
 *
 *  Glossary :
 *   I. Undirected Graphs
 *     1. A self-loop is an edge that connects a vertex to itself.
 *     2. Two edges are parallel if they connect the same pair of vertices.
 *     3. When an edge connects two vertices, we say that the vertices are adjacent to one another and that the edge is
 *        incident on both vertices.
 *     4. The degree of a vertex is the number of edges incident on it.
 *     5. A subgraph is a subset of a graph's edges (and associated vertices) that constitutes a graph.
 *     6. A path in a graph is a sequence of vertices connected by edges. A simple path is one with no repeated vertices.
 *     7. A cycle is a path (with at least one edge) whose first and last vertices are the same. A simple cycle is
 *        a cycle with no repeated edges or vertices (except the requisite repetition of the first and last vertices).
 *     8. The length of a path or a cycle is its number of edges.
 *     9. We say that one vertex is connected to another if there exists a path that contains both of them.
 *     10.A graph is connected if there is a path from every vertex to every other vertex.
 *     11.A graph that is not connected consists of a set of connected components, which are maximal connected subgraphs.
 *     12.An acyclic graph is a graph with no cycles.
 *     13.A tree is an acyclic connected graph.
 *     14.A forest is a disjoint set of trees.
 *     15.A spanning tree of a connected graph is a subgraph that contains all of that graph's vertices and is a single
 *        tree. A spanning forest of a graph is the union of the spanning trees of its connected components.
 *     16.A bipartite graph is a graph whose vertices we can divide into two sets such that all edges connect a vertex
 *        in one set with a vertex in the other set.
 *
 *   II. Directed Graphs
 *     1. A self-loop is an edge that connects a vertex to itself.
 *     2. Two edges are parallel if they connect the same ordered pair of vertices.
 *     3. The outdegree of a vertex is the number of edges pointing from it. The indegree of a vertex is the number
 *        of edges pointing to it.
 *     4. A subgraph is a subset of a digraph's edges (and associated vertices) that constitutes a digraph.
 *     5. A directed path in a digraph is a sequence of vertices in which there is a (directed) edge pointing from each
 *        vertex in the sequence to its successor in the sequence. A simple path is one with no repeated vertices.
 *     6. A directed cycle is a directed path (with at least one edge) whose first and last vertices are the same. A
 *        simple cycle is a cycle with no repeated edges or vertices (except the requisite repetition of the first and
 *        last vertices).
 *     7. The length of a path or a cycle is its number of edges.
 *     8. We say that a vertex w is reachable from a vertex v if there exists a directed path from v to w.
 *     9. We say that two vertices v and w are strongly connected if they are mutually reachable: there is a directed
 *        path from v to w and a directed path from w to v.
 *     10.A digraph is strongly connected if there is a directed path from every vertex to every other vertex.
 *     11.A digraph that is not strongly connected consists of a set of strongly-connected components, which are maximal
 *        strongly-connected subgraphs.
 *     12.A directed acyclic graph (or DAG) is a digraph with no directed cycles.
 *
 *
 * 二、图的存储方式：
 *    1. 邻接矩阵 (Adjacency Matrix): 二维数组形式: 如果存在从顶点 i 指向顶点 j的边，则 A[i][j] == 1; 否则 A[i][j] = 0. 对于带权图
 *                                   使用权重作为 A[i][j]的值
 *          较为浪费空间 (特别对于稀疏图 (Sparse Matrix)), 但是计算方便
 *    2. 邻接表 (Adjacency list): 类似散列表, 数组 + 链表实现；
 *                 如图 (引用自 <a href="https://algs4.cs.princeton.edu/41graph/"></a> )：
 *               <a href="https://github.com/Lazy-Lion/images/blob/master/dataStructure/adjacency-lists.png'></a>
 *          节省空间，但是使用起来较为麻烦
 *    3. 将图持久化到数据库表中
 *
 * Graph类基于邻接表的方式实现有向图
 */
public class Graph {
    private final int v; //numbers of vertices in this digraph, from 0 to v-1
    private int e;   // numbers of edges in this digraph
    private Node<Integer>[] table;   // table[v] is adjacency list of vertex v
    // private LinkedList<Integer>[] table;  // simple implementation of java
    private int[] indegree;   // indegree[v] is the indegree of vertex v, get outdegree by traversing table[v]

    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    private boolean found = false;   // for dfs

    public Graph(int v) {
        this.v = v;
        e = 0;
        table = (Node<Integer>[]) new Node[v];
        indegree = new int[v];
    }

    /**
     * 邻接表 链表节点
     * @param <K>
     */
    private static class Node<K> implements Iterable<K> {
        private K item;
        private Node<K> next;

        public Node(K item) {
            this.item = item;
            this.next = null;
        }

        @Override
        public Iterator<K> iterator() {
            return new Iter(item, next);
        }

        private static class Iter<K> implements Iterator<K> {
            private Node<K> current;
            private Node<K> n;

            private Iter(K item, Node<K> next) {
                this.current = new Node<>(item);
                this.current.next = next;
                n = next;
            }

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public K next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                K r = current.item;
                current = n;
                n = current == null ? null : current.next;
                return r;
            }
        }
    }

    /**
     * @return the number of vertices in this digraph
     */
    public int vertex() {
        return this.v;
    }

    /**
     * @return the number of edges in this digraph
     */
    public int edge() {
        return this.e;
    }

    /**
     * @param v
     * @return the indegree of vertex v
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * @param v
     * @return the outdegree of vertex v
     */
    public int outdegree(int v) {

        int c = 0;
        Node<Integer> l = table[v];
        while (l != null) {
            c++;
            l = l.next;
        }
        return c;
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        Node<Integer> l = table[v];
        boolean hasEdge = false;

        if (l == null) {
            table[v] = new Node<>(w);
        } else {
            /**
             *  1. java中Integer与int的比较:先将Integer转换成int，再进行值比较
             *  2. Integer类中 -128~127 有cache
             */
            if (l.item == w) return;

            while (l.next != null) {
                if (l.next.item == w) {
                    hasEdge = true;
                    break;
                }
                l = l.next;
            }
        }

        if (!hasEdge) {
            if (l != null) l.next = new Node<>(w);
            e++;
            indegree[w]++;
        }
    }

    /**
     * throw an IllegalArgumentException unless 0 <= v < this.v
     */
    private void validateVertex(int v) {
        if (v < 0 || v >= this.v)
            throw new IllegalArgumentException("vertex " + v + " does not exist");
    }

    /**
     * reverse the digraph
     */
    public Graph reverse() {
        Graph graph = new Graph(this.v);
        for (int i = 0; i < this.v; i++) {
            if (table[i] == null) continue;

            for (Integer w : table[i]) {
                graph.addEdge(w, i);
            }
        }
        return graph;
    }

    /**
     * Breadth First Search (BFS, 广度优先搜索)
     *   shortest path from source vertex s to destination vertex t
     *   assert graph is connected
     *
     *  time complexity：O(e + v)  // e = edge(), v = vertex()
     *  space complexity：O(v)      // e = edge()
     */
    public int bfs(int s, int t) {

        validateVertex(s);
        validateVertex(t);

        if (s == t) return 0;

        int[] path = new int[this.v];    // 记录搜索路径 (最短路径)
        for (int i = 0; i < this.v; i++)
            path[i] = -1;

        boolean[] visited = new boolean[this.v];    // 标记顶点是否已被访问
        Queue<Integer> queue = new LinkedList<>();  // 存储已被访问、但邻接顶点还没有被访问的顶点
        queue.add(s);
        visited[s] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            if (table[v] == null) continue;
            for (Integer i : table[v]) {
                if (visited[i]) continue;
                path[i] = v;
                if (i == t) {
                    return getPath(path, s, t);
                }
                queue.add(i);
                visited[i] = true;
            }
        }

        return -1;
    }

    /**
     * print path and return path length
     * assert path exists
     */
    private int getPath(int[] path, int s, int t) {
        if (s == t) {
            return 0;
        }

        int c = getPath(path, s, path[t]) + 1;
        System.out.print(path[t] + " → " + t + "; ");
        return c;
    }

    /**
     * Depth First Search (DFS, 深度优先搜索)
     *   assert graph is connected
     *
     * time complexity： O(e)   - e = edge(),每条边最多访问两次，一次遍历，一次回溯
     * space complexity: O(v)   - v = vertex()
     *
     * Application of DFS:
     *   1. check connectivity of the graph (if not connected, find number of connected components)
     *   2. check a cyclicity of the graph
     */
    public void dfs(int s, int t) {
        if (s == t) return;
        validateVertex(s);
        validateVertex(t);

        boolean[] visited = new boolean[this.v];
        found = false;   // 全局变量，用于标记已找到终点，防止多余执行的递归和回溯
        int[] prev = new int[this.v];  // 记录搜索路径 (非最短路径)
        dfs(visited, s, t, prev);
        getPath(prev, s, t);
    }

    private void dfs(boolean[] visited, int s, int t, int[] prev) {
        if (found) return;

        if (!visited[s]) {
            visited[s] = true;
        }

        if (s == t) {
            found = true;
            return;
        }

        if (table[s] == null) return;
        for (Integer i : table[s]) {
            if (!visited[i]) {
                prev[i] = s;
                dfs(visited, i, t, prev);
            }
        }
    }

    /**
     * DFS,迭代方式：使用栈
     *   代码和 bfs类似，只是 bfs使用 queue, dfs使用 stack，遍历结果类似于树的前序遍历
     */
    public void dfsIteration(int s, int t) {
        if (s == t) return;
        validateVertex(s);
        validateVertex(t);

        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[this.v];
        int[] prev = new int[this.v];

        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.pop();

            if (visited[v]) continue;

            visited[v] = true;
            if (v == t) {
                getPath(prev, s, t);
                return;
            }

            if (table[v] == null) continue;
            List<Integer> vertices = new ArrayList<>();
            for (Integer i : table[v]) {
                if (!visited[i]) {
                    prev[i] = v;
                    vertices.add(i);
                }
            }

            for (int i = vertices.size() - 1; i >= 0; i--) { // push stack in reverse order
                stack.push(vertices.get(i));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(v + " vertices, " + e + " edges " + LINE_SEPARATOR);

        for (int i = 0; i < this.v; i++) {
            if (this.table[i] == null) continue;

            for (Integer w : this.table[i]) {
                s.append(i + " → " + w + LINE_SEPARATOR);
            }
        }
        return s.toString();
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(0, 4);
        g.addEdge(1, 3);
        g.addEdge(1, 0);
        g.addEdge(3, 0);
        g.addEdge(3, 2);
        g.addEdge(4, 0);

        System.out.println(g.toString());
        System.out.println(g.indegree(0));
        System.out.println(g.outdegree(0));

        Graph reverse = g.reverse();
        System.out.println(reverse.toString());
        System.out.println(reverse.indegree(0));
        System.out.println(reverse.outdegree(0));

        System.out.println("Breadth-First Search:");
        System.out.println(g.bfs(0, 3));

        g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 4);
        g.addEdge(3, 0);
        g.addEdge(4, 0);

        System.out.println(g.bfs(0, 4));
        System.out.println(g.bfs(3, 1));

        System.out.println("Depth-First Search(recursion):");
        g.dfs(0, 4);
        System.out.println();
        g.dfs(3, 0);
        System.out.println();

        System.out.println("Depth-First Search(iteration):");
        g.dfsIteration(0, 4);
        System.out.println();
        g.dfsIteration(3, 0);
        System.out.println();

        System.out.println("Breadth-First Search:");
        g.bfs(0, 4);
        System.out.println();
        g.bfs(3, 0);
        System.out.println();

        g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        System.out.println("Depth-First Search(recursion):");
        g.dfs(0, 4);
        System.out.println();
        System.out.println("Depth-First Search(iteration):");
        g.dfsIteration(0, 4);
    }
}

