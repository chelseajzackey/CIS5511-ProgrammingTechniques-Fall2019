/*Chelsea Zackey 
 * CIS 5511
 * Assignment 4: QuickSort
 * 10/2/19
 * 
 * Program that performs Quick Sort on an integer array populated by a 
 * random number generator. 
 * 
 * For n = 10, 20, 30, ..., 500, program creates integer array A[] of size n and sorts the array in nondecreasing order using two versions of Quick Sort. 
 * Version 1 of Quick Sort makes use of the standard partition mechanism presented in the text book. Version 2 of Quick Sort-- made on a copy of the original
 * array A-- makes use of the Hoare-Partition mechanism. Each time a call to either version of Quick Sort is made, program records total number of comparisons 
 * and assignments made, as well as execution time of method in ns. Process is repeated per each size n 100 times in order to generate averages for each data 
 * point per each size n.
 * 
 *
 *  
 * 
 * 
 * 
 * */

import java.util.Random;
import java.lang.Math;
import java.util.concurrent.TimeUnit;

public class QuickSort {
	
	//performance indicators
		public static int comps; // counter for comparisons
		public static int assgn; // counter for assignments
		public static int swaps; // counter for swaps

	// main 
	public static void main(String[] args) {
		int n = 0;
		int horizon = 50;
		double[] comp1 = new double[horizon]; // stores average total comparisons made by QuickSort1 per each problem size n
		double[] assign1 = new double[horizon]; // stores average total assignments made by QuickSort1 per each problem size n
		double[] comp2 = new double[horizon]; // stores average total comparisons made by QuickSort2 per each problem size n
		double[] assign2 = new double[horizon]; // stores average total assignments made by QuickSort2 per each problem size n
		double[] etime1 = new double[horizon]; // stores average execution time of QuickSort1 per each problem size n in ns
		double[] etime2 = new double[horizon]; // stores average execution time of QuickSort2 per each problem size n in ns
		double[] swap1 = new double[horizon]; // stores average total swaps made by QuickSort1 per each problem size n
		double[] swap2 = new double[horizon]; // stores average total swaps made by QuickSort2 per each problem size n
		
		while (n < horizon) {
			int size = (n+1)*10; // array size
			int runs = 100; // number of simulation runs for data collection
			int sim = 0; // simulation counter 
			
			int c1[] = new int[runs]; // stores # of comparisons during QuickSort1 per each run
			int a1[] = new int[runs]; // stores # of assignments during QuickSort1 per each run	
			int c2[] = new int[runs]; // stores # of comparisons during QuickSort2 per each run
			int a2[] = new int[runs]; // stores # of assignments during QuickSort2 per each run
			double[] t1 = new double[runs]; // stores execution time of QuickSort1 per each run in ns
			double[] t2 = new double[runs]; // stores execution time of QuickSort2 per each run in ns
			double[] s1 = new double[runs]; // stores total swaps made by QuickSort1 per each run
			double[] s2 = new double[runs]; // stores total swaps made by QuickSort2 per each run
			
			while(sim < runs) {
				System.out.println("Running simulation no. "+(sim+1)+" for problem size n = "+size+"...");
				System.out.println();
				
				// Declare array A and copy of A, B; populate A; copy A to B;
				// print unsorted array A
				int A[] = new int[size];
				int B[] = new int[size];
				populate(A);
				copyArr(A, B);
				System.out.println("Unsorted array A[]:");
				printArr(A);
				System.out.println();
				
				// Call to Quicksort
				comps = assgn = swaps = 0; // reinitialize counters
				System.out.println("Calling Quicksort v1 on array A...");
				long startTime = System.nanoTime();
				QuickSort1(A, 0, size-1);
				long endTime = System.nanoTime();
				System.out.println("Contents of A[] after Quicksort v1: ");
				printArr(A);
				System.out.println();
				// store v1 data
				c1[sim] = comps;
				a1[sim] = assgn;
				t1[sim] = endTime-startTime;
				s1[sim] = swaps;
				
				comps = assgn = swaps = 0; // reinitialize counters
				System.out.println("Calling Quicksort v2 on copy of array A...");
				startTime = System.nanoTime();
				QuickSort2(B, 0, size-1);
				endTime = System.nanoTime();
				System.out.println("Contents of copy of A[] after Quicksort v2: ");
				printArr(B);
				System.out.println();
				// store v2 data
				c2[sim] = comps;
				a2[sim] = assgn;
				t2[sim] = endTime-startTime;
				s2[sim] = swaps;
				// next iteration
				sim++;
			}
			
			System.out.println("End of data collection for n = "+size+"...");
			System.out.println();
			
			// calculate & store data points
			double c1_ave = (double)sumArr(c1)/runs;
			double a1_ave = (double)sumArr(a1)/runs;
			double c2_ave = (double)sumArr(c2)/runs;
			double a2_ave = (double)sumArr(a2)/runs;
			double t1_ave = (double)sumArr(t1)/runs;
			double t2_ave = (double)sumArr(t2)/runs;
			double s1_ave = (double)sumArr(s1)/runs;
			double s2_ave = (double)sumArr(s2)/runs;
			
			comp1[n] = c1_ave;
			assign1[n] = a1_ave;
			comp2[n] = c2_ave;
			assign2[n] = a2_ave;
			etime1[n] = t1_ave;
			etime2[n] = t2_ave;
			swap1[n] = s1_ave;
			swap2[n] = s2_ave;
			
			n++;
		}
		// print data point arrays per each size n 
		System.out.println("Array of average no. comparisons made by Quicksort v1 per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(comp1);
		System.out.println();
		System.out.println("Array of average no. assignments made by Quicksort v1 per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(assign1);
		System.out.println();
		System.out.println("Array of execution time (ns) of Quicksort v1 per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(etime1);
		System.out.println();
		System.out.println("Array of average no. of swaps made by Quicksort v1 per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(swap1);
		System.out.println();
		System.out.println("Array of average no. comparisons made by Quicksort v2 per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(comp2);
		System.out.println();
		System.out.println("Array of average no. assignments made by Quicksort v2 per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(assign2);
		System.out.println();
		System.out.println("Array of execution time (ns) of Quicksort v2 per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(etime2);
		System.out.println();
		System.out.println("Array of average no. of swaps made by Quicksort v2 per each size n = 10, 20, 30, 40, ..., 500: ");
		printArr(swap2);
		System.out.println();
	}
		
	//QuickSort functions
	public static void QuickSort1(int A[], int p, int r) {
		// Performs quicksort on the integer sub-array A[p, ..., r] using the original partitioning
		// mechanism as presented in the textbook; sorting is performed in nondecreasing order. 
		
		// Verify sub-array indices are valid 
		if(p < r) {
			int q = partition(A, p, r); // define index of pivot element 
			// sort left sub-array of pivot
			QuickSort1(A, p, q-1);
			// sort right sub-array of pivot
			QuickSort1(A, q+1, r);
		}
	}
	
	public static void QuickSort2(int A[], int p, int r) {
		// Performs quicksort on the integer sub-array A[p, ..., r] using the hoare-partition
		// algorithm; sorting is performed in nondecreasing order. 
		
		// Verify sub-array indices are valid 
		if(p < r) {
			int q = hoare_partition(A, p, r); // define index of pivot element 
			// sort left sub-array of pivot
			QuickSort2(A, p, q-1);
			// sort right sub-array of pivot
			QuickSort2(A, q+1, r);
		}
	}
	
	public static int partition(int A[], int p, int r) {
		// Given the subarray A[p, ..., r], return the index j of the element piv after sorting A[p, ..., r]
		// to satisfy A[k] <= piv for all indices p <= k <= j, and A[k] > piv for all indices j < k <= r, where we set 
		// piv = A[r].
		int piv = A[r];
		assgn++;
		int i = p-1;
		assgn++;
		for (int j = p; j < r; j++) {
			comps+=2; // count for-loop comparison and if condition
			if (A[j] <= piv) {
				i++; assgn++;
				swaps++;
				swap(A, i, j);
			}
			assgn++; // count assignment j = j+1
		} // end for-loop; have accessed every element before pivot element A[r]
		// index i is last index of sub-array of A[p, ..., r] comprised of elements < x
		i++; assgn++;
		// place x in first index of sub-array of A[p, ..., r] comprised of elements >= x
		swap(A, i, r); swaps++;
		// return index of pivot
		return i;
	}
	
	public static int hoare_partition(int A[], int p, int r) {
		// // Given the subarray A[p,..., r], return the index j such that A[p, ..., r] has been sorted to satisfy 
		// A[k] <= key for all p <= k <= j, and A[k] >= key for all j+1 <= k <= r, where we set key = A[p]
		int piv = A[p];
		int i = p-1;
		int j = r+1;
		assgn+=3; // above 3 assignments
		while(true) {
			do {
				comps++; // while-loop condition
				j--; assgn++;
			}while(A[j] > piv);
			comps++; // last while-loop condition
			do {
				comps++; // while-loop condition
				i++; assgn++;
			}while(A[i] < piv);
			comps++; // while-loop condition
			
			comps++; // if condition
			if(i < j) {
				swaps++;
				swap(A, i, j);
			}else {
				return j;
			}
		}
	}
	
	
	// Auxiliary Functions
	public static void populate(int A[]) {
		/* Method to populate entries of integer array A[] using random number generator;
		 * entries range from 0 to 2*A.length*/
		Random rand = new Random(); // initialize random generator
		for (int i = 0; i < A.length; i++) {
			A[i] = rand.nextInt(2*A.length);
		}
	}
	
	public static void swap(int A[], int i, int j) {
		// Given the integer array A[] and index values i &j, 
		// swap the values stored in A[i] and A[j]
		if (i >= 0 && j >= 0 && i < A.length && j < A.length) {
			int temp = A[i]; assgn++;
			A[i] = A[j]; assgn++;
			A[j] = temp; assgn++;
		} else { 
			System.out.println("Error call to swap(): invalid indices");
			System.out.println();
		}
	}
	
	public static void copyArr(int A[], int B[]) {
		/* Copies entries of integer array A[] into corresponding entries
		 * of B[]; assumes arrays are of same length*/
		for (int i = 0; i < A.length; i++){
			B[i] = A[i];
		}
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
