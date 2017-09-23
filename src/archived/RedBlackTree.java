package archived;

import java.util.Iterator;

public class RedBlackTree<K, V> /*implements RedBlackI<K, V>*/ {

	Node <K,V> root;
	public int size;
	class Node<K,V>{
		K key;
		V value;
		Node<K,V> left, right, parent;
		boolean isLeftChild, black;
		public Node(K Key,V Value){
			this.key=Key;
			this.value=Value;
			left=right=parent=null;
			black=false;
			isLeftChild=false;
		}
	}
	/***
	 * Calls recursive add method that will sort trees based on keys.
	 * @param  key, value
	 */
	public void add(K Key, V Value) {
		Node <K,V> node = new Node<K,V>(Key,Value);
		if (root==null){
			root=node;
			root.black=true;
			size++;
			return;
		}
		add(root,node);
		size++;
		return;
	}
	/**
	 * Recursive add method that takes new node and sorts it starting from the key. 
	 * If they key of new node is greater then the node being tested, they new node moves to 
	 * the tested key's right child. When it becomes a leaf the node stops. 
	 * @param Parent
	 * @param node
	 */
	private void add(Node<K, V> Parent, Node<K, V> node) {
		if(((Comparable<K>)node.key).compareTo(Parent.key)>0){
			if(Parent.right==null){
				Parent.right=node;
				node.parent=Parent;
				node.isLeftChild=false;
				checkColor(node);
				return;
			}
			add(Parent.right,node);
			return;
		}
		if(((Comparable<K>)node.key).compareTo(Parent.key)<0){
			if(Parent.left==null){
				Parent.left=node;
				node.parent=Parent;
				node.isLeftChild=true;
				checkColor(node);
				return;
			}
		}
		add(Parent.left,node);
		return;
	}
	/**
	 * Check color iterates through the list from the new node up. if it finds 
	 * two red nodes in a row, correct tree is called to determine how to fix tree.
	 * @param node
	 */
	public void checkColor(Node<K,V> node){
		if(node==root)
			return;
		if(!node.black && !node.parent.black){
			correctTree(node);
		}
		checkColor(node.parent);
	}

	/**
	 * correctTree checks the problem nodes' aunt. if the aunt is red, a color flip is done, 
	 * if the aunt is black a rotation is done. This method color flips manually but calls 
	 * a recursive rotate method in the case of a rotation.
	 * @param node
	 */
	public void correctTree(Node<K,V> node) {
		if (node.parent.isLeftChild) {
			if (node.parent.parent.right == null || node.parent.parent.right.black) {
				rotate(node);
			} else {
				if (node.parent.parent.right != null)
					node.parent.parent.right.black = true;
				if(node.parent.parent!=root)
					node.parent.parent.black = false;
				node.parent.black =  true;		
			}
		} else {
			if(node.parent.parent==null)
				return;
			if (node.parent.parent.left == null || node.parent.parent.left.black) {
				rotate(node);
			} else {
				if (node.parent.parent.left != null)
					node.parent.parent.left.black = true;
				if(node.parent.parent!=root)
					node.parent.parent.black = false;
				node.parent.black =  true;		
			}
		}
	}
	/**
	 * This method is called by correct tree if rotation is needed. This method determines 
	 * which rotation is needed. depending on where the problematic node is relative to its 
	 * grandparent node, a different rotation is needed. 
	 * @param node
	 */
	public void rotate(Node<K,V> node) {
		if (node.isLeftChild) {
			if (node.parent.isLeftChild) {
				rightRotate(node.parent.parent);
				node.black = false;
				node.parent.black = true;
				if (node.parent.right != null)
					node.parent.right.black = false;
			} else {
				rightLeftRotate(node.parent.parent);
				node.black = true;
				node.right.black = false;
				node.left.black = false;
			}
		} else {
			if (node.parent.isLeftChild) {
				leftRightRotate(node.parent.parent);
				node.black = true;
				node.right.black = false;
				node.left.black = false;
			} else {
				leftRotate(node.parent.parent);
				node.black = false;
				node.parent.black = true;
				if (node.parent.left != null)
					node.parent.left.black = false;
			}
		}
	}

	/**
	 * rightRotate is called when problem node is the right child's right child
	 * relative to the grandparent node. checks position of problem node and rotates.
	 * @param node
	 */
	public void rightRotate(Node<K,V> node) {
		Node<K,V> temp = node.left;
		node.left = temp.right;
		if (node.left != null) {
			node.left.parent = node;
			node.left.isLeftChild = true;
		}
		if (node.parent == null) {
			temp.black=true;
			temp.parent = null;
			root = temp;
		} else {
			temp.parent = node.parent;
			if (node.isLeftChild) {
				temp.isLeftChild = true;
				temp.parent.left = temp;
			} else {
				temp.isLeftChild = false;
				temp.parent.right = temp;
			}
		}

		temp.right = node;
		node.isLeftChild = false;
		node.parent = temp;
	}

	/**
	 * leftRotate is called when problem node is the left child's left child
	 * relative to the grandparent node. checks position of problem node and rotates.
	 * @param node
	 */
	public void leftRotate(Node<K,V>node){
		Node<K,V> tmp = node.right;
		node.right=tmp.left;
		if(node.right!=null){
			node.right.parent=node;
			node.right.isLeftChild=false;
		}
		if(node.parent==null){
			tmp.black=true;
			tmp.parent=null;
			root=tmp;
		}
		else{
			tmp.parent=node.parent;
			if(!node.isLeftChild){
				tmp.isLeftChild=false; 
				tmp.parent.right=tmp;
			}
			else{
				tmp.isLeftChild=true;
				tmp.parent.left=tmp;
			}
		}	
		tmp.left=node;
		node.isLeftChild=true;
		node.parent=tmp;
	}

	/**
	 * method is called if a double rotation is needed. In the case of a problem node being a 
	 * right child's left child relative to the grandparent node. 
	 * @param node
	 */
	public void rightLeftRotate(Node<K,V>node){
		rightRotate(node.right);
		leftRotate(node);
	}
	/**
	 * method is called if a double rotation is needed. In the case of a problem node being a 
	 * left child's right child relative to the grandparent node. 
	 * @param node
	 */
	public void leftRightRotate(Node<K,V>node){
		leftRotate(node.left);
		rightRotate(node);
	}

	public boolean contains(K key) {	
		return RecContains(key,root);
	}

	/**
	 * recursive contains method that will find key efficiently because of the 
	 * nature of the sorted RB tree. 
	 * @param key
	 * @param node
	 * @return true if found, false if not found.
	 */
	public boolean RecContains(K key, Node<K, V> node) {
		if(node==null)
			return false;
		if(((Comparable<K>)key).compareTo(node.key) == 0)
			return true;
		else if(((Comparable<K>)key).compareTo(node.key) > 0)
			return RecContains(key,node.right);
		else
			return RecContains(key,node.left);
	}


	public V getValue(K key) {
		return find(key,root);
	}
	/**
	 * recursive getValue method that will find key efficiently because of the 
	 * nature of the sorted RB tree. 
	 * @param key
	 * @param node
	 * @return value of the found node.
	 */
	private V find(K key, Node<K, V> node) {
		if(node==null)
			return null;
		if(((Comparable<K>)key).compareTo(node.key) < 0)
			return find(key,node.left);
		else if(((Comparable<K>)key).compareTo(node.key)>0)
			return find(key,node.right);
		else
			return(V) node.value;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		if(root==null)
			return true;
		return false;
	}

	public int height() {
		if(root==null)
			return 0;
		return height(root)-1;
	}
	/**
	 * recursive height method that determines the height of RB tree
	 * @param node
	 * @return height of the tree, whichever side is higher
	 */
	public int height(Node<K, V> node) {
		if (node == null)
			return 0;
		int leftHeight = height (node.left) + 1;
		int rightHeight = height (node.right) + 1;
		if(leftHeight>rightHeight)
			return leftHeight;
		return rightHeight;
	}


	public Iterator<K> iterator() {
		return new iteratorHelper<K>();
	}

	/**
	 *Iterator traverses through RB tree using recursive method.
	 *This iterator will use InOrder traversal 
	 * @param <K>
	 */
	class iteratorHelper<K> implements Iterator<K> {
		K[] keys;
		int currentposition;
		public iteratorHelper() {
			keys = (K[]) new Object[size];
			currentposition = 0;
			addToIterator((Node<K, V>) root);

		}
		public void addToIterator(Node<K,V> node){
			if(node==null)
				return;
			addToIterator(node.left);
			keys[currentposition++]=node.key;
			addToIterator(node.right);

		}	
		public boolean hasNext() {
			return currentposition < keys.length;
		}
		public K next() {
			if (!hasNext())
				return null;
			return keys[currentposition++];
		}
		public void remove() {
			throw new UnsupportedOperationException("Cannot remove from the hash while iterating");
		}
	}//End KeyIteratorHelper	

	public void print() {
		printingInOrder(root,0);
	}	

	/**
	 * recursive print method that uses same logic as iterator. I use the int X
	 * to know what level each node is at when printing. The print 
	 * @param node
	 * @param X
	 */
	public void printingInOrder(Node<K,V> node, int X){
		if(node==null)
			return;
		printingInOrder(node.left,X+1);
		for (int i = 0;i<X;i++){
			System.out.print(".");
		}
		System.out.print(node.key+" : ");
		if(node.black)
			System.out.print("Black");
		else
			System.out.print("Red");
		if(node==root)
			System.out.print("(Root)");
		System.out.println();
		printingInOrder(node.right,X+1);
		return;
	}

}
