/*  Chris Moffett
	cssc0937
 */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinearLinkedList<E extends Comparable<E>> implements LinearListADT<E>  {
	private Node<E> head, tail;
	private int currentSize;
	private int modCounter;

	public LinearLinkedList(){
		head = tail = null;
		currentSize = 0;
		modCounter = 0;
	}

	public void addLast(E obj) {
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

	public void addFirst(E obj) {
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

	public void insert(E obj, int location) {
		location = location-1;
		Node<E> newNode = new Node<E>(obj);
		if (location <= currentSize && location >= 0){
			if (location == 0){
				addFirst(obj);
			} else if (location == currentSize-1){
				addLast(obj);
			} else {
				Node<E> prev = null;
				Node<E> current = head;
				for (int i = 0; i < location; i++){
					prev = current;
					current = current.next;
				}
				prev.next = newNode;
				newNode.next = current;
				modCounter++;
				currentSize++;
			}
		} else {
			throw new RuntimeException("Index is not within contiguous list");
		}
	}

	public E remove(int location) {
		return remove(get(location));
	}

	public E remove(E obj) {
		if (!isEmpty()){
			Node<E> current = head;
			Node<E> prevNode = null;

			while (current != null){			
				if (current.data.compareTo(obj) == 0){				
					if (prevNode == null){ 	//if from head, or last element
						head = tail = current.next; 
					} else if (current.next == null){
						prevNode.next = current.next; //if from tail
						tail = prevNode;
					} else {
						prevNode.next = current.next; //if from mid 
					}
					currentSize--;
					return current.data;
				}				
				prevNode = current;
				current = current.next;
			}
			return null;
		} else {
			return null;	
		}
	}

	public E removeFirst() {
		if (!isEmpty()){
			Node<E> first = head;
			head = first.next;
			if (head == null){ tail = null;}  //if head is pointing to null, then list is empty and tail points to null	
			currentSize--;
			return (E) first.data;
		} else {
			return null;
		}
	}

	public E removeLast() {
		if (!isEmpty()){
			Node<E> current = head;
			Node<E> prevNode = null;

			while (current.next != null){						
				prevNode = current;
				current = current.next;
			}				
			if (prevNode == null){ 	//if from head, or last element
				head = tail = current.next; 
			} else {
				prevNode.next = current.next; //if from tail, set to null
				tail = prevNode;
			}							
			currentSize--;
			return current.data;
		}
		return null;
	} 

	public E get(int location) {
		location = location-1;
		if (location < currentSize && location >= 0 && !isEmpty()){
			Node<E> current = head;
			for (int i = 0; i < location; i++){
				current = current.next;
			}
			return current.data;
		} else {
			throw new RuntimeException("Index is not within contiguous list");
		}	
	}

	public boolean contains(E obj) {
		if (!isEmpty()){
			Node<E> current = head;
			while (current != null){
				if (current.data.compareTo(obj) == 0){
					return true;
				}
				current = current.next;
			}
			return false;
		} else {
			return false;
		}
	}

	public int locate(E obj) {
		if (!isEmpty()){
			int counter = 0;
			Node<E> current = head;
			while (current != null){
				if (current.data.compareTo(obj) == 0){
					return counter+1; //1 based location
				}
				counter++;
				current = current.next;
			}
			return -1;
		} else {
			return -1;
		}		
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
			itrIndex = 1; //one based location
			modCheck = modCounter;
		}

		public boolean hasNext(){
			return itrIndex <= currentSize;
		}

		public E next(){
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			if (modCheck != modCounter){
				throw new ConcurrentModificationException();
			}
			return get(itrIndex++); //remove first? first is the 'next'?
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

}
