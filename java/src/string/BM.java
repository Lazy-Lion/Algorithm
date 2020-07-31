package string;

/**
 * Boyer-Moore算法：高效的字符串匹配算法
 *    keys：
 *    	 1. 从左向右比较模式串与主串的对应字符
 *   	 2. 坏字符 (bad character) : 主串与模式串匹配,从右向左第一个不相同的字符，坏字符对应的是主串字符
 *    	 3. 好后缀 (good suffix) : 坏字符b对应的模式串字符p, 好后缀是指p字符后的模式串子序列
 *   思路：
 *      1.BF: 字符串匹配时，模式串在主串上滑动，字符对应比较，当遇到不匹配字符时，向右滑动一个位置
 *      2.BM: Moore算法的优化思路主要在于，当遇到不匹配字符时，尽可能多的向右滑动，而不是滑动一个位置
 *         1).不同于BF模式串与主串从左到右进行字符比较，BM从右到左比较，不匹配时，获得 bad character(bc), 对应模式串的下标 pi
 *         2).从模式串中找到最靠右的等于bc的字符下标 xi，如果不存在则 xi = -1
 *         3).则此时向右滑动 s = pi - xi
 *         4).pi - xi 的值可能为负数，即有好后缀，且好后缀中存在 bc字符
 *         5).假设好后缀为 u, 模式串中存在另一个子串 v等于 u,如果有多个取最靠右(避免滑动过多), 则滑动 v到当前 u的位置
 *         6).如果模式串中不存在另一个子串等于 u, 分两种情况：
 *             I. u的子后缀和模式串的前缀匹配(取最大匹配的子后缀)，将该前缀滑动到当前 u的子后缀位置；
 *             II.u的子后缀没有和模式串前缀匹配，则滑动 m(模式串的长度)个位置
 *
 *      如上描述：
 *         1).需要判断坏字符在模式串中的位置，这部分通过散列表预处理 (如果字符集很大，内存消耗就会大，所以对于内存敏感的情况，可以
 *             舍弃坏字符规则的处理，只使用好后缀规则，BM算法性能有所降低，内存消耗减少)
 *         2).需要模式串的后缀与模式串子串的匹配关系，模式串的后缀与前缀的匹配关系
 *            由于前缀和后缀只需要确定长度即可确定，所以只需整数标记即可；模式串的子串可以通过起始index和长度确定
 *            这部分也进行预处理
 *         3).滑动匹配, 滑动数选取坏字符和好后缀两种规则求得的最大值
 */
public class BM {

	public int match(String text, String pattern){
		int m = pattern.length();
		int n = text.length();

		if(m < 0 || n < 0 || n < m) return - 1;
		if(m == 0) return 0;

		// preprocessing
		int[] map = getMap(pattern, m);
		int[] suffix = new int[m + 1];
		boolean[] prefix = new boolean[m + 1];
		preSuffix(pattern, m, prefix, suffix);

		// matching
		return match(text, pattern, map, suffix, prefix, m, n);
	}

	/**
	 * assume: 只存在扩展ascii的字符
	 * map[48]表示: ascii值为48的字符('0'),在模式串中的index (多个，选最右), 若不存在则为 -1
	 * @param pattern 模式串
	 * @param m   模式串长度
	 * @return int[]
	 */
	private int[] getMap(String pattern, int m){
		int[] map = new int[256];
		for(int i = 0; i < map.length; i ++)
			map[i] = -1;
		for(int i = 0; i < m; i ++){
			int index = (int) pattern.charAt(i);
			map[index] = i;
		}
		return map;
	}

	/**
	 * 预处理 suffix 和 prefix
	 * suffix[1]表示: 模式串长度为 1的后缀u, 模式串中与u匹配的字符串v (非u, 靠最右), v的开始index
	 * prefix[1]表示: 模式串长度为 1的后缀是(true)否(false)为模式串的前缀
	 * @param pattern 模式串
	 * @param m   模式串长度
	 * @param prefix
	 * @param suffix
	 */
	private void preSuffix(String pattern, int m, boolean[] prefix, int[] suffix){
		for(int i = 0; i < m; i ++)
			suffix[i] = -1;

		for(int i = 0; i < m - 1; i ++){

			//求[0,i]的子串和pattern的公共子后缀, 从右向左逐个比较
			int j = i;
			int k = 0;  // 后缀长度
			while(j >= 0 && pattern.charAt(j) == pattern.charAt(m - 1 - k)){
				j --;
				k ++;
				suffix[k] = j + 1;
			}
			if(j == -1) prefix[i + 1] = true;  // 模式串前缀
		}
	}

	/**
	 * @return -1 if no match, or index of the first matching char
	 */
	private int match(String text, String pattern, int[] map, int[] suffix, boolean[] prefix, int m, int n){
		for(int i = 0; i < n - m + 1;){
			int b_shift = 0;  // 由坏字符规则计算出的滑动距离
			// 1. bad character shift
			int bc;  // 坏字符对应模式串中字符的位置
			int j = m - 1;
			for(; j >= 0; j --){
				if(pattern.charAt(j) != text.charAt(i + j))
					break;
			}

			if(j == -1) return i;  // 匹配

			bc = j;
			b_shift = bc - map[(int)text.charAt(bc + i)];

			// 2. good suffix shift
			if(bc == m - 1){   //no good suffix
				i += b_shift;
				continue;
			}

			int s_shift = 0;  // 由好后缀规则计算出的滑动距离
			int suf = m - 1 - bc;   // length of good suffix
			if(suffix[suf] != -1){
				s_shift = (m - suf) - suffix[suf];
			}else{
				int k;
				for(k = suf - 1; k > 0; k --){
					if(prefix[k]){
						s_shift = m - k;
						break;
					}
				}
				if(k == 0)
					s_shift = m;
			}

			i = i + Math.max(b_shift, s_shift);
		}
		return -1;
	}
}
