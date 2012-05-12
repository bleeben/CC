package data.hash;

import java.lang.reflect.Array;

public class LinearHash<K,V> extends SkeletonHashMap<K, V>{
	private boolean[] deleted;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void init(Class<K> c,Class<V> v) {
		// TODO Auto-generated method stub
		keys = (K[]) Array.newInstance(c, capacity);
		values = (V[]) Array.newInstance(v, capacity);
		deleted = (boolean[]) Array.newInstance(Boolean.class, capacity);
		
	}
	
	@Override
	public V put(K key, V value) {
		int i = hash(key.hashCode());
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
		int i = hash(key.hashCode());
		while(keys[i]!=null){
			if(t[i]!=nil)
		}
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