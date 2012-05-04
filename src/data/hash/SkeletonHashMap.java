package data.hash;

public abstract class SkeletonHashMap<K, V> implements HashMapStructure<K, V> {
	protected int capacity;
	protected int size;
	protected K[] keys;
	protected V[] values;
	protected float minLoad; // 1/3
	protected float maxLoad; // 2/3
	protected float prefLoad; // 1/2?
	// a good load will be between 1/3 and 2/3? subject to change
	protected boolean checkLoad(){
		return load()<minLoad || load()>maxLoad;
	}
	public float load(){
		return 1.0f*size/capacity;
	}
	protected abstract void resize();
}
