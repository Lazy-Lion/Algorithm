package tree;

/**
 * 二叉查找树(Binary Search Tree, 二叉搜索树)：
 *      1.若任意节点的左子树不为空，则左子树所有节点的值均小于它的根节点的值
 *      2.若任意节点的右子树不为空，则右子树所有节点的值均大于等于它的根节点的值
 *      3.任意节点的左、右子树也分别为二叉查找树
 * 中序遍历二叉查找树，输出有序的数据序列，时间复杂度为 O(n)
 * 支持快速查找、插入、删除:
 *      查找的时间复杂度： 最好时间复杂度(完全二叉树)： O(logn)
 *                       最坏时间复杂度(退化成链表):  O(n)
 */
public class BinarySearchTree {
    private TreeNode root;

    /**
     * search a binary search tree for a specific key
     * @param key
     * @return the first node for the specific key
     */
    public TreeNode search(int key) {
        TreeNode node = root;

        while (node != null) {
            if (node.val == key) {
                return node;
            } else if (node.val > key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    // insert: allow duplicate
    // 新插入的数据都是在叶子节点上
    public void insert(int key) {
        TreeNode node = root;

        if (node == null) {
            root = new TreeNode(key);
        }

        while (node != null) {
            if (key < node.val) {
                if (node.left == null) {
                    node.left = new TreeNode(key);
                    return;
                } else {
                    node = node.left;
                }
            } else {  // data >= node.getData()
                if (node.right == null) {
                    node.right = new TreeNode(key);
                    return;
                } else {
                    node = node.right;
                }
            }
        }
    }

    /**
     * delete: search the node with given value => node to be deleted (Node)
     *   1. Node没有子节点(Node is a leaf node)，Node 的父节点指向 Node 的指针设为 null
     *   2. Node只有一个子节点，Node 的父节点指向 Node 的指针设为 Node的非空子节点
     *   3. Node有两个子节点，从右子树上找到最小节点(Min, 或左子树最大节点 Max)，替换 Min节点的数据到 Node，
     *      将 Min节点删除(Min is a leaf node, or Min only has right child node)
     *
     * 实现只删除一个指定关键词的节点，由于insert实现允许重复，若要删除全部，需要继续操作，直到不存在关键词为指定数据的节点(已删除
     *       节点为叶子节点，或继续迭代找不到指定关键词节点)
     * @param key
     * @return true if delete successfully
     */
    public boolean delete(int key) {
        TreeNode pNode = null;  // node父节点
        TreeNode node = root;

        while (node != null && key != node.val) {
            pNode = node;
            if (key < node.val) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        if (node == null) { // not found
            return false;
        }

        if (node.left != null && node.right != null) {   // 待删节点有左右子节点
            TreeNode min = node.right;
            TreeNode pMin = node;   // min节点的父节点
            while (min.left != null) {
                pMin = min;
                min = min.left;
            }

            node.val = min.val;   // 数据替换
            pNode = pMin;   // min节点成为待删除节点
            node = min;
        }

        TreeNode child;
        if (node.left == null) {
            child = node.right;
        } else {
            child = node.left;
        }

        if (pNode == null) { // 删除节点为根节点
            root = child;
        } else if (pNode.left == node) {
            pNode.left = child;
        } else {
            pNode.right = child;
        }

        return true;
    }

    public TreeNode max() {
        TreeNode node = root;
        if (node == null) {
            return null;
        }

        while (node.right != null) {
            node = node.right;
        }

        return node;
    }

    public TreeNode min() {
        TreeNode node = root;
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    public void printTree() {
        inOrderTraversal(root);
        System.out.println();
    }

    // 中序遍历
    private void inOrderTraversal(TreeNode node) {
        if (node == null) return;

        inOrderTraversal(node.left);
        System.out.print(node.val + " ");
        inOrderTraversal(node.right);
    }
}
