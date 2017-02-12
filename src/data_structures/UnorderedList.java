package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.OrderedArrayPriorityQueue.IteratorHelper;

public class UnorderedList {
	
	//mulitple types of inserts/removes

	class Node<T>{
		T data;
		Node<T> next;
		
		public Node(T obj){
			data = obj;
			next = null;
		}
	}
}

