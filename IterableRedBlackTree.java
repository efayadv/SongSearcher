import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;
//import org.junit.jupiter.api.Test;

//import BinarySearchTree.Node;

//import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IterableRedBlackTree<T extends Comparable<T>>
    extends RedBlackTree<T> implements IterableSortedCollection<T> {

	private Comparable<T> startPoint = new Comparable<T>() {
        @Override
        public int compareTo(T o) {
            return -1; // Always return -1 to ensure this is smaller than any value
        }
    };
    
    public void setIterationStartPoint(Comparable<T> startPoint) {
    	if (startPoint != null) {
            this.startPoint = startPoint;
        } else {
            // If null is passed, revert to the default start point
            this.startPoint = new Comparable<T>() {
                @Override
                public int compareTo(T o) {
                    return -1; // Always return -1 to ensure this is smaller than any value
                }
            };
        }
    }

    public Iterator<T> iterator() { 
    	
    	return new RBTIterator<>(root, startPoint); 
    	}
    
    
    @Override
    protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
    	if(newNode == null) throw new NullPointerException("new node cannot be null");

        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                /*
                if (compare == 0) {
                    return false;     
                } 
                */
                if (compare <= 0) {
                    // insert in left subtree
                    if (current.down[0] == null) {
                        // empty space to insert into
                        current.down[0] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[0];
                    }
                } else {
                    // insert in right subtree
                    if (current.down[1] == null) {
                        // empty space to insert into
                        current.down[1] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[1]; 
                    }
                }
            }
        }
    }

    private static class RBTIterator<R> implements Iterator<R> {
    	private Comparable<R> startPoint; // is this right?
    	 private Stack<Node<R>> stack;

	public RBTIterator(Node<R> root, Comparable<R> startPoint) {
		this.startPoint = startPoint;
		stack = new Stack<>();
		buildStackHelper(root);
	}

	private void buildStackHelper(Node<R> node) {
		if (node == null) {
			return;
		}
		int comparison = startPoint.compareTo(node.data);
		
		if (comparison > 0) {
			// Recursive case 1: If the node's data is smaller than the start point,
	        // continue to the right subtree.
	        buildStackHelper(node.down[1]);
		} else {
			 // Recursive case 2: If the node's data is greater than or equal to the start point,
	        // push the node onto the stack and continue to the left subtree.
	        stack.push(node);
	        buildStackHelper(node.down[0]);
		}
		
	}

	public boolean hasNext() { 
		return !stack.isEmpty(); 
		}

	public R next() { 
		
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		// Pop the top node from the stack.
	    Node<R> nextNode = stack.pop();
	    // Visit the node (return its data).
	    R data = nextNode.data;
	    // Build the stack for the next node by calling buildStackHelper on the node's left child.
	    buildStackHelper(nextNode.down[1]);
	    
		return data; 
		}
		
    }
    
    
    
/*    
    
    @Test
    public void testIterationOverIntegers() {
        IterableRedBlackTree<Integer> tree = new IterableRedBlackTree<>();
        // Add some integers to the tree
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);

        Iterator<Integer> iterator = tree.iterator();
        //Assertions.assertTrue(iterator.hasNext(), "Iterator should have elements");

        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        // Expected result in ascending order
        Assertions.assertEquals(Arrays.asList(3, 5, 7), result);
    }
    
    @Test
    public void testIterationOverStrings() {
        IterableRedBlackTree<String> tree = new IterableRedBlackTree<>();
        // Add some strings to the tree
        tree.insert("apple");
        tree.insert("banana");
        tree.insert("cherry");

        Iterator<String> iterator = tree.iterator();
        //Assertions.assertTrue(iterator.hasNext(), "Iterator should have elements");

        List<String> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        // Expected result in lexicographic order
        Assertions.assertEquals(Arrays.asList("apple", "banana", "cherry"), result);
    }
    
    @Test
    public void testIterationWithStartingPoint() {
        IterableRedBlackTree<Integer> tree = new IterableRedBlackTree<>();
        // Add some integers to the tree
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(4);

        // Set the iteration start point to 4
        tree.setIterationStartPoint(4);
        
        Iterator<Integer> iterator = tree.iterator();
        
       // Assertions.assertTrue(iterator.hasNext(), "Iterator should have elements");

        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        // Expected result starting from 4 in ascending order
        Assertions.assertEquals(Arrays.asList(4, 5, 7), result);
    }
*/  
  
}
