package ds2;

/*
 * Written by John Rogers
 * Modified by Kehinde Abioye
 */
import algs4.Queue;
import algs4.StdDraw;

/*
 * This is a simplified version of the BST class
 * from the algs4 package.  Students will modify
 * this as part of Assignment 5.
 *
 */

public class A5BST<Key extends Comparable<? super Key>, Value> {

	private Node<Key,Value> root;           // root of BST
	private int size;

	private static class Node<Key extends Comparable<? super Key>,Value> {
		public Key key;       				// sorted by key
		public Value val;             		// associated data
		public Node<Key,Value> left, right; // left and right subtrees

		public Node(Key key, Value val) {
			this.key = key;
			this.val = val;
		}
	}

	public A5BST() {
		this.root = null;
		this.size = 0;
	}

	/* *********************************************************************
	 * Return the number of key-values pairs in the BST.
	 ***********************************************************************/
	public int size() { return this.size; }
	
	/* *********************************************************************
	 * Does there exist a key-value pair with given key?
	 ***********************************************************************/
	public boolean contains(Key key) {
		return get(key) != null;
	}

	/* *********************************************************************
	 *  Search BST for given key, and return associated value if found,
	 *  return null if not found (recursive version)
	 ***********************************************************************/

	public Value get(Key key) { return get(root, key); }

	private Value get(Node<Key,Value> x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) return get(x.left, key);
		else if (cmp > 0) return get(x.right, key);
		else              return x.val;
	}
	
	/* **************************************************************************
	 *  Methods for counting the number of leaves in a BST. Add these 
	 *  methods to the class A5BST. Call the methods countLeaves written 
	 *  (as seen before) as a public method that calls a private recursive method.
     *
     *  The number of leaves can be computed using this definition:
     *
     *  if the node is null, return 0
     *  if both the child references are null, return 1
     *  return countLeaves applied to the left subtree plus countLeaves applied 
     *  to the right subtree
	 ****************************************************************************/
	
	public int countLeaves() { return countLeaves(root); }

	private int countLeaves(Node<Key,Value> x) {
		if (x == null) return 0;
		if (x.left == null && x.right==null) return 1;
		return countLeaves(x.left)+countLeaves(x.right);
	}
	
	/* *********************************************************************
	 * innerCount() that returns the number of non-leaf nodes, that is, 
	 * inner nodes in the tree. This is a very simple method that calculates 
	 * the difference between the size of the BST and the number of leaves.
	 ***********************************************************************/
	public int innerCount() { 
		return this.size - countLeaves();
	}
	
	
	/* *********************************************************************
	 *  Search BST for given key, and return associated value if found,
	 *  return null if not found (iterative version)
	 ***********************************************************************/
	/*
	public Value get(Key key) {
		Node<Key,Value> x = root;
		while (x != null) {
			int cmp = key.compareTo(x.key);
			if      (cmp < 0) x = x.left;
			else if (cmp > 0) x = x.right;
			else              return x.val;
		}
		return null;
	}
	*/
	/* *********************************************************************
	 *  Insert key-value pair into BST
	 *  If key already exists, update with new value
	 ***********************************************************************/
	public void put(Key key, Value val) {
		if (val == null) { delete(key); return; }
		root = put(root, key, val);
	}

	private Node<Key,Value> put(Node<Key,Value> x, Key key, Value val) {
		if (x == null) {
			this.size++;
			return new Node<>(key, val);
		}
		int cmp = key.compareTo(x.key);
		if      (cmp < 0)
			x.left  = put(x.left,  key, val);
		else if (cmp > 0)
			x.right = put(x.right, key, val);
		else
			x.val   = val;
		return x;
	}

	/* *********************************************************************
	 *  Delete a key-value pair, given the key.
	 ***********************************************************************/

	public void delete(Key key) {
		root = delete(root, key);
	}

	private Node<Key,Value> delete(Node<Key,Value> x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = delete(x.left,  key);
		else if (cmp > 0) x.right = delete(x.right, key);
		else {
			this.size--;
			// x is the node to be deleted.
			// The value returned in each of these cases below
			// becomes the value of the child reference from
			// the parent of x.  Returning a null makes that
			// reference a null and so cuts x off, causing its
			// automatic deletion.

			// Determine how many children x has.
			if (x.right == null && x.left == null){
				// This is a leaf node.  Change its
				// reference from its parent to null.
				return null;
			} else if (x.right == null) {
				// One child, to the left.  Make that
				// the new child of x's parent.
				return x.left;
			} else if (x.left == null) {
				// One child, to the right.  Make that
				// the new child of x's parent.
				return x.right;
			} else {
				// Node x has two children.
				// Find the node in x's left subtree with
				// the maximum key.
				Node<Key,Value> leftTreeMaxNode = findMax(x.left);
				// Copy the key and value from that maximum
				// key node to x, thereby overwriting x's
				// original key and value.
				x.key = leftTreeMaxNode.key;
				x.val = leftTreeMaxNode.val;
				// Delete the node copied from.
				x.left = delete(x.left, leftTreeMaxNode.key);
			}
		}
		return x;
	}

	private Node<Key,Value> findMax(Node<Key,Value> x) {
		Node<Key,Value> temp = x;
		// Follow right children until you get to
		// a node with no right child.  That node
		// has the maximum key in the tree rooted at x.
		while (temp.right != null) {
			temp = temp.right;
		}
		return temp;
	}

	/*
	 * This method produces an iterable object,
	 * in this case a queue, containing the keys
	 * in the tree in ascending order.
	 */
	public Iterable<Key> keys() {
		Queue<Key> q = new Queue<>();
		keys(root, q);
		return q;
	}

	/*
	 * This is an in-order traversal:
	 * 1. Visit the left sub-tree
	 * 2. Visit the node x
	 * 3. Visit the right sub-tree 
	 */
	private void keys(Node<Key,Value> x, Queue<Key> q) {
		if (x == null) return;
		keys(x.left, q);
		q.enqueue(x.key);
		keys(x.right, q);
	}
	
	/*
	 * This method counts and returns the number
	 * of key-value pairs having a particular value.
	 */
	public int countValue(Value value) {
		return countValue(root, value);
	}
	
	/*
	 * This is an pre-order traversal:
	 * 1. Visit the node x
	 * 2. Visit the left sub-tree
	 * 3. Visit the right sub-tree 
	 */
	private int countValue(Node<Key,Value> x, Value value) {
		if (x == null) return 0;
		
		int count = 0;
		if (x.val.equals(value)) count++;
		count += countValue(x.left, value);
		count += countValue(x.right, value);
		return count;
	}

	/*
	 * We define the height of the tree to be the height
	 * of the root node.  We define the height of a node 
	 * to be the maximum of the heights of its children plus 1.
	 * To make this work, we define the height of a missing 
	 * child to be -1.
	 */
	public int height() {
		return height(root);
	}

	/*
	 * This a post-order traversal:
	 * 1. Visit left sub-tree
	 * 2. Visit right sub-tree
	 * 3. Visit the node x
	 */
	private int height(Node<Key,Value> x) {
		if (x == null) return -1;
		int leftHeight = height(x.left);
		int rightHeight = height(x.right);
		return 1+Math.max(leftHeight, rightHeight);
	}

	/* ***************************************************************************
	 *  Visualization
	 *****************************************************************************/

	public void drawTree() {
		if (root != null) {
			StdDraw.setPenColor (StdDraw.BLACK);
			StdDraw.setCanvasSize(1200,700);
			drawTree(root, .5, 0.95, .25, 0);
		}
	}
	private void drawTree (Node<Key,Value> n, double x, double y, double range, int depth) {
		int CUTOFF = 10;
		StdDraw.text (x, y, n.key.toString()+":"+n.val.toString());
		StdDraw.setPenRadius (.007);
		if (n.left != null && depth != CUTOFF) {
			StdDraw.line (x-range, y-.08, x-.01, y-.01);
			drawTree (n.left, x-range, y-.1, range*.5, depth+1);
		}
		if (n.right != null && depth != CUTOFF) {
			StdDraw.line (x+range, y-.08, x+.01, y-.01);
			drawTree (n.right, x+range, y-.1, range*.5, depth+1);
		}
	}
}
