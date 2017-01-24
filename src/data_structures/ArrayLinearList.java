/*  Chris Moffett
cssc0274
 */
package data_structures;
import data_structures.*;
import java.util.Iterator;

public class ArrayLinearList<E> implements LinearListADT<E>{
	private int size = 0;
	private int currentCapacity = DEFAULT_MAX_CAPACITY;
	private E[] array; //one based list

	public ArrayLinearList(){
		array = (E[]) new Object[DEFAULT_MAX_CAPACITY]; //one based list
	}

	public void dynamicResize(boolean isExpand){
		if (isExpand){
			if (size >= currentCapacity){
				E[] temp = (E[]) new Object[currentCapacity * 2];
				currentCapacity = currentCapacity * 2;
				for (int i = 0; i < size; i++){
					temp[i] = array[i];
				}
				array = temp;
			}
		} else {
			if (size <= (currentCapacity/4)){
				E[] temp = (E[]) new Object[currentCapacity / 2];
				currentCapacity = currentCapacity / 2;
				for (int i = 0; i < size; i++){
					temp[i] = array[i];
				}
				array = temp;
			}
		}
	}

	public void shiftElements(int begin, boolean toRight){
		if (toRight){
			for (int i = begin; i < size; i++){
				array[i + 1] = array[i];
			}
		} else {
			for (int i = size; i >= begin; i--){
				array[i] = array[i + 1];
			}
		}
	}

	@Override
	public void addLast(E obj) { //
		array[size++] = obj;  //add to end of list
		dynamicResize(true);
	}

	@Override
	public void addFirst(E obj) { //
		size++;
		dynamicResize(true);
		shiftElements(0, true); //shift all elements up
		array[0] = obj; //always add to beginning

	}

	@Override
	public void insert(E obj, int location) { //location one based
		if (location <= size + 1){
			size++;
			dynamicResize(true);
			shiftElements(location-1, true); //shift all elements up
			array[location-1] = obj;
		} else {
			throw new RuntimeException("Index is not contiguous");
		}

	}

	@Override
	public E remove(int location) { //
		if (location <= size + 1){
			E obj = array[location - 1];
			size--;
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
		for (int i = 0; i < size; i++){
			if (array[i].equals(obj)){
				E temp = array[i];
				size--;
				shiftElements(i, false); //shift all elements down
				return temp;
			}
		}
		return null;
	}

	@Override 
	public E removeFirst() { //dynamic resize needed, ordering perserved?
		if (size != 0){
			for(int i = 1; i < size; i++){
				if (array[i] != null){
					E temp = array[i];
					array[i] = null;
					size--;
					return temp;
				}
			}
		}
		return null;
	}

	@Override
	public E removeLast() { //dynamic resize needed, ordering perserved?
		if (size != 0){
			for(int i = size; i >= 1; i--){
				if (array[i] != null){
					E temp = array[i];
					array[i] = null;
					size--;
					return temp;
				}
			}
		}
		return null;
	}

	@Override
	public E get(int location) {
		try {
			E temp = array[location];
			return temp;
		} catch (RuntimeException e){

		}
		return null;
	}

	@Override
	public boolean contains(E obj) {
		if (size != 0){
			for(int i = 1; i < size; i++){
				if (array[i].equals(obj)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int locate(E obj) {
		if (size != 0){
			for(int i = 1; i < size; i++){
				if (array[i].equals(obj)){
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public void clear() {
		size = 0;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		Iterator<E> itr = new Iterator<E>(){

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public E next() {
				// TODO Auto-generated method stub
				return null;
			}

		};
		return null;
	}

}
