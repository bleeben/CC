package cc.rep;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable, Storable {
	private String name;
	private long id;
	private Collection collection;
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	private String desc;
	private Uri picUri;

	public Item() {
	}

	public Item(String name) {
		this.name = name;
	}

	public Item(Parcel in) {
		this.name = in.readString();
		this.setID(in.readLong());
		this.desc = in.readString();
		this.collection = in.readParcelable(Collection.class.getClassLoader());
		in.readTypedList(tags, Tag.CREATOR);
		in.readParcelable(Uri.class.getClassLoader());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// @Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	// @Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeLong(getID());
		dest.writeString(desc);
		dest.writeParcelable(collection, 0);
		dest.writeTypedList(tags);
		dest.writeParcelable(picUri, 0);
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public void setCollection(long id) {
		// TODO: search through collections to find the id

	}

	public long getID() {
		return id;
	}

	public void setID(long id) {
		this.id = id;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public void addTag(String tag) {
		tags.add(new Tag(tag));
	}

	public boolean removeTag(Tag tag) {
		return tags.remove(tag);
	}

	public boolean removeTag(String tag) {
		return tags.remove(new Tag(tag));
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	public boolean matchesTag(Tag filter) {
		for (Tag tag : tags) {
			if (tag.matchesTag(filter)) {
				return true;
			}
		}
		return false;
	}

	public boolean matchesTags(ArrayList<Tag> filters) {
		for (Tag filter : filters) {
			if (!matchesTag(filter)) {
				return false;
			}
		}
		return true;
	}

	// used to regenerate object
	public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}

		public Item[] newArray(int size) {
			return new Item[size];
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

	public ContentValues makeContentValues() {
		ContentValues v = new ContentValues();
		v.put(ItemOpenHelper.COLUMN_ID, id);
		v.put(ItemOpenHelper.COLUMN_COLLECTION, collection.getID());
		v.put(ItemOpenHelper.COLUMN_NAME, name);
		if (picUri != null) {
			v.put(ItemOpenHelper.COLUMN_PIC, picUri.toString());
		}
		return v;

	}

	public void setPicUri(Uri uri) {
		this.picUri = uri;
	}

	public Uri getPicUri() {
		return picUri;
	}

	public static Item cursorToItem(Cursor cursor) {
		Item i = new Item();
		i.setID(cursor.getLong(0));
		i.setName(cursor.getString(1));
		i.setPicUri(Uri.parse(cursor.getString(3)));
		return i;
	}

	public static Item cursorToIteFromCol(Cursor cursor, Collection collection) {
		Item i = new Item();
		i.setID(cursor.getLong(0));
		i.setName(cursor.getString(1));
		i.setPicUri(Uri.parse(cursor.getString(3)));
		i.setCollection(collection);
		return i;
	}
}
