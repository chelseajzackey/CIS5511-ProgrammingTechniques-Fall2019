
public class BTree_Client {
	
	public static int bSearch(int A[], int key, int l, int r) {
		/*Method that performs binary search on sorted (nondecreasing order) integer (sub)array A[l, ..., r];
		 * Returns index i such that A[i] = key, if key is in A; else, returns -1*/

		// l is leftmost index of subarray A[l, ..., r]
		// r is rightmost index of subarray A[l, ..., r]
		//int m;
		if (A[r] < key) {
			return r+1;
		}
		while(l < r) {
			int m = (int)Math.floor((l+r)/2);
			if (A[m] >= key) {
				r = m;
			}else {
				l = m+1;
			}
		}
		return l;
	}
	public static void printArr(int[] a) {
		String n = "[ | ";
		for (int i = 0; i < a.length; i++) {
			n = n +a[i]+" | ";
		}
		n +="]";
		System.out.println(n);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A = {11, 13, 17, 28, 34, 55, 60, 66, 73, 81, 88, 115};
		System.out.print("Array A: ");
		printArr(A);
		System.out.println("Return from binSearch 18: "+bSearch(A, 18, 0, A.length-1));
		System.out.println("Return from binSearch 9: "+bSearch(A, 9, 0, A.length-1));
		System.out.println("Return from binSearch 15: "+bSearch(A, 15, 0, A.length-1));
		System.out.println("Return from binSearch 34: "+bSearch(A, 34, 0, A.length-1));
		System.out.println("Return from binSearch 60: "+bSearch(A, 60, 0, A.length-1));
		System.out.println("Return from binSearch 80: "+bSearch(A, 80, 0, A.length-1));
		System.out.println("Return from binSearch 43: "+bSearch(A, 43, 0, A.length-1));
		System.out.println("Return from binSearch 67: "+bSearch(A, 67, 0, A.length-1));
		System.out.println("Return from binSearch 115: "+bSearch(A, 115, 0, A.length-1));
		System.out.println("Return from binSearch 114: "+bSearch(A, 114, 0, A.length-1));
		System.out.println("Return from binSearch 130: "+bSearch(A, 130, 0, A.length-1));
	}

	
}
