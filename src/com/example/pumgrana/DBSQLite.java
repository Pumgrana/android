package com.example.pumgrana;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DBSQLite extends SQLiteOpenHelper {
	private static final String TABLE_DATA = "table_data";
	private static final String COL_ID = "ID";
	private static final String COL_DATA_ID = "Data";
	private static final String COL_TITLE = "Title";
	private static final String COL_TEXT = "Text";
	
	private static final String TABLE_TAG = "table_tag";
	private static final String COL_TAG_ID = "Tag";
	private static final String COL_NAME = "Name";
	
	private static final String TABLE_DATA_TAG = "table_data_tag";
	

	private static final String DROP_TABLE = "DROP TABLE " + TABLE_DATA + ";";
	private static final String CREATE_DATA = "CREATE TABLE " + TABLE_DATA + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DATA_ID + " TEXT NOT NULL UNIQUE, " + COL_TITLE + " TEXT NOT NULL, " + COL_TEXT + " TEXT NOT NULL);";
	private static final String CREATE_TAG = "CREATE TABLE " + TABLE_TAG + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TAG_ID + " TEXT NOT NULL UNIQUE, " + COL_NAME + " TEXT NOT NULL);";
	private static final String CREATE_DATA_TAG = "CREATE TABLE " + TABLE_DATA_TAG + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DATA_ID + " TEXT NOT NULL, " + COL_TAG_ID + " TEXT NOT NULL, UNIQUE (" + COL_DATA_ID + "," + COL_TAG_ID + "));";

	public DBSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_DATA);
		db.execSQL(CREATE_TAG);
		db.execSQL(CREATE_DATA_TAG);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}
}
