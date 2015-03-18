package com.example.pumgrana;

import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

// TODO: Auto-generated Javadoc
/**
 * Add a new content to Pumgrana
 */
public class AddContent extends Activity {
	
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
		setContentView(R.layout.activity_add_content);
		// Show the Up button in the action bar.
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.add_content, menu);
		return true;
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
			String text = t.getText().toString();
			EditText t2 = (EditText) findViewById(R.id.editText2);
			String title = t2.getText().toString();
			EditText t3 = (EditText) findViewById(R.id.editText3);
			String sum = t3.getText().toString();
			obj.put("title", title);
			obj.put("summary", sum);
			obj.put("body", text);
			req.execute("post", "http://" + m.getIp() + "/api/content/insert", obj.toString());
			String result = req.get(3, TimeUnit.SECONDS);
			Log.d("Response", result);
			JSONObject res = new JSONObject(result);
			if (res.getInt("status") == 200) {
				TextView saved = (TextView) findViewById(R.id.textView2);
				saved.setVisibility(View.VISIBLE);
				timerHandler.postDelayed(timerRunnable, 1000);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
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
