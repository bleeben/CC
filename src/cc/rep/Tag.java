package cc.rep;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable{
	private String text;
	
	public Tag(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}

	public boolean matchesTag(Tag tag){
		return text.startsWith(tag.getText());
	}

	@Override
	public String toString() {
		return "@"+text;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(text);
	}
	
	public Tag(Parcel in){
		// the parcel is read FIFO
		this.text= in.readString();
	}

	// used to regenerate object
	public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>(){
		public Tag createFromParcel(Parcel in){
			return new Tag(in);
		}
		
		public Tag[] newArray(int size){
			return new Tag[size];
		}
	};
	
}
