package com.example.pumgrana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataDB {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "content.db";
	
	private static final String TABLE_DATA = "table_data";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_TITLE = "Title";
	private static final int NUM_COL_TITLE = 1;	
	private static final String COL_TEXT = "Text";
	private static final int NUM_COL_TEXT = 2;
	
	private SQLiteDatabase bdd;
	
	private DBSQLite myDB;
	
	public DataDB(Context context) {
		myDB = new DBSQLite(context, DB_NAME, null, DB_VERSION);
	}
	
	public void open(){
		bdd = myDB.getWritableDatabase();
	}
	
	public void close(){
		bdd.close();
	}
	
	public SQLiteDatabase getDB(){
		return bdd;
	}
	
	public long insertData(Data data){
		ContentValues values = new ContentValues();
		values.put(COL_TITLE, data.getTitle());
		values.put(COL_TEXT, data.getText());
		return bdd.insert(TABLE_DATA, null, values);
	}
	
	public int updateData(int id, Data data){
		ContentValues values = new ContentValues();
		values.put(COL_TITLE, data.getTitle());
		values.put(COL_TEXT, data.getText());
		return bdd.update(TABLE_DATA, values, COL_ID + " = " + id, null);
	}
	
	public int removeData(int id){
		return bdd.delete(TABLE_DATA, COL_ID + " = " + id, null);
	}
	
	public Data getData(String title){
		Cursor c = bdd.query(TABLE_DATA, new String[] {COL_ID, COL_TITLE, COL_TEXT}, COL_TITLE + "LIKE \"" + title + "\"", null, null, null, null);
		return cursorToData(c);
	}
	
	private Data cursorToData(Cursor c){
		if (c.getCount() == 0)
			return null;
		
		c.moveToFirst();
		Data data = new Data();
		data.setId(c.getInt(NUM_COL_ID));
		data.setTitle(c.getString(NUM_COL_TITLE));
		data.setText(c.getString(NUM_COL_TEXT));
		c.close();
		return data;
	}
	
}
