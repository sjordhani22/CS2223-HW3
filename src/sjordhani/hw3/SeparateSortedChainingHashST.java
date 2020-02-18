package sjordhani.hw3;

import java.util.Arrays;

import edu.princeton.cs.algs4.*;

/**
 * This class can remain AS IS without modification.
 *
 * Modification April-5-2019: Added logic to show the longest linked list used for a given 
 * bin. If you run "without modifications" this list appears in an unsorted order. Once you
 * have completed this assignment, the output will be in sorted order.
 * 
 * @param <Key>
 * @param <Value>
 */
public class SeparateSortedChainingHashST<Key extends Comparable<Key>, Value> {
	int N;                                // number of key-value pairs
	int M;                                // hash table size
	SequentialSearchSortedST<Key, Value>[] st;  // array of linked-list symbol tables

	final static int INIT_CAPACITY = 11;   // initial default size
	final static int AVG_LENGTH = 7;      // Threshold to determine resizing

	/** Initialize empty symbol table with <tt>M</tt> chains. */
	public SeparateSortedChainingHashST(int M) {
		this.M = M;
		st = (SequentialSearchSortedST<Key, Value>[]) new SequentialSearchSortedST[M];
		for (int i = 0; i < M; i++) {
			st[i] = new SequentialSearchSortedST<Key, Value>();
		}
	} 

	/** Choose initial default size. */
	public SeparateSortedChainingHashST() { this(INIT_CAPACITY); } 

	// resize the hash table to have the given number of chains by rehashing all of the keys
	void resize(int chains) {
		SeparateSortedChainingHashST<Key, Value> temp = new SeparateSortedChainingHashST<Key, Value>(chains);
		for (int i = 0; i < M; i++) {
			SequentialSearchSortedST<Key,Value>.Node node = st[i].first;
			while (node != null) {
				temp.put(node.key, node.value);
				node = node.next;
			}
		}

		M  = temp.M;
		N  = temp.N;
		st = temp.st;
	}

	// hash value between 0 and M-1
	int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % M;
	} 

	public int size()                { return N; } 
	public boolean isEmpty()         { return size() == 0; }
	public boolean contains(Key key) { return get(key) != null; } 

	public Value get(Key key) {
		int i = hash(key);
		return st[i].get(key);
	} 

	public void put(Key key, Value val) {
		// double table size if average length of list >= AVG_LENGTH
		if (N >= AVG_LENGTH*M) resize(2*M+1);

		int i = hash(key);
		if (!st[i].contains(key)) N++;
		st[i].put(key, val);
	} 

	public void delete(Key key) {
		int i = hash(key);
		if (st[i].contains(key)) { N--; }
		st[i].delete(key);

		// halve table size if average length of list <= 2M
		if (M > INIT_CAPACITY && N <= 2*M) resize(M/2);
	} 

	public static void main(String[] args) {
		SeparateSortedChainingHashST<String, String> table = new SeparateSortedChainingHashST<String, String>();

		System.out.println("Sample Hash Code Values:");
		String s = "Hello";
		System.out.println(s + " gives " + s.hashCode());
		s = "aaaaaa";
		System.out.println(s + " gives " + s.hashCode());
		
		In in = new In ("words.english.txt");
		StopwatchCPU create = new StopwatchCPU();
		while (!in.isEmpty()) {
			String word = in.readString(); 
			table.put(word, word);      // You can use value as the key
		}
		in.close();
		StdOut.printf("Elapsed time %.2f seconds\n", create.elapsedTime());
		StdOut.println(table.size() + " words in the hash table.");

		// number of empty bins
		// largest chain length
		// number of size 1
		int numEmpty = 0;
		int maxChain = 0;  
		int numSingle = 0;
		int maxChainId = -1;
		int[] distribution = new int[30]; // more than enough
		for (int i = 0; i < table.M; i++) {
			if (table.st[i].isEmpty()) {
				numEmpty++;
			}

			if (table.st[i].size() > maxChain) {
				maxChain = table.st[i].size();
				maxChainId = i;
			}
			if (table.st[i].size() == 1) {
				numSingle++;
			}
			
			distribution[table.st[i].size()]++;
		}

		double percent = 100*(1-(numEmpty*1.0)/table.M);
		StdOut.println("Table has " + table.M + " indices.");
		StdOut.println("  there are " + numEmpty + " empty indices " + percent + "%");
		StdOut.println("  maximum chain is " + maxChain);
		StdOut.println("  number of single is " + numSingle);
		StdOut.println("  size distribution:" + Arrays.toString(distribution));
		
		// longest chain is:
		StdOut.println ("longest chain [" + maxChainId + "] is");
		SequentialSearchSortedST.Node n = table.st[maxChainId].first;
		while (n != null) {
			StdOut.println (n.key);
			n = n.next;
		}
		
		// validate we can reduce
		in = new In ("words.english.txt");
		while (!in.isEmpty()) {
			String word = in.readString(); 
			table.delete(word);
		}
		in.close();

		StdOut.println("Empty Table has " + table.M + " indices.");
	}
}