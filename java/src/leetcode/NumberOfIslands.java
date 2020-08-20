package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 200: Number of Islands
 *
 *   Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water
 * and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 * Example 1:
 *   Input: grid = [
 *     ["1","1","1","1","0"],
 *     ["1","1","0","1","0"],
 *     ["1","1","0","0","0"],
 *     ["0","0","0","0","0"]
 *   ]
 *   Output: 1
 *
 * Example 2:
 *   Input: grid = [
 *     ["1","1","0","0","0"],
 *     ["1","1","0","0","0"],
 *     ["0","0","1","0","0"],
 *     ["0","0","0","1","1"]
 *   ]
 *   Output: 3
 */
public class NumberOfIslands {
    /**
     * DFS
     *   time complexity: O(m*n)
     *   space complexity: O(m*n)
     */
    public static int numIslands(char[][] grid) {
        int count = 0;
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }
        return count;
    }

    private static void dfs(char[][] grid, int x, int y) {
        grid[x][y] = '0';
        if (x - 1 >= 0 && grid[x - 1][y] == '1') { // up
            dfs(grid, x - 1, y);
        }

        if (y + 1 < grid[0].length && grid[x][y + 1] == '1') { // right
            dfs(grid, x, y + 1);
        }

        if (x + 1 < grid.length && grid[x + 1][y] == '1') {  // down
            dfs(grid, x + 1, y);
        }

        if (y - 1 >= 0 && grid[x][y - 1] == '1') {  // left
            dfs(grid, x, y - 1);
        }
    }

    /**
     * bfs
     *   time complexity: O(m*n)
     *   space complexity: O(min(m,n))
     */
    public static int numIslands_2(char[][] grid) {
        int count = 0;
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    grid[i][j] = '0';
                    queue.offer(i * n + j);
                    while (!queue.isEmpty()) {
                        int value = queue.poll();
                        int x = value / n;
                        int y = value % n;

                        if (x - 1 >= 0 && grid[x - 1][y] == '1') { // up
                            queue.offer((x - 1) * n + y);
                            grid[x - 1][y] = '0';
                        }

                        if (y + 1 < grid[0].length && grid[x][y + 1] == '1') { // right
                            queue.offer(x * n + (y + 1));
                            grid[x][y + 1] = '0';
                        }

                        if (x + 1 < grid.length && grid[x + 1][y] == '1') {  // down
                            queue.offer((x + 1) * n + y);
                            grid[x + 1][y] = '0';
                        }

                        if (y - 1 >= 0 && grid[x][y - 1] == '1') {  // left
                            queue.offer(x * n + y - 1);
                            grid[x][y - 1] = '0';
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * 并查集：
     *   如果当前位置为1，则将其相邻4个方向上1的并查集合并
     *
     * @see tree.DisjointSet
     * time complexity: O(m * n * alpha(m*n))
     * space complexity: O(m * n)
     */
    public static int numIslands_3(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;

        DisjointSet disjointSet = new DisjointSet(grid);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    grid[i][j] = '0';
                    if (i - 1 >= 0 && grid[i - 1][j] == '1') { // up
                        disjointSet.union(i * n + j, (i - 1) * n + j);
                    }

                    if (j + 1 < n && grid[i][j + 1] == '1') { // right
                        disjointSet.union(i * n + j, i * n + j + 1);
                    }

                    if (i + 1 < m && grid[i + 1][j] == '1') {  // down
                        disjointSet.union(i * n + j, (i + 1) * n + j);
                    }

                    if (j - 1 >= 0 && grid[i][j - 1] == '1') {  // left
                        disjointSet.union(i * n + j, i * n + j - 1);
                    }
                }
            }
        }
        return disjointSet.count;
    }

    /**
     * @see tree.DisjointSet
     */
    private static class DisjointSet {
        public int count;
        public int[] parent;
        public int[] rank;

        public DisjointSet(char[][] grid) {
            assert grid.length > 0;

            count = 0;
            int m = grid.length;
            int n = grid[0].length;
            parent = new int[m * n];
            rank = new int[m * n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {
                        count++;
                        int index = n * i + j;
                        parent[index] = index;
                        rank[index] = 0;
                    }
                }
            }
        }

        public int find(int idx) {
            if (parent[idx] != idx) {
                parent[idx] = find(parent[idx]); // 扁平化优化
            }
            return parent[idx];
        }

        public void union(int idx1, int idx2) {
            int xRoot = find(idx1);
            int yRoot = find(idx2);

            if (xRoot != yRoot) {
                if (rank[xRoot] < rank[yRoot]) {
                    parent[xRoot] = yRoot;
                } else if (rank[xRoot] > rank[yRoot]) {
                    parent[yRoot] = xRoot;
                } else {
                    parent[yRoot] = xRoot;
                    rank[xRoot] += 1;
                }
                count--;
            }
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new char[][]{
                new char[]{'1', '1', '1', '1', '0'},
                new char[]{'1', '1', '0', '1', '0'},
                new char[]{'1', '1', '0', '0', '0'},
                new char[]{'0', '0', '0', '0', '0'}
        });
        params.add(param);

        param = new ArrayList<>();
        param.add(new char[][]{
                new char[]{'1', '1', '0', '0', '0'},
                new char[]{'1', '1', '0', '0', '0'},
                new char[]{'0', '0', '1', '0', '0'},
                new char[]{'0', '0', '0', '1', '1'}
        });
        params.add(param);

        param = new ArrayList<>();
        param.add(new char[][]{
                new char[]{'1', '1', '1'},
                new char[]{'0', '1', '0'},
                new char[]{'1', '1', '1'},
        });
        params.add(param);

        param = new ArrayList<>();
        param.add(new char[][]{
                new char[]{'1', '1', '1'},
                new char[]{'1', '0', '1'},
                new char[]{'1', '1', '1'},
        });
        params.add(param);

        Utils.testStaticMethod(NumberOfIslands.class
                , new HashSet<String>() {
                    {
//                        add("numIslands");
//                        add("numIslands_2"); 由于参数是二维数组，且方法修改了参数值 => 一次只能测试一个方法
                        add("numIslands_3");
                    }
                }, params);
    }

}
