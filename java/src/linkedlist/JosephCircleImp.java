package linkedlist;

/**
 * 约瑟夫环
 * question: n个人（编号0~(n-1))，从0开始报数，报到(m-1)的退出，剩下的人继续从0开始报数。求胜利者的编号。
 */
public class JosephCircleImp {


    /**
     * 模拟约瑟夫环思路
     * 时间复杂度 O(m*n)，复杂度较高
     * @param n 总数
     * @param m 跳过的数字
     * @return 胜利者编号
     */
    public static int simulate(int n, int m){
        Node starter = createJosephCircle(n);

        for(int i = 0; i < n - 1; i ++){ // 执行次数，胜者只有一个
            Node node = starter;
            for(int j = 1; j < m - 1; j ++){
                node = node.next;
            }
            node.next = node.next.next;
            starter = node.next;
        }

        return starter.getData();
    }


    /**
     * 数学方法
     * 0 1 2 3 ... m-1 m ... n-1    (n阶序列，m-1出列)
     * m m+1 ...  n-1 0  ... m-2  (新的 n-1 序列)
     * 0 1 2  ... n-m-1 ... m'-1 m' ... n-2 (重新编号)
     * f(x,m) 表示问题规模为 x 时的下标编号：f(x,m) = (f(x-1) + m) mod x
     * 且  f(1,m) 为最终的下标序列，只有 1 个结果且为 0
     * @param n 问题规模
     * @param m m > 0
     * @return 胜利者index
     */
    public static int josephCircle(int n, int m){

        if(m <= 0) return -1;

        int result = 0;
        for(int i = 2; i <= n; i ++){
            result = (result + m) % i;
        }

        return result;
    }


    /**
     * 递归实现
     */
    public static int josephCircleByRecursion(int n, int m){
        if(m < 1) return -1;
        if(n == 1) return 0;

        return (josephCircleByRecursion(n - 1, m) + m) % n;
    }

    /**
     * 创建约瑟夫环，编号从0开始
     * @param n
     * @return
     */
    private static Node createJosephCircle(int n){

        if( n <= 0 ) return null;

        Node head = new Node(0, null);
        Node current = head;

        for(int i = 1; i < n; i ++ ){
            Node node = new Node(i,null);
            current.next = node;
            current = node;
        }

        current.next = head;

        return head;
    }

}
