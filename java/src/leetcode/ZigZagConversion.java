package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode 6 ZigZag Conversion
 *
 *   The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to
 * display this pattern in a fixed font for better legibility)
 *
 *   P   A   H   N
 *   A P L S I I G
 *   Y   I   R
 * And then read line by line: "PAHNAPLSIIGYIR"
 *
 * Write the code that will take a string and make this conversion given a number of rows:
 *
 * string convert(string s, int numRows);
 * Example 1:
 *   Input: s = "PAYPALISHIRING", numRows = 3
 *   Output: "PAHNAPLSIIGYIR"
 * Example 2:
 *   Input: s = "PAYPALISHIRING", numRows = 4
 *   Output: "PINALSIGYAHRPI"
 * Explanation:
 *   P     I    N
 *   A   L S  I G
 *   Y A   H R
 *   P     I
 */
public class ZigZagConversion {
    /**
     * way 1 : 模拟数据格式转换过程，将String中的字符按条件放入二维数组指定位置中
     * 时间空间复杂度：O(n * r) : n - 字符串长度；r - 行数
     */
    public static String convert(String s, int numRows) {
        int len = s.length();
        if (len <= 1 || numRows <= 1 || len <= numRows) return s;
        StringBuilder builder = new StringBuilder();

        // 列序号为 0, numRows-1, 2*(numRows-1), ... 的列有numRows个数，其余列只有1个数
        int m = (int) Math.ceil((len - numRows) * 1.0 / (numRows - 2 + numRows)) * (numRows - 1) + 1;

        char[][] conversion = new char[numRows][m];
        int r = 0;
        int c = 0;
        for (int i = 0; i < len; i++) {
            conversion[r][c] = s.charAt(i);
            int[] idx = getNextIndex(r, c, numRows);
            r = idx[0];
            c = idx[1];
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < m; j++) {
                if (conversion[i][j] != '\u0000') {
                    builder.append(conversion[i][j]);
                }
            }
        }

        return builder.toString();
    }

    private static int[] getNextIndex(int r, int c, int numRows) {
        if (r == numRows - 1) return new int[]{r - 1, c + 1};

        if (c % (numRows - 1) == 0) return new int[]{r + 1, c};
        else return new int[]{r - 1, c + 1};
    }

    /**
     * way 2: Math method
     *  依次遍历每行：index是平行边所属列的列坐标，idx是该行其他字符在字符串中对应的位置
     *    1. 第0行和第numRows行： idx = index + 2*(numRows - 1)
     *    2. 0 ~ numRows 行： idx = index + 2*(numRows - 1) or idx = index - 2*i   (i - 行号)
     *  时间、空间复杂度：O(n)
     */
    public static String convert2(String s, int numRows) {
        int len = s.length();
        if (len <= 1 || numRows <= 1 || len <= numRows) return s;

        StringBuilder builder = new StringBuilder();

        int idx;
        for (int i = 0; i < numRows; i++) {
            idx = i;
            if (i == 0 || i == numRows - 1) {
                while (idx < len) {
                    builder.append(s.charAt(idx));
                    idx = idx + 2 * (numRows - 1);
                }
            } else {
                builder.append(s.charAt(idx));
                while (idx < len) {
                    idx = idx + 2 * (numRows - 1);
                    int idx1 = idx - 2 * i;
                    int idx2 = idx;
                    if (idx1 < len) builder.append(s.charAt(idx1));
                    if (idx2 < len) builder.append(s.charAt(idx2));
                }
            }
        }
        return builder.toString();
    }

    /**
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static String convert_3(String s, int numRows) {
        if (s == null || s.length() == 0) {
            return "";
        }

        numRows = Math.min(s.length(), numRows);
        if (numRows == 1) {
            return s;
        }
        List<StringBuilder> rowList = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) {
            rowList.add(new StringBuilder());
        }

        int curRow = 0;
        boolean down = true;
        for (char c : s.toCharArray()) {
            rowList.get(curRow).append(c);

            if (curRow == 0) {
                down = true;
            } else if (curRow == numRows - 1) {
                down = false;
            }
            curRow = down ? curRow + 1 : curRow - 1;
        }

        for (int i = 1; i < numRows; i++) {
            rowList.get(0).append(rowList.get(i));
        }
        return rowList.get(0).toString();
    }

    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 3));
        System.out.println(convert("PAYPALISHIRING", 4));
        System.out.println(convert("AB", 1));

        System.out.println(convert2("PAYPALISHIRING", 3));
        System.out.println(convert2("PAYPALISHIRING", 4));
        System.out.println(convert2("AB", 1));

        System.out.println(convert_3("PAYPALISHIRING", 3));
        System.out.println(convert_3("PAYPALISHIRING", 4));
        System.out.println(convert_3("AB", 1));
    }
}
