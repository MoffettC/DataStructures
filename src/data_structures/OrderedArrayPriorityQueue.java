/*  Chris Moffett
	cssc0937
 */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedArrayPriorityQueue<E> implements PriorityQueue{

	private Comparable[] storage;
	private int currentSize;
	private int maxSize;
	private long modCounter;
	private boolean isFound;

	public OrderedArrayPriorityQueue(int size){
		maxSize = size;
		storage = (Comparable[]) new Object[maxSize];
		currentSize = 0;
		modCounter = 0;
		isFound = false;
	}

	public OrderedArrayPriorityQueue(){
		this(DEFAULT_MAX_CAPACITY);
	}

	public boolean insert(Comparable obj) { //FIFO starting at end of array going back
		if (isFull()){
			return false;
		}
		int where = findInsertionPoint(obj, 0, currentSize-1);
		for (int i = currentSize-1; i>=where; i--){
			storage[i+1] = storage[i];
		}
		storage[where] = (Comparable) obj;
		currentSize++;
		modCounter++;
		return true;
	}

	public Comparable remove() { //remove from end of array, FIFO
		if(isEmpty()){
			return null;
		}
		modCounter++;
		return (Comparable) storage[--currentSize];
	}

	public Comparable peek() { //change to binary search
		if (!isEmpty()){
			Comparable bestSoFar = (Comparable) storage[0];
			for (int i = 1; i < currentSize; i++){
				if (storage[i].compareTo(bestSoFar) < 0){
					bestSoFar = storage[i];
				}
			}
			return (Comparable) bestSoFar;
		}
		return null;
	}

	public boolean contains(Comparable obj) {
		int check = searchForElement(obj, 0, currentSize-1);
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

	private int findInsertionPoint(Comparable object, int lo, int hi){
		if(hi < lo){
			return lo;
		}

		int mid = (lo+hi) >> 1;

		if (((Comparable<E>)object).compareTo((E) storage[mid]) >= 0){
			return findInsertionPoint(object, lo, mid-1); //go left
		}
		return findInsertionPoint(object, mid+1, hi); //go right
	}

	private int searchForElement(Comparable object, int lo, int hi){
		if(hi < lo){
			return -1;
		}

		int mid = (lo+hi) >> 1; //bit shift, divide by 2

		if (((Comparable<E>)object).compareTo((E) storage[mid]) == 0){
			return mid; //go left
		} else if (((Comparable<E>)object).compareTo((E) storage[mid]) > 0){
			return searchForElement(object, lo, mid-1); //go left
		}
		return searchForElement(object, mid+1, hi); //go right
	}
}
