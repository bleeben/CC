package data.hash;

public abstract class SkeletonHashMap<K, V> {
	
	protected int size;
	protected static int DEFAULT_CAPACITY = 16;
	protected int capacity = 16;
	protected int minCapacity = 16;
	protected int minThreshold;
	protected int maxThreshold;
	
	protected float minLoad; // 1/3
	protected float maxLoad; // .75
	protected float prefLoad; // 1/2?
	
	/***
	 * Returns the size.
	 * @return the size.
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Returns the load (size/capacity).
	 * @return the load.
	 */
	public float getLoad(){
		return 1.0f*size/capacity;
	}
	
	/**
	 * Returns the preferred load.
	 * @return the preferred load.
	 */
	public float getPrefLoad(){
		return prefLoad;
	}

	/**
	 * Returns true if <tt>(size >= minThreshold || size <= minCapacity) && size <= maxThreshold;</tt>.
	 * @returns <tt>true</tt> if the current loadFactor is valid.
	 */
	protected boolean checkLoad() {
		return size <= maxThreshold;
		//return (size >= minThreshold || size <= minCapacity) && size <= maxThreshold;
	}
	
	/**
	 * Assigns a given value to a given key in the HashMap. Returns value on success.
	 * @param key
	 * @param value
	 * @return value?...
	 */
	public abstract V put(K key, V value);

	/**
	 * Returns the value associated with the given key if it exists in the HashMap.
	 * Otherwise, returns null.
	 * @param key - the key to check for.
	 * @return value - the value associated with the key.
	 */
	public abstract V get(K key);
	
	/**
	 * Removes the given key from the HashMap if it exists and returns the value
	 * that was associated with it. If the key was in the table, it returns the
	 * value that was associated with the given key. Otherwise, it returns null.
	 * @param key - the key to remove
	 * @return value - the value associated with the key.
	 */
	public abstract V remove(K key);

	/**
	 * Returns true if the HashMap contains the given key.
	 * @param key - the key to check for 
	 * @return <tt>true</tt> if the provided key is present in the HashMap.
	 */
	public abstract boolean containsKey(K key);
	
	/**
	 * Returns true if the HashMap contains the given value. Must iterate
	 * through all key/value pairs, so it is really slow.
	 * @param value - the key to check for 
	 * @return <tt>true</tt> if the provided value is present in the HashMap.
	 */
	public abstract boolean containsValue(V value);
	
	/**
	 * Resizes the hash table if it is either too large or two small. Depending
	 * on the specific type of HashTable, this will be implemented in different
	 * ways - usually, by creating a new HashTable and rehashing everything.
	 */
	protected abstract void resize();

	 /**
	  * Applies a supplemental hash function to a given hashCode, which
	  * defends against poor quality hash functions.  This is critical
	  * because HashMap uses power-of-two length hash tables, that
	  * otherwise encounter collisions for hashCodes that do not differ
	  * in lower bits. Note: Null keys always map to hash 0, thus index 0.
	  * 
	  * Stolen shamelessly from Java (okay maybe with a little bit of shame).
	  */
	static int hash(int h) {
		 // This function ensures that hashCodes that differ only by constant 
		 // multiples at each bit position have a bounded number of collisions 
		 // (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	/** 
	 * This function takes an input capacity and rounds it up to the nearest power
	 * of two using bit twiddling. It's completely unnecessary but I think bit
	 * twiddling is really cool. 
	 * 
	 * http://graphics.stanford.edu/~seander/bithacks.html
	 * 
	 * @param capacity - input capacity
	 * @return capacity - rounded up to the nearest power of two
	 */
	public int nearestPowerOfTwo(int capacity){
		capacity--; 
		capacity |= capacity >> 1;
		capacity |= capacity >> 2;
		capacity |= capacity >> 4;
		capacity |= capacity >> 8;
		capacity |= capacity >> 16;
		capacity++;
		
		return capacity;
	}
	
	public abstract SkeletonHashMap<K,V> spawn();
}
