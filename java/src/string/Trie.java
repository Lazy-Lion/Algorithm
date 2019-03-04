package string;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Trie树：也称字典树、前缀树。
 *                               ○
 *                         t↙  a↓   ↘i
 *                      ○        ○      ○
 *                  o↙  ↘e              ↘n
 *                 ○       ○                ○
 *                    a↙ d↓ ↘n         n↙
 *                   ○    ○    ○        ○
 *      如上例：数据不是直接保存在节点上，是由节点在树上的位置确定的；结尾字符不一定都是叶子节点，如下实现通过 isEnd标记。
 */
public class Trie {
	private static final int R = 256;  // extended ascii

	private TrieNode root;  // 根节点，无实际意义
	private int n;   // Trie树中存储的字符串个数

	public Trie(){
		root = new TrieNode();
	}

	public int size(){
		return n;
	}

	public void insert(String text){
		if(text == null) return;
		insert(text.toCharArray());
	}

	public void insert(char[] text){
		if(text.length == 0) return;

		TrieNode p = root;
		for(int i = 0; i < text.length; i ++){
			int index = (int)text[i];
			if(p.next[index] == null){
				TrieNode node = new TrieNode();
				p.next[index]  = node;
			}
			p = p.next[index];
		}
		p.isEnd = true;
		n ++;
	}

	public boolean search(String text){
		if(text == null) return false;
		return search(text.toCharArray());
	}

	public boolean search(char[] text){
		if(text.length == 0) return false;

		TrieNode p = root;
		for(int i = 0; i < text.length; i ++){
			int index = (int)text[i];
			if(p.next[index] == null)
				return false;
			p = p.next[index];
		}
		if(p.isEnd)
			return true;
		return false;
	}

	public void delete(String text){
		delete(text.toCharArray());
	}

	public void delete(char[] text){
		if(text.length == 0) return;
		delete(root, text, 0);
	}

	/**
	 * 递归方式从Trie树中删除输入字符串
	 * @param node 当前待处理字符在Trie树中的前驱节点
	 * @param text 待删字符数组
	 * @param m 正在处理的字符：0表示正在处理 text[0], i 表示正在处理 text[i]
	 * @return
	 */
	private TrieNode delete(TrieNode node, char[] text, int m){
		if(node == null) return null;

		if(m == text.length){  // 已处理完最后一个字符，不直接删除，设置isEnd=false, 后续根据是否有后续节点判断是否删除
			if(node.isEnd) n --;   // 表示Trie树存在输入字符串
			node.isEnd = false;
		}else{
			int index = (int)text[m];
			node.next[index] = delete(node.next[index], text, m + 1);
		}

		// 如果 node不是其他字符串的结尾，且没有后继节点，返回 null (删除)；否则返回 node (保留)
		if(node.isEnd) return node;
		for(int i = 0; i < R; i ++){
			if(node.next[i] != null)
				return node;
		}
		return null;

	}

	public Iterable<String> getAllWithPrefix(String prefix){
		Queue<String> result = new LinkedList<>();
		TrieNode p = get(prefix);

		collect(p, new StringBuilder(prefix), result);
		return result;
	}

	private TrieNode get(String text){
		if(text == null)
			return root;

		TrieNode p = root;
		for(int i = 0; i < text.length(); i ++){
			int index = (int)text.charAt(i);
			if(p.next[index] == null)
				return null;
			p = p.next[index];
		}
		return p;
	}

	private void collect(TrieNode node, StringBuilder prefix, Queue<String> result){

		if(node == null) return;
		if(node.isEnd) result.add(prefix.toString());

		for(int i = 0; i < R; i ++){
			if(node.next[i] != null){
				prefix.append((char)i);
				collect(node.next[i], prefix, result);
				prefix.deleteCharAt(prefix.length() - 1);
			}
		}
	}

	private static class TrieNode{   // Trie 节点
//		private char data; // 存储节点字符; 实际上并不需要，可以通过next[]判断
		private boolean isEnd = false;  // true if the node represents end of a string
		private TrieNode[] next = new TrieNode[R]; // 子节点数组, 下标对应字符的ascii值，如果字符集很大，这部分会比较消耗内存，
		                                           // 可以使用其他数据结构，以损失部分性能为代价，降低内存消耗
	}
}
