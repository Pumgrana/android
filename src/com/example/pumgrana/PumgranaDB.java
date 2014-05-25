package com.example.pumgrana;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PumgranaDB {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "pumgrana.db";
	
	private static final String TABLE_DATA = "table_data";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_DATA_ID = "Data";
	private static final int NUM_COL_DATA_ID = 1;	
	private static final String COL_TITLE = "Title";
	private static final int NUM_COL_TITLE = 2;	
	private static final String COL_TEXT = "Text";
	private static final int NUM_COL_TEXT = 3;
	
	private static final String TABLE_TAG = "table_tag";
	private static final String COL_TAG_ID = "Tag";
	private static final int NUM_COL_TAG_ID = 1;
	private static final String COL_NAME = "Name";
	private static final int NUM_COL_NAME = 2;
	
	private static final String TABLE_DATA_TAG = "table_data_tag";
	private static final int NUM_X_COL_DATA_ID = 1;	
	private static final int NUM_X_COL_TAG_ID = 2;
	private SQLiteDatabase bdd;
	
	private DBSQLite myDB;
	
	public PumgranaDB(Context context) {
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
		values.put(COL_DATA_ID, data.getDataId());
		values.put(COL_TITLE, data.getTitle());
		values.put(COL_TEXT, data.getText());
		return bdd.insert(TABLE_DATA, null, values);
	}
	
	public long insertTag(Tag tag){
		ContentValues values = new ContentValues();
		values.put(COL_TAG_ID, tag.getTagId());
		values.put(COL_NAME, tag.getName());
		return bdd.insert(TABLE_TAG, null, values);
	}
	
	public long insertDataTag(DataTag dTag){
		ContentValues values = new ContentValues();
		values.put(COL_DATA_ID, dTag.getDataId());
		values.put(COL_TAG_ID, dTag.getTagId());
		return bdd.insert(TABLE_DATA_TAG, null, values);
	}
	
	public int updateData(int id, Data data){
		ContentValues values = new ContentValues();
		values.put(COL_TITLE, data.getTitle());
		values.put(COL_TEXT, data.getText());
		return bdd.update(TABLE_DATA, values, COL_ID + " = " + id, null);
	}
	
	public int updateTag(int id, Tag tag){
		ContentValues values = new ContentValues();
		values.put(COL_NAME, tag.getName());
		return bdd.update(TABLE_TAG, values, COL_ID + " = " + id, null);
	}
	
	public long updateDataTag(int id, DataTag dTag){
		ContentValues values = new ContentValues();
		values.put(COL_DATA_ID, dTag.getDataId());
		values.put(COL_TAG_ID, dTag.getTagId());
		return bdd.update(TABLE_DATA_TAG, values, COL_ID + " = " + id, null);
	}
	
	public int removeData(int id){
		return bdd.delete(TABLE_DATA, COL_ID + " = " + id, null);
	}
	
	public int removeTag(int id){
		return bdd.delete(TABLE_TAG, COL_ID + " = " + id, null);
	}
	
	public int removeDataTag(int id){
		return bdd.delete(TABLE_DATA_TAG, COL_ID + " = " + id, null);
	}
	
	public Data getData(String title){
		Cursor c = bdd.query(TABLE_DATA, new String[] {COL_ID, COL_DATA_ID, COL_TITLE, COL_TEXT}, COL_TITLE + " LIKE \"" + title + "\"", null, null, null, null);
		return cursorToData(c);
	}
	
	public Tag getTag(String name){
		Cursor c = bdd.query(TABLE_TAG, new String[] {COL_ID, COL_TAG_ID, COL_NAME}, COL_NAME + " LIKE \"" + name + "\"", null, null, null, null);
		return cursorToTag(c);
	}
	
	public List<Data> getDbyT(String t) {
		Cursor c = bdd.query(TABLE_DATA_TAG, new String[] {COL_ID, COL_DATA_ID, COL_TAG_ID}, COL_TAG_ID + " LIKE \"" + t + "\"", null, null, null, null);
		List<Data> listData = new ArrayList<Data>();
		while (c.moveToNext())
		{
			Data data = getDataById(c.getString(NUM_X_COL_DATA_ID));
			listData.add(data);
		}
		c.close();
		return listData;
	}
	
	public List<Data> getDbyT(List<String> tl) {
		String where = "";
		Iterator<String> it = tl.iterator();
		while(it.hasNext()) {
			String s = it.next();
			where += COL_TAG_ID + " LIKE \"" + s + "\"";
			if (it.hasNext())
				where += " OR ";
		}
		Log.i("where clause", where);
		Cursor c = bdd.query(TABLE_DATA_TAG, new String[] {COL_ID, COL_DATA_ID, COL_TAG_ID}, where, null, null, null, null);
		List<Data> listData = new ArrayList<Data>();
		while (c.moveToNext())
		{
			Data data = getDataById(c.getString(NUM_X_COL_DATA_ID));
			listData.add(data);
		}
		c.close();
		return listData;
	}
	
	public List<Tag> getTbyD(String d) {
		Cursor c = bdd.query(TABLE_DATA_TAG, new String[] {COL_ID, COL_DATA_ID, COL_TAG_ID}, COL_DATA_ID + " LIKE \"" + d + "\"", null, null, null, null);
		List<Tag> listTag = new ArrayList<Tag>();
		while (c.moveToNext())
		{
			Tag tag = getTagById(c.getString(NUM_X_COL_TAG_ID));
			listTag.add(tag);
		}
		c.close();
		return listTag;
	}
	
	public Data getDataById(String id){
		Cursor c = bdd.query(TABLE_DATA, new String[] {COL_ID, COL_DATA_ID, COL_TITLE, COL_TEXT}, COL_DATA_ID + " LIKE \"" + id + "\"", null, null, null, null);
		return cursorToData(c);
	}
	
	public Tag getTagById(String id){
		Cursor c = bdd.query(TABLE_TAG, new String[] {COL_ID, COL_TAG_ID, COL_NAME}, COL_TAG_ID + " LIKE \"" + id + "\"", null, null, null, null);
		return cursorToTag(c);
	}
	
	public List<Data> getAllData(){
		Cursor c = bdd.query(TABLE_DATA, new String[] {COL_ID, COL_DATA_ID, COL_TITLE, COL_TEXT}, null, null, null, null, null);
		if (c.getCount() == 0)
			return null;
		List<Data> listData = new ArrayList<Data>();
		while (c.moveToNext())
		{
			Data data = new Data();
			data.setId(c.getInt(NUM_COL_ID));
			data.setDataId(c.getString(NUM_COL_DATA_ID));
			data.setTitle(c.getString(NUM_COL_TITLE));
			data.setText(c.getString(NUM_COL_TEXT));
			listData.add(data);
		}
		c.close();
		return listData;
	}
	
	public List<Tag> getAllTag(){
		Cursor c = bdd.query(TABLE_TAG, new String[] {COL_ID, COL_TAG_ID, COL_NAME}, null, null, null, null, null);
		if (c.getCount() == 0)
			return null;
		List<Tag> listTag = new ArrayList<Tag>();
		while (c.moveToNext())
		{
			Tag tag = new Tag();
			tag.setId(c.getInt(NUM_COL_ID));
			tag.setTagId(c.getString(NUM_COL_TAG_ID));
			tag.setName(c.getString(NUM_COL_NAME));
			listTag.add(tag);
		}
		c.close();
		return listTag;
	}
	
	private Data cursorToData(Cursor c){
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		Data data = new Data();
		data.setId(c.getInt(NUM_COL_ID));
		data.setDataId(c.getString(NUM_COL_DATA_ID));
		data.setTitle(c.getString(NUM_COL_TITLE));
		data.setText(c.getString(NUM_COL_TEXT));
		c.close();
		return data;
	}
	
	private Tag cursorToTag(Cursor c){
		if (c.getCount() == 0)
			return null;
		c.moveToFirst();
		Tag tag = new Tag();
		tag.setId(c.getInt(NUM_COL_ID));
		tag.setTagId(c.getString(NUM_COL_TAG_ID));
		tag.setName(c.getString(NUM_COL_NAME));
		c.close();
		return tag;
	}
}
