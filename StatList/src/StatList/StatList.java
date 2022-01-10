package StatList;

public class StatList {
	protected class stat_node{
		int comps; // performance indicator for no. of comparisons performed by a method
		int assgn; // performance indicator for no. of assignments performed by a method
		
		protected stat_node() { // default constructor; initializes no. of comparisons & assignments = 0
 			comps = assgn = 0;
		}
		
		protected stat_node(int c, int a) { // alternate constructor; sets comps = c, assgn = a
 			comps = c;
 			assgn = a;
		}
	}
}
