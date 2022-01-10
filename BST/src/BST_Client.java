import java.util.Scanner;



public class BST_Client {

	public static void main(String[] args) {
		BST t1 = new BST(); // supports duplicate keys in tree
		BST t2 = new CBST(); // represents duplicate keys by maintaining node counters
		boolean check = true; // boolean determining whether user wants to run another command
		Scanner kb = new Scanner(System.in); // initialize user input file stream
		

		while (check) { 
			prompt(); // prompt user to enter command 
			int choice = kb.nextInt(); // receive user command choice

		switch (choice) { 
			case 1: // tree-insert
				System.out.print("\nEnter word to be inserted in tree: "); 
				String word = kb.next(); 
				t1.tree_insert(word); 
				t2.tree_insert(word); 
				break; 
			case 2: //tree-search 
				System.out.print("\nEnter word to be searched in tree: "); 
				String find = kb.next(); 
				int c1 = t1.tree_search(find); int c2 =
				t2.tree_search(find); 
				System.out.println("\n\""+find+"\" has been found "+c1+" many times in tree 1 (supporting duplicate nodes).");
				System.out.println("\n\""+find+"\" has been found "+c2+" many times in tree 2 (using occurrence counters)."); 
				break; 
			case 3: // tree-walk 
				System.out.println("\nPrinting alphabetically ordered representation of tree 1 (supporting duplicate nodes):"); 
				t1.tree_walk(); 
				System.out.print("\n"); // new line
				System.out.println("\nPrinting alphabetically ordered representation of tree 2 (using occurrence counters):"); 
				t2.tree_walk(); 
				break; 
			case 4: // tree-delete
				System.out.print("\nEnter word to be deleted in trees: "); 
				String delete = kb.next(); 
				if (t1.tree_delete(delete) == 1) {
					System.out.println("\nSuccessfully deleted one occurrence of word "+delete+" from tree 1."); 
				}else {
					System.out.println("\nWord "+delete+" not found in tree 1."); 
				}
				if (t2.tree_delete(delete) == 1) {
					System.out.println("\nSuccessfully deleted one occurrence of word "+delete+" from tree 2."); 
				}else {
					System.out.println("\nWord "+delete+" not found in tree 2."); 
				}
				break; 
			case 5: // print tree
				System.out.println("\nPrinting structural representation of tree 1 (supporting duplicate nodes):"); 
				t1.tree_print(); 
				System.out.print("\n"); // new line
				System.out.println("\nPrinting structural representation of tree 2 (using occurrence counters):"); 
				t2.tree_print(); 
				break; 
			case 6: 
				check = false; 
				break; 
			default:
				System.out.println("\nInvalid command. Please try again."); 
				break; 
			}
			
			System.out.print("\n"); // new line 
		}
		 
		System.out.println("\nEnding BST program...");
		kb.close();
	}

	public static void prompt() { // print prompt asking for user input determining which function to perform on
		// tree
		System.out.println(
				"1) Insert a word. \n2) Determine frequency occurrence of a word.\n3) Print words in tree in alphabetical order.\n4) Delete a word.\n5) Print tree structure.\n6) End program.");
		System.out.println("\nEnter no. of command to perform on binary search tree 1 (supporting duplicate nodes) and tree 2 (using occurrence counters): ");
	}

}
