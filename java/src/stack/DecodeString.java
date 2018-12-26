package stack;

/**
 * 问题描述: leetcode 880
 *
 * 注：leetcode将问题分类到stack，这里的处理方式没有用到栈
 *
 * An encoded string S is given.  To find and write the decoded string to a tape, the encoded string is read one character at a time and the following steps are taken:
 *
 * If the character read is a letter, that letter is written onto the tape.
 * If the character read is a digit (say d), the entire current tape is repeatedly written d-1 more times in total.
 * Now for some encoded string S, and an index K, find and return the K-th letter (1 indexed) in the decoded string.
 *
 *
 * Input: S = "leet2code3", K = 10
 * Output: "o"
 * Explanation:
 * The decoded string is "leetleetcodeleetleetcodeleetleetcode".
 * The 10th letter in the string is "o".
 *
 *
 * Input: S = "ha22", K = 5
 * Output: "h"
 * Explanation:
 * The decoded string is "hahahaha".  The 5th letter is "h".
 *
 * Input: S = "a2345678999999999999999", K = 1
 * Output: "a"
 * Explanation:
 * The decoded string is "a" repeated 8301530446056247680 times.  The 1st letter is "a".
 *
 *
 * Note:
 * 1.2 <= S.length <= 100
 * 2.S will only contain lowercase letters and digits 2 through 9.
 * 3.S starts with a letter.
 * 4.1 <= K <= 10^9
 * 5.The decoded string is guaranteed to have less than 2^63 letters.
 */
public class DecodeString {

    // 模拟过程
    // Memory Limit Exceeded
    public static String decodeAtIndex1(String s, int k){
        StringBuilder builder = new StringBuilder(k);  // k 保证为 int 范围

        for(int i = 0; i < s.length(); i ++){
            if(builder.length() >= k) break;

            char ch = s.charAt(i);
            if( ch <= 122 && ch >= 97){  // 小写字母
                builder.append(ch);
            }

            if( ch <= 57 && ch >= 50){  // 2 ~ 9
                int value = ch - '0';

                String old = builder.toString();
                for(int loop = 1; loop < value; loop ++){
                    if(builder.length() >= k) break;

                    builder.append(old);
                }
            }
        }

        return String.valueOf(builder.charAt(k - 1));  // char to string
    }

    public static String decodeAtIndex(String S, int K){
        int len = S.length();

        long decodeLen = 0;

        for(int i = 0; i < len; i ++){
            if(Character.isDigit(S.charAt(i))){
                decodeLen *= (S.charAt(i) - '0');
            }else{
                decodeLen ++;
            }
        }

        for(int i = len - 1; i >= 0; i --){
            if(Character.isDigit(S.charAt(i))){
                decodeLen /= (S.charAt(i) - '0');
                continue;
            }

            if(K > decodeLen){
                K = (int)(K % decodeLen);
            }
            if(K == decodeLen || K == 0)
                return String.valueOf(S.charAt(i));

            decodeLen --;
        }

        return null;

    }
}
