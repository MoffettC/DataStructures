package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedList<E extends Comparable <E>> implements Iterable<E> {
	private Node<E> head, tail;
	private int currentSize;
	private int modCounter;

	public UnorderedList(){
		head = tail = null;
		currentSize = 0;
		modCounter = 0;
	}
	//mulitple types of inserts/removes

	public void addFirst(E obj){
		Node<E> newNode = new Node<E>(obj);
		if (isEmpty()){
			head = tail = newNode;
		} else {
			newNode.next = head;
			head = newNode;
		}
		modCounter++;
		currentSize++;
	}

	public void addLast(E obj){
		Node<E> newNode = new Node<E>(obj);
		if (isEmpty()){
			head = tail = newNode;
		} else {
			tail.next = newNode;
			tail = newNode;
		}
		modCounter++;
		currentSize++;
	}

	public E removeFirst() {
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

	public E remove(E obj){
		int loc = find(obj);
		if (loc != -1){
			Node<E> node = head;
			Node<E> prevNode = node;
			for (int i = 1; i < loc; i++){
				prevNode = node;
				node = node.next;			//iterate thru to the node to remove
			}
			prevNode.next = node.next;
			if (prevNode.next == null){
				tail = prevNode;
			}
			return node.data;
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
			return removeFirst(); //remove first? first is the 'next'?
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
}

