/*  Chris Moffett
	cssc0937
 */

package archived;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E>{
	private int currentSize = 0;
	private int currentCapacity = DEFAULT_MAX_CAPACITY;
	private E[] array; //one based list

	public ArrayLinearList(){
		array = (E[]) new Object[DEFAULT_MAX_CAPACITY]; 
	}

	private void expandArray(){
		if (currentSize >= currentCapacity){
			currentCapacity = currentCapacity * 2;
			E[] temp = (E[]) new Object[currentCapacity];
			for (int i = 0; i < currentSize; i++){
				temp[i] = array[i];
			}
			array = temp;
		}

	}

	private void shrinkArray(){
		if (currentSize < (currentCapacity/4)){
			currentCapacity = currentCapacity / 2;
			E[] temp = (E[]) new Object[currentCapacity];
			for (int i = 0; i < currentSize + 1; i++){ //currentSize+1 needed to cover where array gets reduced, but outside index needs to get copied
				temp[i] = array[i];
			}
			array = temp;
		}
	}

	private void shiftElementsRight(int begin){
		for (int i = currentSize; i > begin; i--){
			array[i] = array[i - 1];
		}
	}

	private void shiftElementsLeft(int begin){
		for (int i = begin; i < currentSize; i++){
			array[i] = array[i + 1];
		}
	}

	@Override
	public void addLast(E obj) { 
		insert(obj, currentSize+1); //add outside of array
	}

	@Override
	public void addFirst(E obj) { 
		insert(obj, 1); 
	}

	@Override
	public void insert(E obj, int location) { //location one based
		location = location-1;
		if (location <= currentSize && location >= 0){ //needs to include 1 outside of array index
			currentSize++;
			expandArray();
			shiftElementsRight(location); 
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
			shrinkArray();
			shiftElementsLeft(location); 
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
					shrinkArray();
					shiftElementsLeft(i);
					return temp;
				} 
			}
		}
		return null;
	}

	@Override 
	public E removeFirst() {
		return remove(1);
	}

	@Override
	public E removeLast() { 
		return remove(currentSize);
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
