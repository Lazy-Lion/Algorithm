package string;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * AC自动机算法：Aho–Corasick算法是由 Alfred V. Aho和 Margaret J.Corasick 发明的字符串搜索算法。
 *
 * BF、RK、BM、KMP算法都是单模式串匹配算法，AC自动机算法是多模式串匹配算法。
 *
 * AC自动机基于 Trie树:
 *   1.使用模式串集合构造 Trie树;
 *   2.在 Trie树上构建失败指针 (相当于 KMP中的失效函数，利用失败指针跳过已匹配过的部分，以便快速匹配)
 *
 *      pattern: {abce, abcde, bcd, ce} 构造如下 Trie树：为了方便，将节点用实际字符数据值表示，实际的实现中节点不存储字符数据
 *                               root
 *                          ↙    ↓    ↘
 *                       a        b       c
 *                    ↙          ↓        ↘
 *                  b             c(q)       e
 *                ↙              ↓
 *              c(p)             d(qc)
 *           ↙   ↘
 *         e(pc1)   d(pc2)
 *                   ↘
 *                     e
 *
 *      text: abcabcde
 *
 *      AC思路：
 *        1.从root节点开始匹配text, 到节点pc时不匹配，此时应该回到root（Trie 前缀树的特性，每次匹配会匹配所有以text的第一个字符开头的模式串）,
 *      text从第二个字符b开始匹配，到pc时再次不匹配，继续回到root，text从第三个字符开始匹配，如此操作下去。
 *        2. 从1中的执行过程中发现，到节点p的子节点不匹配时，"abc"都是匹配的，"abc"存在最长后缀 "bc" 在Trie树上，
 *      记录下来（失败指针） p.fail = q。那么对于模式串 "bcd"的 "bc"前缀已匹配完成，无需重复匹配，只需继续匹配下一个字符即可。
 *        3. 根节点不存在失败指针 root.fail = null
 *        4. 根节点的子节点对应的字符串只有一个字符，没有后缀 node.fail = root
 *        5. 失败指针的计算： 根据前一个的失败指针(最长后缀匹配)，计算当前失败指针
 *           求节点 pc的失败指针，假设其父节点 p的失败指针为 q
 *          1) 如果 q == null, pc.fail = root, 否则执行 2)
 *          2) 如果 q的子节点中存在 qc, 使得 qc和 pc字符相同，则 pc.fail = qc，否则执行 3)
 *          3) q = q.fail 执行 1)
 *         p的失败指针 q所在树的层数必然小于 p，所以可以用层次遍历的方式依次计算。
 *        6. AC的匹配过程：
 *          questions:
 *            1)如果仅仅使用 Trie树记录 fail指针，匹配完成之后回到root, text从下一个字符再开始匹配，其实之前使用 fail指针匹配的，之后
 *          会重复再操作一次，效率并不会提高（会较低。较之前的操作，多了fail指针的相关操作）。
 *            2) Trie 树当前节点为 current (初始 current = root)
 *               i.从文本串读取下一个字符 ch (从第一个字符开始)
 *               ii.current.children匹配 ch,
 *                  匹配失败时使用fail指针向上回溯，从树上找其后缀字符串对应的模式串
 *                  匹配成功，current设为当前子节点，执行 i
 *
 *               这样会遗漏部分数据，如下图示例1所示，匹配结果只会有 abc,而 b, bc, c也符合条件
 *                 示例1：
 *                 pattern: {abc, b, bc, c}
 *                 text: abc
 *
 *                 ----(节点后end表示该字符是结尾字符，叶子节点都是结尾字符)
 *                               root
 *                          ↙    ↓     ↘
 *                       a        b(end)  c
 *                    ↙          ↓
 *                  b             c
 *                ↙
 *              c
 *
 *              示例2：
 *                 pattern: {abd, b, bc, c}
 *                 text: abc
 *
 *                 ----(节点后end表示该字符是结尾字符，叶子节点都是结尾字符)
 *                               root
 *                          ↙    ↓     ↘
 *                       a        b(end)  c
 *                    ↙          ↓
 *                  b             c
 *                ↙
 *              d
 *
 *              => 完整匹配过程：（无论匹配是否成功都需要通过 fail回溯）
 *                 Trie 树当前节点为 current, 初始 current = root
 *                 i.从文本串读取下一个字符 ch (从第一个字符开始)
 *                 ii.current.children匹配 ch：
 *                     ` 匹配成功：current更新成匹配成功的子节点
 *                             判断current的 isEnd是否为 true, 判断 current.fail 的 isEnd是否 true, 判断 current.fail.fail
 *                         的isEnd 是否为 true (直到 current.fail.fail.fail... 为 root, 目的是为了不遗漏当前字符串的所有
 *                         后缀子串), 若是则可添加到结果集合。
 *                             重复执行 i
 *                     ` 匹配失败：
 *                        `` if current.fail == null, 说明当前字符串的任一后缀都不存在一个模式串的前缀与之匹配（此时应从下一个字符开始重新匹配）
 *                           , current = root, 执行 i
 *                        `` if current.fail != null, current = current.fail，执行 ii
 */
public class AC {
    private static final int R = 256; // extended ascii

    private ACNode root;  // the root of Trie

    /**
     * 构造方法
     * @param pattern 模式串数组
     */
    public AC(String[] pattern) {
        root = new ACNode();
        buildTrie(pattern);
        buildFailurePointer();
    }

    /**
     * @param text 主串
     * @return List<String>: String is two-tuples like (a,b), a refer to the first index of matching substring,
     *                       b refer to the length of matching substring
     */
    public List<String> match(String text) {
        int n = text.length();
        ACNode p = root;

        List<String> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            char c = text.charAt(i);

            if (p.children[(int) c] == null && p != root) {
                p = p.fail;
            }

            p = p.children[(int) c];
            if (p == null) p = root; // 说明当前字符串的任一后缀都不存在一个模式串的前缀与之匹配
            ACNode temp = p;
            while (temp != root) {
                if (temp.isEnd) {
                    int pos = i - temp.length + 1;
                    result.add("(" + pos + "," + temp.length + ")");
                }
                temp = temp.fail;
            }
        }
        return result;
    }

    /**
     * 使用模式串构造 Trie树
     */
    private void buildTrie(String[] pattern) {
        for (int i = 0; i < pattern.length; i++) {
            String str = pattern[i];
            appendString(str.toCharArray());
        }
    }

    private void appendString(char[] str) {
        int n;
        if (str == null || (n = str.length) <= 0) return;

        ACNode p = root;
        for (int i = 0; i < n; i++) {
            int c = (int) str[i];

            ACNode node = p.children[c];
            if (node == null) {
                node = new ACNode();
                p.children[c] = node;
            }

            if (i == n - 1) {
                node.isEnd = true;
                node.length = n;
            }
            p = node;
        }
    }

    private void buildFailurePointer() {
        Queue<ACNode> queue = new LinkedList<>();
        /**
         * root的失败指针指向 null, p.fail == null作为终止条件 （也可将root指针指向它自身，将 p.fail == root 作为终止条件）
         */
        queue.add(root);

        while (!queue.isEmpty()) {
            ACNode p = queue.poll();
            for (int i = 0; i < R; i++) {
                ACNode pc = p.children[i];
                if (pc == null) continue;
                if (p == root) {
                    pc.fail = root;  // 第一层字符串长度为1，没有后缀，失败指针设为 root
                } else {
                    ACNode q = p.fail;
                    while (q != null) {
                        ACNode qc = q.children[i];
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null)
                        pc.fail = root;
                }
                queue.add(pc);
            }
        }
    }


    private static class ACNode {
        private ACNode[] children = new ACNode[R]; // extended ascii
        private boolean isEnd = false;  // 是否为结尾字符
        private int length = -1;        // 当isEnd = true时，记录字符串长度
        private ACNode fail;            // 失败指针
    }
}
