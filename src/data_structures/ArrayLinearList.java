/*  Chris Moffett
Your cssc account number
*/
package data_structures;
import java.util.Iterator;

public class ArrayLinearList<T> implements LinearListADT{
	private int startIndex = 1;
	private int lastIndex = DEFAULT_MAX_CAPACITY;
	public Object[] array = new Object[DEFAULT_MAX_CAPACITY];
	
	public ArrayLinearList(){
		//must remain empty
	}
	
	@Override
	public void addLast(Object obj) { //dynamic resize needed
		array[lastIndex] = obj;
		lastIndex--;
	}

	@Override
	public void addFirst(Object obj) { //dynamic resize needed
		array[startIndex] = obj;
		startIndex++;
	}

	@Override
	public void insert(Object obj, int location) {
		if (array[location] == null){
			array[location] = obj;
		} else {
			//shift
		}

	}

	@Override
	public Object remove(int location) {
		try {
			array[location] = null;
		} catch (RuntimeException e){

		}
		return null;
	}

	@Override
	public Object remove(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(int location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int locate(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
