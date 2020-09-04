package tree;

/**
 * 并查集（Disjoint Set）:
 *   定义(wikipedia)：
 *     In computer science, a disjoint-set data structure (also called a union–find data structure or merge–find set)
 *   is a data structure that tracks a set of elements partitioned into a number of disjoint (non-overlapping) subsets.
 *   It provides near-constant-time operations (bounded by the inverse Ackermann function) to add new sets,
 *   to merge existing sets, and to determine whether elements are in the same set.
 *   In addition to many other uses, disjoint-sets play a key role in Kruskal's algorithm for finding the minimum spanning tree of a graph.
 *
 *   并查集由一至多个集合组成，每个集合以树表示。并查集用于处理一些不交集的合并和查询问题。
 *   并查集的每个节点需要保存其父节点的引用（如果有节点的父节点为它本身，那该节点即为树的根节点）。
 *
 *   并查集相关操作：
 *     1. find：确定该元素属于哪个子集（该元素所属树的根节点为当前集合的代表）
 *     2. union: 将两个子集合合并成一个子集合（可选择两个树的任一个根节点作为最终的根节点，即合并后的集合的代表）
 *     3. makeSet: 建立单元素集合（处理并查集时，一开始假设每个元素都在一个独立的集合中）
 *
 *   伪代码：
 *     find(x):
 *       if x.parent == x:
 *         return x
 *       else:
 *         return find(x.parent)
 *
 *     union(x, y):
 *       xRoot = find(x)
 *       yRoot = find(y)
 *       if xRoot != yRoot:
 *         yRoot.parent = xRoot
 *
 *     makeSet(x):
 *       x.parent = x
 *
 *   上述方式创建的树可能会严重不平衡，可采用下面两种方法优化；
 *   1) 按秩合并
 *     总是将更小的树连接至更大的树上，即将元素所在深度小的集合合并到元素所在深度大的集合。
 *   2) 在执行 find 时扁平化树结构，每个节点并不关心其父节点，而只关心其根节点，所以可以将每个节点的parent都直接连接到根上
 *      find方法可做如下改写：
 *        find(x):
 *          if x.parent != x:
 *            x.parent = find(x.parent)
 *          return x.parent;
 *
 *   使用以上两种方式优化后：
 *     时间复杂度：O(alpha(n))  参考<a href="https://en.wikipedia.org/wiki/Disjoint-set_data_structure#Time_complexity"></a>
 *     空间复杂度：O(n)
 */
public class DisjointSet {
    /**
     * @see leetcode.NumberOfIslands#numIslands_3(char[][])
     * @see leetcode.RegionsCutBySlashes
     */
}
