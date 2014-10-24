package com.example.pumgrana;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * PumgranaDB manager.
 */
public class PumgranaDB {
	
	/** The Constant DB_VERSION. */
	private static final int DB_VERSION = 1;
	
	/** The Constant DB_NAME. */
	private static final String DB_NAME = "pumgrana.db";
	
	/** The Constant TABLE_DATA. */
	private static final String TABLE_DATA = "table_data";
	
	/** The Constant COL_ID. */
	private static final String COL_ID = "ID";
	
	/** The Constant NUM_COL_ID. */
	private static final int NUM_COL_ID = 0;
	
	/** The Constant COL_DATA_ID. */
	private static final String COL_DATA_ID = "Data";
	
	/** The Constant NUM_COL_DATA_ID. */
	private static final int NUM_COL_DATA_ID = 1;	
	
	/** The Constant COL_TITLE. */
	private static final String COL_TITLE = "Title";
	
	/** The Constant NUM_COL_TITLE. */
	private static final int NUM_COL_TITLE = 2;	
	
	/** The Constant COL_TEXT. */
	private static final String COL_TEXT = "Text";
	
	/** The Constant NUM_COL_TEXT. */
	private static final int NUM_COL_TEXT = 3;
	
	/** The Constant TABLE_TAG. */
	private static final String TABLE_TAG = "table_tag";
	
	/** The Constant COL_TAG_ID. */
	private static final String COL_TAG_ID = "Tag";
	
	/** The Constant NUM_COL_TAG_ID. */
	private static final int NUM_COL_TAG_ID = 1;
	
	/** The Constant COL_NAME. */
	private static final String COL_NAME = "Name";
	
	/** The Constant NUM_COL_NAME. */
	private static final int NUM_COL_NAME = 2;
	
	/** The Constant TABLE_DATA_TAG. */
	private static final String TABLE_DATA_TAG = "table_data_tag";
	
	/** The Constant NUM_X_COL_DATA_ID. */
	private static final int NUM_X_COL_DATA_ID = 1;	
	
	/** The Constant NUM_X_COL_TAG_ID. */
	private static final int NUM_X_COL_TAG_ID = 2;
	
	/** The bdd. */
	private SQLiteDatabase bdd;
	
	/** The my db. */
	private DBSQLite myDB;
	
	/**
	 * Instantiates a new pumgrana db.
	 *
	 * @param context the context
	 */
	public PumgranaDB(Context context) {
		myDB = new DBSQLite(context, DB_NAME, null, DB_VERSION);
	}
	
	/**
	 * Open.
	 */
	public void open(){
		bdd = myDB.getWritableDatabase();
	}
	
	/**
	 * Close.
	 */
	public void close(){
		bdd.close();
	}
	
	/**
	 * Gets the db.
	 *
	 * @return the db
	 */
	public SQLiteDatabase getDB(){
		return bdd;
	}
	
	/**
	 * Insert data.
	 *
	 * @param data the data
	 * @return the long
	 */
	public long insertData(Data data){
		ContentValues values = new ContentValues();
		values.put(COL_DATA_ID, data.getDataId());
		values.put(COL_TITLE, data.getTitle());
		values.put(COL_TEXT, data.getText());
		return bdd.insert(TABLE_DATA, null, values);
	}
	
	/**
	 * Insert tag.
	 *
	 * @param tag the tag
	 * @return the long
	 */
	public long insertTag(Tag tag){
		ContentValues values = new ContentValues();
		values.put(COL_TAG_ID, tag.getTagId());
		values.put(COL_NAME, tag.getName());
		return bdd.insert(TABLE_TAG, null, values);
	}
	
	/**
	 * Insert data tag.
	 *
	 * @param dTag the d tag
	 * @return the long
	 */
	public long insertDataTag(DataTag dTag){
		ContentValues values = new ContentValues();
		values.put(COL_DATA_ID, dTag.getDataId());
		values.put(COL_TAG_ID, dTag.getTagId());
		return bdd.insert(TABLE_DATA_TAG, null, values);
	}
	
	/**
	 * Update data.
	 *
	 * @param id the id
	 * @param data the data
	 * @return the int
	 */
	public int updateData(int id, Data data){
		ContentValues values = new ContentValues();
		values.put(COL_TITLE, data.getTitle());
		values.put(COL_TEXT, data.getText());
		return bdd.update(TABLE_DATA, values, COL_ID + " = " + id, null);
	}
	
	/**
	 * Update data.
	 *
	 * @param id the id
	 * @param data the data
	 * @return the int
	 */
	public int updateData(String id, Data data){
		ContentValues values = new ContentValues();
		values.put(COL_TITLE, data.getTitle());
		values.put(COL_TEXT, data.getText());
		return bdd.update(TABLE_DATA, values, COL_DATA_ID + " LIKE \"" + id + "\"", null);
	}

	/**
	 * Update tag.
	 *
	 * @param id the id
	 * @param tag the tag
	 * @return the int
	 */
	public int updateTag(int id, Tag tag){
		ContentValues values = new ContentValues();
		values.put(COL_NAME, tag.getName());
		return bdd.update(TABLE_TAG, values, COL_ID + " = " + id, null);
	}
	
	/**
	 * Update data tag.
	 *
	 * @param id the id
	 * @param dTag the d tag
	 * @return the long
	 */
	public long updateDataTag(int id, DataTag dTag){
		ContentValues values = new ContentValues();
		values.put(COL_DATA_ID, dTag.getDataId());
		values.put(COL_TAG_ID, dTag.getTagId());
		return bdd.update(TABLE_DATA_TAG, values, COL_ID + " = " + id, null);
	}
	
	/**
	 * Removes the data.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int removeData(int id){
		return bdd.delete(TABLE_DATA, COL_ID + " = " + id, null);
	}
	
	/**
	 * Removes the tag.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int removeTag(int id){
		return bdd.delete(TABLE_TAG, COL_ID + " = " + id, null);
	}
	
	/**
	 * Removes the data tag.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int removeDataTag(int id){
		return bdd.delete(TABLE_DATA_TAG, COL_ID + " = " + id, null);
	}
	
	/**
	 * Gets the data.
	 *
	 * @param title the title
	 * @return the data
	 */
	public Data getData(String title){
		Cursor c = bdd.query(TABLE_DATA, new String[] {COL_ID, COL_DATA_ID, COL_TITLE, COL_TEXT}, COL_TITLE + " LIKE \"" + title + "\"", null, null, null, null);
		return cursorToData(c);
	}
	
	/**
	 * Gets the tag.
	 *
	 * @param name the name
	 * @return the tag
	 */
	public Tag getTag(String name){
		Log.d("getTag", name);
		Cursor c = bdd.query(TABLE_TAG, new String[] {COL_ID, COL_TAG_ID, COL_NAME}, COL_NAME + " LIKE \"" + name + "\"", null, null, null, null);
		return cursorToTag(c);
	}
	
	/**
	 * Gets the dby t.
	 *
	 * @param t the t
	 * @return the dby t
	 */
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
	
	/**
	 * Gets the dby t.
	 *
	 * @param tl the tl
	 * @return the dby t
	 */
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
		Cursor c = bdd.query(TABLE_DATA_TAG, new String[] {COL_ID, COL_DATA_ID, COL_TAG_ID}, where, null, COL_DATA_ID, null, null);
		List<Data> listData = new ArrayList<Data>();
		while (c.moveToNext())
		{
			Log.d("getDbyT", "cursor");
			Data data = getDataById(c.getString(NUM_X_COL_DATA_ID));
			listData.add(data);
		}
		c.close();
		return listData;
	}
	
	/**
	 * Gets the tby d.
	 *
	 * @param d the d
	 * @return the tby d
	 */
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
	
	/**
	 * Gets the data by id.
	 *
	 * @param id the id
	 * @return the data by id
	 */
	public Data getDataById(String id){
		Cursor c = bdd.query(TABLE_DATA, new String[] {COL_ID, COL_DATA_ID, COL_TITLE, COL_TEXT}, COL_DATA_ID + " LIKE \"" + id + "\"", null, null, null, null);
		return cursorToData(c);
	}
	
	/**
	 * Gets the tag by id.
	 *
	 * @param id the id
	 * @return the tag by id
	 */
	public Tag getTagById(String id){
		Cursor c = bdd.query(TABLE_TAG, new String[] {COL_ID, COL_TAG_ID, COL_NAME}, COL_TAG_ID + " LIKE \"" + id + "\"", null, null, null, null);
		return cursorToTag(c);
	}
	
	/**
	 * Gets the all data.
	 *
	 * @return the all data
	 */
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
	
	/**
	 * Gets the all tag.
	 *
	 * @return the all tag
	 */
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
	
	/**
	 * Cursor to data.
	 *
	 * @param c the c
	 * @return the data
	 */
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
	
	/**
	 * Cursor to tag.
	 *
	 * @param c the c
	 * @return the tag
	 */
	private Tag cursorToTag(Cursor c){
		if (c.getCount() == 0) {
			Log.d("CtoT", "NULL");
			return null;
		}
		c.moveToFirst();
		Tag tag = new Tag();
		tag.setId(c.getInt(NUM_COL_ID));
		tag.setTagId(c.getString(NUM_COL_TAG_ID));
		tag.setName(c.getString(NUM_COL_NAME));
		c.close();
		return tag;
	}
}
