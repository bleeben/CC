package cc.rep;

import android.os.Parcel;
import android.os.Parcelable;

public class Collection implements Parcelable {
	private String name;
	private long id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection(Parcel in){
		// the parcel is read FIFO
		this.name = in.readString();
		this.id = in.readLong();
	}
	
	public Collection(){
		
	}
	
	public long getID(){
		return id;
	}
	
	public void setID(long id){
		this.id = id;
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
		dest.writeLong(id);
	}
	
	// used to regenerate object
	public static final Parcelable.Creator<Collection> CREATOR = new Parcelable.Creator<Collection>(){
		public Collection createFromParcel(Parcel in){
			return new Collection(in);
		}
		
		public Collection[] newArray(int size){
			return new Collection[size];
		}
	};
	
	

}
