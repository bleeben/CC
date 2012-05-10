package cc.rep;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ItemsDataSource {
	private SQLiteDatabase database;
	private ItemOpenHelper dbHelper;
	private String[] allColumns = {ItemOpenHelper.COLUMN_ID, ItemOpenHelper.COLUMN_NAME};
	
	public ItemsDataSource(Context context){
		dbHelper = new ItemOpenHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public Item createItem(String name){
		ContentValues values = new ContentValues();
		values.put(ItemOpenHelper.COLUMN_NAME, name);
		long insertId = database.insert(ItemOpenHelper.ITEM_TABLE_NAME, null, values);
		Cursor cursor = database.query(ItemOpenHelper.ITEM_TABLE_NAME, allColumns, ItemOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Item newItem = cursorToItem(cursor);
		cursor.close();
		return newItem;
	}
	
	public void deleteItem(Item item){
		long id = item.getID();
		System.out.println("Item deleted with id: " + id);
		database.delete(ItemOpenHelper.ITEM_TABLE_NAME, ItemOpenHelper.COLUMN_ID + " " + id, null);
	}
	
	public List<Item> getAllItems(){
		List<Item> items = new ArrayList<Item>();
		
		Cursor cursor = database.query(ItemOpenHelper.ITEM_TABLE_NAME, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Item c = cursorToItem(cursor);
			items.add(c);
			cursor.moveToNext();
		}
		cursor.close();
		return items;
	}
	
	public List<Item> getItemsOfCollection(long collection_id){
		List<Item> items = new ArrayList<Item>();
		
		Cursor cursor = database.query(ItemOpenHelper.ITEM_TABLE_NAME, allColumns, ItemOpenHelper.COLUMN_COLLECTION + " = " + collection_id, null, null, null, null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			Item i = cursorToItem(cursor);
			items.add(i);
			cursor.moveToNext();
		}
		cursor.close();
		return items;
	}
	
	private Item cursorToItem(Cursor cursor){
		Item i = new Item();
		i.setID(cursor.getLong(0));
		i.setName(cursor.getString(1));
		i.setCollection(cursor.getLong(2));
		return i;
	}
	
}
