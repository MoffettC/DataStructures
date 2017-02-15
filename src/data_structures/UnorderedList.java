package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.OrderedArrayPriorityQueue.IteratorHelper;
import data_structures.OrderedList.Node;

public class UnorderedList<E extends Comparable <E>> {
	private Node<E> head, tail;
	
	public UnorderedList(){
		head = tail = null;
	}
	//mulitple types of inserts/removes
	
	public void addFirst(E obj){
		Node<E> newNode = new Node<E>(obj);
		newNode.next = head;
		head = newNode;
	}
	
	public Comparable removeFirst() {
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
}

