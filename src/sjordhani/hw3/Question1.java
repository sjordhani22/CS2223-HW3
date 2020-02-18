package sjordhani.hw3;

import edu.princeton.cs.algs4.StdRandom;

/**
 * This is the template code for question 1.
 *
 * Be sure to Explain whether the empirical results support the proposition.
 *
 */
public class Question1 {
	public static void main(String[] args) {
		System.out.println("N" + "\t" + "MaxComp" + "\t" + "MaxExch");
		
		for(int N = 16; N <= 512; N = N*2) {
			
			int maxExchanges = 0;
			int maxComparisons = 0;
			Comparable[] Arr = new Comparable[N];
			
			for(int T = 0; T < 100; T++) {
				for(int i = 0; i < N; i++) {
					Arr[i] = StdRandom.uniform();
				}
				Heap.constructHeap(Arr);
				
				maxExchanges = Heap.numExch;
				maxComparisons = Heap.numLess;
			}
			System.out.println(N + "\t" + maxComparisons + "\t" + maxExchanges);
		}
		// for N in 16 .. 512
		
		//   for each N, do T=100 trials you want to keep track of 
		//       what you believe to be the MOST number of exch invocations
		//       and most number of less invocations
		
		//       compute a random array of N uniform doubles
		
		//   Make sure you output for each N the maximum values you saw
		//   in a table like...
		//
		//       N   MaxComp    MaxExch
		//       16  22         8
		//     .....
	}
}
