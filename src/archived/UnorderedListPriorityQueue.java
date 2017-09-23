/*  Chris Moffett
	cssc0937
 */
package archived;

import java.util.Iterator;

import data_structures.UnorderedList;

public class UnorderedListPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E>{
	private UnorderedList list;
	public UnorderedListPriorityQueue(){
		list = new UnorderedList();
	}
	
	public boolean insert(E object) {
		list.addLast(object);
		return true;
	}

	public E remove() {return (E) list.removeMin();}
	public E peek() {return (E) list.findMin();}
	public boolean contains(E obj) {return list.contains(obj);}
	public int size() {return list.size();}

	public void clear() {list.clear();}
	public boolean isEmpty() {return list.isEmpty();}
	public boolean isFull() {return false;} //never full LL
	public Iterator iterator() {return list.iterator();}
}
