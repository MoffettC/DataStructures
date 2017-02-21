package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.UnorderedList.IteratorHelper;

public class OrderedList<E extends Comparable <E>> implements Iterable<E>{
	private Node<E> head;
	private int currentSize;
	private int modCounter;
	
	public OrderedList(){
		head = null;
		currentSize = 0;
		modCounter = 0;
	}
	//only one insert/remove

	public void insert(E obj){ //search for order, otherwise insert from tail
		Node<E> newNode = new Node<E>(obj);
		if (isEmpty()){
			head = newNode;
		} else {
			tail.next = newNode;
			tail = newNode;
		}
		modCounter++;
		currentSize++;
	} 
	
	public E removeNext() { //remove from front
		if (!isEmpty()){
			Node<E> first = head;
			head = first.next;
			if (head == null){
				tail = null; //if head is pointing to null, then list is empty and tail points to null
			}
			return (E) first;
		} else {
			return null;
		}
	}
	
	public int find(E obj){
		if (!isEmpty()){
			int count = 0;
			Node<E> node = head;
			do {
				count++;
				if ((node.data.compareTo(obj) == 0)){
					return count; 		//return obj or true or pos?
				}
				node = node.next;
			} while (node.next != null);

			if ((node.data.compareTo(obj) == 0)){ //check last node
				count++;
				return count; //return obj or true or pos?
			}		
		}
		return -1; //not found
	}

	public boolean contains(E obj){
		return false;
	}
	
	public boolean isEmpty(){
		return currentSize == 0;
	}

	public int size(){
		return currentSize;
	}

	public void clear(){
		head = tail = null;
		modCounter = 0;
		currentSize = 0;
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
		return new IteratorHelper();
	}

	class IteratorHelper implements Iterator<E>{
		int itrIndex;
		long modCheck;

		public IteratorHelper(){
			itrIndex = 0;
			modCheck = modCounter;
		}

		public boolean hasNext(){
			return itrIndex < currentSize;
		}

		public E next(){
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			if (modCheck != modCounter){
				throw new ConcurrentModificationException();
			}
			return removeNext(); //remove first? first is the 'next'?
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
}

