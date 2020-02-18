package sjordhani.hw3;

import edu.princeton.cs.algs4.StdOut;

public class BST<Key extends Comparable<Key>, Value extends Comparable<Value>> {

	Node root; // root of the tree
	Node best; // This will be used for maxValue

	class Node {
		Key key;
		Value val;
		Node left, right; // left and right subtrees
		int N; // number of nodes in subtree

		public Node(Key key, Value val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}

		public String toString() {
			return "[" + key + "]";
		}
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public String toString() {
		return "<bst: root=" + root + ", N=" + size(root) + ">";
	}

	/** Return number of key-value pairs in ST. */
	public int size() {
		return size(root);
	}

	// Helper method that deals with "empty nodes"
	private int size(Node node) {
		if (node == null)
			return 0;

		return node.N;
	}

	// One-line method for containment.
	public boolean contains(Key key) {
		return get(key) != null;
	}

	/** Search parent. */
	public Value get(Key key) {
		return get(root, key);
	}

	private Value get(Node parent, Key key) {
		if (parent == null)
			return null;

		int cmp = key.compareTo(parent.key);

		if (cmp < 0)
			return get(parent.left, key);
		else if (cmp > 0)
			return get(parent.right, key);
		else
			return parent.val;
	}

	/** Invoke put on parent, should it exist. */
	public void put(Key key, Value val) {
		root = put(root, key, val);

	}

	private Node put(Node parent, Key key, Value val) {
		if (parent == null)
			return new Node(key, val, 1);

		int cmp = key.compareTo(parent.key);
		if (cmp < 0)
			parent.left = put(parent.left, key, val);
		else if (cmp > 0)
			parent.right = put(parent.right, key, val);
		else
			parent.val = val;

		parent.N = 1 + size(parent.left) + size(parent.right);
		return parent;
	}

	// new methods for discussion
	public Key min() {
		return min(root).key;
	}

	private Node min(Node parent) {
		if (parent.left == null) {
			return parent;
		}
		return min(parent.left);
	}

	public Key nonRecursiveMin() {
		Node n = root;

		while (n.left != null) {
			n = n.left;
		}

		return n.key;
	}

	public Key floor(Key key) {
		Node rc = floor(root, key);
		if (rc == null)
			return null;
		return rc.key;
	}

	private Node floor(Node parent, Key key) {
		if (parent == null)
			return null;

		int cmp = key.compareTo(parent.key);
		if (cmp == 0)
			return parent; // found? Then this is floor
		if (cmp < 0)
			return floor(parent.left, key); // smaller? must be in left subtree

		Node t = floor(parent.right, key); // greater? we might be floor, but
		if (t != null)
			return t; // only if
		else
			return parent;
	}

	// traversal ideas
	// invoke an inorder traversal of the tree
	public void inorder() {
		inorder(root);
	}

	private void inorder(Node n) {
		if (n == null) {
			return;
		}

		inorder(n.left);
		StdOut.println(n.key);
		inorder(n.right);
	}

	/**
	 * Return the number of leaves in the binary tree. You must complete this
	 * method. Consider adapting the logic you find in the traversals. Add helper
	 * methods if needed.
	 */
	public int numLeaves() {
		return findLeaves(root);
	}

	// This is the helper method for numLeaves
	public int findLeaves(Node node) {
		if (node == null) {
			return 0;
		} else if (node.left == null && node.right == null) {
			return 1;
		} else {
			return (findLeaves(node.left) + findLeaves(node.right));
		}
	}

	/**
	 * Return the key that contains the maximum value in the tree. You must complete
	 * this method. Consider adapting the logic you find in the traversals. Add
	 * helper methods if needed.
	 * 
	 * Hint: create a helper method
	 * 
	 * private Node maxValue(Node n, Node best) { ... }
	 * 
	 * where 'best' is the node containing the maximum value seen so far...
	 */
	public Key maxValue() {
		best = root;
		return maxValHelper(root);
	}

	public Key maxValHelper(Node node) {
		Node current = node;
		if (best != null) {
			if (current.val.compareTo(best.val) > 0) {
				best = node;
			}
		} else {
			return null;
		}
		if (current.left != null) {
			maxValHelper(node.left);
		}
		if (current.right != null) {
			maxValHelper(node.right);
		}
		return best.key;
	}

	// traversal ideas
	// invoke a pre-order traversal of the tree
	public void preorder() {
		preorder(root);
	}

	private void preorder(Node n) {
		if (n == null) {
			return;
		}
		StdOut.println(n.key);

		preorder(n.left);
		preorder(n.right);
	}

	/** Implement method to return Value when removing largest element. */
	public void deleteMin() {
		if (root != null) {
			root = deleteMin(root);
		}
	}

	Node deleteMin(Node parent) {
		if (parent.left == null) {
			return parent.right;
		}

		parent.left = deleteMin(parent.left);
		parent.N = size(parent.left) + size(parent.right) + 1;
		return parent;
	}

	public void delete(Key key) {
		root = delete(root, key);
	}

	private Node delete(Node parent, Key key) {
		if (parent == null)
			return null;

		// recurse until you find parent with this key.
		int cmp = key.compareTo(parent.key);
		if (cmp < 0)
			parent.left = delete(parent.left, key);
		else if (cmp > 0)
			parent.right = delete(parent.right, key);
		else {
			// handle easy cases first:
			if (parent.right == null)
				return parent.left;
			if (parent.left == null)
				return parent.right;

			// has two children: Plan on returning min of our right child
			Node old = parent;
			parent = min(old.right); // will eventually be "new parent"

			// Note this is a simpler case: Delete min from right subtree
			// and DON'T FORGET to stitch back in the original left child
			parent.right = deleteMin(old.right);
			parent.left = old.left;
		}

		// as recursions unwind, pass back potential new parent
		return parent;
	}
}
