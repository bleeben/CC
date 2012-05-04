package data.hash;

public abstract class SkeletonHashMap<K, V> {
	protected int capacity;
	protected int size;
	protected K[] keys;
	protected V[] values;
	protected float minLoad; // 1/3
	protected float maxLoad; // 2/3
	protected float prefLoad; // 1/2?

	// a good load will be between 1/3 and 2/3? subject to change
	protected boolean checkLoad() {
		return load() < minLoad || load() > maxLoad;
	}

	public float load() {
		return 1.0f * size / capacity;
	}

	protected abstract void resize();

	public abstract V put(K key, V value);

	public abstract V get(K key);

	public abstract V remove(K key);

	public abstract boolean containsKey(K key);

	public abstract boolean containsValue(V value);

	/**
	 * Applies a supplemental hash function to a given hashCode, which defends
	 * against poor quality hash functions. This is critical because HashMap
	 * uses power-of-two length hash tables, that otherwise encounter collisions
	 * for hashCodes that do not differ in lower bits. Note: Null keys always
	 * map to hash 0, thus index 0.
	 */
	static int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	static int indexFor(int h, int length) {
		return h & (length - 1);
	}
}
