package cc.rep;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Collection implements Parcelable, Storable {
	private String name;
	private long id;
	private List<Item> items = new ArrayList<Item>();
	private List<Sharer> sharers = new ArrayList<Sharer>();
	private String desc;
	private boolean isVisible = false;
	private Uri picUri;

	public boolean isPrivate() {
		return !isVisible;
	}

	public boolean isPublic() {
		return isVisible;
	}

	public void setPrivate(boolean p) {
		isVisible = !p;
	}

	public void setPublic(boolean p) {
		isVisible = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection(Parcel in) {
		// the parcel is read FIFO
		this.name = in.readString();
		this.id = in.readLong();
		this.desc = in.readString();
		this.isVisible = in.readByte() == 1;
		in.readTypedList(items, Item.CREATOR);
		in.readTypedList(sharers, Sharer.CREATOR);
		this.picUri = in.readParcelable(Uri.class.getClassLoader());
	}

	public Collection() {
	}

	public Collection(String name) {
		setName(name);
	}

	public long getID() {
		return id;
	}

	public void setID(long id) {
		this.id = id;
	}

	public void addItem(Item item) {
		items.add(item);
	}


	public void recentAdd(Item item) {
		items.add(0,item);
		if(items.size() > 10)
			items.remove(10);
	}

	public void addSharer(Sharer sharer) {
		sharers.add(sharer);
	}
	
	public void setSharer(Sharer sharer, int position) {
		sharers.set(position, sharer);
	}

	public boolean removeItem(Item item) {
		return items.remove(item);
	}

	public boolean removeSharer(Sharer sharer) {
		return sharers.remove(sharer);
	}

	public int size() {
		return items.size();
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Sharer> getSharers() {
		return sharers;
	}
	
	public void setSharers(List<Sharer> list){
		this.sharers = list;
	}

	public Item getItem(int location) {
		return items.get(location);
	}

	public void setItem(int location, Item item) {
		items.set(location, item);
	}

	public Sharer getSharer(int location) {
		return sharers.get(location);
	}

	// @Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	// @Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeLong(id);
		dest.writeString(desc);
		dest.writeByte((byte) (isVisible ? 1 : 0));
		dest.writeTypedList(items);
		dest.writeTypedList(sharers);
		dest.writeParcelable(picUri, 0);

	}

	public ArrayList<Item> getMatches(Tag filter) {
		ArrayList<Item> temp = new ArrayList<Item>();

		for (Item item : items) {
			if (item.matchesTag(filter)) {
				temp.add(item); // TODO - Don't copy, right? Want to have it so
								// that an edits you make edit the original.
			}
		}

		return temp;
	}

	public ArrayList<Item> getMatches(ArrayList<Tag> filters) {
		ArrayList<Item> temp = new ArrayList<Item>();

		for (Item item : items) {
			if (item.matchesTags(filters)) {
				temp.add(item); // TODO - Don't copy, right? Want to have it so
								// that an edits you make edit the original.
			}
		}

		return temp;
	}
	
	public boolean matches(Tag filter){
		for (Item item : items) {
			if (item.matchesTag(filter)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean matches(ArrayList<Tag> filters) {
		for (Item item : items) {
			if (item.matchesTags(filters)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Collection> getMatches(List<Collection> cs, Tag filter){
		List<Collection> collections = new ArrayList<Collection>();

		for (Collection c : cs) {
			if (c.matches(filter)){
				collections.add(c);
			}
		}
		return collections;
	}
	
	public static List<Collection> getMatches(List<Collection> cs, ArrayList<Tag> filters){
		List<Collection> collections = new ArrayList<Collection>();

		for (Collection c : cs) {
			if (c.matches(filters)){
				collections.add(c);
			}
		}
		return collections;
	}
	
	
	// used to regenerate object
	public static final Parcelable.Creator<Collection> CREATOR = new Parcelable.Creator<Collection>() {
		public Collection createFromParcel(Parcel in) {
			return new Collection(in);
		}

		public Collection[] newArray(int size) {
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
		this.desc = desc;
	}

	public void setPicUri(Uri uri) {
		this.picUri = uri;
	}

	public Uri getPicUri() {
		return picUri;
	}

	public ContentValues makeContentValues() {
		ContentValues v = new ContentValues();
		v.put(CollectionOpenHelper.COLUMN_ID, id);
		v.put(CollectionOpenHelper.COLUMN_DESC, desc);
		v.put(CollectionOpenHelper.COLUMN_NAME, name);
		if (picUri != null) {
			v.put(CollectionOpenHelper.COLUMN_PIC, picUri.toString());
		}
		return v;
	}

	public static Collection cursorToCollection(Cursor cursor) {
		Collection c = new Collection();
		c.setID(cursor.getLong(0));
		c.setName(cursor.getString(1));
		c.setDesc(cursor.getString(2));
		c.setPicUri(Uri.parse(cursor.getString(3)));
		return c;
	}

	public void setItems(List<Item> items2) {
		this.items = items2;
		
	}
}
