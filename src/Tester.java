/**
 * Created by maggielee on 4/17/17.
 */
import java.util.Iterator;

import data_structures.BinarySearchTree;
import data_structures.DictionaryADT;

public class Tester {
    DictionaryADT<Integer, String> myTest;

    public Tester() {
        myTest = new BinarySearchTree<Integer,String>();
        runTests();
    }

    private void runTests() {
        myTest.add(13,"M");
        myTest.add(1, "A");
        myTest.add(7, "G");
        myTest.add(9, "I");
        myTest.add(5, "E");
      //  myTest.add(14, "G");
        myTest.delete(13);
        Iterator<Integer> it = myTest.keys();
        while(it.hasNext())
            System.out.print(it.next() + " ");

    }

    public static void main(String [] args) {
        new Tester();
    }
}