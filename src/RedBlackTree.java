import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * contains insert and ensureRedProperty methods for red black trees
 * + tests for correct position & color of inserted nodes
 */
public class RedBlackTree<T extends Comparable<T>> extends BSTRotation<T> {
	
	 /**
     * Checks if a new red node in the RedBlackTree causes a red property violation
     * by having a red parent. If this is not the case, the method terminates without
     * making any changes to the tree. If a red property violation is detected, then
     * the method repairs this violation and any additional red property violations
     * that are generated as a result of the applied repair operation.
     * @param newRedNode a newly inserted red node, or a node turned red by previous repair
     */
    protected void ensureRedProperty(RBTNode<T> newRedNode) {
    	
    	//make root black
    	if(((RBTNode<T>)(super.root)).isRed()) {
    		((RBTNode<T>)(super.root)).flipColor();
    	}
    	
    	//if the new node is the root, is null, or it's parent is already black, return
    	if(newRedNode == super.root || newRedNode == null || !newRedNode.getUp().isRed()) {
    		return;
    	}
    	
        RBTNode<T> parent = (RBTNode<T>) newRedNode.getUp(); //newRedNode's parent
        RBTNode<T> grandparent = (RBTNode<T>) parent.getUp();//parent's parent
        RBTNode<T> aunt = null;//aunt only defined if grandparent exists
        
        if (grandparent != null) {
        	aunt = (grandparent.getLeft() == parent) ? (RBTNode<T>) grandparent.getRight() : (RBTNode<T>) grandparent.getLeft();
        }
        
        if (grandparent != null) {
        	
        	//Cases for if the aunt node is black/doesn't exist
        	if(aunt == null || !aunt.isRed()) {
        		
        		//sub cases for if parent is grandparent's left child
        		if(parent == grandparent.getLeft()) {
        	
        			//Case 1: newRedNode is parent's left child - rotate & recolor parent and grandparent
        			if(newRedNode == parent.getLeft()) {
        			
        				super.rotate(parent, grandparent);//rotate
        				grandparent.flipColor();//recolor
        				parent.flipColor();	//recolor
        			}
        
        			//Case 2: newRedNode is parent's right child - rotate new node with parent and grandparent 
        			else if (newRedNode == parent.getRight()) {
        			
        				super.rotate(newRedNode, parent);//rotate new node to parent's spot
        				super.rotate(newRedNode, grandparent);//rotate new node to grandparent's spot
        				newRedNode.flipColor();//flip new node color to black
        				grandparent.flipColor();//flip gp color black
        			}
        		
        		}
        		//sub cases for if parent is grandparent's right child
        		if (parent == grandparent.getRight()) {
        		
        			//Case 3: newRedNode is parent's left child - rotate new node with parent and grandparent
        			if(newRedNode == parent.getLeft()) {
        				super.rotate(newRedNode, parent);
        				super.rotate(newRedNode, grandparent);
        				newRedNode.flipColor();
        				grandparent.flipColor();
        			
        			}
        		
        			//Case 4: newRedNode is parent's right child
        			if(newRedNode == parent.getRight()) {
        				super.rotate(parent, grandparent);
        				parent.flipColor();
        				grandparent.flipColor();
        			}
        		}
        	}
        	
        	else if (aunt.isRed()) {
        		
        		//recolor aunt node so it's black
        		aunt.flipColor();
        		
        		//recolor parent node so it's black
        		if(parent.isRed()) {
        			parent.flipColor();
        		}
        		
        		//recolor grandparent node so it's black
        		if(grandparent != root && !grandparent.isRed()) {
        			grandparent.flipColor();
        		}
        	}
        	
        	ensureRedProperty(grandparent); //recursive call on grandparent node
        }
        
    }
    
    /**
     * insert red nodes into red black tree using insert helper and calls
     * ensureRedProperty
     */
    @Override
    public void insert(T data) throws NullPointerException {
    	
        if (data == null) {
            throw new NullPointerException("Data cannot be null.");
        }
        
        RBTNode<T> newNode = new RBTNode<>(data); // creating new red node with data

        if (root == null) { // if BST is empty, make root equal to newNode
            root = newNode;
            
            if(((RBTNode<T>)(super.root)).isRed()) {
            	((RBTNode<T>)(super.root)).flipColor();
            }
            
            return;
        } 
        
        else {
            insertHelper(newNode, root); // insert newNode if root exists already
            
            if (((RBTNode<T>)super.root).isRed()){
            	
            	((RBTNode<T>)(super.root)).flipColor();// explicitly set root color to black
            }
            
            ensureRedProperty(newNode); // make sure red property is maintained for newNode
        }
        
       
    }
    
    /**
     * Tests that each node in test tree is in the correct position and is the correct color
     */
    @Test
    public void RBTTest1() {

    	RedBlackTree<String> tree = new RedBlackTree<>();
    	
    	//Q03 RBT from question 3
    	tree.insert("N");//root
    	
    	tree.insert("H");
    	tree.insert("S");
    	tree.insert("E");
    	tree.insert("K");
    	tree.insert("Q");
    	tree.insert("Y");
    	tree.insert("W");
    	tree.insert("Z");
    	
    	tree.insert("M");//node to cause rotation & color flips
    	
    	//check the structure and color of each node
    	assertEquals(tree.root.toLevelOrderString(), "[ N(b), H(r), S(r), E(b), K(b), Q(b), Y(b), M(r), W(r), Z(r) ]");
    	
    }
    
    /**
     * Manually checks that every node in test tree is in the correct position and has the correct color
     */
    @Test
    public void RBTTest2() {
    	
    	RedBlackTree<String> tree2 = new RedBlackTree<>();
    	
    	//Q03 RBT from question 4
    	tree2.insert("M");//initial root
    	tree2.insert("H");
    	tree2.insert("T");
    	tree2.insert("E");
    	tree2.insert("J");//new root after L insertion
    	tree2.insert("Q");
    	tree2.insert("V");
    	tree2.insert("I");
    	tree2.insert("K");
    	
    	tree2.insert("L");//node to cause rotations & color flips
    	
    	//Manually check each node is correct color
    	//J should be black
    	assertFalse(((RBTNode<String>) tree2.root).isRed());
    	//H should be red
    	assertFalse(!((RBTNode<String>) tree2.root).getLeft().isRed());
    	//M should be red
    	assertFalse(!((RBTNode<String>) tree2.root).getRight().isRed());
    	//E should be black
    	assertFalse(((RBTNode<String>) tree2.root).getLeft().getLeft().isRed());
    	//I should be black
    	assertFalse(((RBTNode<String>) tree2.root).getLeft().getRight().isRed());
    	//K should be black
    	assertFalse(((RBTNode<String>) tree2.root).getRight().getLeft().isRed());
    	//T should be black
    	assertFalse(((RBTNode<String>) tree2.root).getRight().getRight().isRed());
    	//L should be red
    	assertFalse(!((RBTNode<String>) tree2.root).getRight().getLeft().getRight().isRed());
    	//Q should be red
    	assertFalse(!((RBTNode<String>) tree2.root).getRight().getRight().getLeft().isRed());
    	//V should be red
    	assertFalse(!((RBTNode<String>) tree2.root).getRight().getRight().getRight().isRed());
    	
    	
    	//Manually check order of each node in tree
    	assertTrue(tree2.root.getData().equals("J"));
    	assertTrue(tree2.root.getLeft().getData().equals("H"));
    	assertTrue(tree2.root.getRight().getData().equals("M"));
    	assertTrue(tree2.root.getLeft().getLeft().getData().equals("E"));
    	assertTrue(tree2.root.getLeft().getRight().getData().equals("I"));
    	assertTrue(tree2.root.getRight().getLeft().getData().equals("K"));
    	assertTrue(tree2.root.getRight().getRight().getData().equals("T"));
    	assertTrue(tree2.root.getRight().getLeft().getRight().getData().equals("L"));
    	assertTrue(tree2.root.getRight().getRight().getLeft().getData().equals("Q"));
    	assertTrue(tree2.root.getRight().getRight().getRight().getData().equals("V"));
    	
    	//Check order & color using toLevelOrderString method
    	assertEquals(tree2.root.toLevelOrderString(), "[ J(b), H(r), M(r), E(b), I(b), K(b), T(b), L(r), Q(r), V(r) ]");
    }
    
    /**
     *Manually checks that every node in test tree is in the correct position and has the correct color
     */
    @Test
    public void RBTTest3() {
    	
    	RedBlackTree<String> tree3 = new RedBlackTree<>();
    	
    	//Q03 RBT from question 5
    	tree3.insert("O");//initial root
    	tree3.insert("G");//root after A insertion
    	tree3.insert("U");
    	tree3.insert("C");
    	tree3.insert("K");
    	tree3.insert("R");
    	tree3.insert("W");
    	tree3.insert("B");
    	tree3.insert("E");
    	
    	tree3.insert("A");//node to cause rotations & color flips
    	
    	//Manually check each node is correct color
    	//G should be black
    	assertFalse(((RBTNode<String>) tree3.root).isRed());
    	//C should be red
    	assertFalse(!((RBTNode<String>) tree3.root).getLeft().isRed());
    	//O should be red
    	assertFalse(!((RBTNode<String>) tree3.root).getRight().isRed());
    	//B should be black
    	assertFalse(((RBTNode<String>) tree3.root).getLeft().getLeft().isRed());
    	//E should be black
    	assertFalse(((RBTNode<String>) tree3.root).getLeft().getRight().isRed());
    	//A should be red
    	assertFalse(!((RBTNode<String>) tree3.root).getLeft().getLeft().getLeft().isRed());
    	//K should be black
    	assertFalse(((RBTNode<String>) tree3.root).getRight().getLeft().isRed());
    	//U should be black
    	assertFalse(((RBTNode<String>) tree3.root).getRight().getRight().isRed());
    	//R should be red
    	assertFalse(!((RBTNode<String>) tree3.root).getRight().getRight().getLeft().isRed());
    	//W should be red
    	assertFalse(!((RBTNode<String>) tree3.root).getRight().getRight().getRight().isRed());
    	
    	//Manually check order of each node in tree
    	assertTrue(tree3.root.getData().equals("G"));
    	assertTrue(tree3.root.getLeft().getData().equals("C"));
    	assertTrue(tree3.root.getRight().getData().equals("O"));
    	assertTrue(tree3.root.getLeft().getLeft().getData().equals("B"));
    	assertTrue(tree3.root.getLeft().getRight().getData().equals("E"));
    	assertTrue(tree3.root.getLeft().getLeft().getLeft().getData().equals("A"));
    	assertTrue(tree3.root.getRight().getLeft().getData().equals("K"));
    	assertTrue(tree3.root.getRight().getRight().getData().equals("U"));
    	assertTrue(tree3.root.getRight().getRight().getLeft().getData().equals("R"));
    	assertTrue(tree3.root.getRight().getRight().getRight().getData().equals("W"));
    	
    	//Check order & color using toLevelOrderString method
    	assertEquals(tree3.root.toLevelOrderString(), "[ G(b), C(r), O(r), B(b), E(b), K(b), U(b), A(r), R(r), W(r) ]");
    }
    
    
}