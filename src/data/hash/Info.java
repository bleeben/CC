package data.hash;

public class Info {
	public float load;
	public int size;
	public boolean intOrString; // true if int, false if string
	public float prefLoad;
	public Info(){}
	public Info(float load, float prefLoad, int size, boolean isInt){
		this.load=load;
		this.prefLoad=prefLoad;
		this.size=size;
		this.intOrString=isInt;
	}
	
	public Info(float load, float prefLoad, int size){
		this.load=load;
		this.prefLoad=prefLoad;
		this.size=size;
		this.intOrString=false;
	}
}
