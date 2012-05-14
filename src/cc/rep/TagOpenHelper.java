package cc.rep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TagOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "database.db";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TEXT = "text";
	
	private static final int DATABASE_VERSION = 1;
	public static final String ITEM_TABLE_NAME = "tags";
    private static final String ITEM_TABLE_CREATE =
            "CREATE TABLE " + ITEM_TABLE_NAME + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TEXT + " TEXT not null );";

	public TagOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public TagOpenHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ITEM_TABLE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(CollectionOpenHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE_NAME);
		onCreate(db);
	}

}
