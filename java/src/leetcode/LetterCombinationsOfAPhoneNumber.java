package leetcode;

import java.util.*;

/**
 * leetcode 17
 */
public class LetterCombinationsOfAPhoneNumber {
    private static final Map<Character, List<Character>> map = new HashMap<Character, List<Character>>() {
        {
            put('2', Arrays.asList('a', 'b', 'c'));
            put('3', Arrays.asList('d', 'e', 'f'));
            put('4', Arrays.asList('g', 'h', 'i'));
            put('5', Arrays.asList('j', 'k', 'l'));
            put('6', Arrays.asList('m', 'n', 'o'));
            put('7', Arrays.asList('p', 'q', 'r', 's'));
            put('8', Arrays.asList('t', 'u', 'v'));
            put('9', Arrays.asList('w', 'x', 'y', 'z'));
        }
    };

    private static List<String> result;
    public static List<String> letterCombinations(String digits) {
        result = new ArrayList<>();
        if (digits.length() == 0) {
            return result;
        }
        backtrack(digits.toCharArray(), 0, new StringBuilder());
        return result;
    }

    private static void backtrack(char[] digits, int index, StringBuilder stringBuilder) {
        if (index >= digits.length) {
            result.add(stringBuilder.toString());
            return;
        }

        for (char ch : map.get(digits[index])) {
            stringBuilder.append(ch);
            backtrack(digits, index + 1, stringBuilder);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));
        System.out.println(letterCombinations(""));
        System.out.println(letterCombinations("2"));
    }
}
