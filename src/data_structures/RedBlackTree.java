package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class RedBlackTree<K extends Comparable<K>,V> implements DictionaryADT<K, V>  {

	TreeMap<K, V> rbt;
	private int modCounter;
	
	public RedBlackTree(){
		rbt = new TreeMap<>();
	}
	
	public boolean contains(K key) {
		return rbt.containsKey(key);
	}

	public boolean add(K key, V value) {
		if (!rbt.containsKey(key)){
			 rbt.put(key, value);
			 modCounter++;
			return true;
		}
		return false;
	}

	public boolean delete(K key) {
		V v = rbt.remove(key);
		if (v != null){
			modCounter++;
			return true;
		}
		return false;
	}

	public V getValue(K key) {
		return rbt.get(key);
	}

	public K getKey(V value) {
		K k = null;
		for(Entry<K, V> entry : rbt.entrySet()) {
			  if (((Comparable) entry.getValue()).compareTo(value) == 0){
				 k = entry.getKey();
			  }
		}
		return k;
	}

	public int size() {
		return rbt.size();
	}

	public boolean isFull() {
		return false; //never full
	}

	public boolean isEmpty() {
		return rbt.size() == 0;
	}

	public void clear() {
		rbt.clear();
	}

	public Iterator keys() {
		return new KeyIteratorHelper();
	}

	public Iterator values() {
		return new ValueIteratorHelper();
	}

	abstract class IteratorHelper<E> implements Iterator<E>{
		protected Entry<K,V>[] nodes;
		protected int idx;
		protected long modCheck;
		
		public IteratorHelper(){
			nodes = new Entry[rbt.size()];
			idx = 0;
			int j = 0;
			modCheck = modCounter;
			for(Entry<K, V> entry : rbt.entrySet()) {
					nodes[j++] = entry;
			}
			shellSort();
		}
		
		public boolean hasNext(){
			if (modCheck != modCounter)
					throw new ConcurrentModificationException();
			return idx < rbt.size();
		}
		
		public abstract E next();
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
		
		private void shellSort(){
			int in, out,  h=1;
			Entry<K,V> n;
			int size = nodes.length;
			
			while (h <= size/3) // calculate gaps
				h = h * 3 + 1;
			while (h > 0){
				for (out = h; out < size; out++){
					n = nodes[out];
					in = out;
					while (in > h-1 && (nodes[in-h].getKey()).compareTo(n.getKey()) >= 0){
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
			return (K) nodes[idx++].getKey();
		}	
	}
	
	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}
		public V next() {
			return (V) nodes[idx++].getValue();
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
}
