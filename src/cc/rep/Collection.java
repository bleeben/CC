package cc.rep;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Collection implements Parcelable, Storable{
	private String name;
	private long id;
	private List<Item> items = new ArrayList<Item>();
	private List<Sharer> sharers = new ArrayList<Sharer>();
	private String desc;
	private boolean isVisible=false;
	
	public boolean isPrivate(){
		return !isVisible;
	}
	public boolean isPublic(){
		return isVisible;
	}
	public void setPrivate(boolean p){
		isVisible=!p;
	}
	public void setPublic(boolean p){
		isVisible=p;
	}
	
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
		in.readTypedList(items, Item.CREATOR);
		in.readTypedList(sharers, Sharer.CREATOR);
	}
	
	public Collection(){
		items = new ArrayList<Item>();
	}
	
	public long getID(){
		return id;
	}
	
	public void setID(long id){
		this.id = id;
	}

	public void addItem(Item item){
		items.add(item);
	}
	public void addSharer(Sharer sharer){
		sharers.add(sharer);
	}
	public boolean removeItem(Item item){
		return items.remove(item);
	}
	public boolean removeSharer(Sharer sharer){
		return sharers.remove(sharer);
	}
	
	public int size(){
		return items.size();
	}

	public List<Item> getItems(){
		return items;
	}
	public List<Sharer> getSharers(){
		return sharers;
	}
	
	public Item getItem(int location){
		return items.get(location);
	}
	public void setItem(int location, Item item){
		items.set(location, item);
	}
	public Sharer getSharer(int location){
		return sharers.get(location);
	}
	
	//@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeLong(id);
		dest.writeTypedList(items);
		dest.writeTypedList(sharers);
	}
	
	public ArrayList<Item> getMatches(Tag filter) {
		ArrayList<Item> temp = new ArrayList<Item>();
		
		for(Item item:items){
			if(item.matchesTag(filter)){
				temp.add(item); //TODO - Don't copy, right? Want to have it so that an edits you make edit the original.
			}
		}
		
		return temp;
	}	
	
	public ArrayList<Item> getMatches(ArrayList<Tag> filters) {
		ArrayList<Item> temp = new ArrayList<Item>();
		
		for(Item item:items){
			if(item.matchesTags(filters)){
				temp.add(item); //TODO - Don't copy, right? Want to have it so that an edits you make edit the original.
			}
		}
		
		return temp;
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

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return desc;
	}

	@Override
	public void setDesc(String desc) {
		// TODO Auto-generated method stub
		this.desc=desc;
	}
	
	

}
