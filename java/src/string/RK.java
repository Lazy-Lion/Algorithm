package string;
/**
 * Rabin-Karp 算法, 由两位发明者名字命名。
 * 思路： 引入哈希算法
 *     1. 计算模式串的 hash 值
 *     2. 计算主串中长度为 m 的子串的 hash 值 ( n-m+1次 )
 *     3. 比较 hash 值，如果不同则不匹配， 如果相同则比较该子串和模式串本身 (存在哈希冲突)
 *  哈希函数：hash[s+1,s+m]可以依赖于 hash[s,s+m-1]计算，而不需要依赖于所有字符
 *    滚动哈希(rolling hash):
 *    Rabin-Karp的滚动哈希： hash = c1*a^(m-1) + c2*a^(m-2) + ... + cm*a^0
 *       - a 是常量，numbers of characters in the alphabet
 *       - c1,...,ck 是字符
 *       - m 是模式串和子串长度
 *
 *     hash(i)   = hash(c1 .. cm)     = c1*a^(m-1) + c2*a^(m-2) + ... + cm*a^0
 *     hash(i+1) = hash(c2 .. c(m+1)) =              c2*a^(m-1) + ... + cm*a^1 + c(m+1)*a^0
 *     a*hash(i) =                      c1*a^m     + c2*a^(m-1) + ... + cm*a^1
 *     hash(i+1) = a*(hash(i) - c1*a^(m-1)) + c(m+1)
 *
 * 时间复杂度：
 *    预处理：O(m) - step 1
 *    匹配： 最好情况O(n), 如果哈希冲突较多会退化成 O(n*m)  - step 2,3
 */
public class RK {
    private static final int DEFAULT_NUMBER = 26;
    private int alphNumber;   //numbers of characters in the alphabet
    private int pow;   // a^(m-1)

    public RK(){
        this.alphNumber = DEFAULT_NUMBER;
    }

    public RK(int charNumber){
        this.alphNumber = charNumber;
    }

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

        this.pow = (int)Math.pow(alphNumber, m-1);

        int pHash = hash(pattern, 0, m);
        int tHash = 0;
        for(int i = 0; i < n - m + 1; i ++){
            if(i == 0)
                tHash = hash(text, 0, m);
            else
                tHash = hash(text, tHash, i - 1, m);

            if(tHash == pHash && validate(text, pattern, i, m))
                return i;
        }
        return -1;
    }


    private int hash(String text, int start, int m){
        assert m > 0;
        assert text.length() >= start + m;

        int hash = 0;
        for(int i = start; i < start + m - 1; i ++){
            hash = (hash + (text.charAt(i) - 'a')) * alphNumber;
        }
        hash += (text.charAt(start + m - 1) - 'a');
        return hash;
    }

    private int hash(String text, int oldHash, int oldStart, int m){
        assert m > 0;
        assert text.length() >= oldStart + m;
        int hash;
        hash = this.alphNumber * (oldHash - (text.charAt(oldStart) - 'a')*this.pow)
                + ( text.charAt(oldStart + m) - 'a');
        return hash;

    }

    private boolean validate(String text, String pattern, int start, int m){
        assert  text.length() >= start + m;

        for(int i = 0; i < m; i ++){
            if(text.charAt(start + i) != pattern.charAt(i))
                return false;
        }
        return true;
    }
}
