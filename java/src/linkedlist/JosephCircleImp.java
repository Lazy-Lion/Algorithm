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
    public int simulate(int n, int m){
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
     * @param n
     * @param m
     * @return
     */
    public int josephCircle(int n, int m){
        return 0;
    }

    /**
     * 创建约瑟夫环，编号从0开始
     * @param n
     * @return
     */
    private Node createJosephCircle(int n){

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
