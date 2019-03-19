package graph;

/**
 * test
 */
public class Main {
	public static void main(String[] args){
		System.out.println("拓扑排序:");
		TopologicalSort t = new TopologicalSort(5);
		t.addEdge(0,1);
		t.addEdge(0,4);
		t.addEdge(0,3);
		t.addEdge(1,2);
		t.addEdge(2,3);
		t.addEdge(4,1);

		System.out.println(t.topologicalSort1());
		System.out.println(t.topologicalSort2());
	}
}
