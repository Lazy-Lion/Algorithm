package string;

import java.util.Arrays;

/**
 * Sunday 算法：比 BM更快的算法
 *   keys:
 *     1. 从左向右比较模式串与主串的对应字符
 *     2. 模式串在主串上从左向右滑动
 *
 *   思路：对 BF进行优化，当遇到不匹配的字符时，关注主串中参加匹配的最后一个字符的下一个字符 c
 *     1). 如果模式串中不存在字符 c, 则模式串向右移动位数 = 模式串长度 + 1
 *     2). 如果模式串中存在字符 c, 则模式串向右移动位数 = 模式串中等于 c的最右字符位置到模式串尾部的距离 + 1
 *
 *   示例：
 *   text:       a b c d h a c f h j a b
 *   pattern:    h j a
 *
 *  steps:
 *    1. a和 h不匹配：考察字符 d不在 pattern中，pattern后移 m + 1
 *       text:       a b c d h a c f h j a b
 *       pattern:            h j a
 *    2. a和 j不匹配：考察字符 f不在 pattern中，pattern后移 m + 1
 *       text:       a b c d h a c f h j a b
 *       pattern:                    h j a
 *    3. 找到匹配
 *
 *  time complexity: 比较次数优于 BM算法 ({@link BM#match(String, String)})
 *  space complexity: O(K) - K: 字符集个数
 */
public class Sunday {

    public int match(String text, String pattern) {
        int n, m;

        if (text == null || pattern == null) return (text == pattern ? 0 : -1);

        if ((n = text.length()) < (m = pattern.length())) return -1;
        if (m == 0) return 0;
        if (n == 0)  return (m == n ? 0 : -1);

        int[] map = getMap(pattern, m);
        int k = 0; // move distance
        while (k < n - m + 1) {
            int i = k;
            int j = 0;
            for (; j < m; i++, j++) {
                if (text.charAt(i) != pattern.charAt(j)) {
                    if (i + (m - j) < n) {
                        char c = text.charAt(i + (m - j));
                        if (map[c] == -1) {
                            k += (m + 1);
                        } else {
                            k += (m - map[c]);
                        }
                        break; // next match
                    } else {
                        return -1;
                    }
                }
            }

            if (j == m) return i - m; // match successful
        }

        return -1;
    }

    /**
     * {@link #match(String, String)} 换一种写法
     */
    public int match_2(String text, String pattern) {
        int n, m;

        if (text == null || pattern == null) return (text == pattern ? 0 : -1);

        if ((n = text.length()) < (m = pattern.length())) return -1;
        if (m == 0) return 0;
        if (n == 0) return (m == n ? 0 : -1);

        int[] map = getMap(pattern, m);
        int i = 0;
        while (i < n - m + 1) {
            int j = 0;

            for (; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    int index = i + m;

                    if (index >= n) return -1;
                    char c = text.charAt(index);
                    if (map[c] == -1) {
                        i += (m + 1);
                    } else {
                        i += (m - map[c]);
                    }
                    break; // next match
                }
            }

            if (j == m) return i; // match successful
        }

        return -1;
    }

    /**
     * assume: 只存在扩展ascii的字符
     * map[48]表示: ascii值为48的字符('0'),在模式串中的index (多个，选最右), 若不存在则为 -1
     */
    private int[] getMap(String pattern, int m) {
        int[] map = new int[256];

        Arrays.fill(map, -1);

        for (int i = 0; i < m; i++) {
            map[pattern.charAt(i)] = i;
        }
        return map;
    }
}
