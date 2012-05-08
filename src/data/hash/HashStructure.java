package data.hash;

public interface HashStructure<K,V> {
public void put(K key, V value);
public void get(K key);
public void remove(K key);
public void query(K key);
}
