package cc.rep;

import android.net.Uri;

public interface Storable{
	public String getName();
	public void setName(String name);
	public long getID();
	public void setID(long id);
	public String getDesc();
	public void setDesc(String desc);
	public void setPicUri(Uri uri);
	public Uri getPicUri();
}
