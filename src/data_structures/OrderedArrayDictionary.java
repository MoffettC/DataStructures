package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class OrderedArrayDictionary<K extends Comparable<K>,V> implements DictionaryADT<K, V> {

	private int currentSize;
	private int maxSize;
	private int modCounter;
	private DictNode<K, V>[] list;	

	public OrderedArrayDictionary(int dictSize){
		currentSize = 0;
		modCounter = 0;
		maxSize = dictSize;
		list = new DictNode[maxSize];
	}

	public boolean contains(K key) {
		if (isEmpty()){ return false; }
		int where = searchForElement(key, 0, currentSize-1);
		if (where == -1) {//doesnt exist
			return false;
		} else {
			return true;
		}	
	}

	public boolean add(K key, V value) {
		if (isFull() || contains(key)) {return false;}
		
		DictNode obj = new DictNode(key, value);
		int where = findInsertionPoint(key, 0, currentSize-1);
		for (int i = currentSize - 1; i >= where; i--){
			list[i+1] = list[i];
		}
		list[where] = (DictNode) obj;
		currentSize++;
		modCounter++;
		return true;
	}

	public boolean delete(K key) {
		if(isEmpty()){return false;}
		
		int where = searchForElement(key, 0, currentSize-1);
		if (where == -1) {return false;} //doesnt exist
		for (int i = where; i < currentSize-2; i++){ //shift down
			list[i] = list[i+1];
		}
		modCounter++;
		currentSize--;
		return true; 
	}

	public V getValue(K key) {
		int where = searchForElement(key, 0, currentSize-1);
		if (where == -1) {return null;} //doesnt exist
		return list[where].value;
	}

	public K getKey(V value) {
		for (int i = 0; i < currentSize; i++){
			if (list[i] != null && ((Comparable) list[i].value).compareTo(value) == 0){
				return (K) list[i].key;
			}
		}
		return null;
	}

	public int size() {
		return currentSize;
	}

	public boolean isFull() {
		return currentSize == maxSize;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public void clear() {
		for (int i = 0; i <currentSize; i++){
			list = null;
		}
		currentSize = 0;
		modCounter = 0;
	}

	private int findInsertionPoint(K object, int lo, int hi){
		if(hi < lo){
			return lo;
		}
		int mid = (lo+hi) >> 1;
		if (object.compareTo(list[mid].key) <= 0){
			return findInsertionPoint(object, lo, mid-1); //go left
		}
		return findInsertionPoint(object, mid+1, hi); //go right
	}

	private int searchForElement(K object, int lo, int hi){
		if(hi < lo){
			return -1; //not found
		}

		int mid = (lo+hi) >> 1; //bit shift, divide by 2

		if (object.compareTo(list[mid].key) == 0){
			return mid; 
		} else if (object.compareTo(list[mid].key) < 0){
			return searchForElement(object, lo, mid-1); //go left
		}
		return searchForElement(object, mid+1, hi); //go right
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

		public IteratorHelper(){
			nodes = new DictNode[currentSize];
			idx = 0;
			modCheck = modCounter;

			for (int i = 0; i <currentSize; i++){
					nodes[i] = list[i];
			}
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
		
		private void shellSort(){
			int in, out,  h=1;
			DictNode<K,V> n;
			int size = nodes.length;
			
			while (h <= size/3) // calculate gaps
				h = h * 3 + 1;
			while (h > 0){
				for (out = h; out < size; out++){
					n = nodes[out];
					in = out;
					while (in > h-1 && nodes[in-h].compareTo(n) >= 0){
						nodes[in] = nodes[in-h];
						in -= h;
					}
					nodes[in] = n;
				}
				h = (h-1)/3;
			}
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

	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}
		public V next() {
			return (V) nodes[idx++].value;
		}	
	}

	class DictNode<K, V> implements Comparable<DictNode<K, V>>{
		K key;
		V value;

		public DictNode(K k, V v){
			key = k;
			value = v;
		}

		@Override
		public int compareTo(DictNode<K, V> o) {
			return ((Comparable<K>)key).compareTo((K)o.key);
		}
	}
} //end class

