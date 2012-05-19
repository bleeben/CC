package data.hash;

import java.util.Hashtable;

public class FKSTemp<K,V> extends SkeletonHashMap<K, V>{
	Hashtable<K,V>[] entries;
	//HashFunction[] hs;
	//int[] sizes;
	int startingCollisionSize=8;
	
	@Override
	public FKSTemp<K,V> spawn(){
		return new FKSTemp<K,V>();
		
	}
	
	@Override
	public V put(K key, V value) {
		int hash = key.hashCode();
		int hashCap = hash % (capacity-1);
		Hashtable<K,V> ll = entries[hashCap];
		if(ll==null){
			ll = new Hashtable<K,V>(startingCollisionSize);
			entries[hashCap] = ll;
			//BLAH
			//ll.put(key,value);
		} else {
			
		}
		ll.put(key, value);
		//ll.add(0,new Entry<K, V>(hash,key,value));
		++size;
		resize();
		return value;
	}

	@Override
	public V get(K key) {
		int hash = key.hashCode();
		int hashCap = hash % (capacity-1);
		Hashtable<K,V> ll = entries[hashCap];
		if(ll==null){
			return null;
		} else {
			return ll.get(key);
		}
	}

	@Override
	public V remove(K key) {
		int hash = key.hashCode();
		int hashCap = hash % (capacity-1);
		Hashtable<K,V> ll = entries[hashCap];
		V e = ll.remove(key);
		if (e == null)
			return e;
		--size;
		resize();
		return e;
	}

	@Override
	public boolean containsKey(K key) {
		int hash = key.hashCode();
		int hashCap = hash % (capacity-1);
		Hashtable<K,V> ll = entries[hashCap];
		return ll.containsKey(key);
	}

	@Override
	public boolean containsValue(V value) {
		for(Hashtable<K,V> ll:entries){
			if (ll.containsValue(value))
				return true;
		}
		return false;
	}
	
	@Override
	public void resize() {
		if(checkLoad())
			return;

		Hashtable<K,V>[] tempEntries = entries;
		
		while(!checkLoad()){
			capacity = size < minThreshold ? capacity/2 : capacity*2;
			minThreshold = (int) (minLoad*capacity);
			maxThreshold = (int) (maxLoad*capacity);
		}

		entries = (Hashtable<K,V>[]) new Hashtable<?,?>[capacity];
		
		for(Hashtable<K,V> ll:tempEntries){ // need to do by index
			if(ll!=null){
				for(java.util.Map.Entry<K, V> e:ll.entrySet()){
					put(e.getKey(),e.getValue());
				}
			}
		}
	}
	
	public FKSTemp(){
		this(DEFAULT_CAPACITY);
	}	
	public FKSTemp(int capacity){
		this.capacity = nearestPowerOfTwo(capacity);
		
		minLoad = 3.0f / 16.0f; // 3/16
		maxLoad = 3.0f / 4.0f; // 3/4
		minThreshold = (int) (minLoad*capacity);
		maxThreshold = (int) (maxLoad*capacity);

		entries = (Hashtable<K,V>[]) new Hashtable<?,?>[capacity];
	}

	public static void main(String[] args){
		FKSTemp<String,Integer> test = new FKSTemp<String, Integer>();
		 test.put("Test", 10);
		 System.out.println(test.get("Test"));
		 
	}


}