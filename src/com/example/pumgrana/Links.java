package com.example.pumgrana;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Links extends Activity {
	Misc m;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_links);
		// Show the Up button in the action bar.
		setupActionBar();
		String id = getIntent().getExtras().getString("id");
		m = new Misc(this);
		if (m.isOnline()) {
		getLinks(id);
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	private void getLinks(String id) {
		Requests req = new Requests();
		req.execute("get", "http://" + m.getIp() + "/api/link/list_from_content/"+id);
		try {
			String result = req.get(3, TimeUnit.SECONDS);
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("links");
			PumgranaDB dataDB = new PumgranaDB(this);
			dataDB.open();
			List<String> viewData = new ArrayList<String>();
			for (int i=0; i < jObj.length(); i++) {
				Data data = new Data();
				data = dataDB.getDataById(jObj.getJSONObject(i).getString("content_id"));
				viewData.add(data.getTitle());
			}
			dataDB.close();
			ListView l=(ListView) findViewById(R.id.listView1);
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, viewData);
	        l.setAdapter(adapter);
	        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        	@Override
	            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	        		Intent intent = new Intent(parent.getContext(), Content.class);
	        		String name = parent.getAdapter().getItem(position).toString();
//	        		String ids = contentIds.get(position);
	        		Bundle b = new Bundle();
	        		b.putString("name", name);
//	        		b.putString("ids", ids);
	        		intent.putExtras(b);
	        		startActivity(intent);
	        	}
	        });
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			Log.i("timeout", "timed out");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.links, menu);
		return true;
	}

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
			finish();
			//NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
