/*  Chris Moffett
cssc0274
 */
package data_structures;
import data_structures.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E>{
	private int currentSize = 0;
	private int currentCapacity = DEFAULT_MAX_CAPACITY;
	private E[] array; //one based list

	public ArrayLinearList(){
		array = (E[]) new Object[DEFAULT_MAX_CAPACITY]; //one based list
	}

	public void dynamicResize(boolean isExpand){
		if (isExpand){
			if (currentSize >= currentCapacity){
				E[] temp = (E[]) new Object[currentCapacity * 2];
				currentCapacity = currentCapacity * 2;
				for (int i = 0; i < currentSize; i++){
					temp[i] = array[i];
				}
				array = temp;
			}
		} else {
			if (currentSize <= (currentCapacity/4)){
				E[] temp = (E[]) new Object[currentCapacity / 2];
				currentCapacity = currentCapacity / 2;
				for (int i = 0; i < currentSize; i++){
					temp[i] = array[i];
				}
				array = temp;
			}
		}
	}

	public void shiftElements(int begin, boolean toRight){
		if (toRight){
			for (int i = begin; i < currentSize; i++){
				array[i + 1] = array[i];
			}
		} else {
			for (int i = currentSize; i >= begin; i--){
				array[i] = array[i + 1];
			}
		}
	}

	@Override
	public void addLast(E obj) { //
		array[currentSize++] = obj;  //add to end of list
		dynamicResize(true);
	}

	@Override
	public void addFirst(E obj) { //
		currentSize++;
		dynamicResize(true);
		shiftElements(0, true); //shift all elements up
		array[0] = obj; //always add to beginning

	}

	@Override
	public void insert(E obj, int location) { //location one based
		if (location <= currentSize + 1){
			currentSize++;
			dynamicResize(true);
			shiftElements(location-1, true); //shift all elements up
			array[location-1] = obj;
		} else {
			throw new RuntimeException("Index is not contiguous");
		}

	}

	@Override
	public E remove(int location) { //location one based
		if ((location <= currentSize + 1) && (currentSize != 0)){
			E obj = array[location - 1];  
			currentSize--;
			dynamicResize(false);
			shiftElements(location-1, false); //shift all elements down
			return obj;
		} else {
			throw new RuntimeException("Index is not within list");
		}
	}

	@Override
	public E remove(E obj) { //dynamic resize needed, ordering perserved?
		// TODO Auto-generated method stub
		if (currentSize != 0){
			for (int i = 0; i < currentSize; i++){
				if (array[i].equals(obj)){
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
		if (currentSize != 0){
			if (array[0] != null){
				E temp = array[0];
				currentSize--;
				dynamicResize(false);
				shiftElements(0, false); //shift all elements down
				return temp;
			}
		}
		return null;
	}

	@Override
	public E removeLast() { //dynamic resize needed, ordering perserved?
		if (currentSize != 0){
			if (array[currentSize] != null){
				E temp = array[currentSize];
				array[currentSize] = null;
				currentSize--;
				dynamicResize(false);
				return temp;
			}
		}
		return null;
	}

	@Override
	public E get(int location) { //location one based
			E temp = array[location - 1];
			return temp;

			//throw new RuntimeException();

		//return null;
	}

	@Override
	public boolean contains(E obj) {
		if (currentSize != 0){
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
		if (currentSize != 0){
			for(int i = 0; i < currentSize; i++){
				if (((Comparable<E>)obj).compareTo(array[i]) == 0){
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
		if (currentSize == 0){
			return true;
		} else {
			return false;
		}
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
			if(itrIndex < currentSize){
				return true;
			} else {
				return false;
			}
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
