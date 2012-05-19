package data.hash;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.junit.Test;

public class HashTestSuite<T extends SkeletonHashMap<String, Integer>> {
	private T hashSubject;
	private T spawnCopy;
	private Runtime run;
	public HashTestSuite(T subject){
		setHashSubject(subject);
		setupTimers();
		run = Runtime.getRuntime();
		spawnCopy = spawnTest();
	}
	
	public HashMap<String,HashMap<String,Long>> extractTimes(){
		HashMap<String, HashMap<String, Long>> result = new HashMap<String, HashMap<String, Long>>();
		result.put("startTimes", startTimes);
		result.put("runTimes", runTimes);
		result.put("startNanos", startNanos);
		result.put("runNanos", runNanos);
		return result;
	}
	
	public HashMap<String,Info> extractInfos(){
		return infos;
	}
	
	public HashMap<String,ArrayList<Long>> extractMemories(){
		return memoryUsages;
	}
	
	public HashMap extractData(){
		HashMap result = new HashMap();
		result.put("startTimes", startTimes);
		result.put("runTimes", runTimes);
		result.put("startNanos", startNanos);
		result.put("runNanos", runNanos);
		result.put("memoryUsages", memoryUsages);
		result.put("infos", infos);
		return result;
	}
	
	public void start(){
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
	private HashMap<String,Info> infos;
	
	public void setupTimers(){
		startTimes = new HashMap<String,Long>();
		runTimes = new HashMap<String,Long>();
		startNanos = new HashMap<String,Long>();
		runNanos = new HashMap<String,Long>();
		timerTestNum=1;
		memoryUsages=new HashMap<String,ArrayList<Long>>();
		infos = new HashMap<String,Info>();
	}
	
	public void logMemory(String tag) { // string tag returned from startTimer
		long totalMem = run.totalMemory();
		if (!memoryUsages.containsKey(tag))
			memoryUsages.put(tag, new ArrayList<Long>());
		memoryUsages.get(tag).add(totalMem);
	}
	
	public void logInfo(String tag) { // string tag returned from startTimer
		infos.put(tag, new Info(hashSubject.getLoad(),hashSubject.getPrefLoad(), hashSubject.getSize()));
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
		logInfo(tag);
		return true;
	}
	
	public void runTests() {
		testSample();
		testPutGet(1000000);
		//new IntegerTest().run(this);
	}
	
	public T spawnTest() {
		String tag=startTimer("spawn");
		
		//run tests here
		T result=(T) hashSubject.spawn();
		//
		
		stopTimer(tag); // output should be saved
		return result;
	}
	
	public void testSample(){
		String tag=startTimer("sample");
		
		//run tests here
		
		stopTimer(tag); // output should be saved
	}
	
	public void testPutGet(int size) {
		hashSubject = spawnTest();
		String tag = startTimer("put");
		for(int i = 0; i < size; ++i){
			hashSubject.put(""+i,i);
			logInfo(tag);
		}
		/*for(int i = 0; i < size; ++i){
			assertEquals(hashSubject.get(""+i), new Integer(i));
		}*/
		stopTimer(tag);
		tag = startTimer("get");
		for(int i = 0; i < size; ++i){
			hashSubject.get(""+i);
			logInfo(tag);
		}
		/*for(int i = 0; i < size; ++i){
			assertEquals(hashSubject.get(""+i), new Integer(i));
		}*/
		stopTimer(tag);
		tag = startTimer("containsKey");
		for(int i = 0; i < size; ++i){
			hashSubject.containsKey(""+i);
			logInfo(tag);
		}
		/*for(int i = 0; i < size; ++i){
			assertEquals(hashSubject.get(""+i), new Integer(i));
		}*/
		stopTimer(tag);
		tag = startTimer("containsVal");
		for(int i = 0; i < size; ++i){
			hashSubject.containsValue(i);
			logInfo(tag);
		}
		/*for(int i = 0; i < size; ++i){
			assertEquals(hashSubject.get(""+i), new Integer(i));
		}*/
		stopTimer(tag);
	}
	
	
	
}
