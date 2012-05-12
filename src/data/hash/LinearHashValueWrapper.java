package data.hash;

public class LinearHashValueWrapper<V> {
	private V value;
	private boolean del;
	
	public LinearHashValueWrapper(){
		value = null;
		del = false;
	}
	public LinearHashValueWrapper(V value, boolean del){
		this.value = value;
		this.del = del;
	}
	
	public V getValue(){
		return value;
	}
	
	public boolean deleted(){
		return del;
	}
	
	public void delete(){
		del = true;
	}
	
}
