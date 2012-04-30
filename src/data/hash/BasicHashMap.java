package data.hash;

public abstract class BasicHashMap<K, V> implements HashMapStructure<K, V> {
	protected int capacity;
	protected int size;
	protected K[] keys;
	protected V[] values;
}
