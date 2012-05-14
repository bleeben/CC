package contentprovider;

import cc.rep.CollectionOpenHelper;
import cc.rep.ItemOpenHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MainContentProvider extends ContentProvider {
	private CollectionOpenHelper collectionDB;
	private ItemOpenHelper itemDB;

	public static final int C = 100;
	public static final int C_ID = 110;

	public static final int I = 200;
	public static final int I_ID = 220;
	
	public static final int T = 300;
	public static final int T_ID = 330;

	private static final String AUTHORITY = "com.contentprovider.cc.maincontentprovider";

	private static final String COLLECTION_BASE_PATH = CollectionOpenHelper.COLLECTION_TABLE_NAME;
	public static final Uri CONTENT_URI_C = Uri.parse("content://" + AUTHORITY
			+ "/" + COLLECTION_BASE_PATH);

	private static final String ITEM_BASE_PATH = ItemOpenHelper.ITEM_TABLE_NAME;
	public static final Uri CONTENT_URI_I = Uri.parse("content://" + AUTHORITY
			+ "/" + ITEM_BASE_PATH);

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, COLLECTION_BASE_PATH, C);
		sURIMatcher.addURI(AUTHORITY, COLLECTION_BASE_PATH + "/#", C_ID);

		sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH, I);
		sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH + "/#", I_ID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase sqlDB;
		int rowsAffected = 0;
		switch (sURIMatcher.match(uri)){
		case C:
			sqlDB = collectionDB.getWritableDatabase();
			rowsAffected = sqlDB.delete(CollectionOpenHelper.COLLECTION_TABLE_NAME, selection, selectionArgs);
			break;
		case C_ID:
			String id = uri.getLastPathSegment();
			sqlDB = collectionDB.getWritableDatabase();
			if (TextUtils.isEmpty(selection)){
				rowsAffected = sqlDB.delete(CollectionOpenHelper.COLLECTION_TABLE_NAME, CollectionOpenHelper.COLUMN_ID + " = " + id, null);
			} else {
				rowsAffected = sqlDB.delete(CollectionOpenHelper.COLLECTION_TABLE_NAME, selection + " and " + CollectionOpenHelper.COLUMN_ID + " = " + id, null);
			}
			break;
		case I:
			sqlDB = itemDB.getWritableDatabase();
			rowsAffected = sqlDB.delete(ItemOpenHelper.ITEM_TABLE_NAME, selection, selectionArgs);
			break;
		case I_ID:
			String id2 = uri.getLastPathSegment();
			sqlDB = itemDB.getWritableDatabase();
			if (TextUtils.isEmpty(selection)){
				rowsAffected = sqlDB.delete(ItemOpenHelper.ITEM_TABLE_NAME, ItemOpenHelper.COLUMN_ID + " = " + id2, null);
			} else {
				rowsAffected = sqlDB.delete(ItemOpenHelper.ITEM_TABLE_NAME, selection + " and " + ItemOpenHelper.COLUMN_ID + " = " + id2, null);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown uri: " + uri.toString());
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsAffected;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		String single = "vnd.android.cursor.item/";
		String mult = "vnd.android.cursor.dir/";
		switch (sURIMatcher.match(uri)) {
		case C:
			return mult + "vnd.cc.collections";
		case C_ID:
			return single + "vnd.cc.collection";
		case I:
			return mult + "vnd.cc.items";
		case I_ID:
			return single + "vnd.cc.item";
		default:
			throw new IllegalArgumentException("Unknown uri: " + uri.toString());
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase sqlDB;
		long rowID;
		switch (sURIMatcher.match(uri)) {
		case C:
		case C_ID:
			sqlDB = collectionDB.getWritableDatabase();
			rowID = sqlDB.insert(CollectionOpenHelper.COLLECTION_TABLE_NAME, null, values);
			if (rowID > 0){
				Uri _uri = ContentUris.withAppendedId(CONTENT_URI_C, rowID);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
			break;
		case I:
		case I_ID:
			sqlDB = itemDB.getWritableDatabase();
			rowID = sqlDB.insert(ItemOpenHelper.ITEM_TABLE_NAME, null, values);
			if (rowID > 0){
				Uri _uri = ContentUris.withAppendedId(CONTENT_URI_I, rowID);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown uri: " + uri.toString());
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		collectionDB = new CollectionOpenHelper(getContext());
		itemDB = new ItemOpenHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qB = new SQLiteQueryBuilder();
		Cursor cursor;
		switch (sURIMatcher.match(uri)) {
		case C:
			qB.setTables(CollectionOpenHelper.COLLECTION_TABLE_NAME);
			cursor = qB.query(collectionDB.getReadableDatabase(), projection,
					selection, selectionArgs, null, null, sortOrder);
			break;
		case C_ID:
			qB.setTables(CollectionOpenHelper.COLLECTION_TABLE_NAME);
			qB.appendWhere(CollectionOpenHelper.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			cursor = qB.query(collectionDB.getReadableDatabase(), projection,
					selection, selectionArgs, null, null, sortOrder);
			break;
		case I:
			qB.setTables(ItemOpenHelper.ITEM_TABLE_NAME);
			cursor = qB.query(itemDB.getReadableDatabase(), projection,
					selection, selectionArgs, null, null, sortOrder);
			break;
		case I_ID:
			qB.setTables(ItemOpenHelper.ITEM_TABLE_NAME);
			qB.appendWhere(ItemOpenHelper.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			cursor = qB.query(itemDB.getReadableDatabase(), projection,
					selection, selectionArgs, null, null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri.toString());
		}
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase sqlDB;
		int rowsAffected = 0;
		switch (sURIMatcher.match(uri)) {
		case C:
			sqlDB = collectionDB.getWritableDatabase();
			rowsAffected = sqlDB.update(CollectionOpenHelper.COLLECTION_TABLE_NAME, values, selection, selectionArgs);
			break;
		case C_ID:
			sqlDB = collectionDB.getWritableDatabase();
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)){
				rowsAffected = sqlDB.update(CollectionOpenHelper.COLLECTION_TABLE_NAME, values, CollectionOpenHelper.COLUMN_ID + " = " + id, null);
			} else {
				rowsAffected = sqlDB.update(CollectionOpenHelper.COLLECTION_TABLE_NAME, values, selection + " and " + CollectionOpenHelper.COLUMN_ID + " = " + id, null);
			}
			break;
		case I:
			sqlDB = itemDB.getWritableDatabase();
			rowsAffected = sqlDB.update(ItemOpenHelper.ITEM_TABLE_NAME, values, selection, selectionArgs);
			break;
		case I_ID:
			sqlDB = itemDB.getWritableDatabase();
			String id2 = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)){
				rowsAffected = sqlDB.update(ItemOpenHelper.ITEM_TABLE_NAME, values, ItemOpenHelper.COLUMN_ID + " = " + id2, null);
			} else {
				rowsAffected = sqlDB.update(ItemOpenHelper.ITEM_TABLE_NAME, values, selection + " and " + ItemOpenHelper.COLUMN_ID + " = " + id2, null);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown uri: " + uri.toString());
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsAffected;
	}

}
