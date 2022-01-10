
public class B_Tree {

	private int order; // order of B Tree (max no. of children per node)
	private bnode root; // root of B Tree 
	
	public B_Tree(){
	/* Default constructor: sets default order = 4 and root to null reference*/
		order = 4;
		root = null;
	}
	
	public B_Tree(int o){
	/* Alternate constructor: sets order = o and root to null reference*/
		order = o;
		root = null;
	}
	
	public B_Tree(int o, bnode r){
	/* Alternate constructor: sets order = o and root = r*/
		order = o;
		root = r;
	}
	
	
	
	protected class bnode{
	/* Protected inner class defining data structure used for B Tree nodes*/
	
		protected int[] keys; // array of keys for this bnode
		protected bnode[] children; // array of references to child nodes
		protected int num_key; // number of keys for this node (no. children == num_key+1)
		protected boolean leaf; // indicates whether or not bnode is a leaf node
		
		protected bnode(int order){
		/* Default constructor: initializes children array of size order, keys array 
		of size order-1, and sets num_child = 0, leaf = false*/
			keys = new int[order-1];
			children = new bnode[order];
			num_key = 0;
			leaf = false;
		}
		
		protected int search_node(int key){
		/*Returns index i: key <= keys[i], num_key if key > keys[num_key-1], or -1 if num_key == 0; implements 
		iterative version of binary search to achieve goal*/
			if (this.num_key == 0){ // empty bnode
				return -1;
			}
			int l = 0; // beginning of keys array
			int r = this.num_key-1; // last entry of keys array
			int m = 0; // variable for midpoint of subarray keys[l, ..., r] (initialized to 0 for syntax purposes)
			
			while (l < r){
				m = (int)Math.floor((double)(l+r)/2.0); // calculate midpoint
				if(this.keys[m] == key){
					// found search key	
					return m;
				}else if(this.keys[m] < key){
					// key can only be in keys[m+1, ..., r]
					l = m+1; // search right subarray
				}else{
					// key can only be in keys[l, ..., m-1]
					r = m-1; // search left sub-array
				}
			}
			
			if(this.keys[m] >= key){
				return m;
			}  
			return m+1;
		}
	}
	
	public static int BinSearch(int key, int[] A) {
		int l = 0; // beginning of keys array
		int r = A.length-1; // last entry of keys array
		int m = 0; // variable for midpoint of subarray keys[l, ..., r] (initialized to 0 for syntax purposes)
		
		while (l < r){
			m = (int)Math.floor((double)(l+r)/2.0); // calculate midpoint
			if(A[m] == key){
				// found search key	
				return m;
			}else if(A[m] < key){
				// key can only be in keys[m+1, ..., r]
				l = m+1; // search right subarray
			}else{
				// key can only be in keys[l, ..., m-1]
				r = m-1; // search left sub-array
			}
		}
		
		if(A[m] >= key){
			return m;
		}  
		return m+1;
	}
	
	// Client code
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A = {2, 7, 18, 29, 42, 55, 57, 72, 75, 81, 83, 90};
		System.out.print("Return from searching for ");
		
	}

}
