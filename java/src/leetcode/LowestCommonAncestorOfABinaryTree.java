package leetcode;

import leetcode.definition.TreeNode;
import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 236: Lowest Common Ancestor of a Binary Tree
 *
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 *
 * Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]
 *
 * Example 1:
 *   Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 *   Output: 3
 *   Explanation: The LCA of nodes 5 and 1 is 3.
 *
 * Example 2:
 *   Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 *   Output: 5
 *   Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 *
 * Note:
 *   All of the nodes' values will be unique.
 *   p and q are different and both values will exist in the binary tree.
 */
public class LowestCommonAncestorOfABinaryTree {
    /**
     * way1: recursion
     *
     * time complexity: O(n)
     * space complexity: O(n)
     *
     * 单次查询的时间复杂度为 O(n)
     * 由于随机树高为logn, 所以在随机树上单次查询时间复杂度：O(logn)
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (p == root || q == root) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }
        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;
    }

    /**
     * way2: 倍增法（预处理 + 查询）
     *   两种情况：
     *     1. 两节点深度相同：两个节点共同向上回溯，直到两节点相遇。
     *     2. 两节点深度不同：把深度较深的节点向上回溯，直到两节点深度相同。转换成问题1
     *   对于上述情况，每次向上回溯一步，时间复杂度较高，进行预处理优化：
     *     parent[x][i]: parent[x][i]表示节点x的第2^i个祖先 => parent[x][i] = parent[parent[x][i - 1]][i-1]
     *                          => 极端情况下，树是一个链表 2^i <= n => i <= log2(n)
     *                          => 对于任意树的任意节点，向上回溯log2(n)步至少可以回溯到树的根节点（根节点的父节点为null）
     *     depth[x]: depth[x]表示节点x的深度
     *
     *
     *   预处理时间复杂度：O(nlogn)
     *   单次查询时间复杂度：O(logn)
     *
     *   预处理时间复杂度较高，但是预处理完成之后，查询的时间复杂度仅为O(logn)，适用于需要多次查询的情况
     */
    private static Map<TreeNode, List<TreeNode>> parent; // 使用Map替代数组parent[x][i]
    private static Map<TreeNode, Integer> depth;  // 使用Map替代数组depth[x]
    private static int n; // n表示第2^i个祖先中i的最大值

    /**
     * 预处理时间复杂度： O(NlogN)
     * 预处理完成之后，单次查询时间复杂度：O(logN)
     *
     * 空间复杂度：O(NlogN)
     */
    public static TreeNode lowestCommonAncestor_2(TreeNode root, TreeNode p, TreeNode q) {
        int size = getSize(root); // 获取树的节点个数
        n = (int) Math.floor(Math.log(size + 0.0) / Math.log(2.0));  // 求log2(size)，即最大i

        // 1. 初始化， 用于预处理时统一根节点和其他子节点的处理
        parent = new HashMap<>();
        parent.put(null, new ArrayList<>(n + 1));
        // 根节点的第2^i个父节点均为null, 将null的parent设为null
        for (int i = 0; i <= n; i++) {
            parent.get(null).add(null);
        }

        depth = new HashMap<>();
        depth.put(null, -1);

        // 2.预处理dfs
        dfs(null, root, 0);

        // 找到深度较小的节点 → p
        if (depth.get(p).compareTo(depth.get(q)) > 0) {
            TreeNode temp = p;
            p = q;
            q = temp;
        }

        // 3.将深度较大的节点回溯至和深度较小节点同样的深度
        if (!depth.get(p).equals(depth.get(q))) {
            for (int i = n; i >= 0; i--) {
                if (depth.get(p).compareTo(depth.get(q)) < 0 && depth.get(parent.get(q).get(i)).compareTo(depth.get(p)) >= 0) {
                    q = parent.get(q).get(i);
                }
            }
        }

        if (p == q) { // 相同深度时是同一个节点则为最小公共祖先
            return p;
        }

        // 4. 两个节点同时向上回溯
        for (int i = n; i >= 0; i--) {
            // 需要从大向小回溯（[0,i)的组合可以表示小于2^i的任意数）
            // 循环结束时，parent.get(p).get(0) == parent.get(q).get(0) => 即 p、q的父节点相同，为最近公共祖先
            if (parent.get(p).get(i) != parent.get(q).get(i)) {
                p = parent.get(p).get(i);
                q = parent.get(q).get(i);
            }
        }

        return parent.get(p).get(0);
    }

    /**
     * 获取树的节点个数
     */
    private static int getSize(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return getSize(root.left) + getSize(root.right) + 1;
    }

    /**
     * 预处理
     *   time complexity: O(NlogN)
     *   space complexity: recursion - O(N)
     */
    private static void dfs(TreeNode root, TreeNode node, int currentDepth) {
        if (node == null) {
            return;
        }

        for (int i = 0; i <= n; i++) {
            List<TreeNode> list = parent.get(node);
            if (list == null) {
                list = new ArrayList<>(n + 1);
                parent.put(node, list);
            }
            if (i == 0) {
                list.add(i, root);
            } else {
                list.add(i, parent.get(list.get(i - 1)).get(i - 1));
            }
        }

        depth.put(node, currentDepth);

        dfs(node, node.left, currentDepth + 1);
        dfs(node, node.right, currentDepth + 1);
    }

    /**
     * 如果某个节点p是两个节点u、v的公共祖先，如果u、v分别在节点p的左右子树上，则p是u、v的最近公共祖先。
     *
     * way3: Tarjan算法：是离线算法。在图中寻找强连通分量，需要用并查集记录节点的祖先节点。基于深度优先(DFS)搜索一个有向图。 todo
     *   （
     *     强连通： 图上两点vi,vj (vi != vj), vi到Vj 和 vj到vi 都存在路径，则vi，vj强连通。
     *     强连通图： 图上任意两点都强连通的有向图。
     *     强连通分量：有向图的强连通子图
     *
     *     图进行DFS时，引申的概念：
     *       树边：DFS时经过的边，即DFS搜索树上的边。
     *       前向边：与DFS方向一致，从某个结点指向其某个子孙的边。
     *       后向边：与DFS方向相反，从某个结点指向其某个祖先的边。（返祖边）
     *       横叉边：从某个结点指向搜索树中的另一子树中的某结点的边。
     *    ）
     *
     *   任选一个节点开始进行深度优先搜索（若深度优先搜索结束后仍有未访问的节点，则从中任选一点再进行dfs）。搜索过程中已访问的节点不再访问。
     *
     *   dfs + 并查集
     */
    public static TreeNode lowestCommonAncestor_3(TreeNode root, TreeNode p, TreeNode q) {
        return null;
    }

    /**
     * way4: 转换成RMQ问题(Range Minimum Query,区间最值查询，用来在数组中查找两个指定索引区间内最小值的位置。) todo
     */
    public static TreeNode lowestCommonAncestor_4(TreeNode root, TreeNode p, TreeNode q) {
        return null;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();
        List<Object> param = new ArrayList<>();

        TreeNode root = new TreeNode(3);
        TreeNode p = new TreeNode(5);
        root.left = p;
        TreeNode q = new TreeNode(1);
        root.right = q;
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(7);
        TreeNode q2 = new TreeNode(4);
        root.left.right.right = q2;
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        param.add(root);
        param.add(p);
        param.add(q2);
        params.add(param);

        Utils.testStaticMethod(LowestCommonAncestorOfABinaryTree.class
                , new HashSet<String>() {
                    {
                        add("lowestCommonAncestor");
                        add("lowestCommonAncestor_2");
                    }
                }, params);
    }
}
