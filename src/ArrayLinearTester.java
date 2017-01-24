/*  Chris Moffett
cssc0274
*/
import data_structures.ArrayLinearList;

public class ArrayLinearTester {

	private static ArrayLinearList<Object> aList;
	
	public static void main(String [] args)
	{
		Object hello = new Object();
		aList = new ArrayLinearList<>();
		for (int i = 0; i < 100; i++){
			aList.addFirst(hello);
		}
		System.out.println("nope");

		for (int j = 0; j < 75 ; j++){
			aList.remove(24);
		}
		//aList.remove(60);
		System.out.println("nope");
	}
	
	public void testMethod(){
		
	}
	
}
