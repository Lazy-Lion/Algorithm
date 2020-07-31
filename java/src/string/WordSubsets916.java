package string;

import java.util.*;

/**
 * leetcode 916. Word Subsets
 * We are given two arrays A and B of words.  Each word is a string of lowercase letters.
 *
 *   Now, say that word b is a subset of word a if every letter in b occurs in a, including multiplicity.  For example,
 * "wrr" is a subset of "warrior", but is not a subset of "world".
 *
 * Now say a word a from A is universal if for every b in B, b is a subset of a.
 *
 * Return a list of all universal words in A.  You can return the words in any order.
 *
 * Example 1:
 *   Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["e","o"]
 *   Output: ["facebook","google","leetcode"]
 * Example 2:
 *   Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["l","e"]
 *   Output: ["apple","google","leetcode"]
 * Example 3:
 *   Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["e","oo"]
 *   Output: ["facebook","google"]
 * Example 4:
 *   Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["lo","eo"]
 *   Output: ["google","leetcode"]
 * Example 5:
 *   Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["ec","oc","ceo"]
 *   Output: ["facebook","leetcode"]
 * Note:
 *   1 <= A.length, B.length <= 10000
 *   1 <= A[i].length, B[i].length <= 10
 *   A[i] and B[i] consist only of lowercase letters.
 *   All words in A[i] are unique: there isn't i != j with A[i] == A[j].
 */
public class WordSubsets916 {
	public static List<String> wordSubsets(String[] A, String[] B) {
		List<String> result = new ArrayList<>();

		int[] map = getMap(B);

		for(String s : A) {
			if(isSubset(Arrays.copyOf(map, map.length), s)) {
				result.add(s);
			}
		}

		return result;
	}

	private static int[] getMap(String[] B) {
		int[] map = new int[26];
		int[] bmap;

		for(String s : B) {
			bmap = new int[26];
			for(int j = 0; j < s.length(); j ++) {  // 针对B数组中每个String，各个字符的数目
				bmap[s.charAt(j) - 'a'] ++;
			}
			for(int k = 0; k < map.length; k ++) {
				map[k] = Math.max(map[k], bmap[k]);
			}
		}
		return map;
	}

	private static boolean isSubset(int[] map, String s) {
		int idx;
		for(int i = 0; i < s.length(); i ++) {
			idx = s.charAt(i) - 'a';
			if(map[idx] > 0) {
				map[idx] --;
			}
		}
		for(int i : map) {
			if(i > 0)
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String[] A = new String[]{"amazon","apple","facebook","google","leetcode"};
		String[] B = new String[]{"e", "o"};
		System.out.println(wordSubsets(A, B));
		B = new String[]{"l", "e"};
		System.out.println(wordSubsets(A, B));
		B = new String[]{"e", "oo"};
		System.out.println(wordSubsets(A, B));
		B = new String[]{"lo","eo"};
		System.out.println(wordSubsets(A, B));
		B = new String[]{"ec","oc","ceo"};
		System.out.println(wordSubsets(A, B));
	}
}
