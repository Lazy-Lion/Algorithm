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
 *    1. 邻接矩阵 (Adjacency Matrix): 二维数组形式: A[i][j] == 1 表示存在边从顶点 i 指向顶点 j; 否则 A[i][j] = 0. 对于带权图
 *                                   使用权重作为 A[i][j]的值
 *          较为浪费空间 (特别对于稀疏图 (Sparse Matrix)), 但是计算方便
 *    2. 邻接表 (Adjacency list): 类似散列表, 数组 + 链表实现；
 *                 如图 (引用自 https://algs4.cs.princeton.edu/41graph/ )：
 *               https://github.com/Lazy-Lion/Graphic/blob/master/dataStructure/adjacency-lists.png
 *          节省空间，但是使用起来较为麻烦
 *
 */
public class graph {
}
