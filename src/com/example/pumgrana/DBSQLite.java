package com.example.pumgrana;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


// TODO: Auto-generated Javadoc
/**
 * SQLite manager.
 */
public class DBSQLite extends SQLiteOpenHelper {
	
	/** The Constant TABLE_DATA. */
	private static final String TABLE_DATA = "table_data";
	
	/** The Constant COL_ID. */
	private static final String COL_ID = "ID";
	
	/** The Constant COL_DATA_ID. */
	private static final String COL_DATA_ID = "Data";
	
	/** The Constant COL_TITLE. */
	private static final String COL_TITLE = "Title";
	
	/** The Constant COL_TEXT. */
	private static final String COL_TEXT = "Text";
	
	/** The Constant TABLE_TAG. */
	private static final String TABLE_TAG = "table_tag";
	
	/** The Constant COL_TAG_ID. */
	private static final String COL_TAG_ID = "Tag";
	
	/** The Constant COL_NAME. */
	private static final String COL_NAME = "Name";
	
	/** The Constant TABLE_DATA_TAG. */
	private static final String TABLE_DATA_TAG = "table_data_tag";
	

	/** The Constant DROP_TABLE. */
	private static final String DROP_TABLE = "DROP TABLE " + TABLE_DATA + ";";
	
	/** The Constant CREATE_DATA. */
	private static final String CREATE_DATA = "CREATE TABLE " + TABLE_DATA + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DATA_ID + " TEXT NOT NULL UNIQUE, " + COL_TITLE + " TEXT NOT NULL, " + COL_TEXT + " TEXT NOT NULL);";
	
	/** The Constant CREATE_TAG. */
	private static final String CREATE_TAG = "CREATE TABLE " + TABLE_TAG + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TAG_ID + " TEXT NOT NULL UNIQUE, " + COL_NAME + " TEXT NOT NULL);";
	
	/** The Constant CREATE_DATA_TAG. */
	private static final String CREATE_DATA_TAG = "CREATE TABLE " + TABLE_DATA_TAG + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DATA_ID + " TEXT NOT NULL, " + COL_TAG_ID + " TEXT NOT NULL, UNIQUE (" + COL_DATA_ID + "," + COL_TAG_ID + "));";

	/**
	 * Instantiates a new DBSQ lite.
	 *
	 * @param context the context
	 * @param name the name
	 * @param factory the factory
	 * @param version the version
	 */
	public DBSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_DATA);
		db.execSQL(CREATE_TAG);
		db.execSQL(CREATE_DATA_TAG);
	}
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}
}
