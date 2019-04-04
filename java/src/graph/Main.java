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


		System.out.println("Dijkstra Algorithm");
		Dijkstra d = new Dijkstra(7);
		d.addEdeg(0,1,6);
		d.addEdeg(0,3,1);
		d.addEdeg(1,4,1);
		d.addEdeg(2,0,2);
		d.addEdeg(2,3,3);
		d.addEdeg(3,4,4);
		d.addEdeg(5,4,2);
		d.addEdeg(6,5,6);
		d.addEdeg(6,2,2);
		d.addEdeg(6,0,3);
		System.out.println(d.dijkstra(6,4));
	}
}
