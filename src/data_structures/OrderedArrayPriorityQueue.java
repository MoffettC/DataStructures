/*  Chris Moffett
	cssc0937
 */
package data_structures;

import java.util.*;

public class OrderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E>{

	private E[] storage;
	private int currentSize;
	private int maxSize;
	private long modCounter;
	private boolean isFound;

	public OrderedArrayPriorityQueue(int size){
		maxSize = size;
		storage = (E[]) new Object[maxSize];
		currentSize = 0;
		modCounter = 0;
		isFound = false;
	}

	public OrderedArrayPriorityQueue(){
		this(DEFAULT_MAX_CAPACITY);
	}

	public boolean insert(E obj) { //FIFO starting at end of array going back
		if (isFull()){
			return false;
		}
		int where = findInsertionPoint(obj, 0, currentSize-1);
		for (int i = currentSize-1; i>=where; i--){
			storage[i+1] = storage[i];
		}
		storage[where] = (E) obj;
		currentSize++;
		modCounter++;
		return true;
	}

	public E remove() { //remove from end of array, FIFO
		if(isEmpty()){
			return null;
		}
		modCounter++;
		return (E) storage[--currentSize];
	}

	public E peek() { //change to binary search
		if (!isEmpty()){
			E bestSoFar = (E) storage[0];
			for (int i = 1; i < currentSize; i++){
				if (storage[i].compareTo(bestSoFar) < 0){
					bestSoFar = storage[i];
				}
			}
			return (E) bestSoFar;
		}
		return null;
	}

	public boolean contains(E obj) {
		int check = searchForElement(obj, 0, currentSize-1); //binary search
		if (check < 0){
			return false;
		}
		return true;
	}

	public int size() {
		return currentSize;
	}

	public void clear() {
		currentSize = 0;		
	}


	public boolean isEmpty() {
		return currentSize == 0;
	}

	public boolean isFull() {
		return currentSize == maxSize;
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
			return (E) storage[itrIndex++];
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

	private int findInsertionPoint(E object, int lo, int hi){
		if(hi < lo){
			return lo;
		}

		int mid = (lo+hi) >> 1;

		if (object.compareTo((E) storage[mid]) >= 0){
			return findInsertionPoint(object, lo, mid-1); //go left
		}
		return findInsertionPoint(object, mid+1, hi); //go right
	}

	private int searchForElement(E object, int lo, int hi){
		if(hi < lo){
			return -1; //not found
		}

		int mid = (lo+hi) >> 1; //bit shift, divide by 2

		if (object.compareTo((E) storage[mid]) == 0){
			return mid; 
		} else if ((object).compareTo((E) storage[mid]) > 0){
			return searchForElement(object, lo, mid-1); //go left
		}
		return searchForElement(object, mid+1, hi); //go right
	}
}
