/*  Chris Moffett
cssc0274
 */
package data_structures;
import java.util.*;

public class ArrayLinearList<E> implements LinearListADT<E>{
	private int currentSize = 0;
	private int currentCapacity = DEFAULT_MAX_CAPACITY;
	private E[] array; //one based list

	public ArrayLinearList(){
		array = (E[]) new Object[DEFAULT_MAX_CAPACITY]; 
	}

	public void dynamicResize(boolean isExpand){
		if (isExpand){
			if (currentSize >= currentCapacity){
				currentCapacity = currentCapacity * 2;
				E[] temp = (E[]) new Object[currentCapacity];
				for (int i = 0; i < currentSize; i++){
					temp[i] = array[i];
				}
				array = temp;
			}
		} else {
			if (currentSize < (currentCapacity/4)){
				currentCapacity = currentCapacity / 2;
				E[] temp = (E[]) new Object[currentCapacity];
				for (int i = 0; i < currentSize + 1; i++){ //currentSize+1 needed to cover the case where array gets reduced, but outside index needs to get copied
					temp[i] = array[i];
				}
				array = temp;
			}
		}
	}

	public void shiftElements(int begin, boolean toRight){
		if (toRight){
			for (int i = currentSize; i > begin; i--){
				array[i] = array[i - 1];
			}
		} else {
			for (int i = begin; i < currentSize; i++){
				array[i] = array[i + 1];
			}
		}
	}

	@Override
	public void addLast(E obj) { 
		array[currentSize++] = obj;  //add to end of list
		dynamicResize(true);
	}

	@Override
	public void addFirst(E obj) { 
		currentSize++;
		dynamicResize(true);
		shiftElements(0, true); //shift all elements up
		array[0] = obj; //always add to beginning

	}

	@Override
	public void insert(E obj, int location) { //location one based
		location = location-1;
		if (location <= currentSize && location >= 0){ //needs to include 1 outside of array index
			currentSize++;
			dynamicResize(true);
			shiftElements(location, true); //shift all elements up
			array[location] = obj;
		} else {
			throw new RuntimeException("Index is not within contiguous list");
		}

	}

	@Override
	public E remove(int location) { //location one based
		location = location-1;
		if (location < currentSize && location >= 0 && !isEmpty()){
			E obj = array[location];  
			currentSize--;
			dynamicResize(false);
			shiftElements(location, false); //shift all elements down
			return obj;
		} else {
			throw new RuntimeException("Index is not within contiguous list");
		}
	}

	@Override
	public E remove(E obj) { 
		if (!isEmpty()){
			for (int i = 0; i < currentSize; i++){
				if (((Comparable<E>) obj).compareTo(array[i]) == 0){
					E temp = array[i];
					currentSize--;
					dynamicResize(false);
					shiftElements(i, false); //shift all elements down
					return temp;
				} 
			}
		}
		return null;
	}

	@Override 
	public E removeFirst() { //dynamic resize needed, ordering perserved?
		if (!isEmpty()){
			E temp = array[0];
			currentSize--;
			dynamicResize(false);
			shiftElements(0, false); //shift all elements down
			return temp;
		}
		return null;
	}

	@Override
	public E removeLast() { //dynamic resize needed, ordering perserved?
		if (!isEmpty()){
			E temp = array[--currentSize];
			dynamicResize(false);
			return temp;
		}
		return null;
	}

	@Override
	public E get(int location) { //location one based
		location = location-1;
		if (location < currentSize && location >= 0 && !isEmpty()){
			return array[location];
		} else {
			throw new RuntimeException("Index is not within contiguous list");
		}		
	}

	@Override
	public boolean contains(E obj) {
		if (!isEmpty()){
			for (int i = 0; i < currentSize; i++){
				if (((Comparable<E>)obj).compareTo(array[i]) == 0){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int locate(E obj) {
		if (!isEmpty()){
			for(int i = 0; i < currentSize; i++){
				if (((Comparable<E>) obj).compareTo(array[i]) == 0){
					return i+1; //one based location
				}
			}
		}
		return -1;
	}

	@Override
	public void clear() {
		currentSize = 0;
	}

	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	class IteratorHelper implements Iterator<E>{
		int itrIndex;

		public IteratorHelper(){
			itrIndex = 0;
		}

		public boolean hasNext(){
			return itrIndex < currentSize;
		}

		public E next(){
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			return array[itrIndex++];
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
}
