package data.hash;

import java.util.ArrayList;
import java.util.HashMap;

public class HashTestSuite<T extends SkeletonHashMap<String, ?>> {
	private T hashSubject;
	private T copy;
	private Runtime run;
	public HashTestSuite(T subject){
		setHashSubject(subject);
		setupTimers();
		run = Runtime.getRuntime();
		copy = copyTest();
		runTests();
	}
	
	public T getHashSubject() {
		return hashSubject;
	}
	public void setHashSubject(T hashSubject) {
		this.hashSubject = hashSubject;
	}
	
	private HashMap<String,Long> startTimes;
	private HashMap<String,Long> runTimes;
	private HashMap<String,Long> startNanos;
	private HashMap<String,Long> runNanos;
	private int timerTestNum;
	private HashMap<String,ArrayList<Long>> memoryUsages;
	
	public void setupTimers(){
		startTimes = new HashMap<String,Long>();
		runTimes = new HashMap<String,Long>();
		startNanos = new HashMap<String,Long>();
		runNanos = new HashMap<String,Long>();
		timerTestNum=1;
		memoryUsages=new HashMap<String,ArrayList<Long>>();
	}
	
	public void logMemory(String tag) { // string tag returned from startTimer
		long totalMem = run.totalMemory();
		if (!memoryUsages.containsKey(tag))
			memoryUsages.put(tag, new ArrayList<Long>());
		memoryUsages.get(tag).add(totalMem);
	}
	
	public String startTimer(String tag){ // returns true is started, false if already existed
		//if (startTimes.containsKey(tag) && !runTimes.containsKey(tag))
		//	return null;
		tag = tag+timerTestNum++;
		logMemory(tag);
		long startNano=System.nanoTime();
		startNanos.put(tag, startNano);
		long startTime=System.currentTimeMillis();
		startTimes.put(tag, startTime);
		return tag;
	}

	public boolean stopTimer(String tag){ // returns false if tag doesn't exist
		if (!startTimes.containsKey(tag))
			return false;
		long stopTime=System.currentTimeMillis();
		long stopNano=System.nanoTime();
		long runTime=stopTime-startTimes.get(tag);
		long runNano=stopNano-startNanos.get(tag);
		runTimes.put(tag, runTime);
		runNanos.put(tag, runNano);
		logMemory(tag);
		return true;
	}
	
	public void runTests() {
		testSample();
	}
	
	public T copyTest() {
		String tag=startTimer("copy");
		
		//run tests here
		T result=(T) hashSubject.copy();
		//
		
		stopTimer(tag); // output should be saved
		return result;
	}
	
	public void testSample(){
		String tag=startTimer("sample");
		
		//run tests here
		
		stopTimer(tag); // output should be saved
	}
	
	
	
	
}
