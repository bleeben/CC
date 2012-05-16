package data.hash;

import java.lang.reflect.Array;

public class CachedLinearHash<K,V> extends LinearHash<K, V>{
	private static final int DEFAULT_CACHE_SIZE = 16;
	private Entry[] cachedEntries;
	private int cachedEntrySizeMask;	

	@Override
	public V put(K key, V value) {
		int cachei = key.hashCode() & cachedEntrySizeMask;
		Entry<K,V> e = cachedEntries[cachei];
		cachedEntries[cachei] = new Entry<K,V>(key.hashCode(),key,value);
		if(e != null){
			super.put(e.getKey(),e.getValue());
//			int i = find_space(e.getKey());
//			entries[i] = new Entry<K, V>(key.hashCode(),key,value);
//			deleted[i] = false;
//			++size;
//			resize();
		}
		return value;
	}
	
	@Override
	public V get(K key) {
		int cachei = key.hashCode() & cachedEntrySizeMask;
		Entry<K,V> e = cachedEntries[cachei];
		if(e != null){
			if(key.hashCode() == e.getHash() && key.equals(e.getKey()))
				return e.getValue();
		}
		return super.get(key);
//		int i = find(key);
//		Entry<K,V> e = entries[i];
//		if(e!=null){
//			return e.getValue();
//		}
//		return null;
	}

	@Override
	public V remove(K key) {
		int cachei = key.hashCode() & cachedEntrySizeMask;
		Entry<K,V> e = cachedEntries[cachei];
		if(e != null){
			if(key.hashCode() == e.getHash() && key.equals(e.getKey())) {
				cachedEntries[cachei] = null;
				return e.getValue();
			}
		}
		return super.remove(key);
//		
//		if(e!=null){
//			--size;
//			entries[i] = null;
//			deleted[i] = true;
//			resize();
//			return e.getValue();
//		}
//		return null;
	}

	@Override
	public boolean containsKey(K key) {
		int cachei = key.hashCode() & cachedEntrySizeMask;
		Entry<K,V> e = cachedEntries[cachei];
		if(e != null){
			if(key.hashCode() == e.getHash() && key.equals(e.getKey())) {
				return true;
			}
		}
		return super.containsKey(key);
//		int i = find(key);
//		return entries[i]!=null && key.equals(entries[i].getKey());
	}

	@Override
	public boolean containsValue(V value) {
		for(int i = 0; i < cachedEntries.length; ++i){
			Entry<K,V> e = cachedEntries[i];
			if(e != null && value.equals(e.getValue()))
				return true;
		}
		super.containsValue(value);
		return false;
	}
	
	public CachedLinearHash(){
		this(DEFAULT_CAPACITY, DEFAULT_CACHE_SIZE);
	}	
	
	public CachedLinearHash(int cacheSize){
		this(DEFAULT_CAPACITY, cacheSize);
	}	
	
	public CachedLinearHash(int capacity, int cacheSize){
		super(capacity);
		
		cachedEntries = new Entry[cacheSize];
		cachedEntrySizeMask = cacheSize-1;
	}
	

	public static void main(String[] args){
		 LinearHash<String,Integer> test = new LinearHash<String, Integer>();
		 test.put("Test", 10);
		 System.out.println("".hashCode());
		 System.out.println(hash("".hashCode()));
		 
	}
}