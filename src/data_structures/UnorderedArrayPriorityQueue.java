package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.OrderedArrayPriorityQueue.IteratorHelper;

public class UnorderedArrayPriorityQueue<E> implements PriorityQueue {

	private Comparable[] storage;
	private int currentSize;
	private int maxSize;
	private long modCounter;
	private int peakPos;

	public UnorderedArrayPriorityQueue(int size){
		maxSize = size;
		storage = (Comparable[]) new Object[maxSize];
		currentSize = 0;
		modCounter = 0;
		peakPos = 0;
	}

	public UnorderedArrayPriorityQueue(){
		this(DEFAULT_MAX_CAPACITY);
	}

	public boolean insert(Comparable obj) {
		if (isFull()) { return false; }
		
		storage[currentSize++] = (Comparable) obj; //always add to end, order doesnt matter	
		modCounter++;
		return true;
	}

	public Comparable remove() { //must iterate thru whole list to see which item has highest priority
		if(isEmpty()){ return null; }
		
		Comparable next = peek();
		for (int i = peakPos; i < currentSize-1; i++){ //shift down array
			storage[i] = storage[i+1];
		}	
		modCounter++;
		
		return next;
	}

	public Comparable peek() {  //unordered must search entire list
		if (!isEmpty()){
			Comparable bestSoFar = (Comparable) storage[0];
			for (int i = 1; i < currentSize; i++){
				if (storage[i].compareTo(bestSoFar) < 0){
					bestSoFar = storage[i];
					peakPos = i;
				}
			}
			return (Comparable) bestSoFar;
		}
		return null;
	}

	public boolean contains(Comparable obj) { //unordered must search entire list
		if (!isEmpty()){
			for (int i = 0; i < currentSize-1; i++){
				if (storage[i].compareTo(obj) < 0){
					return true;
				}
			}
		}
		return false;
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
}
