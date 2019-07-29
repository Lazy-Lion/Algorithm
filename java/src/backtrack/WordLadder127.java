package backtrack;

import java.util.*;

/**
 * 127. Word Ladder
 *   Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation
 * sequence from beginWord to endWord, such that:
 *
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 * Note:
 *   Return 0 if there is no such transformation sequence.
 *   All words have the same length.
 *   All words contain only lowercase alphabetic characters.
 *   You may assume no duplicates in the word list.
 *   You may assume beginWord and endWord are non-empty and are not the same.
 * Example 1:
 *   Input:
 *     beginWord = "hit",
 *     endWord = "cog",
 *     wordList = ["hot","dot","dog","lot","log","cog"]
 *
 *   Output: 5
 *   Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 *   return its length 5.
 *
 * Example 2:
 *   Input:
 *     beginWord = "hit"
 *     endWord = "cog"
 *     wordList = ["hot","dot","dog","lot","log"]
 *   Output: 0
 *
 *   Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
 */
public class WordLadder127 {
	private static final String LADDER = "LADDER";  // 分层标识
	private static final String WILDCARD = "*"; // 通配符标识

	/**
	 * 图的广度优先遍历求最短路径
	 */
	public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
		if(!wordList.contains(endWord)) return 0;

		Map<String, List<String>> map = getMap(beginWord, wordList); // 与key只有一个字符不同的字符串列表映射
		Set<String> set = new HashSet<>();   // 记录已访问过的word

		Queue<String> queue = new LinkedList<>();
		set.add(beginWord);
		queue.offer(beginWord);
		queue.offer(LADDER);
		int ladder = 1;  // 当前路径长度(层数)

		while(!queue.isEmpty()) {
			String str = queue.poll();
			if(str.equals(LADDER)) {
				ladder ++;
				if(!queue.isEmpty())  // 下一层还有数据
					queue.offer(LADDER);
				continue;
			}
			for(String s : map.get(str)) {
				if(s.equals(endWord)) return ++ladder;
				if(!set.contains(s)) {
					queue.offer(s);
					set.add(s);
				}
			}
		}
		return 0;
	}

	/**
	 *  时间复杂度： O( N*N*M )   N - wordList长度，M - 字符串长度
	 */
	private static Map<String, List<String>> getMap(String begin, List<String> wordList) {
		Map<String, List<String>> map = new HashMap<>();

		List<String> list = new ArrayList<>();
		for(String s : wordList) {
			if(oneDiff(s, begin))
				list.add(s);
		}
		map.put(begin, list);

		for(int i = 0; i < wordList.size(); i ++) {
			list = new ArrayList<>();
			for(int j = 0; j < wordList.size(); j ++) {
				if(i == j) continue;

				if(oneDiff(wordList.get(i), wordList.get(j))) {
					list.add(wordList.get(j));
				}
			}
			map.put(wordList.get(i), list);
		}
		return map;
	}

	private static boolean oneDiff(String s1, String s2) {
		int c = 0;
		for(int i = 0; i < s1.length(); i ++) {
			if(s1.charAt(i) != s2.charAt(i))
				c ++;
			if(c > 1) break;
		}
		if(c == 1) return true;
		return false;
	}

	/**
	 * 邻接节点的计算方法优化：
	 *  hit -> *it, h*t, hi*, 使用通配符替代一个字符
	 *  h*t -> hot, hit ... , 若两个不相等的字符串，通配符转换后相等，则为邻接字符
	 *  时间复杂度：不超过 max{ O(N*N), O(N*M) }
	 */
	public static int ladderLengthOptimize(String beginWord, String endWord, List<String> wordList) {
		if(!wordList.contains(endWord)) return 0;

		int len = beginWord.length();

		Map<String, List<String>> map = getWildcardMap(beginWord, wordList);

		Queue<String> queue = new LinkedList<>();
		Set<String> set = new HashSet<>();
		queue.offer(beginWord);
		queue.offer(LADDER);
		set.add(beginWord);
		int ladder = 1;

		String str;
		String strW;
		while(!queue.isEmpty()) {
			str = queue.poll();
			if(str.equals(LADDER)) {
				ladder ++;
				if(!queue.isEmpty())
					queue.offer(LADDER);
				continue;
			}

			for(int i = 0; i < len; i ++) {
				strW = str.substring(0, i) + WILDCARD + str.substring(i + 1, len);
				for(String s : map.get(strW)) {
					if(s.equals(endWord)) return ++ladder;

					if(!set.contains(s)) {
						set.add(s);
						queue.offer(s);
					}
				}
			}
		}

		return 0;
	}

	private static Map<String, List<String>> getWildcardMap(String begin, List<String> wordList) {
		int len = begin.length();
		Map<String, List<String>> map = new HashMap<>();

		String str;
		String strW;
		List<String> list;
		for(int i = -1; i < wordList.size(); i ++) {
			if(i == -1) {
				str = begin;
			} else {
				str = wordList.get(i);
			}

			for(int j = 0; j < len; j ++) {
				strW = str.substring(0, j) + WILDCARD + str.substring(j + 1, len);
				if(map.containsKey(strW)) {
					map.get(strW).add(str);
				} else {
					list = new ArrayList<>();
					list.add(str);
					map.put(strW, list);
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		int result = ladderLength("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog"));
		System.out.println(result);

		result = ladderLength("hit", "cog", Arrays.asList("hot","dot","dog","lot","log"));
		System.out.println(result);

		result = ladderLength("a", "c", Arrays.asList("a","b","c"));
		System.out.println(result);

		result = ladderLength("hit", "cog", Arrays.asList("hot","hit","cog","dot","dog"));
		System.out.println(result);

		result = ladderLength("kiss", "tusk", Arrays.asList("miss","dusk","kiss","musk","tusk","diss","disk","sang","ties","muss"));
		System.out.println(result);

		result = ladderLength("hot", "dog", Arrays.asList("hot","dog"));
		System.out.println(result);


		result = ladderLengthOptimize("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog"));
		System.out.println(result);

		result = ladderLengthOptimize("hit", "cog", Arrays.asList("hot","dot","dog","lot","log"));
		System.out.println(result);

		result = ladderLengthOptimize("a", "c", Arrays.asList("a","b","c"));
		System.out.println(result);

		result = ladderLengthOptimize("hit", "cog", Arrays.asList("hot","hit","cog","dot","dog"));
		System.out.println(result);

		result = ladderLengthOptimize("kiss", "tusk", Arrays.asList("miss","dusk","kiss","musk","tusk","diss","disk","sang","ties","muss"));
		System.out.println(result);

		result = ladderLengthOptimize("hot", "dog", Arrays.asList("hot","dog"));
		System.out.println(result);
	}
}
