package cc.rep;

public class Collection {
	private String name;
	private long id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection(String name){
		this.name = name;
	}
	
	public Collection(){
		
	}
	
	public long getID(){
		return id;
	}
	
	public void setID(long id){
		this.id = id;
	}

}
