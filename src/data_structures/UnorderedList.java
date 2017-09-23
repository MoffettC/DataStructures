/*  Chris Moffett
	cssc0937
 */
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
	
	public void insert(E obj, int location) {
		Node<E> newNode = new Node<E>(obj);
		if (location <= currentSize && location >= 0){
			if (location == 0){
				addFirst(obj);
			} else if (location == currentSize){
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

	public E removeFirst() {
		if (!isEmpty()){
			Node<E> first = head;
			head = first.next;
			if (head == null){
				tail = null; //if head is pointing to null, then list is empty and tail points to null
			}
			currentSize--;
			modCounter++;
			return first.data;
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
			modCounter++;
			return current.data;
		}
		return null;
	} 

	public E remove(E obj) {
		if (!isEmpty()){
			Node<E> current = head;
			Node<E> prevNode = null;
		
			while (current != null){			
				if (current.data.compareTo(obj) == 0){				
					currentSize--;
					modCounter++;
					if (prevNode == null){ 	//if from head, or last element
						head = tail = current.next; 
					} else if (current.next == null){
						prevNode.next = current.next; //if from tail
						tail = prevNode;
					} else {
						prevNode.next = current.next; //if from mid 
					}									
					return current.data;
				}				
				prevNode = current;
				current = current.next;
			}
		} 
		return null;	
	}

	public E find(E obj){
		if (!isEmpty()){
			int count = 0;	
			Node<E> current = head;
			while (current != null){
				if (current.data.compareTo(obj) == 0){
					return current.data;
				}
				count++;
				current = current.next;
			}	
		}
		return null; //not found
	}

	public E findMin(){
		if (head == null) return null;
		E best = head.data;
		Node<E> current = head.next;
		while (current != null){
			if (current.data.compareTo(best) < 0){
				best = current.data;
			}
			current = current.next;
		}
		return best;
	}

	public E removeMin(){
		if (isEmpty()) return null;
		Node<E> prevNode = null, prevBest = null, currentBest = head;
		E best = head.data;
		Node<E> current = head.next;
		prevNode = head;
		while (current != null){
			if (current.data.compareTo(best) < 0){
				prevBest = prevNode;
				currentBest = current;
				best = current.data;
			}
			prevNode = current;
			current = current.next;
		}
		if (prevNode == null){
			head = tail = current; //last one in list
		} else if (prevBest == null){ //if first one is best
			head = currentBest.next;
		} else if (currentBest.next == null){
			prevBest.next = currentBest.next; //if last one is best
			tail = prevBest;
		} else {
			prevBest.next = currentBest.next;
		}
		modCounter++;
		currentSize--;
		return best;
	}
	
	public E get(int location) {
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
		} 
		return false;
	}

	public int locate(E obj) {
		if (!isEmpty()){
			int counter = 0;
			Node<E> current = head;
			while (current != null){
				if (current.data.compareTo(obj) == 0){
					return counter; 
				}
				counter++;
				current = current.next;
			}
		} 
			return -1;
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
			return get(itrIndex++); 
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
}

