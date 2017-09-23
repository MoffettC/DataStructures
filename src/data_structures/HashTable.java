package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class HashTable<K extends Comparable<K>,V> implements DictionaryADT<K, V>{

	private int currentSize;
	private int maxSize;
	private int tableSize;
	private int modCounter;
	private UnorderedList<DictNode<K, V>>[] list;	

	public HashTable(int dictSize){
		currentSize = 0;
		modCounter = 0;
		maxSize = dictSize;
		double size = maxSize * 1.2;
		tableSize = (int) size;
		list = new UnorderedList[tableSize];
		for (int i = 0; i < tableSize; i++){
			list[i] = new UnorderedList<DictNode<K, V>>();
		}
	}

	public int getIndex(K key){
		return (key.hashCode() & 0x7FFFFFFF) % tableSize;
	}

	public boolean contains(K key) { /////CHECK CHECK CHECK
		if (!isEmpty()){
			DictNode dictNode = new DictNode(key, null);
			DictNode returnVal = list[getIndex(key)].find(dictNode);
			if (returnVal != null){ 	//if found
				return true;
			}
		}
		return false;
	}

	public boolean add(K key, V value) {
		if (!isFull() ){
			DictNode dictNode = new DictNode<K, V>(key, value);
			DictNode returnVal = list[getIndex(key)].find(dictNode);
			if (returnVal != null){ 	//if found
				return false;
			}
			list[getIndex(key)].addLast(dictNode);
			currentSize++;
			modCounter++;
			return true;
		}
		return false;
	}

	public boolean delete(K key) { /////CHECK CHECK CHECK
		if (isEmpty()){ return false; }
		DictNode tmp = list[getIndex(key)].remove(new DictNode<K, V>(key, null));
		if (tmp == null){ return false;}
		currentSize--;
		modCounter++;
		return true;
	}

	public V getValue(K key) {
		int test = getIndex(key);
		DictNode tmp = list[getIndex(key)].find(new DictNode(key, null));
		if (tmp == null){return null;}
		return (V) tmp.value;
	}

	public K getKey(V value) { 
		for (int i = 0; i < tableSize; i++){
			for (DictNode dn : list[i]){
				if (((Comparable) dn.value).compareTo(value) == 0){
					return (K) dn.key;
				}
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
		for (int i = 0; i <tableSize; i++){
			list[i].clear();
		}
		currentSize = 0;
		modCounter = 0;
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
			int j = 0;
			modCheck = modCounter;
			for (int i = 0; i < tableSize; i++){
				for (DictNode<K, V> n : list[i]){
					nodes[j++] = n;
				}
			}
			shellSort();
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
					while (in > h-1 && nodes[in-h].key.compareTo(n.key) >= 0){
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

		public int compareTo(DictNode<K, V> o) {
			return ((Comparable<K>)key).compareTo((K)o.key);
		}
	}
} //end class


