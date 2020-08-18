package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * leetcode 96: Unique Binary Search Trees
 *
 * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
 *
 * Example:
 *   Input: 3
 *   Output: 5
 *   Explanation:
 *     Given n = 3, there are a total of 5 unique BST's:
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 *
 * Constraints:
 *   1 <= n <= 19
 */
public class UniqueBinarySearchTrees {
    private static Map<String, Integer> map = new HashMap<>(); // for pruning

    /**
     * recursion + prune
     *
     * 固定一个数为根节点，则以此数为根节点的binary tree个数为： 左子树个数 * 右子树个数
     * 递归
     *   递推公式：count(start,end) = sum(count(start, i-1) * count(i + 1, end))  → i from 1 to n
     *   终止条件：count(start, end) = 1  if start >= end
     *
     *   time complexity: O(n^2)
     *   space complexity: O(n)
     */
    public static int numTrees(int n) {
        return countTrees(1, n);
    }

    private static int countTrees(int start, int end) {
        if (start >= end) {
            return 1;
        }

        int sum = 0;
        for (int i = start; i <= end; i++) {
            int temp1, temp2;
            String key1 = start + "," + (i - 1);
            String key2 = (i + 1) + "," + end;
            temp1 = map.containsKey(key1) ? map.get(key1) : countTrees(start, i - 1);
            temp2 = map.containsKey(key2) ? map.get(key2) : countTrees(i + 1, end);

            sum = sum + temp1 * temp2;
        }
        map.put(start + "," + end, sum);
        return sum;
    }

    /**
     * {@link #numTrees(int)}， count() 只与 start和 end之间包含几个数有关，而与其具体值无关
     * => count(0) = 1;
     *    count(1) = 1;
     *    count(2) = count(0) * count(1) + count(1) * count(0)
     *    count(3) = count(0) * count(2) + count(1) * count(1) + count(2) * count(0)
     * => count(n) = sum(count(i - 1) * count(n - i))   i from 1 to n
     */
    private static final Map<Integer, Integer> map2 = new HashMap<>();

    {
        map2.put(0, 1);
        map2.put(1, 1);
    }

    public static int numTrees_2(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        int sum = 0;
        for (int i = 1; i <= n; i++) {
            int temp1 = map2.containsKey(i - 1) ? map2.get(i - 1) : numTrees_2(i - 1);
            int temp2 = map2.containsKey(n - i) ? map2.get(n - i) : numTrees_2(n - i);

            sum += temp1 * temp2;
        }
        map2.put(n, sum);
        return sum;
    }

    /**
     * iteration {@link #numTrees_2(int)}
     */
    public static int numTrees_3(int n) {
        int[] count = new int[n + 1];
        count[0] = 1;
        count[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                count[i] += count[j - 1] * count[i - j];
            }
        }
        return count[n];
    }

    /**
     * 数学方法: {@link #numTrees_2(int)} 分析的递推式
     *   卡塔兰数(Catalan number)：
     *     C(0) = 1, C(n+1) = (2*(2*n + 1) / (n+2)) * C(n)
     */
    public static int numTrees_4(int n) {
        long count = 1; // 防止计算逸出
        for (int i = 0; i < n; i++) {
            count = count * 2 * (2 * i + 1) / (i + 2); // 先计算乘法，再计算除数，防止小数舍去引起的结果错误
        }
        return (int) count;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Utils.testStaticMethod(UniqueBinarySearchTrees.class
                , new HashSet<String>() {
                    {
                        add("numTrees");
                        add("numTrees_2");
                        add("numTrees_3");
                        add("numTrees_4");
                    }
                }
                , Arrays.asList(
                        Arrays.asList(3)
                ));
    }
}
