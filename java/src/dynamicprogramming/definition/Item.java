package dynamicprogramming.definition;

public class Item {
    public int c;  // 物品消耗容量
    public int w;  // 物品价值
    public int m;  // 物品个数

    public Item(int c, int w) {
        this.c = c;
        this.w = w;
    }

    public Item(int c, int w, int m) {
        this(c, w);
        this.m = m;
    }
}
