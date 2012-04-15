package cc.rep;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
	private String name;
	private long id;
	
	public Item() {
		
	}
	
	public Item(String name){
		this.name = name;
	}

	public Item(Parcel in) {
		this.name = in.readString();
		this.id = in.readLong();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeLong(id);
		
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
