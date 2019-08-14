package edition1;

/**
 * @author Wither
 *
 *
 */

public class Node {
    String data;
	Node left;
	Node right;
	int height;
	
	public Node() { 
		this(null, null, null);
	}
	public Node(String s) {
		this.data = s;
		this.left = null;
		this.right = null;
		this.height = 0;
	}
	public Node(String s, Node l, Node r) {
		this.data = s;
		this.left = l;
		this.right = r;
		this.height = 0;
	}
	
}
