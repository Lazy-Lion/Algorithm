package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode 22
 */
public class GenerateParentheses {

    private static final char LEFT_BRACKET = '(';
    private static final char RIGHT_BRACKET = ')';

    private static List<String> result;

    public static List<String> generateParenthesis(int n) {
        result = new ArrayList<>();
        generateParenthesis(n, 0, 0, new StringBuilder());
        return result;
    }

    private static void generateParenthesis(int max, int leftCount, int rightCount, StringBuilder builder) {
        if (leftCount == rightCount && leftCount == max) {
            result.add(builder.toString());
            return;
        }

        // select left bracket
        if (leftCount < max) {
            builder.append(LEFT_BRACKET);
            generateParenthesis(max, leftCount + 1, rightCount, builder);
            builder.deleteCharAt(builder.length() - 1);
        }
        if (rightCount < leftCount) {
            builder.append(RIGHT_BRACKET);
            generateParenthesis(max, leftCount, rightCount + 1, builder);
            builder.deleteCharAt(builder.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
        System.out.println(generateParenthesis(1));
    }
}
