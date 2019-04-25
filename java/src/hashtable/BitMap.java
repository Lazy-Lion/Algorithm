package hashtable;


/**
 * 位图：
 *   1.提出问题：如何判断范围在 1 ~ 1亿 的整数是否在给定的 1000万个 整数中
 *   2.解决方法： 定义大小为 1亿+1 的数组，数组下标对应整数，数组存储true or false; 如果下标对应的整数在给定的1000万个整数中，则
 *       设为true,否则为false；
 *   3.扩展： 如果范围更大，如 1- 10亿，此方法消耗的内存会比直接存储1000万个整数多
 *     布隆过滤器(bloom filter):
 *     		1)对于 1-10亿范围的整数，依然建立1亿大小的位图数组；
 *     	    2)对每个整数，通过多个hash函数分别计算hash值（对应位图数组下标），将相应的数组元素设为true；
 *     	    3)判断整数时，通过各个hash函数计算hash值，如果各个hash值对应的数组元素均为true，则存在；否则不存在；
 *     	    4)实际上判断为存在时，可能会有误判（由于存在hash冲突），所以使用布隆过滤器需要对误判有一定的容忍度。可以通过
 *     	      扩容位图数组大小，优化hash函数来降低误判。
 *
 * java实现： java实际上不是用 1bit 表示一个boolean类型数据
 *    (https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.3.4
 *    对于boolean类型数据JVM是按照int处理的；对于boolean数组，每个元素按照byte处理。)
 *    所以不应直接使用boolean数组，可以使用位运算实现
 */
public class BitMap {

	private static final int SHIFT_BITS = 3;
	private static final int MODULUS = 1 << SHIFT_BITS;  //位图数组元素是byte类型，8 bits

	private int scale; // 数据范围
	private byte[] bytes;

	public BitMap(int scale){
		this.scale = scale;
		this.bytes = new byte[(scale >>> SHIFT_BITS) + 1];   // 移位运算优先级低于加法运算
	}

	public void set(int v){
		if(v > scale) return;
		int index = v >>> SHIFT_BITS;
		int bitIndex = v % MODULUS;
		bytes[index] |= (1 << bitIndex);
	}

	public boolean get(int v){
		if(v > scale) return false;
		int index = v >>> SHIFT_BITS;
		int bitIndex = v % MODULUS;
		return (bytes[index] & (1 << bitIndex)) != 0;
	}
}
