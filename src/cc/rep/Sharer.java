package cc.rep;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Sharer implements Parcelable,Storable{
	//TODO
    private String name="";
    private String email="";
    //private String permission;
    private Permission perms=Permission.CAN_VIEW;
    
    public Sharer(String email) {
    	this.name = email;
        this.email = email;
        //this.permission = "edit";
        this.perms=Permission.CAN_EDIT;
    }    
    public Sharer(String email, Permission permission) {
    	this.name = email;
        this.email = email;
        this.perms = permission;
    }
    public Sharer(String name, String email, Permission permission) {
    	this.name = name;
        this.email = email;
        this.perms = permission;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Permission getPermission() {
        return perms;
    }

    public void setName(String name) {
        this.name = name;
    }


	public Sharer(Parcel in) {
		this.name = in.readString();
		this.email = in.readString();
		this.perms = Permission.values()[in.readInt()];
	}
	
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPermission(Permission permission) {
        this.perms = permission;
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
		dest.writeInt(perms.ordinal());
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

	@Override
	public long getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setID(long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getDesc() {
		return getEmail();
	}
	@Override
	public void setDesc(String desc) {
		setEmail(desc);
	}
	@Override
	public void setPicUri(Uri uri) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Uri getPicUri() {
		// TODO Auto-generated method stub
		return null;
	}
}
