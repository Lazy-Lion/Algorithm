package backtrack;

import java.util.*;

/**
 * leetcode 126. Word Ladder II
 *   Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation
 * sequence(s) from beginWord to endWord, such that:
 *
 * Only one letter can be changed at a time
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 *
 * Note:
 *   Return an empty list if there is no such transformation sequence.
 *   All words have the same length.
 *   All words contain only lowercase alphabetic characters.
 *   You may assume no duplicates in the word list.
 *   You may assume beginWord and endWord are non-empty and are not the same.
 * Example 1:
 *   Input:
 *     beginWord = "hit",
 *     endWord = "cog",
 *     wordList = ["hot","dot","dog","lot","log","cog"]
 *   Output:
 *     [
 *      ["hit","hot","dot","dog","cog"],
 *      ["hit","hot","lot","log","cog"]
 *     ]
 * Example 2:
 *   Input:
 *   beginWord = "hit"
 *   endWord = "cog"
 *   wordList = ["hot","dot","dog","lot","log"]
 * Output: []
 * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
 */
public class WordLadderII126 {
	private static final String WILDCARD = "*";
	private static final String LADDER = "LADDER";

	/**
	 * 思路类似 WordLadder127，并记录轨迹
	 * note: 可能存在多个最短轨迹，所以访问到 endWord 时不立刻终止计算，直到需要访问下一层级才终止
	 */
	public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
		List<List<String>> result = new ArrayList<>();

		if(!wordList.contains(endWord)) return result;

		Map<String, List<String>> map = getWildcardMap(beginWord, wordList);
		Map<String, List<String>> path = new HashMap<>();
		Map<String, Integer> visited = new HashMap<>();
		Queue<String> queue = new LinkedList<>();

		queue.offer(beginWord);
		queue.offer(LADDER);

		int ladder = 1;
		visited.put(beginWord, ladder);

		int len = beginWord.length();
		int endLadder = wordList.size();
		String strW;
		while(!queue.isEmpty() && ladder <= endLadder) {
			String str = queue.poll();

			if(str.equals(LADDER)) {
				ladder ++;
				if(!queue.isEmpty())
					queue.offer(LADDER);
				continue;
			}

			for(int i = 0; i < len; i ++) {
				strW = str.substring(0, i) + WILDCARD + str.substring(i + 1, len);
				for(String s : map.get(strW)) {
					if(s.equals(str)) continue;

					if(s.equals(endWord))
						endLadder = ladder + 1;

					if(!visited.containsKey(s)) {
						queue.offer(s);

						List<String> list = new ArrayList<>();
						list.add(str);
						path.put(s, list);
						visited.put(s, ladder);
					}
					if(visited.containsKey(s) && visited.get(s) == ladder) {
						if(path.containsKey(s) && !path.get(s).contains(str)) {
							path.get(s).add(str);
						} else {
							List<String> list = new ArrayList<>();
							list.add(str);
							path.put(s, list);
						}
					}
				}
			}
		}

		getPath(beginWord, endWord, path, result, new ArrayList<>());

		return result;
	}

	private static void getPath(String beginWord, String endWord, Map<String, List<String>> path, List<List<String>> result
			, List<String> list) {
		if(beginWord == endWord) {
			List<String> temp = new ArrayList<>();
			temp.add(beginWord);
			for(int i = list.size() - 1; i >= 0; i --) {
				temp.add(list.get(i));
			}
			result.add(temp);
			return;
		}

		if(!path.containsKey(endWord)) return;

		list.add(endWord);
		for(String s : path.get(endWord)) {
			getPath(beginWord, s, path, result, list);
		}
		list.remove(list.size() - 1);
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
		List<List<String>> result = findLadders("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog"));
		System.out.println(result);

		result = findLadders("hit", "cog", Arrays.asList("hot","dot","dog","lot","log"));
		System.out.println(result);
	}
}
