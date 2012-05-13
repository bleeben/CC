package data.hash;

import java.util.LinkedList;

public class ChainingHash<K,V> extends SkeletonHashMap<K, V>{
	LinkedList<Entry<K,V>>[] entries;
	
	public int chain_hash(K key){
		return hash(key.hashCode()) & (capacity - 1);
	}
	
	@Override
	public V put(K key, V value) {
		int hash = key.hashCode();
		LinkedList<Entry<K,V>> ll = entries[hash % (capacity-1)];
		if(ll==null){
			ll = new LinkedList<Entry<K,V>>();
			entries[hash % (capacity-1)] = ll;
		}
		for(Entry<K,V> e:ll){
			if (hash == e.getHash() && key.equals(e.getKey())){
				e.setValue(value);
				return value;
			}
		}
		ll.add(new Entry<K, V>(hash,key,value));
		++size;
		resize();
		return value;
	}

	@Override
	public V get(K key) {
		int hash = key.hashCode();
		LinkedList<Entry<K,V>> ll = entries[hash % (capacity-1)];
		if(ll==null){
			return null;
		}
		for(Entry<K,V> e:ll){
			if (hash == e.getHash() && key.equals(e.getKey()))
				return e.getValue();			
		}
		return null;
	}

	@Override
	public V remove(K key) {
//		int hash = key.hashCode();
//		LinkedList<Entry<K,V>> ll = entries[hash % (capacity -1)];
//		for(Entry<K,V> e:ll){
//			if (hash == e.getHash() && key.equals(e.getKey())) {
//				V value = null;
//				
//				return e.getValue();			
//		}
//		int i = ll.indexOf(e);
//		if(i>=0){
//			e = ll.remove(i);
//			--size;
//			resize();
//			return e.getValue();
//		}
		return null;
	}

	@Override
	public boolean containsKey(K key) {
//		LinkedList<Entry<K,V>> ll = entries.get(chain_hash(key));
//		for(Entry<K,V> e:ll){
//			if (key.equals(e.getKey()))
//				return true;		
//		}
		return false;
	}

	@Override
	public boolean containsValue(V value) {
//		for(LinkedList<Entry<K,V>> ll:entries){
//			for(Entry<K,V> e:ll){
//				if(value.equals(e.getValue())){
//					return true;
//				}
//			}
//		}
		return false;
	}
	
	@Override
	public void resize() {
		if(checkLoad())
			return;

		LinkedList<Entry<K,V>>[] tempEntries = entries;
		
		while(!checkLoad()){
			capacity = size < minThreshold ? capacity/2 : capacity*2;
			minThreshold = (int) (minLoad*capacity);
			maxThreshold = (int) (maxLoad*capacity);
		}

		entries = (LinkedList<Entry<K,V>>[]) new LinkedList<?>[capacity];

		for(LinkedList<Entry<K,V>> ll:tempEntries){
			if(ll!=null && ll.size()>0){
				for(Entry<K,V> e:ll){
					put(e.getKey(),e.getValue());
				}
			}
		}
	}
	
	public ChainingHash(){
		this(DEFAULT_CAPACITY);
	}	
	public ChainingHash(int capacity){
		this.capacity = nearestPowerOfTwo(capacity);
		
		minLoad = 3.0f / 16.0f; // 3/16
		maxLoad = 3.0f / 4.0f; // 3/4
		minThreshold = (int) (minLoad*capacity);
		maxThreshold = (int) (maxLoad*capacity);

		entries = (LinkedList<Entry<K,V>>[]) new LinkedList<?>[capacity];
	}

	public static void main(String[] args){
		ChainingHash<String,Integer> test = new ChainingHash<String, Integer>();
		 test.put("Test", 10);
		 System.out.println(test.get("Test"));
		 
	}


}
