package com.example.pumgrana;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Content extends Activity {
	public String name;
	public String id;
	public String str = "[a]";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		setupActionBar();
		Log.i("attention", "crash");
		name = getIntent().getExtras().getString("name");
		Log.i("attention", "crashed");
//		id = getIntent().getExtras().getString("ids");
		TextView t = (TextView) findViewById(R.id.textView1);
		t.setText(name);
		getDetails(name);
//        new HttpAsyncTask().execute("http://163.5.84.222/api/content/detail/".concat(id));
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}

	private void getDetails(String name) {
    	PumgranaDB dataDB = new PumgranaDB(this);
		dataDB.open();
		Data data = dataDB.getData(name);
		id = data.getDataId();
		dataDB.close();
		TextView t = (TextView) findViewById(R.id.textView2);
		t.setText(data.getText());
	}
    public void openTags(View view)
    {
    	Intent intent = new Intent(this, TagShow.class);
		Bundle b = new Bundle();
		b.putString("name", name);
		b.putString("id", id);
		intent.putExtras(b);
    	startActivity(intent);
    }
    
    public void openLinks(View view)
    {
    	Intent intent = new Intent(this, Links.class);
    	Bundle b = new Bundle();
    	b.putString("id", id);
    	intent.putExtras(b);
    	startActivity(intent);
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
/*    
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return connect(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            try {
				JSONObject jObject = new JSONObject(result);
				JSONArray jObj = jObject.getJSONArray("contents");
				String text = jObj.getJSONObject(0).getString("text");
				TextView t = (TextView) findViewById(R.id.textView2);
				t.setText(text);
			} catch (JSONException e) {
				e.printStackTrace();
			}
       }
    }
    
    public static String connect(String url)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url); 
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                instream.close();
                return result;
            }
            return null;
        } catch (Exception e) {Log.e("error", e.toString());}
		return null;
    }
    
    private static String convertStreamToString(InputStream is) {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    	StringBuilder sb = new StringBuilder();

    	String line = null;
    	try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
*/    
}
