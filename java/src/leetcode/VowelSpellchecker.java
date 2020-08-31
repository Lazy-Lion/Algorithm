package leetcode;

import javafx.util.Pair;

import java.util.*;

/**
 * leetcode 966. Vowel Spellchecker
 * Given a wordlist, we want to implement a spellchecker that converts a query word into a correct word.
 *
 * For a given query word, the spell checker handles two categories of spelling mistakes:
 *
 *   Capitalization: If the query matches a word in the wordlist (case-insensitive), then the query word is returned
 * with the same case as the case in the wordlist.
 *   Example: wordlist = ["yellow"], query = "YellOw": correct = "yellow"
 *   Example: wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
 *   Example: wordlist = ["yellow"], query = "yellow": correct = "yellow"
 *
 *   Vowel Errors: If after replacing the vowels ('a', 'e', 'i', 'o', 'u') of the query word with any vowel individually,
 * it matches a word in the wordlist (case-insensitive), then the query word is returned with the same case as the match
 * in the wordlist.
 *   Example: wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
 *   Example: wordlist = ["YellOw"], query = "yeellow": correct = "" (no match)
 *   Example: wordlist = ["YellOw"], query = "yllw": correct = "" (no match)
 *
 * In addition, the spell checker operates under the following precedence rules:
 *   When the query exactly matches a word in the wordlist (case-sensitive), you should return the same word back.
 *   When the query matches a word up to capitlization, you should return the first such match in the wordlist.
 *   When the query matches a word up to vowel errors, you should return the first such match in the wordlist.
 *   If the query has no matches in the wordlist, you should return the empty string.
 *   Given some queries, return a list of words answer, where answer[i] is the correct word for query = queries[i].

 * Example 1:
 *   Input: wordlist = ["KiTe","kite","hare","Hare"],
 *          queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
 *   Output: ["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
 *
 * Note:
 *   1 <= wordlist.length <= 5000
 *   1 <= queries.length <= 5000
 *   1 <= wordlist[i].length <= 7
 *   1 <= queries[i].length <= 7
 *   All strings in wordlist and queries consist only of english letters.
 */
public class VowelSpellchecker {
	private static final Set<Character> VOWEL_CHAR =
			new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
	private static final String WILDCARD = "*";

	private static HashMap<String, String> wordlist_lower;   // wordlist 忽略大小写映射
	private static HashSet<String> wordlist_set;

	public static String[] spellchecker(String[] wordlist, String[] queries) {
		int n = queries.length;

		String[] result = new String[n];

		wordlistMapping(wordlist);
		Map<String, Pair<String, Integer>> vowelMap = vowelMap(wordlist);

		for(int i = 0 ; i < n; i ++) {
			result[i] = correct(queries[i], wordlist, vowelMap);
		}
		return result;
	}

	/**
	 * 元音字符可以替换为其他元音，使用通配符 * 替代, 建立映射关系
	 */
	private static Map<String, Pair<String, Integer>> vowelMap(String[] wordlist) {
		Map<String, Pair<String, Integer>> map = new HashMap<>();

		String s, str;
		for(int k = 0; k < wordlist.length; k ++) {
			s = wordlist[k];
			str = s.toLowerCase();

			// 优化思路： 目前的实现对于含有多个元音字符的字符串，分别替换一至多个字符，记录key；
			//           可以优化为直接替换全部的元音字符为通配符(通配符多，限定条件少，必然先遇到符合条件的原字符串)，记录key；
			//           这样value中只需要记录第一个遇到的原字符即可，不需要记录原字符所在的位置;
			//           vowelCorrect() 方法匹配时也直接替换全部元音字符进行匹配
			for(int i = 0; i < s.length(); i ++) {
				if(VOWEL_CHAR.contains(str.charAt(i))) {  // 字母字符装箱的Character对象，使用的是Cache
					str = str.substring(0,i) + WILDCARD + str.substring(i + 1);
					if(!map.containsKey(str)) {
						map.put(str, new Pair<>(s, k));
					}
				}
			}
		}
		return map;
	}

	private static void wordlistMapping(String[] wordlist) {
		wordlist_set = new HashSet<>();
		wordlist_lower = new HashMap<>();

		String lower;
		for(String s : wordlist) {
			wordlist_set.add(s);

			lower = s.toLowerCase();
			wordlist_lower.putIfAbsent(lower, s);
		}
	}

	private static String correct(String str, String[] wordlist, Map<String, Pair<String, Integer>> vowelMap) {
		if(str.isEmpty()) return "";

		if(wordlist_set.contains(str)) return str;   // wordlist中存在字符串

		String correct;

		correct = capitalization(str);

		if(correct.isEmpty()) {
			correct = vowelCorrect(str, vowelMap, wordlist.length);
		}

		return correct;
	}

	/**
	 * 忽略大小写匹配
	 */
	private static String capitalization(String str) {

		String lower = str.toLowerCase();

		if(wordlist_lower.containsKey(lower)) {
			return wordlist_lower.get(lower);
		}

		return "";
	}

	/**
	 * 元音替换匹配
	 */
	private static String vowelCorrect(String str, Map<String, Pair<String, Integer>> vowelMap, int n) {
		String correct = "";
		int index = n;

		String s = str.toLowerCase();
		Pair<String, Integer> pair;

		// 这部分的优化参看构造vowelMap的vowelMap()方法
		for(int i = 0; i < str.length(); i ++) {
			if(VOWEL_CHAR.contains(s.charAt(i))) {
				s = s.substring(0, i) + WILDCARD + s.substring(i + 1);
				if(vowelMap.containsKey(s)) {
					pair = vowelMap.get(s);
					if(pair.getValue() < index) {
						correct = pair.getKey();
						index = pair.getValue();
					}
				}
			}
		}

		return correct;
	}



	public static void main(String[] args) {
		String[] wordlist = new String[] {"KiTe","kite","hare","Hare"};
		String[] queries = new String[] {"kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"};
		System.out.println(Arrays.toString(spellchecker(wordlist, queries)));
	}
}
