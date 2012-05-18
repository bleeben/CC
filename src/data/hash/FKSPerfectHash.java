package data.hash;

public class FKSPerfectHash<K,V> extends SkeletonHashMap<K, V>{
	
	@Override
	public FKSPerfectHash<K,V> spawn(){
		return new FKSPerfectHash<K,V>();
		
	}
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

	@Override
	public Object copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
