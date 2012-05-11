package cc.rep;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable, Storable {
	private String name;
	private long id;
	private Collection collection;
	
	public Item() {
		
	}
	
	public Item(String name){
		this.name = name;
	}

	public Item(Parcel in) {
		this.name = in.readString();
		this.setID(in.readLong());
		this.collection = in.readParcelable(Collection.class.getClassLoader());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeLong(getID());
		dest.writeParcelable(collection, 0);
		
	}
	
	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	
	public void setCollection(long id){
		// TODO: search through collections to find the id
		
	}

	public long getID() {
		return id;
	}

	public void setID(long id) {
		this.id = id;
	}

	// used to regenerate object
	public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>(){
		public Item createFromParcel(Parcel in){
			return new Item(in);
		}
		
		public Item[] newArray(int size){
			return new Item[size];
		}
	};
}
