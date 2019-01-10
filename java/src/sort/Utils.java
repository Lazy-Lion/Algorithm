package sort;

/**
 * 工具类
 */
public class Utils {

    /**
     * 数组指定index内容交换
     * @param a
     * @param index1
     * @param index2
     */
    public static void swap(int[] a, int index1, int index2){
        if(index1 < 0 || index1 >= a.length || index2 < 0 || index2 > a.length) return;

        if(index1 == index2) return;

        int temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }


    /**
     * 生成指定长度随机整数数组
     * @param len
     * @return
     */
    public static int[] generateArray(int len){
        return generateArray(len, len);
    }

    /**
     * 生成指定长度随机整数数组
     * @param len 指定长度
     * @param range 指定随机整数范围[0,range)
     * @return
     */
    public static int[] generateArray(int len, int range){
        int[] result = new int[len];

        for(int i = 0; i < len; i ++){
            result[i] = (int) ( Math.random() * range );  // 0 - range 随机整数
        }

        return result;
    }
}
