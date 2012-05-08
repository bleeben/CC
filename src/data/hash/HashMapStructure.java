package data.hash;

/*
 * DEPRECATED
 */

public interface HashMapStructure<K,V> {
public V put(K key, V value);
public V get(K key);
public V remove(K key);
public boolean containsKey(K key);
public boolean containsValue(V value);
}
