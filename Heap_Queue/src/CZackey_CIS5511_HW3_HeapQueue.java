/*Chelsea Zackey 
 * CIS 5511
 * Assignment 3: Heap-Based Priority Queue
 * 9/25/19
 * 
 * Program that implements a min-heap-based priority queue storing words in dictionary order. The queue data
 * structure supports the tasks of word insertion, removal of the word that comes first in alphabetical order, 
 * changing the value of the word stored in the ith node (given that i is a valid index), and reporting the 
 * total number of nodes in the queue at any time. Main method of the program includes client code testing that
 * each of these tasks are performed correctly for each instance of the data structure. 
 * 
 * For each problem (total heap) size n= 10, 20, 30, ..., 100, the client code testing class methods first reads (n+1)-many lines (words) from a local 
 * dictionary file and stores the first n words in the array test_words to reuse throughout various tests. 
 * 
 * To test the add-val method's capacity to 1) insert words into heap if they do not already exist, 2) check that the heap does not exceed capacity, and 
 * 3) maintains the theoretical min-heap structure, the client code first adds the n words stored in test_words into the queue. Since the elements of 
 * test_words are read from a dictionary file, the array is necessarily sorted in increasing lexicographic order. To see whether maintenance is successfully
 * performed in add-val, we first add the words of the last half of test_words into the heap, then proceed to add the words of the first half of the array. 
 * Being that the words in the first half of the array take alphabetical precedence, adding dictionary words to the heap in this order allows us to 
 * determine whether add-val assigns the correct (higher) priority to the words stored of the first half of test_words. Client then proceeds to try and 
 * add the n+1^th word read from the dictionary file into the already full queue, as well as a duplicate word.
 * 
 * To test the remove-min and change-val methods, the client code first calls the former method floor(n/2)-many times. Once this is complete, 
 * we have a heap of size floor(n/2). At this point, client calls method change-val floor(n/4)-many times to change the values of the words in the
 * last half of the heap with elements from the first quarter of the array test_words, which have a lesser lexicographic rank than all of the words 
 * remaining in the heap, and should therefore be re-heaped with higher priority at the end of each call to change-val. The algorithm then proceeds to 
 * call remove-min until the heap is empty, then calls remove-min once more on the empty heap to ensure that the program does not crash for this boundary case.
 * 
 * Program currently prints out all results from sample runs for when n=10, 20, and 50. This can be modified by adjusting the variable "horizon". 
 * Each time results are printed, the size of the heap is reported via a call to the method "size" in order to verify this method reports the 
 * correct total number of nodes in the heap at all times.  
 *
 * 
 * */

import java.io.*;

public class HeapQueue {
	private String[] A;
	private int cap;
	private int total;
	static int comp = 0; // tracks number of comparisons made for each algorithm
	static int assign = 0; // tracks number of assignments made for each algorithm
	
// constructors 
	public HeapQueue() {
		// Default HeapQueue constructor; sets cap == 100 and initializes empty array-based heap
		cap = 100;
		A = new String[cap];
		total = 0;
	}
	
	public HeapQueue(int size) {
		// Alternate HeapQueue constructor; sets cap == size and initializes empty array-based heap
		cap = size;
		A = new String[cap];
		total = 0;
	}
	
// main method	
	public static void main(String[] args) {
		int horizon = 10; // chosen so that we never reach the end of dictionary file during any experiment
		int n = 0; // problem size 
		File words = new File("/Users/edenzackey/Desktop/CIS 5511/HW3/engmix.txt"); // sample English dictionary file to extract words from 
		FileReader fr;
		try {  // initialize simulations with data from dictionary file
			fr = new FileReader(words);
			BufferedReader buff = new BufferedReader(fr);
			
			while (n < horizon) {
				// initialize heap & relevant heap variables
				int heapSize = (n+1)*10;
				HeapQueue dictionary = new HeapQueue(heapSize);
				String test_words[] = new String[heapSize]; // array to store words read during this simulation 
							// to re-use for testing class methods
				System.out.println("Sample run for total heap size n = "+heapSize+"...");
				System.out.println();
				
				// begin reading words from dictionary file and storing them in array test_words
				String word = buff.readLine(); 
				int word_count = 0; // counts how many words we have read
				while (word_count <= heapSize && word != null){
					//read heapSize+1 many words from file; store first heapSize many words in test_words
					if(word_count < heapSize) { // store first 
						test_words[word_count]=word;
					}
					word = buff.readLine(); // read next line
					word_count++; // increment word count
				}
				
				// begin adding words to queue in reverse lexicographic order
				for (int i =0; i < heapSize; i++) {
					// add word to queue 
					dictionary.add_word(test_words[(heapSize-1)-i]);
					
					// testing purposes-- print sample runs of select sizes 
					if(heapSize == 10 || heapSize == 20 || heapSize == 50) {
						System.out.println("Priority queue after attempting to add string \""+test_words[(heapSize-1)-i]+"\": "); 
						dictionary.printHeap();
						System.out.println("Total words in queue-based dictionary: "+dictionary.size()); 
						System.out.println();
					}
				}
				
				// attempt adding extra word into heap
				if(word != null) { // verify that last word read from file is not EoF 
					if(heapSize == 10 || heapSize == 20 || heapSize == 50) {
						System.out.println("Priority queue after attempting to add extra word \""+word+"\" into full queue: "); 
						dictionary.printHeap();
						System.out.println("Total words in queue-based dictionary: "+dictionary.size()); 
						System.out.println();
					}
				}
				
				// attempt adding repeat word into heap
				if(heapSize == 10 || heapSize == 20 || heapSize == 50) {
					System.out.println("Priority queue after attempting to repeat word \""+test_words[0]+"\": "); 
					dictionary.printHeap();
					System.out.println("Total words in queue-based dictionary: "+dictionary.size()); 
					System.out.println();
				}
				
				
				// testing removal of highest priority node (remove-min)
				word_count = 0;
				int mid = (int)Math.floor((double)heapSize/2);
				while (word_count < mid) {
					// attempt to remove the root from the heap; 
					// test method change-val when size decreases to floor(heapSize/2)
					
					// testing purposes-- print sample runs of select sizes 
					if(heapSize == 10 || heapSize == 25 || heapSize == 50) {
						System.out.println("Queue before attempting to remove word that comes first in alphabetical order: ");
						dictionary.printHeap();
						System.out.println("Total words in queue-based dictionary: "+dictionary.size());
						System.out.println();
					}
					
					String min = dictionary.remove_min();// attempt extracting root
					
					// testing purposes-- print sample runs of select sizes 
					if(heapSize == 10 || heapSize == 20 || heapSize == 50) {
						// print results
						if (!min.equalsIgnoreCase("")) { // dictionary not empty
							System.out.println("Determined that word \""+min+"\" has lowest lexicographic ranking out of all entries in queue.");
							System.out.println();
							System.out.println("Priority queue after removing \""+min+"\": ");
							dictionary.printHeap();
							System.out.println("Total words in queue-based dictionary: "+dictionary.size());
							System.out.println();
						}else {
							System.out.println("Algorithm determined that queue was empty.");
							System.out.println();
						}
					}
					word_count++;
				} // end while: word_count == Math.floor((double)heapSize/2)
				
				// begin testing change-val method by replacing last half of the heap with previously removed words
				mid = (int)Math.floor((double)word_count/2);
				for(int i = 0; i < mid; i++) {
					dictionary.change_val((dictionary.size()-1)-i, test_words[i]);
					// testing purposes-- print sample runs of select sizes 
					if(heapSize == 10 || heapSize == 20 || heapSize == 50) {
						System.out.println("Priority queue after changing word at index "+((dictionary.size()-1)-i)+" to word \""+test_words[i]+"\": ");
						dictionary.printHeap();
						System.out.println("Total words in queue-based dictionary: "+dictionary.size());
						System.out.println();
					}
				}
				
				// continue removing root from heap until first attempt to remove root from empty heap
				while(word_count <= heapSize) {
					// testing purposes-- print sample runs of select sizes
					if(heapSize == 10 || heapSize == 20 || heapSize == 50) {
						System.out.println("Queue before attempting to remove word that comes first in alphabetical order: ");
						dictionary.printHeap();
						System.out.println("Total words in queue-based dictionary: "+dictionary.size());
						System.out.println();
					}
					
					String min = dictionary.remove_min(); // attempt extracting root
					
					// testing purposes-- print sample runs of select sizes
					if(heapSize == 10 || heapSize == 20 || heapSize == 50) {
						// print results
						if (!min.equalsIgnoreCase("")) { // dictionary not empty
							System.out.println("Determined that word \""+min+"\" has lowest lexicographic ranking out of all entries in queue.");
							System.out.println();
							System.out.println("Priority queue after removing \""+min+"\": ");
							dictionary.printHeap();
							System.out.println("Total words in queue-based dictionary: "+dictionary.size());
							System.out.println();
						}else {
							System.out.println("Algorithm determined that queue was empty.");
							System.out.println();
						}
					}
					word_count++;
				}
				n++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Reached EoF for problem size n="+n);
			e.printStackTrace();
		}
	}

// class methods
	public int size() {
		/* Returns total entries in the calling heap queue*/
		return this.total;
	}
	
	public int capacity() {
		/* Returns length of the array implementing the calling heap queue*/
		return this.cap;
	}
	
	
	public int parent(int i) {
		/* Returns index of parent of node A[i]; index = ceil(i/2) -1 if i is valid index and not 
		 * root node; else returns -1*/
		if (i > 0 && i < this.total) {
			return (int)(Math.ceil((double)i/2) -1); 
		}
		return -1;
	}
	
	public int left(int i) {
		// Returns index of left child of node A[i] if one exists; else, return -1 
		// left index = 2*i+1
		// left child doesn't exist if i >= floor(total/2)
		if(i >= 0 && i < Math.floor((double)this.total/2)) {
			return 2*i+1;
		}
		return -1;
	}
	
	public int right(int i) {
		// Returns index of right child of node A[i] if one exists; else, return -1 
		// right index = 2(i+1);
		// right child doesn't exist if i >= floor((total-1)/2)
		if(i >= 0 && i < Math.floor((double)(this.total-1)/2)) {
			return 2*(i+1);
		}
		return -1;
	}
	
	public void min_heapify(int i) {
		/*Recursively exchanges value of nodes A[i] and min(A[left(i)], A[right(i)]) in heap queue as necessary to maintain min-heap structure*/
		if (i>=0 && i < this.total) { // check that index i is valid index
			int l = this.left(i); // index of left child of node A[i]
			int r = this.right(i); // index of right child of node A[i]
			int least = i; // index of entry with largest value initialized as i
			
			if (l != -1) { // A[i] has left child
				if (this.A[i].compareToIgnoreCase(this.A[l]) > 0){ // A[i] comes after A[l] alphabetically
					least = l;
				}
			}
			if (r!= -1) { // A[i] has left child
				if (this.A[least].compareToIgnoreCase(this.A[r]) > 0){ // min(A[i], A[l]) comes after A[r] alphabetically
					least = r;
				}
			}
			if (least != i) { // need to swap A[i] with least child
				this.swap(i, least); // swap values of A[i] and A[least]
				this.min_heapify(least); // reposition A[largest] to maintain min-heap structure
			}
			
		}
	}
	
/*	public void build_min_heap() {
		/*Rearranges entries of A[] as necessary in order to obtain min-heap priority structure.*/
		
		// begin at last node that has children and work our way up
	/*	for (int i = (int)Math.ceil(this.total/2)-1; i >= 0; i--) { // first leaf node is at index ceil(total/2)
			this.min_heapify(i); // reposition node i relative to values of children
		}
	}*/
	
	public String remove_min() {
		/*Remove string in heap that comes first in alphabetical order, if not empty;
		 * else, returns empty string.
		 * In heap-based priority queue, "min" node is root A[0].*/
		if(this.total > 0) { // Heap not empty
			String min = this.A[0];
			
			// insert last node into root position; call min_heapify to reposition new root to maintain
			// min heap structure if necessary
			this.A[0] = this.A[this.total-1];
			this.A[this.total-1] = ""; // erase previous content of last node
			this.total--; //decrement total nodes
			this.min_heapify(0);
			return min;
		}else { // heap is empty
			System.out.println("Queue is empty.");
		}
		return "";
	}
	
	public void add_word(String key) {
		/*Adds key to proper position in heap if key does not already exist in heap; else does nothing*/
		if (this.total < this.cap) { // we have room to support insertion
			if(!this.search_heap(key)) { // key is not in heap
				this.total++; // increment total words
				this.A[this.total-1] = key; // add key to end of heap
				this.decrease_val(this.total-1, key); // adjust position of key relative to parent(s) to maintain min-heap structure
			}
		}else { // heap is full capacity
			System.out.println("Queue is full; cannot add words at this time.");
		}
	}
	
	public void decrease_val(int index, String key) {
		/*Exchanges values of nodes A[index] == key and A[parent(index)] as necessary to 
		 * maintain min-heap structure*/
		if(index > 0 && index< this.total) {
			int par = parent(index);
			while(index > 0 && this.A[par].compareToIgnoreCase(this.A[index]) > 0) { // child comes before parent alphabetically
				this.swap(index, par); // swap values of A[index] and A[par]
				index = par;
				par = parent(index);
			}
		}	
	}
	
	public void change_val(int index, String key) {
		/*Change the value of entry A[index] to key, then repositions A[index] relative to 
		 * min-heap structure if necessary*/
		if(index >=0 && index < this.total) { // check that index is valid 
			String temp = this.A[index];
			this.A[index] = key;
			
			// testing purposes-- print sample runs of select sizes 
			if(this.cap == 10 || this.cap == 20 || this.cap == 50) {
				System.out.println("Heap queue dictionary before repositioning string \""+key+"\" at index "+index+": ");
				this.printHeap();
				System.out.println("Total words in queue-based dictionary: "+this.total);
				System.out.println();
			}
			// reposition key relative to min-heap structure
			// Assuming former A[index] is the root of its own min-heap, 
			// call min_heapify() if key >= A[index]; else call decrease_val()
			if (key.compareToIgnoreCase(temp) < 0) { // key < A[index]; call decrease_val()
				this.decrease_val(index, key);
			}else if (key.compareToIgnoreCase(temp) > 0){ // key > A[index]; call min_heapify()
				this.min_heapify(index);
			} // else, key == A[index]; already maintains correct priority
		}else {
			System.out.println("Error message from call to change_val("+index+", "+key+"): invalid index "+index);
		}
	}
	
	public boolean search_heap(String key) {
		/* Returns true if key is an entry of A[]; else returns false*/
		for(int i =0; i < this.total; i++) {
			if (key.compareToIgnoreCase(this.A[i]) == 0) { // strings are equal
				return true;
			}
		}
		return false;
	}
	
	public void swap(int i, int j) {
		// Swaps the values stored in A[i] and A[j]
		if (i >= 0 && j >= 0 && i < this.total && j < this.total) { // check that indices are valid
			String temp = this.A[i];
			this.A[i] = this.A[j];
			this.A[j] = temp;
		}
	}
	
	public void printHeap() {
		/* Prints entries of the calling heap queue*/
		if(this.total > 0) { // check that heap is not empty
			printArr(this.A, this.total);
		}
	}
	
	public static void printArr(String A[], int size){
		/* Prints entries of string array A[]*/
		for(int i = 0; i < size; i++){
			System.out.print(A[i]+"\n");
		}
	}

}
