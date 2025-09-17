
/**
 * contains a rotate method for binary search tree and tests methods
 * @param <T> generic type
 */
public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {
	
	/**
	 * Constructor that calls the superclass constructor
	 */
	public BSTRotation() {
		super();
	}
	
	  /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right{
     * 
     * child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this
     * method will either throw a NullPointerException: when either reference is
     * null, or otherwise will throw an IllegalArgumentException.
     *
     * @param child is the node being rotated from child to parent position 
     * @param parent is the node being rotated from parent to child position
     * @throws NullPointerException when either passed argument is null
     * @throws IllegalArgumentException when the provided child and parent
     *     nodes are not initially (pre-rotation) related that way
     */
    protected void rotate(BSTNode<T> child, BSTNode<T> parent)
        throws NullPointerException, IllegalArgumentException {
        
    	if (child == null || parent == null) {
    		throw new NullPointerException("Parent nodes and child nodes cannot be null");
    	}
    	
    	//right rotation (parent and left child)
    	if (parent.getLeft() == child) {
    		
    		parent.setLeft(child.getRight());//parent's left child becomes child's right child
    		
    		if (child.getRight() != null) {//update right child's parent reference
    			child.getRight().setUp(parent);
    		}
    		

    		child.setRight(parent);//child becomes parent of og parent
    		child.setUp(parent.getUp());//update child's parent reference to og parent's parent
    		parent.setUp(child);//update og parent's parent reference to child
    		
    		
    		
    		//update grandparent's child reference to point to the new child
    		 if (child.getUp() != null) {
    	            BSTNode<T> grandparent = child.getUp();
    	            if (grandparent.getLeft() == parent) {
    	                grandparent.setLeft(child);//update grandparent's left child reference
    	            }
    	            else if (grandparent.getRight() == parent) {
    	                grandparent.setRight(child);// update grandparent's right child reference
    	            }
    	    } 
    		else if (root == parent) {
    				root = child;//if child now has no parent reference, set it equal to the root
    		}
    	}
    	
    	//left rotation (parent and right child)
    	else if (parent.getRight() == child) {
    		
    		parent.setRight(child.getLeft());
    		
    		if(child.getLeft() != null) {
    			child.getLeft().setUp(parent);//update left child's parent reference
    		}
    		
    		child.setLeft(parent);//child becomes parent of og parent
    		child.setUp(parent.getUp()); //update child's parent reference to og parent's parent
    		parent.setUp(child);//update og parent's parent reference to child
    		
    		//update grandparent's child reference to point to the new child
            if (child.getUp() != null) {
                BSTNode<T> grandparent = child.getUp();
                if (grandparent.getLeft() == parent) {
                    grandparent.setLeft(child);//update grandparent's left child reference
                }
                else if (grandparent.getRight() == parent) {
                    grandparent.setRight(child);//update grandparent's right child reference
                }
            } 
    		else if (root == parent) {
    			root = child;//if child has no parent reference, set it equal to the root
    		}
    	}
    	else {
    		throw new IllegalArgumentException("Child must be direct child of parent node");
    	}
    }
    
    /**
     * tests rotate method right rotations
     * @return true if it pasts the tests false otherwise
     */
    public static boolean test1() {
    	
    	boolean result1 = false;
        boolean result2 = false;
    	
        //right root rotation with 3 nodes, 1 leaf node
        BSTRotation<Integer> tree1 = new BSTRotation<>();
        
        tree1.insert(30);//root
        tree1.insert(20);//root's left child
        tree1.insert(10);//20's left child

        BSTNode<Integer> root = tree1.root;//30
        BSTNode<Integer> child = root.getLeft();//20

        tree1.rotate(child, root);  //perform right rotation between 20 and 30

        //child (20) should be root, and og root (30) should be right child
        if (tree1.root == child && child.getRight() == root && root.getLeft() == null) {
             result1 = true;
        }
        
        //complex right rotation with 4 nodes, 2 leaf nodes
        BSTRotation<Integer> tree2 = new BSTRotation<>();
        
        tree2.insert(30);//root
        tree2.insert(20);//root's left child
        tree2.insert(25);//20's right child
        tree2.insert(10);//20's left child
        
        BSTNode<Integer> root2 = tree2.root;//30
        BSTNode<Integer> child2 = root2.getLeft();//20
        BSTNode<Integer> childL = child2.getLeft();//10
        BSTNode<Integer> childR = child2.getRight();//25
        
        tree2.rotate(childL, child2);//perform right rotation between 10 and 20
        
        //10 should become left child of 30, and 20 its right child
        if (tree2.root == root2 && root2.getLeft() == childL && childL.getRight() == child2
        		&& child2.getRight() == childR) {
            result2 = true;
        }

        return (result1 && result2);
    }
    
    /**
     * tests rotate method with right rotations
     * @return true if tests pass, false otherwise
     */
    public static boolean test2() {
    	
    	boolean result1 = false;
    	boolean result2 = false;
    	
    	//left root rotation with 3 nodes, 1 leaf node
        BSTRotation<Integer> tree = new BSTRotation<>();
        tree.insert(10);//root
        tree.insert(20);//root's right child
        tree.insert(30);//20's right child

        BSTNode<Integer> root = tree.root;//10
        BSTNode<Integer> child = root.getRight();//20

        tree.rotate(child, root);//perform left rotation on root

        //child (20) should be root, and original root (10) should be left child
        if (tree.root == child && child.getLeft() == root && root.getRight() == null) {
            result1 = true;
        }
        
        //complex left rotation with 6 nodes, 3 leaf nodes
        BSTRotation<Integer> tree2 = new BSTRotation<>();
        
        tree2.insert(5);//root
        tree2.insert(7);//root's right child
        tree2.insert(3);//root's left child
        tree2.insert(8);//7's right child
        tree2.insert(4);//3's right child
        tree2.insert(6);//7's left child
        
        
        BSTNode<Integer> root2 = tree2.root;//5
        BSTNode<Integer> parent = root2.getRight();//7
        BSTNode<Integer> child2 = parent.getRight();//8
        BSTNode<Integer> child6 = parent.getLeft();//6
        
        tree2.rotate(child2, parent);//perform left rotation
        
        //root2 should remain the same, root's right child should now be 8,
        //and root's right child's left child should be 7
        if(tree2.root == root2 && root2.getRight() == child2 && child2.getLeft() == parent 
        && parent.getLeft() == child6) {
        	result2 = true;
        }
        
        return result1 && result2;
    }
    
    /**
     * tests right rotation on a tree with 3 leaf nodes
     * @return true if tests past, false otherwise
     */
    public static boolean test3() {
    	
    	boolean result1 = false;
    	boolean result2 = false;
    	
    	//rotation with 5 nodes, 2 leaf nodes
        BSTRotation<Integer> tree = new BSTRotation<>();
        tree.insert(40);//root
        tree.insert(30);//left child of root
        tree.insert(50);//right child of root
        tree.insert(20);//left child of 30
        tree.insert(35);//right child of 30

        BSTNode<Integer> ogRoot = tree.root;//40
        BSTNode<Integer> ogChild = ogRoot.getLeft();//30
        BSTNode<Integer> childR = ogChild.getRight();//35
        BSTNode<Integer> rootR = ogRoot.getRight();//50
        BSTNode<Integer> childL = ogChild.getLeft();//20

        tree.rotate(ogChild, ogRoot); //perform right rotation

        //child (30) should be root, root (40) should be right child, and 35 should stay under 30
        if (tree.root == ogChild && ogChild.getRight() == ogRoot && ogRoot.getLeft() == childR && ogRoot.getRight() == rootR
        		&& ogChild.getLeft() == childL ) {
            result1 = true;
        }
        
        //rotation with only 2 nodes, 1 leaf node
        BSTRotation<Integer> tree2 = new BSTRotation<>();
        
        tree2.insert(10);//root
        tree2.insert(20);//10's right child
        
        BSTNode<Integer> root2 = tree2.root;
        BSTNode<Integer> child2 = root2.getRight();
        
        tree2.rotate(child2, root2);//perform left rotation
        
        if (tree2.root == child2 && child2.getLeft() == root2 && child2.getRight() == null && root2.getLeft() == null
        		 && root2.getRight() == null) {
        	result2 = true;
        }
        return result1 && result2;
    }
    
    /**
     * main method to call tests methods
     * @param args - unused
     */
    public static void main(String[] args) {
        BSTRotation<Integer> testTree = new BSTRotation<>();
        
        System.out.println("Test 1: " + testTree.test1());//true if right rotation is correct
        System.out.println("Test 2: " + testTree.test2());//true if left rotation on root is correct
        System.out.println("Test 3: " + testTree.test3());//true if right rotation with shared children is correct
    }
}
