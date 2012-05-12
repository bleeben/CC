package cc.rep;

import android.os.Parcel;
import android.os.Parcelable;

public class Sharer implements Parcelable{
	//TODO
    private String name;
    private String email;
    private String permission;
    private Permission perms;
    
    public Sharer(String email) {
    	this.name = email;
        this.email = email;
        this.permission = "edit";
    }    
    public Sharer(String email, String permission) {
    	this.name = email;
        this.email = email;
        this.permission = permission;
    }
    public Sharer(String name, String email, String permission) {
    	this.name = name;
        this.email = email;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPermission() {
        return permission;
    }

    public void setName(String name) {
        this.name = name;
    }


	public Sharer(Parcel in) {
		this.name = in.readString();
		this.email = in.readString();
		this.permission = in.readString();
	}
	
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPermission(String permission) {
        this.permission = permission;
    }
    
	//@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(email);
		dest.writeString(permission);
	}
	
	// used to regenerate object
	public static final Parcelable.Creator<Sharer> CREATOR = new Parcelable.Creator<Sharer>(){
		public Sharer createFromParcel(Parcel in){
			return new Sharer(in);
		}
		
		public Sharer[] newArray(int size){
			return new Sharer[size];
		}
	};
}
