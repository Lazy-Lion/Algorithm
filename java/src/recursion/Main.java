package recursion;

/**
 * test
 */
public class Main {

    /**
     * 递归需要满足的条件：
     * 1. 一个问题的解可以分解为几个子问题的解
     * 2. 这个问题与分解之后的子问题，除了数据规模不同，求解思路完全一样
     * 3. 存在递归终止条件
     */


    /**
     * 编写递归代码：
     *  1. 写出递归公式
     *  2. 找到终止条件
     */


    /**
     * 递归的调试：
     *  使用打印日志的方式
     */

    /**
     * 不要试图分析递归的每个步骤，只需抽象成递归公式
     *
     * Note:
     *  递归代码要警惕堆栈溢出：1)可以自己实现一个堆上的栈，手动模拟递归; 2)当问题范围小于一定条件时，可以将递归转换成其他迭代方式实现
     *  递归代码要警惕重复计算
     *  递归函数调用耗时多
     *  递归空间复杂度高
     *
     */

    public static void main(String[] args) {
        ClimbingStairs s = new ClimbingStairs();
        System.out.println(s.climbStairs(2));
        System.out.println(s.climbStairs(3));


        System.out.println(s.climbStairsIteration(2));
        System.out.println(s.climbStairsIteration(3));
    }

}
