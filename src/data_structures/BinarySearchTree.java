package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;


public class BinarySearchTree<K extends Comparable<K>,V > implements DictionaryADT<K, V> {

	private int currentSize;
	private int modCounter;
	private DictNode<K,V> root;
	private K foundKey;
	private V foundValue;

	public BinarySearchTree(){
		currentSize = 0;
		modCounter = 0;
		root = null;
	}

	public boolean contains(K key) {
		foundKey = null;
		containsKey(root, key);
		return foundKey != null;
	}

	private void containsKey(DictNode<K,V> node, K key){
		if (node == null) {return;}
		containsKey(node.left, key);
		if (key.compareTo(node.key) == 0) foundKey = node.key;
		containsKey(node.right, key);	
	}

	public boolean add(K key, V value) {
		if (root == null) {
			currentSize++; modCounter++;
			root = new DictNode(key, value);
			return true;
		} else {
			/*if (insert(key, value, root, null, false)){
				currentSize++; modCounter++;
				return true;
			} else {
				System.out.println("DUPLICATE");
				return false;
			}*/
			insert(key, value, root, null, false);
			currentSize++; modCounter++;
			return true;
		}
	}

	private void insert(K key, V value, DictNode<K, V> node, DictNode<K, V>  parent, boolean wasLeft) {
		if (node == null){
			if (wasLeft){
				parent.left = new DictNode(key, value);
			} else {
				parent.right = new DictNode(key, value);
			}
			//return true;
		} else if (key.compareTo(node.key) == 0){
			//return false; //duplicate
		} else if (key.compareTo(node.key) < 0){
			//return 
			insert(key, value, node.left, node, true);
		} else {
			//return 
			insert(key, value, node.right, node, false);
		}	
	}

	public boolean delete(K key) {
		if (isEmpty()) return false;
		if (!delete(key, root, null, false)) return false;
		currentSize--; modCounter++;
		return true;
	}

	private boolean delete(K key, DictNode<K, V> node, DictNode<K, V>  parent, boolean wasLeft) { //ROOT?
		if (node == null) return false;

		if (key.compareTo(node.key) < 0) {
			return delete(key, node.left, node, true);

		} else if (key.compareTo(node.key) > 0){
			return delete(key, node.right, node, false);

		} else { 											//found node to delete
			if (node.left == null && node.right == null){   //0 child
				if (wasLeft){
					parent.left = null;
				} else {
					parent.right = null;
				}				
			} else if (node.left == null){					//1 child
				if (root == node){
					root = node.right;
				} else {
					if (wasLeft){
						parent.left = node.right;
					} else {
						parent.right = node.right;
					}
				}
			} else if (node.right == null){					//1 child
				if (root == node){
					root = node.left;
				} else {
					if (wasLeft){
						parent.left = node.left;
					} else {
						parent.right = node.left;
					}
				}
			} else {										//2 child
				DictNode<K,V> replacement = findReplacement(node.right);
				delete(replacement.key);
				currentSize++; //your not deleting the replacement key, only shifting it
				node.key = replacement.key;
				node.value = replacement.value;
				replacement = null;
			}
			return true;
		}		
	}

	private DictNode<K,V> findReplacement(DictNode<K,V> node){
		DictNode<K,V> nextMax;
		if (node.left != null){
			nextMax = findReplacement(node.left);
		} else {
			nextMax = node;
		}
		return nextMax;
	}

	public V getValue(K key) {
		foundValue = null;
		if (!isEmpty()){
			if (root.key.compareTo(key) != 0){
				findValue(root, key);
			} else {
				return root.value;
			}
		}
		return foundValue;
	}

	private void findValue(DictNode<K, V> node, K key) {
		if (node.key.compareTo(key) > 0 && node.left != null){
			findValue(node.left, key);
		} else if (node.key.compareTo(key) < 0 && node.right != null){
			findValue(node.right, key);
		} else if (node.key.compareTo(key) == 0){
			foundValue = node.value;
		} else {
			return; // no value found
		}
	}

	public K getKey(V value) {
		foundKey = null;
		findKey(root, value);
		return foundKey;
	}

	private void findKey(DictNode<K,V> node, V value){
		if (node == null) {return;}
		if (foundKey != null){return;}
		findKey(node.left, value);
		if (((Comparable)value).compareTo(node.value) == 0) foundKey = node.key;
		if (foundKey != null){return;}
		findKey(node.right, value);	
		if (foundKey != null){return;}
	}

	public int size() {
		return currentSize;
	}

	public boolean isFull() {
		return false;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public void clear() {
		root = null;
		currentSize = 0;
	}

	public Iterator keys() {
		return new KeyIteratorHelper();
	}

	public Iterator values() {
		return new ValueIteratorHelper();
	}

	abstract class IteratorHelper<E> implements Iterator<E>{
		protected DictNode<K,V>[] nodes;
		protected int idx;
		protected long modCheck;
		private int j = 0;

		public IteratorHelper(){
			nodes = new DictNode[currentSize];
			idx = 0;
			modCheck = modCounter;
			visit(root);
		}	

		private void visit(DictNode<K,V> node){	
			if (node == null) return;
			visit(node.left);
			nodes[j++] = node;
			visit(node.right);
		}

		public boolean hasNext(){
			if (modCheck != modCounter)
				throw new ConcurrentModificationException();
			return idx < currentSize;
		}

		public abstract E next();

		public void remove(){
			throw new UnsupportedOperationException();
		}
	} //end class

	class KeyIteratorHelper<K> extends IteratorHelper<K>{
		public KeyIteratorHelper(){
			super();
		}
		public K next() {
			return (K) nodes[idx++].key;
		}	
	}

	class ValueIteratorHelper<K> extends IteratorHelper<K>{
		public ValueIteratorHelper(){
			super();
		}
		public K next() {
			return (K) nodes[idx++].value;
		}	
	}

	class DictNode<K, V> implements Comparable<DictNode<K, V>>{
		K key;
		V value;
		DictNode left;
		DictNode right;

		public DictNode(K k, V v){
			key = k;
			value = v;
		}

		public int compareTo(DictNode<K, V> o) {
			return ((Comparable<K>)key).compareTo((K)o.key);
		}
	}
}
