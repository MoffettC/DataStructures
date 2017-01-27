/*  Chris Moffett
cssc0274
*/
import java.util.ArrayList; //remove
import java.util.Iterator;

import data_structures.ArrayLinearList;

public class ArrayLinearTester {

	private static ArrayLinearList<String> aList;
	
	public static void main(String [] args)
	{ 
		Object nope = new Object();
		//Object hello = new Object();
		aList = new ArrayLinearList<>();
		
		//Add first add last
		aList.addFirst("0000");
		aList.addFirst("4");
		aList.addFirst("6");
		aList.addLast("8");
	
		for (int i = 0; i < 100; i++){
			aList.addFirst(String.format("Loop %d", i));
		}
		aList.addLast("9999");
		
		//Insert Remove
		aList.insert("nope", 60);
		System.out.println("nope");
		aList.remove("nope");
		
		//Remove First,Last,Obj,Location
		for (int j = 0; j < 100; j++){
			aList.removeFirst();
		}	
		aList.removeLast();
		aList.remove(1);
		aList.remove("4");
		
		//Misc Commands
		boolean test = aList.contains("2");
		boolean test2 = aList.isEmpty();
		int test3 = aList.locate("9999");
		Object test4 = aList.get(1);
		int test5 = aList.size();
		
		//Iterators
		Iterator<String> itr = aList.iterator();
		while(itr.hasNext()){
			String s = itr.next();
			System.out.println(s);
		}
		
		//Clear
		aList.clear();	
		System.out.println("end");
	}	
}
