
/*	CS310 Spring 2017
	Here is a simple driver program you can use to test your code.  If
	it fails to compile you have something wrong with your code.  Add
	additional statements to complete the testing.
*/	


import data_structures.*;

public class Driver {
	public Driver() {
		runTests();
		}
		
	private void runTests() {
	    LinearListADT<Integer> list = new ArrayLinearList<Integer>();
	    list.insert(1,1);
	    list.insert(2,2);
	    list.insert(3,2);
	    list.insert(4,2);
	    list.insert(5,5);								
	    try {
		list.insert(2,0);  // should throw an exception!
		}
	    catch(RuntimeException e) {} 
	    try {
		list.insert(77777,7);  // should throw an exception!
		}
	    catch(RuntimeException e) {} 
	    list.addFirst(-1);
	    list.addLast(-1);	    
	    //Should print -1,1,4,3,2,5,-1
		for(int i : list)
			System.out.println(i);
			
	    list.clear();
		for(int i : list) // should not print anything, nor crash
			System.out.println(i);	
			
	    for(int i=0; i < 100; i++)
	    	list.insert((i+1),1);
	    for(int i : list) // should print 100 .. 1
		System.out.print(i+" ");
	    System.out.println();		    
		}
	
	public static void main(String [] args) {
		new Driver();
		}
	}
