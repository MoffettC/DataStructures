/*  Chris Moffett
cssc0274
*/
import java.util.ArrayList; //remove

import data_structures.ArrayLinearList;

public class ArrayLinearTester {

	private static ArrayLinearList<Object> aList;
	
	public static void main(String [] args)
	{
		ArrayList<String> string = new ArrayList<>();
		Object nope = new Object();
		//Object hello = new Object();
		aList = new ArrayLinearList<>();
		
		aList.addFirst(2);
		aList.addFirst(4);
		aList.addFirst(6);

		
		for (int i = 0; i < 100; i++){
			aList.addFirst(i);
		}
		
		aList.insert(nope, 60);
		System.out.println("nope");

		for (int j = 0; j < 104 ; j++){
			//aList.remove(24);
			aList.removeFirst();
		}
		//aList.remove(60);
		//string.clear();
		System.out.println("nope");
	}
	
	public void testMethod(){
		
	}
	
}
