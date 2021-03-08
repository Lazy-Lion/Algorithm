package leetcode;

/**
 * leetcode 8
 */
public class StringToInteger {
    private static final char SPACE = ' ';
    private static final char BASE_DIGITAL = '0';

    /**
     * way 1
     */
    public static int myAtoi(String s) {
        int result = 0;

        char prev = SPACE;
        boolean negative = false;
        for (char ch : s.toCharArray()) {
            if (ch == SPACE) {
                if (prev == SPACE) {
                    continue;
                } else {
                    return result;
                }
            }
            if (ch == '+') {
                if (prev == SPACE) {
                    prev = ch;
                    continue;
                } else {
                    return result;
                }
            } else if (ch == '-') {
                if (prev == SPACE) {
                    negative = true;
                    prev = ch;
                    continue;
                } else {
                    return result;
                }
            }
            prev = ch;
            if (isDigital(ch)) {
                result = calculate(result, negative ? BASE_DIGITAL - ch : ch - BASE_DIGITAL);
                if (result == Integer.MAX_VALUE || result == Integer.MIN_VALUE) {
                    return result;
                }
            } else {
                return result;
            }
        }
        return result;
    }

    private static boolean isDigital(char ch) {
        return ch - BASE_DIGITAL >= 0 && ch - BASE_DIGITAL <= 9;
    }

    private static int calculate(int val, int plus) {
        if (Integer.MAX_VALUE / 10 < val || (Integer.MAX_VALUE / 10 == val && plus > 7)) {
            return Integer.MAX_VALUE;
        }
        if (Integer.MIN_VALUE / 10 > val || (Integer.MIN_VALUE / 10 == val && plus <= -8)) {
            return Integer.MIN_VALUE;
        }
        return val * 10 + plus;
    }

    /**
     * way 2
     * DFA: Deterministic Finite Automaton,确定有限自动机
     *
     *  状态集: start, signed, number, end
     *                        (数字)
     *      (空格)   ↗  -----------------------↘    (数字)
     *      ↙ ↖ ↗  (+,-)               (数字)   ↘↙↖
     *      start --------->  signed -----------> number
     *           ↘(非数字)     ↓ (非数字)        ↙ (非数字)
     *                        end
     *
     * 转移方式： if start: (空格) → start
     *                     (+,-) → signed
     *                     (数字) → number
     *                     (其他) → end
     *           if signed: (数字) → number
     *                      (非数字) → end
     *           if number: (数字) → number
     *                      (非数字) → end
     */

    private static final String[] STATUS = new String[]{"START", "SIGNED", "NUMBER", "END"};

    public static int myAtoi_2(String s) {
        long ans = 0;
        String status = STATUS[0];
        int sign = 1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (isDigital(ch)) {
                status = STATUS[2];
                ans = ans * 10 + sign * (ch - '0');
                if (ans > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                } else if (ans < Integer.MIN_VALUE) {
                    return Integer.MIN_VALUE;
                }
            } else if (status.equals(STATUS[0])) {
                if (ch == ' ') {
                    continue;
                } else if (ch == '+' || ch == '-') {
                    sign = (ch == '-') ? -1 : 1;
                    status = STATUS[1];
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        return (int) ans;
    }


    public static void main(String[] args) {
        System.out.println(myAtoi("-91283472332"));
        System.out.println(myAtoi("words and 987"));
        System.out.println(myAtoi("4193 with words"));
        System.out.println(myAtoi("-42"));
        System.out.println(myAtoi("42"));
        System.out.println(myAtoi("3.14159"));
        System.out.println("-------------");
        System.out.println(myAtoi_2("-91283472332"));
        System.out.println(myAtoi_2("words and 987"));
        System.out.println(myAtoi_2("4193 with words"));
        System.out.println(myAtoi_2("-42"));
        System.out.println(myAtoi_2("42"));
        System.out.println(myAtoi_2("3.14159"));
    }
}
