package cc.rep;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable{
	private String name;
	
	public Item() {
		
	}
	
	public Item(String name){
		this.name = name;
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
		// TODO Auto-generated method stub
		dest.writeString(name);
	}
	
}
