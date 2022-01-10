/*Chelsea Zackey 
 * CIS 5511
 * Assignment 6: AVL Tree
 * 10/30/19
 * 
 * Client code for main program to test correctness of insertion and deletion methods of AVL tree class.
 * Inserts arbitrarily chosen.
 * */

import java.util.Scanner;
public class AVL_Client {

	public static void main(String[] args) {
		AVL_tree a = new AVL_tree();
		
		/*boolean check = true; // boolean determining whether user wants to run another command
		Scanner kb = new Scanner(System.in); // initialize user input file stream
		

		while (check) { 
			prompt(); // prompt user to enter command 
			int choice = kb.nextInt(); // receive user command choice

		switch (choice) { 
			case 1: // tree-insert
				System.out.print("\nEnter word to be inserted in AVL tree: "); 
				String word = kb.next(); 
				a.tree_insert(word);
				break; 
			case 2: //tree-search 
				System.out.print("\nEnter word to be searched in AVL tree: "); 
				String find = kb.next(); 
				if (a.tree_search(find) == 0) {
					System.out.println("Word \'"+find+"\' was not found in AVL tree.");
				}else {
					System.out.println("Word \'"+find+"\' was found in AVL tree.");
				}
				break; 
			case 3: // tree-walk 
				System.out.println("\nPrinting alphabetically ordered representation of AVL tree:"); 
				a.tree_walk(); 
				System.out.print("\n"); // new line
				break; 
			case 4: // tree-delete
				System.out.print("\nEnter word to be deleted in trees: "); 
				String delete = kb.next(); 
				if (a.tree_delete(delete) == 1) {
					System.out.println("\nSuccessfully deleted  word "+delete+" from AVL tree."); 
				}else {
					System.out.println("\nWord "+delete+" not found in AVL tree."); 
				}
				break; 
			case 5: // print tree
				System.out.println("\nPrinting structural representation of AVL tree:"); 
				a.tree_print(); 
				System.out.print("\n"); // new line 
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
		 
		System.out.println("\nEnding AVL tree program...");
		kb.close();*/
		
		
		// FORMER CLIENT PROGRAM USED TO AUTOMATE SAMPLE RUNS TESTING CORRECTNESS OF INSERTION, DELETION & REBALANCING
		
				a.tree_insert("challenge");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println();
				a.tree_insert("zesty");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println();
				a.tree_insert("autonomy");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println();
				a.tree_insert("activism");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println();
				a.tree_insert("economy");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println();
				a.tree_insert("sunflower");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println();
				a.tree_insert("zesty");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_insert("challenge");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_insert("joy");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println();
				a.tree_insert("critical");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("chance");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("chilly");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("chilli");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("subsidy");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("tuition");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("zealous");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("overzealous");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("animated");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("bark");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("belly");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("brazen");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("brazil");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("branding");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("barricade");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("optimal");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("optimize");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("jade");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("jasmine");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("gift");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\n");
				a.tree_insert("magic");
				System.out.println("\n");
				a.tree_insert("zebra");
				System.out.println("\nAVL tree after insertion:\n");
				a.tree_print();
				System.out.println("\nPrinting stored words in alphabetical order:\n");
				a.tree_walk();
				System.out.println("\nHeights of root, root.left and root.right, respectively:\n");
				System.out.println(a.get_height(a.get_root()));
				System.out.println(a.get_height(a.get_left(a.get_root())));
				System.out.println(a.get_height(a.get_right(a.get_root())));
				
				System.out.println("\n------------- BEGIN DELETION TESTS -------------\n");
				System.out.println("\nAttempt deleting leaf node \'chilli\':\n");
				System.out.println();
				a.tree_delete("chilli");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting an inner node \'sunflower\':\n");
				System.out.println();
				a.tree_delete("sunflower");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting word not in tree \'word\':\n");
				System.out.println();
				a.tree_delete("word");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting root node \'chilly\':\n");
				System.out.println();
				a.tree_delete("chilly");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting leaf node \'jasmine\':\n");
				System.out.println();
				a.tree_delete("jasmine");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting leaf node \'autonomy\':\n");
				System.out.println();
				a.tree_delete("autonomy");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting leaf node \'activism\':\n");
				System.out.println();
				a.tree_delete("activism");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting root node \'critical\':\n");
				System.out.println();
				a.tree_delete("critical");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting an inner node \'zealous\':\n");
				System.out.println();
				a.tree_delete("zealous");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting an inner node \'optimal\':\n");
				System.out.println();
				a.tree_delete("optimal");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nAttempt deleting an inner node \'challenge\':\n");
				System.out.println();
				a.tree_delete("challenge");
				System.out.println("\nAVL tree after deletion:\n");
				a.tree_print();
				System.out.println("\nCreating new, empty AVL tree...\n");
				AVL_tree b = new AVL_tree();
				System.out.println("\nAttempt deleting word from an empty tree:\n");
				b.tree_delete("word");
				System.out.println("\nAVL tree after deletion:\n");
				b.tree_print();
				System.out.println("\nPrinting stored words in original tree in alphabetical order:\n");
				a.tree_walk();
				System.out.println("\nHeights of root, root.left and root.right, respectively:\n");
				System.out.println(a.get_height(a.get_root()));
				System.out.println(a.get_height(a.get_left(a.get_root())));
				System.out.println(a.get_height(a.get_right(a.get_root())));
				
	}

	public static void prompt() { // print prompt asking for user input determining which function to perform on
		// tree
		System.out.println(
				"1) Insert a word. \n2) Search AVL tree for word.\n3) Print words in AVL tree in alphabetical order.\n4) Delete a word.\n5) Print AVL tree structure.\n6) End program.");
		System.out.println("\nEnter no. of command to perform on AVL tree: ");
	}

}
