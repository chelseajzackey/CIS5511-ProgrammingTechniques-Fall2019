/* Chelsea Zackey
 * CIS 5511
 * Lab 06: AVL Tree
 * 10/30/2019
 * 
 * 
 * AVL Tree class extending binary search tree class from Lab 05. 
 * 
 */
public class AVL_tree extends BST{
	private anode root;

	public AVL_tree() { // default constructor
		root = null;
		size = 0;
	}

	public AVL_tree(String word) { // alternate constructor; sets root.key = word, left = right = null, size = 1
		root = new anode(word, null, null, null, 1);
		size = 1;
	}
	
	@Override
	public void tree_insert(String word) { // inserts new occurrence of word in tree
		anode p = this.root; // pointer used to traverse tree
		anode pred = null; // pointer used to track parent of p
		while (p != null && !p.key.equals(word)) { // Traverse tree until we get to proper point of insertion
			pred = p; // store value of p before visiting children
			if (p.key.compareToIgnoreCase(word) > 0) { // word < p.key; set p = p.left
				p = p.left;
			} else { // p.key < word; set p = p.right
				p = p.right;
			}
		} // end-loop; pred == desired parent node of new word according to BST structure

		if (p == null) { // word not in tree; insert new node w/ key value == word
			System.out.println("\tInserting word \'"+word+"\'");
			anode new_word = new anode(word, null, null, null, 1);
			if (pred == null) { // tree was empty
				this.root = new_word; // set new_word as root
			} else if (pred.key.compareToIgnoreCase(word) > 0) { // word < pred.key; set new_word = left child of pred
				pred.left = new_word;
				new_word.par = pred; // reconnect to parent
			} else { // pred.key < word; set new_word = left child of pred
				pred.right = new_word;
				new_word.par = pred; // reconnect to parent
			}
			this.size++; // increase count to account for new node in tree
			
			// reset height variables of all nodes in ancestral path ending at new_word traveling up to root
			this.reset_height(new_word);
			// check to see if any node along insertion path has become imbalanced
			anode q = this.deepest_imbalance(new_word);
			if(q != null) { // q is deepest imbalanced node along insertion path; perform rebalancing maintenance
				// rebalance tree at node q
				System.out.println("\tDeepest imbalanced node: "+q.toString());
				this.rebalance(q);
				this.reset_height(q); // reset heights of ancestors of q after rebalancing
			}else {
				System.out.println("\tHooray! Tree is balanced.");
			}
		} // else do nothing
	}

	@Override	
	public int tree_delete(String word) { // searches tree for node with key value "word"; deletes one occurrence of word if found in tree and returns 1; else, returns 0
		System.out.println("\tAttempting to delete word \'"+word+"\'...");
		anode p = this.root; // ptr used to traverse tree while searching for first possible occurrence of word in tree
		// search for node containing word in tree
		while (p != null && !p.key.equals(word)) { // traverse tree until first occurrence of word found
			if (p.key.compareToIgnoreCase(word) > 0) { // word < p.key
				p = p.left; // set p = p.left
			} else { // p.key <= word
				p = p.right; // set p = p.right
			}
		} // end loop; either p == null or p.key == word
		if (p == null) { // word not in tree
			//System.out.println("\tWord \'"+word+"\' not found in tree.");
			return 0;
		} // else, word is in tree
		
		anode s = p.par; // create extra pointer as placeholder for last point of this deletion path
		
		// initiate deletion process for each special case
		if (p.left == null) { // case when either p has no children (transplanting p.right is transplanting null ptr) or single right child
			this.tree_transplant(p, p.right); // connect root of right subtree (or null ptr) to p.par.right
		}else if (p.right == null) { // case when p has single left child
			this.tree_transplant(p, p.left); // connect root of left subtree to p.par.left
		}else { // case when p has both children --> replace with successor node (in this case, min of right subtree since right subtree exists)
			anode succ = this.tree_min(p.right); // successor of p
			s = succ.par; // change position of s to check for imbalances in p.right after transplanting
			if(succ.par != p) { // successor is not child of p; "rotate" subtree w root p.right accordingly
				this.tree_transplant(succ, succ.right); // connect succ.right to former position of succ
				succ.right = p.right; // connect root of p's right subtree to succ.right before replacing 
				succ.right.par = succ; // reconnect succ as parent of succ.right (now p.right)
			}
			// transplant s into position of p
			this.tree_transplant(p, succ);
			succ.left = p.left; // link p's left child as succ.left
			p.left.par = succ; // connect succ as parent of p's left child
		}
		this.size--; // decrement size of tree to account for loss of node
		
		// reset height variables of all nodes in ancestral path ending at new_word traveling up to root
		this.reset_height(s);
		// check to see if any node along insertion path has become imbalanced
		anode q = this.deepest_imbalance(s);
		if(q != null) { // q is deepest imbalanced node along insertion path; perform rebalancing maintenance
			System.out.println("\tDeepest imbalanced node: "+q.toString());
			// rebalance tree at node q
			this.rebalance(q);
			this.reset_height(q); // reset heights of ancestors of q after rebalancing
		}else {
			System.out.println("\tHooray! Tree is balanced.");
		}
		return 1;
	}

	
	@Override
	public int tree_search(String word) { // iterative implementation of tree search; returns 1 if word is found in BST, else 0.
		anode p = root; // pointer for traversal
		while (p != null && !p.key.equals(word)) { // p traverses tree until it points to first occurrence of word
			if (p.key.compareToIgnoreCase(word) > 0) { // word < p.key; set p = p.left
				p = p.left;
			} else { // p.key < word; set p = p.right
				p = p.right;
			}
		} // end while, either p == null or p.key == word
		if (p != null) { // found word in tree
			return 1;
		} // word not found
		return 0;
	}

	@Override
	public void tree_walk() {// print words in tree in alphabetical order via in-order traversal of the node in the tree
		// store key vals in tree in array for printing via an in-order traversal 
		String[] words = new String[this.size]; // initialize array to store keys
		toArray_inOrder(this.root, words, 0); // convert tree into in-order array representation
		if(words[0] == null) { // empty tree
			System.out.println("Empty tree.");
		}else { // call method to print array of strings
			printArr(words, words.length);
		}
	}
	
	@Override
	public void tree_print() { // print structural representation of tree; accomplishes this by printing the tree horizontally and using counters to determine 
		if (this.size > 0) {
			// level of tree each node resides in
			// strategy: for each line, print next right-most node in tree that hasn't already been printed;
			anode p = this.tree_max(this.root); // begin with maximum node in tree
			int level = this.level(p); // stores level of tree that p resides in to print proper amount of spaces before printing p
			if (p != null) { // non-empty tree
				while (p != null) {
					level = this.level(p); // find level of p
					print_spaces(level); // print spaces indicating level of tree
					// determine whether p is left or right child of parent to decide printing format
					if (p.par != null) { // print format for non-root node
						if (p == p.par.left) { // print format for left children
							System.out.println("\\_ " + p.toString());
						} else { // print format for right children
							System.out.println("/- " + p.toString());
						}
					} else { // print root 
						System.out.println(p.toString());
					}

					p = this.tree_predecessor(p); // next right-most node is predecessor of p
				}
			} 
		}else { // empty tree
			System.out.println("Empty tree.");
			
		}
		
	}
	
	
	// UTILITY METHODS
	public anode get_root() {
		return this.root;
	}
	
	public anode get_left(anode p) {
		return p.left;
	}
	
	public anode get_right(anode p) {
		return p.right;
	}
	
	public int get_height(anode p) {
		return p.height;
	}
	
	protected int balance(anode p) { // returns balance factor of a given node (L.height-R.height), where L is root of left subtree and R is root of right subtree
		int h_l = 0; // stores height of left subtree
		int h_r = 0; // stores height of right subtree
		if (p.left != null) {
			h_l = p.left.height;
		}
		if (p.right != null) {
			h_r = p.right.height;
		}
		return h_l-h_r;
	}
	
	protected void rebalance(anode p) { // void method for rebalancing this AVL tree at node p;
		// first determines what case imbalance falls under, then performs necessary rotation sequence for each case
		
		// determine imbalance case
		int bal = this.balance(p); // calculate balance factor of p
		
		// see if p is left heavy or right heavy
		switch(bal) {
			case 2: // p is left heavy
				// determine if p.left is left or right heavy
				switch(this.balance(p.left)) {
					case 0: // p.left balance; treat as LL case 
						// perform single right rotation at node p
						this.right_rotate(p);
						break;
					case 1: // p.left is left heavy; LL CASE
						// perform single right rotation at node p
						this.right_rotate(p);
						break;
					case -1: // p.left is right heavy; LR CASE
						// perform left rotation at p.left 
						this.left_rotate(p.left);
						// follow with right rotation at p
						this.right_rotate(p);
						break;
					default: // default case should never be evaluated due to our assumptions that |p's balance factor| <= 2 for AVL tree
						break;
				}
				break;
				
			case -2: // p is right heavy
				// determine if p.right is left or right heavy
				switch(this.balance(p.right)) {
					case 0: // p.right is balanced, treat as RR case
						// perform single left rotation at node p
						this.left_rotate(p);
						break;
					case 1: // p.right is left heavy; RL CASE
						// perform right rotation at p.right 
						this.right_rotate(p.right);
						// follow with left rotation at p
						this.left_rotate(p);
						break;
					case -1: // p.right is right heavy; RR CASE
						// perform single left rotation at node p
						this.left_rotate(p);
						break;
					default: // default case should never be evaluated due to our assumptions that |p's balance factor| <= 2 for AVL tree
						break;
				}
				break;
			default: // default case should never be evaluated due to our assumptions that |p's balance factor| <= 2 for AVL tree
				break;
		}
	}
	
	protected boolean valid(int bal) { // checks whether balance factor bal is valid for AVL tree; returns true if |bal| <= 1 
		if (Math.abs(bal) <= 1) {
			return true;
		}
		return false;
	}
	
	protected anode deepest_imbalance(anode p) { // assuming p is a point of insertion or deletion, find the node within the insertion/deletion path
		// with an invalid balance factor this is farthest from the root
		if (p != null) { 
			anode q = p;
			while (q != null && this.valid(this.balance(q))) { // climb up the insertion/deletion path until either q has invalid balance factor
				// or q == this.root
				q = q.par; // travel up path
			} // end loop, either q is imbalanced or we have reached root with no imbalances
			return q;
		}
		return p;
	}
	
	protected void left_rotate(anode x) { // performs a left rotation of this AVL tree at node x
		anode y = x.right; // right child of x to replace position of x after rotation
		x.right = y.left; // set x's right subtree to y's left subtree
		if (y.left != null) { // avoid null ptr exception when resetting parent field of y.left
			y.left.par = x; // connect root of y's left subtree to parent x
		}
		y.par = x.par; // reset y's parent to x's parent in preparation for replacing x with y
		// check x's former relation to parent; reset y's position in tree accordingly
		if (x.par == null) { // x is root
			this.root = y; // y is new root
		}else if (x.par.left == x) { // x is left child
			x.par.left = y; // y is new left child
		}else { // x is right child
			x.par.right = y; // y is new right child
		}
		y.left = x; // x becomes new left child of y
		x.par = y; // y becomes new parent of x
		// reset heights of y and x
		x.height = this.height(x);
		y.height = this.height(y);
		
	}
	
	protected void right_rotate(anode x) { // performs a right rotation of this AVL tree at node x
		anode y = x.left; // left child of x to replace position of x after rotation
		x.left = y.right; // set x's left subtree to y's right subtree
		if (y.right != null) { // avoid null ptr exception when resetting parent field of y.right
			y.right.par = x; // connect root of y's left subtree to parent x
		}
		y.par = x.par; // reset y's parent to x's parent in preparation for replacing x with y
		// check x's former relation to parent; reset y's position in tree accordingly
		if (x.par == null) { // x is root
			this.root = y; // y is new root
		}else if (x.par.left == x) { // x is left child
			x.par.left = y; // y is new left child
		}else { // x is right child
			x.par.right = y; // y is new right child
		}
		y.right = x; // x becomes new right child of y
		x.par = y; // y becomes new parent of x
		// reset heights of y and x
		x.height = this.height(x);
		y.height = this.height(y);
	}
	
	protected void tree_transplant(anode before, anode after) { // replaces subtree with root before with subtree with root after by connecting after to before.par
		if(before.par == null) { // before is root of BST
			this.root = after; // after becomes new root of tree
		}else if (before == before.par.left) { // before was left child of its parent
			before.par.left = after; // after becomes left child
		}else {
			before.par.right = after; // after becomes right child
		}
		if (after != null) { // avoid null pointer exception before attempting to update after.par
			after.par = before.par;
		}
	}
	
	// not used
		/*public anode tree_successor(anode n) { // returns pointer to in-order successor of node n in tree
			if (n.right != null) { // n has right subtree; successor is in-order minimum node in right subtree
				return tree_min(n.right);
			}
			// else, successor is first ancestor node pred of n such that n is in left subtree of pred
			anode p = n;  // node ptr used to traverse ancestors of n
			anode pred = p.par;
			while(pred != null && p == pred.right) { // travel up tree until p is in left subtree of its parent
				p = pred; // p becomes parent
				pred = pred.par; // pred becomes new parent (grandparent)
			}
			return pred;
		}*/
		
		
		public anode tree_predecessor(anode n) { // returns pointer to in-order predecessor of node n in tree
			if (n.left != null) { // n has left subtree; predecessor is in-order maximum node in left subtree
				return tree_max(n.left);
			}
			// else, predecessor is first ancestor node pred of n such that n is in right subtree of pred
			anode p = n;  // node ptr used to traverse ancestors of n
			anode pred = p.par;
			while(pred != null && p == pred.left) { // travel up tree until p is in right subtree of its parent
				p = pred; // p becomes parent
				pred = pred.par; // pred becomes new parent (grandparent)
			}
			return pred;
		}
		
		protected anode tree_min(anode n) { // returns ptr to node in subtree with root n that has minimum key value, i.e. left-most node in subtree
			anode p = n; // node ptr used to traverse subtree with root n
			while(p.left != null) { // travel to right-most node in subtree
				p = p.left;
			}
			return p;
		}
		
		
		public anode tree_max(anode n) { // returns ptr to node in subtree with root n that has max key value, i.e. right-most node in subtree
			anode p = n; // node ptr used to traverse subtree with root n
			while(p.right != null) { // travel to right-most node in subtree
				p = p.right;
			}
			return p;
		}
		
		protected int level(anode p) { // returns level of tree node p resides in by traversing path from p to this.root
			int level = 0; // return value
			anode pred = p; // ptr 
			while(pred != this.root) { // traverse path from p to this.root, incrementing level each time
				level++;
				pred = pred.par;
			}
			return level;
		}
		
		
		public int height(anode p) { //returns height of subtree with root p
			if (p!=null) { // avoid null ptr exception
				int h_l = 0; // height of left subtree
				int h_r = 0;// height of right subtree
				if (p.left != null) { // avoid null ptr exception
					h_l = p.left.height; // store height of left subtree
				}
				if (p.right != null) { // avoid null ptr exception
					h_r = p.right.height; // store height of right subtree 
				}
				return Math.max(h_l, h_r) + 1;
			}
			return 0;
		}
		
		protected void reset_height(anode p) { //resets height of all nodes in subtree with root p by recursively resetting heights of left & right subtrees following post-order traversal
			if (p!=null) {
				p.height = this.height(p);
				this.reset_height(p.par);
			}
		}
		
		protected static int toArray_inOrder(anode p, String[] words, int i) { // uses in-order traversal (using ptr to subtree root node p) to store key values of each node in array words[] to demonstrate
			// parent-child relations when printing; add'l parameter i is used to to track current index of words[] we are visiting
			// returns no. nodes stored in words[] after return from recursive call
			int nodes = 0; // return value
			if (p != null) { // avoid null ptr exception
				nodes = toArray_inOrder(p.left, words, i); // store left subtree in-order first
				words[nodes+i] = p.toString(); // visit root node
				nodes++; // increment nodes by one
				nodes = nodes + toArray_inOrder(p.right, words, nodes+i); // visit right subtree
			}
			return nodes;
		}
	
	protected class anode extends BST_node{
		int height; // field for height of subtree with this node as a root
		anode par;
		anode left;
		anode right;
		
		protected anode() { // default constructor; calls parent default constructor and sets height = 0
			super();
			height = 0;
		}
		
		protected anode(String k, anode p, anode l, anode r, int h) { // alternate constructor; calls parent alternate constructor and sets height = 0
			super(k, p, l, r);
			height = h;
		}
	}
}
