package data.hash;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Test;


public class IntegerTest extends TestExtension {
	private int SIZE = 1000000;
	
	
	public void run(HashTestSuite suite){
		super.run(suite);
		testGet();
		testGet1();
		testGet2();
		testGet3();
	}
	
	@Test
	public void testGet() {
		LinearHash<String,Integer> table = new LinearHash<String,Integer>();
		table.put("1", 1);
		table.put("2", 2);
		table.put("3", 3);
		assertEquals(table.get("1"), new Integer(1));
		assertEquals(table.get("2"), new Integer(2));
		assertEquals(table.get("3"), new Integer(3));
	}
	
	@Test
	public void testGet1() {
		LinearHash<String,Integer> table = new LinearHash<String,Integer>();
		for(int i = 0; i < SIZE; ++i){
			table.put(""+i,i);
		}
		for(int i = 0; i < SIZE; ++i){
			assertEquals(table.get(""+i), new Integer(i));
		}
	}
	@Test
	public void testGet2() {
		Hashtable<String,Integer> table = new Hashtable<String,Integer>();
		for(int i = 0; i < SIZE; ++i){
			table.put(""+i,i);
		}
		for(int i = 0; i < SIZE; ++i){
			assertEquals(table.get(""+i), new Integer(i));
		}
	}	
	@Test
	public void testGet3() {
		LinearHash<String,Integer> table = new LinearHash<String,Integer>(2*SIZE);
		for(int i = 0; i < SIZE; ++i){
			table.put(""+i,i);
		}
		for(int i = 0; i < SIZE; ++i){
			assertEquals(table.get(""+i), new Integer(i));
		}
	}
}
