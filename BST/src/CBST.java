
public class CBST extends BST {

	private BST_cnode root;

	public CBST() { // default constructor
		root = null;
		size = 0;
	}

	public CBST(String word) { // alternate constructor; sets root.key = word, left = right = null, size = 1
		root = new BST_cnode(word, null, null, null);
		size = 1;
	}

	
	// CLASS METHODS
	
	@Override
	public void tree_insert(String word) { // inserts new occurrence of word in tree
		BST_cnode p = this.root; // pointer used to traverse tree
		BST_cnode pred = null; // pointer used to track parent of p
		while (p != null && !p.key.equals(word)) { // Traverse tree until we get to proper point of insertion
			pred = p; // store value of p before visiting children
			if (p.key.compareToIgnoreCase(word) > 0) { // word < p.key; set p = p.left
				p = p.left;
			} else { // p.key < word; set p = p.right
				p = p.right;
			}
		} // end-loop; pred == desired parent node of new word according to BST structure

		if (p != null) { // word is already in tree; increase occurrence count of node
			p.count++;
		} else { // word not in tree; create new node
			BST_cnode new_word = new BST_cnode(word, null, null, null);
			if (pred == null) { // tree was empty
				root = new_word; // change key value of root to word
			} else if (pred.key.compareToIgnoreCase(word) > 0) { // word < pred.key; set new_word = left child of pred
				pred.left = new_word;
				new_word.par = pred; // reconnect to parent
			} else { // pred.key < word; set new_word = left child of pred
				pred.right = new_word;
				new_word.par = pred; // reconnect to parent
			}
			this.size++; // increase count to account for new node in tree
		}
	}

	@Override	
	public int tree_delete(String word) { // searches tree for node with key value "word"; deletes one occurrence of word if found in tree and returns 1; else, returns 0
		BST_cnode p = this.root; // ptr used to traverse tree while searching for first possible occurrence of word in tree
		// search for node containing word in tree
		while (p != null && !p.key.equals(word)) { // traverse tree until first occurrence of word found
			if (p.key.compareToIgnoreCase(word) > 0) { // word < p.key
				p = p.left; // set p = p.left
			} else { // p.key <= word
				p = p.right; // set p = p.right
			}
		} // end loop; either p == null or p.key == word
		if (p == null) { // word not in tree
			return 0;
		} // found at least one occurrence of word in tree
		
		p.count--; // decrement key val count
		
		if (p.count == 0) { // deleted last occurrence of word in tree; delete node p
			if (p.left == null) { // case when either p has no children (transplanting p.right is transplanting null ptr) or single right child
				this.tree_transplant(p, p.right); // connect root of right subtree (or null ptr) to p.par.right
			}else if (p.right == null) { // case when p has single left child
				this.tree_transplant(p, p.left); // connect root of left subtree to p.par.left
			}else { // case when p has both children --> replace with successor node (in this case, min of right subtree since right subtree exists)
				BST_cnode s = this.tree_min(p.right); // successor of p
				if(s.par != p) { // successor is not child of p; "rotate" subtree w root p.right accordingly
					this.tree_transplant(s, s.right); // connect s.right to former position of s
					s.right = p.right; // connect root of p's right subtree to s.right before replacing 
					s.right.par = s; // reconnect s as parent of s.right (now p.right)
					
				}
				// transplant s into position of p
				this.tree_transplant(p, s);
				s.left = p.left; // link p's left child as s.left
				p.left.par = s; // connect s as parent of p's left child
			}
			this.size--; // decrement size of tree to account for loss of node
		}
		return 1;
	}

	
	@Override
	public int tree_search(String word) { // iterative tree search implementation no. of occurrences of word in calling BST obj
		BST_cnode p = root; // pointer for traversal
		while (p != null && !p.key.equals(word)) { // p traverses tree until it points to first occurrence of word
			if (p.key.compareToIgnoreCase(word) > 0) { // word < p.key; set p = p.left
				p = p.left;
			} else { // p.key < word; set p = p.right
				p = p.right;
			}
		} // end while, either p == null or p.key == word
		if (p != null) { // found word in tree
			return p.count;
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
		// level of tree each node resides in
		// strategy: for each line, print next right-most node in tree that hasn't already been printed;
		BST_cnode p = this.tree_max(this.root); // begin with maximum node in tree
		int level = this.level(p); // stores level of tree that p resides in to print proper amount of spaces before printing p
		if(p!=null) { // non-empty tree
			while(p!= null) {
				level = this.level(p); // find level of p
				print_spaces(level); // print spaces indicating level of tree
				System.out.println(p.toString()); // print node p
				p = this.tree_predecessor(p); // next right-most node is predecessor of p
			}
		}else { // empty tree
			System.out.println("Empty tree.");
		}
		
	}
	
	
	// UTILITY METHODS
	
	protected void tree_transplant(BST_cnode before, BST_cnode after) { // replaces subtree with root before with subtree with root after by connecting after to before.par
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
		/*public BST_cnode tree_successor(BST_cnode n) { // returns pointer to in-order successor of node n in tree
			if (n.right != null) { // n has right subtree; successor is in-order minimum node in right subtree
				return tree_min(n.right);
			}
			// else, successor is first ancestor node pred of n such that n is in left subtree of pred
			BST_cnode p = n;  // node ptr used to traverse ancestors of n
			BST_cnode pred = p.par;
			while(pred != null && p == pred.right) { // travel up tree until p is in left subtree of its parent
				p = pred; // p becomes parent
				pred = pred.par; // pred becomes new parent (grandparent)
			}
			return pred;
		}*/
		
		
		public BST_cnode tree_predecessor(BST_cnode n) { // returns pointer to in-order predecessor of node n in tree
			if (n.left != null) { // n has left subtree; predecessor is in-order maximum node in left subtree
				return tree_max(n.left);
			}
			// else, predecessor is first ancestor node pred of n such that n is in right subtree of pred
			BST_cnode p = n;  // node ptr used to traverse ancestors of n
			BST_cnode pred = p.par;
			while(pred != null && p == pred.left) { // travel up tree until p is in right subtree of its parent
				p = pred; // p becomes parent
				pred = pred.par; // pred becomes new parent (grandparent)
			}
			return pred;
		}
		
		protected BST_cnode tree_min(BST_cnode n) { // returns ptr to node in subtree with root n that has minimum key value, i.e. left-most node in subtree
			BST_cnode p = n; // node ptr used to traverse subtree with root n
			while(p.left != null) { // travel to right-most node in subtree
				p = p.left;
			}
			return p;
		}
		
		
		public BST_cnode tree_max(BST_cnode n) { // returns ptr to node in subtree with root n that has max key value, i.e. right-most node in subtree
			BST_cnode p = n; // node ptr used to traverse subtree with root n
			while(p.right != null) { // travel to right-most node in subtree
				p = p.right;
			}
			return p;
		}
		
		protected int level(BST_cnode p) { // returns level of tree node p resides in by traversing path from p to this.root
			int level = 0; // return value
			BST_node pred = p; // ptr 
			while(pred != this.root) { // traverse path from p to this.root, incrementing level each time
				level++;
				pred = pred.par;
			}
			return level;
		}
		
		protected static int toArray_inOrder(BST_cnode p, String[] words, int i) { // uses in-order traversal (using ptr to subtree root node p) to store key values of each node in array words[] to demonstrate
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
	


	protected class BST_cnode extends BST_node {
		int count;
		BST_cnode par;
		BST_cnode left;
		BST_cnode right;

		protected BST_cnode() { // default constructor; calls parent BST_node constructor, sets count = 0
			super();
			count = 0;
		}

		protected BST_cnode(String k, BST_cnode p, BST_cnode l, BST_cnode r) { // alternate constructor; calls parent BST_node constructor, sets count = 1 for new key val
			super(k, p, l, r);
			count = 1;
		}

		@Override
		public String toString() {
			return this.key + "(" + this.count + ")";
		}
	}
}
