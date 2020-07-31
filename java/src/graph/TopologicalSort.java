package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 拓扑排序：针对有向无环图
 *   In computer science, a topological sort or topological ordering of a directed graph is a linear ordering of its
 * vertices such that for every directed edge uv from vertex u to vertex v, u comes before v in the ordering.
 *
 * 拓扑排序的序列并不一定是唯一的，可能存在多种情况。
 */
public class TopologicalSort {

	private Graph g;  // directed graph

	public TopologicalSort(int n){
		this.g = new Graph(n);
	}

	public void addEdge(int v, int w){
		g.addEdge(v, w);
	}

	/**
	 * Kahn's algorithm:
	 *    1. store each vertex's in-degree in an array
	 *    2. while there are vertex remaining
	 *       1) find a vertex with in-degree zero and output it (if not exists, graph has cyclic)
	 *       2) reduce in-degree of all vertices adjacent to it by one
	 * Time Complexity: O(v + e) -- v: numbers of vertices in this graph
	 *                        e: numbers of edges in this graph
	 *
	 * 如果返回结果中的顶点个数小于图的顶点个数，表明图中存在环
	 */
	public List<Integer> topologicalSort1(){
		List<Integer> result = new ArrayList<>();

		int[] inDegree = new int[g.n];  // incoming degree array, time O(e)
		for(int i = 0; i < g.n; i ++){
			for(int j = 0; j < g.table[i].size(); j ++){
				inDegree[g.table[i].get(j)] ++;
			}
		}

		Queue<Integer> queue = new LinkedList<>();
		for(int i = 0; i < g.n; i ++){   // time O(v)
			if(inDegree[i] == 0)
				queue.add(i);
		}

		while(!queue.isEmpty()){    // time O(e)
			Integer v = queue.poll();
			result.add(v);
			for(int i = 0; i < g.table[v].size(); i ++){
				int w = g.table[v].get(i);
				inDegree[w] --;

				if(inDegree[w] == 0) queue.add(w);
			}
		}
		return result;
	}

	/**
	 * Depth-First traversal
	 * Time Complexity：O(v + e)
	 */
	public List<Integer> topologicalSort2(){
		List<Integer> result = new ArrayList<>();

		LinkedList<Integer>[] inverseAdj = new LinkedList[g.n];
		for(int i = 0; i < g.n; i ++){
			inverseAdj[i] = new LinkedList<>();
		}

		for(int i = 0; i < g.n; i ++){    // construct inverse adjacency list
			for(int j = 0; j < g.table[i].size(); j ++){
				int v = g.table[i].get(j);
				inverseAdj[v].add(i);
			}
		}

		boolean[] visited = new boolean[g.n];
		for(int i = 0; i < g.n; i ++){
			if(!visited[i]) {
				visited[i] = true;
				dfs(i, inverseAdj, visited, result);
			}
		}

		return result;
	}

	private void dfs(int v, LinkedList<Integer>[] inverseAdj, boolean[] visited, List<Integer> result){
		for(int i = 0; i < inverseAdj[v].size(); i ++){  //递归的终止条件实际上是当前节点没有前驱节点，或前驱节点都被访问过
			int w = inverseAdj[v].get(i);

			if(visited[w]) continue;
			visited[w] = true;
			dfs(w, inverseAdj, visited, result);
		}
		result.add(v);   // 前驱节点都输出完成后再输出当前节点
	}



	// definition of a directed graph
	private static class Graph{
		private int n;   // numbers of vertices
		private LinkedList<Integer>[] table; // adjacency list

		public Graph(int n){
			this.n = n;
			table = new LinkedList[n];
			for(int i = 0; i < n; i ++){
				table[i] = new LinkedList<>();
			}
		}

		public void addEdge(int v, int w){
			table[v].add(w);
		}
	}
}


