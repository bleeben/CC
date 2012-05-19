package data.hash;

import java.util.ArrayList;
import java.util.HashMap;

public class TestMain {
	/*
	 * compile data here?
	 */
	
	public static void main(String[] arg) {
		HashTestSuite suite1 = new HashTestSuite(
				new LinearHash<String, Integer>());
		HashTestSuite suite2 = new HashTestSuite(
				new CachedLinearHash<String, Integer>());
		HashTestSuite suite3 = new HashTestSuite(
				new ChainingHash<String, Integer>());
		HashTestSuite suite4 = new HashTestSuite(
				new CuckooHash<String, Integer>());
		HashTestSuite suite5 = new HashTestSuite(
				new JavaHashWrapper<String, Integer>());
		HashTestSuite suite6 = new HashTestSuite(
				new FKSPerfectHash<String, Integer>());
		HashTestSuite suite7 = new HashTestSuite(new FKSTemp<String, Integer>());
		suite1.start();
		suite2.start();
		suite3.start();
		suite4.start();
		suite5.start();
		suite6.start();
		suite7.start();
		extractData(suite1);
	}

	public static void extractData(HashTestSuite suite) {
		HashMap data = suite.extractData();
		HashMap<String,Long> startTimes = (HashMap<String,Long>) data.get("startTimes");
		HashMap<String,Long> runTimes = (HashMap<String,Long>) data.get("runTimes");
		HashMap<String,Long> startNanos = (HashMap<String,Long>) data.get("startNanos");
		HashMap<String,Long> runNanos = (HashMap<String,Long>) data.get("runNanos");
		HashMap<String,ArrayList<Long>> memoryUsages = (HashMap<String,ArrayList<Long>>) data.get("memoryUsages");
		HashMap<String,Info> infos = (HashMap<String,Info>) data.get("infos");
		
		// yay data? compile it somehow?
	}
}
