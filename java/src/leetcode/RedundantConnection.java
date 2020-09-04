package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * leetcode 684. Redundant Connection
 *
 *   In this problem, a tree is an undirected graph that is connected and has no cycles.
 *
 *   The given input is a graph that started as a tree with N nodes (with distinct values 1, 2, ..., N), with one
 * additional edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that already existed.
 *
 *   The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] with u < v, that represents
 * an undirected edge connecting nodes u and v.
 *
 *   Return an edge that can be removed so that the resulting graph is a tree of N nodes. If there are multiple answers,
 * return the answer that occurs last in the given 2D-array. The answer edge [u, v] should be in the same format, with u < v.
 *
 * Example 1:
 *   Input: [[1,2], [1,3], [2,3]]
 *   Output: [2,3]
 *   Explanation: The given undirected graph will be like this:
 *       1
 *      / \
 *     2 - 3
 *
 * Example 2:
 *   Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
 *   Output: [1,4]
 *   Explanation: The given undirected graph will be like this:
 *     5 - 1 - 2
 *         |   |
 *         4 - 3
 * Note:
 *   The size of the input 2D-array will be between 3 and 1000.
 *   Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
 */
public class RedundantConnection {
    /**
     * 最小生成树 {@link graph.MinimumSpanningTree}
     *     边的权重可理解为 edges数组索引下标
     *
     * Kruskal 算法
     *
     * time complexity: O(n alpha(n))  - alpha(n) 并查集操作时间
     * space complexity: O(n)
     */
    public static int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;

        UnionFind unionFind = new UnionFind(n);
        for (int[] edge : edges) {
            if (unionFind.find(edge[0]) == unionFind.find(edge[1])) {
                return new int[]{edge[0], edge[1]};
            } else {
                unionFind.union(edge[0], edge[1]);
            }
        }

        return new int[2];
    }

    private static class UnionFind {
        private int[] parent;
        private int[] rank;

        UnionFind(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];

            for (int i = 0; i <= n; i++) {
                parent[i] = i;
            }
        }

        int find(int idx) {
            if (parent[idx] != idx) {
                parent[idx] = find(parent[idx]);
            }
            return parent[idx];
        }

        void union(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);

            if (rank[xRoot] < rank[yRoot]) {
                parent[xRoot] = yRoot;
            } else if (rank[xRoot] > rank[yRoot]) {
                parent[yRoot] = xRoot;
            } else {
                parent[xRoot] = yRoot;
                rank[yRoot] += 1;
            }
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new int[][]{
                new int[]{1, 2},
                new int[]{1, 3},
                new int[]{2, 3}
        });
        params.add(param);

        param = new ArrayList<>();
        param.add(new int[][]{
                new int[]{1, 2},
                new int[]{2, 3},
                new int[]{3, 4},
                new int[]{1, 4},
                new int[]{1, 5}
        });
        params.add(param);

        Utils.testStaticMethod(RedundantConnection.class
                , new HashSet<String>() {
                    {
                        add("findRedundantConnection");
                    }
                }, params);
    }
}
