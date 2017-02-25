package archived;
/*  Chris Moffett
	cssc0937
 */
import java.util.*;

import data_structures.*;

public class ArrayLinearTester {

	private static ArrayLinearList<Object> aList;
	
	public static void main(String [] args)
	{ 
		//Object hello = new Object();
		aList = new ArrayLinearList<>();
		
		//Add first add last
		aList.addFirst("0000");
		aList.addFirst("4");
		aList.addFirst("6");
		aList.addLast("8");
		aList.addLast("9999");	
		
		aList.removeLast();
		aList.removeLast();
		
		for (int i = 0; i < 1000; i++){
			//aList.addFirst(String.format("Loop %d", i));
			aList.addLast(String.format("Loop %d", i));

		}
		Integer test31 = new Integer(9000);
		//aList.locate("0");
		//aList.contains(test31);
		System.out.println(aList.get(1));
		System.out.println(aList.get(999));
		//aList.removeLast();
		
		//Insert Remove
		aList.insert("nope", 60);
		System.out.println("nope");
		aList.remove("nope");
		
		//Remove First,Last,Obj,Location
		for (int j = 0; j < 1000; j++){
			//aList.removeFirst();
			aList.removeLast();
		}	
		
		aList.removeFirst();
		//aList.removeFirst();
		aList.remove("8");
		aList.removeLast();
		aList.remove(1);
		
		//Misc Commands
		boolean test = aList.contains("2");
		boolean test2 = aList.isEmpty();
		int test3 = aList.locate("9999");
		//Object test4 = aList.get(1);
		int test5 = aList.size();
		
		String nope = new String("hi");
		aList.addFirst(nope);
		//Iterators
		Iterator<Object> itr = aList.iterator();
		while(itr.hasNext()){
			String s = (String) itr.next();
			System.out.println("Itr: " + s);
		}
		
		//Clear
		aList.clear();	
		System.out.println("end");
	}	
}
