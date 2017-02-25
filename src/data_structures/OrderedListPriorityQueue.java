package data_structures;

import java.util.*;

public class OrderedListPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E>{
	
	private OrderedList list;
	
	public OrderedListPriorityQueue(){
		list = new OrderedList();
	}

	public boolean insert(E object) {
		list.insert(object);
		return true; //always will be able to insert??
	}

	public E remove() {
		return (E) list.removeNext();
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
		return true; //never full, linked list
	}

	public Iterator iterator() {
		return list.iterator();
	}

}
