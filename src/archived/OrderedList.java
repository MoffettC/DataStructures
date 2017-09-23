/*  Chris Moffett
	cssc0937
 */
package archived;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
		Node<E> prev = null, current = head;
		while(current != null && obj.compareTo(current.data) >= 0){
			prev = current;
			current = current.next;
		}	
		if(prev == null){
			newNode.next = current;
			head = newNode;
		} else {
			prev.next = newNode;
			newNode.next = current;
		}	
		modCounter++;
		currentSize++;
	} 
	
	public E removeNext() { //remove from front
		if (!isEmpty()){
			Node<E> first = head;
			head = first.next;
			currentSize--;
			modCounter++;
			return (E) first.data;
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
		head = null;
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

