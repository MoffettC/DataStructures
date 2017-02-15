package data_structures;

import java.util.*;

public class UnorderedList<E extends Comparable <E>> {
	private Node<E> head, tail;
	private int currentSize;
	private int modCounter;
	
	public UnorderedList(){
		head = tail = null;
	}
	//mulitple types of inserts/removes
		
	public void addFirst(E obj){
		Node<E> newNode = new Node<E>(obj);
		newNode.next = head;
		head = newNode;
	}
		
	public E addLast(E obj){
		
		return null;
	}
	
	public E remove(E obj){
		return null;
	}
	
	public E removeFirst() {
		Node<E> first = head;
		head = first.next;
		return (E) first;
	}
	
	public E removeLast(){
		return null;
	}
	
	public E find(E obj){
		return null;
	}
	
	public E peek(E obj){
		return null;
	}
	
	public boolean contains(E obj){
		return false;
	}
	
	public boolean isEmpty(){
		return false;
	}
	
	public int size(){
		return currentSize;
	}
	
	public void clear(){
		
	}

	class Node<T>{
		T data;
		Node<T> next;
		
		public Node(T obj){
			data = obj;
			next = null;
		}
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
			return removeFirst(); //remove first? first is the 'next'?
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
}

