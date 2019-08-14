package edition1;
/**
 * 
 * @author Wither
 * 
 *
 */
import java.text.Collator;
import java.util.Locale;
import java.util.Stack;

public class AvlTree {
	public Node root;
	public AvlTree() {
		root = null;
	}
	public Node getRoot() {
		return root;
	}
	public void makeEmpty() {
        root = null;
    }
    public boolean isEmpty() {
        return root == null;
    }
	public void insert(String s) {
		root = insert(s, root);
	}
	public static int compare(String o1, String o2) {
		return Collator.getInstance(Locale.CHINESE).compare(o1, o2); 
	}
	public void writeToFile(String filePath) {
		
	}
	private Node insert(String x, Node t) {
        if(t == null)
            return new Node(x, null, null);
        int compareResult = compare(x, t.data);
          
        if(compareResult < 0) {
            t.left = insert(x, t.left); 
            if(height(t.left) - height(t.right) == 2)
                if(compare(x, t.left.data) < 0)
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithLeftChild(t);
        }
        else if(compareResult > 0) {
            t.right = insert(x, t.right);
            if(height(t.right) - height(t.left) == 2)
                if(compare(x, t.right.data) > 0)
                    t = rotateWithRightChild(t);
                else
                    t = doubleWithRightChild(t);
        }
        else  
        	;
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }
	private int height(Node t) {
        return t == null ? -1 : t.height;
    }
	private Node rotateWithLeftChild(Node k2) {
        Node k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }
    private Node rotateWithRightChild(Node k1) {
        Node k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        return k2;
    }
    private Node doubleWithLeftChild(Node k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }
    private Node doubleWithRightChild(Node k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
    public void printTree(Node t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.data);
            printTree(t.right);
        }
    }
    public String travel(Node t) {
    	String res = "";
    	if(t == null) 
    		return res;
    	Stack<Node> st = new Stack<Node>();
    	while(!st.isEmpty() || t != null) {
    		while(t != null) {
    			st.push(t);
    			t = t.left;
    		}
    		t = st.pop();
    		System.out.println(t.data);
    		res += t.data + "\r\n";
    		t = t.right;
    	}
    	return res;
    }
}
