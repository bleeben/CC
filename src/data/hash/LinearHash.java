package data.hash;

import java.lang.reflect.Array;

public class LinearHash<K,V> extends SkeletonHashMap<K, V>{

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

	public LinearHash(Class<K> c,Class<V> v) {
		init(c,v);
	}
}