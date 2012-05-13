package data.hash;

public class Entry<K,V> {
	private int hash;
	private K key;
	private V value;
	
	public Entry(int hash, K key, V value, Entry e){
		if(e!=null){
			this.hash = e.getHash();
			this.key = (K) e.getKey();
			this.value = (V) e.getValue();
		}
		this.hash = hash;
		this.key = key;
		this.value = value;
	}
	
	public int getHash(){
		return hash;
	}
	
	public K getKey(){
		return key;
	}
	
	public V getValue(){
		return value;
	}
}
