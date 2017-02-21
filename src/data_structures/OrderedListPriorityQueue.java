package data_structures;

import java.util.Iterator;

public class OrderedListPriorityQueue<E> implements PriorityQueue{
	
	private OrderedList list;
	
	public OrderedListPriorityQueue(){
		list = new OrderedList();
	}

	@Override
	public boolean insert(Comparable object) {
		// TODO Auto-generated method stub
		return false;
	}

	public Comparable remove() {
		return list.removeNext();
	}

	@Override
	public Comparable peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(Comparable obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
