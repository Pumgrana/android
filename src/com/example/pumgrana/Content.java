package com.example.pumgrana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Content extends Activity {
	public String name;
	public String id;
	public String str = "[a]";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
//		name = getIntent().getExtras().getString("name");
//		id = getIntent().getExtras().getString("ids");
//		String conc = name.concat(id);
//		TextView t = (TextView) findViewById(R.id.textView1);
//		t.setText(conc);
//        new HttpAsyncTask().execute("http://163.5.84.222/api/content/detail/".concat(id));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}
	
    public void openTags(View view)
    {
    	Intent intent = new Intent(this, TagShow.class);
		Bundle b = new Bundle();
		b.putString("name", name);
		b.putString("ids", id);
		intent.putExtras(b);
    	startActivity(intent);
    }
    
    public void openLinks(View view)
    {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
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
