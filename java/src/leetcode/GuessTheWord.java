package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * leetcode 843: Guess the Word
 *
 *   This problem is an interactive problem new to the LeetCode platform.
 *   We are given a word list of unique words, each word is 6 letters long, and one word in this list is chosen
 * as secret.
 *   You may call master.guess(word) to guess a word.  The guessed word should have type string and must be from the
 * original list with 6 lowercase letters.
 *   This function returns an integer type, representing the number of exact matches (value and position) of your guess
 * to the secret word.  Also, if your guess is not in the given wordlist, it will return -1 instead.
 *   For each test case, you have 10 guesses to guess the word. At the end of any number of calls, if you have made 10
 * or less calls to master.guess and at least one of these guesses was the secret, you pass the testcase.
 *   Besides the example test case below, there will be 5 additional test cases, each with 100 words in the word list.
 * The letters of each word in those testcases were chosen independently at random from 'a' to 'z', such that every
 * word in the given word lists is unique.
 *
 * Example 1:
 *   Input: secret = "acckzz", wordlist = ["acckzz","ccbazz","eiowzz","abcczz"]
 * Explanation:
 *   master.guess("aaaaaa") returns -1, because "aaaaaa" is not in wordlist.
 *   master.guess("acckzz") returns 6, because "acckzz" is secret and has all 6 matches.
 *   master.guess("ccbazz") returns 3, because "ccbazz" has 3 matches.
 *   master.guess("eiowzz") returns 2, because "eiowzz" has 2 matches.
 *   master.guess("abcczz") returns 4, because "abcczz" has 4 matches.
 *  We made 5 calls to master.guess and one of them was the secret, so we pass the test case.
 *
 * Note:  Any solutions that attempt to circumvent the judge will result in disqualification.
 */
public class GuessTheWord {
	private static final int STRING_SIZE = 6; // 每个字符串有6个字符

	/**
	 * 思路：
	 *   1. 实际上无法保证10次以内就可以猜出secret
	 *      例如： ["aaaaaa", "bbbbbb", ... "zzzzzz", ...] secret = "zzzzzz"
	 *      如果依照 a~z的顺序猜测，需要26次才能猜出，通过随机猜测可减少这种情况发生的概率，但若要确保可以猜出，需要特定的原始wordlist
	 *   2. 利用master.guess()的返回值，逐步减少wordList
	 *      例如： c = master.guess(s) and c < 6, 则wordlist应去除s以及与s中字符恰好匹配个数不等于c的字符串， 直到 c == 6
	 *
	 * @param wordlist
	 * @param master
	 */
	public static void findSecretWord(String[] wordlist, Master master) {
		int loop = 0;
		while (loop ++ < 10) {
			String str = wordlist[new Random().nextInt(wordlist.length)];
			int count = master.guess(str);
			if(count == STRING_SIZE) return; // success

			wordlist = narrow(wordlist, str, count);
		}
	}

	private static String[] narrow(String[] wordlist, String str, int count) {
		List<String> result = new ArrayList<>();

		for(int i = 0; i < wordlist.length; i ++) {
			if(wordlist[i].equals(str)) continue; // 已猜测的字符串不保留

			int cnt = 0;
			for(int j = 0; j < STRING_SIZE; j ++) {  // 保留恰好匹配的个数等于count的字符串
				if(str.charAt(j) == wordlist[i].charAt(j)) {
					cnt ++;
				}

				if(cnt > count) break;
			}

			if(cnt == count) {
				result.add(wordlist[i]);
			}
		}

		return result.toArray(new String[result.size()]);
	}

	/**
	 *  This is the Master's API interface.
	 *  You should not implement it, or speculate about its implementation
	 */
	interface Master {
		int guess(String word);
	}
}
