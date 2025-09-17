
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T>{
	
	BSTNode<T> root = null; //root node of tree
	
	/**
     * Performs the naive binary search tree insert algorithm to recursively
     * insert the provided newNode (which has already been initialized with a
     * data value) into the provided tree/subtree.  When the provided subtree
     * is null, this method does nothing. 
     */
	protected void insertHelper(BSTNode<T> newNode, BSTNode<T> subtree) {
	    if (newNode.getData().compareTo(subtree.getData()) <= 0) {
	        if (subtree.left == null) {
	            subtree.left = newNode;
	            newNode.setUp(subtree);  // Set the parent (up) reference for the new node
	        } else {
	            insertHelper(newNode, subtree.left);  // Recursive call for left subtree
	        }
	    } else {
	        if (subtree.right == null) {
	            subtree.right = newNode;
	            newNode.setUp(subtree);  // Set the parent (up) reference for the new node
	        } else {
	            insertHelper(newNode, subtree.right);  // Recursive call for right subtree
	        }
	    }
	}
    	
    /**
     * Inserts a new data value into the sorted collection.
     * @param data the new value being inserted
     * @throws NullPointerException if data argument is null, we do not allow
     * null values to be stored within a SortedCollection
     */
	@Override
	public void insert(T data) throws NullPointerException {
		
		if (data == null) {
			throw new NullPointerException("Data cannot be null");
		}
		
		BSTNode<T> node = new BSTNode<>(data);
		
		if(root == null) {
			//if tree is empty, insert new node
			root = node;
		}
		
		else {
			insertHelper(node, root);
		}
	}
	
	/**
     * Check whether data is stored in the tree.
     * @param data the value to check for in the collection
     * @return true if the collection contains data one or more times, 
     * and false otherwise
     */
	@Override
	public boolean contains(Comparable<T> data) {
		
		if(root == null) {
		return false; //tree empty
		}
		
		BSTNode<T> currentNode = root;
		
		while (currentNode != null) {
			int comp = data.compareTo(currentNode.getData());
			
			if(comp == 0) {
				return true;
			}
			//search left subtree if node is less than
			else if (comp < 0) {
				currentNode = currentNode.left;
			}
			//search right subtree if node is more than
			else if (comp > 0) {
				currentNode = currentNode.right;
			}
		}
		
		return false;
	}
	
	/**
     * Counts the number of values in the collection, with each duplicate value
     * being counted separately within the value returned.
     * @return the number of values in the collection, including duplicates
     */
	@Override
	public int size() {
		
		if (root == null) {
		return 0; //tree empty
		}
		
		return sizeHelper(root);
	}
	
	/**
	 * recursive method that traverses left and right subtrees in order to count size of BST
	 * @param node
	 * @return
	 */
	private int sizeHelper(BSTNode<T> node) {
		
		if (node == null) {
			return 0; 
		}
		
		// recursive call to count left & right subtree nodes as well as current node
		return 1 + sizeHelper(node.left) + sizeHelper(node.right);
	}
	
	/**
     * Checks if the collection is empty.
     * @return true if the collection contains 0 values, false otherwise
     */
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	/**
     * Removes all values and duplicates from the collection.
     */
	@Override
	public void clear() {
		root = null;
	}
	
	/**
	 * Tests methods on two different integer binary search trees
	 * @return true if tests pass, false otherwise
	 */
	public static boolean test1() {
		BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
		
		//bigger bst
		bst1.insert(6);
		bst1.insert(3);
		bst1.insert(8);
		bst1.insert(1);
		bst1.insert(4);
		bst1.insert(5);
		bst1.insert(9);
		
		//check size
		if (bst1.size() != 7) {
			System.out.println("Test 1 - Actual size: " + bst1.size());
			return false;
		}
		
		//check that bst isn't empty
		if (bst1.isEmpty()) {
			System.out.println("bst1 shouldn't be empty");
			return false;
		}
		
		if(!bst1.contains(6)) {
			System.out.println("bst1 should contain 6");
			return false;
		}
		
		if(!bst1.contains(3)) {
			System.out.println("bst1 should contain 3");
			return false;
		}
		
		if(!bst1.contains(8)) {
			System.out.println("bst1 should contain 8");
			return false;
		}
		
		if(!bst1.contains(1)) {
			System.out.println("bst1 should contain 1");
			return false;
		}
		
		if(!bst1.contains(4)) {
			System.out.println("bst1 should contain 4");
			return false;
		}
		
		if(!bst1.contains(5)) {
			System.out.println("bst1 should contain 5");
			return false;
		}
		
		if(!bst1.contains(9)) {
			System.out.println("bst1 should contain 9");
			return false;
		}
		
		//test clear method
		bst1.clear();
		
		if(!bst1.isEmpty()) {
			System.out.println("bst1 should be empty");
			return false;
		}
		
		if(bst1.contains(6)) {
			System.out.println("bst1 shouldn't contain 6 because it was cleared");
			return false;
		}
		
		//small bst
		bst1.insert(2);
		bst1.insert(1);
		bst1.insert(3);
		
		if(bst1.size() != 3) {
			System.out.println("Test 2 - Actual size: " + bst1.size());
			return false;
		}
		if(bst1.isEmpty()) {
			return false;
		}
		
		if(!bst1.contains(2)) {
			System.out.println("bst1 should contain 2");
			return false;
		}
		
		if(!bst1.contains(3)) {
			System.out.println("bst1 should contain 3");
			return false;
		}
		
		if(!bst1.contains(1)) {
			System.out.println("bst1 should contain 1");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Tests methods on two different string binary search trees
	 * @return true if tests pass, false otherwise
	 */
	public static boolean test2() {
		BinarySearchTree<String> bst2 = new BinarySearchTree<>();
		
		//bigger bst
		bst2.insert("H");//root node
		bst2.insert("F");//H's left child
		bst2.insert("K");//H's right child
		bst2.insert("C");//F's left child
		bst2.insert("G");//F's right child
		bst2.insert("I");//K's left child
		bst2.insert("N");//K's right child
		
		//check size
		if (bst2.size() != 7) {
			System.out.println("Test 1 - Actual size: " + bst2.size());
			return false;
		}
		
		//check that bst isn't empty
		if (bst2.isEmpty()) {
			System.out.println("bst2 shouldn't be empty");
			return false;
		}
		
		if(!bst2.contains("H")) {
			System.out.println("bst2 should contain H");
			return false;
		}
		
		if(!bst2.contains("F")) {
			System.out.println("bst2 should contain F");
			return false;
		}
		
		if(!bst2.contains("K")) {
			System.out.println("bst2 should contain K");
			return false;
		}
		
		if(!bst2.contains("C")) {
			System.out.println("bst2 should contain C");
			return false;
		}
		
		if(!bst2.contains("G")) {
			System.out.println("bst2 should contain G");
			return false;
		}
		
		if(!bst2.contains("I")) {
			System.out.println("bst2 should contain I");
			return false;
		}
		
		if(!bst2.contains("N")) {
			System.out.println("bst2 should contain N");
			return false;
		}
		
		//test clear method
		bst2.clear();
		
		if(!bst2.isEmpty()) {
			System.out.println("bst2 should be empty");
			return false;
		}
		
		if(bst2.contains("H")) {
			System.out.println("bst2 shouldn't contain H because it was cleared");
			return false;
		}
		
		//small bst
		bst2.insert("B");//root node
		bst2.insert("A");//left child
		bst2.insert("C");//right child
		
		if(bst2.size() != 3) {
			System.out.println("Test 2 - Actual size: " + bst2.size());
			return false;
		}
		if(bst2.isEmpty()) {
			return false;
		}
		
		if(!bst2.contains("B")) {
			System.out.println("bst2 should contain B");
			return false;
		}
		
		if(!bst2.contains("A")) {
			System.out.println("bst2 should contain A");
			return false;
		}
		
		if(!bst2.contains("C")) {
			System.out.println("bst2 should contain C");
			return false;
		}
		
		return true;
	}
	
	/**
	 * tests methods on two different shaped binary search trees
	 * @return true if tests pass, false otherwise
	 */
	public static boolean test3() {
		BinarySearchTree<Integer> bst3 = new BinarySearchTree<>();
		
		//bst with only right children
		bst3.insert(10);//root node
		bst3.insert(20);//10's right child
		bst3.insert(30);//20's right child
		bst3.insert(40);//30's right child
		bst3.insert(50);//40's right child
		bst3.insert(60);//50's right child
		bst3.insert(70);//60's right child
		
		if(bst3.size() != 7) {
			System.out.println("Test 1 - Actual Size: " + bst3.size());
			return false;
		}
		
		if(bst3.isEmpty()) {
			System.out.println("bst3 shouldn't be empty");
			return false;
	
		}
		
		if(!bst3.contains(10)) {
			System.out.println("bst3 should contain 10");
			return false;
		}
		
		if(!bst3.contains(20)) {
			System.out.println("bst3 should contain 20");
			return false;
		}
		
		if(!bst3.contains(30)) {
			System.out.println("bst3 should contain 30");
			return false;
		}
		
		if(!bst3.contains(40)) {
			System.out.println("bst3 should contain 40");
			return false;
		}
		
		if(!bst3.contains(50)) {
			System.out.println("bst3 should contain 50");
			return false;
		}
		
		if(!bst3.contains(60)) {
			System.out.println("bst3 should contain 60");
			return false;
		}
		
		if(!bst3.contains(70)) {
			System.out.println("bst3 should contain 70");
			return false;
		}
		
		bst3.clear();
		
		if(!bst3.isEmpty()) {
			System.out.println("bst3 has been cleared and should be empty");
			return false;
		}
		
		if(bst3.contains(10)) {
			System.out.println("bst3 has been cleared and should not contain 10");
			return false;
		}
		
		//bst with only left children
		bst3.insert(70);//root node
		bst3.insert(60);//70's left child
		bst3.insert(50);//60's left child
		bst3.insert(40);//50's left child
		bst3.insert(30);//40's left child
		bst3.insert(20);//30's left child
		bst3.insert(10);//20's left child
		
		if(bst3.size() != 7) {
			System.out.println("Test 2 - actual size: " + bst3.size());
			return false;
		}
		
		if(bst3.isEmpty()) {
			System.out.println("bst3 shouldn't be empty");
			return false;
		}
		
		if(!bst3.contains(70)) {
			System.out.println("bst3 should contain 70");
			return false;
		}
		
		if(!bst3.contains(60)) {
			System.out.println("bst3 should contain 60");
			return false;
		}
		
		if(!bst3.contains(50)) {
			System.out.println("bst3 should contain 50");
			return false;
		}
		
		if(!bst3.contains(40)) {
			System.out.println("bst3 should contain 40");
			return false;
		}
		
		if(!bst3.contains(30)) {
			System.out.println("bst3 should contain 30");
			return false;
		}
		
		if(!bst3.contains(20)) {
			System.out.println("bst3 should contain 20");
			return false;
		}
		
		if(!bst3.contains(10)) {
			System.out.println("bst3 should contain 10");
			return false;
		}
		
		return true;
	}
	
	/**
	 * calls test methods and prints results
	 * @param args - unused
	 */
	public static void main(String[] args) {
	
		BinarySearchTree<Integer> testTree1 = new BinarySearchTree<>();
		BinarySearchTree<String> testTree2 = new BinarySearchTree<>();
		BinarySearchTree<Integer> testTree3 =  new BinarySearchTree<>();
		
		System.out.println("Test 1 result: " + testTree1.test1());
		System.out.println("Test 2 result: " + testTree2.test2());
		System.out.println("Test 3 result: " + testTree3.test3());
	}
	
}
