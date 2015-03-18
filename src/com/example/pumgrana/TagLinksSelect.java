package com.example.pumgrana;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TagLinksSelect extends Activity {

    /** The view tag. */
    List<String> viewTag;
    ListView listView;
    /** The id tag. */
    List<String> idTag;
    
    /** The adapter. */
    ArrayAdapter<String> adapter;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_links_select);
        String checked = this.getIntent().getExtras().getString("checked");
        String [] array = checked.substring(1, checked.length() - 1).split(",");
        String uri = this.getIntent().getExtras().getString("uri");
        getTagNoDB(array, uri);

	}

    private void getTagNoDB(String [] array, String uri) {
    	Misc m = new Misc(this);
    	Requests req = new Requests();
        listView = (ListView) findViewById(R.id.listView1);
        try {
			req.execute("get", "http://" + m.getIp() + "/api/tag/list_from_content_links/" + URLEncoder.encode(uri, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag_links_select, menu);
		return true;
	}

}
