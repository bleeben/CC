package data.hash;

import java.util.Iterator;
import java.util.LinkedList;

public class FKSTemp<K,V> extends SkeletonHashMap<K, V>{
	Entry<K,V>[][] entries;
	HashFunction[] hs;
	int[] sizes;
	int startingCollisionSize=8;
	
	@Override
	public FKSTemp<K,V> spawn(){
		return new FKSTemp<K,V>();
		
	}
	public int chain_hash(K key){
		return hash(key.hashCode()) & (capacity - 1);
	}
	
	@Override
	public V put(K key, V value) {
		int hash = key.hashCode();
		int hashCap = hash % (capacity-1);
		Entry<K,V>[] ll = entries[hashCap];
		if(ll==null){
			ll = new Entry[startingCollisionSize];
			entries[hashCap] = ll;
			BLAH
			ll[0]=new Entry<K, V>(hash,key,value);
			sizes[hashCap]=1;
		} else {
			
		}
		for(K e:ll){
			if (hash == e.getHash() && key.equals(e.getKey())){
				e.setValue(value);
				return value;
			}
		}
		ll.add(0,new Entry<K, V>(hash,key,value));
		++size;
		resize();
		return value;
	}

	@Override
	public V get(K key) {
		int hash = key.hashCode();
		Entry<K,V>[] ll = entries[hash % (capacity-1)];
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

		Entry<K,V>[][] tempEntries = entries;
		
		while(!checkLoad()){
			capacity = size < minThreshold ? capacity/2 : capacity*2;
			minThreshold = (int) (minLoad*capacity);
			maxThreshold = (int) (maxLoad*capacity);
		}

		entries = (Entry<K,V>[][]) new Entry<?,?>[capacity][];

		for(Entry<K,V>[] ll:tempEntries){ // need to do by index
			if(ll!=null && sizesll>0){
				for(Entry<K,V> e:ll){
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

		entries = (Entry<K,V>[][]) new Entry<?,?>[capacity][];
	}

	public static void main(String[] args){
		FKSTemp<String,Integer> test = new FKSTemp<String, Integer>();
		 test.put("Test", 10);
		 System.out.println(test.get("Test"));
		 
	}

	@Override
	public Object copy() {
		// TODO Auto-generated method stub
		return null;
	}


}

class PerfectHashResolver<K,V> extends SkeletonHashMap<K, V>{

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void resize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object copy() {
		// TODO Auto-generated method stub
		return null;
	}

}