package data_structures;

import java.util.*;

public class OrderedList<E extends Comparable <E>> implements Iterable<E> {
	private Node<E> head, tail;
	
	public OrderedList(){
		head = tail = null;
	}
	//only one insert/remove

	public void insert(E obj){
		Node<E> newNode = new Node<E>(obj);
		newNode.next = head;
		head = newNode;
	} 
	
	public E remove() {
		return null;
	}
	
	class Node<T>{
		T data;
		Node<T> next;
		
		public Node(T obj){
			data = obj;
			next = null;
		}
	}
		
	public Iterator<E> iterator() {
		return null;
	}


}

