package data_structures;

import java.util.Iterator;

import data_structures.UnorderedList.Node;

public class OrderedList<E> implements Iterable<E> {
	private Node<E> head, tail;
	
	public OrderedList(){
		head = tail = null;
	}
	//only one insert/remove

	
	
	class Node<T>{
		T data;
		Node<T> next;
		
		public Node(T obj){
			data = obj;
			next = null;
		}
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}

