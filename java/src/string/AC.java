package string;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * AC自动机算法：Aho–Corasick算法是由Alfred V. Aho和Margaret J.Corasick 发明的字符串搜索算法。
 *
 * BF、RK、BM、KMP算法都是单模式串匹配算法，AC自动机算法是多模式串匹配算法。
 *
 * AC自动机基于 Trie树:
 *   1.使用模式串构造 Trie树;
 *   2.在 Trie树上构建失败指针 (相当于 KMP中的失效函数)
 */
public class AC {
	private static final int R = 256; // extended ascii

	private ACNode root;
	private int n;

	/**
	 * 构造方法
	 * @param pattern 模式串数组
	 */
	public AC(String[] pattern){
		root = new ACNode();
		buildTrie(pattern);
		buildFailurePointer();
	}

	/**
	 * @param text 主串
	 * @return  List<String>: String is two-tuples like (a,b), a refer to the first index of matching substring,
	 *                       b refer to the length of matching substring
	 */
	public List<String> match(String text){
		int n = text.length();
		ACNode p = root;

		List<String> result = new ArrayList<>();

		for(int i = 0; i < n; i ++){
			char c = text.charAt(i);

			if(p.children[(int)c] == null && p != root){
				p = p.fail;
			}

			p = p.children[(int)c];
			if(p == null) p = root;
			ACNode temp = p;
			while(temp != root){
				if(temp.isEnd){
					int pos = i - temp.length + 1;
					result.add("(" + pos + "," + temp.length + ")");
				}
				temp = temp.fail;
			}
		}
		return result;
	}

	/**
	 * 使用模式串构造 Trie树
	 * @param pattern
	 */
	private void buildTrie(String[] pattern){
		for(int i = 0; i < pattern.length; i ++){
			String str = pattern[i];
			appendString(str.toCharArray());
		}
	}

	private void appendString(char[] str){
		int n = str.length;
		if( n <= 0) return;

		ACNode p = root;
		for(int i = 0; i < n; i ++){
			int c = (int)str[i];

			ACNode cn = p.children[c];
			if(cn == null){
				cn = new ACNode();
			}

			if(i == n - 1){
				cn.isEnd = true;
				cn.length = n;
			}
			p.children[c] = cn;
			p = cn;
		}
	}

	/**
	 * 根据前一个的失败指针，计算当前失败指针
	 * 示例 ：为了方便，将节点用实际字符数据值表示，实际的实现中节点是不存储数据的
	 *                                root   (root.fail = null)
	 *                          ↙     ↓    ↘
	 *                       a        b       c
	 *                    ↙           ↓        ↘
	 *                  b             c (q)       e
	 *                ↙              ↓
	 *              c (p)             d (qc)
	 *           ↙
	 *         e (pc)
	 *    求节点 pc的失败指针，假设其父节点 p的失败指针为 q:
	 *      1. 如果 q == null, pc.fail = root, 否则执行 2
	 *      2. 如果 q的子节点中存在 qc, 使得 qc和 pc对应的字符相同，则 pc.fail = qc, 否则执行 3
	 *      3. q = q.fail 执行 1
	 *    p的失败指针 q必然是 p的上层节点，所以以层次遍历的方式依次计算。
	 */
	private void buildFailurePointer(){
		Queue<ACNode> queue = new LinkedList<>();
		queue.add(root); // root的失败指针指向 null

		while(!queue.isEmpty()){
			ACNode p = queue.poll();
			for(int i = 0; i < R; i ++){
				ACNode pc = p.children[i];
				if(pc == null) continue;
				if(p == root){
					pc.fail = root;
				}else{
					ACNode q = p.fail;
					while(q != null){
						ACNode qc = q.children[i];
						if(qc != null){
							pc.fail = qc;break;
						}
						q = q.fail;
					}
					if(q == null)
						pc.fail = root;
				}
				queue.add(pc);
			}
		}
	}



	private static class ACNode{
		private ACNode[] children = new ACNode[R]; // extended ascii
		private boolean isEnd = false;  // 是否为结尾字符
		private int length = -1;        // 当isEnd = true时，记录字符串长度
		private ACNode fail;            // 失败指针
	}
}
