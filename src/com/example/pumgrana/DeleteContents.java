package com.example.pumgrana;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeleteContents extends Activity {

	Misc m;
	List<String> dataUri;
    ArrayAdapter<String> adapter;
	ListView l;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_contents);
		updateViewNoDB();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_contents, menu);
		return true;
	}

    private void updateViewNoDB()
    {
    	m = new Misc(this);
    	Requests req = new Requests();
        req.execute("get", "http://" + m.getIp() + "/api/content/list_content");
        try {
			String result = req.get(10, TimeUnit.SECONDS);
			if (result != null) {
				JSONObject jObject = new JSONObject(result);
				JSONArray jObj = jObject.getJSONArray("contents");
				List<String> viewData = new ArrayList<String>();
				dataUri = new ArrayList<String>();
				for (int i=0; i < jObj.length(); i++) {				
					viewData.add((jObj.getJSONObject(i)).getString("title"));
					dataUri.add((jObj.getJSONObject(i)).getString("uri"));
				}
		    l= (ListView) findViewById(R.id.listView1);
	        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, viewData);
	        l.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	        l.setAdapter(adapter);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void onDelete(View view) {
        SparseBooleanArray checked = l.getCheckedItemPositions();
        ArrayList<String> deleteUri = new ArrayList<String>();
        JSONArray jArr = new JSONArray();
        for (int i = 0; i < checked.size(); i++) {
        	int position = checked.keyAt(i);
        	jArr.put(dataUri.get(position));
        }
        Requests req = new Requests();
        JSONObject jObj = new JSONObject();
        try {
			jObj.put("contents_uri", jArr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.d("jObj", jObj.toString());
        req.execute("post", "http://" + m.getIp() + "/api/content/delete", jObj.toString());
        String result = null;
		try {
			result = req.get(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Log.d("RESULT", result);
        updateViewNoDB();
	}
}
