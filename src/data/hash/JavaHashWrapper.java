package data.hash;
import java.util.HashMap;

public class JavaHashWrapper<K,V> extends SkeletonHashMap<K, V>{
	private HashMap hashMap;
	public JavaHashWrapper(Class<K> c,Class<V> v){
		hashMap = new HashMap<K,V>();
	}
	@Override
	public V put(K key, V value) {
		hashMap.put(key, value);
		return null;
	}

	@Override
	public V get(K key){
		return (V) hashMap.get(key);
	}

	@Override
	public V remove(K key) {
		return (V) hashMap.remove(key);
	}

	@Override
	public boolean containsKey(K key) {
		return hashMap.containsKey(key);
	}

	@Override
	public boolean containsValue(V value) {
		return hashMap.containsValue(value);
	}

	@Override
	protected void resize() {
		return;
		//not needed;
	}
	@Override
	public Object copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
