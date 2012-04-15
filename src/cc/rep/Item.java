package cc.rep;

import android.os.Parcel;
import android.os.Parcelable;

<<<<<<< HEAD
public class Item implements Parcelable {
=======
public class Item implements Parcelable{
>>>>>>> 26b4faa94b913486614f808bff119872998c2be6
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
<<<<<<< HEAD
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
=======
		// TODO Auto-generated method stub
		dest.writeString(name);
	}
>>>>>>> 26b4faa94b913486614f808bff119872998c2be6
	
}
