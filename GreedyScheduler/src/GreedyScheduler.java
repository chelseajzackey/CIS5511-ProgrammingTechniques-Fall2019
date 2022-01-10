
public class GreedyScheduler {
	private int cmpltn; // global completion time 
	private Task[] A; // array implementing min heap priority queue
	private int cap; // max allowable tasks in queue
	private int total; // total number of tasks in queue currently
	//static int comps = 0; // tracks number of comparisons made for each algorithm
	//static int assigns = 0; // tracks number of assignments made for each algorithm


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] T = {"a_1", "a_2", "a_3", "a_4", "a_5", "a_6", "a_7", "a_8"};
		int[] p = {12, 8, 7, 6, 14, 3, 9, 2};
		System.out.println("Creating new Greedy Scheduler with following attributes: ");
		System.out.print("Tasks: ");
		for (int i = 0; i < T.length; i++) {
			System.out.print(T[i]+" ");
		}
		System.out.print("\nProcessing times: ");
		for (int i = 0; i < p.length; i++) {
			System.out.print(p[i]+" ");
		}
		GreedyScheduler G = new GreedyScheduler(T, p);
		System.out.println("\nSolving for this problem...\n");
		G.SolveSchedule(T, p);
	}

	// class methods
	public GreedyScheduler(String T[], int[] P) { // T is array of task IDs, P is array of processing times
		cmpltn = 0; 
		A = new Task[T.length];
		cap = T.length;
		total = 0;
	}
	
	public double aveCompletion() {
		return (double)cmpltn/cap;
	}
	public Task[] SolveSchedule(String T[], int[] P) {
		Task[] schedule = new Task[T.length];
		// Initialize populate queue A
		for (int i = 0; i < this.cap; i++) {
			Task t = new Task(T[i], P[i]);
			this.add(t);
		}
		int j = 0;
		while(this.total > 0) { // queue not empty 
			Task t = this.remove_min();
			cmpltn += t.p;
			t.c = cmpltn;
			schedule[j] = t;
			j++;
		}
		this.printSoln(schedule);
		return schedule;
	}
	
	public void printSoln(Task[] schedule) {
		System.out.println("Printing solution for this problem...\n");
		System.out.println("Schedule generated: ");
		printArr(schedule, schedule.length);
		System.out.println("Ave completion time: "+this.aveCompletion());
	}
	
	// heap queue methods
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
				if (this.A[i].p > this.A[l].p){ // A[i] comes after A[l] alphabetically
					least = l;
				}
			}
			if (r!= -1) { // A[i] has left child
				if (this.A[least].p > this.A[r].p){ // min(A[i], A[l]) comes after A[r] alphabetically
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

	public Task remove_min() {
		/*Remove string in heap that comes first in alphabetical order, if not empty;
		 * else, returns empty string.
		 * In heap-based priority queue, "min" node is root A[0].*/
		if(this.total > 0) { // Heap not empty
			Task min = this.A[0];

			// insert last node into root position; call min_heapify to reposition new root to maintain
			// min heap structure if necessary
			this.A[0] = this.A[this.total-1];
			this.A[this.total-1] = null; // erase previous content of last node
			this.total--; //decrement total nodes
			this.min_heapify(0);
			return min;
		}else { // heap is empty
			System.out.println("Queue is empty.");
		}
		return null;
	}

	public void add(Task key) {
		/*Adds key to proper position in heap if key does not already exist in heap; else does nothing*/
		if (this.total < this.cap) { // we have room to support insertion
			this.total++; // increment total words
			this.A[this.total-1] = key; // add key to end of heap
			this.decrease_val(this.total-1, key); // adjust position of key relative to parent(s) to maintain min-heap structure
		}else { // heap is full capacity
			System.out.println("Queue is full; cannot add words at this time.");
		}
	}

	public void decrease_val(int index, Task key) {
		/*Exchanges values of nodes A[index] == key and A[parent(index)] as necessary to 
		 * maintain min-heap structure*/
		if(index > 0 && index< this.total) {
			int par = parent(index);
			while(index > 0 && this.A[par].p > this.A[index].p) { // child comes before parent alphabetically
				this.swap(index, par); // swap values of A[index] and A[par]
				index = par;
				par = parent(index);
			}
		}	
	}

	public void change_val(int index, Task key) {
		/*Change the value of entry A[index] to key, then repositions A[index] relative to 
		 * min-heap structure if necessary*/
		if(index >=0 && index < this.total) { // check that index is valid 
			Task temp = this.A[index];
			this.A[index] = key;

			// testing purposes-- print sample runs of select sizes 
			/*if(this.cap == 10 || this.cap == 20 || this.cap == 50) {
					System.out.println("Heap queue dictionary before repositioning string \""+key+"\" at index "+index+": ");
					this.printHeap();
					System.out.println("Total words in queue-based dictionary: "+this.total);
					System.out.println();
				}*/
			// reposition key relative to min-heap structure
			// Assuming former A[index] is the root of its own min-heap, 
			// call min_heapify() if key >= A[index]; else call decrease_val()
			if (key.p < temp.p) { // key < A[index]; call decrease_val()
				this.decrease_val(index, key);
			}else if (key.p  > temp.p){ // key > A[index]; call min_heapify()
				this.min_heapify(index);
			} // else, key == A[index]; already maintains correct priority
		}else {
			System.out.println("Error message from call to change_val("+index+", "+key+"): invalid index "+index);
		}
	}


	public void swap(int i, int j) {
		// Swaps the values stored in A[i] and A[j]
		if (i >= 0 && j >= 0 && i < this.total && j < this.total) { // check that indices are valid
			Task temp = this.A[i];
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

	public static void printArr(Task A[], int size){
		/* Prints entries of string array A[]*/
		for(int i = 0; i < size; i++){
			System.out.print(A[i].toString());
		}
	}

	protected class Task{
		private String ID; // task ID
		private int p; // processing time
		private int c; // task's expected completion time

		protected Task(String name, int process) {
			this.ID = name;
			this.p = process;
			this.c = 0; // to be determined by solution algorithm
		}

		@Override 
		public String toString() {
			return "Task ID: "+this.ID+"\nProcessing time: "+this.p+"\nExpected completion time: "+this.c+"\n\n";
		}
	}

}
