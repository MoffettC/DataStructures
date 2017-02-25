package data_structures;

import java.util.*;

public class UnorderedListPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E>{
	private UnorderedList list;
	public UnorderedListPriorityQueue(){
		list = new UnorderedList();
	}
	
	public boolean insert(E object) {
		list.addLast(object);
		return true;
	}

	public E remove() {
		return (E) list.remove(list.findMin());
	}

	public E peek() {
		return (E) list.findMin();
	}

	public boolean contains(E obj) {
		return list.contains(obj);
	}

	public int size() {
		return list.size();
	}

	public void clear() {
		list.clear();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean isFull() {
		return false;
	}

	public Iterator iterator() {
		return list.iterator();
	}
}
