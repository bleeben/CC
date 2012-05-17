package data.hash;

import static org.junit.Assert.assertEquals;

public class CuckooHash<K,V> extends SkeletonHashMap<K, V>{
	private Entry[][] entries;

	private HashFunction f1;
	private HashFunction f2;
	
	private int maxCycle;
	
	
	@Override
	public V put(K key, V value) {
		Entry<K,V> e;
		e = entries[0][f1.hash(key)];
		if (e!=null && key.equals(e.getKey())){
			e.setValue(value);
			return value;
		}
		e = entries[1][f2.hash(key)];
		if (e!=null && key.equals(e.getKey())){
			e.setValue(value);
			return value;
		}
		int t = 0;
		int m = f1.hash(key);
		for(int i = 0; i<maxCycle; ++i){
			e = entries[t][m];
			entries[t][m] = new Entry<K,V>(key.hashCode(),key,value);
			if (e==null) {
				++size;
				resize();
				return value;
			}
			
			key = e.getKey();
			value = e.getValue();
			
			if (t == 0){
				t = 1;
				m = f2.hash(key);
			} else {
				t = 0;
				m = f1.hash(key);
			}
			
			
		}
		
		rehash();
		put(key,value);
		return null;
	}
	
//	public V put(K key, V value) {
//		Entry<K,V> x = new Entry<K,V>(hash(key.hashCode()),key,value);
//		Entry<K,V> e = entries[0][f1.hash(key)];
//		if (e!=null && key.equals(e.getKey())){
//			e.setValue(value);
//			return value;
//		}
//		e = entries[1][f2.hash(key)];
//		if (e!=null && key.equals(e.getKey())){
//			e.setValue(value);
//			return value;
//		}
//		int t = 0;
//		int m = f1.hash(key);
//		for(int i = 0; i<maxCycle; ++i){
//			e = entries[t][m];
//			if (e==null) {
//				entries[t][m] = x;
//				++size;
//				resize();
//				return value;
//			}
//			
//			entries[t][m] = x;
//			x = e;
//			
//			if (t == 0){
//				t = 1;
//				m = f2.hash(x.getKey());
//			} else {
//				t = 0;
//				m = f1.hash(x.getKey()ey);
//			}
//			
//			
//		}

	@Override
	public V get(K key) {
		Entry<K,V> e;
		
		e = entries[0][f1.hash(key)];
		if(e!=null && key.equals(e.getKey()))
			return e.getValue();
		
		e = entries[1][f2.hash(key)];
		if(e!=null && key.equals(e.getKey()))
			return e.getValue();
		
		return null;
	}

	@Override
	public V remove(K key) {
		Entry<K,V> e;
		V value;
		e = entries[0][f1.hash(key)];
		if(e!=null && key.equals(e.getKey())){
			value = e.getValue();
			entries[0][f1.hash(key)] = null;
			--size;
			return value;
		}
		e = entries[1][f2.hash(key)];
		if(e!=null && key.equals(e.getKey())){
			value = e.getValue();
			entries[1][f2.hash(key)] = null;
			--size;
			return value;
		}
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		Entry<K,V> e;
		e = entries[0][f1.hash(key)];
		
		if(e!=null && key.equals(e.getKey()))
			return true;
		
		e = entries[1][f2.hash(key)];
		return e!=null && key.equals(e.getKey());
	}

	@Override
	public boolean containsValue(V value) {
		Entry<K,V> e;
		for(int i = 0;i<2;++i){
			for(int j = 0;j<capacity;++j){
				e = entries[i][j];
				if(e!=null && value.equals(e.getValue()))
					return true;
			}
		}
		return false;
	}

	@Override
	protected void resize() {
		if(checkLoad())
			return;
		rehash();
	}
	public void rehash() {
		Entry<K,V>[][] tempEntries = entries;
		
		int tempCapacity = capacity;

		while(!checkLoad()){
			capacity = size < minThreshold ? capacity/2 : capacity*2;
			minThreshold = (int) (minLoad*capacity);
			maxThreshold = (int) (maxLoad*capacity);
		}
		maxCycle = (int) (6*Math.log(capacity));
		
		entries = new Entry[2][capacity];
		f1 = new HashFunction(capacity);
		f2 = new HashFunction(capacity);

		for(int i = 0;i<2;i++){
			for(int j = 0;j<tempCapacity;j++){
				Entry<K,V> e = tempEntries[i][j];
				if(e!=null){
					put(e.getKey(),e.getValue());
				}
			}
		}
	}
	

	public CuckooHash(){
		this(DEFAULT_CAPACITY);
	}	
	public CuckooHash(int capacity){
		capacity = nearestPowerOfTwo(capacity);
		this.capacity = capacity;
		
		minLoad = 3.0f / 8.0f; // 3/16
		maxLoad = 3.0f / 2.0f; // 3/4
		minThreshold = (int) (minLoad*capacity);
		maxThreshold = (int) (maxLoad*capacity);
		
		maxCycle = (int) (6*Math.log(capacity));
		
		entries = new Entry[2][capacity];

		f1 = new HashFunction(capacity);
		f2 = new HashFunction(capacity);
	}

	public void print(){
		System.out.println("START");
		for(int i = 0;i<capacity;++i){
			if(entries[0][i]!=null){
				System.out.print("left: " + entries[0][i].getKey() + " " + entries[0][i].getValue());
			}
			if(entries[1][i]!=null){
				System.out.print("right: " + entries[1][i].getKey() + " "  + entries[1][i].getValue());
			}
			System.out.println();
		}
		System.out.println("END");
	}
	
	@Override
	public Object copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
