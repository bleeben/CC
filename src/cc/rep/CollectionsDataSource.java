package cc.rep;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CollectionsDataSource {
	private SQLiteDatabase database;
	private CollectionOpenHelper dbHelper;
	private String[] allColumns = {CollectionOpenHelper.COLUMN_ID, CollectionOpenHelper.COLUMN_NAME};
	
	
	public CollectionsDataSource(Context context){
		dbHelper = new CollectionOpenHelper(context);
	}
	
	public void open() throws SQLEXception{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public Collection createCollection(String name){
		ContentValues values = new ContentValues();
		values.put(CollectionOpenHelper.COLUMN_NAME, name);
		long insertId = database.insert(CollectionOpenHelper.COLLECTION_TABLE_NAME, null, values);
		Cursor cursor = database.query(CollectionOpenHelper.COLLECTION_TABLE_NAME, allColumns, CollectionOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Collection newCol = cursorToCollection(cursor);
		cursor.close();
		return newCol;
		
	}
	
	public void deleteCollection(Collection col){
		long id = col.getID();
		System.out.println("Collection deleted with id: " + id);
		database.delete(CollectionOpenHelper.COLLECTION_TABLE_NAME, CollectionOpenHelper.COLUMN_ID + " " + id, null);
	}
	
	public List<Collection> getAllCollections(){
		List<Collection> collections = new ArrayList<Collection>();
		
		Cursor cursor = database.query(CollectionOpenHelper.COLLECTION_TABLE_NAME, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Collection c = cursorToCollection(cursor);
			collections.add(c);
			cursor.moveToNext();
		}
		cursor.close();
		return collections;
	}
	
	private Collection cursorToCollection(Cursor cursor){
		Collection c = new Collection();
		c.setID(cursor.getLong(0));
		c.setName(cursor.getString(1));
		return c;
	}
}
