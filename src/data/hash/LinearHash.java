package data.hash;

import java.lang.reflect.Array;

public class LinearHash<K,V> extends SkeletonHashMap<K, V>{
	private boolean[] deleted;

	public int linear_hash(K key){
		return hash(key.hashCode()) & (capacity-1); // hash % capacity
	}
	public int find_space(K key){
		int i = linear_hash(key);
	     while (entries[i]!=null && !key.equals(entries[i].getKey())){
	    	 i = (i+1) % capacity;
	     }
	     return i;
	} 
	
	public int find(K key){
		int i = linear_hash(key);
	     while ((entries[i]!=null && !key.equals(entries[i].getKey())) || deleted[i]){
//	    	 i = (i+1) - (capacity & -((i+1)>=capacity);
	    	 i = (i+1) % capacity;
	     }
	     return i;
	}
	

	@Override
	public V put(K key, V value) {
		int i = find_space(key);
		if(entries[i]==null)
			++size;
		Entry<K,V> e = entries[i];
		entries[i] = new Entry<K, V>(hash(key.hashCode()),key,value,e);
		deleted[i] = false;
		resize();
		return value;
	}
	
	@Override
	public V get(K key) {
		int i = find(key);
		if(entries[i]!=null){
			return (V) entries[i].getValue();
		}
		return null;
	}

	@Override
	public V remove(K key) {
		int i = find(key);
		V value = null;
		
		if(entries[i]!=null){
			--size;
			value = (V) entries[i].getValue();
			entries[i] = null;
			deleted[i] = true;
			resize();
		}
		return value;
	}

	@Override
	public boolean containsKey(K key) {
		int i = find(key);
		return entries[i]!=null && key.equals(entries[i].getKey());
	}

	@Override
	public boolean containsValue(V value) {
		for(Entry<K,V> e:entries){
			if(e!=null && value.equals(e.getValue())){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void resize() {
		if(checkLoad())
			return;

//		System.out.println(size + " " + capacity + " " +minThreshold + " " + maxThreshold);
		
		Entry<K,V>[] tempEntries = entries;
		int tempCapacity = capacity;

		while(!checkLoad()){ //Sloow
			capacity = size < minThreshold ? capacity/2 : capacity*2;
			minThreshold = (int) (minLoad*capacity);
			maxThreshold = (int) (maxLoad*capacity);
//			System.out.println("-"+ size + " " + capacity + " " +minThreshold + " " + maxThreshold);
		}
		
		entries = new Entry[capacity];
		deleted = new boolean[capacity];

		for(int i = 0;i<tempCapacity;i++){
			Entry<K,V> e = tempEntries[i];
			if(e!=null){			
				int j = linear_hash(e.getKey());
				if(entries[j]==null){
					entries[j] = e;
				} else {
					while(entries[j]!=null){ //Slow slow can't figure out why :[
						j = (j+1) % capacity;
						System.out.println("" + i + " " + j);
					}
					entries[j] = e;
				}
			}
		}
	}

	
	public LinearHash(Class<K> c,Class<V> v){
		minLoad = 3.0f / 16.0f; // 3/6
		maxLoad = 3.0f / 4.0f; // 3/4
		
		init(c,v);
		deleted = new boolean[capacity];
	}	
	public LinearHash(Class<K> c,Class<V> v, int capacity){
		minLoad = 3.0f / 16.0f; // 3/6
		maxLoad = 3.0f / 4.0f; // 3/4
		capacity--; // round v up to the nearest power of 2.
		capacity |= capacity >> 1;
		capacity |= capacity >> 2;
		capacity |= capacity >> 4;
		capacity |= capacity >> 8;
		capacity |= capacity >> 16;
		capacity++;
		this.capacity = capacity;
		
		init(c,v);
		deleted = new boolean[capacity];
	}

	public static void main(String[] args){
		 LinearHash<String,Integer> test = new LinearHash<String, Integer>(String.class,Integer.class);
		 test.put("Test", 10);
		 System.out.println("0".hashCode());
		 System.out.println(hash("0".hashCode()));
		 
	}
}