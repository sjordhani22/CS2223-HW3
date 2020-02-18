package sjordhani.hw3;

import edu.princeton.cs.algs4.StdOut;

/**
 *  The {@code Heap} class provides a static method to constructHeap.
 *  
 *  This is the first step in HeapSort, and all that is being considered for 
 *  Question 1.
 *  
 *  You will need to modify this class to count # of exchanges, 
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @modified George Heineman
 */
public class Heap {
	static int numExch = 0;
	static int numLess = 0;
	/**
	  * Rearranges the array in ascending order, using the natural order.
	  * @param a the array to be sorted
	  */
	 public static void constructHeap(Comparable[] a) {
		 int n = a.length;
		 numExch = 0;
		 numLess = 0;
		 
		 
		 // construct heap from the raw array of which we know nothing.
		 // This is the focus of Question 1 on the homework.
		 for (int k = n/2; k >= 1; k--) {
			 sink(a, k, n);
		 }
		 
		 // at this point, a has been turned into a heap.
		 // For this question, we are not concerned with fully sorting...
		 // while (n > 1) {
		 //	 exch(a, 1, n--);
		 //	 sink(a, 1, n);
		 // }
	 }

	 /***************************************************************************
	  * Helper functions to restore the heap invariant.
	  ***************************************************************************/

	 private static void sink(Comparable[] pq, int k, int n) {
		 while (2*k <= n) {
			 int j = 2*k;
			 if (j < n && less(pq, j, j+1)) j++;
			 if (!less(pq, k, j)) break;
			 exch(pq, k, j);
			 k = j;
		 }
	 }

	 /***************************************************************************
	  * Helper functions for comparisons and swaps.
	  * Indices are "off-by-one" to support 1-based indexing.
	  ***************************************************************************/
	 private static boolean less(Comparable[] pq, int i, int j) {
		 numLess++;
		 return pq[i-1].compareTo(pq[j-1]) < 0;
	 }

	 private static void exch(Object[] pq, int i, int j) {
		 numExch++;
		 Object swap = pq[i-1];
		 pq[i-1] = pq[j-1];
		 pq[j-1] = swap;
	 }

	 // print array to standard output
	 private static void show(Comparable[] a) {
		 for (int i = 0; i < a.length; i++) {
			 StdOut.println(a[i]);
		 }
	 }

 }
