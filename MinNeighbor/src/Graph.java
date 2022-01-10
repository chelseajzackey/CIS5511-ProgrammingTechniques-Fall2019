/*Chelsea Zackey 
 * CIS 5511
 * Assignment 10: Min Neighbor in Graph
 * 12/2/19
 * 
 * Class definition of graph data structure used in solve min neighbor problem.
 * 
*/


import java.util.LinkedList;
import java.util.ListIterator;
import java.lang.Integer;
import java.lang.Math;

public class Graph{
	public int v_no; // |V|
	public Vertex[] V; // array representation of vertex set (edge set contained in fields of each vertex in V)
	
	public Graph(int verts){
	/* Default constructor; creates representation of graph containing verts-many vertices and no edges:
			- sets v_no = verts
			- initializes V as array of vertices of size verts*/
			this.v_no = verts; 
			this.V = new Vertex[verts];
			this.initVertices(); // initialize each entry of V
	}
	
	public Graph(int verts, LinkedList<Edge> E){
	/* Alternate constructor; creates representation of graph with vertices v_1, ..., v_verts and edges determined by 	list E:
			- sets v_no = verts (assumes verts >= 1 for simplicity)
			- initializes V as new Vertex array of size v_no
			- call this.addEdge for all edges in E */
			this.v_no = verts;
			this.V = new Vertex[v_no];
			this.initVertices(); // initialize each entry of V
			// add all edges listed in E to adjacency list contained in V
			while(E.size() != 0){
				Edge e = E.pop();
				this.addEdge(e.from, e.to);
			}
	}
	
	public void initVertices() {
	/* Populates array V[] with initialized vertex entries with proper ID values. */
		for (int i = 0; i < this.v_no; i++){
			V[i] = new Vertex(i+1);
		}
	}
	
	public void addEdge(int src, int dest){
	/* Creates edge in graph connecting vertex with ID "src" to vertex with ID "dest";
		assumes for simplicity that 1 <= src, dest <= this.v_no */
		V[src-1].neighbors.add(Integer.valueOf(dest)); /*  add integer representation of vertex dest at end of source's neighbors list */
	}
	
	/*public Graph transpose(){
	/* Returns transpose graph of this graph object. */	
	//	Graph G_t = new Graph(this.v_no); // initialize new graph to represent transpose of G
		// traverse calling graph to determine which edges to add to transpose G_t
	/*	for (int i = 0; i < this.v_no; i++) {
			ListIterator<Integer> l = V[i].neighbors.listIterator();
			while(l.hasNext()) {
				G_t.addEdge(l.next().intValue(), i+1);
			}
		}
		return G_t;
	}*/
	
	public void printGraph() {
		for(int i = 0; i < this.v_no; i++) {
			V[i].printVertex();
		}
		System.out.println();
	}
	/*public int getVsize(){
	/* Returns size of vertex set V */
	/*	return this.v_no;
	}*/
	
	public static Graph randomGraph(int vrt, int edg) {
	/* Returns randomly generated graph G = (V, E) with |V| = vrt and |E| = edg*/
		Graph G = new Graph(vrt);
		for (int i = 0; i < edg; i++){ // insert randomly generated edges into G
			int x = (int)(Math.random()*(vrt))+1;
			int y = (int)(Math.random()*(vrt))+1;
			G.addEdge(x, y);
		}
		return G;
	}
	
	public class Vertex{
		//boolean visited;
		public int ID; // ID >= 1 for every vertex object
		public LinkedList<Integer> neighbors; // linked list representing neighbors of this vertex
		
		public Vertex(int id){
		/*Default constructor; 
				-set ID = id
				-initialize neighbors as empty linked list */
			this.ID = id;
			this.neighbors = new LinkedList<Integer>();
		}
		
		public Vertex(int id, LinkedList<Integer> n){
		/*Alternate constructor; 
				-set ID = id 
				-set neighbors = n */
			this.ID = id;
			this.neighbors = n;
		}
		
		@Override
		public String toString(){
		/* Overridden toString method; returns string representation of this Edge*/
			return "v_"+this.ID;
		}
		
		public void printVertex() {
		/* Print adjacency list representation of this vertex*/
			System.out.print(this.toString()+": ");
			if (this.neighbors.size()!=0) {
				ListIterator<Integer> l = this.neighbors.listIterator();
				while (l.hasNext()){
					System.out.print("v_"+l.next().toString()+" ");
				}
			}
			System.out.print("\n");
		}
	}
	
	public class Edge{
		int from; // source vertex 
		int to; // destination vertex
		
		public Edge(int s, int d){
		/*Default constructor; 
				-set from = s (source), to = d (dest)
				- assumes s, d >= 1 for simplicity */
			this.from = s;
			this.to = d;
		}
		
		@Override
		public String toString(){
		/* Overridden toString method; returns string representation of this Edge*/
			return "("+this.from+" , "+this.to+")";
		}
	}
}