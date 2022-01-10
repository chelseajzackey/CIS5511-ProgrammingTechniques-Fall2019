/* Chelsea Zackey
 * 11/13/19
 * CIS 5511
 * Lab 08: Longest Common Substring
 * 
 * 
 * 
 */

import java.lang.Character;
import java.util.Stack;
public class LC_Substring {
	
	private int x_len; // length of input string x
	private int y_len; // length of input string y
	private int[][] c; char[][] b; // solution matrices for LONGES COMMON (CONTINUOUS) SUBSTRING problem for problem instance (String x, String y)
	// b = path matrix; c = length matrix
	private int length; // length of longest common substring held between strings x and y 
	private String LCS; // single solution to longest common substring problem
	
	// CLASS CONSTRUCTORS
	
	public LC_Substring() {
	/*Default constructor: sets x_len = y_len = 0, initializes b and c as x_len by y_len char and int matrices, respectively, and sets 
	 * length = 0*/
		x_len = 0;
		y_len = 0;
		b = new char[x_len][y_len];
		c = new int[x_len][y_len];
		length = 0;
	}
	
	public void print_solns(String x, String y) {
		// print matrix b
		System.out.println("Printing length matrix for this solution...");
		System.out.print("  ");
		for (int i = 0; i < this.y_len; i++) {
			System.out.print(y.charAt(i)+" ");
		}
		System.out.print("\n");
		for (int i = 0; i < this.x_len; i++) {
			System.out.print(x.charAt(i)+" ");
			for (int j = 0; j < this.y_len; j++) {
				System.out.print(this.c[i][j]+" ");
			}
			System.out.print("\n");
		}
		
		System.out.println("Printing directional matrix for this solution...");
		System.out.print(" ");
		for (int i = 0; i < this.y_len; i++) {
			System.out.print(y.charAt(i)+" ");
		}
		System.out.print("\n");
		for (int i = 0; i < this.x_len; i++) {
			System.out.print(x.charAt(i)+" ");
			for (int j = 0; j < this.y_len; j++) {
				System.out.print(this.b[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
	
	
	public LC_Substring(String x, String y) {
	/*Alternate constructor: sets x_len = x.length(), y_len = y.length(), initializes b and c as x_len by y_len char and int matrices, respectively, and sets 
	* length = 0*/
			x_len = x.length();
			y_len = y.length();
			b = new char[x_len][y_len];
			c = new int[x_len+1][y_len+1];
			length = 0;
		}
	
	// CLASS "GETTER" METHODS
	public int get_xlen() { // Returns value of x_len field for this LC_Substring problem
		return this.x_len;
	}
	
	public int get_ylen() { // Returns value of y_len field for this LC_Substring problem
		return this.y_len;
	}
	
	public int[][] get_c() { // Returns length matrix c for this LC_Substring problem
		return this.c;
	}
	
	public char[][] get_b() { // Returns solution path matrix b for this LC_Substring problem
		return this.b;
	}
	
	public int get_length() { // Returns solution length for this LC_Substring problem
		return this.length;
	}
	
	public String get_LCS() { // Returns one string solution for this LC_Substring problem
		return this.LCS;
	}
	
	// PUBLIC OPERATIONAL CLASS METHODS
	public static LC_Substring solve_LCSubstring(String x, String y) {
	/* Return new LC_Substring with data fields set to solutions to the longest common (continuous) substring problem for input strings x and y.*/
		LC_Substring L = new LC_Substring(x, y); // create new LC_Substring problem instance (data structure) for return
		//Stack<String> substring = new Stack<String>(); // create new stack to 
		for (int i = 0; i < L.x_len; i++) {
			char x_i = x.charAt(i); // extract next character of x
			x_i = Character.toLowerCase(x_i); // convert char x_i to lower case for comparison
			for (int j = 0; j< L.y_len; j++) {
				char y_j = y.charAt(j);// extract next character of y
				y_j = Character.toLowerCase(y_j); // convert char y_j to lower case for comparison
				
				if (x_i == y_j) { // characters at these positions of x and y match
					if (i > 0 && j > 0 && L.b[i-1][j-1] != '\\') { // avoid invalid index
						// x_i = y_j is NOT a continuation of previous continuous substring
						L.c[i+1][j+1] = 1; // reset length count
					} else { // new match is a continuation of a previous continuous substring
						L.c[i+1][j+1] =  L.c[i][j] + 1; // continue running calculation
					}
					L.b[i][j] = '\\'; // b matrix at this point set to diagonal direction
				}else if (L.c[i][j+1] >= L.c[i+1][j]){
				// Greatest length value is above c[i+1][j+1] in solution matrix
					L.c[i+1][j+1] = L.c[i][j+1]; // set this length value in matrix to greatest local length value
					L.b[i][j] = '^'; // b matrix at this point set to up direction
				}else {
				// Greatest length value is to left of c[i+1][j+1] in solution matrix	
					L.c[i+1][j+1] = L.c[i+1][j]; // set this length value in matrix to greatest local length value
					L.b[i][j] = '<'; // b matrix at this point set to up direction
				}
				// reset L.length
				L.length = Math.max(L.length, L.c[i+1][j+1]);
			}
		}
		L.find_LCSubstring(x);
		return L;
	}
	
	private void find_LCSubstring(String x) {
	/* Finds a LC substring for this LC_Substring problem as determined by solution found in contents of c & b matrices (assumed to be initialized & populated, along with this.length).*/
		if (this.length != 0) { 
		// there exists a non-trivial solution for this problem sequence
			int i = this.x_len-1; int j = this.y_len-1; // indices for matrix traversal
			Stack<String> substring = new Stack<String>(); // Stack storing characters for LC substring found for this problem instance
			char bval = this.b[i][j]; // value of current position in directional matrix this.b
			int l = this.c[i+1][j+1]; // length value for current position in solution matrix this.c
			
			// traverse solution matrices by following directions in b matrix until we reach starting position of 
			// some substring solution for this LC_Substring problem (i.e. when bval == \ and l = this.length)
			while (i > 0 && j > 0  && (l != this.length || bval != '\\')) {
				if (bval == '^') {
					// greater value is in this.c[i][j+1]
					i--;
				}else {
					// bval == '<'; greater value is in this.c[i+1][j]
					j--;
				}
				l = this.c[i+1][j+1];
				bval = this.b[i][j];
			} // end loop; should have l == this.length and bval == '\'
			
			while (bval == '\\' && i > 0 && j > 0){
				substring.push(Character.toString(x.charAt(i)));
				i--; j--;
				bval = this.b[i][j];
			}
			if (bval == '\\') {
				substring.push(Character.toString(x.charAt(i)));
			}
			if (!substring.empty()) {
				this.LCS = create_string(substring);
				return;
			}else {
				System.out.println("Error: algorithm could not find solution.");
			}
		}
		this.LCS = null;
	}
	
	private String create_string(Stack<String> q) {
		String s = "";
		while(!q.empty()) {
			s += q.pop();
		}
		return s;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LC_Substring L = solve_LCSubstring("gcdefaaknavll", "gcdffaaknavlkll");
		System.out.println("Finding length of longest common substring between strings \'gcdefaaknavll\' and \'gcdffaaknavlkll\'...");
		System.out.println("Length of this longest common substring: "+L.get_length()+"\n");
		System.out.println("Found longest common substring: "+L.get_LCS()+"\n");
		L.print_solns("gcdefaaknavll", "gcdffaaknavlkll");
		L = solve_LCSubstring("gcdefaaknavll", "gadefabknvllxyz");
		System.out.println("Finding length of longest common substring between strings \'gcdefaaknavll\' and \'gadefabknvllxyz\'...");
		System.out.println("Length of this longest common substring: "+L.get_length()+"\n");
		System.out.println("Found longest common substring: "+L.get_LCS()+"\n");
		L.print_solns("gcdefaaknavll", "gadefabknvllxyz");
	}
	
	
}
