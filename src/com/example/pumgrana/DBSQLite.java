package com.example.pumgrana;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DBSQLite extends SQLiteOpenHelper {
	private static final String TABLE_DATA = "table_data";
	private static final String COL_ID = "ID";
	private static final String COL_TITLE = "Title";
	private static final String COL_TEXT = "Text";

	private static final String DROP_TABLE = "DROP TABLE " + TABLE_DATA + ";";
	private static final String CREATE_DB = "CREATE TABLE" + TABLE_DATA + " (" + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TITLE + " TEXT IS NOT NULL, " + COL_TEXT + " TEXT IS NOT NULL);";

	public DBSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_DB);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}
}
