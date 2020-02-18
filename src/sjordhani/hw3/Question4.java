package sjordhani.hw3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.princeton.cs.algs4.StdIn;

/** 
 * If you place the "tale.txt" file as a top-level file in your MyCS2223 project, it will be found
 */
public class Question4 {    
	
	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream(new File ("tale.txt")));
		String[] strings = StdIn.readAllStrings();

		// First Construct the Binary Search Tree from these Strings where
		// the associated value is the total number of times the key appeared
		// in "The Tale Of Two Cities".
		BST<String,Integer> bt = new BST<String,Integer>();
	    for(String i:strings) {
	    	if(bt.get(i) == null) {
	    		bt.put(i, 1);
	    	}
	    	else {
	    		bt.put(i, (bt.get(i)+1));
	    	}
	    }
		// Now output the number of leaves in the tree.
		System.out.println(bt.numLeaves() + " leaves in the tree.");
		for(int k = 0; k < 10; k++) {
			System.out.println(bt.maxValue() + "\t" + bt.get(bt.maxValue()));
			bt.delete(bt.maxValue());
		}
	}
	
}
