package leetcode;

import leetcode.definition.TreeNode;
import util.TreeUtils;
import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * leetcode 987: Vertical Order Traversal of a Binary Tree
 *
 * Given a binary tree, return the vertical order traversal of its nodes values.
 *   For each node at position (X, Y), its left and right children respectively will be at positions (X-1, Y-1)
 * and (X+1, Y-1).
 *
 *   Running a vertical line from X = -infinity to X = +infinity, whenever the vertical line touches some nodes, we
 * report the values of the nodes in order from top to bottom (decreasing Y coordinates).
 * If two nodes have the same position, then the value of the node that is reported first is the value that is smaller.
 * Return an list of non-empty reports in order of X coordinate.  Every report will have a list of values of nodes.
 *
 * Example 1:
 *              3
 *           /     \
 *         9         20
 *                 /    \
 *              15       7
 *   Input: [3,9,20,null,null,15,7]
 *   Output: [[9],[3,15],[20],[7]]
 *   Explanation:
 *     Without loss of generality, we can assume the root node is at position (0, 0):
 *     Then, the node with value 9 occurs at position (-1, -1);
 *     The nodes with values 3 and 15 occur at positions (0, 0) and (0, -2);
 *     The node with value 20 occurs at position (1, -1);
 *     The node with value 7 occurs at position (2, -2).
 *
 * Example 2:
 *                  1
 *               /     \
 *            2           3
 *          /   \       /   \
 *        4      5     6     7
 *   Input: [1,2,3,4,5,6,7]
 *   Output: [[4],[2],[1,5,6],[3],[7]]
 *   Explanation:
 *      The node with value 5 and the node with value 6 have the same position according to the given scheme.
 *      However, in the report "[1,5,6]", the node value of 5 comes first since 5 is smaller than 6.
 *
 * Note:
 *   The tree will have between 1 and 1000 nodes.
 *   Each node's value will be between 0 and 1000.
 */
public class VerticalOrderTraversalOfABinaryTree {
    private static List<Location> list;
    /**
     * steps:
     *   1.遍历二叉树，记录每个数对应的坐标(自定义类Location)
     *   2.对记录进行排序，排序规则
     *     1) x坐标按照从小到大排列
     *     2) x相同，y坐标按照从大到小排列
     *     3) x、y相同，val值按照从小到大排列
     *   3.依次按行输出结果
     *
     * 时间复杂度： 遍历二叉树 O(n), 排序 O(nlogn), 遍历节点依次输出 O(n) => 总时间复杂度: O(nlogn)
     * 空间复杂度： O(n)
     */
    public static List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        list = new ArrayList<>();
        dfs(root, 0, 0);

        Collections.sort(list);

        int rowNum = list.get(0).x;
        List<Integer> row = new ArrayList<>();
        result.add(row);
        int x, val;
        for (Location l : list) {
            x = l.x;
            val = l.val;
            if (x == rowNum) {
                row.add(val);
            } else {
                row = new ArrayList<>();
                result.add(row);
                row.add(val);
                rowNum = x;
            }
        }

        return result;
    }

    private static void dfs(TreeNode node, int x, int y) {
        if (node == null) {
            return;
        }
        dfs(node.left, x - 1, y - 1);
        list.add(new Location(x, y, node.val));
        dfs(node.right, x + 1, y - 1);
    }

    private static class Location implements Comparable<Location> {
        int x, y, val;

        public Location(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }

        @Override
        public int compareTo(Location o) {
            if (this.x != o.x) {
                return this.x - o.x;
            } else if (this.y != o.y) {
                return o.y - this.y;
            } else {
                return this.val - o.val;
            }
        }
    }

    /**
     * level traversal + min heap
     */
    public static List<List<Integer>> verticalTraversal_2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        Deque<Location> locationQueue = new ArrayDeque<>();
        PriorityQueue<Location> minHeap = new PriorityQueue<>();

        queue.offer(root);
        Location start = new Location(0, 0, root.val);
        locationQueue.offer(start);
        minHeap.add(start);

        TreeNode node;
        Location location, temp;
        while (!queue.isEmpty()) {
            node = queue.poll();
            location = locationQueue.poll();

            if (node.left != null) {
                queue.offer(node.left);

                temp = new Location(location.x - 1, location.y - 1, node.left.val);
                locationQueue.offer(temp);
                minHeap.add(temp);
            }

            if (node.right != null) {
                queue.offer(node.right);

                temp = new Location(location.x + 1, location.y - 1, node.right.val);
                locationQueue.offer(temp);
                minHeap.add(temp);
            }
        }

        List<Integer> list = new ArrayList<>();
        Integer currentRow = null;
        while (!minHeap.isEmpty()) {
            location = minHeap.poll();
            if (currentRow == null) {
                currentRow = location.x;
            } else if (currentRow != null && currentRow != location.x) {
                result.add(list);
                list = new ArrayList<>();
                currentRow = location.x;
            }
            list.add(location.val);
        }
        if (!list.isEmpty()) {
            result.add(list);
        }

        return result;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(VerticalOrderTraversalOfABinaryTree.class
                , new HashSet<String>() {
                    {
                        add("verticalTraversal");
                        add("verticalTraversal_2");
                    }
                }, Arrays.asList(
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{3, 9, 20, null, null, 15, 7})),
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{1, 2, 3, 4, 5, 6, 7})),
                        Arrays.asList(TreeUtils.constructBinaryTree(new Integer[]{0, null, 1, null, null, 2, 3, null, null, null, null, null, null, 4, 5}))
                ));
    }
}
