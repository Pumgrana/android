package com.example.pumgrana;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

// TODO: Auto-generated Javadoc
/**
 * Modify an existing content.
 */
public class Content_edit extends Activity {
	
	/** The name. */
	public String name;
	
	/** The id. */
	public String id;
	
	public String uri;
	/** The text. */
	public String text;
	
	/** The timer handler. */
	Handler timerHandler = new Handler();
	
	/** The timer runnable. */
	Runnable timerRunnable = new Runnable() {
		@Override
		public void run(){
			TextView t = (TextView) findViewById(R.id.textView2);
			t.setVisibility(View.INVISIBLE);
		}
	};
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_edit);
		// Show the Up button in the action bar.
		setupActionBar();
		uri = getIntent().getExtras().getString("uri");		
		getDetailsNoDB(uri);
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

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content_edit, menu);
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
			finish();
			//NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Gets the details.
	 *
	 * @param name the name
	 * @return the details
	 */
	
	private void getDetailsNoDB(String uri) {
		Misc m = new Misc(this);
    	Requests req = new Requests();
    	Log.d("fetchData", "req");
		try {
			req.execute("get", "http://" + m.getIp() + "/api/content/detail/" + URLEncoder.encode(uri, "utf-8"));
			String result = req.get(3, TimeUnit.SECONDS);
			JSONObject jObject = new JSONObject(result);
			JSONArray jObj = jObject.getJSONArray("contents");
			for (int i=0; i < jObj.length(); i++) {
		        TextView t = (TextView) findViewById(R.id.editText2);
				t.setText(jObj.getJSONObject(i).getString("title"));
				TextView t2 = (TextView) findViewById(R.id.editText1);
				t2.setText((jObj.getJSONObject(i)).getString("body"));
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
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getDetails(String name) {
    	PumgranaDB dataDB = new PumgranaDB(this);
		dataDB.open();
		Data data = dataDB.getData(name);
		id = data.getDataId();
		text = data.getText();
		dataDB.close();
		EditText t = (EditText) findViewById(R.id.editText1);
		t.setText(text);
	}

	/**
	 * On save.
	 *
	 * @param view the view
	 */
	public void onSave(View view) {
		Requests req = new Requests();
		JSONObject obj = new JSONObject();
		try {
			Log.d("Response", "Save");
			Misc m = new Misc(this);
			EditText t = (EditText) findViewById(R.id.editText1);
			text = t.getText().toString();
			EditText t2 = (EditText) findViewById(R.id.editText2);
			String title = t2.getText().toString();
			obj.put("content_uri", uri);
			obj.put("title", title);
			obj.put("summary", "");
			obj.put("body", text);
			Log.d("STRING", obj.toString());
			req.execute("post", "http://" + m.getIp() + "/api/content/update", obj.toString());
			String result = req.get(3, TimeUnit.SECONDS);
			Log.d("Response", result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
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
		
	}
	
	/**
	 * Open tags.
	 *
	 * @param view the view
	 */
	public void openTags(View view) {
		Intent intent = new Intent(this, TagEdit.class);
		Bundle b = new Bundle();
		b.putString("name", name);
		b.putString("id", id);
		Log.d("tags", name);
		Log.d("tags", id);
		intent.putExtras(b);
    	startActivity(intent);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	public void onBackPressed() {
        Bundle b = new Bundle();
        b.putString("uri", uri);
        Intent mIntent = new Intent();
        mIntent.putExtras(b);
        setResult(0, mIntent);
        super.onBackPressed();
    }
}
