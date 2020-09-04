package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 959: Regions Cut By Slashes
 *
 *   In a N x N grid composed of 1 x 1 squares, each 1 x 1 square consists of a /, \, or blank space.  These characters
 * divide the square into contiguous regions.
 *
 * (Note that backslash characters are escaped, so a \ is represented as "\\".)
 *
 * Return the number of regions.
 *
 * Example 1:
 *   Input:
 *   [
 *     " /",
 *     "/ "
 *   ]
 *   Output: 2
 *
 * Example 2:
 *   Input:
 *   [
 *     " /",
 *     "  "
 *   ]
 *   Output: 1
 *
 * Example 3:
 *   Input:
 *   [
 *     "\\/",
 *     "/\\"
 *   ]
 *   Output: 4
 *
 * Example 4:
 *   Input:
 *   [
 *     "/\\",
 *     "\\/"
 *   ]
 *   Output: 5
 *
 * Example 5:
 *   Input:
 *   [
 *     "//",
 *     "/ "
 *   ]
 *   Output: 3
 *
 * Note:
 *   1 <= grid.length == grid[0].length <= 30
 *   grid[i][j] is either '/', '\', or ' '.
 */
public class RegionsCutBySlashes {
    private static final char LEFT_SLASH = '/';
    private static final char RIGHT_SLASH = '\\';
    private static final char BLANK_SLASH = ' ';

    /**
     * 并查集：一个矩形用两条对角线分割成4块区域，按照上右下左的顺序编号
     *        对于grid[n][n]，共可分为 4*n*n个区域
     *        对于grid[i][j]，其上右下左对应的区域编号分别为 4*(i*n+j), 4*(i*n+j)+1, 4*(i*n+j)+2, 4*(i*n+j)+3
     *
     * time complexity: O(n * n * alpha(n))  - alpha(n) 并查集操作时间
     * space complexity: O(n * n)
     */
    public static int regionsBySlashes(String[] grid) {
        int n = grid.length;
        if (n == 0) {
            return 0;
        }

        UnionFind unionFind = new UnionFind(4 * n * n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int baseIndex = 4 * (i * n + j);

                if (grid[i].charAt(j) == BLANK_SLASH) {
                    unionFind.union(baseIndex, baseIndex + 1);
                    unionFind.union(baseIndex, baseIndex + 2);
                    unionFind.union(baseIndex, baseIndex + 3);
                } else if (grid[i].charAt(j) == LEFT_SLASH) {
                    unionFind.union(baseIndex, baseIndex + 3);
                    unionFind.union(baseIndex + 1, baseIndex + 2);
                } else if (grid[i].charAt(j) == RIGHT_SLASH) {
                    unionFind.union(baseIndex, baseIndex + 1);
                    unionFind.union(baseIndex + 2, baseIndex + 3);
                }

                // up
                if (i > 0) {
                    unionFind.union(baseIndex, 4 * ((i - 1) * n + j) + 2);
                }
                // down
                if (i < n - 1) {
                    unionFind.union(baseIndex + 2, 4 * ((i + 1) * n + j));
                }
                // left
                if (j > 0) {
                    unionFind.union(baseIndex + 3, 4 * (i * n + j - 1) + 1);
                }
                // right
                if (j < n - 1) {
                    unionFind.union(baseIndex + 1, 4 * (i * n + j + 1) + 3);
                }
            }
        }

        return unionFind.count;
    }

    private static class UnionFind {
        private int[] parent;
        private int[] rank;
        private int count;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
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

            if (xRoot != yRoot) {
                if (rank[xRoot] < rank[yRoot]) {
                    parent[xRoot] = yRoot;
                } else if (rank[xRoot] > rank[yRoot]) {
                    parent[yRoot] = xRoot;
                } else {
                    parent[xRoot] = yRoot;
                    rank[yRoot] += 1;
                }
                count--;
            }
        }
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();
        param.add(new String[]{" /", "/ "});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{" /", "  "});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"\\/", "/\\"});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"/\\", "\\/"});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"//", "/ "});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"\\/\\ ", " /\\/", " \\/ ", "/ / "});
        params.add(param);

        Utils.testStaticMethod(RegionsCutBySlashes.class
                , new HashSet<String>() {
                    {
                        add("regionsBySlashes");
                    }
                }, params);
    }
}
