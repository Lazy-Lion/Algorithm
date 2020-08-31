package string;

/**
 * Knuth Morris Pratt算法 (KMP, 由三位作者的名字命名):
 *  keys:
 *    1.不同于Boyer-Moore算法从右向左比较字符，KMP从左向右比较
 *    2.坏字符：同 BM算法
 *    3.好前缀
 *    4.好前缀的后缀子串
 *
 *  思路：模式串在主串上从左向右滑动，KMP算法类似 BM算法，也是优化滑动距离
 *    1.从左向右比较时，当遇到坏字符时，也可以确定好前缀 prefix (或者没有好前缀，直接向右滑动一位)，否则执行 step 2
 *    2.如果存在 prefix的前缀子串和 prefix的后缀子串匹配(取最长)，则滑动 prefix的前缀子串到 prefix后缀子串的位置，否则
 *      滑动 l (prefix的长度)
 *    3.失效函数 {@link #getNext(String, int)}
 *
 *  综上所述：
 *    前缀子串和后缀子串对应只与模式串有关，可以预处理
 *
 *  time complexity:
 *    预处理：O(m)
 *    匹配：O(n)
 *    => O(m + n)
 *  space complexity: O(m)
 */
public class KMP {
    public int match(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        if (n < 0 || m < 0 || n < m) return -1;
        if (m == 0) return 0;

        int[] next = getNext(pattern, m);

        /**
         * matching 实现：不回溯主串下标i，只改变模式串下标j
         *
         * 示例：
         * text:     a b a d e f a b c
         * pattern:  a b a e f
         *
         *   此时坏字符对应的text，pattern下标分别为 i = 3，j = 3
         *   next[2] = 0, 需要移动 pattern: k = j - (next[2] + 1) = 2
         *   a b a d e f a b c
         *       a b a e f
         *   移动之后 j' = j - k = next[2] + 1 = 1, i' = i
         */
        int j = 0;
        for (int i = 0; i < n; i++) {
            /**
             * 类似 {@link #getNext(String, int)} 中 while的时间分析
             * 执行  j = next[j - 1] + 1 是为了向右移动，j 肯定减小
             * => 执行总次数不超过 n
             */
            while (j > 0 && pattern.charAt(j) != text.charAt(i)) { // move right
                j = next[j - 1] + 1;
            }
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
            }
            if (j == m) { // 匹配
                return i - m + 1;
            }
        }
        return -1;
    }

    public int match2(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        if (n < 0 || m < 0 || n < m) return -1;
        if (m == 0) return 0;

        int[] next = getNext(pattern, m);

        // matching 实现：类似Boyer-Moore算法，滑动窗口方式，相较于match(text,pattern)存在多余的比较
        for (int i = 0; i < n - m + 1; ) {
            int j = 0;
            for (; j < m; j++) {
                if (pattern.charAt(j) != text.charAt(i + j))
                    break;
            }
            if (j == m) return i; // 匹配成功

            if (j == 0) {  // 没有好前缀
                i++;
                continue;
            }
            int v = next[j - 1];
            if (v == -1) {
                i += j;
            } else {
                i += (j - v);
            }
        }
        return -1;
    }

    /**
     * 失效函数
     * @param pattern 模式串
     * @param m 模式串长度
     * @return int[] next, 描述好前缀中后缀子串和模式串前缀子串之间的关系
     *
     *    模式串 p
     *    next[i]：模式串的前缀子串 p[0,i]（prefix, 前缀都有可能是好前缀）, prefix的前缀子串中最长可匹配prefix的后缀子串的结尾字符下标;
     *    next[0] = -1 , p[0,0]没有后缀子串
     *
     *     例 模式串 a b a b a c d
     *    前缀（好前缀候选）|   前缀结尾下标   |  后缀字符串最长匹配的好前缀结尾下标 |  next
     *    a                      0                   -1 (not exist)              next[0] = -1
     *    ab                     1                   -1                          next[1] = -1
     *    aba                    2                   0                           next[2] = 0
     *    abab                   3                   1                           next[3] = 1
     *    ababa                  4                   2                           next[4] = 2
     *    ababac                 5                   -1                          next[5] = -1
     *
     *    next[i]可通过 next[i - 1]计算得到：
     *      假设 k = next[i] => p[0,k]是p[0,i]的最长可匹配最长子串
     *      1. if p[i + 1] == p[k + 1]: next[i + 1] = next[i] + 1
     *      2. if p[i + 1] != p[k + 1]: 计算 p[0,i]的后缀字符串次长匹配的前缀结尾下标 u, 如果 p[i + 1] == p[u + 1]，则
     *         next[i + 1] = u + 1, 否则再计算 p[0,i]的再次短的前缀结尾下标 u'，直到 p[i + 1] == p[u' + 1], 或 u' = -1 (表示不存在)
     *      3. 对于上述步骤2，求次长匹配的前缀结尾下标 u
     *
     *        next[i] = k => p[0, k] == p[i-k ,i]
     *        p[0,i]的次长匹配前缀(p[0,u])肯定是其最长匹配前缀(p[0,k])的前缀子串, 且p[0,u] = p[i-u,i]
     *        而 p[i-u,i] = p[k-u,k] => p[0,u] = p[k-u,k] （可作图理解）
     *        => 可以转换成前缀 p[0, k]的最长匹配，即 next[k]
     */
    private int[] getNext(String pattern, int m) {
        int[] next = new int[m - 1];
        next[0] = -1;

        int k = -1;
        for (int i = 1; i < m - 1; i++) {
            /**
             * k < i < m 始终成立
             * 每次for循环，i++，但 k可能不会增加
             *
             * 执行 k = next[k], k 会减小（最少减 1），减小到-1之后将不会执行，直到后续 k又继续增加
             * 执行所有for循环之后，即使不考虑 k = next[k]， k 最大值也不会达到 m
             * => k = next[k] 执行总次数小于 m次，否则 k 会小于 -1，不满足条件
             */
            while (k != -1 && pattern.charAt(k + 1) != pattern.charAt(i)) {
                k = next[k];
            }
            if (pattern.charAt(k + 1) == pattern.charAt(i)) {
                k++;
            }

            next[i] = k;
        }
        return next;
    }
}
