/*Chelsea Zackey 
 * CIS 5511
 * Assignment 1: Programming Portion
 * 9/11/19
 * 
 * Program that performs Insertion Sort and Merge Sort on an integer array populated by a 
 * random number generator. 
 * 
 * For n = 10, 20, 30, ..., 500, program creates integer array of size n, A[], and performs 
 * Insertion Sort and Merge Sort on array, respectively. 
 * Sorting methods record number of loop body iterations are performed per each call, as well as elapsed time in milliseconds. 
 * Iterations are averaged over 500 runs; averages for size n are stored in data collection array. 
 * 
 * Once all simulations have been performed, program prints each data collection array. 
 * 
 * 
 * 
 * */

import java.util.Random;
import java.lang.Math;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class Sorting {
	
	//cost analysis indicators: Insertion Sort
	static int itimes1; //counter for total # times while loop condition is checked in Insertion Sort
	static int itimes2; //counter for total # times while loop body is called in Insertion Sort
	
	//cost analysis indicators: Merge Sort
	static int mtimes1; //counter for total # times while loop condition is checked in Merge Sort
	static int mtimes2; //counter for total # times while loop bodies are called in Merge Sort
	
	public static void main(String[] args) {
		int n = 0;
		int horizon = 150;
		double[] i_data1 = new double[horizon]; // average while loop condition check of Insertion Sort per problem size n
		double[] i_data2 = new double[horizon]; // average while loop iteration check of Insertion Sort per problem size n
		double[] m_data1 = new double[horizon]; // average while loop condition check of Merge Sort per problem size n
		double[] m_data2 = new double[horizon];// average while loop iteration check of Merge Sort per problem size n
		long[] i_taves = new long[horizon]; // average execution time of Insertion Sort per problem size n
		long[] m_taves = new long[horizon];// average execution time of Merge Sort per problem size n
		
		while(n < horizon) {
			int size = (n+1)*10; // array size
			int runs = 100; // number of simulation runs for data collection
			int sim = 0; // simulation counter 
			
			// arrays storing value of itimes1, itimes2, mtimes1 and mtimes2, respectively, per each run
			int[] it1= new int[runs];
			int[] it2= new int[runs];
			int[] mt1= new int[runs];
			int[] mt2= new int[runs];
			// arrays storing execution time (ms) for sorting methods
			long[] i_time = new long[runs];
			long[] m_time = new long[runs];
			
			System.out.println("Running sorting simulations for problem size n = "+size+"...");

			while(sim < runs) {
				itimes1 = itimes2 = mtimes1 = mtimes2 = 0;
				int[] A = new int[size];  // array to be sorted
				int[] B = new int[size]; // copy of array for sorting comparison
				
				// Initialize array A[]; copy A[] into B[]; print A[]
				populate(A);
				copyArr(A, B);
				System.out.println("Unsorted array A[]:");
				printArr(A);
				System.out.println();
				
				// perform Insertion Sort on A[] and MergeSort on B[]; record execution times
				long startTime = System.nanoTime();
				insertionSort(A);
				long endTime = System.nanoTime();
				i_time[sim] = endTime - startTime;  //Total execution time in nanoseconds
				
				startTime = System.nanoTime();
				mergeSort(B, 0, B.length-1);
				endTime = System.nanoTime();
				m_time[sim] = endTime - startTime;  //Total execution time in nanoseconds
				
				
				// store data
				it1[sim] = itimes1;
				it2[sim] = itimes2;
				mt1[sim] = mtimes1;
				mt2[sim] = mtimes2;
				
				//print results
				System.out.println("Array A[] after Insertion Sort:");
				printArr(A);
				System.out.println();
				System.out.println("Execution time of Insertion sort: "+i_time[sim]);
				System.out.println();
				System.out.println("No. times while loop condition checked: "+itimes1);
				System.out.println();
				System.out.println("No. times while loop body executed: "+itimes2);
				
				System.out.println();
				System.out.println("Array A[] after Merge Sort:");
				printArr(B);
				System.out.println();
				System.out.println("Execution time of Merge sort: "+m_time[sim]);
				System.out.println();
				System.out.println("No. times while loop condition checked: "+mtimes1);
				System.out.println();
				System.out.println("No. times while loop body executed: "+mtimes2);
				System.out.println();
				System.out.println();
				
				sim++;
			}
			
			System.out.println("End of data collection for n = "+size+"...");
			System.out.println("it1[]:");
			printArr(it1);
			System.out.println();
			System.out.println("it2[]:");
			printArr(it2);
			System.out.println();
			System.out.println("mt1[]:");
			printArr(mt1);
			System.out.println();
			System.out.println("mt2[]:");
			printArr(mt2);
			System.out.println();
			System.out.println("i_time[]:");
			printArr(i_time);
			System.out.println();
			System.out.println("m_time[]:");
			printArr(m_time);
			System.out.println();
			System.out.println();
			
			// calculate & print averages per run 
			double iave1 = (double)sumArr(it1)/runs;
			double iave2 = (double)sumArr(it2)/runs;
			double mave1 = (double)sumArr(mt1)/runs;
			double mave2 = (double)sumArr(mt2)/runs;
			long itave = sumArr(i_time)/runs;
			long mtave = sumArr(m_time)/runs;

			System.out.println("Average counts per run for n = "+size+"...");
			System.out.println();
			System.out.println("Average no. times while loop condition checked in Insertion sort: "+iave1);
			System.out.println("Average no. times while loop body executed in Insertion sort: "+iave2);
			System.out.println("Average no. times while loop condition checked in Merge sort: "+mave1);
			System.out.println("Average no. times while loop body executed in Merge sort: "+mave2);
			System.out.println("Average execution time for Insertion sort: "+itave+"ns");
			System.out.println("Average execution time for Merge sort: "+mtave+"ns");
			System.out.println();
			System.out.println("----------------------------------------------------------------------------");
			System.out.println();
			
			
			// store data for size n
			i_data1[n] = iave1;
			i_data2[n] = iave2;
			m_data1[n] = mave1;
			m_data2[n] = mave2;
			i_taves[n] = itave;
			m_taves[n] = mtave;

			n++;
		}
		
		// print data point arrays per each size n 
		System.out.println("Array of average no. times while loop condition checked in Insertion sort per each size n = 10, 20, 30, 40, ..., 1000: ");
		printArr(i_data1);
		System.out.println();
		System.out.println("Array of average no. times while loop body executed in Insertion sort per each size n = 10, 20, 30, 40, ..., 1000: ");
		printArr(i_data2);
		System.out.println();
		System.out.println("Array of average no. times while loop condition checked in Merge sort per each size n = 10, 20, 30, 40, ..., 1000: ");
		printArr(m_data1);
		System.out.println();
		System.out.println("Array of average no. times while loop body executed in Merge sort per each size n = 10, 20, 30, 40, ..., 1000: ");
		printArr(m_data2);
		System.out.println();
		System.out.println("Array of average execution time (ns) of Insertion sort per each size n = 10, 20, 30, 40, ..., 1000: ");
		printArr(i_taves);
		System.out.println();
		System.out.println("Array of average execution time (ns) of Merge sort per each size n = 10, 20, 30, 40, ..., 1000: ");
		printArr(m_taves);
		
	}

	public static void insertionSort(int A[]){
		/* Method to perform insertion sort on integer array A[];
		 * sorting is done in nondecreasing order.
		 * Assumes subarray A[1, ..., j-1] is sorted; inserts A[j] into 
		 * proper position in A[1, ..., j-1], for all 2 <= j <= A.length
		 * */
			int key; // array element to be inserted
			int i; // index for traversal of sorted subarray
			
			for (int j = 1; j < A.length; j++) {
				key = A[j]; 
				i = j-1; // begin traversal at A[j-1]
				int t = 0; // counter tracking number of times while loop is executed 
							// for jth iteration
				
				while (i >= 0 && (A[i]>key)){
				// shift all elements A[i] in A[1, ..., j-1] such that A[i] > key
				// to left of key
					A[i+1] = A[i];
					i--;
					t++;
				}
				A[i+1] = key; // insert key into proper position in subarray A[1, ..., j]
				
				// store counter variable information in global cost indicators
				itimes1 += t+1;
				itimes2 += t;
			}
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
		int t1 = 0; // counter tracking # times 1st while loop body is executed 
		int t2 = 0; // counter tracking # times 2nd while loop body is executed 
		int t3 = 0; // counter tracking # times 3rd while loop body is executed 
		
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
			t1++; // increment counter
		}
		// end loop; either i = size1 or j = size2 
		// store while loop counts
		mtimes1 += t1 + 1;
		mtimes2 += t1;
		
		// Insert remaining elements of L
		while(i < size1){
			A[k] = L[i];
			i++; 
			k++;
			t2++;
		}
		// store while loop counts
		mtimes1 += t2 + 1;
		mtimes2 += t2;	
		
		// j < size2; insert remaining elements of R
		while(j < size2){
			A[k] = R[j];
			j++;
			k++;
			t3++;
		}
		// store while loop counts
		mtimes1 += t3 + 1;
		mtimes2 += t3;
	}
		
	public static void populate(int A[]) {
		/* Method to populate entries of integer array A[] using random number generator;
		 * entries range from 0 to 100*/
		Random rand = new Random(); // initialize random generator
		for (int i = 0; i < A.length; i++) {
			A[i] = rand.nextInt(100);
		}
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
}
