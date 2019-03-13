package greedy;

import java.util.*;

/**
 * Huffman Coding实现
 */
public class HuffmanCoding {
	private static Huffman root;  // Huffman Tree 根节点

	/**
	 * 构建 Huffman Tree
	 * @param sequence int[], int[i] 表示字符 (char)i 对应的权重为 int[i]
	 * @return
	 */
	public static Huffman buildHuffmanTree(int[] sequence){
		int n = sequence.length;

		PriorityQueue<Huffman> queue = new PriorityQueue<>();

		for(int i = 0; i < n; i ++){
			if(sequence[i] > 0){
				queue.offer(new HuffmanLeaf(sequence[i], (char) i));
			}
		}

		while(queue.size() > 1){
			Huffman l = queue.poll();
			Huffman r = queue.poll();
			queue.offer(new HuffmanNode(l, r));
		}

		root = queue.poll();
		return root;
	}

	/**
	 * 迭代求解 Huffman code
	 * @param root
	 * @return
	 */
	public static Map<Character, String> getCodes(Huffman root){
		Map<Character, String> map = new HashMap<>();

		// 只有一个字符, 默认编码 "0"
		if(root instanceof HuffmanLeaf){
			map.put(((HuffmanLeaf)root).value, "0");
			return map;
		}

		StringBuilder builder = new StringBuilder();
		Stack<Huffman> stack = new Stack<>();
		Huffman h = root;

		while(true){
			if(!(h instanceof HuffmanLeaf)) {
				stack.push(h);
				h = ((HuffmanNode) h).left;
				builder.append("0");   // 编码: 左连接用 0 表示， 右连接用 1 表示
			}else{
				map.put(((HuffmanLeaf) h).value, builder.toString());

				if(stack.isEmpty()) break;

				h = stack.pop();
				doBuilder(root, h, builder);

				h = ((HuffmanNode) h).right;
				builder.append("1");
			}
		}

		return map;
	}

	/**
	 * 回溯节点对应的 builder
	 * @param root Huffman Tree 根节点
	 * @param h 当前回溯节点
	 * @param builder 回溯前的 builder
	 */
	private static void doBuilder(Huffman root, Huffman h, StringBuilder builder){

		Huffman node = root;
		int i = 0;
		for(; i < builder.length(); i ++){
			if(node == h)
				break;

			if(builder.charAt(i) == '0'){
				node = ((HuffmanNode) node).left;
			}else{
				node = ((HuffmanNode) node).right;
			}
		}

		if(i < builder.length())
			builder.delete(i, builder.length());
	}

	/**
	 * 递归方式求解 Huffman code
	 * @param h
	 * @param prefix
	 * @param result
	 */
	public static void getCodes(Huffman h, StringBuilder prefix, Map<Character, String> result){
		if(h instanceof HuffmanLeaf) {
			if(root == h)
				result.put(((HuffmanLeaf) h).value, "0");
			else
				result.put(((HuffmanLeaf) h).value, prefix.toString());
		}else{
			HuffmanNode node = (HuffmanNode) h;

			prefix.append("0");
			getCodes(node.left, prefix, result);
			prefix.deleteCharAt(prefix.length() - 1);

			prefix.append("1");
			getCodes(node.right, prefix, result);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}


	public static void main(String[] args){
		String test = "this is an example for huffman encoding";
		assert !test.isEmpty();

		int[] sequence = new int[256];

		for(char c : test.toCharArray()){
			sequence[c] ++;

		}

		Map<Character, String> map = HuffmanCoding.getCodes(buildHuffmanTree(sequence));

		Map<Character, String> map2 = new HashMap<>();
		getCodes(buildHuffmanTree(sequence), new StringBuilder(), map2);

		System.out.println("迭代");
		for(Map.Entry<Character,String> entry : map.entrySet()){
			System.out.println(entry.getKey() + ";" + entry.getValue());
		}

		System.out.println("递归");
		for(Map.Entry<Character,String> entry : map2.entrySet()){
			System.out.println(entry.getKey() + ";" + entry.getValue());
		}
	}
}



abstract class Huffman implements Comparable<Huffman>{
	public int weight;

	public Huffman(int weight){
		this.weight = weight;
	}

	@Override
	public int compareTo(Huffman h) {
		return this.weight - h.weight;
	}
}

class HuffmanLeaf extends Huffman{
	public final char value;

	public HuffmanLeaf(int weight, char c){
		super(weight);
		this.value = c;
	}
}

class HuffmanNode extends Huffman{
	public Huffman left, right;

	public HuffmanNode(Huffman l, Huffman r){
		super(l.weight + r.weight);
		this.left = l;
		this.right = r;
	}
}
