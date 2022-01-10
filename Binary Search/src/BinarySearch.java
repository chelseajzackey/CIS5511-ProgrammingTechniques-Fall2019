/*Chelsea Zackey 
 * CIS 5511
 * Assignment 2: Binary Search
 * 9/18/19
 * 
 * Program that performs Binary Search on a sorted integer array populated by a 
 * random number generator. 
 * 
 * For n = 10, 20, 30, ..., 500, program creates integer array A[] of size n and sorts the array in nondecreasing order using Merge Sort. 
 * The program then generates a random integer search value in ranging from 0 to 2n-1, which is the same range used when populating the entries of A[].
 * The program then calls Binary Search on A[], recording the total number of comparisons and assignments performed during each call to the method. 
 * 
 * Process is repeated per each size n 100 times in order to generate averages for each data point per each size n.
 * 
 *
 *  
 * 
 * 
 * 
 * */

import java.util.Random;
import java.lang.Math;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class BinarySearch {

	//cost analysis indicators
	public static int comps; // counter for comparisons
	public static int assgn; // counter for assignments
	
	public static void main(String[] args) {
		int n = 0;
		int horizon = 50;
		double[] comp = new double[horizon]; // stores average total comparisons made by Binary Search per each problem size n
		double[] assign = new double[horizon]; // stores average total assignments made by Binary Search per each problem size n
		
		while (n < horizon) {
			int size = (n+1)*10; // array size
			int runs = 100; // number of simulation runs for data collection
			int sim = 0; // simulation counter 
			
			int c[] = new int[runs]; // stores # of comparisons during per each run
			int a[] = new int[runs]; // stores # of assignments during per each run
			
			
			while(sim < runs) {
				System.out.println("Running simulation no. "+(sim+1)+" for problem size n = "+size+"...");
				System.out.println();
				
				comps = assgn = 0; // reinitialize counters
				
				// Declare, initialize and sort A
				int A[] = new int[size]; 
				populate(A);
				mergeSort(A, 0 , A.length-1);
				
				
				System.out.println("Array A[]:");
				printArr(A);
				System.out.println();
				
				int key = new_key(A.length); // create new search key
				
				// Call to binary search
				System.out.println("Searching for key "+key+" in A[]...");
				System.out.println();
				int answer = binSearch(A, key, 0, A.length-1);
				
				// store data
				c[sim] = comps;
				a[sim] = assgn;
				
				//print results
				if (answer == -1) {
					System.out.println("Search key "+key+" was not found in A[].");
					System.out.println();
				}else {
					System.out.println("Search key "+key+" was found in A["+answer+"].");
					System.out.println();
				}
				
				sim++;
			}
			System.out.println("End of data collection for n = "+size+"...");
			System.out.println();
			
			// calculate & store data points
			double c_ave = (double)sumArr(c)/runs;
			double a_ave = (double)sumArr(a)/runs;
			
			comp[n] = c_ave;
			assign[n] = a_ave;
			
			n++;
		}
		// print data point arrays per each size n 
		System.out.println("Array of average no. comparisons made by Binary Search per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(comp);
		System.out.println();
		System.out.println("Array of average no. assignments made by Binary Search per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(assign);
		System.out.println();
	}
	
	public static int binSearch(int A[], int key, int l, int r) {
		/*Method that performs binary search on sorted (nondecreasing order) integer (sub)array A[l, ..., r];
		 * Returns index i such that A[i] = key, if key is in A; else, returns -1*/
		
		// l is leftmost index of subarray A[l, ..., r]
		// r is rightmost index of subarray A[l, ..., r]
		
		comps++; // count first comparison made in below if-else statement 
		if (l < r) {
			// check that indices are valid
			int m = (int)Math.floor((l+r)/2); // compute middle index of subarray
			assgn++;
			comps++; // count comparison made in below if-else statement
			if (A[m] < key) {
				// key should be in subarray right of A[m]
				return binSearch(A, key, m+1, r);
			} else if (A[m] > key) {
				// key should be in subarray left of A[m]
				return binSearch(A, key, l, m-1);
			}else{
				// A[m] == key
				return m;
			}
		}else if (A[l] == key){
			// assumes l == r, i.e. subarray is a singleton
			return l;
		}
		// key not in A[]
		return -1;
	}
	
	public static void mergeSort(int A[], int l, int r){
		/*Method that performs Merge Sort on subarray A[l, ..., r] of integer array A[];
		 * sorting performed in nondecreasing order
		 * */
		if (l < r) {
			// find middle index of subarray A[l, ..., r]
			int m = (int)Math.ceil((l+r)/2);
			mergeSort(A, l, m); // sort left subarray A[l, ..., m]
			mergeSort(A, m+1, r); // sort right subarray A[m+1, ..., r]
			merge(A, l, m, r); // merge sorted subarrays A[l, ..., m] and A[m+1, ..., r]
		}
	}
	
	public static void merge(int A[], int l, int m, int r){
		/*Merge subroutine of mergeSort; merge two subarrays of integer array A[] sorted 
		 * in nondecreasing order.
		 * First subarray is A[l, ..., m] = L[]; second is A[m+1, ... r] = R[]. 
		 * Assumes L[] and R[] are sorted in nondecreasing order before merging into
		 * subarray A[l, ..., r]. 
		 * Does NOT assume L[] and R[] are of same size
		 * */ 
		int size1 = m - l + 1; // size of new temp array L[]
		int size2 = r-m; // size of new temp array R[]
		int[] L = new int[size1];
		int[] R = new int[size2]; 

		// copy subarrays of A[] into L[] and R[]
		for(int i = 0; i < size1; i++){
			L[i] = A[l+i];
		}
		for(int i = 0; i < size2; i++){
			R[i] = A[m+1+i];
		}
		
		int i, j; i = j = 0; // index i traverses L[]; index j traverses R[]
		int k = l; // index k traverses A[l, ..., r]
		
		// begin merging L[] and R[] into A[l, ..., r] by comparing elements 
		while (i < size1 && j < size2) {
			if (L[i] <= R[j]){
				// L[i] is next element in A
				A[k] = L[i];
				i++; // move to next element of L
			}else{
				// R[j] is next element in A
				A[k] = R[j];
				j++; // move to next element of R
			}
			k++; // move to next element of A
		}
		// end loop; either i = size1 or j = size2 

		
		// Insert remaining elements of L
		while(i < size1){
			A[k] = L[i];
			i++; 
			k++;
		}
		
		// j < size2; insert remaining elements of R
		while(j < size2){
			A[k] = R[j];
			j++;
			k++;
		}
	
	}
	
	public static void populate(int A[]) {
		/* Method to populate entries of integer array A[] using random number generator;
		 * entries range from 0 to 2*A.length*/
		Random rand = new Random(); // initialize random generator
		for (int i = 0; i < A.length; i++) {
			A[i] = rand.nextInt(2*A.length);
		}
	}
	
	public static int new_key(int n) {
		/* Method returns random integer ranging from 0 to n to be searched for in Binary Search*/
		Random rand = new Random(); // initialize random generator
		return rand.nextInt(2*n);
	}
	
	public static int sumArr(int A[]) {
		/* Returns the sum of all entries of integer array A[]*/
		int sum = 0; //initialize sum 
		for (int i = 0; i < A.length; i++){
			sum += A[i];
		}
		return sum;
	}
	
	public static double sumArr(double A[]) {
		/* Returns the sum of all entries of double array A[]*/
		double sum = 0; //initialize sum 
		for (int i = 0; i < A.length; i++){
			sum += A[i];
		}
		return sum;
	}
	
	public static long sumArr(long A[]) {
		/* Returns the sum of all entries of long array A[]*/
		long sum = 0; //initialize sum 
		for (int i = 0; i < A.length; i++){
			sum += A[i];
		}
		return sum;
	}
	
	public static void printArr(int A[]){
		/* Prints entries of integer array A[]*/
		for(int i = 0; i < A.length; i++){
			System.out.print(A[i]+" ");
		}
		System.out.println(); // add new line at end
	}
	
	public static void printArr(double A[]){
		/* Prints entries of double array A[]*/
		for(int i = 0; i < A.length; i++){
			System.out.print(A[i]+"\n");
		}
		System.out.println(); // add new line at end
	}
	
	public static void printArr(long A[]){
		/* Prints entries of long array A[]*/
		for(int i = 0; i < A.length; i++){
			System.out.print(A[i]+"\n");
		}
		System.out.println(); // add new line at end
	}
}

