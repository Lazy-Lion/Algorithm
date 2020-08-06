package leetcode;

import util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * leetcode 682: Baseball Game
 *
 * You're now a baseball game point recorder.
 * Given a list of strings, each string can be one of the 4 following types:
 *   1. Integer (one round's score): Directly represents the number of points you get in this round.
 *   2. "+" (one round's score): Represents that the points you get in this round are the sum of the last two valid round's points.
 *   3. "D" (one round's score): Represents that the points you get in this round are the doubled data of the last valid round's points.
 *   4. "C" (an operation, which isn't a round's score): Represents the last valid round's points you get were invalid and should be removed.
 *
 * Each round's operation is permanent and could have an impact on the round before and the round after.
 * You need to return the sum of the points you could get in all the rounds.
 *
 * Example 1:
 *   Input: ["5","2","C","D","+"]
 *   Output: 30
 *   Explanation:
 *     Round 1: You could get 5 points. The sum is: 5.
 *     Round 2: You could get 2 points. The sum is: 7.
 *     Operation 1: The round 2's data was invalid. The sum is: 5.
 *     Round 3: You could get 10 points (the round 2's data has been removed). The sum is: 15.
 *     Round 4: You could get 5 + 10 = 15 points. The sum is: 30.
 *
 * Example 2:
 *   Input: ["5","-2","4","C","D","9","+","+"]
 *   Output: 27
 *   Explanation:
 *     Round 1: You could get 5 points. The sum is: 5.
 *     Round 2: You could get -2 points. The sum is: 3.
 *     Round 3: You could get 4 points. The sum is: 7.
 *     Operation 1: The round 3's data is invalid. The sum is: 3.
 */
public class BaseballGame {
    private static final String CANCEL = "C";
    private static final String DOUBLE = "D";
    private static final String PLUS = "+";

    /**
     * stack
     * time complexity: O(n)
     * space complexity: O(n)
     */
    public static int calPoints(String[] ops) {
        int length = ops.length;

        if (length == 0) return 0;
        Stack<Integer> stack = new Stack<>();

        int sum = 0;
        int val;
        for (int i = 0; i < ops.length; i++) {
            if (CANCEL.equals(ops[i])) {
                if (stack.isEmpty()) {
                    return 0; // invalid input
                }
                sum -= stack.pop();
            } else if (DOUBLE.equals(ops[i])) {
                if (stack.isEmpty()) {
                    return 0; // invalid input
                }
                val = stack.peek() << 1;
                sum += val;
                stack.push(val);
            } else if (PLUS.equals(ops[i])) {
                if (stack.isEmpty() || stack.size() <= 1) {
                    return 0; // invalid input
                }
                int top = stack.pop();
                val = top + stack.peek();
                stack.push(top);
                stack.push(val);
                sum += val;
            } else {
                val = Integer.valueOf(ops[i]);
                stack.push(val);
                sum += val;
            }
        }

        return sum;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<List<Object>> params = new ArrayList<>();

        List<Object> param = new ArrayList<>();
        param.add(new String[]{"5", "2", "C", "D", "+"});
        params.add(param);

        param = new ArrayList<>();
        param.add(new String[]{"5", "-2", "4", "C", "D", "9", "+", "+"});
        params.add(param);

        Utils.testStaticMethod(BaseballGame.class, params);
    }
}
