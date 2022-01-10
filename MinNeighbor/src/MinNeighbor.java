/*Chelsea Zackey 
 * CIS 5511
 * Assignment 10: Min Neighbor in Graph
 * 12/2/19
 * 
 * 
 * Class definition of data structure used to solve min neighbor problem for a given graph G.
 * Main program outputs sample data from solving 10 different problem instances defined by randomly
 * generated graph objects.
 * 
*/
import java.util.LinkedList;
import java.util.ListIterator;
import java.lang.Integer;

public class MinNeighbor {

	private Graph G; // input graph to solve Min Neighbor problem for 
	private boolean[] visited; // array of booleans indicating processing status for vertices of G (entry i-1 corresponds to vertex v_i)
	private int[] soln; // integer array containing solution to Min Neighbor problem for this graph G (entry at index i-1 is ID of v_i's min neighbor)
	private int v_a, e_a; // performance indicators for vertex and edge access operations, respectively
	
	public MinNeighbor(Graph g){
	/* Default constructor */
		this.G = g;
		this.visited = new boolean[G.v_no];
		this.soln = new int[G.v_no];
		v_a = e_a = 0; 
	}
	
	public int[] solve() {
	/* Returns integer array representing solution to Min Neighbor problem for this graph G*/	
		// visited array already initialized to all false values 
		// set min neighbor of every vertex in G as itself
		for(int i = 0; i < this.G.v_no; i++) {
			this.soln[i] = i+1;
			v_a++;
		}
		
		Graph G_t = this.transpose(this.G); // calculate transpose graph of G
		/*System.out.println("Printing transpose graph of G: ");
		G_t.printGraph();*/
		
		// run DFS on G_t
		for (int i = 1; i <= this.G.v_no; i++) {
			if (!visited[i-1]){
				// explore vertex v_i
				visited[i-1] = true;
				this.DFS_explore(G_t, i);
			}
			v_a++;
		}
		return this.soln;
	}
	
	/*public void DFS_explore_recursive(Graph gr, int v) {
		visited[v-1] = true;
		while(gr.V[v-1].neighbors.size()!=0) { // traverse adjacency list of vertex v 
			int u = gr.V[v-1].neighbors.pop().intValue();
			if (soln[v-1] < soln[u-1]){
				soln[u-1] = soln[v-1];
			}
			if (!visited[u-1]) {
				this.DFS_explore(gr, u);
			}
			e_a++;
		}
		v_a++;
	}*/
	
	public void DFS_explore(Graph gr, int v) {
	/* Iterative implementation of DFS explore routine. */
		//visited[v-1] = true;
		LinkedList<Integer> S = new LinkedList<Integer>(); // stack used for iterative implementation of DFS
		S.push(Integer.valueOf(v)); // push v onto S
		while(S.size()!=0) { // while S not empty 
			int u = S.pop().intValue(); // process next node in stack
			if(gr.V[u-1].neighbors.size()!=0) { // traverse Adj[u]
				ListIterator<Integer> l = gr.V[u-1].neighbors.listIterator();
				while(l.hasNext()) {
					int w = l.next().intValue();
					if (soln[u-1] < soln[w-1]){
						soln[w-1] = soln[u-1];
					}
					if (!visited[w-1]) {
						visited[w-1] = true;
						S.push(Integer.valueOf(w));
					}
					e_a++;
				}
			}
			v_a++; // end of processing (exploring) node u 
		}
	}
	
	public Graph transpose(Graph G){
	/* Returns transpose graph of G. */	
			Graph G_t = new Graph(G.v_no); // initialize new graph to represent transpose of G
			// traverse calling graph to determine which edges to add to transpose G_t
			for (int i = 0; i < G.v_no; i++) {
				v_a++;
				ListIterator<Integer> l = G.V[i].neighbors.listIterator();
				while(l.hasNext()) {
					G_t.addEdge(l.next().intValue(), i+1);
					e_a++;
				}
			}
			return G_t;
	}
	
	public int[] return_stats() {
	/* Returns number of vertex and edge accesses made for this MinNeighbor problem. */
		int[] stats = {v_a, e_a};
		return stats;
	}
	public static void main(String[] args) {
		/*Graph G = new Graph(5);
		G.addEdge(1,  4);
		G.addEdge(1,  3);
		G.addEdge(2,  3);
		G.addEdge(3,  1);
		G.addEdge(3,  5);
		G.addEdge(5,  2);
		G.addEdge(5,  4);
		/*Graph G = new Graph(8);
		G.addEdge(1, 8);
		G.addEdge(2, 8);
		//G.addEdge(3, 8);
		G.addEdge(5, 2);
		//G.addEdge(4, 1);
		G.addEdge(5, 4);
		G.addEdge(3, 5);
		G.addEdge(7, 3);
		G.addEdge(6, 2);
		//G.addEdge(2, 1);
		G.addEdge(8, 3);*/
		/*System.out.println("Printing graph G: ");
		G.printGraph();
		MinNeighbor X = new MinNeighbor(G);
		int[] solved = X.solve();
		System.out.println("Printing minimum neighbor solution for graph G: ");
		print_soln(solved);*/
		
		for (int i = 1; i <= 5; i++) {
			Graph G_1 = Graph.randomGraph(i*5, (i+1)*5);
			Graph G_2 = Graph.randomGraph((i+1)*5, i*5);
			MinNeighbor M1 = new MinNeighbor(G_1);
			MinNeighbor M2 = new MinNeighbor(G_2);
			
			// Solve for G_1
			System.out.println("Printing graph G (|V| = "+i*5+", |E| = "+(i+1)*5+"): ");
			G_1.printGraph();
			int[] s1 = M1.solve();
			int[] stats1 = M1.return_stats();
			System.out.println("Printing minimum neighbor solution for graph G: ");
			print_soln(s1);
			System.out.println();
			print_stats(stats1);
			System.out.println();
			
			// Solve for G_2
			System.out.println("Printing graph G (|V| = "+(i+1)*5+", |E| = "+i*5+"): ");
			G_2.printGraph();
			int[] s2 = M2.solve();
			int[] stats2 = M2.return_stats();
			System.out.println("Printing minimum neighbor solution for graph G: ");
			print_soln(s2);
			System.out.println();
			print_stats(stats2);
			System.out.println();
		}

	}
	

	public static void print_stats(int[] stats){
		System.out.println("Vertex accesses: "+stats[0]+"\tEdge accesses: "+stats[1]);
	}
	public static void print_soln(int[] sol) {
		for (int i = 0; i < sol.length; i++) {
			System.out.print("v_"+(i+1)+": "+(sol[i])+"\n");
		}
	}

}
