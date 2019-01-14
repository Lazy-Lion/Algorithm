package search;

import java.util.Random;

/**
 * Skip list : 跳表
 *       核心思想：对于有序单链表的查找，需要从头到尾遍历，时间复杂度为O(n)
 *          可以对链表建立多级索引：
 *
 *           顶层索引个数:  2
 *
 *           第k级索引个数: n/(2^k)
 *
 *           二级索引 1   →   5   →   9    →     13    →     17
 *                   ↓       ↓       ↓          ↓           ↓
 *          一级索引  1 → 3 → 5 → 7 → 9 →  11 →  13 →  15 →  17 →  19
 *                   ↓   ↓   ↓   ↓   ↓    ↓     ↓     ↓     ↓     ↓
 *          数据     1→2→3→4→5→6→7→8→9→10→11→12→13→14→15→16→17→18→19→20
 *
 *
 * 时间复杂度: 查找时间复杂度：索引层级和数据高度：logn; 每层遍历次数：如每隔一个数据建立一层索引，到下一层时遍历次数不超过3次；
 *                    ==> T(n) = O(logn)
 *            插入时间复杂度：T(n) = O(logn), 链表插入时间复杂度为O(1)，但是需要先找到位置
 *            删除时间复杂度: T(n) = O(logn)
 *
 * 空间复杂度: n/2 + n/4  + n/8 + ... + 2 = n - 2 ==> O(n)
 */
public class SkipList {

    /**
     * 跳表实现：
     *   assume: 数据为正整数，不存在重复数据
     */

    private static final int MAX_LEVEL = 16;

    private int levelCount = 1;             // 目前表内最大层数
    private SkipNode head = new SkipNode();
    private Random r = new Random();

    public SkipNode find(int value){

        SkipNode p = head;

        for(int i = levelCount - 1; i >= 0; i --){
            if(p.forwards[i] != null && p.forwards[i].data < value)
                p = p.forwards[i];
        }

        if(p.forwards[0] != null && p.forwards[0].data == value)
            return p.forwards[0];

        return null;
    }

    public void insert(int value){
        int level = randomLevel();
        SkipNode newNode = new SkipNode();
        newNode.data = value;
        newNode.maxLevel = level;

        SkipNode[] update = new SkipNode[level];

        for(int i = 0; i < level; i ++){
            update[i] = head;
        }

        //寻找value插入的位置
        SkipNode p = head;
        for(int i = level - 1; i >= 0; i --){
            while(p.forwards[i] != null && p.forwards[i].data < value){
                p = p.forwards[i];
            }
            update[i] = p;
        }

        for(int i = 0; i < level; i ++){
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }

        if(levelCount < level) levelCount = level;
    }

    public void delete(int value){

    }


    // 随机生成节点的层数
    private int randomLevel(){
        int level = 1;

        for(int i = 1; i < MAX_LEVEL; i ++){
            if(r.nextInt() % 2 == 1)
                level ++;
        }

        return level;
    }


    /**
     * 跳表链表节点
     */
    class SkipNode{
        private int data = -1;
        private SkipNode[] forwards = new SkipNode[MAX_LEVEL];
        private int maxLevel = 0;
    }
}
