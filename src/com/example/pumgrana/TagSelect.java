package com.example.pumgrana;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
 
// TODO: Auto-generated Javadoc
/**
 * Select the tags to filter.
 */
public class TagSelect extends Activity implements
        OnClickListener {
    
    /** The button. */
    Button button;
    
    /** The list view. */
    ListView listView;
    
    /** The view tag. */
    List<String> viewTag;
    
    /** The id tag. */
    List<String> idTag;
    
    /** The adapter. */
    ArrayAdapter<String> adapter;
 
    /**
     *  Called when the activity is first created.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_select);
        Log.i("onCreate", "ok");
        String checked = this.getIntent().getExtras().getString("checked");
        Log.i("getStr", "ok");
        String [] array = checked.substring(1, checked.length() - 1).split(",");
        findViewsById();
        Log.i("array", "ok");
        for (String s : array)
        	Log.d("data", s);
        getTagNoDB(array);
/*        
    	PumgranaDB tagDB = new PumgranaDB(this);
    	tagDB.open();
    	List<Tag> listTag = tagDB.getAllTag();
    	Iterator<Tag> it = listTag.iterator();
		List<String> viewTag = new ArrayList<String>();
    	while (it.hasNext()) {
    		Tag tag = it.next();
    		viewTag.add(tag.getName());
    	}
    	tagDB.close();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, viewTag);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        int i = 0;
        for (String s : array)
        	if ((i = adapter.getPosition(s.trim())) != -1)
        			listView.setItemChecked(adapter.getPosition(s.trim()), true);*/
     }
 
    /**
     * Gets the tag no db.
     *
     * @param array the array
     * @return the tag no db
     */
    private void getTagNoDB(String [] array) {
    	Misc m = new Misc(this);
    	Requests req = new Requests();
        req.execute("get", "http://" + m.getIp() + "/api/tag/list_by_type/CONTENT");
        try {
			String result = req.get(10, TimeUnit.SECONDS);
			Log.d("RESULT", result);
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("tags");
			List<String> viewTag = new ArrayList<String>();
			idTag = new ArrayList<String>();
			for (int i=0; i < jObj.length(); i++) {
				idTag.add(jObj.getJSONObject(i).getString("uri"));
				viewTag.add(jObj.getJSONObject(i).getString("subject"));
			}
	        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, viewTag);
	        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	        listView.setAdapter(adapter);
	        int i = 0;
	        for (String s : array)
	        	if ((i = adapter.getPosition(s.trim())) != -1)
	        		listView.setItemChecked(adapter.getPosition(s.trim()), true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			Log.i("timeout", "timed out");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    /**
     * Find views by id.
     */
    private void findViewsById() {
        listView = (ListView) findViewById(R.id.listView1);
    }
 
    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        ArrayList<String> selectedId = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i)) {
                selectedItems.add(adapter.getItem(position));
            	selectedId.add(idTag.get(position));
            }
        }
 
        String[] outputStrArr = new String[selectedItems.size()];
        String[] idArr = new String[selectedItems.size()];
        
        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
            
        }
        
        TextView t = (TextView) findViewById(R.id.textView1);
        t.setText(Arrays.toString(outputStrArr));
        Intent intent = new Intent(getApplicationContext(),
                ResultActivity.class);
 
        // Create a bundle object
        Bundle b = new Bundle();
        b.putStringArray("selectedItems", outputStrArr);
        b.putStringArrayList("ids", selectedId);
        // Add the bundle to the intent.
        intent.putExtras(b);
 
        // start the ResultActivity
        startActivity(intent);
    }
    
	/**
	 * On add.
	 *
	 * @param view the view
	 */
	public void onAdd(View view) {
		Intent intent = new Intent(this, TagAdd.class);
    	startActivity(intent);
	}

    
    /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    public void onBackPressed() {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        ArrayList<String> selectedIds = new ArrayList<String>();
        if (checked != null) {
	        for (int i = 0; i < checked.size(); i++) {
	            // Item position in adapter
	            int position = checked.keyAt(i);
	            // Add sport if it is checked i.e.) == TRUE!
	            if (checked.valueAt(i)) {
	            	Log.d("Position", String.valueOf(position));
	            	Log.d("Size", String.valueOf(idTag.size()));
	                selectedItems.add(adapter.getItem(position));
	            	selectedIds.add(idTag.get(position));
	            }
	        }
        }
        String[] outputStrArr = new String[selectedItems.size()];
        
        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }
        String tags = Arrays.toString(outputStrArr);
        Bundle b = new Bundle();
        b.putString("Tags", tags);
        b.putStringArrayList("ids", selectedIds);
        Intent mIntent = new Intent();
        mIntent.putExtras(b);
        TextView t = (TextView) findViewById(R.id.textView1);
        t.setText(Arrays.toString(outputStrArr));
        setResult(0, mIntent);
        super.onBackPressed();
    }
}