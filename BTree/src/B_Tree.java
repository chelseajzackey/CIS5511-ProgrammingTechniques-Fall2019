/* Chelsea Zackey
 * 11/6/19
 * CIS 5511
 * Lab 07
 * 
 * 
 * 
 */

public class B_Tree {
	
	private int order;
	private btnode root;
	private int size;
	
	public B_Tree() { // default constructor creates empty tree with null root
		order = 3; // default min order
		root = null; // null root
		size = 0; // empty tree
	}
	
	public B_Tree(int o) { // alternate constructor
		order = o;
	}
	// CLASS METHODS
	
	public void BT_insert(int key) {
		// search for proper position of key in tree
		btnode p = this.root; // node ptr for tree traversal
		int i = binSearch(p.keys, k, 0, p.num_keys-1);
		while (!p.leaf && p.keys[i] != key) {
		// if p.keys[i] != key, then key is in p.child[i].keys[] if key is in tree
			p = p.children[i]; 
			i = binSearch(p.keys, k, 0, p.num_keys-1);
		} // either p is leaf or p.keys[i] == key
		if (p.leaf && p.keys[i] != key) {
			p.num_keys++;
			if (p.num_keys < p.order-1) { // leaf node not full; can support insertion
				
			} else { // split leaf node into two children; pass median key up to parent
				
			}
		}
	}
	// UTILITY METHODS
	
	public btnode BT_search(int key) { // given key value "key", returns node in B_Tree where key would be found if key exists in tree 
		btnode p = this.root; // node ptr for tree traversal
		int i = binSearch(p.keys, key, 0, p.num_keys-1); // return i: p.keys[i] >= key
		while (!p.leaf && p.keys[i] != key) {
		// if p.keys[i] != key, then p.keys[i] > key; search keys in p.child[i]
			if (p.keys[i] < key) { // occurs when 
				i++; 
			}
			p = p.children[i]; 
			i = binSearch(p.keys, key, 0, p.num_keys-1);
		} // either p is leaf or p.keys[]
		return p;
	}
	
	public int node_search(int k, btnode p) {  // given key value "k", returns index in key array of node p where k should be if k is in p.keys[]
		if (p != null) {
			int i = binSearch(p.keys, k, 0, p.num_keys-1); // find index i such that p.keys[i] >= k;
		}
		return -1; // return invalid index if p == null
	}
	
	private static int binSearch(int A[], int key, int l, int r) {
		//performs binary search on sorted (increasing order) integer (sub)array A[l, ..., r];
		//Returns index i such that A[i] >= key (A[i] == key if key is in array); else, returns r+1 when A[r] < key
		if (A[r] < key) {
			return r+1;
		}
		while(l < r) {
			int m = (int)Math.floor((l+r)/2); // find midpoint
			if (A[m] >= key) { // key in A[l, ..., m]
				r = m; // search A[l, ..., m]
			}else { // key in A[m+1, ..., r]
				l = m+1; // search A[m+1, ..., r]
			}
		}
		return l;
	}
	
	private void insert_key(int k, int i, btnode p) { // inserts key k in index i of p.keys[]
		if (p!= null && i < p.num_keys) {
			
		}
	}
	
	private void insert_child(btnode c, int i, btnode p) { // inserts node reference c in index i of p.children[]
		if (p!=null && i < p.order) {
			if (p.children[i] != null) { // shift entries p.child[i, ..., num_keys] over one
				
			}
		}
	}
	
	private class btnode{
		private int order, num_keys;
		private btnode par;
		private int[] keys;
		private btnode[] children;
		private boolean leaf;
		
		private btnode() { // default btnode constructor
			order = 3; // default min order
			par = null;
			children = new btnode[order]; // initialize child array
			keys = new int[order-1]; // initialize keys array 
			num_keys = 0; // no keys in array
			leaf = false; // assumed non-leaf node
		}
		
		private btnode(int ord, int num, btnode p, int[] k, btnode[] c, boolean l) { // alternate constructor
			order = ord; // set order
			leaf = l; // set leaf status
			par = p; // parent node
			children = new btnode[order]; // initialize child array
			keys = new int[order-1]; // initialize keys array 
			num_keys = num; //set no. keys
		}
		
		@Override
		public String toString() {
			String n = "[ | ";
			for (int i = 0; i < this.num_keys; i++) {
				n = n + this.keys[i]+" | ";
			}
			return n+"]";
		}
		
	}
	
}
