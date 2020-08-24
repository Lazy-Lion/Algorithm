package dynamicprogramming.definition;

public class Item {
    public int cost;  // 物品消耗容量
    public int weight;  // 物品价值
    public int count;  // 物品数量

    public Item(int cost, int weight) {
        this.cost = cost;
        this.weight = weight;
    }

    public Item(int cost, int weight, int count) {
        this(cost, weight);
        this.count = count;
    }
}
