package string;

/**
 * Brute Force Algorithm (暴力匹配算法，也称朴素匹配算法 (Naive String Searching Algorithm))
 * 思路：模式串相当于一个滑动窗口，在主串上以 1 的速度滑动，每次滑动之后将模式串与主串对应位置字符比较
 *      => 滑动次数为 n - m + 1 次，每次滑动需要比较 m 次
 *
 * 不需要预处理，匹配的时间复杂度 O(n*m)
 */
public class BF {
    /**
     * @param text 主串
     * @param pattern 模式串
     * @return -1 if no match, or index of the first matching char
     */
    public int match(String text, String pattern){
        int n = text.length();
        int m = pattern.length();

        if(n < 0 || m < 0 || n < m) return -1;
        if(m == 0) return 0;

        for(int i = 0; i < n - m + 1; i ++){
            int j = 0;
            for(; j < m; j ++){
                if(text.charAt(i + j) != pattern.charAt(j))
                    break;
            }
            if(j == m)  // succeed
                return i;
        }
        return -1;
    }
}
