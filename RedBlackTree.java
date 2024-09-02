// -== CS400 Spring 2024 File Header Information ==-
// Name: Emilio Fayad
// Email: <fayadvillarr@wisc.edu email address>
// Lecturer: Florian Heimerl
// Notes to Grader: N/A
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {
	
	protected static class RBTNode<T> extends Node<T> {
    		public boolean isBlack = false;
    		public RBTNode(T data) { super(data); }
    		public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
    		public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
    		public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
	}	
	
	//RBTNode<T> root = (RBTNode<T>)super.root;

	/**
     * this method enforces Red Black Tree Properties to the node that has been added.
     * This method recursively enforce the proprties if needed.
     *
     * @param newNode new node that is added to the tree
     */
    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newNode) {
        //check if root node is the new inserted node and change to black if true
        if (newNode == root) {
            newNode.isBlack = true;
            return;
        } else if (newNode.getUp().isBlack) { //check if parent's color is red, if not return(no need to change)
            return;
        }

        RBTNode<T> gParent = newNode.getUp().getUp();
        RBTNode<T> parent = newNode.getUp();
        RBTNode<T> aunt;
        if (parent.isRightChild()) {
            aunt = gParent.getDownLeft();
        } else {
            aunt = gParent.getDownRight();
        }

        //aunt is black. enforce red black property
        if (aunt == null || aunt.isBlack) {
            //if newNode and its parents both right child or left, rotate once, otherwise twice.
            //then swap color accordingly.
            if (newNode.isRightChild() == parent.isRightChild()) {
                rotate(parent, parent.getUp());
                gParent.isBlack = false;
                newNode.isBlack = false;
                parent.isBlack = true;
            } else {
                rotate(newNode, newNode.getUp());
                rotate(newNode, newNode.getUp());
                newNode.getDownLeft().isBlack = false;
                newNode.getDownRight().isBlack = false;
                newNode.isBlack = true;
            }

        } else { //aunt is red
            //set both aunt and parent to black
            aunt.isBlack = true;
            parent.isBlack = true;

            //if grandparent is root, paint it black and return
            if (gParent == root) {
                gParent.isBlack = true;
                return;
            } else { //enforce properties recursively.
                gParent.isBlack = false;
                enforceRBTreePropertiesAfterInsert(gParent);
            }
        }


    }

    /**
     *	this function inserts new node to the Red Black Tree
     *
     *	@param data data of the node to be inserted to the tree
     *	@return true if inserted correctly, false otherwise.
     * */
    public boolean insert(T data) {
        RBTNode<T> node = new RBTNode<>(data);
        boolean result = insertHelper(node);
        enforceRBTreePropertiesAfterInsert(node);
        return result;
    }
	

	/*
	Created a RBT 7 elements to check if the root is properly distinguished and if the level 
	order traversal of the tree is correct.
	
	*/
	


	/*
	@Test
	public void case1(){
		RedBlackTree<Integer> case1 = new RedBlackTree<Integer>();
		case1.insert(11);
		case1.insert(6);
		case1.insert(3);
		case1.insert(9);
		case1.insert(7);
		case1.insert(10);
		case1.insert(13);

		String expected = "[ 6, 3, 9, 7, 11, 10, 13 ]";
		String actual = case1.toLevelOrderString();
		
		Assertions.assertEquals(expected, actual);	
	}

	/*
	Testing the insertion of three elements in and check if the left son of the root is black,
	if its NOT black, the test passes.
	*/

	/*
	@Test
    public void case2(){
		RedBlackTree<Integer> case2 = new RedBlackTree<Integer>();
        case2.insert(10);
        case2.insert(5);
        case2.insert(15);
                
        RBTNode<Integer> ten = (RedBlackTree.RBTNode<Integer>) case2.root;
        RBTNode<Integer> five = (RedBlackTree.RBTNode<Integer>) ten.down[0];
        
		
		Assertions.assertFalse(five.isBlack);


        }
	/*
	testing the insertion of a single node and check if the node (root) is black
	*/

	/*
	@Test
    public void case3(){
        RedBlackTree<Integer> case3 = new RedBlackTree<Integer>();
        case3.insert(4);
		
		RBTNode<Integer> four = (RedBlackTree.RBTNode<Integer>) case3.root;               	
		Assertions.assertTrue(four.isBlack);

        }
	*/


	/*
	@Test
	public void case4(){
		RedBlackTree<String> case4 = new RedBlackTree<String>();
		case4.insert("a");
		case4.insert("b");
		case4.insert("c");
		
		String expected = "[ b, a, c ]";
		String actual = case4.toLevelOrderString();
		//RBTNode<Integer> six = case4.root;
		
		//Assertions.assertFalse(six == null);		
		Assertions.assertEquals(expected, actual);	
	}

	*/		

}


