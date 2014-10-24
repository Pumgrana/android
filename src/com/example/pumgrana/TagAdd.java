package com.example.pumgrana;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

// TODO: Auto-generated Javadoc
/**
 * Add a new tag to Pumgrana.
 */
public class TagAdd extends Activity {
	
	/** The m. */
	public Misc m;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_add);
		m = new Misc(this);
	}

	/**
	 * On save.
	 *
	 * @param view the view
	 */
	public void onSave(View view) {
		try {
			Requests req = new Requests();
			EditText tagEdit = (EditText) findViewById(R.id.editText1);
			String tag = tagEdit.getText().toString();
			JSONObject obj = new JSONObject();
			obj.put("type_name", "CONTENT");
			JSONArray arr = new JSONArray();
			arr.put(tag);
			obj.put("tags_subject", arr);
			req.execute("post", "http://" + m.getIp() + "/api/tag/insert", obj.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag_add, menu);
		return true;
	}

}
