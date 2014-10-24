package com.example.pumgrana;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

// TODO: Auto-generated Javadoc
/**
 * Edit the tags of a content.
 */
public class TagEdit extends Activity {
    
    /** The list view. */
    ListView listView;
    
    /** The adapter. */
    ArrayAdapter<String> adapter;
	
	/** The id. */
	String id;
	
	/** The list tag. */
	List<Tag> listTag;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		
		id = getIntent().getExtras().getString("id");
    	PumgranaDB tagDB = new PumgranaDB(this);
    	tagDB.open();
    	listTag = tagDB.getAllTag();
    	List<Tag> curTag = tagDB.getTbyD(id);
    	Iterator<Tag> it = listTag.iterator();
		List<String> viewTag = new ArrayList<String>();
    	while (it.hasNext()) {
    		Tag tag = it.next();
    		viewTag.add(tag.getName());
    	}
    	tagDB.close();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, viewTag);
        listView = (ListView)findViewById(R.id.listView1);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        it = curTag.iterator();
        while (it.hasNext()) {
        	Tag tag = it.next();
        	int pos = adapter.getPosition(tag.getName());
        	listView.setItemChecked(pos, true);
        }
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 *
	 * @param view the view
	 */
	
	public void onSave(View view) {
        listView = (ListView)findViewById(R.id.listView1);
        PumgranaDB db = new PumgranaDB(this);
        db.open();
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
        	int pos = checked.keyAt(i);
        	if (checked.valueAt(i)) {
        		String tName = adapter.getItem(pos);
        		Tag t = db.getTag(tName);
        		String tag = t.getTagId();
        		DataTag dTag = new DataTag();
        		dTag.setDataId(id);
        		dTag.setTagId(tag);
        		db.insertDataTag(dTag);
        	}
        }
	}
	
	/**
	 * Setup action bar.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag_edit, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
