package data.hash;

public class Entry<K,V> {
	private int hash;
	private K key;
	private V value;
	
	public Entry(K key, V value){
		this.hash = key.hashCode();
		this.key = key;
		this.value = value;
	}

	
	public Entry(int hash, K key, V value){
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
	
	public boolean keyquals(K k){
		return hash == k.hashCode() && key == k;
	}

	public boolean equals(Entry<K,V> e){
		return e!=null && hash == e.getHash() && key == e.getKey();
	}
	public V getValue(){
		return value;
	}
	public V setValue(V value){
		return this.value = value;
	}
}
